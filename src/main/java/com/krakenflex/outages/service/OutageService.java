package com.krakenflex.outages.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.krakenflex.outages.dto.DeviceDto;
import com.krakenflex.outages.dto.OutageDto;
import com.krakenflex.outages.dto.SiteDto;
import com.krakenflex.outages.dto.SiteOutageDto;
import com.krakenflex.outages.util.DateUtil;
import com.krakenflex.outages.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * business logic layer for services
 */
@Service
public class OutageService {

    private RestClientUtil restClientUtil;

    @Value("${outages.controlDate}")
    private String controlDate;

    @Autowired
    public OutageService(RestClientUtil restClientUtil) {
        this.restClientUtil = restClientUtil;
        if(controlDate == null) controlDate = "2022-01-01T00:00:00.000Z";
    }

    /**
     * send request and return all thye outages
     * @return
     * @throws JsonProcessingException
     */
    public List<OutageDto> getOutages() throws JsonProcessingException {
        TypeReference outages =  new TypeReference<List<OutageDto>>(){};

        return restClientUtil.<List<OutageDto>>getRequest(outages, "outages");
    }

    /**
     * send request and return site info
     * @param siteId
     * @return
     * @throws JsonProcessingException
     */
    public SiteDto getSiteInfo(String siteId) throws JsonProcessingException {
        TypeReference sites =  new TypeReference<SiteDto>(){};

        return restClientUtil.<SiteDto>getRequest(sites, "site-info/"+siteId);
    }

    /**
     * posts calculated result to the site-outages service
     * @param siteId
     * @param siteOutages
     * @return
     * @throws JsonProcessingException
     */
    public ResponseEntity<String> postOutages(String siteId, List<SiteOutageDto> siteOutages) throws JsonProcessingException {
        ParameterizedTypeReference<String> res =  new ParameterizedTypeReference<>(){};

        return restClientUtil.<String,List<SiteOutageDto>>postRequest(res, "site-outages/"+siteId, siteOutages);
    }

    /**
     * returns hashmap for deveices to find device in O(1)
     * @param siteDto
     * @return
     */
    private HashMap<String, DeviceDto> getDevices(SiteDto siteDto){
        if(siteDto == null) return null;

        HashMap<String, DeviceDto> result = new HashMap<>();

        for(DeviceDto deviceDto:siteDto.getDevices()) {
            result.put(deviceDto.getId(),deviceDto);
        }

        return result;
    }

    /**
     * filters and prepare Site outages for the post operation
     * @param controlDate
     * @param outageDtos
     * @param siteDto
     * @return
     */
    private List<SiteOutageDto> filterOutages(Date controlDate, List<OutageDto> outageDtos, SiteDto siteDto){
        if(outageDtos == null || controlDate == null || siteDto == null) return null;

        HashMap<String, DeviceDto> devices = getDevices(siteDto);
        if(devices == null) return null;

        List<SiteOutageDto> result = new ArrayList<>();

        for(OutageDto outageDto : outageDtos) {
            Date outageStartDate = DateUtil.getDateFromString(outageDto.getBegin());

            if(outageStartDate == null) continue;

            DeviceDto deviceDto = devices.get(outageDto.getId());

            if(!outageStartDate.before(controlDate) && deviceDto != null) {
                SiteOutageDto siteOutageDto = new SiteOutageDto();
                siteOutageDto.setId(outageDto.getId());
                siteOutageDto.setName(deviceDto.getName());
                siteOutageDto.setBegin(outageDto.getBegin());
                siteOutageDto.setEnd(outageDto.getEnd());
                result.add(siteOutageDto);
            }
        }

        return result;
    }

    /**
     * main business logic
     * gets outages and site-info, filters and combines data to be posted
     * @param siteId
     * @return
     * @throws JsonProcessingException
     */
    public ResponseEntity<String> mainJob(String siteId) throws JsonProcessingException {
        ResponseEntity<String> requestResult;
        List<OutageDto> outageDtos = getOutages();
        SiteDto siteDto = getSiteInfo(siteId);

        List<SiteOutageDto> result = filterOutages(DateUtil.getDateFromString(controlDate),outageDtos,siteDto);

        if(result == null) {
            return null;
        } else {
            requestResult = postOutages(siteId,result);
        }

        return requestResult;
    }
}
