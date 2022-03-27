package com.krakenflex.outages.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * base response dto for controller responses
 * @param <T>
 */
@ApiModel("General base response")
public class BaseResponseDto<T> implements Serializable {
    @ApiModelProperty("Response Code")
    private Integer responseCode;

    @ApiModelProperty("Error list")
    private List<String> errors;

    @ApiModelProperty("Warnings List")
    private List<String> warnings;

    @ApiModelProperty("Response Body")
    private T response;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
