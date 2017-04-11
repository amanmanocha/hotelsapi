package com.agoda.hotels.ratelimit;
class RateLimitChecker {
  private final int maxLimit;
  private final TimeProvider timeProvider;
  
  private long currentSecond = 0;
  private int countForCurrentSecond = 0;

  public RateLimitChecker(TimeProvider timeProvider, int maxLimit) {
    this.timeProvider = timeProvider;
    if(maxLimit == 0) 
      maxLimit = Integer.MAX_VALUE;
    this.maxLimit = maxLimit;
  }

  public synchronized boolean checkWithinRateLimits() {
    final long now = timeProvider.currentSeconds();
    if (currentSecond == now) {
      countForCurrentSecond++;
    } else {
      currentSecond = now;
      countForCurrentSecond = 1;
    }
    return countForCurrentSecond <= maxLimit;
  }
}
