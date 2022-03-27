package com.krakenflex.outages.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * base data transfer objects for other dto's
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDto implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
