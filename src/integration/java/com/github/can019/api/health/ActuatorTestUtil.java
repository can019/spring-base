package com.github.can019.api.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Profile({"staging", "test", "local"})
public class ActuatorTestUtil {
    @Autowired
    private WebEndpointsSupplier webEndpointsSupplier;

    public List<String> getActuatorWebEndpointList() {
        return webEndpointsSupplier.getEndpoints().stream()
                .flatMap(endpoint -> endpoint.getOperations().stream())
                .map(operation -> operation.getRequestPredicate().getPath())
                .collect(Collectors.toUnmodifiableList());
    }
}
