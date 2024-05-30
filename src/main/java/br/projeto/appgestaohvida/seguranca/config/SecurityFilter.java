package br.projeto.appgestaohvida.seguranca.config;


import br.projeto.appgestaohvida.seguranca.models.Usuario;
import br.projeto.appgestaohvida.seguranca.repositories.UsuarioRepositorio;
import br.projeto.appgestaohvida.seguranca.services.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private AutenticacaoService autenticacaoService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extraiTokenHeader(request);
        if (token != null) {
            String login = autenticacaoService.ValidaTokenJwt(token);
            Usuario usuario = usuarioRepositorio.findByLogin(login);

            if (usuario != null) {
                var authentication =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não encontrado");
            }
        }
        filterChain.doFilter(request, response);
    }



    public String extraiTokenHeader(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        var parts = authHeader.split(" ");
        if(parts.length < 2 || !parts[0].equals("Bearer")){
            return null;
        }
        return parts[1];
    }
}

