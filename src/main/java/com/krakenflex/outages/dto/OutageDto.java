package com.krakenflex.outages.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * outage dto which keeps id, begin and end data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutageDto extends BaseDto {
    private String begin;
    private String end;

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
