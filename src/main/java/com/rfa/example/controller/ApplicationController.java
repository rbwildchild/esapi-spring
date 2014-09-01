package com.rfa.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/app", method = RequestMethod.GET)
public class ApplicationController {
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody String greeting() {
		return "Hi there!";
	}
}
