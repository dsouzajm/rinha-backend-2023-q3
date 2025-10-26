package br.com.dsouzajm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Stack {
    private UUID id;
    private String stack;
}
