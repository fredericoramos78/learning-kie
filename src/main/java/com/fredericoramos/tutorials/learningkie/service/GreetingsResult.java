package com.fredericoramos.tutorials.learningkie.service;


public class GreetingsResult {
    private String message;
    
    public GreetingsResult() {}
    
    public boolean foundAGreetingMessage() { return this.message != null; }
    
    public void setGreetingMessage(String newMessage) {
        this.message = newMessage;
    }
    
    @Override
    public String toString() {
        return this.message;
    }
}
