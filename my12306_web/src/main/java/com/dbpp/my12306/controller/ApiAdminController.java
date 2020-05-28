package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Admin;
import com.dbpp.my12306.service.AdminService;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiAdminController {
	private final AuthService authService;
	private final AdminService adminService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiAdminController(AuthService authService, AdminService adminService,
	                          LoggerService loggerService) {
		this.authService = authService;
		this.adminService = adminService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@RequestParam(name = "id", required = false) Integer adminId,
	                                   @RequestParam(name = "name", required = false) String adminName,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<Admin>();
		try {
			while (true) {
				if (adminId == null && adminName == null) {
					ret.setData(auth.getData());
					ret.setStatus(ResultCode.SUCCESS);
					break;
				}
				Admin data = adminService.getSelective(adminId, adminName);
				if (data == null) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such admin", null);
				} else {
					ret.setData(data);
					ret.setStatus(ResultCode.SUCCESS);
				}
				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiAdminController.getInfoAdmin adminId=" + adminId
						+ " adminName=" + adminName + " auth=" + auth.getData().getAdminName(),
				ret.getStatus() + " " + ret.getDetail());
		return ret;
	}


	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		Admin admin = new Admin();
		ResponseSet<Integer> ret;
		String adminName = m.get("admin_name");
		String password = m.get("password");
		String kind = m.get("kind");
		if (adminName == null || kind == null || password == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
		} else {
			ret = new ResponseSet<>();

			admin.setAdminName(adminName);
			admin.setPassword(password);
			admin.setKind(kind);
			try {
				adminService.add(admin);
				ret.setStatus(ResultCode.CREATED);
				ret.setData(admin.getAdminId());
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
		loggerService.info(logger, "ApiAdminController.add auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret);
		return ret;
	}


	@RequestMapping(value = "/admin/{admin_id}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("admin_id") Integer adminId,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = adminService.delete(adminId);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such admin.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiAdminController.delete adminId=" + adminId +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}
}
