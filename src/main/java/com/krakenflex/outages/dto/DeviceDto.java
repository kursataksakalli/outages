package com.krakenflex.outages.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Device dto which keeps name and id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDto extends BaseDto {
    private String name;

    /**
     * returns name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
