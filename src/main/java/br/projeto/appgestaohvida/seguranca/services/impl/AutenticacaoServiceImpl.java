package br.projeto.appgestaohvida.seguranca.services.impl;


import br.projeto.appgestaohvida.seguranca.dtos.AuthDto;
import br.projeto.appgestaohvida.seguranca.models.Usuario;
import br.projeto.appgestaohvida.seguranca.repositories.UsuarioRepositorio;

import br.projeto.appgestaohvida.seguranca.services.AutenticacaoService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;



@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepositorio.findByLogin(login);
    }

    @Override
    public String obterToken(AuthDto authDto) {
      Usuario usuario =  usuarioRepositorio.findByLogin(authDto.login());
        return geraTokenJwt(usuario);
    }

    public String geraTokenJwt(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret-key");
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(geraDataExpiracao())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token" + exception.getMessage());
        }
    }

    private Instant geraDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }
    public String ValidaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret-key");
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException exception){
            return "";
        }
    }
}
