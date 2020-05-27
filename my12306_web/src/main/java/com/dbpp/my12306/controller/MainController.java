package com.dbpp.my12306.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:index.html";
	}

	@RequestMapping(value = "/api/help")
	public String help() {
		return "api_help";
	}
}
