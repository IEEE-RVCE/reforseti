package org.ieeervce.api.siterearnouveau;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SiteRearNouveauApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	SiteRearNouveauApplication siteRearNouveauApplication;

	@Test
	void contextLoads() {
		assertThat(siteRearNouveauApplication).isNotNull();
	}
	@Test
	void testRootApiWorks() throws Exception{
		mockMvc.perform(get("/"))
			.andExpect(content().string(containsString(SiteRearNouveauApplication.IEEE_RVCE)));
	}

}
