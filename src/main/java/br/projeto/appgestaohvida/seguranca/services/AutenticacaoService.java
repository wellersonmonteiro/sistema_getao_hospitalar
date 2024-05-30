package br.projeto.appgestaohvida.seguranca.services;


import br.projeto.appgestaohvida.seguranca.dtos.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AutenticacaoService extends UserDetailsService {

    public String obterToken(AuthDto authDto);
    public String ValidaTokenJwt(String token);
}
