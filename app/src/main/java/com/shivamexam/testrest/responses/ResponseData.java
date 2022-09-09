package com.shivamexam.testrest.responses;

import com.google.gson.annotations.SerializedName;

public class ResponseData{

	@SerializedName("response")
	private String response;

	public String getResponse() {
		return response;
	}
}