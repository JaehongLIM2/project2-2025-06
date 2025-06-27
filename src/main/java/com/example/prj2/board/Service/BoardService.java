package com.example.prj2.board.Service;

import com.example.prj2.board.Dto.BoardDto;
import com.example.prj2.board.Dto.BoardForm;
import com.example.prj2.board.Entity.Board;
import com.example.prj2.board.Repository.BoardRepository;
import com.example.prj2.user.Entity.User;
import com.example.prj2.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void write(BoardForm boardForm) {
        // 작성자 ID(String) → User 객체로 변환
        User writer = userRepository.findById(boardForm.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
        // boardForm -> 익셉션
        Board board = new Board();
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setWriter(writer);
        boardRepository.save(board);
    }

    public Page<Board> list(Integer page) {
        // list 주석처리 후 paging
        Page<Board> boardPage = boardRepository.findAll(PageRequest.of(page, 10,
                Sort.by(Sort.Direction.DESC, "created").descending()));

        return boardPage;

//        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

    }

    public BoardDto view(Long id) {
        Board board = boardRepository.findById(id).get();
        board.setViews(board.getViews() + 1);
        BoardDto boardDto = new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter().getId(),
                board.getCreated(),
                board.getViews()
        );

        return boardDto;
    }

    public void update(BoardForm form, String loginId) {
        // 조회
        Board board = boardRepository.findById(form.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다: id=" + form.getId()));
        // 수정 : 로그인한 아이디와 작성자 아이디와 같을 때만
        if (board.getWriter().equals(loginId)) {
            board.setTitle(form.getTitle());
            board.setContent(form.getContent());
            // 저장
            boardRepository.save(board);
        }
    }

    public void delete(Long id, String loginId) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));

        // 작성자 체크
        if (!board.getWriter().equals(loginId)) {
            try {
                throw new AccessDeniedException("본인만 삭제할 수 있습니다.");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
        boardRepository.deleteById(id);

    }


}
