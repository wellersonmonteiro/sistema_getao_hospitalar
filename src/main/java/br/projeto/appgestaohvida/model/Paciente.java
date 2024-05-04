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
    String nome = null;
    String cpf = null;
    String telefone = null;
    String email = null;
    String rua = null;
    String cep = null;
    String genero = null;
    String dataNascimento = null;
    String tipoDeAtendimento = null;


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
