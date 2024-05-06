package br.projeto.appgestaohvida.model;

import org.springframework.stereotype.Service;

@Service

public class ListasPacientes<TIPO> {

        private Paciente<TIPO> primeiro;
        private Paciente<TIPO> ultimo;
        private int tamanho;

        public ListasPacientes(){
            this.tamanho = 0;
        }

        public Paciente<TIPO> getPrimeiro() {
            return primeiro;
        }

        public void setPrimeiro(Paciente<TIPO> primeiro) {
            this.primeiro = primeiro;
        }

        public Paciente<TIPO> getUltimo() {
            return ultimo;
        }

        public void setUltimo(Paciente<TIPO> ultimo) {
            this.ultimo = ultimo;
        }

        public int getTamanho() {
            return tamanho;
        }

        public void setTamanho(int tamanho) {
            this.tamanho = tamanho;
        }

        public void adicionar(String novoValor){
            Paciente<TIPO> novoElemento = new Paciente<>(novoValor);
            if (this.primeiro == null && this.ultimo == null){
                this.primeiro = novoElemento;
                this.ultimo = novoElemento;
            }else{
                this.ultimo.setProximo(novoElemento);
                this.ultimo = novoElemento;
            }
            this.tamanho++;
        }
    public String obterPrimeiraSenha(ListasPacientes<TIPO> lista) {
        if (lista.getPrimeiro() != null) {
            Paciente<TIPO> primeiroPaciente = lista.getPrimeiro();

            if (primeiroPaciente.getSenha() != null) {
                return primeiroPaciente.getSenha(); // Retorna a senha do primeiro paciente
            } else {
                return "Senha não disponível para o primeiro paciente";
            }
        } else {
            return "Nenhum paciente na lista";
        }
    }
    public void excluirPrimeiro() {
        if (this.primeiro != null) {
            // Verifica se há apenas um elemento na lista
            if (this.primeiro == this.ultimo) {
                this.primeiro = null;
                this.ultimo = null;
            } else {
                // Atualiza o primeiro elemento para o próximo da lista
                this.primeiro = this.primeiro.getProximo();
            }
            this.tamanho--; // Decrementa o tamanho da lista
        }
    }



    public void remover(int valorProcurado){
            Paciente<TIPO> anterior = null;
            Paciente<TIPO> atual = this.primeiro;
            for(int i=0; i < this.getTamanho(); i++){
                if (atual.getSenha().equals(valorProcurado)){
                    if (this.tamanho == 1){
                        this.primeiro = null;
                        this.ultimo = null;
                    }else if (atual == primeiro){
                        this.primeiro = atual.getProximo();
                        atual.setProximo(null);
                    }else if (atual == ultimo){
                        this.ultimo = anterior;
                        anterior.setProximo(null);
                    }else{
                        anterior.setProximo(atual.getProximo());
                        atual = null;
                    }
                    this.tamanho--;
                    break;
                }
                anterior = atual;
                atual = atual.getProximo();
            }
        }

        public Paciente getElemento(int posicao){
            Paciente atual = this.primeiro;
            for(int i=0; i < posicao; i++){
                if (atual.getProximo() != null){
                    atual = atual.getProximo();
                }
            }
            return atual;
        }
    public void removerPorIndice(int indice){
        if (indice < 0 || indice >= this.getTamanho()) {
            throw new IndexOutOfBoundsException("Índice fora dos limites da lista");
        }

        Paciente<TIPO> anterior = null;
        Paciente<TIPO> atual = this.primeiro;

        for (int i = 0; i < indice; i++) {
            anterior = atual;
            atual = atual.getProximo();
        }

        if (this.tamanho == 1) {
            this.primeiro = null;
            this.ultimo = null;
        } else if (atual == primeiro) {
            this.primeiro = atual.getProximo();
        } else if (atual == ultimo) {
            this.ultimo = anterior;
            anterior.setProximo(null);
        } else {
            anterior.setProximo(atual.getProximo());
        }

        this.tamanho--;
    }
    public void adicionarGererico(Paciente<TIPO> novoPaciente) {
        if (this.primeiro == null && this.ultimo == null) {
            this.primeiro = novoPaciente;
            this.ultimo = novoPaciente;
        } else {
            this.ultimo.setProximo(novoPaciente);
            this.ultimo = novoPaciente;
        }
        this.tamanho++;
    }




}
