# ![RealWorld Example App](./docs/images/realworld-dual-mode.png)

> ### Spring Boot codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld-example-apps) API spec.

# Getting Started

## DB

```shell
$ docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql
```

## Test

```shell
$ ./gradlew test
```

To locally run the provided Postman collection against your backend, execute:

```shell
$ ./docs/run-api-tests.sh
```

For more details, see [`run-api-tests.sh`](docs/run-api-tests.sh).

## SonarQube

```shell
$ ./gradlew sonarqube -Dsonar.host.url=[sonarqube_url] -Dsonar.login=[token]
```

## Documentation

To generate REST API Documentation, execute:

```shell
$ ./gradlew bootJar
```

For more details, see [`RealWorld - Spring Rest Docs`]( http://htmlpreview.github.io/?https://github.com/seunghan0421/realworld-starter-kit/blob/master/docs/index.html).

## Architecture
### A typical top-level directory layout
```shell

    ├── build                   # Compiled files (alternatively `dist`)
    ├── docs                    # Documentation files (alternatively `doc`)
    ├── src                     # Source files (alternatively `lib` or `app`)
    ├── test                    # Automated tests (alternatively `spec` or `tests`)
    ├── tools                   # Tools and utilities
    ├── LICENSE
    └── README.md
```
### File Directory Structure


> **Q: Why did you use Hexagonal-architecture?**
>
> **A:** The Hexagonal or Ports and Adapters Architecture, is not the silver bullet for all applications. But if broken windows are allowed(as you often make mistakes when you use Traditional Layered Architecture), it might cause a lot of headaches.<br>
&nbsp;&nbsp; When properly implemented and paired with other methodologies, like Domain-Driven Design, Ports and Adapters can ensure an application’s long term stability and extensibility, bringing a great deal of value to the system and the enterprise..

    .
    ├── src                    
    │   ├── docs         
    │       └── asciidoc                # documentation by Spring Rest Docs
    │   ├── main
    │       ├── common          
    │       ├── infra       
    │       ├── article                 # One of the aggregates
    │           ├── domain
    │           ├── adapter
    │               ├── in              # Web Adapter
    │               └── out             # Persistence Adapter
    │                   └── persistence 
    │           └── application
    │               └── service         # Service Implementations
    │               └── port         
    │                   ├── in          # Service Interface(Port)
    │                   └── out         # Persistence Port
    │       └── ...  
    │   └── test                        # Unit tests, Acceptence tests, etcs
    └──
# Diagram

![diagram](docs/images/diagram.png)

# License

All of the codebases are **MIT licensed** unless otherwise specified.

<br />

[![Brought to you by Thinkster](docs/images/end.png)](https://thinkster.io)
