package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Passenger;
import com.dbpp.my12306.mapper.LoggerMapper;
import com.dbpp.my12306.mapper.PassengerMapper;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerService {

	@Autowired
	private PassengerMapper passengerMapper;
	@Autowired
	private LoggerMapper loggerMapper;

	Logger log = LogManager.getLogger(this.getClass().getName());

	/**
	 * Get the number of passengers
	 *
	 * @return response set: number of users
	 */
	public ResponseSet<Integer> count() {
		String event = "Passenger count.";
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.count());
		} catch (Exception e) {
			ret.setData(-1);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

	/**
	 * Get the number of passengers
	 *
	 * @return response set: number of users
	 */
	public ResponseSet<Integer> countAllOf(int userId) {
		String event = "Passenger count for userId=" + userId + ".";
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.countAllOf(userId));
		} catch (Exception e) {
			ret.setData(-1);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

	/**
	 * Get all passengers
	 *
	 * @return the list of users and user info
	 */
	public ResponseSet<List<Passenger>> getAll() {
		String event = "Get all passengers.";
		ResponseSet<List<Passenger>> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.getAll());
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

	/**
	 * Get all passengers of one user
	 *
	 * @return the list of passengers and passenger info
	 */
	public ResponseSet<List<Passenger>> getAllOf(int userId) {
		String event = "Get all passengers of user, userId=" + userId + ".";
		ResponseSet<List<Passenger>> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.getAllOf(userId));
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}


	/**
	 * Get user by id
	 *
	 * @param id user id
	 * @return the user object. Null if not exits.
	 */
	public ResponseSet<Passenger> getById(int id) {
		String event = "Get passenger by id=" + id + ".";
		ResponseSet<Passenger> ret = new ResponseSet<>();
		try {
			ret.setData(passengerMapper.getById(id));
			ret.setStatus(ret.getData() != null ?
					ResultCode.SUCCESS : ResultCode.NO_RESULT);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

	/**
	 * Add passenger to the database
	 *
	 * @return the id of user.
	 */
	@Transactional
	public ResponseSet<Integer> add(int userId, String firstName, String lastName,
	                                String kind, String gender, String idNo) {
		Passenger psg = new Passenger(userId, firstName, lastName, kind, gender, idNo);
		String event = "Add passenger: " + psg + ".";
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.add(psg));
		} catch (Exception e) {
			ret.setDetail(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

	/**
	 * Hide passenger from database
	 *
	 * @param id   psg id to hide
	 * @return delete or not
	 */
	@Transactional
	public ResponseSet<Integer> hide(int id) {
		String event = "Delete passenger: id=" + id + ".";
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = passengerMapper.delete(id);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such passenger.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

	/**
	 * Delete passenger from database
	 *
	 * @param id   psg id to delete
	 * @return delete or not
	 */
	@Transactional
	public ResponseSet<Integer> delete(int id) {
		String event = "Delete passenger: id=" + id + ".";
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = passengerMapper.delete(id);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such passenger.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		log.info(event + " " + ret);
		loggerMapper.add(event, ret.toString());
		return ret;
	}

//	/**
//	 * Update the user.
//	 * If the user not exits, change nothing
//	 * @param user update this user
//	 * @return the pk of user
//	 */
//	long update(User user);
//
//	/**
//	 * Check exist
//	 * @param id user id to check
//	 * @return exist or not
//	 */
//	boolean exist(int id);
}
