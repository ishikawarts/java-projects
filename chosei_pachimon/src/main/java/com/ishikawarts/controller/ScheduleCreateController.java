package com.ishikawarts.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ScheduleCreateController {

	@PostMapping("/created")
	public String create(Model model) {
		
		String hash = DigestUtils.md5Hex("example1");
		String url = "http://localhsot:8080/event?h=" + hash;
		
		model.addAttribute("hash", hash);
		model.addAttribute("url", url);
		
		return "created";
	}

}
