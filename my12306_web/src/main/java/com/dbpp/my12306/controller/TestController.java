package com.dbpp.my12306.controller;

import com.dbpp.my12306.utils.ResponseSet;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("!prod")
@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping(value = {"/test1", "/s/test1"})
	public ResponseSet<String> test1(@RequestBody Map<String, String> name) {
		return new ResponseSet<>(name.get("name"));
	}

	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public ResponseSet<String> test2() {
		return new ResponseSet<>("get");
	}

	@RequestMapping(value = "/test2", method = RequestMethod.POST)
	public ResponseSet<String> test2(@RequestBody Map<String, String> m) {
		return new ResponseSet<>("post: " + m.get("name"));
	}

	@RequestMapping(value = "/test3")
	public ResponseSet<String> test3(@RequestParam(required = false) int id) {
		return new ResponseSet<>("get: " + id);
	}

//	@RequestMapping(value = "/test3")
//	public ResponseSet<String> test3(@RequestParam String name) {
//		return new ResponseSet<>("get: " + name);
//	}
}
