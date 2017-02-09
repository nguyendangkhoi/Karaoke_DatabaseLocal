package com.it.darkluke.karaokeapp.Model;

/**
 * Created by DELL on 07/02/2017.
 */

public class Song  {
    private long id;
    private String key;
    private String name;
    private String author;
    private boolean liked;

    public Song(long id, String key, String name, String author, boolean liked) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.author = author;
        this.liked = liked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Song() {

    }
}
