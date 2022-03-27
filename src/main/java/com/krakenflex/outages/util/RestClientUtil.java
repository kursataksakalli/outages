package com.krakenflex.outages.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krakenflex.outages.exception.UnavailableServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * client util for the rest services
 */
@Component
public class RestClientUtil {

    @Value("${outages.baseUrl}")
    public static String BASE_URL = "https://api.krakenflex.systems/interview-tests-mock-api/v1/";

    @Value("${outages.apiKey}")
    public static String API_KEY = "EltgJ5G8m44IzwE6UN2Y4B4NjPW77Zk6FJK3lL23";

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    @Autowired
    public RestClientUtil(ObjectMapper objectMapper,RestTemplate restTemplate){
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * returns url with root address
     * @param url
     * @return
     */
    public String getUrl(String url) {
        return BASE_URL + url;
    }

    /**
     * get request for the remote services
     * @param cls
     * return type
     * @param url
     * get url
     * @param <T>
     *     return type
     * @return
     * @throws JsonProcessingException
     */
    public <T> T getRequest(TypeReference<T> cls, String url) throws JsonProcessingException {
        T result;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept","application/json");
        httpHeaders.set("x-api-key",API_KEY);

        HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(getUrl(url), HttpMethod.GET, requestEntity, String.class);

        if(response.getStatusCodeValue() != 200) throw new UnavailableServiceException("There is a problem at service!");

        result = objectMapper.readValue(response.getBody(),cls);

        return result;
    }

    /**
     * post request for the remote system
     * @param cls
     * return type
     * @param url
     * post url
     * @param body
     * post body
     * @param <T>
     *     return type
     * @param <P>
     *     body type
     * @return
     * @throws JsonProcessingException
     */
    public <T,P> ResponseEntity<T> postRequest(ParameterizedTypeReference<T> cls, String url, P body) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("accept","application/json");
        httpHeaders.set("x-api-key",API_KEY);

        HttpEntity<P> requestEntity = new HttpEntity<P>(body, httpHeaders);

        ResponseEntity<T> response = restTemplate.exchange(BASE_URL+url, HttpMethod.POST, requestEntity, cls);

        if(response.getStatusCodeValue() != 200) throw new UnavailableServiceException("There is a problem at service!");

        return response;
    }
}
