package com.springboot.studyproject.domain.user;

import com.springboot.studyproject.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name= "user_email_fromsocial_uk",
                        columnNames = {"email","fromSocial"}
                )
        }
)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private boolean fromSocial;

    private String password;

    @Builder
    public User(String name, String email, String picture, Role role, String password, boolean fromSocial){
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;
        this.fromSocial=fromSocial;
        this.password=password;
    }

    public User update(String name,String picture){
        this.name=name;
        this.picture=picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}