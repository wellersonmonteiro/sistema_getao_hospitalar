package br.projeto.appgestaohvida.controller.filasatendimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listas/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end
public class ControllerMain {
    @Autowired
    ControllerListaPP controllerListaPP;
    @Autowired
    ControllerListaP controllerListaP;
    @Autowired
    ControllerListaB controllerListaB;
    @Autowired
    ContrellerListaA controllerListaA;
    @GetMapping
    public String main() {
        if (controllerListaA.temListaA()){
            String resposta = controllerListaA.obterPrimeiraSenha();
            controllerListaA.retirarLista();

            return resposta;
        }
       else if (controllerListaPP.temListaPP()) {
            String resposta = controllerListaPP.obterPrimeiraSenha();
            controllerListaPP.retirarLista();

            return resposta;

        } else if (controllerListaP.temListaP()) {

             String resposta = controllerListaP.obterPrimeiraSenha();
             controllerListaP.retirarLista();

            return resposta;

        }else{
            String resposta = controllerListaB.obterPrimeiraSenha();
            controllerListaB.retirarLista();

            return resposta;
        }
    }

    @GetMapping
    @RequestMapping("/conjuntolistas")
    public String conjuntoListas() {
        String resposta = "[";
        int contadorDasListas = 0;
        int contadorListaA= 0;
        int contadorListaB= 0;
        int contadorListaP= 0;
        int contadorListaPP= 0;
        int listaA = controllerListaA.tamanhoList();
        int listaB = controllerListaB.tamanhoList();
        int listaP = controllerListaP.tamanhoList();
        int listaPP = controllerListaPP.tamanhoList();
        int numeroDeListasPercorrer = 1;

        if (controllerListaA.temListaA() && listaA >9){
            contadorDasListas = 10;
            contadorListaA = 10;
        } else if(controllerListaA.temListaA()) {
            contadorListaA = controllerListaA.tamanhoList();
            contadorDasListas  = controllerListaA.tamanhoList();
        }

         if (contadorDasListas <10 && controllerListaPP.temListaPP() ){
             int valorDoI = (10 - contadorDasListas);
             for (int i = 0; i < valorDoI; i++) {
                 contadorListaPP++;
                 contadorDasListas++;
                 if (contadorListaPP == listaPP ) {
                     break;
                 }
             }
        }
        if (contadorDasListas <10 && controllerListaP.temListaP() ){
            int valorDoI = (10 - contadorDasListas);
            for (int i = 0; i < valorDoI; i++) {
                contadorListaP++;
                contadorDasListas++;
                if (contadorListaP == listaP ) {
                    break;
                }
            }
        }
        if (contadorDasListas <10 && controllerListaB.temListaB() ){
            int valorDoI = (10 - contadorDasListas);
            for (int i = 0; i < valorDoI; i++) {
                contadorListaB++;
                contadorDasListas++;
                if (contadorListaB == listaB ) {
                    break;
                }
            }
        }

        for (int i = 1; i <=contadorListaA; i++) {

            resposta = resposta + "{\"Número na Fila\": " + numeroDeListasPercorrer + ", " + controllerListaA.obterFormatado(i -1) + ",\"Tipo de atendimento\":\"Emergêcia\""+"}";
            if (numeroDeListasPercorrer < 10) { resposta =  resposta + ",";}
            numeroDeListasPercorrer++;

        }
        for (int i = 1; i <=contadorListaPP; i++) {

            resposta = resposta + "{\"Número na Fila\": " + numeroDeListasPercorrer + ", " + controllerListaPP.obterFormatado(i -1) + ",\"Tipo de atendimento\":\"Prioritário I\""+"}";
            if (numeroDeListasPercorrer < 10) { resposta =  resposta + ",";}
            numeroDeListasPercorrer++;

        }
        for (int i = 1; i <=contadorListaP; i++) {

            resposta = resposta + "{\"Número na Fila\": " + numeroDeListasPercorrer + ", " + controllerListaP.obterFormatado(i -1) + ",\"Tipo de atendimento\":\"Prioritário II\""+"}";
            if (numeroDeListasPercorrer < 10) { resposta =  resposta + ",";}
            numeroDeListasPercorrer++;

        }
        for (int i = 1; i <=contadorListaB; i++) {

            resposta = resposta + "{\"Número na Fila\": " + numeroDeListasPercorrer + ", " + controllerListaB.obterFormatado(i -1) + ",\"Tipo de atendimento\":\"Geral\""+"}";
            if (numeroDeListasPercorrer < 10) { resposta =  resposta + ",";}
            numeroDeListasPercorrer++;

        }
        return resposta = resposta + "]";
    }

}