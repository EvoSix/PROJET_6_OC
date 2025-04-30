package com.openclassrooms.mddapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

    public class Post {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        @ManyToOne
        @JoinColumn(name = "author_id")
        private User author;

        @ManyToOne
        @JoinColumn(name = "topic_id")
        private Topic topic;
    @CreatedDate
        @Column
        private LocalDateTime createdAt;



}
