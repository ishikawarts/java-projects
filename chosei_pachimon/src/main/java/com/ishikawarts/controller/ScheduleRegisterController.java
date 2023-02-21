package com.ishikawarts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ishikawarts.foundation.exception.SystemException;
import com.ishikawarts.service.ExampleService;
import com.ishikawarts.service.dto.ExampleParameter;

@Controller
public class ScheduleRegisterController {

	private final ExampleService exampleService;

	@Autowired
	public ScheduleRegisterController(ExampleService exampleService) {
		this.exampleService = exampleService;
	}

	@GetMapping("/event")
	public String init(Model model, @RequestParam(name = "h") String hash) {

		var param = ExampleParameter.builder()
				.username("system")
				.eventId(hash)
				.build();
			try {
				
				var result = this.exampleService.execute(param);
				
				if(result.isSuccess()) {
					model.addAttribute("event", result.event());
				}
				
			} catch (SystemException e) {
				// エラーページへ遷移とかさせる
			}
		
		

		model.addAttribute("hash", hash);
		return "event";
	}

	@PostMapping("/event")
	public String register(Model model, @RequestParam(name = "h") String hash) {
		model.addAttribute("hash", hash);
		return "event";
	}
}
