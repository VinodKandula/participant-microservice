# Participant microservice

The purpose of this microservice is to learn about CQRS and Event Sourcing using [Axon Framework](http://www.axonframework.org/).

The application creates and queries participants via REST endpoints.

## Main profiles

### Offline
This profile is used when there is no network connectivity.

* Command side: Writes to a file on the local machine
* Query side: Uses an in mem H2 database with JPA and [Querydsl](http://www.querydsl.com/).

Example usage: 
```
gradle bootRun -Dspring.profiles.active=offline
```

* Command side: Uses Mongodb as the event store
* Query side: Uses Cassandra

### Online
This profile is used when there is network connectivity (e.g. there is access to the eureka server and config server)

Example usage:
```
gradle bootRun -Dspring.profiles.active=online,local -Deureka.client.serviceUrl.defaultZone=http://192.168.1.33:13303/eurekaServer/eureka/,http://192.168.1.34:13303/eurekaServer/eureka/
```
