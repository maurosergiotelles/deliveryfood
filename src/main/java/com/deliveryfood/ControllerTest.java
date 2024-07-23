package com.deliveryfood;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/base2")
public class ControllerTest {

	@GetMapping("/test")
	@ResponseBody
	public String getTeste() {
		return "teste.....>>>>.......";
		
		
		
	}
	

	


}
