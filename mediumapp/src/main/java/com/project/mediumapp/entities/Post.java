package com.project.mediumapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.swing.*;

@Entity
@Table ( name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;


    @ManyToOne (fetch = FetchType.LAZY) // many-> post, one -> user --- LAZY-> post işlemleriyle beraber user gelmesin diye
    @JoinColumn (name = "user_id",nullable = false) // user nesnesi user_id için yapıldı demek istiyorum ve false olamasın diyorum
    @OnDelete(action = OnDeleteAction.CASCADE) // user silindiğinde postlar da silinsin diyoruz
    @JsonIgnore

    User user;

    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;

}
