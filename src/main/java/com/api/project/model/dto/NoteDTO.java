package com.api.project.model.dto;

import jakarta.validation.constraints.*;

public class NoteDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 2, max = 50, message = "O título deve ter entre 2 e 50 caracteres")
    private String title;

    @NotBlank(message = "O texto é obrigatório")
    @Size(min = 2, max = 5000, message = "Escreva algo sobre a nota")
    private String text;

    @NotNull(message = "Game ID is required")
    private Long userId;

    public NoteDTO() {

    }

    public NoteDTO(String title, String text, Long userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;
    }

    // Getters e Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}