package com.example.newsapp;

public class News {

    String url;
    private String date;
    private String title;
    private String author;
    private String section;

    public News(String url, String date, String title, String author, String section) {
        this.url = url;
        this.date = date;
        this.title = title;
        this.author = author;
        this.section = section;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "News{" +
                "url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
