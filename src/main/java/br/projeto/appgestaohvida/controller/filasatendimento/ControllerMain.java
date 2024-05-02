package br.projeto.appgestaohvida.controller.filasatendimento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listas/main")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:63342/", "https://example.com"}, allowCredentials = "true")//Colocar o endereço do servidor front end
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
}