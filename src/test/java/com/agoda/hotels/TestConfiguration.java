package com.agoda.hotels;

import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.agoda.hotels.ratelimit.TimeProvider;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {
  @Bean
  @Primary
  public TimeProvider mockTimeProvider() {
    return mock(TimeProvider.class);
  }

  @Bean
  @Primary
  public Properties myProps() {
    Properties mockProperties = new Properties();
    mockProperties.setProperty("rate.limit", "10");
    return mockProperties;
  }
}
