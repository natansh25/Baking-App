package com.example.ahmet.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmet.bakingapp.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {
    public static final String INTENT_RECIPE_ID = "recipe_id";

    private boolean isTabletLandscape;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        isTabletLandscape = findViewById(R.id.view_fragment_container) != null;

        int recipeId = getIntent().getIntExtra(INTENT_RECIPE_ID, 0);

        SelectStepFragment selectStepFragment = SelectStepFragment.forRecipe(recipeId);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.select_fragment_container, selectStepFragment)
                .commit();

        if (isTabletLandscape) {
            ViewStepFragment viewStepFragment = new ViewStepFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.view_fragment_container, viewStepFragment)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public void onClickWatch() {
        if (!isTabletLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("recipe")
                    .replace(R.id.select_fragment_container, new ViewStepFragment())
                    .commit();
        }
    }
}
