package br.projeto.appgestaohvida.model.infra;

import br.projeto.appgestaohvida.model.ListasPacientes;
import br.projeto.appgestaohvida.model.Paciente;
import br.projeto.appgestaohvida.model.Senha;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Testew {
    public static void main(String[] args) {
        System.out.println("Listas:");
        ListasPacientes<Paciente>  emegercia = new ListasPacientes<>();


        emegercia.adicionar("B");

        emegercia.adicionar("C");


        System.out.println(emegercia.getPrimeiro());



    }
}
