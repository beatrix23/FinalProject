package ro.ubb.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	private Random random = new Random();

	private int previous = 0;

	@RequestMapping("/test")
	public String test() {
		int i = random.nextInt(3);
		while (i == previous) {
			i = random.nextInt(3);
		}
		previous = i;
		return "test" + i;
	}

}
