package com.example.prj2.user.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(of = {"id", "email", "nickname", "phone"})
@Table(name = "user")
public class User {

    @Id
    private String id;

    private String password;

    private String email;

    private String nickname;

    private String phone;

    @Column(name = "profile_image")
    private String profileImage;
}
