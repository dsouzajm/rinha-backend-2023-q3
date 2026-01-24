package br.com.dsouzajm.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Stack implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String stack;

    public Stack() {
    }

    public Stack(UUID id, String stack) {
        this.id = id;
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}