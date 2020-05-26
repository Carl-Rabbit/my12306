package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Seat;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.SeatService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiSeatController {
	private final AuthService authService;
	private final SeatService seatService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiSeatController(AuthService authService, SeatService seatService,
	                         LoggerService loggerService) {
		this.authService = authService;
		this.seatService = seatService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/admin/seat/{train_no}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@PathVariable(name = "train_no") String trainNo,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<List<Seat>>();
		try {
			if (trainNo.length() != 12) {
				ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param train_code's length must be 12.", null);
			} else {
				List<Seat> data = seatService.getAllOf(trainNo);
				if (data == null || data.isEmpty()) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such seat", null);
				} else {
					ret.setData(data);
					ret.setStatus(ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiSeatController.getInfoAdmin seatId=" + trainNo
				+ " auth=" + auth.getData().getAdminName(), ret.getStatus() + " " + ret.getDetail());
		return ret;
	}


	@RequestMapping(value = "/admin/seat", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		Seat seat = new Seat();
		ResponseSet<Long> ret;
		String trainNo = m.get("train_no");
		int carriage = Integer.parseInt(m.getOrDefault("carriage", "-1"));
		short location = Short.parseShort(m.getOrDefault("location", "-1"));
		String code = m.get("code");
		String cls = m.get("cls");
		String type = m.get("type");
		if (trainNo == null || carriage == -1 || location == -1 || code == null
				|| cls == null || type == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
		} else if (trainNo.length() != 12) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param train_code's length must be 12.", null);
		} else {
			ret = new ResponseSet<>();

			seat.setTrainNo(trainNo);
			seat.setCarriage(carriage);
			seat.setLocation(location);
			seat.setCode(code);
			seat.setCls(cls);
			seat.setType(type);

			try {
				seatService.add(seat);
				ret.setStatus(ResultCode.CREATED);
				ret.setData(seat.getSeatId());
			} catch (Exception e) {
				ret.setDetail(null);
				if (e.getCause().getMessage().contains("duplicate key")) {
					ret.setStatus(ResultCode.SUCCESS_IS_HAVE);
				} else if (e.getCause().getMessage().contains("row contains")) {
					ret.setStatus(ResultCode.CONS_ERROR);
				} else {
					ret.setStatus(ResultCode.EXCEPTION);
				}
				String msg = e.getCause().getMessage();
				ret.setDetail((msg.contains("详细：")) ?
						msg.split("详细：")[1] : msg);
				e.printStackTrace();
			}
		}
		loggerService.info(logger, "ApiSeatController.add auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	@RequestMapping(value = "/admin/seat/{seat_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("seat_id") Long seatId,
	                                  HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {

			int r = seatService.deleteById(seatId);
			if (r >= 1) {
				ret.setDetail("Delete completed, " + r + " row(s) affected.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such seat.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiSeatController.deleteAllOf seatId=" + seatId +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}


	@RequestMapping(value = "/admin/seats", method = RequestMethod.DELETE)
	public ResponseSet<?> deleteAllOf(@RequestParam("train_no") String trainNo,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			if (trainNo.length() != 12) {
				ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param train_code's length must be 12.", null);
			} else {
				int r = seatService.deleteAllOf(trainNo);
				if (r >= 1) {
					ret.setDetail("Delete completed, " + r + " row(s) affected.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such seat.");
					ret.setStatus(ResultCode.FAIL);
				}
				ret.setData(r);
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiSeatController.deleteAllOf seatId=" + trainNo +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}
}
