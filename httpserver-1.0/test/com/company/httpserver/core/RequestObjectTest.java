package com.company.httpserver.core;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.datatransfer.StringSelection;

import org.junit.jupiter.api.Test;

class RequestObjectTest {

	@Test
	void testGetParameterValue() {
		RequestObject request = new RequestObject("/oa/user/save?username=admin&gender=1&interest=food");
		System.out.println(request.getParameterValue("interest"));
	}

	@Test
	void testGetParameterValues() {
		RequestObject request = new RequestObject("/oa/user/save?username=admin&gender=1&interest=food&interest=sleep");
		String [] interests = request.getParameterValues("interest");
		for (String interest : interests) {
			System.out.println(interest);
		}
	}

}
