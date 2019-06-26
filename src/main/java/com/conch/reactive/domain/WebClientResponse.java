package com.conch.reactive.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WebClientResponse {
    private String context;
    private List<Instruction> instructions;
}
