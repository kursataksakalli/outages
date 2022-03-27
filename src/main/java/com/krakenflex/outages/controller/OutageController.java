package com.krakenflex.outages.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krakenflex.outages.dto.BaseResponseDto;
import com.krakenflex.outages.service.OutageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller for main logic
 */
@RestController("/outages")
@Api(value = "Triggers for main logic")
public class OutageController {

    private OutageService outageService;

    @Autowired
    public OutageController(OutageService outageService) {
        this.outageService = outageService;
    }

    /**
     * default trigger for main logic
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/main")
    @ApiOperation(value = "Execute main logic and returns result of the service POST /site-outages/{siteId} for site norwich-pear-tree")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job completed successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    public BaseResponseDto<String> mainJob() throws JsonProcessingException {
        ResponseEntity<String> response = outageService.mainJob("norwich-pear-tree");
        BaseResponseDto<String> result = new BaseResponseDto<>();
        result.setResponseCode(response.getStatusCodeValue());
        result.setResponse(response.getBody());

        return result;
    }

    /**
     * parametric trigger for main logic
     * @param siteId
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/main/{siteid}")
    @ApiOperation(value = "Execute main logic and returns result of the service POST /site-outages/{siteId} for specified site")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Job completed successfully"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    public BaseResponseDto<String> mainJob(@PathVariable("siteid") @ApiParam("Specified site-id like norwich-pear-tree") String siteId) throws JsonProcessingException {
        ResponseEntity<String> response = outageService.mainJob(siteId);

        String responseBody = null;
        Integer resultCode = 503;

        BaseResponseDto<String> result = new BaseResponseDto<>();

        if(response != null) resultCode = response.getStatusCodeValue();
        result.setResponseCode(resultCode);

        if(response != null) responseBody = response.getBody();
        result.setResponse(responseBody);

        return result;
    }
}
