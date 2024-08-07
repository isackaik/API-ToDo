package com.projetos.apitodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    public interface CreateUser{}
    public interface UpdateUser{}
    public static final String TABLE_NAME = "tb_user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank (groups = CreateUser.class)
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotBlank (groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    @NotBlank(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class})
    @CPF(groups = {CreateUser.class, UpdateUser.class}, message = "CPF inválido")
    private String cpf;

    @Column(name = "dt_nascimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dt_nascimento;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();


}
