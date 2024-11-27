package com.example.ipFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class IpFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpFilterApplication.class, args);
	}

	@GetMapping("/index")
	public String index(){
		return "redirect:/";
	}
}
