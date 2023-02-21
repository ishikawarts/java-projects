package com.ishikawarts.foundation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected static String forward(String url) {

		if (url.startsWith("/")) {
			return "forward:".concat(url);
		}

		return "forward:/".concat(url);
	}

	protected static String redirect(String url) {

		if (url.startsWith("/")) {
			return "redirect:".concat(url);
		}

		return "redirect:/".concat(url);
	}
}
