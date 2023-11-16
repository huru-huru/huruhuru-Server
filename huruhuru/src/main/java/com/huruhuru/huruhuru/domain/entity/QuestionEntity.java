package com.huruhuru.huruhuru.domain.entity;

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
@Table(name = "questions")
public class QuestionEntity {
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

    @ElementCollection
    @CollectionTable(name = "answer", joinColumns = @JoinColumn(name = "question_id"))
    private List<AnswerEntity> answerList = new ArrayList<>();

}
