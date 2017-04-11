![alt text](http://cdn0.agoda.net/images/MVC/default/logo-agoda-mobile@2X.png "agoda")

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





All material herein © 2005 – 2014 Agoda Company Pte. Ltd., All Rights Reserved.<br />
AGODA ® is a registered trademark of AGIP LLC, used under license by Agoda Company Pte. Ltd.<br />
Agoda is part ofPriceline (NASDAQ:PCLN)<br />


Solution notes
==============

I have used spring-data-rest to get to the API ready with minimum code required. It gives hateos for free. Following are the end-points

  1. All hotels
      /hotels
  2. Search hotels by CityId
      /hotels/search/findByCity{?city}
  2. Provide optional sorting of the result by Price (both ASC and DESC order).
      /hotels/search/findByCity{?city,sort}

All end-points require x-api-key to be presenent as header. 
