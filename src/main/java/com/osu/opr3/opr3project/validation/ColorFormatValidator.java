package com.osu.opr3.opr3project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.awt.*;

public class ColorFormatValidator implements ConstraintValidator<ColorFormat, String> {

    private boolean checkLightness;

    @Override
    public void initialize(ColorFormat constraintAnnotation) {
        checkLightness = constraintAnnotation.checkLightness();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (!value.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
            return false;
        }

        try {
            Color color = Color.decode(value);
            float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

            if (checkLightness) {
                float valueComponent = hsv[2];

                return !(valueComponent <= 0.5f);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}