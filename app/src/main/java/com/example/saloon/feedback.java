package com.example.saloon;

public class feedback {
    String id;
    String title;
    String  comment;




    public feedback(,String id,String title, String comment,) {
        this.title = title;
        this.comment = comment;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
