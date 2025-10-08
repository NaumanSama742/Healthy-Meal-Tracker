package com.example.admin_panel;

public class MealIntake {
    private String id;
    private String mealName;
    private String date;
    private String time;
    private double calories;
    private double carbs;
    private double fat;
    private double protein;

    public MealIntake() {}

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMealName() { return mealName; }
    public void setMealName(String mealName) { this.mealName = mealName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }
}
