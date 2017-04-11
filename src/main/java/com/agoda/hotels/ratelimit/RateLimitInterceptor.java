package com.agoda.hotels.ratelimit;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RateLimitInterceptor extends HandlerInterceptorAdapter {
  private static final String DEFAULT_RATE_LIMIT = "10";
  private static final String X_API_KEY = "X-API-KEY";

  private final ConcurrentMap<String, RateLimitChecker> map = new ConcurrentHashMap<>();
  private final int commonRateLimit;
  private final Environment env;

  @Autowired
  private RateLimitInterceptor(Environment env) {
    super();
    this.env = env;
    
    int commonRateLimit = new Integer(env.getProperty("rate.limit", DEFAULT_RATE_LIMIT));
    if (commonRateLimit == 0) {
      this.commonRateLimit = Integer.MAX_VALUE;
    } else if (commonRateLimit < 0) {
      throw new IllegalArgumentException("rate.limit" + " can not be set to negative value");
    } else {
      this.commonRateLimit = commonRateLimit;
    }
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String apiKey = request.getHeader(X_API_KEY);
    if (apiKey == null || apiKey.isEmpty()) {
      response.sendError(HttpStatus.FORBIDDEN.value());
      return false;
    }

    return rateLimit(response, apiKey);
  }

  private boolean rateLimit(HttpServletResponse response, String apiKey) throws IOException {
    int limitForKey = new Integer(env.getProperty("rate.limit." + apiKey, "" + commonRateLimit));

    map.putIfAbsent(apiKey, new RateLimitChecker(limitForKey));
    boolean isAllowed = map.get(apiKey).checkWithinRateLimits();

    if (!isAllowed)
      response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());

    return isAllowed;
  }
}