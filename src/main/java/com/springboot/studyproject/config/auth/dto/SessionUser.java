package com.springboot.studyproject.config.auth.dto;

import com.springboot.studyproject.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    private boolean fromSocial;
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.fromSocial = user.isFromSocial();
    }
}