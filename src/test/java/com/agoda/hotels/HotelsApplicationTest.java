package com.agoda.hotels;

import static org.approvaltests.Approvals.verify;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.agoda.hotels.ratelimit.TimeProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelsApplication.class)
@WebAppConfiguration
@ContextConfiguration(classes=TestConfiguration.class)
public class HotelsApplicationTest {
  private static final String TEST_API_KEY = "1";
  private static final String ANOTHER_KEY = "2";

  private final MediaType EXPECTED_CONTENT_TYPE = new MediaType("application", "hal+json", Charset.forName("UTF-8"));
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mockMvc;

  @Autowired
  private TimeProvider mockTimeProvider;
  @Autowired
  private Properties mockProperties;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }
  //@formatter:off
  @Test public void 
  hotels_get_end_point_should_return_all_hotels() throws Exception {
    MvcResult result = mockMvc.perform(get("/hotels")
                              .header("x-api-key", TEST_API_KEY))
                              .andExpect(status().isOk())
                              .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
                              .andReturn();
    verify(result.getResponse().getContentAsString());
  }

  @Test public void 
  hotels_get_with_sort_by_price_should_return_all_hotels_sorted_by_price() throws Exception {
    MvcResult result = mockMvc.perform(get("/hotels?sort=price")
                              .header("x-api-key", TEST_API_KEY))
                              .andExpect(status().isOk())
                              .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
                              .andReturn();
    verify(result.getResponse().getContentAsString());
  }

  @Test public void 
  hotels_get_with_sort_by_price_desc_should_return_all_hotels_sorted_by_price_desc() throws Exception {
    MvcResult result = mockMvc.perform(get("/hotels?sort=price,desc")
                              .header("x-api-key", TEST_API_KEY))
                              .andExpect(status().isOk())
                              .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
                              .andReturn();
    verify(result.getResponse().getContentAsString());
  }

  @Test public void 
  hotels_get_with_search_by_city_should_return_hotels_for_city_only() throws Exception {
    MvcResult result = mockMvc.perform(get("/hotels/search/findByCity?city=Bangkok")
                              .header("x-api-key", TEST_API_KEY))
                              .andExpect(status().isOk())
                              .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
                              .andReturn();
    verify(result.getResponse().getContentAsString());
  }

  @Test
  public void hotels_get_without_api_key_returns_forbidden() throws Exception {
    mockMvc.perform(get("/hotels")).andExpect(status().isForbidden());
  }

  @Test public void 
  more_than_rate_limit_calls_are_rejected() throws Exception {
    mockProperties.setProperty("rate.limit." + ANOTHER_KEY, "1");

    doReturn(1L).when(mockTimeProvider).currentSeconds();

    mockMvc.perform(get("/hotels")
           .header("x-api-key", ANOTHER_KEY))
           .andExpect(status().isOk());
    mockMvc.perform(get("/hotels")
           .header("x-api-key", ANOTHER_KEY))
           .andExpect(status().isTooManyRequests());
  }
  //@formatter:on

}
