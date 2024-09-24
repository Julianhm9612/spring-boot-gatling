package com.example.spring_boot_gatling;

import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class RampUpSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json");
  
    ScenarioBuilder scn = scenario("Ramp-Up Simulation")
        .exec(http("request_hello")
        .get("/api/hello")
        .check(status().is(200)));
  
    {
        setUp(
            scn.injectOpen(rampUsers(1000).during(600)) // Ramp up to 1000 users over 10 minutes
        ).protocols(httpProtocol);
    }
}
