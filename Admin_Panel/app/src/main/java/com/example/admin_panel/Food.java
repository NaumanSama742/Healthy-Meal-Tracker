package com.example.admin_panel;

public class Food {
    private String id;
    private String name;
    private double calories;
    private double carbs;
    private double fat;
    private double protein;
    private String serving_size;

    public Food() {}

    // Getters and setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public String getServing_size() { return serving_size; }
    public void setServing_size(String serving_size) { this.serving_size = serving_size; }
}
