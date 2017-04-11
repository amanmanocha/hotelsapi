package com.agoda;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.agoda.hotels.HotelsApplication;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelsApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class HotelsApplicationTests {
  private MediaType contentType = new MediaType("application", "hal+json", Charset.forName("UTF-8"));

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
              .build();
  }

  @Test
  public void validate_get_address() throws Exception {

      mockMvc.perform(get("/hotels").header("x-api-key", "1"))
              .andExpect(status().isOk())
              .andExpect(
                      content().contentType(contentType))
              .andExpect(jsonPath("$.street").value("12345 Horton Ave"));

  }
}
