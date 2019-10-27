package edu.ucmo.kcfedapp.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class AppController {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @GetMapping(value = "/public", produces = "application/json")
    public String publicEndpoint() {
        return new JSONObject()
                .put("message", "Hello from a public endpoint! You don't need to be authenticated to see this.")
                .toString();
    }

    @GetMapping(value = "/private", produces = "application/json")
    public String privateEndpoint() {
        return new JSONObject()
                .put("message", "Hello from a private endpoint! You need to be authenticated to see this.")
                .toString();
    }

    @GetMapping(value = "/api/private-scoped", produces = "application/json")
    public String privateScopedEndpoint() {
        return new JSONObject()
                .put("message", "Hello from a private endpoint! You need to be authenticated and have a scope of read:messages to see this.")
                .toString();
    }

    @GetMapping(value = "/private/config", produces = "application/json")
    public String getAppConfigs() {
        return new JSONObject()
                .put("audience", audience)
                .put("issuer", issuer)
                .toString();
    }
}
