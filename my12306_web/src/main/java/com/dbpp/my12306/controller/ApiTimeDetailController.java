package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.TimeDetail;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.TimeDetailService;
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
public class ApiTimeDetailController {
	private final AuthService authService;
	private final TimeDetailService timeDetailService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiTimeDetailController(AuthService authService, TimeDetailService timeDetailService,
	                               LoggerService loggerService) {
		this.authService = authService;
		this.timeDetailService = timeDetailService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/admin/time_detail/{detail_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@PathVariable(name = "detail_id") Integer timeDetailId,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<TimeDetail>();
		try {
			TimeDetail data = timeDetailService.get(timeDetailId);
			if (data == null) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such time detail info", null);
			} else {
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiTimeDetailController.getInfoAdmin timeDetailId=" + timeDetailId
				+ " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/time_details", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdminAllOf(@RequestParam(name = "train_code") String trainCode,
	                                        HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<List<TimeDetail>>();
		try {
			List<TimeDetail> data = timeDetailService.getAllOf(trainCode);
			if (data == null) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such time detail info", null);
			} else {
				ret.setDetail(data.size() + " detail(s) fetched.");
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiTimeDetailController.getInfoAdminAllOf trainCode=" + trainCode
				+ " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/time_detail", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		TimeDetail timeDetail = new TimeDetail();
		ResponseSet<Integer> ret = new ResponseSet<>();

		try {
			while (true) {
				String trainCode = m.get("train_code");
				int stationId = Integer.parseInt(m.getOrDefault("station_id", "-1"));
				int stationIndex = Integer.parseInt(m.getOrDefault("station_index", "-1"));
				String arriveTime = m.get("arrive_time");
				String leaveTime = m.get("leave_time");
				String runtime = m.get("runtime");
				int mileage = Integer.parseInt(m.getOrDefault("mileage", "-1"));

				if (trainCode == null || stationId == -1 || stationIndex == -1 || arriveTime == null
						|| leaveTime == null || runtime == null || mileage == -1) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
					break;
				}
				if (!arriveTime.matches("\\d{2}:\\d{2}:\\d{2}")
						|| !leaveTime.matches("\\d{2}:\\d{2}:\\d{2}")
						|| !runtime.matches("\\d{2}:\\d{2}:\\d{2}")) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Time param must in format HH:MM:SS.", null);
					break;
				}

				timeDetail.setTrainCode(trainCode);
				timeDetail.setStationId(stationId);
				timeDetail.setStationIndex(stationIndex);
				timeDetail.setArriveTime(arriveTime);
				timeDetail.setLeaveTime(leaveTime);
				timeDetail.setRuntime(runtime);
				timeDetail.setMileage(mileage);

				timeDetailService.add(timeDetail);

				ret.setStatus(ResultCode.CREATED);
				ret.setDetail("Time detail created");
				ret.setData(timeDetail.getTimeDetailId());
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
				ret.setDetail((msg.contains("Detail: ")) ?
						msg.split("Detail: ")[1] : msg);
			}
			e.printStackTrace();
		}
		loggerService.info(logger, "ApiTimeDetailController.add auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	@RequestMapping(value = "/admin/time_detail", method = RequestMethod.PUT)
	public ResponseSet<?> update(@RequestBody Map<String, String> m,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		TimeDetail timeDetail = new TimeDetail();
		ResponseSet<Integer> ret = new ResponseSet<>();

		try {
			while (true) {
				int timeDetailId = Integer.parseInt(m.getOrDefault("time_detail_id", "-1"));
				String trainCode = m.get("train_code");
				int stationId = Integer.parseInt(m.getOrDefault("station_id", "-1"));
				int stationIndex = Integer.parseInt(m.getOrDefault("station_index", "-1"));
				String arriveTime = m.get("arrive_time");
				String leaveTime = m.get("leave_time");
				String runtime = m.get("runtime");
				int mileage = Integer.parseInt(m.getOrDefault("mileage", "-1"));

				if (timeDetailId == -1) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing required param: time_detail_id.", null);
					break;
				}
				if (trainCode == null && stationId == -1 && stationIndex == -1 && arriveTime == null
						&& leaveTime == null && runtime == null && mileage == -1) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Required one update param.", null);
					break;
				}
				if ((arriveTime != null && !arriveTime.matches("\\d{2}:\\d{2}:\\d{2}"))
						|| (leaveTime != null) && !leaveTime.matches("\\d{2}:\\d{2}:\\d{2}")
						|| (runtime != null) && !runtime.matches("\\d{2}:\\d{2}:\\d{2}")) {
					ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Time param must in format HH:MM:SS.", null);
					break;
				}

				timeDetail.setTimeDetailId(timeDetailId);
				timeDetail.setTrainCode(trainCode);
				timeDetail.setStationId(stationId == -1 ? null : stationId);
				timeDetail.setStationIndex(stationIndex == -1 ? null : stationIndex);
				timeDetail.setArriveTime(arriveTime);
				timeDetail.setLeaveTime(leaveTime);
				timeDetail.setRuntime(runtime);
				timeDetail.setMileage(mileage == -1 ? null : mileage);

				int r = timeDetailService.update(timeDetail);
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
				ret.setDetail((msg.contains("Detail: ")) ?
						msg.split("Detail: ")[1] : msg);
			}
			e.printStackTrace();
		}
		loggerService.info(logger, "ApiTimeDetailController.update auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}


	@RequestMapping(value = "/admin/time_detail", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@RequestParam(name = "train_code") String trainCode,
	                             @RequestParam(name = "indexes", required = false) Integer[] indexes,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			while (true) {
				if (indexes == null || indexes.length == 0) {
					ret.setDetail("Require one non-empty list param: ids or indexes");
					ret.setStatus(ResultCode.PARAMS_ERROR);
					break;
				}

				int r = timeDetailService.deleteByIndexes(trainCode, indexes);
				if (r >= 1) {
					ret.setDetail("Delete completed, " + r + " row(s) affected.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such time details.");
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
		loggerService.info(logger, "ApiTimeDetailController.delete m=" +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/time_details", method = RequestMethod.DELETE)
	public ResponseSet<?> deleteAllOf(@RequestParam(name = "train_code") String trainCode,
	                                  HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = timeDetailService.deleteAllOf(trainCode);
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
