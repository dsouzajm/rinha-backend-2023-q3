package br.com.dsouzajm.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String apelido;
    private String nome;
    private LocalDate nascimento;
    private List<Stack> stacks;

    public Pessoa() {
    }

    public Pessoa(UUID id, String apelido, String nome, LocalDate nascimento, List<Stack> stacks) {
        this.id = id;
        this.apelido = apelido;
        this.nome = nome;
        this.nascimento = nascimento;
        this.stacks = stacks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public List<Stack> getStacks() {
        return stacks;
    }

    public void setStacks(List<Stack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id) && Objects.equals(apelido, pessoa.apelido) && Objects.equals(nome, pessoa.nome) && Objects.equals(nascimento, pessoa.nascimento) && Objects.equals(stacks, pessoa.stacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apelido, nome, nascimento, stacks);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", apelido='" + apelido + '\'' +
                ", nome='" + nome + '\'' +
                ", nascimento=" + nascimento +
                ", stacks=" + stacks +
                '}';
    }
}