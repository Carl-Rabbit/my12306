package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.Passenger;
import com.dbpp.my12306.service.PassengerService;
import com.dbpp.my12306.utils.ResponseSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/psg")
public class ApiPassengerController {
	@Autowired
	private PassengerService PassengerService;

	Logger logger = LogManager.getLogger(this.getClass().getName());

	@RequestMapping("/count")
	public ResponseSet<Integer> count() {
		return PassengerService.count();
	}

	@RequestMapping("/count-all-of")
	public ResponseSet<Integer> countAllOf(@RequestParam Integer userId) {
		return PassengerService.countAllOf(userId);
	}

	@RequestMapping("/get-by-id")
	public ResponseSet<Passenger> getById(@RequestParam int id) {
		return PassengerService.getById(id);
	}

	@RequestMapping("/get-all")
	public ResponseSet<List<Passenger>> getAll() {
		return PassengerService.getAll();
	}

	@RequestMapping("/get-all-of")
	public ResponseSet<List<Passenger>> getAllOf(@RequestParam Integer userId) {
		return PassengerService.getAllOf(userId);
	}

	@RequestMapping("/add")
	public ResponseSet<Integer>
	add(@RequestParam Integer userId,
	    @RequestParam String firstName,
	    @RequestParam String lastName,
	    @RequestParam(defaultValue = "A") String kind,
	    @RequestParam String gender,
	    @RequestParam String idNo) {
		return PassengerService.add(userId, firstName, lastName,
				kind, gender, idNo);
	}

	@RequestMapping("/hide")
	public ResponseSet<Integer> hide(@RequestParam int id) {
		return PassengerService.hide(id);
	}

	@RequestMapping("/delete")
	public ResponseSet<Integer> delete(@RequestParam Integer id) {
		return PassengerService.delete(id);
	}
}
