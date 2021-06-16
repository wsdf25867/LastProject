package com.example.levelus;

public class QuestlogInfo {
    private String quest_num;
    private String rating;
    private String category;
    private String title_ko;
    private String accepted_date;
    private String finished_date;
    private String achievement;
    private String period;
    private String difficulty;


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

    public String getAchievement() { return achievement; }

    public void setAchievement(String achievement) { this.achievement = achievement; }

    public String getDifficulty() { return difficulty; }

    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getPeriod() { return period; }

    public void setPeriod(String period) { this.period = period; }




}
