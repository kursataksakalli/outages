package com.krakenflex.outages.unittest.configuration;

import com.krakenflex.outages.configuration.RestTemplateConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RestTemplateConfigurationTest {

    private RestTemplateConfiguration restTemplateConfiguration;

    @BeforeEach
    public void init() {
        restTemplateConfiguration = new RestTemplateConfiguration();
    }

    @Test
    @DisplayName("Rest template test")
    public void restTemplateTest() {
        RestTemplate restTemplate = restTemplateConfiguration.restTemplate();

        assertNotNull(restTemplate);
    }
}
