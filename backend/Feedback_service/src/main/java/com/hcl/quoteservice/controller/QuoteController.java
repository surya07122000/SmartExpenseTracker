package com.hcl.quoteservice.controller;

import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quote")
@CrossOrigin(origins = "*")
public class QuoteController {

	private static final List<String> FEEDBACKS = List.of("\"This app changed the way I manage my money!\" - HariHaran",
			"\"Simple, effective, and reliable.\" - Benin");

	@GetMapping
	public List<String> getFeedbacks() {
        return FEEDBACKS;
	}
    

}
