package com.hruhuru.huruhuru.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class AnswerEntity {
    private String answerContent;
    private boolean isCorrect;
}