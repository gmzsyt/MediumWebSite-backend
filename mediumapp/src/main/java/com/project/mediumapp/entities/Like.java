package com.project.mediumapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table( name =  "p_like")
@Data
public class Like {
    @Id
    Long id;
    @ManyToOne(fetch = FetchType.LAZY) // many-> like, one -> bir post için bir sürü like olabilir
    @JoinColumn (name = "post_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Post post;

    @ManyToOne (fetch = FetchType.LAZY) // many-> like, one -> bir userın bir çok likeı olabilir
    @JoinColumn (name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;
}
