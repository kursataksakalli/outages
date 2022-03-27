package com.krakenflex.outages.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * site dto which keeps id, name, and devices
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteDto extends BaseDto{
    private String name;
    private List<DeviceDto> devices;

    /**
     * return devices array data
     * @return
     */
    public List<DeviceDto> getDevices() {
        return devices;
    }

    /**
     * sets devices data
     * @param devices
     */
    public void setDevices(List<DeviceDto> devices) {
        this.devices = devices;
    }

    /**
     * returns name of the site
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets name of the site
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
