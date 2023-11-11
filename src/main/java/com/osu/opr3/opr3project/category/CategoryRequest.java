package com.osu.opr3.opr3project.category;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank
    private String name;
    private String description;
    @ColorFormat(checkLightness = true)
    private String color;
}
