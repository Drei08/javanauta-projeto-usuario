package com.javanauta.aprendendospring.business;

import com.javanauta.aprendendospring.infrastructure.entity.Usuario;
import com.javanauta.aprendendospring.infrastructure.exceptions.ConflictException;
import com.javanauta.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioReposiroty;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario){
        try{
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioReposiroty.save(usuario);

        }catch (ConflictException e){
            throw new ConflictException("Email ja cadastrado", e.getCause());

        }
    }

    public void emailExiste(String email){
        try{
            boolean exist = verificaEmailExistente(email);
            if(exist){
                throw new ConflictException("Email já cadastrado" + email);
            }

        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioReposiroty.existsByEmail(email);
    }
}
