package com.agoda.hotels.ratelimit;

import static java.lang.System.currentTimeMillis;

import org.springframework.stereotype.Service;
@Service
public class SystemTimeProvider implements TimeProvider {

  private static final int MILLIS_IN_SECOND = 1000;

  @Override
  public long currentSeconds() {
    return currentTimeMillis() / MILLIS_IN_SECOND;
  }

}
