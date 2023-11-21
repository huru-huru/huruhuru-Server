package com.huruhuru.huruhuru.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huruhuru.huruhuru.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "question")
public class QuestionEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long category;

    @Column(nullable = false)
    private Long theme;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long testCount;

    @Column(nullable = false)
    private Long questionNumber;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "question")
    private List<AnswerEntity> answerList = new ArrayList<>();

}
