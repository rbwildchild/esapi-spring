package com.rfa.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rfa.example.model.LoginRequest;
import com.rfa.example.model.RequestResponse;
import com.rfa.example.model.User;

@Controller
@RequestMapping(value = "/login", method = RequestMethod.GET)
public class LoginController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register() {
		return new ModelAndView("register");
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public @ResponseBody
	RequestResponse authenticate(@RequestBody LoginRequest loginRequest) {
		if (loginRequest.getUser().equals("admin") && loginRequest.getPassword().equals("password")) {
			return new RequestResponse(true, "Login successful");
		} else
			return new RequestResponse(false, "Access denied");
	}

	@RequestMapping(value = "/register_user", method = RequestMethod.POST)
	public @ResponseBody
	RequestResponse registerUser(@RequestBody User user) {
		if (user.getFirstName() != null && user.getFirstName().length() > 0) {
			return new RequestResponse(true, "Register successful");
		} else
			return new RequestResponse(false, "Access denied");
	}

}
