package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Passenger;
import com.dbpp.my12306.entity.Ticket;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.PassengerService;
import com.dbpp.my12306.service.TicketService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiTicketController {
	private final AuthService authService;
	private final TicketService ticketService;
	private final PassengerService passengerService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiTicketController(AuthService authService, TicketService ticketService,
	                           PassengerService passengerService, LoggerService loggerService) {
		this.authService = authService;
		this.ticketService = ticketService;
		this.passengerService = passengerService;
		this.loggerService = loggerService;
	}


	@RequestMapping(value = "/ticket/{ticket_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfo(@PathVariable(name = "ticket_id") Integer ticketId,
	                              HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<Ticket>();
		try {
			Ticket data = ticketService.getInfo(ticketId);
			if (data == null) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such ticket", null);
			} else {
				Passenger psg = passengerService.getById(data.getPassengerId(), true).getData();
				if (psg == null || psg.getUserId() != auth.getData().getUserId()) {
					ret.setDetail("Current user has no authorization to visit this ticket.");
					ret.setStatus(ResultCode.INVALID_AUTH);
				} else {
					ret.setData(data);
					ret.setStatus(ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiTicketController.getInfo ticketId=" + ticketId
				+ " auth=" + auth.getData().getUserName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/ticket/{ticket_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@PathVariable(name = "ticket_id") Integer ticketId,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<Ticket>();
		try {
			Ticket data = ticketService.getInfo(ticketId);
			if (data == null) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such city", null);
			} else {
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiTicketController.getInfo ticketId=" + ticketId
				+ " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}


	@RequestMapping(value = "/ticket", method = RequestMethod.PUT)
	@Transactional
	public ResponseSet<?> operate(@RequestBody Map<String, String> m,
	                              HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<Ticket>();

		try {
			int ticketId = Integer.parseInt(m.getOrDefault("ticket_id", "-1"));
			String opt = m.get("opt");
			if (ticketId == -1 || !"refund".equals(opt)) {
				ret.setStatus(ResultCode.PARAMS_ERROR);
				ret.setDetail("Bad param.");
			} else {
				Ticket data = ticketService.getInfo(ticketId);
				if (data == null) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such ticket", null);
				} else {
					Passenger psg = passengerService.getById(data.getPassengerId(), true).getData();
					if (psg == null || psg.getUserId() != auth.getData().getUserId()) {
						ret.setDetail("Current user has no authorization to update this ticket.");
						ret.setStatus(ResultCode.INVALID_AUTH);
					} else {
						// ready to set
						Integer r = ticketService.refund(ticketId);
						if (r == null || r == 0) {
							ret.setDetail("Refund fail due to unknown error.");
							ret.setData(data);
							ret.setStatus(ResultCode.FAIL);
						} else {
							ret.setDetail("Refund success");
							data.setStatus("R");
							ret.setData(data);
							ret.setStatus(ResultCode.SUCCESS);
						}
					}
				}
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		loggerService.info(logger, "ApiTicketController.update m=" + m
				+ " auth=" + auth.getData().getUserName(), ret);
		return ret;
	}


	@RequestMapping(value = "/admin/ticket/{ticket_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("ticket_id") Integer ticketId,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = ticketService.delete(ticketId);
			if (r == 1) {
				ret.setDetail("Delete completed. WRING: some other data is deleted cascade.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such ticket.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiTicketController.delete ticketId=" + ticketId +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

}
