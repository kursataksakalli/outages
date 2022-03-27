package com.krakenflex.outages.unittest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krakenflex.outages.dto.DeviceDto;
import com.krakenflex.outages.dto.OutageDto;
import com.krakenflex.outages.dto.SiteDto;
import com.krakenflex.outages.dto.SiteOutageDto;
import com.krakenflex.outages.service.OutageService;
import com.krakenflex.outages.util.RestClientUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class OutageServiceTest {

    private OutageService outageService;

    @Mock
    private RestClientUtil restClientUtil;

    @BeforeEach
    public void init(){
        outageService = new OutageService(restClientUtil);
    }

    @Test
    @DisplayName("Get outages method test")
    public void getOutagesTest(){
        List<OutageDto> result = null;

        try {
            Mockito.when(restClientUtil.getRequest(any(), eq("outages")))
                    .thenReturn(new ArrayList());
            result = outageService.getOutages();
        } catch (JsonProcessingException e) {
        }

        assertNotNull(result);
        assertEquals(0,result.size());
    }

    @Test
    @DisplayName("Get site info method test")
    public void getSiteInfoTest() {
        SiteDto result = new SiteDto();
        result.setName("test");

        try {
            Mockito.when(restClientUtil.<SiteDto>getRequest(any(), eq("site-info/getSiteInfoTest")))
                    .thenReturn(result);
            result = outageService.getSiteInfo("getSiteInfoTest");
        } catch (JsonProcessingException e) {
        }

        assertNotNull(result);
        assertEquals("test",result.getName());
    }

    @Test
    @DisplayName("Post outages test")
    public void postOutagesTest() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("test", HttpStatus.OK);

        try {
            Mockito.when(restClientUtil.<String,List<SiteOutageDto>>postRequest(any(), eq("site-outages/postOutagesTest"), eq(null)))
                    .thenReturn(responseEntity);
            responseEntity = outageService.postOutages("postOutagesTest",null);
        } catch (JsonProcessingException e) {
        }

        assertNotNull(responseEntity);
        assertEquals("test",responseEntity.getBody());
    }

    @Test
    @DisplayName("Main job test")
    public void mainJobTest() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("test", HttpStatus.OK);

        List<OutageDto> outages = new ArrayList<>();
        OutageDto outageDto = new OutageDto();
        outageDto.setId("outage1");
        outageDto.setBegin("2022-01-01T00:00:00.000Z");

        outages.add(outageDto);

        outageDto = new OutageDto();
        outageDto.setId("outage2");
        outageDto.setBegin("2021-01-01T00:00:00.000Z");

        outages.add(outageDto);

        outageDto = new OutageDto();
        outageDto.setId("outage3");
        outageDto.setBegin("2022-01-01T00:00:00.000Z");

        outages.add(outageDto);

        SiteDto siteDto = new SiteDto();

        siteDto.setName("site");
        siteDto.setId("test");
        siteDto.setDevices(new ArrayList<>());

        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("device1");
        deviceDto.setId("outage1");
        siteDto.getDevices().add(deviceDto);

        deviceDto = new DeviceDto();
        deviceDto.setName("device2");
        deviceDto.setId("outage2");
        siteDto.getDevices().add(deviceDto);


        try {

            doReturn(outages).when(restClientUtil).<List<OutageDto>>getRequest(any(), eq("outages"));

            doReturn(siteDto).when(restClientUtil).<SiteDto>getRequest(any(), eq("site-info/test"));

            doReturn(responseEntity).when(restClientUtil).<String,List<SiteOutageDto>>postRequest(any(), eq("site-outages/test"), argThat(new MainJobListMathcher()));

            responseEntity = outageService.mainJob("test");
        } catch (JsonProcessingException e) {
        }

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    public class MainJobListMathcher implements ArgumentMatcher<List<SiteOutageDto>> {

        @Override
        public boolean matches(List<SiteOutageDto> list) {
            if(list == null) return false;
            if(list.size() != 1) return false;

            if(!list.get(0).getId().equals("outage1")) return false;

            return true;
        }
    }
}
