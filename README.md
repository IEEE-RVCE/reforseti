# reforseti

An adventure in Java. A reimplementation of `site-rear` in Java, Springboot.

Note: This is currently a **POC**. This is **not** intended to replace the current API Server.
Reference API Document: [api-ref.md](https://github.com/IEEE-RVCE/site-rear/blob/master/docs/api-ref.md)
## Aims

- Performance test respective APIs
- Mirror the API of site-rear as well as possible

## Requirements

- Java *17*
- Postgres DB
  - It **must** be database-compatible with the existing `site-rear` project. 
- JWT-based authentication
  - it **must** be able to use at least the same auth methods as `site-rear`

## Key differences

- The API return shape is now more regular. Refer `ResultsDTO`
- API verbs have been normalized. 
  - `events` is now `event`


## Getting started

- Install Java **17**. SDKMan is a good way to start.
- Connect to local DB with `ssh -L 5442:localhost:5442 -N ssh_helper@machine.ieee-rvce.org`
- Copy and put `application-local.properties` into `src/main/resources`. It will have local DB configurations
- Use any IDE - VSCode/IntelliJ.
  - VSCode: Add the following configuration to `launch.json` under the `configurations` array:
    ```jsonc
    {
        "type": "java",
        "name": "SiteRearNouveauApplication",
        "request": "launch",
        "mainClass": "org.ieeervce.api.siterearnouveau.SiteRearNouveauApplication",
        "projectName": "site-rear-nouveau",
        "env": {
            "SPRING_PROFILES_ACTIVE": "local"
        }
    }
    ```
  - IntelliJ: Set the active profiles to `local`
- Start the project with `SPRING_PROFILES_ACTIVE=local ./gradlew bootRun`

## Current Implementation Notes ~~Hacks~~

- JWT and Basic auth supported
- CORS is disabled.
