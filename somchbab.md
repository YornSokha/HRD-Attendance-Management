# **SomChbab**

## `1. Controller` 
##### **1.1 In package java**
+ In case you implement on feature that has not created a controller yet, you have to create one eg. _**UserController**_  in package `controller`.
##### **1.2 In package resources**
+ All view must be in package `templates` and cover by folder named of each feature eg. academic, classroom... 
+ For custom script you have to create js file in `static/script/app` folder. eg. _custom-datepicker.js_
## `3. Repository`
+ In `repository` package, you have to add `?_repository` package and create an `interface` in it to implement code to fetch date. 
In case you have complex query, you have to create a class in package `provider`.



#### To format date from database
+ System.out.println(DateFormatter.format(dob, FormatDateType.YMD));
In case you have complex query, you have to create a class in package `provider`.1