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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

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

    public void edit(String loginId, UserForm userForm, MultipartFile profileImage) {
        // 불러오기
        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저가 존재하지 않습니다: " + loginId));

        // 이미지 처리
        if (!profileImage.isEmpty()) {
            validateImage(profileImage);
            String profileImagePath = editUsersImage(profileImage);
            user.setProfileImage(profileImagePath);
        }


        // 수정 (값이 존재할 때)
        user.setNickname(userForm.getNickname());
        user.setEmail(userForm.getEmail());
        user.setPhone(userForm.getPhone());

        // 저장
        userRepository.save(user);
    }

    // 이미지 파일 검증 메소드
    private void validateImage(MultipartFile profileImage) {
        String contentType = profileImage.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }
    }

    public UserDto view(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 사용자가 없습니다: " + id));

        return new UserDto(user.getId(), user.getNickname(), user.getEmail(), user.getPhone(), user.getProfileImage());
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

    public String editUsersImage(MultipartFile profileImage) {
        // 프로필 사진 업로드 메소드

        // 1. 파일 이름 설정
        // UUID.randomUUID() 는 파일이름, 세션토큰등
        //                      중복되면 안되는 것에 사용되는 랜덤 메소드
        String profileImageName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
        // 2. 저장 경로
        String profileImageDirPath = System.getProperty("user.home") + "\\IdeaProjects\\prj2\\image\\";
        try {
            // 2-1. 폴더가 없으면 생성
            File imageDir = new File(profileImageDirPath);
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            // 3. 파일 저장
            File destFile = new File(profileImageDirPath, profileImageName);
            profileImage.transferTo(destFile);
            // 4. DB에서 가져올 경로
            return "/images/profile/" + profileImageName;

        } catch (IOException e) {
            throw new RuntimeException("프로필 이미지 업로드 실패", e);
        }
    }
}
