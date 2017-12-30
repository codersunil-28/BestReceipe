package com.example.android.bestreceipe.Model;

/**
 * Created by sunilkumar on 28/12/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Recipes implements Parcelable {

    public final static Parcelable.Creator<Recipes> CREATOR = new Creator<Recipes>() {
        public Recipes createFromParcel(Parcel in) {
            Recipes instance = new Recipes();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.ingredients, (com.example.android.bestreceipe.Model.Ingredients.class.getClassLoader()));
            in.readList(instance.steps, (com.example.android.bestreceipe.Model.Steps.class.getClassLoader()));
            instance.servings = ((int) in.readValue((int.class.getClassLoader())));
            instance.image = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Recipes[] newArray(int size) {
            return (new Recipes[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private ArrayList<Ingredients> ingredients = null;
    @SerializedName("steps")
    @Expose
    private ArrayList<Steps> steps = null;
    @SerializedName("servings")
    @Expose
    private int servings;
    @SerializedName("image")
    @Expose
    private String image;

    private Recipes() {
    }

    public Recipes(int id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }
}