package com.dhivakar.quotegeneratorbot.data.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "question_meta")
public class QuestionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "question")
    private String question;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private QuestionType type;

    @Column(name = "createdtime")
    private LocalDateTime creationTime;
}
