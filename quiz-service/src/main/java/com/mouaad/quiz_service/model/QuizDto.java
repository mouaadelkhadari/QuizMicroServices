package com.mouaad.quiz_service.model;


import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private Integer numQuestions;
    private String title;
}
