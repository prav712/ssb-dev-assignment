package com.ssb.dev;

import com.ssb.dev.controller.StationUpdateProviderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SsbDevAssignmentApplicationTests {

	@Autowired
	private StationUpdateProviderController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
