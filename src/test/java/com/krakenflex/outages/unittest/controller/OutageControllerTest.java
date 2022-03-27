package com.krakenflex.outages.unittest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krakenflex.outages.controller.OutageController;
import com.krakenflex.outages.dto.BaseResponseDto;
import com.krakenflex.outages.service.OutageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OutageControllerTest {

    private OutageController outageController;

    @Mock
    private OutageService outageService;

    @BeforeEach
    public void init() {
        outageController = new OutageController(outageService);
    }

    @Test
    public void mainJobTestWithNoArgument() {
        BaseResponseDto<String> response = null;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("test", HttpStatus.OK);
        try {
            doReturn(responseEntity).when(outageService).mainJob("norwich-pear-tree");
            response = outageController.mainJob();
        } catch (JsonProcessingException e) {
            response = null;
        }

        assertNotNull(response);
        assertEquals(200, response.getResponseCode());
        assertEquals("test", response.getResponse());
    }

    @Test
    public void mainJobTestWithArgument() {
        BaseResponseDto<String> response = null;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("test", HttpStatus.OK);
        try {
            doReturn(responseEntity).when(outageService).mainJob("norwich-pear-tree");
            doReturn(null).when(outageService).mainJob("norwich-pear-tree2");
            response = outageController.mainJob("norwich-pear-tree");
        } catch (JsonProcessingException e) {
            response = null;
        }

        assertNotNull(response);
        assertEquals(200, response.getResponseCode());
        assertEquals("test", response.getResponse());

        try {
            response = outageController.mainJob("norwich-pear-tree2");
        } catch (JsonProcessingException e) {
            response = null;
        }

        assertNull(response.getResponse());
        assertNotEquals(200, response.getResponseCode());
    }
}
