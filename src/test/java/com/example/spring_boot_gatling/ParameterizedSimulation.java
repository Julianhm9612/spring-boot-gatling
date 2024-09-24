package com.example.spring_boot_gatling;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class ParameterizedSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol;

    FeederBuilder.FileBased<String> csvFeeder = csv("userData.csv").circular();
  
    ScenarioBuilder scn = scenario("Parameterized Simulation")
        .feed(csvFeeder)
        .exec(http("request_login")
        .post("/api/login")
        .formParam("username", "#{username}")
        .formParam("password", "#{password}")
        .check(status().is(200)));
  
    {
        setUp(
        scn.injectOpen(atOnceUsers(1000))
        ).protocols(httpProtocol);
    }

    public ParameterizedSimulation() {
        this.httpProtocol = http
                .baseUrl("http://localhost:8080")
                .acceptHeader("application/json");
    }
}
