package com.agoda.hotels.ratelimit;
class RateLimitChecker {
  private final int maxLimit;
  
  private long currentSecond = 0;
  private int countForCurrentSecond = 0;

  public RateLimitChecker(final int maxLimit) {
    this.maxLimit = maxLimit;
  }

  public synchronized boolean checkWithinRateLimits() {
    final long now = System.currentTimeMillis() / 1000;
    if (currentSecond == now) {
      countForCurrentSecond++;
    } else {
      currentSecond = now;
      countForCurrentSecond = 1;
    }
    return countForCurrentSecond <= maxLimit;
  }
}
