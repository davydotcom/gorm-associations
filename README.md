# GORM Association Issue with Traversal

It appears that if a base object is queries and traversed to 2 seperate objects that both have an association with another object. the 2nd path of traversal results in a NULL object compared to the original path..


This is hard to explain but the tests proves the issue. `NestedAssociationQuerySpec` has 2 tests. one that verifies the path of traversal works and one that proves it stops working if another route is queried first within the same hibernate session..

It appears this is not reproducible using the h2 in memory database

First create a mysql schema of `test_associations`


* GrailsVersion: `3.2.8`
* GORM Version: `6.1.0.RELEASE`, `6.1.0.RC2`, `6.1.0.RC1`, `6.1.0.M2`, `6.1.0.M1`


To run tests simply run `grails test-app --integration`

**NOTE:** This test passes when using GORM `6.0.9.RELEASE` and can be verified by changing `gormVersion` in `gradle.properties`.