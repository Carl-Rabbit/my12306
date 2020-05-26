package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Admin;
import com.dbpp.my12306.entity.Passenger;
import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.PassengerService;
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
public class ApiPassengerController {
	private final AuthService authService;
	private final PassengerService passengerService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiPassengerController(AuthService authService, PassengerService PassengerService,
	                              LoggerService loggerService) {
		this.authService = authService;
		this.passengerService = PassengerService;
		this.loggerService = loggerService;
	}


	/**
	 * Get one passenger
	 * Can only get this user's passenger
	 *
	 * @param request user request
	 * @return Passenger info
	 */
	@RequestMapping(value = "/psg/{psg_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfo(@PathVariable(name = "psg_id") Integer psgId,
	                              HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		User user = auth.getData();
		var ret = passengerService.getById(psgId, false);
		Passenger data = ret.getData();
		if (data != null && data.getUserId() != user.getUserId()) {
			ret = new ResponseSet<>(ResultCode.INVALID_AUTH, "The user has no privilege " +
					"to visit this passenger", null);
		}
		loggerService.info(logger, "ApiUserController.getInfo psgId=" + psgId
						+ " auth=" + user.getUserName()
				, ret);
		return ret;
	}


	/**
	 * Get all passengers of this user
	 * Can only get this user's passenger
	 *
	 * @param request user request
	 * @return List of passengers
	 */
	@RequestMapping(value = "/psgs", method = RequestMethod.GET)
	public ResponseSet<?> getInfos(HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		User user = auth.getData();
		var ret = passengerService.getAllOf(user.getUserId(), false);
		loggerService.info(logger, "ApiPassengerController.getInfos " +
				"auth=" + user.getUserName(), ret);
		return ret;
	}


	/**
	 * Get one passenger (Admin)
	 * Can get any passenger
	 *
	 * @param request admin request
	 * @return Passenger info
	 */
	@RequestMapping(value = "/admin/psg/{psg_id}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@PathVariable(name = "psg_id") Integer psgId,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = passengerService.getById(psgId, true);
		loggerService.info(logger, "ApiPassengerController.getInfoAdmin psgId=" + psgId
				+ " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}

	/**
	 * Get all passengers of this user
	 * Can get any passenger
	 *
	 * @param id      the id of user
	 * @param name    the name of user. require at least one
	 * @param request admin request
	 * @return List of passenger
	 */
	@RequestMapping(value = "/admin/psgs", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@RequestParam(name = "user_id", required = false) Integer id,
	                                   @RequestParam(name = "username", required = false) String name,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<List<Passenger>> ret;
		if (id != null) {
			ret = passengerService.getAllOf(id, true);
		} else if (name != null) {
			ret = passengerService.getAllOf(name, true);
		} else {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Require at least one param", null);
		}
		loggerService.info(logger, "ApiPassengerController.getInfoAdmin id=" + id
				+ " name=" + name + " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}


	/**
	 * Add one passenger to user
	 *
	 * @param m the passenger info
	 * @return the passenger info after insert
	 */
	@RequestMapping(value = "/psg", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret;
		int userId = auth.getData().getUserId();
		String firstName = m.get("first_name");
		String lastName = m.get("last_name");
		String kind = m.get("kind");
		String gender = m.get("gender");
		String idNo = m.get("id_no");
		if (firstName == null || lastName == null || gender == null || idNo == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
		} else {
			ret = passengerService.add(userId, firstName, lastName, kind, gender, idNo);
		}
		loggerService.info(logger, "ApiPassengerController.add auth=" + auth.getData().getUserName(), ret);
		return ret;
	}


	/**
	 * No delete, but disable passenger
	 *
	 * @param request user request
	 * @return delete(disable) or not
	 */
	@RequestMapping(value = "/psg/{psg_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable(name = "psg_id") int psgId,
	                             HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		User user = auth.getData();
		var ret = passengerService.disable(user.getUserId(), psgId);
		loggerService.info(logger, "ApiPassengerController.disable psgId=" + psgId +
				" auth" + user.getUserName(), ret);
		return ret;
	}


	/**
	 * Choose to true delete or set to be disable one passenger
	 * Need admin authorization
	 *
	 * @param request admin request
	 * @return complete or not
	 */
	@RequestMapping(value = "admin/psg/{psg_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> deleteAdmin(@PathVariable(name = "psg_id") int psgId,
	                                  @RequestParam(name = "true_del", required = false) boolean trueDel,
	                                  HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		Admin user = auth.getData();
		var ret = passengerService.delete(psgId, trueDel);
		loggerService.info(logger, "ApiPassengerController.disableAdmin psgId=" + psgId +
				" trueDel=" + trueDel + " auth" + user.getAdminName(), ret);
		return ret;
	}

	@RequestMapping(value = "/admin/psgs/num", method = RequestMethod.GET)
	public ResponseSet<?> count(HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) { return auth; }

		var ret = passengerService.count();
		loggerService.info(logger, "ApiPassengerController.count", ret);
		return ret;
	}
}
