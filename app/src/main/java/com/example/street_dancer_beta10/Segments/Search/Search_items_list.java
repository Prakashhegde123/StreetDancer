package com.example.street_dancer_beta10.Segments.Search;


public class Search_items_list {
    private String Name;
    private int photo;

    public Search_items_list() {
    }
    public Search_items_list(String name, int photo) {
        Name = name;
        this.photo = photo;
    }

    public String getName() {
        return Name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}