package com.project.mediumapp.requests;

import lombok.Data;

@Data
public class PostRequest {
    private Long id;
    private String title;
    private String text;
    private String userName;
}

