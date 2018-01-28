package br.ufrpe.projetao.rotatour;

import android.graphics.Bitmap;

/**
 * Created by Victor Alexandre on 1/27/2018.
 */

public class Friend {

    private String friendName, friendUsername, friendEmail, friendPhoto;




    public Friend(String friendName, String friendUsername, String friendEmail, String friendPhoto) {
        this.friendName = friendName;
        this.friendUsername = friendUsername;

        this.friendEmail = friendEmail;
        this.friendPhoto = friendPhoto;

    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getFrienddEmail() {
        return friendEmail;
    }

    public void setFrienddEmail(String frienddEmail) {
        this.friendEmail = frienddEmail;
    }


    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public void setFriendPhoto(String friendPhoto) {
        this.friendPhoto = friendPhoto;
    }

    public String getFriendPhoto() {
        return friendPhoto;
    }
}
