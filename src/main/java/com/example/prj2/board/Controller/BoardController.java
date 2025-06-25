package com.example.prj2.board.Controller;

import com.example.prj2.board.Dto.BoardDto;
import com.example.prj2.board.Dto.BoardForm;
import com.example.prj2.board.Entity.Board;
import com.example.prj2.board.Service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("write")
    public String writeBoard() {
        return "board/write";
    }

    @PostMapping("write")
    public String write(@Valid @ModelAttribute("boardForm") BoardForm boardForm,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/write";
        }

        boardService.write(boardForm);
        return "redirect:/board/list";
        // 익셉션 발생하면 얼랏 생성
    }

    @GetMapping("list")
    public String listBoard(Model model) {
        List<Board> list = boardService.list();
        model.addAttribute("boardList", list);

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
                            Model model) {
        BoardDto view = boardService.view(id);
        model.addAttribute("board", view);
        return "board/edit";
    }

    @PostMapping("edit")
    public String editBoard(@ModelAttribute BoardForm form) {
        boardService.update(form);
        return "redirect:/board/view?id=" + form.getId();
    }

    @PostMapping("delete")
    public String deleteBoard(@RequestParam("id") Long id) {
        boardService.delete(id);

        return "redirect:/board/list";
    }
}
