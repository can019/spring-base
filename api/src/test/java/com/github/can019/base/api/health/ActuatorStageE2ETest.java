package com.github.can019.base.api.health;

import com.github.can019.base.api.test.util.url.BaseUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("staging")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ActuatorE2ETestUtil.class)
public class ActuatorStageE2ETest {
    @Autowired
    ActuatorE2ETestUtil actuatorE2ETestUtil;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = BaseUrl.LOCALHOST_URL.getUrl();


    @Test
    @DisplayName("엔드포인트 테스트")
    void healthEndpointTest() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl+":" + port + "/actuator/health", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
    }

    @Test
    @DisplayName("Info 엔드포인트 테스트")
    void infoEndpointTest() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl+":" + port + "/actuator/info", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    @DisplayName("Staging 환경에서 액추에이터의 http end point는 info, health, health/{*path}만 노출되어야 한다.")
    void actuatorWebExposureTest(){
        List<String> actuatorWebEndpointListUrlList = actuatorE2ETestUtil.getActuatorWebEndpointList();

        assertThat(actuatorWebEndpointListUrlList).containsExactlyInAnyOrder("health","health/{*path}", "info");
    }
}
