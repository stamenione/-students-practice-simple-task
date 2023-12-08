package com.example.MealOrder.service;

public interface EmailService {

    void sendEmail(String to, String title, String text);
}
