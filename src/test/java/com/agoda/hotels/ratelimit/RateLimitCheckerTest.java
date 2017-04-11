package com.agoda.hotels.ratelimit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class RateLimitCheckerTest {
  @Mock TimeProvider timeProvider;

  @Test public void 
  returns_false_when_rate_exceeds() throws Exception {
    RateLimitChecker rateLimitChecker = new RateLimitChecker(timeProvider, 1);
    
    doReturn(1L).when(timeProvider).currentSeconds();
    
    assertThat(rateLimitChecker.checkWithinRateLimits()).isTrue();
    assertThat(rateLimitChecker.checkWithinRateLimits()).isFalse();
  }
  
  @Test public void 
  resets_when_time_moves() throws Exception {
    RateLimitChecker rateLimitChecker = new RateLimitChecker(timeProvider, 1);
    doReturn(1L).when(timeProvider).currentSeconds();
    
    assertThat(rateLimitChecker.checkWithinRateLimits()).isTrue();
    assertThat(rateLimitChecker.checkWithinRateLimits()).isFalse();
    
    doReturn(2L).when(timeProvider).currentSeconds();

    assertThat(rateLimitChecker.checkWithinRateLimits()).isTrue();
  }
}
