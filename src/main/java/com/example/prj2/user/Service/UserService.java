package com.example.prj2.user.Service;

import com.example.prj2.user.Dto.UserDto;
import com.example.prj2.user.Dto.UserForm;
import com.example.prj2.user.Dto.UserListInfo;
import com.example.prj2.user.Entity.User;
import com.example.prj2.user.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void add(UserForm userForm) {
        User user = new User();
        user.setId(userForm.getId());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setNickname(userForm.getNickname());
        user.setPhone(userForm.getPhone());
        userRepository.save(user);
    }

    public List<UserListInfo> list() {
        return userRepository.findAllBy();
    }

    public boolean login(String id, String password) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getPassword().equals(password);
        }
        return false;
    }

    public void edit(String loginId, UserForm userForm) {
        // 불러오기
        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저가 존재하지 않습니다: " + loginId));
        // 수정 (값이 존재할 때)
        user.setNickname(userForm.getNickname());
        user.setEmail(userForm.getEmail());
        user.setPhone(userForm.getPhone());

        // 저장
        userRepository.save(user);
    }

    public UserDto view(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 사용자가 없습니다: " + id));

        return new UserDto(user.getId(), user.getNickname(), user.getEmail(), user.getPhone());
    }

    public void delete(UserForm userForm, HttpSession session) {
        userRepository.deleteById(userForm.getId());
        session.removeAttribute("loginId");
    }

    public boolean checkPassword(String loginId,
                                 @NotBlank(message = "비밀번호 필수입니다.")
                                 String inputPassword) {

        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return user.getPassword().equals(inputPassword);
    }
}
