package com.projetos.apitodo.services;

import com.projetos.apitodo.models.User;
import com.projetos.apitodo.repositories.TaskRepository;
import com.projetos.apitodo.repositories.UserRepository;
import com.projetos.apitodo.services.exceptions.DataBindingViolationException;
import com.projetos.apitodo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
           "Usuário não encontrado! Id: " +id+ ", Tipo: " + User.class.getName()
        ));

    }

    @Transactional
    public User create(User user){

        user.setId(null);
        user = this.userRepository.save(user);
        return user;

    }

    @Transactional
    public User update(User user){

        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setDt_nascimento(user.getDt_nascimento());
        return this.userRepository.save(newUser);

    }

    public void delete(Long id){

        findById(id);

        try {
            this.userRepository.deleteById(id);
        } catch (Exception e){
            throw new DataBindingViolationException("Usuário vinculado a outras entidades. Não foi possível excluir!");
        }

    }

}
