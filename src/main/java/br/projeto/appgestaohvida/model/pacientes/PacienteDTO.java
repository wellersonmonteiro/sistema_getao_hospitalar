package br.projeto.appgestaohvida.model.pacientes;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor

@Getter
@Setter

public class PacienteDTO<TIPO> {
    LocalTime now = LocalTime.now();

    String senha;
    String hora = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    private PacienteDTO<TIPO> proximo;

    public PacienteDTO(String senha) {
        this.senha = senha;

    }

    @Override
    public String toString() {
        return ""+senha+"";
    }

    @Getter
    @JsonProperty("viaAerea")
    private boolean viaAerea;
    @Getter
    @JsonProperty("respiracaoIneficaz")
    private boolean respiracaoIneficaz;
    @Getter
    @JsonProperty("choque")
    private boolean choque;
    @Getter
    @JsonProperty("naoRespondeEstimulo")
    private boolean naoRespondeEstimulo;
    @Getter
    @JsonProperty("emConvulsao")
    private boolean emConvulsao;
    @Getter
    @JsonProperty("dorSevera")
    private boolean dorSevera;
    @Getter
    @JsonProperty("grandeHemorragia")
    private boolean grandeHemorragia;
    @Getter
    @JsonProperty("alteracaoConsciencia")
    private boolean alteracaoConsciencia;
    @Getter
    @JsonProperty("temperaturaAlta")
    private boolean temperaturaAlta;
    @Getter
    @JsonProperty("traumaCraniano")
    private boolean traumaCraniano;
    @Getter
    @JsonProperty("pequenaHemorragia")
    private boolean pequenaHemorragia;
    @Getter
    @JsonProperty("dorModerada")
    private boolean dorModerada;
    @Getter
    @JsonProperty("vomitoPersistente")
    private boolean vomitoPersistente;
    @Getter
    @JsonProperty("temperaturaEntre38e39")
    private boolean temperaturaEntre38e39;
    @Getter
    @JsonProperty("idosoOuGravidaSintomatico")
    private boolean idosoOuGravidaSintomatico;
    @Getter
    @JsonProperty("dorLeve")
    private boolean dorLeve;
    @Getter
    @JsonProperty("nauseas")
    private boolean nauseas;
    @Getter
    @JsonProperty("temperaturaEntre37e38")
    private boolean temperaturaEntre37e38;

}