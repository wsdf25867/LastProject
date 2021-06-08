package com.example.levelus;

import java.util.Date;

public class QuestlogInfo {
    private String quest_num;
    private String rating;
    private String category;
    private String title_ko;
    private Date accepted_date;
    private Date finished_date;

    public QuestlogInfo(){

    }
    public String getQuest_num() {
        return quest_num;
    }

    public void setQuest_num(String quest_num) {
        this.quest_num = quest_num;
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

    public Date getAccepted_date() {
        return accepted_date;
    }

    public void setAccepted_date(Date accepted_date) {
        this.accepted_date = accepted_date;
    }

    public Date getFinished_date() {
        return finished_date;
    }

    public void setFinished_date(Date finished_date) {
        this.finished_date = finished_date;
    }


}
