package com.springboot.studyproject.config.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class UserSaveRequestDto {
    private String name;
    private String email;
    private String password;
    private String picture;
}
