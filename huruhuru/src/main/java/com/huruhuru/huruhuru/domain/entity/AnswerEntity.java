package com.huruhuru.huruhuru.domain.entity;

import jakarta.persistence.*;

@Embeddable
public class AnswerEntity {
    private String answerContent;
    private boolean isCorrect;
}