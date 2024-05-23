package com.project.mediumapp.requests;

import lombok.Data;

@Data

public class CommentRequest {
    private Long id;
    private String userName;
    private String text;
}

