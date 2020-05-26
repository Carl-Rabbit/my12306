package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.RouteSchedule;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.RouteScheduleService;
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
public class ApiRouteScheduleController {
	private final AuthService authService;
	private final RouteScheduleService routeScheduleService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiRouteScheduleController(AuthService authService, RouteScheduleService routeScheduleService,
	                                  LoggerService loggerService) {
		this.authService = authService;
		this.routeScheduleService = routeScheduleService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/admin/route_sch/{train_code}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdminAllOf(@PathVariable(name = "train_code") String trainCode,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<List<RouteSchedule>>();
		try {
			List<RouteSchedule> data = routeScheduleService.getAllOf(trainCode);
			if (data == null || data.isEmpty()) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such route schedule info", null);
			} else {
				ret.setDetail(data.size() + " schedule(s) fetched.");
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiRouteScheduleController.getInfoAdminAllOf trainCode=" + trainCode
				+ " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/route_sch", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		RouteSchedule routeSchedule = new RouteSchedule();
		ResponseSet<Long> ret = new ResponseSet<>();

		try {
			while (true) {
				String trainCode = m.get("train_code");
				String trainNo = m.get("train_no");
				String departDate = m.get("depart_date");
				String status = m.getOrDefault("status", null);

				if (trainCode == null || trainNo == null
						|| departDate == null) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
					break;
				}
				if (!departDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Date param must in format YYYY-MM-DD.", null);
					break;
				}

				routeSchedule.setTrainCode(trainCode);
				routeSchedule.setTrainNo(trainNo);
				routeSchedule.setDepartDate(departDate);
				routeSchedule.setStatus(status);

				routeScheduleService.add(routeSchedule);

				ret.setStatus(ResultCode.CREATED);
				ret.setDetail("Route schedule created");
				ret.setData(routeSchedule.getRouteId());
				break;
			}
		} catch (Exception e) {
			ret.setDetail(null);
			if (e.getCause() == null) {
				ret.setDetail(e.getMessage());
			} else {
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
			}
			e.printStackTrace();
		}
		loggerService.info(logger, "ApiRouteScheduleController.add auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	@RequestMapping(value = "/admin/route_sch", method = RequestMethod.PUT)
	public ResponseSet<?> update(@RequestBody Map<String, String> m,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		RouteSchedule routeSchedule = new RouteSchedule();
		ResponseSet<Integer> ret = new ResponseSet<>();

		try {
			while (true) {
				long routeScheduleId = Long.parseLong(m.getOrDefault("route_sch_id", "-1"));
				String trainCode = m.get("train_code");
				String trainNo = m.get("train_no");
				String departDate = m.get("depart_date");
				String status = m.getOrDefault("status", null);

				if (routeScheduleId == -1) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Must provide param: route_sch_id", null);
					break;
				}
				if (trainCode == null && trainNo == null
						&& departDate == null && status == null) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Require one param to update.", null);
					break;
				}
				if (departDate != null && !departDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Date param must in format YYYY-MM-DD.", null);
					break;
				}

				routeSchedule.setRouteId(routeScheduleId);
				routeSchedule.setTrainCode(trainCode);
				routeSchedule.setTrainNo(trainNo);
				routeSchedule.setDepartDate(departDate);
				routeSchedule.setStatus(status);

				int r = routeScheduleService.update(routeSchedule);
				if (r >= 1) {
					ret.setStatus(ResultCode.SUCCESS);
					ret.setDetail("Time detail updated");
				} else {
					ret.setStatus(ResultCode.FAIL);
					ret.setDetail("Nothing changed");
				}
				ret.setData(r);
				break;
			}
		} catch (Exception e) {
			ret.setDetail(null);
			if (e.getCause() == null) {
				ret.setDetail(e.getMessage());
			} else {
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
			}
			e.printStackTrace();
		}
		loggerService.info(logger, "ApiRouteScheduleController.update auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}


	@RequestMapping(value = "/admin/route_sch/{route_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("route_id") Long routeScheduleId,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			while (true) {
				int r = routeScheduleService.delete(routeScheduleId);
				if (r >= 1) {
					ret.setDetail("Delete completed.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such route schedule.");
					ret.setStatus(ResultCode.FAIL);
				}
				ret.setData(r);
				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiRouteScheduleController.delete m=" +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/route_sch", method = RequestMethod.DELETE)
	public ResponseSet<?> deleteAllOf(@RequestParam(name = "train_code") String trainCode,
	                                  HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = routeScheduleService.deleteAllOf(trainCode);
			if (r >= 1) {
				ret.setDetail("Delete completed, " + r + " row(s) affected.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such time detail.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);


		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiTimeDetailController.deleteAllOf trainCode=" + trainCode +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}
}
