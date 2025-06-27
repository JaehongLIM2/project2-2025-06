package com.example.prj2.user.Controller;

import com.example.prj2.board.Dto.BoardForm;
import com.example.prj2.user.Dto.UserDto;
import com.example.prj2.user.Dto.UserForm;
import com.example.prj2.user.Dto.UserListInfo;
import com.example.prj2.user.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("signup")
    public String signupForm() {

        return "user/signup";
    }

    @PostMapping("signup")
    public String signup(UserForm userForm,
                         @RequestParam("profileImage") MultipartFile profileImage) {

        if (profileImage == null || profileImage.isEmpty()) {
            throw new IllegalStateException("업로드된 파일이 없습니다.");
        }

        // 프로필 사진 받기
        userService.editUsersImage(profileImage);

        userService.add(userForm, profileImage);

        return "redirect:/board/list";
    }

    @GetMapping("list")
    public String list(Model model) {
        List<UserListInfo> list = userService.list();
        model.addAttribute("userList", list);
        return "user/list";
    }

    @GetMapping("view")
    public String view(String id, Model model) {
        UserDto user = userService.view(id);
        model.addAttribute("user", user);
        return "user/view";
    }

    @GetMapping("edit")
    public String edit(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            return "redirect:/user/login";
        }

        // 유저 정보 조회 후 모델에 추가
        UserDto user = userService.view(loginId);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute UserForm userForm,
                       HttpSession session,
                       RedirectAttributes redirectAttributes,
                       Model model) {

        // 로그인 세션 구분
        String loginId = (String) session.getAttribute("loginId");

        if (loginId == null) {
            return "redirect:/user/login";
        }

        MultipartFile profileImage = userForm.getProfileImage();

        if (profileImage == null || profileImage.isEmpty()) {
            throw new IllegalStateException("업로드된 파일이 없습니다.");
        }

        // 프로필 사진 받기
        userService.editUsersImage(profileImage);

        // 비밀번호 비교
        if (!userService.checkPassword(loginId, userForm.getPassword())) {
            model.addAttribute("message",
                    "비밀번호가 일치하지 않습니다.");
            model.addAttribute("user", userForm);
            return "user/edit";
        }
        // 수정 로직
        userService.edit(loginId, userForm, profileImage);

        // addFlashAttribute()는 임시 데이터 전달(세션 1회용 메세지)
        redirectAttributes.addFlashAttribute("message", "회원정보가 변경되었습니다.");

        // addAttribute는 쿼리스트링에 붙이는 역할
        redirectAttributes.addAttribute("id", loginId);
        return "redirect:/user/view";

    }

    @PostMapping("delete")
    public String delete(@ModelAttribute UserForm userForm,
                         HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        userService.delete(userForm, session);

        if (loginId == null) {
            return "redirect:/board/list";

        }
        return "redirect:/user/login";
    }


    @GetMapping("login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("login")
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        HttpSession session) {
        if (userService.login(id, password)) {
            session.setAttribute("loginId", id);
            return "redirect:/board/list";
        }


        return "redirect:/user/login?error";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 완전 삭제
        return "redirect:/user/login";
    }
}
