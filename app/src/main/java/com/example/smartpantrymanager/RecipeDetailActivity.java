package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvRecipeDetailName;
    private TextView tvRecipeIngredients;
    private TextView tvRecipeMethod;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        tvRecipeDetailName =
                findViewById(R.id.tvRecipeDetailName);

        tvRecipeIngredients =
                findViewById(R.id.tvRecipeIngredients);

        tvRecipeMethod =
                findViewById(R.id.tvRecipeMethod);

        databaseHelper =
                new DatabaseHelper(this);

        int recipeId =
                getIntent().getIntExtra(
                        "recipe_id",
                        -1
                );

        String recipeName =
                getIntent().getStringExtra(
                        "recipe_name"
                );

        String instructions =
                getIntent().getStringExtra(
                        "recipe_instructions"
                );

        tvRecipeDetailName.setText(recipeName);

        tvRecipeMethod.setText(
                instructions
        );

        loadIngredients(recipeId);
    }

    private void loadIngredients(
            int recipeId
    ) {

        List<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(
                        recipeId
                );

        StringBuilder builder =
                new StringBuilder();

        for (
                RecipeIngredient ingredient :
                ingredients
        ) {

            builder.append("• ")
                    .append(
                            ingredient.getIngredientName()
                    )
                    .append(" - ")
                    .append(
                            ingredient.getQuantity()
                    )
                    .append(" ")
                    .append(
                            ingredient.getUnit()
                    )
                    .append("\n");
        }

        tvRecipeIngredients.setText(
                builder.toString()
        );
    }
}