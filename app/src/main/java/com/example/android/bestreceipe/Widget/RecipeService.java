package com.example.android.bestreceipe.Widget;

/**
 * Created by sunilkumar on 28/12/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bestreceipe.MainActivity;
import com.example.android.bestreceipe.Model.Ingredients;
import com.example.android.bestreceipe.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class RecipeService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeServiceFactory(this);
    }

    private class RecipeServiceFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context;
        private ArrayList<Ingredients> mIngredientsList;

        RecipeServiceFactory(Context context) {
            this.context = context;
            mIngredientsList = new ArrayList<>();
            mIngredientsList.add(new Ingredients(4, "BLAH", "ROLL"));
        }


        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String json = preferences.getString(MainActivity.SHARED_PREFS_KEY, "");
            if (!json.equals("")) {
                Gson gson = new Gson();
                mIngredientsList = gson.fromJson(json, new TypeToken<ArrayList<Ingredients>>() {
                }.getType());
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mIngredientsList != null) {
                return mIngredientsList.size();
            } else return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_item);
            rv.setTextViewText(R.id.appwidget_text_name, String.valueOf(mIngredientsList.get(i).getIngredient()));
            rv.setTextViewText(R.id.appwidget_text_measure, String.valueOf(mIngredientsList.get(i).getMeasure()));
            rv.setTextViewText(R.id.appwidget_text_quantity, String.valueOf(mIngredientsList.get(i).getQuantity()));
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}