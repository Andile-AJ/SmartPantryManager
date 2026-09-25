package com.example.smartpantrymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    private DatabaseHelper databaseHelper;
    private IngredientAdapter ingredientAdapter;

    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPantry =
                findViewById(R.id.recyclerPantry);

        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        btnSettings =
                findViewById(R.id.btnSettings);

        databaseHelper =
                new DatabaseHelper(this);

        ingredientList =
                new ArrayList<>();

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        ingredientAdapter =
                new IngredientAdapter(
                        this,
                        ingredientList,
                        new IngredientAdapter.OnIngredientActionListener() {

                            @Override
                            public void onEdit(Ingredient ingredient) {

                                Intent intent =
                                        new Intent(
                                                MainActivity.this,
                                                AddEditIngredientActivity.class
                                        );

                                intent.putExtra(
                                        "ingredient_id",
                                        ingredient.getId()
                                );

                                intent.putExtra(
                                        "ingredient_name",
                                        ingredient.getName()
                                );

                                intent.putExtra(
                                        "ingredient_quantity",
                                        ingredient.getQuantity()
                                );

                                intent.putExtra(
                                        "ingredient_unit",
                                        ingredient.getUnit()
                                );

                                intent.putExtra(
                                        "ingredient_expiry",
                                        ingredient.getExpiryDate()
                                );

                                startActivity(intent);
                            }

                            @Override
                            public void onDelete(Ingredient ingredient) {

                                confirmDelete(
                                        ingredient
                                );
                            }
                        }
                );

        recyclerPantry.setAdapter(
                ingredientAdapter
        );

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            AddEditIngredientActivity.class
                    );

            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipeActivity.class
                    );

            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });

        loadIngredients();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadIngredients();
    }

    private void loadIngredients() {

        ingredientList.clear();

        ingredientList.addAll(
                databaseHelper.getAllIngredients()
        );

        ingredientAdapter.notifyDataSetChanged();
    }

    private void confirmDelete(
            Ingredient ingredient
    ) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage(
                        "Are you sure you want to delete "
                                + ingredient.getName()
                                + "?"
                )

                .setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which
                            ) {

                                databaseHelper.deleteIngredient(
                                        ingredient.getId()
                                );

                                loadIngredients();
                            }
                        }
                )

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .show();
    }
}