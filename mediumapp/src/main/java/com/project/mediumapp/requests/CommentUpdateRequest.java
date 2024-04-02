package com.project.mediumapp.requests;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CommentUpdateRequest {
    String text;

}
