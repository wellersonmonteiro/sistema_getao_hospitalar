package br.projeto.appgestaohvida.seguranca.dtos;


import br.projeto.appgestaohvida.seguranca.enums.RoleEnum;

public record UsuarioDto(String nome, String login, String senha, RoleEnum role) {
}
