Hotel API Programming
===================
In this exam, you are provided with hotels database in CSV (Comma Separated Values) format. 

We need you implement HTTP service, according to the API requirements described below.
You may use any language or platform that you like: C#/Java/Scala/etc.

Good luck!


API
======
  1. Search hotels by CityId
  2. Provide optional sorting of the result by Price (both ASC and DESC order).



Solution notes
==============

I have used spring-data-rest to get to the API ready with minimum code required. It gives hateos for free. Following are the end-points

  1. All hotels
      
          /hotels
  
  2. Search hotels by CityId
      
          /hotels/search/findByCity{?city}
      
          e.g. - /hotels/search/findByCity?city=Bangkok
  3. Provide optional sorting of the result by Price (both ASC and DESC order).
      
          /hotels/search/findByCity{?city,sort}
  
          e.g. - /hotels/search/findByCity?city=Bangkok&sort=price
            
          /hotels/search/findByCity?city=Bangkok&sort=price,desc
     

All end-points require x-api-key to be presenent as header. 


The API should be rate limited (x requests per 10 seconds) based on API key provided in each call.
API key can have different rate limits set, in this case from a configuration file, and if not present use a global default.

The per second rate limit can be configured by setting 'rate.limit' in appliction.properties file, which should be present in the classpath.
Per second rate limit a for specific keys can be configured by setting 'rate.limit.{key}' in same application.properties file.

Pending improvements

1. Frequency of rate limit should be made configurable.
2. Swagger for describing end points.
