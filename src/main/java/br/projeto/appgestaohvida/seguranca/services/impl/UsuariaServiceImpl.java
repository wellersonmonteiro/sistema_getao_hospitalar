package br.projeto.appgestaohvida.seguranca.services.impl;


import br.projeto.appgestaohvida.seguranca.dtos.UsuarioDto;
import br.projeto.appgestaohvida.seguranca.models.Usuario;
import br.projeto.appgestaohvida.seguranca.repositories.UsuarioRepositorio;

import br.projeto.appgestaohvida.seguranca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UsuariaServiceImpl implements UsuarioService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Override
    public UsuarioDto salvar(UsuarioDto usuarioDto) {
        Usuario usuarioJaExiste = usuarioRepositorio.findByLogin(usuarioDto.login());

        if (usuarioJaExiste != null) {
            throw new RuntimeException("Usuário ja existe!");
        }
        var passwordHash = passwordEncoder.encode(usuarioDto.senha());
        Usuario entity = new Usuario(usuarioDto.nome(), usuarioDto.login(), passwordHash,usuarioDto.role());
        Usuario novoUsuario = usuarioRepositorio.save(entity);
        return new UsuarioDto(novoUsuario.getNome(), novoUsuario.getLogin(), novoUsuario.getSenha(), novoUsuario.getRole());
    }
}
