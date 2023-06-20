package com.projetos.apitodo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = User.TABLE_NAME)
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

    @Column(name = "password", length = 60, nullable = false)
    @NotBlank (groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    //private List<Task> tasks = new ArrayList<Task>();
}
