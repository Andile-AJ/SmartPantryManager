package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerSuggestedRecipes;
    private TextView tvNoRecipes;

    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerSuggestedRecipes =
                findViewById(R.id.recyclerSuggestedRecipes);

        tvNoRecipes =
                findViewById(R.id.tvNoRecipes);

        databaseHelper =
                new DatabaseHelper(this);

        recipeList =
                new ArrayList<>();

        recyclerSuggestedRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recipeAdapter =
                new RecipeAdapter(
                        recipeList,
                        recipe -> {

                            Intent intent =
                                    new Intent(
                                            SuggestedRecipeActivity.this,
                                            RecipeDetailActivity.class
                                    );

                            intent.putExtra(
                                    "recipe_id",
                                    recipe.getId()
                            );

                            intent.putExtra(
                                    "recipe_name",
                                    recipe.getName()
                            );

                            intent.putExtra(
                                    "recipe_instructions",
                                    recipe.getInstructions()
                            );

                            startActivity(intent);
                        }
                );

        recyclerSuggestedRecipes.setAdapter(
                recipeAdapter
        );

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        recipeList.clear();

        recipeList.addAll(
                databaseHelper.getSuggestedRecipes()
        );

        recipeAdapter.notifyDataSetChanged();

        if (recipeList.isEmpty()) {

            tvNoRecipes.setVisibility(
                    View.VISIBLE
            );

            recyclerSuggestedRecipes.setVisibility(
                    View.GONE
            );

        } else {

            tvNoRecipes.setVisibility(
                    View.GONE
            );

            recyclerSuggestedRecipes.setVisibility(
                    View.VISIBLE
            );
        }
    }
}