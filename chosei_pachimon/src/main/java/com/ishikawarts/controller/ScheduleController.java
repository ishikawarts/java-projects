package com.ishikawarts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {
	
	@GetMapping("/")
	public String init() {
		return "top";
	}
	
}
