package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.*;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.BusinessService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.PassengerService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class ApiBusinessController {
	private final AuthService authService;
	private final BusinessService businessService;
	private final PassengerService passengerService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	String pattern = "^\\d{4}-\\d{2}-\\d{2}$";

	public ApiBusinessController(AuthService authService, BusinessService businessService,
	                             PassengerService passengerService, LoggerService loggerService) {
		this.authService = authService;
		this.businessService = businessService;
		this.passengerService = passengerService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/routes/{from}/{to}/{date}", method = RequestMethod.GET)
	public ResponseSet<?> listRoutes(@PathVariable(name = "from") String fromStr,
	                                 @PathVariable(name = "to") String toStr,
	                                 @PathVariable(name = "date") String date) {

		var ret = new ResponseSet<>();
		try {
			while (true) {
				// check date
				if (!Pattern.matches(pattern, date)) {
					ret.setDetail("Param date must in format YYYY-MM-DD.");
					ret.setStatus(ResultCode.PARAMS_ERROR);
					break;
				}

				List<RouteInfo> data = businessService.listRoutes(fromStr, toStr, date);
				ret.setDetail(data.size() + " route(s) fetched.");
				ret.setData(data);
				ret.setStatus(ResultCode.SUCCESS);
				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiBusinessController.listRoutes trainCode=" + fromStr
				+ " toStr=" + toStr + " date=" + date, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	@RequestMapping(value = {"/routes/detail/{train_code}", "/time_details/{train_code}"},
			method = RequestMethod.GET)
	public ResponseSet<?> listRouteDetails(@PathVariable(name = "train_code") String trainCode) {

		var ret = new ResponseSet<>();
		try {
			List<RouteDetailInfo> data = businessService.listRouteDetails(trainCode);
			ret.setDetail(data.size() + " detail(s) fetched.");
			ret.setData(data);
			ret.setStatus(ResultCode.SUCCESS);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getCause().getMessage());
		}
		loggerService.info(logger, "ApiBusinessController.listRoutes trainCode="
						+ trainCode, ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	@RequestMapping(value = {"/routes", "/tickets"}, method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public ResponseSet<?> buyTickets(@RequestBody List<BuyQuery> list,
	                                 HttpServletRequest request) {
		var auth = authService.checkUser(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		var ret = new ResponseSet<>();

		Integer userId = auth.getData().getUserId();

		try {
			while (true) {
				if (list.size() == 0) {
					ret.setDetail("Required info of tickets to buy.");
					ret.setStatus(ResultCode.PARAMS_ERROR);
					break;
				}

				ParamSet ps = new ParamSet(list);

				if (!ps.isGood()) {
					ret.setDetail(ps.msg);
					ret.setStatus(ResultCode.PARAMS_ERROR);
					break;
				}

				if (!ps.checkPsgBelong(userId)) {
					ret.setDetail(ps.msg);
					ret.setStatus(ResultCode.PARAMS_ERROR);
					break;
				}

				Object[] data = businessService.buyTickets(userId,
						ps.psgIdArr,
						ps.trainCodeArr,
						ps.fromIndexArr,
						ps.toIndexArr,
						ps.departDateArr,
						ps.seatClassArr,
						ps.seatTypeArr,
						ps.preferArr);
				var order = (Order) data[0];
				var ids = (List<Integer>) data[1];
				var tickets = (List<Ticket>) data[2];
				ret.setDetail(tickets.size() + " success. " +
						(ids.size() - tickets.size()) + " failed.");
				ret.setData(new HashMap<String, Object>() {{
					this.put("order", order);
					this.put("ticket_id", ids);
					this.put("tickets", tickets);
				}});
				ret.setStatus(ResultCode.SUCCESS);
				break;
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			if (e.getCause() != null) {
				ret.setDetail(e.getCause().getMessage());
			} else {
				ret.setDetail(e.getMessage());
			}
		}
		loggerService.info(logger, "ApiBusinessController.buyTickets list=" + list
				+ " auth=" + auth.getData().getUserName(), ret.getStatus() + " " + ret.getDetail());
		return ret;
	}

	private class ParamSet {
		int len;
		Integer[] psgIdArr;
		String[] trainCodeArr;
		Integer[] fromIndexArr;
		Integer[] toIndexArr;
		String[] departDateArr;
		String[] seatClassArr;
		String[] seatTypeArr;
		String[] preferArr;
		String msg = "";

		ParamSet(List<BuyQuery> list) {
			len = list.size();
			psgIdArr = new Integer[len];
			trainCodeArr = new String[len];
			fromIndexArr = new Integer[len];
			toIndexArr = new Integer[len];
			departDateArr = new String[len];
			seatClassArr = new String[len];
			seatTypeArr = new String[len];
			preferArr = new String[len];

			int i = 0;
			for (BuyQuery q : list) {
				psgIdArr[i] = q.getPsgId();
				trainCodeArr[i] = q.getTrainCode();
				fromIndexArr[i] = q.getFromIndex();
				toIndexArr[i] = q.getToIndex();
				departDateArr[i] = q.getDate();
				seatClassArr[i] = q.getSeatCls();
				seatTypeArr[i] = q.getSeatType();
				preferArr[i] = q.getPrefer();
				++i;
			}
		}

		boolean isGood() {
			// check date
			for (String s : departDateArr) {
				if (!Pattern.matches(pattern, s)) {
					msg = "Param date must in format YYYY-MM-DD.";
					return false;
				}
			}

			// check cls
			for (String s : seatClassArr) {
				if (!"A".equals(s) && !"B".equals(s) && !"C".equals(s)) {
					msg = "Param seatCls must be A or B or C.";
					return false;
				}
			}

			// check type
			for (String s : seatTypeArr) {
				if (!"W".equals(s) && !"Z".equals(s)) {
					msg = "Param seatCls must be W or Z.";
					return false;
				}
			}

			// check index
			for (int i = 0; i < len; i++) {
				if (fromIndexArr[i] >= toIndexArr[i]) {
					msg = "From index must be smaller than to index.";
					return false;
				}
			}
			return true;
		}

		boolean checkPsgBelong(Integer userId) {
			for (Integer id : psgIdArr) {
				Passenger psg = passengerService.getById(id, true).getData();
				if (psg == null) {
					msg = "No such passenger where psg_id=" + id;
					return false;
				}
				if (psg.getUserId() != userId) {
					msg = "The user has no privilege to visit this passenger.";
					return false;
				}
				if (!"Y".equals(psg.getAvailable())) {
					msg = "This passenger is not valid.";
					return false;
				}
			}
			return true;
		}
	}
}
