package com.deliveryfood;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ControllerTest {

	@GetMapping("/test")
	@ResponseBody
	public String getTeste() {
		return "teste.....>>>>.......";
		
		
		
	}
	

	


}
