package br.projeto.appgestaohvida.seguranca.controllers;


import br.projeto.appgestaohvida.seguranca.dtos.AuthDto;
import br.projeto.appgestaohvida.seguranca.services.AutenticacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthDto authDTo) {
        var usuarioAutententicationToken = new UsernamePasswordAuthenticationToken(
                authDTo.login(), authDTo.senha());
        authenticationManager.authenticate(usuarioAutententicationToken);
        return autenticacaoService.obterToken(authDTo);
    }
}
