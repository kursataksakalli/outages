package com.krakenflex.outages.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * dto to send back result to the service
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SiteOutageDto extends BaseDto {
    private String name;
    private String begin;
    private String end;

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

    /**
     * returns begin date
     * @return
     */
    public String getBegin() {
        return begin;
    }

    /**
     * sets begin date
     * @param begin
     */
    public void setBegin(String begin) {
        this.begin = begin;
    }

    /**
     * returns end date
     * @return
     */
    public String getEnd() {
        return end;
    }

    /**
     * sets end date
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }
}
