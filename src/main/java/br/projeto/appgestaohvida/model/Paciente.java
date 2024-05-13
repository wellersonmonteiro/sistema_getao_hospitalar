package br.projeto.appgestaohvida.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter


public class Paciente<TIPO> {
    LocalTime now = LocalTime.now();

    String senha;
    String hora = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    String nome ;
    String cpf;
    String senhaCor;

    public Paciente(String senha, String nomeCompleto, String cpf, String telefone, String email, String rua,
                    String cep, String genero, String dataNascimento, String tipoDeAtendimento) {
        this.senha = senha;
        this.nome = nomeCompleto;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.rua = rua;
        this.cep = cep;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.tipoDeAtendimento = tipoDeAtendimento;
    }

    public Paciente(String nome, String senha, String cpf){
        this.nome = nome;
        this.senha = senha;
        this.cpf = null;
    }


    String telefone ;
    String email ;
    String rua ;
    String cep;
    String genero;
    String dataNascimento;
    String tipoDeAtendimento;


    private Paciente<TIPO> proximo;

    public Paciente(String senha) {
        this.senha = senha;

    }

    @Override
    public String toString() {
        return ""+senha+"";
    }

    public Paciente(String hora, String senha) {
        this.hora = hora;
        this.senha = senha;
    }
}
