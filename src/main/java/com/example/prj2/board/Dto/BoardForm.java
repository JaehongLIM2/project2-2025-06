package com.example.prj2.board.Dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class BoardForm {
    private Long id;

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;
    
    private String writer;
}
