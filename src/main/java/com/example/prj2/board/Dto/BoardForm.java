package com.example.prj2.board.Dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class BoardForm {
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;

    @NotBlank(message = "작성자는 필수입니다.")
    private String writer;
}
