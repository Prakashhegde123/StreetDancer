package com.example.street_dancer_beta10.Segments.Profile;

public class    ProfileFollowersFollowingsModel {

    private String id;
    private String UserName;
    private String Name;
    private int UserProfile;

    public ProfileFollowersFollowingsModel(String id,String UserName, String Name, int UserProfile) {
        this.id=id;
        this.UserName = UserName;
        this.Name = Name;
        this.UserProfile = UserProfile;
    }

    public ProfileFollowersFollowingsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(int UserProfile) {
        this.UserProfile = UserProfile;
    }
}