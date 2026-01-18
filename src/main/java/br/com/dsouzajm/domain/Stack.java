package br.com.dsouzajm.domain;

import java.util.Objects;
import java.util.UUID;

public class Stack {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stack stack1 = (Stack) o;
        return Objects.equals(id, stack1.id) && Objects.equals(stack, stack1.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stack);
    }

    @Override
    public String toString() {
        return "Stack{" +
                "id=" + id +
                ", stack='" + stack + '\'' +
                '}';
    }
}