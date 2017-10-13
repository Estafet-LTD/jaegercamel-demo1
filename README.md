# jaegercamel-demo1

This project is part of a group of sample microservices deployable in Openshift using S2I
See jaegercamel-demo2 for the other microservice

Because the objective of this project is to demonstrate traceability using opentracing, the algorithm used here is highly inefficient

---

The two main classes related to OpenTracing are com.estafet.camelboot.Application and com.estafet.camelboot.ValidateService

_Application_ shows a simple initialization using environment variables

_ValidateService_ shows how to manually create a Span


---
This project expects the following Environment variables

+ JAEGER_SAMPLER_TYPE	 - const
+ JAEGER_SAMPLER_PARAM - 1
+ JAEGER_SAMPLER_MANAGER_HOST_PORT - [hostname]:5778
+ JAEGER_AGENT_HOST - [hostname]
+ JAEGER_SERVICE_NAME	 - (Added as part of the initialization)