package br.projeto.appgestaohvida.model.infra;

public enum TipoSenha {
    A("A"), //Emergêcia
    B("B"),//Geral
    P("P"), //Prioritario pessoa com idade => 60 anos e  deficentes.
    PP("PP")//Pessoa com idade=>80 anos, mulheres grávidas, crianças>=1 ano
    ;
    private String atual;

    TipoSenha(String value) {
        this.atual = value;
    }
    public String getAtual() {
        return atual;
    }

}
