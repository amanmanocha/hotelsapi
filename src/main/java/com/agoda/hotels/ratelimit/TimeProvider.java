package com.agoda.hotels.ratelimit;

public interface TimeProvider {
  public long currentSeconds();
}
