package com.example.ekztry6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable

{

    public Integer idRecipe;
    public String fullRecipe;
    public String calory;
    public String timeGot;
    public String image;


    public Recipe(Integer idRecipe, String fullRecipe, String calory, String timeGot, String image) {
        this.idRecipe = idRecipe;
        this.fullRecipe = fullRecipe;
        this.calory = calory;
        this.timeGot = timeGot;
        this.image = image;
    }

    public Integer getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getFullRecipe() {
        return fullRecipe;
    }

    public void setFullRecipe(String fullRecipe) {
        this.fullRecipe = fullRecipe;
    }

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    public String getTimeGot() {
        return timeGot;
    }

    public void setTimeGot(String timeGot) {
        this.timeGot = timeGot;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    protected Recipe(Parcel in) {

            idRecipe = in.readInt();

        fullRecipe = in.readString();
        calory = in.readString();
        timeGot = in.readString();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

            dest.writeInt(idRecipe);

        dest.writeString(fullRecipe);
        dest.writeString(calory);
        dest.writeString(timeGot);
        dest.writeString(image);
    }
}