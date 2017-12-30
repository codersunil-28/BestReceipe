package com.example.android.bestreceipe.NetworkUtils;

/**
 * Created by sunilkumar on 28/12/17.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.bestreceipe.Model.Ingredients;
import com.example.android.bestreceipe.Model.Recipes;
import com.example.android.bestreceipe.Model.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkAsyncTask extends AsyncTask<Void, Void, ArrayList<Recipes>> {

    private static final String LOG_TAG = NetworkAsyncTask.class.getSimpleName();
    private NetworkAsyncListener networkAsyncListener;

    public NetworkAsyncTask(NetworkAsyncListener listener) {
        networkAsyncListener = listener;
    }

    @Override
    protected ArrayList<Recipes> doInBackground(Void... voids) {
        ArrayList<Recipes> recipeList = new ArrayList<>();
        JSONArray recipeArray = NetworkParser.getRecipeData();
        try {
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject currentRecipe = recipeArray.getJSONObject(i);
                int recipeId = currentRecipe.getInt("id");
                String recipeName = currentRecipe.getString("name");
                JSONArray ingredientsList = currentRecipe.getJSONArray("ingredients");
                ArrayList<Ingredients> ingredientData = new ArrayList<>();
                for (int j = 0; j < ingredientsList.length(); j++) {
                    JSONObject currentIngredient = ingredientsList.getJSONObject(j);
                    long ingredientQuantity = currentIngredient.getLong("quantity");
                    String ingredientMeasure = currentIngredient.getString("measure");
                    String ingredientName = currentIngredient.getString("ingredient");
                    Ingredients ingredients = new Ingredients(ingredientQuantity, ingredientMeasure, ingredientName);
                    ingredientData.add(ingredients);
                }
                JSONArray stepsList = currentRecipe.getJSONArray("steps");
                ArrayList<Steps> stepsData = new ArrayList<>();
                for (int k = 0; k < stepsList.length(); k++) {
                    JSONObject currentStep = stepsList.getJSONObject(k);
                    int stepId = currentStep.getInt("id");
                    String stepShortDescription = currentStep.getString("shortDescription");
                    String stepLongDescription = currentStep.getString("description");
                    String stepVideoUrl = currentStep.getString("videoURL");
                    String stepImageUrl = currentStep.getString("thumbnailURL");
                    Steps steps = new Steps(stepId, stepShortDescription, stepLongDescription, stepVideoUrl, stepImageUrl);
                    stepsData.add(steps);
                }
                int recipeServings = currentRecipe.getInt("servings");
                String recipeImage = currentRecipe.getString("image");
                Recipes recipes = new Recipes(recipeId, recipeName, ingredientData, stepsData, recipeServings, recipeImage);
                recipeList.add(recipes);
                Log.v(LOG_TAG, "JSON DETAILS " + currentRecipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "JSON EXCEPTION IS " + e);
        }
        return recipeList;
    }

    @Override
    protected void onPostExecute(ArrayList<Recipes> recipesList) {
        super.onPostExecute(recipesList);
        networkAsyncListener.returnRecipeList(recipesList);
    }
}
