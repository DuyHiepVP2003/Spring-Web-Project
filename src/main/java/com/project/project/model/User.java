package com.project.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private boolean enabled;

    @Column(name = "verification_code",updatable = false)
    private String verificationCode;

    @Transient
    private String confirmPassword;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
