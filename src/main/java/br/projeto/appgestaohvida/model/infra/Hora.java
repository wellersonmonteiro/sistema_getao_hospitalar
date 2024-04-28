package br.projeto.appgestaohvida.model.infra;

import java.time.LocalTime;
@FunctionalInterface
public interface Hora {
    LocalTime getHora();
}
