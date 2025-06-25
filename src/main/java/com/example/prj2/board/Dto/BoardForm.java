package com.example.prj2.board.Dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class BoardForm {
    private Long id;

    @NotBlank
    private String title;

    private String content;
    private String writer;
}
