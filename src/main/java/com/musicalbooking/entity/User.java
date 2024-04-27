package com.musicalbooking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "The name cannot be empty or null")
    @Size(max = 50, message = "The name must have a maximum of 50 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "The lastname cannot be empty or null")
    @Size(max = 50, message = "The lastname must have a maximum of 50 characters")
    private String lastname;

    @Column(nullable = false)
    @NotBlank(message = "The email cannot be empty or null")
    @Email(message = "Please insert a correctly email")
    @Size(max = 50, message = "The email must have a maximum of 50 characters")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "The email cannot be empty or null")
    @Size(max = 300)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String lastname, String email, String password, Role role) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
