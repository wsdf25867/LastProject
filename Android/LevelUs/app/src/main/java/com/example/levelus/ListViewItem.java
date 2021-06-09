package com.example.levelus;

import android.graphics.drawable.Drawable;

public class ListViewItem { // 완료 퀘스트 리스트 beans
    private Drawable iconDrawable;
    private String rating;
    private String category;
    private String title_ko;
    private String accepted_date;
    private String finished_date;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle_ko() {
        return title_ko;
    }

    public void setTitle_ko(String title_ko) {
        this.title_ko = title_ko;
    }

    public String getAccepted_date() {
        return accepted_date;
    }

    public void setAccepted_date(String accepted_date) {
        this.accepted_date = accepted_date;
    }

    public String getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(String finished_date) {
        this.finished_date = finished_date;
    }
}
