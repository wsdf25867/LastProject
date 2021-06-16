package com.example.levelus;

public class UserAccount implements Comparable<UserAccount>{
    private String idToken;
    private String emailId;
    private String password;

    private String name;
    private String age;
    private String favorite;
    private String local;

    private String level;
//    private int rank;

    public String getLevel() {
        return level;
    }

    public void setLevel(){
        this.level = "0";
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

//    public int getRank() { return rank; }

//    public void setRank(int rank) { this.rank = rank; }

    public UserAccount(){

    }

    @Override
    public int compareTo(UserAccount o) {
        if (Integer.parseInt(this.level) < Integer.parseInt(o.getLevel())) {
            return 1;
        }
        else if (Integer.parseInt(this.level) > Integer.parseInt(o.getLevel())) {
            return -1;
        }
        return 0;
    }
}
