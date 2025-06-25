package com.example.prj2.board.Dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.prj2.board.Entity.Board}
 */
@Value
public class BoardDto implements Serializable {
    Long id;
    String title;
    String content;
    String writer;
    LocalDateTime created;
    Long views;
}