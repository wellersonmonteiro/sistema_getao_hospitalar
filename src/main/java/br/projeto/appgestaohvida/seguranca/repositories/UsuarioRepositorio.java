package br.projeto.appgestaohvida.seguranca.repositories;


import br.projeto.appgestaohvida.seguranca.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
}
