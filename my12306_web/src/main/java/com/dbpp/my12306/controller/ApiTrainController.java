package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Train;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.TrainService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiTrainController {
	private final AuthService authService;
	private final TrainService trainService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiTrainController(AuthService authService, TrainService trainService, LoggerService loggerService) {
		this.authService = authService;
		this.trainService = trainService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/admin/train/{train_no}", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@PathVariable(name = "train_no") String trainNo,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<Train>();
		try {
			if (trainNo.length() != 12) {
				ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param train_code's length must be 12.", null);
			} else {
				Train data = trainService.get(trainNo);
				if (data == null) {
					ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such train", null);
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
		loggerService.info(logger, "ApiTrainController.getInfoAdmin trainNo=" + trainNo
				+ " auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}


	@RequestMapping(value = "/admin/train", method = RequestMethod.POST)
	public ResponseSet<?> add(@RequestBody Map<String, String> m,
	                          HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		Train train = new Train();
		ResponseSet<String> ret;
		String trainNo = m.get("train_no");
		String trainKind = m.get("train_kind");
		if (trainNo == null || trainKind == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Missing some required param.", null);
		} else if (trainNo.length() != 12) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param train_code's length must be 12.", null);
		} else if (trainKind.length() != 1 || !trainKind.matches("[CDGPSYKTZ]|\\s")) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Param train_kind must be in [CDGPSYKTZ] " +
					"or one empty char ' '.", null);
		} else{
			ret = new ResponseSet<>();

			train.setTrainNo(trainNo);
			train.setTrainKind(trainKind);
			try {
				trainService.add(train);
				ret.setStatus(ResultCode.CREATED);
				ret.setData(train.getTrainNo());
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
		loggerService.info(logger, "ApiTrainController.add auth=" + auth.getData().getAdminName()
				+ " m=" + m, ret);
		return ret;
	}


	@RequestMapping(value = "/admin/train/{train_no}", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@PathVariable("train_no") String trainNo,
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
				int r = trainService.delete(trainNo);
				if (r == 1) {
					ret.setDetail("Delete completed.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such train.");
					ret.setStatus(ResultCode.FAIL);
				}
				ret.setData(r);
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		loggerService.info(logger, "ApiTrainController.delete trainNo=" + trainNo +
				" auth=" + auth.getData().getAdminName(), ret);
		return ret;
	}
}
