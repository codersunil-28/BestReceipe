package com.example.android.bestreceipe.NetworkUtils;

/**
 * Created by sunilkumar on 28/12/17.
 */


import com.example.android.bestreceipe.Model.Recipes;

import java.util.ArrayList;

public interface NetworkAsyncListener {
    void returnRecipeList(ArrayList<Recipes> recipesList);
}
