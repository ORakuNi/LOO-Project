package com.example.loo.model.board;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BoardUpdateForm {
    private Long board_id;
    @NotBlank(message = "제목을 입력해 주세요.")
    @Size(max = 50, message = "제목은 50자 이내로 입력해 주세요.")
    private String board_title;
    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(max = 4000, message = "내용은 4000자 이내로 입력해 주세요.")
    private String board_contents;
    private String member_mail;
    private String member_name;
    private BoardCategory board_category;
    private Long hit;
    private LocalDateTime create_time;
}
