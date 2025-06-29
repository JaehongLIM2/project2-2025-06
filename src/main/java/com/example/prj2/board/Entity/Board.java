package com.example.prj2.board.Entity;

import com.example.prj2.user.Entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;

    @Column(name = "created", insertable = false, updatable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private Long views = 0L;
}
