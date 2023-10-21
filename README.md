# reforseti

An adventure in Java. Reimplementing `site-rear` in Java, Springboot.

Note: This is currently a **POC**. This is **not** intended to replace the current API Server.

## Aims

- Performance test respective APIs
- Mirror the API of site-rear as well as possible

## Requirements

- Use the same DB layout. 
- Same Auth mechanism

## Key differences

- The API return shape is now more regular. Refer `ResultsDTO`
- API verbs have been normalized. 
  - `events` is now `event`


## Getting started

- Install Java **17**. SDKMan is a good way to start.
- Connect to local DB with `ssh -L 5442:localhost:5442 -N ssh_helper@machine.ieee-rvce.org`
- Use any IDE - VSCode/IntelliJ
- Start the project with `./gradlew bootRun`

## Current Implementation Notes ~~Hacks~~

- For authentication, it is currently an **In memory** list of users. Refer [`SecurityConfig`](./src/main/java/org/ieeervce/api/siterearnouveau/config/SecurityConfig.java)
