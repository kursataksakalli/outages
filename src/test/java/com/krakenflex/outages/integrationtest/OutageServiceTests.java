package com.krakenflex.outages.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krakenflex.outages.dto.OutageDto;
import com.krakenflex.outages.dto.SiteDto;
import com.krakenflex.outages.service.OutageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OutageServiceTests {

    @Autowired
    private OutageService outageService;

    @Value("${outages.doIntegrationTests}")
    private boolean doTests;

    @Test
    @DisplayName("Outage service test")
    void outageTest() {
        if(!doTests) return;

        List<OutageDto> result;
        try {
            result = outageService.getOutages();
        } catch (JsonProcessingException e) {
            result = null;
        }

        assertNotNull(result);
        assertNotEquals(0,result.size());
    }

    @Test
    @DisplayName("Site info service test")
    void siteInfoTest() {
        if(!doTests) return;

        SiteDto siteDto = null;
        try {
            siteDto = outageService.getSiteInfo("norwich-pear-tree");
        } catch (JsonProcessingException e) {
            siteDto = null;
        }

        assertNotNull(siteDto);
        assertNotNull(siteDto.getDevices());
        assertNotEquals(0,siteDto.getDevices().size());
    }

    @Test
    @DisplayName("Site outage post service test")
    void siteOutagePostTest() {
        if(!doTests) return;

        ResponseEntity<String> response = null;

        try {
            response = outageService.mainJob("norwich-pear-tree");
        } catch (JsonProcessingException e) {
            response = null;
        }

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

}
