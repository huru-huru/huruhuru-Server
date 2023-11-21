package com.huruhuru.huruhuru.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
public class AnswerEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, name = "answer_id")
    private Long id;

    private String answerContent;
    private boolean isCorrect;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

}