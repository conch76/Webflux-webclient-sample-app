package com.conch.reactive.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiRequest {
    private int intParameter;
    @ApiModelProperty(required = true, notes = "Timestamp")
    private long created;
}
