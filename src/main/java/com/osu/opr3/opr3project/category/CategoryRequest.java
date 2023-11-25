package com.osu.opr3.opr3project.category;

import com.osu.opr3.opr3project.validation.ColorFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank
    private String name;
    private String description;
    @ColorFormat(checkLightness = true)
    private String color;
}
