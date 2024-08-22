package com.deliveryfood.client.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Problem {

	private Integer status;
	private String type;
	private String detail;
	private String title;
	private List<Object> objects = new ArrayList<>();

	@Data
	public static class Object {

		private String name;
		private String userMessage;

	}

}
