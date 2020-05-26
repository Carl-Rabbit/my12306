package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Station;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.StationService;
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
public class ApiStationController {
	private final AuthService authService;
	private final StationService stationService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiStationController(AuthService authService, StationService stationService, LoggerService loggerService) {
		this.authService = authService;
		this.stationService = stationService;
		this.loggerService = loggerService;
	}


	@RequestMapping(value = "/station", method = RequestMethod.GET)
	public ResponseSet<?> getInfo(@RequestParam(name = "id", required = false) Integer stationId,
	                              @RequestParam(name = "name", required = false) String stationName,
	                              @RequestParam(name = "city_name", required = false) String cityName) {

		var ret = new ResponseSet<List<Station>>();
		try {
			List<Station> data = stationService.get(stationId, stationName, cityName);
			if (data.isEmpty()) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such station", null);
			} else {
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiStationController.getInfo stationId=" + stationId
				+ " stationName=" + stationName, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}


	@RequestMapping(value = "/admin/station", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		Station station = new Station();
		ResponseSet<Integer> ret;
		String name = m.get("station_name");
		String stationCode = m.get("station_code");
		int cityId = Integer.parseInt(m.getOrDefault("city_id", "-1"));
		if (name == null || cityId == -1) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
		} else if (stationCode != null && stationCode.length() != 3) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param station_code must be 3 upper letters.", null);
		} else {
			ret = new ResponseSet<>();

			station.setStationName(name);
			station.setStationCode(stationCode);
			station.setCityId(cityId);
			try {
				stationService.add(station);
				ret.setStatus(ResultCode.CREATED);
				ret.setData(station.getStationId());
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
		loggerService.info(logger, "ApiStationController.add auth=" + auth.getData().getAdminName()
				+ " station=" + station, ret);
		return ret;
	}


	@RequestMapping(value = "/admin/station/{station_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("station_id") Integer stationId,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = stationService.delete(stationId);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such station.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiStationController.delete stationId=" + stationId +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/station", method = RequestMethod.DELETE)
	public ResponseSet<?> deleteByName(@RequestParam("name") String name,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = stationService.deleteByName(name);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such station.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiStationController.delete name=" + name +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}
}
