package org.springframework.samples.petclinic.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/test-python")
	public String testPython() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://django-test.python.svc:8000/",
				String.class);
		return response.toString();
	}

}
