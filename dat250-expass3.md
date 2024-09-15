# DAT250 Assignment 3

Author: Ole Magnus Fon Johnsen

## What?

I have set up a React SPA application in the `app/`-folder that communicates with the backend / REST API I created in the previous assignment. The modifications I have made to the application are:

- Setting up CORS for the when I run the application locally
- Before each request I check the cookie for a session token and get the current user based on that
- Removed all the `ownerId` / `userId` fields from the API since it will now be based on the session token.

## Components

I didn't name the components exactly `CreateUserComponent`, `CreatePollComponent` and `VoteComponent`, but my components would be:

- `CreateUserComponent` -> `register.tsx` / `login.tsx`
- `CreatePollComponent` -> `polls.create.tsx`
- `VoteComponent` -> `polls.$id.tsx` and `vote-option-button.tsx`

## Challenges?

Not that many challenges. I have worked with React for a while and I am pretty familiar with it. One of the problems I had was setting up the authentication system for each request. Setting up middleware in Spring Boot was a bit more difficult than I thought it would be. And I would say it required a bit more moving parts than expected.

I also struggled with calling the API from the frontend because of CSRF protection. It was a bit difficult to tell that the CSRF protection should be disabled when setting up an environment like this.

## Pending issues?

I still don't hash the passwords, but that is a small issue, so I didn't bother with it.
