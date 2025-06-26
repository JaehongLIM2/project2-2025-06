package com.example.prj2.user.Repository;

import com.example.prj2.user.Dto.UserListInfo;
import com.example.prj2.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<UserListInfo> findAllBy();

    List<User> id(String id);
}