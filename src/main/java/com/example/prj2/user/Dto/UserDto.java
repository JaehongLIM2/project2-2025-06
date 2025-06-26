package com.example.prj2.user.Dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.prj2.user.Entity.User}
 */
@Value
public class UserDto implements Serializable {
    String id;
    String nickname;
    String email;
}