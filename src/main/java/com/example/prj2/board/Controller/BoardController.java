package com.example.prj2.board.Controller;

import com.example.prj2.board.Dto.BoardDto;
import com.example.prj2.board.Dto.BoardForm;
import com.example.prj2.board.Entity.Board;
import com.example.prj2.board.Service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("write")
    public String writeBoard(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        return "board/write";
    }

    @PostMapping("write")
    public String write(@Valid @ModelAttribute("boardForm") BoardForm boardForm,
                        BindingResult bindingResult,
                        HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");

        if (bindingResult.hasErrors()) {
            return "board/write";
        }
        // 만약 로그인 되지않았을때
        if (loginId == null) {
            model.addAttribute("alertMessage",
                    "로그인 후 작성할 수 있습니다.");
            model.addAttribute("redirectUrl",
                    "/user/login");
            return "common/alert";
        }
        // 작성자 설정
        boardForm.setWriter(loginId);

        boardService.write(boardForm);
        return "redirect:/board/list";
    }

    @GetMapping("list")
    public String listBoard(@RequestParam(defaultValue = "0") Integer page,
                            Model model) {

        Page<Board> boardPage = boardService.list(page);

        model.addAttribute("boardPage", boardPage);

        return "board/list";
    }

    @GetMapping("view")
    public String viewBoard(@RequestParam("id") Long id,
                            Model model) {
        BoardDto view = boardService.view(id);
        model.addAttribute("board", view);
        return "board/view";
    }

    @GetMapping("edit")
    public String editBoard(@RequestParam("id") Long id,
                            HttpSession session,
                            Model model) {
        BoardDto view = boardService.view(id);

        // 세선 로그인 아이디 가져오기
        String loginId = (String) session.getAttribute("loginId");

        model.addAttribute("board", view);
        return "board/edit";
    }

    @PostMapping("edit")
    public String editBoard(@ModelAttribute BoardForm form,
                            HttpSession session) {

        String loginId = (String) session.getAttribute("loginId");

        // session으로 넘어온 아이디를 service로 보내기
        boardService.update(form, loginId);

        return "redirect:/board/view?id=" + form.getId();
    }

    @PostMapping("delete")
    public String deleteBoard(@RequestParam("id") Long id,
                              HttpSession session) {
        // session 불러오기
        String loginId = (String) session.getAttribute("loginId");
        // session 으로 불러온 아이디를 service로 보내기
        boardService.delete(id, loginId);

        return "redirect:/board/list";
    }
}
