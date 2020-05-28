package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.City;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.CityService;
import com.dbpp.my12306.service.LoggerService;
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
public class ApiCityController {
	private final AuthService authService;
	private final CityService cityService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiCityController(AuthService authService, CityService cityService, LoggerService loggerService) {
		this.authService = authService;
		this.cityService = cityService;
		this.loggerService = loggerService;
	}


	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public ResponseSet<?> getInfo(@RequestParam(name = "id", required = false) Integer cityId,
	                              @RequestParam(name = "name", required = false) String cityName,
	                              @RequestParam(required = false) String province) {

		var ret = new ResponseSet<List<City>>();
		try {
			List<City> data = cityService.get(cityId, cityName, province);
			if (data.isEmpty()) {
				ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such city", null);
			} else {
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiCityController.getInfo cityId=" + cityId
				+ " cityName=" + cityName + " province=" + province, ret);
		return ret;
	}


	@RequestMapping(value = "/admin/city", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		City city = new City();
		ResponseSet<Integer> ret;
		String name = m.get("city_name");
		String province = m.get("province");
		if (name == null || province == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
		} else {
			ret = new ResponseSet<>();

			city.setName(name);
			city.setProvince(province);
			try {
				cityService.add(city);
				ret.setStatus(ResultCode.CREATED);
				ret.setData(city.getCityId());
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
				ret.setDetail((msg.contains("Detail: ")) ?
						msg.split("Detail: ")[1] : msg);
				e.printStackTrace();
			}
		}
		loggerService.info(logger, "ApiCityController.add auth=" + auth.getData().getAdminName()
				+ " city=" + city, ret);
		return ret;
	}


	@RequestMapping(value = "/admin/city/{city_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("city_id") Integer cityId,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = cityService.delete(cityId);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such city.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiCityController.delete cityId=" + cityId +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/city", method = RequestMethod.DELETE)
	public ResponseSet<?> deleteByName(@RequestParam("name") String name,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = cityService.deleteByName(name);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such city.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiCityController.delete name=" + name +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}
}
