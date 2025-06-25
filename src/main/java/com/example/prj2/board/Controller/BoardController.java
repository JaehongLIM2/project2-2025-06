package com.example.prj2.board.Controller;

import com.example.prj2.board.Dto.BoardDto;
import com.example.prj2.board.Dto.BoardForm;
import com.example.prj2.board.Entity.Board;
import com.example.prj2.board.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String write(BoardForm boardForm) {
        boardService.write(boardForm);
        return "redirect:/board/write";
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
}
