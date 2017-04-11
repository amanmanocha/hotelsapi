package com.agoda.hotels.ratelimit;
class RateLimitChecker {
  private final int limit;
  private final TimeProvider timeProvider;
  
  private long lastCheckedSeconds = 0;
  private int hitsForLastCheckedSeconds = 0;

  public RateLimitChecker(TimeProvider timeProvider, int maxLimit) {
    this.timeProvider = timeProvider;
    if(maxLimit == 0) 
      maxLimit = Integer.MAX_VALUE;
    this.limit = maxLimit;
  }

  public synchronized boolean checkWithinRateLimits() {
    final long currentSeconds = timeProvider.currentSeconds();
    if (lastCheckedSeconds == currentSeconds) {
      hitsForLastCheckedSeconds++;
    } else {
      lastCheckedSeconds = currentSeconds;
      hitsForLastCheckedSeconds = 1;
    }
    return hitsForLastCheckedSeconds <= limit;
  }
}
