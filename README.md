# API Monitoring

## Description

This is a simple project that mimics real-time monitoring of web API.
There is only one main api to be monitored:

```http request
GET http://localhost:8080/api/student?
    amount=234
```

This API returns a list of students with the specified amount, which can only be
a positive integer less than or equal to 1000. For more endpoints, please refer to
the [OpenAPI Specification](monitor-openapi.yaml).

## Features

- The api endpoint can give correct response based on the amount parameter you passed.
- Qps (query per second) monitoring
- Success Rate monitoring
- Response time monitoring and logging (to console)
- Calculate average response time of 10 groups categorized based on `amount` parameter
- Rate limiting using [Resilience4j](https://resilience4j.readme.io/)
- Dynamic visualization of the metrics using [Grafana](https://grafana.com/)

## Requirements

- [MySQL](https://www.mysql.com/) server, version 8.0 or higher is recommended
- Java 17 or higher
- [Prometheus](https://prometheus.io/) server for metrics collection
- [Grafana](https://grafana.com/) for dynamic visualization of certain metrics

## How to run

1. Clone the repository
2. Create a MySQL database and run the `schema.sql` script to create the necessary table `student`
3. run `data_generator.sql` to populate the table with some data and start the service.
4. Prepare Prometheus & Grafana server, both are welcomed to be used via docker containers.
   If you use docker, bind the `prometheus.yml` in prometheus directory to the
   container's `/etc/prometheus/prometheus.yml` file. Be sure to start both servers.
5. open your browser and go to Grafana webpage to access Grafana dashboard,
   add Prometheus as a data source and create a dashboard. Monitor the following metrics:
    - qps: `rate(http_server_requests_seconds_count{uri="/api/student"}[1m])`
    - success rate: `student_succeedRate`
6. Run the main method in `Application` class.

## Monitoring

The project is instrumented with Micrometer, a metrics collection library that integrates
with Spring Boot Actuator. The metrics are exposed via `/actuator/prometheus` endpoint.

The metrics collected are:

- `student.req`: a counter that increments every time the API is called
- `student.req.success`: a counter that increments every time the API is called successfully
- `student.succeedRate`: a gauge that shows the success rate of the API
- spring-boot-actuator auto-detected metrics including request count, etc.