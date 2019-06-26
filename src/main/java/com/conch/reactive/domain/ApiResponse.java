package com.conch.reactive.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    List<Instruction> instructionList = new ArrayList<>();

    public void addInstruction(Instruction instruction) {
        this.instructionList.add(instruction);
    }
}
