package com.openclassrooms.mddapi.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 50)
    private String label;

    @NonNull
    @Column(nullable = false, length = 50)
    private String description;
}
