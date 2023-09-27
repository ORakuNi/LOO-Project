package com.example.loo.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardUpdateForm {
    private Long board_id;
    @NotBlank
    private String board_title;
    @NotBlank
    private String board_contents;
    private String member_mail;
    private Long hit;
    private LocalDateTime create_time;
}
