package com.agoda.hotels.ratelimit;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class RateLimitInterceptor extends HandlerInterceptorAdapter {
  private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

  private static final String DEFAULT_RATE_LIMIT = "10";
  private static final String X_API_KEY = "X-API-KEY";

  private final ConcurrentMap<String, RateLimitChecker> map = new ConcurrentHashMap<>();
  private final int commonRateLimit;
  private final Properties env;
  private final TimeProvider timeProvider;

  @Autowired
  public RateLimitInterceptor(Properties env, TimeProvider timeProvider) {
    this.env = env;
    this.timeProvider = timeProvider;
    
    int commonRateLimit = new Integer(env.getProperty("rate.limit", DEFAULT_RATE_LIMIT));
    if (commonRateLimit < 0) {
      logger.warn("rate.limit is set to negative value. No limit will be applied.");
      this.commonRateLimit = 0;
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
    int limitForKey = limitFor(apiKey);

    map.putIfAbsent(apiKey, new RateLimitChecker(timeProvider, limitForKey));
    boolean isWithinRateLimits = map.get(apiKey).checkWithinRateLimits();

    if (!isWithinRateLimits)
      response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());

    return isWithinRateLimits;
  }

  protected int limitFor(String apiKey) {
    try {
      Integer limit = new Integer(env.getProperty("rate.limit." + apiKey, "" + commonRateLimit));
      
      if (limit < 0) {
        logger.warn("rate.limit is set to negative value for apiKey $1. No limit will be applied.", apiKey);
        return 0;
      }
      return limit;
    } catch(Exception e) {
      return commonRateLimit;
    }
  }
}