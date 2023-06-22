package com.projetos.apitodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotBlank (groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @Column(name = "cpf", length = 14, nullable = false)
    @NotBlank(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class})
    @CPF(groups = {CreateUser.class, UpdateUser.class}, message = "CPF inválido")
    private Integer cpf;

    @Column(name = "dt_nascimento", nullable = false)
    @NotBlank (groups = {CreateUser.class, UpdateUser.class})
    @Temporal(TemporalType.DATE)
    private Date dt_Nascimento;

    //private List<Task> tasks = new ArrayList<Task>();


    public User(){

    }

    public User(Long id, String username, String password, Integer cpf, Date dt_Nascimento) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.dt_Nascimento = dt_Nascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCpf() {
        return cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf;
    }

    public Date getDt_Nascimento() {
        return dt_Nascimento;
    }

    public void setDt_Nascimento(Date dt_Nascimento) {
        this.dt_Nascimento = dt_Nascimento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(cpf, user.cpf) && Objects.equals(dt_Nascimento, user.dt_Nascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, cpf, dt_Nascimento);
    }

    @Override
    public String toString() {
        return "User "+ id + " - " + username + " - " + cpf;
    }
}
