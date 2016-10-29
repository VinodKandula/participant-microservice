# Participant microservice

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/900ee33358c9452090979fa290f3a80f)](https://www.codacy.com/app/mahanhz/participant-microservice?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mahanhz/participant-microservice&amp;utm_campaign=Badge_Grade)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/mahanhz/participant-microservice.svg)](http://isitmaintained.com/project/mahanhz/participant-microservice "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/mahanhz/participant-microservice.svg)](http://isitmaintained.com/project/mahanhz/participant-microservice "Percentage of issues still open")

The purpose of this microservice is to learn about CQRS and Event Sourcing using [Axon Framework](http://www.axonframework.org/).

The application creates and queries participants via REST endpoints.

Domain and architecture can be found [here](https://amhzing.wordpress.com/participant/).

## Main profiles

### Offline
This profile is used when there is no network connectivity.

* Command side: File based event store
* Query side: Uses an in mem H2 database with JPA and [Querydsl](http://www.querydsl.com/).

Example usage: 
```
gradle bootRun -Dspring.profiles.active=offline
```

### Online
This profile is used when there is network connectivity (e.g. there is access to the eureka server and config server)

* Command side: Uses Mongodb as the event store
* Query side: Uses Cassandra with Datastax query builder

Example usage:
```
gradle bootRun -Dspring.profiles.active=online,local -Deureka.client.serviceUrl.defaultZone=http://192.168.1.33:13303/eurekaServer/eureka/,http://192.168.1.34:13303/eurekaServer/eureka/
```
