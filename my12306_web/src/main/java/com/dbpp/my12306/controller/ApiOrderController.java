package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Order;
import com.dbpp.my12306.entity.Ticket;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.OrderService;
import com.dbpp.my12306.service.TicketService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiOrderController {
	private final AuthService authService;
	private final OrderService orderService;
	private final TicketService ticketService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private String detail;

	public ApiOrderController(AuthService authService, OrderService orderService,
	                          TicketService ticketService, LoggerService loggerService) {
		this.authService = authService;
		this.orderService = orderService;
		this.ticketService = ticketService;
		this.loggerService = loggerService;
	}


	@RequestMapping(value = "/order/{order_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfo(@PathVariable(name = "order_id") Integer orderId,
	                              HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}
		var ret = new ResponseSet<>();
		Order data;
		List<Ticket> tickets;
		try {
			while (true) {
				data = orderService.getInfo(orderId);
				if (data == null) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such order", null);
					break;
				}
				if (data.getUserId() != auth.getData().getUserId()) {
					ret = new ResponseSet<>(ResultCode.INVALID_AUTH,
							"Current user has no authorization to visit this order.", null);
					break;
				}
				tickets = ticketService.getAllOf(orderId);
				ret.setData(new HashMap<String, Object>() {{
					this.put("order", data);
					this.put("tickets", tickets);
				}});
				ret.setStatus(ResultCode.SUCCESS);

				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiOrderController.getInfo orderId=" + orderId
				+ " auth=" + auth.getData().getUserName(), ret.getStatus() + " " + ret.getDetail());
		return ret;

	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public ResponseSet<?> getAll(HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}
		var ret = new ResponseSet<>();
		List<Order> data;
		try {
			while (true) {
				data = orderService.getAll(auth.getData().getUserId());
				if (data.isEmpty()) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such order", null);
					break;
				}
				ret.setStatus(ResultCode.SUCCESS);
				ret.setData(data);
				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiOrderController.getAll"
				+ " auth=" + auth.getData().getUserName(), ret.getStatus() + " " + ret.getDetail());
		return ret;

	}

	@RequestMapping(value = "/admin/order/{order_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@PathVariable(name = "order_id") Integer orderId,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<>();
		Order data;
		List<Ticket> tickets;
		try {
			while (true) {
				data = orderService.getInfo(orderId);
				if (data == null) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such order", null);
					break;
				}
				tickets = ticketService.getAllOf(orderId);
				ret.setData(new HashMap<String, Object>() {{
					this.put("order", data);
					this.put("tickets", tickets);
				}});
				ret.setStatus(ResultCode.SUCCESS);

				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiOrderController.getInfo orderId=" + orderId
				+ " auth=" + auth.getData().getAdminName(), ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	@RequestMapping(value = "/admin/order/{order_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("order_id") Integer orderId,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = orderService.delete(orderId);
			if (r == 1) {
				ret.setDetail(detail);
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such order.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiOrderController.delete orderId=" + orderId +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

}
