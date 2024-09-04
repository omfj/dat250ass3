# DAT250 Assignment 2

Author: Ole Magnus Fon Johnsen

## What?

Poll application using Spring Boot. The application is a REST API that allows users to create, read, update and delete polls. Users can then choose from one of the earlier defined options in the poll, and vote on it. For storage, it uses a KV database that stores the polls and the users locally in the root of the repository.

## Challenges?

Didn't face too many challenges. I was struggling a bit with how to run unit tests without mocking the database, but I found out that it doesn't run the tests in parallel, so I could just add a `@AfterEach` to clear/reset the database after each test. This made it so that all the tests are pretty 1:1 with the actual implementation.

## Pending issues?

I would like to also add a better authentication system, but I held back on that part since I felt like it was a bit out of scope for this assignment. I did a sneak peek on the next assignment and saw that we would be implementing a frontend for this application, so I will probably add a more robust authentication system then.

## Conclusion

Had a lot of fun working on this assignment. Usually I work with less opinionated frameworks, so it was fun to see how much you can get done with Spring Boot in such a short amount of time. It was also pretty easy to find resources online.