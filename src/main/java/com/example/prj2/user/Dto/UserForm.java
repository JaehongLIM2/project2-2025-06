package com.example.prj2.user.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {

    @NotBlank(message = "아이디은 필수입니다.")
    private String id;

    @NotBlank(message = "비밀번호 필수입니다.")
    private String password;

    @NotBlank(message = "별명은 필수입니다.")
    private String nickname;

    private String email;

    private String phone;
}
