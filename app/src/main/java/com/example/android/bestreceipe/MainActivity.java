package com.example.android.bestreceipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bestreceipe.Model.Ingredients;
import com.example.android.bestreceipe.Model.Recipes;
import com.example.android.bestreceipe.Model.Steps;
import com.example.android.bestreceipe.NetworkUtils.NetworkAsyncListener;
import com.example.android.bestreceipe.NetworkUtils.NetworkAsyncTask;
import com.example.android.bestreceipe.Widget.RecipeWidgetProvider;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NetworkAsyncListener {

    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";
    private static final String MENU_SELECTED = "selected";
    private static final String MENU_NUMBER = "menu_number";
    private static final String LIST_KEY = "list_key";
    private static final String TAB_BOOLEAN = "tab_layout";
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.tv_recipe_name)
    TextView mRecipeName;
    @BindView(R.id.tv_serving_count)
    TextView mRecipeSizeCount;
    @BindView(R.id.tv_step_count)
    TextView mRecipeStepCount;
    @BindView(R.id.instructions_tv)
    TextView mInstructionsTv;
    @BindView(R.id.iv_recipe_image)
    ImageView mRecipePlaceHolder;
    @BindView(R.id.cv_recipe_list)
    CardView mRecipeCardView;
    @BindView(R.id.empty_layout)
    ConstraintLayout mEmptyLayout;
    @BindView(R.id.bake_img_logo)
    ImageView mLogoView;
    ConnectivityManager connManager;
    NetworkInfo networkInfo;
    ArrayList<Recipes> mRecipesList;
    ArrayList<Ingredients> mIngredientsList;
    ArrayList<Steps> mStepsList;
    StepsFragment mStepFragment;
    IngredientsFragment mIngredientFragment;
    private String idName;
    private int arrayPosition;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mEmptyLayout.setVisibility(View.VISIBLE);
        mRecipeCardView.setVisibility(View.GONE);
        mInstructionsTv.setTypeface(EasyFonts.droidSerifBold(this));

        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null)
            networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            NetworkAsyncTask httpRequest = new NetworkAsyncTask(this);
            httpRequest.execute();
        } else {
            Snackbar snackbar = Snackbar.make(mNavigationView, getString(R.string.check_connection), Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            snackbar.show();
        }
        if (savedInstanceState != null) {
            mTwoPane = savedInstanceState.getBoolean(TAB_BOOLEAN);
            mRecipesList = savedInstanceState.getParcelableArrayList(LIST_KEY);
            idName = savedInstanceState.getString(MENU_SELECTED);
            arrayPosition = savedInstanceState.getInt(MENU_NUMBER);
            if (idName.matches(getString(R.string.nutella_pie))) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setRecipeData(arrayPosition);
                setupIngredientFragment();
                setupStepsFragment();
            } else if (idName.matches(getString(R.string.brownies))) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setRecipeData(arrayPosition);
                setupIngredientFragment();
                setupStepsFragment();
            } else if (idName.matches(getString(R.string.yellow_cake))) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setRecipeData(arrayPosition);
                setupIngredientFragment();
                setupStepsFragment();
            } else if (idName.matches(getString(R.string.cheesecake))) {
                mEmptyLayout.setVisibility(View.GONE);
                mRecipeCardView.setVisibility(View.VISIBLE);
                setRecipeData(arrayPosition);
                setupIngredientFragment();
                setupStepsFragment();
            }
        } else {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mRecipeCardView.setVisibility(View.GONE);
        }
        mTwoPane = findViewById(R.id.recipe_detail_container) != null;
        mLogoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mNavigationView.setNavigationItemSelectedListener(this);
        mRecipeName.setTypeface(EasyFonts.droidSerifBold(this));
        mRecipeSizeCount.setTypeface(EasyFonts.droidSerifBold(this));
        mRecipeStepCount.setTypeface(EasyFonts.droidSerifBold(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_NUMBER, arrayPosition);
        outState.putString(MENU_SELECTED, idName);
        outState.putParcelableArrayList(LIST_KEY, mRecipesList);
        outState.putBoolean(TAB_BOOLEAN, mTwoPane);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        idName = (String) item.getTitle();
        if (idName.matches(getString(R.string.nutella_pie))) {
            arrayPosition = 0;
            setRecipeData(arrayPosition);
            setupIngredientFragment();
            setupStepsFragment();
        } else if (idName.matches(getString(R.string.brownies))) {
            arrayPosition = 1;
            setRecipeData(arrayPosition);
            setupIngredientFragment();
            setupStepsFragment();
        } else if (idName.matches(getString(R.string.yellow_cake))) {
            arrayPosition = 2;
            setRecipeData(arrayPosition);
            setupIngredientFragment();
            setupStepsFragment();
        } else if (idName.matches(getString(R.string.cheesecake))) {
            arrayPosition = 3;
            setRecipeData(arrayPosition);
            setupIngredientFragment();
            setupStepsFragment();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void returnRecipeList(ArrayList<Recipes> recipesList) {
        mRecipesList = recipesList;
        Menu navDrawerMenu = mNavigationView.getMenu();
        for (Recipes recipes : mRecipesList) {
            navDrawerMenu.add(recipes.getName());
        }
    }

    private void setRecipeData(int arrayPositionIndex) {
        mEmptyLayout.setVisibility(View.GONE);
        mRecipeCardView.setVisibility(View.VISIBLE);
        mToolBar.setTitle(mRecipesList.get(arrayPositionIndex).getName());
        if (mRecipesList != null) {
            if (mRecipesList.get(arrayPositionIndex).getImage().matches("")) {
                mRecipePlaceHolder.setImageResource(R.drawable.pie_place);
            } else {
                Picasso.with(this).load(mRecipesList.get(arrayPositionIndex).getImage()).into(mRecipePlaceHolder);
            }
            mRecipeName.setText(mRecipesList.get(arrayPositionIndex).getName());
            mRecipeSizeCount.setText(String.valueOf(mRecipesList.get(arrayPositionIndex).getServings()));
            mRecipeStepCount.setText(String.valueOf(mRecipesList.get(arrayPositionIndex).getSteps().size()));
            mIngredientsList = mRecipesList.get(arrayPositionIndex).getIngredients();
            mStepsList = mRecipesList.get(arrayPositionIndex).getSteps();
            long quantity = (long) mIngredientsList.get(arrayPositionIndex).getQuantity();
            String ingredient = mIngredientsList.get(arrayPositionIndex).getIngredient();
            String measure = mIngredientsList.get(arrayPositionIndex).getMeasure();
            makeData(quantity, measure, ingredient);
            sendBroadcast();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupIngredientFragment() {
        Bundle argumentsForIngredients = new Bundle();
        argumentsForIngredients.putParcelableArrayList("arraylist", mIngredientsList);
        if (mIngredientsList != null) {
            mIngredientFragment = new IngredientsFragment();
            mIngredientFragment.setArguments(argumentsForIngredients);
            getSupportFragmentManager().beginTransaction().replace(R.id.ingredients_fragment_container, mIngredientFragment).commit();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupStepsFragment() {
        Bundle argumentsForSteps = new Bundle();
        argumentsForSteps.putParcelableArrayList("stepslist", mStepsList);
        argumentsForSteps.putBoolean("tabLayout", mTwoPane);
        if (mStepsList != null) {
            mStepFragment = new StepsFragment();
            mStepFragment.setArguments(argumentsForSteps);
            getSupportFragmentManager().beginTransaction().replace(R.id.steps_fragment_container, mStepFragment).commit();
        } else {
            Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void makeData(long quantity, String measure, String ingredient) {
        mIngredientsList.add(new Ingredients(quantity, measure, ingredient));
        Gson gson = new Gson();
        String json = gson.toJson(mIngredientsList);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SHARED_PREFS_KEY, json).apply();
    }

    private void sendBroadcast() {
        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE\"");
        sendBroadcast(intent);
    }
}