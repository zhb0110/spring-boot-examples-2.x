package com.example.restservice;

public class Greeting {

    Long id;

    String content;

    public Greeting(long incrementAndGet, String format) {
        this.id = incrementAndGet;
        this.content = format;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
