package com.example.spring_boot_gatling;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class PeakTrafficSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json");
  
    ScenarioBuilder scn = scenario("Peak Traffic Simulation")
        .exec(http("request_hello")
        .get("/api/hello")
        .check(status().is(200)));
  
    {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(100).during(300), // Constant load of 100 users per second
                rampUsersPerSec(10).to(100).during(600) // Gradually increase from 10 to 100 users per second
            )
        ).protocols(httpProtocol);
    }
}
