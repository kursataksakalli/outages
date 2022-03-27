package com.krakenflex.outages.unittest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.outages.util.RestClientUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RestClientUtilTest {

    private RestClientUtil restClientUtil;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void init(){
        restClientUtil = new RestClientUtil(objectMapper,restTemplate);
    }

    @Test
    @DisplayName("Get url method test")
    public void getUrlTest(){
        String newUrl = restClientUtil.getUrl("test");
        assertEquals("https://api.krakenflex.systems/interview-tests-mock-api/v1/test",newUrl);
    }

    @Test
    @DisplayName("Get request method test")
    public void getRequestTest(){
        TypeReference typeRef =  new TypeReference<String>(){};
        boolean error = false;

        ResponseEntity<String> responseEntity = new ResponseEntity<>("test", HttpStatus.OK);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept","application/json");
        httpHeaders.set("x-api-key", "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);

        doReturn(responseEntity).when(restTemplate).exchange(restClientUtil.getUrl("test"), HttpMethod.GET, requestEntity, String.class);

        try {
            Mockito.when(objectMapper.readValue("test",typeRef)).thenReturn("test");
        } catch (JsonProcessingException e) { }

        String response = null;

        try {
            response = (String) restClientUtil.getRequest(typeRef,"test");
        } catch (JsonProcessingException e) {
            error = true;
        }

        assertFalse(error);
        assertEquals("test", response);
    }

    @Test
    @DisplayName("Post request method test")
    public void postRequestTest(){
        ParameterizedTypeReference<String> res =  new ParameterizedTypeReference<>(){};

        ResponseEntity<String> responseEntity = new ResponseEntity<>("test", HttpStatus.OK);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept","application/json");
        httpHeaders.set("x-api-key","EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23");

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, httpHeaders);

        Mockito.when(restTemplate.exchange(restClientUtil.getUrl("test"), HttpMethod.POST, requestEntity, res))
                .thenReturn(responseEntity);

        try {
            responseEntity = restClientUtil.postRequest(res,"test",null);
        } catch (JsonProcessingException e) {}

        assertNotNull(responseEntity);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals("test",responseEntity.getBody());
    }
}
