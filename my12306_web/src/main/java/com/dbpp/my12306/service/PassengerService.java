package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Passenger;
import com.dbpp.my12306.mapper.PassengerMapper;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class PassengerService {

	private final PassengerMapper passengerMapper;

	public PassengerService(PassengerMapper passengerMapper) {
		this.passengerMapper = passengerMapper;
	}

	/**
	 * Get the number of passengers
	 *
	 * @return response set: number of users
	 */
	public ResponseSet<Integer> count() {
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.count());
		} catch (Exception e) {
			ret.setData(-1);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
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
		return ret;
	}

	/**
	 * Get all passengers
	 *
	 * @return the list of users and user info
	 */
	public ResponseSet<List<Passenger>> getAll(boolean seeDisable) {
		ResponseSet<List<Passenger>> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.getAll(seeDisable));
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * Get all passengers of one user
	 *
	 * @return the list of passengers and passenger info
	 */
	public ResponseSet<List<Passenger>> getAllOf(int userId, boolean seeDisable) {
		ResponseSet<List<Passenger>> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.getAllOf(userId, seeDisable));
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * Get all passengers of one user
	 *
	 * @return the list of passengers and passenger info
	 */
	public ResponseSet<List<Passenger>> getAllOf(String userName, boolean seeDisable) {
		ResponseSet<List<Passenger>> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(passengerMapper.getAllOfByName(userName, seeDisable));
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}


	/**
	 * Get user by id
	 *
	 * @param id user id
	 * @return the user object. Null if not exits.
	 */
	public ResponseSet<Passenger> getById(int id, boolean seeDisable) {
		ResponseSet<Passenger> ret = new ResponseSet<>();
		try {
			var psg = passengerMapper.getById(id);
			if (psg == null) {
				ret.setStatus(ResultCode.NO_RESULT);
			} else if (seeDisable || "Y".equals(psg.getAvailable())) {
				ret.setData(psg);
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("This passenger is disabled and normal user can't access");
				ret.setStatus(ResultCode.INVALID_AUTH);
			}

		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * Add passenger to the database
	 *
	 * @return the id of passenger.
	 */
	@Transactional
	public ResponseSet<Integer> add(int userId, String firstName, String lastName,
	                                String kind, String gender, String idNo) {
		Passenger psg = new Passenger(userId, firstName, lastName, kind, gender, idNo);
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.CREATED);
			passengerMapper.add(psg);
			psg.setAvailable("Y");
			if (psg.getKind() == null) {
				psg.setKind("A");
			}
			ret.setData(psg.getUserId());
		} catch (Exception e) {
			ret.setDetail(null);
			if (e.getCause().getMessage().contains("duplicate key")) {
				ret.setStatus(ResultCode.SUCCESS_IS_HAVE);
			} else if (e.getCause().getMessage().contains("row contains")) {
				ret.setStatus(ResultCode.CONS_ERROR);
			} else {
				ret.setStatus(ResultCode.EXCEPTION);
			}
			ret.setDetail(e.getCause().getMessage().split("详细：")[1]);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

	/**
	 * Set passenger to be disable
	 *
	 * @return completed or not
	 */
	@Transactional
	public ResponseSet<Integer> disable(int userId, int psgId) {
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			Passenger psg = passengerMapper.getById(psgId);
			if (psg.getUserId() != userId) {
				ret.setDetail("The user doesn't have the privilege " +
						"to access this passenger");
				ret.setStatus(ResultCode.INVALID_AUTH);
			} else {
				int r = passengerMapper.disable(psgId);
				if (r == 1) {
					ret.setDetail("Hide completed.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such passenger.");
					ret.setStatus(ResultCode.FAIL);
				}
				ret.setData(r);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

	/**
	 * Delete passenger from database
	 *
	 * @param id psg id to delete
	 * @return delete or not
	 */
	@Transactional
	public ResponseSet<Integer> delete(int id, boolean trueDel) {
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			if (trueDel) {
				int r = passengerMapper.delete(id);
				if (r == 1) {
					ret.setDetail("Delete completed.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such passenger.");
					ret.setStatus(ResultCode.FAIL);
				}
				ret.setData(r);
			} else {
				int r = passengerMapper.disable(id);
				if (r == 1) {
					ret.setDetail("Hide completed.");
					ret.setStatus(ResultCode.SUCCESS);
				} else {
					ret.setDetail("No such passenger.");
					ret.setStatus(ResultCode.FAIL);
				}
				ret.setData(r);
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}
}
