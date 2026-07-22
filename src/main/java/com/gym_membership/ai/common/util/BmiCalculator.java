package com.gym_membership.ai.common.util;


public final class BmiCalculator {

    private BmiCalculator() {
        // Prevent instantiation
    }

    public static double calculate(double heightCm, double weightKg) {

        if (heightCm <= 0 || weightKg <= 0) {
            throw new IllegalArgumentException("Height and weight must be greater than zero.");
        }

        double heightInMeters = heightCm / 100.0;

        return weightKg / (heightInMeters * heightInMeters);
    }

    public static String getCategory(double bmi) {

        if (bmi < 18.5) {
            return "UNDERWEIGHT";
        }

        if (bmi < 25) {
            return "NORMAL";
        }

        if (bmi < 30) {
            return "OVERWEIGHT";
        }

        return "OBESE";
    }
}