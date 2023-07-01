package com.projetos.apitodo.services;

import com.projetos.apitodo.models.User;
import com.projetos.apitodo.models.enums.ProfileEnum;
import com.projetos.apitodo.repositories.TaskRepository;
import com.projetos.apitodo.repositories.UserRepository;
import com.projetos.apitodo.services.exceptions.DataBindingViolationException;
import com.projetos.apitodo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        user = this.userRepository.save(user);
        return user;

    }

    @Transactional
    public User update(User user){

        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setDt_nascimento(user.getDt_nascimento());
        newUser.setPassword(this.bCryptPasswordEncoder.encode(newUser.getPassword()));
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
