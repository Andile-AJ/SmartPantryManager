package com.example.smartpantrymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView tvEmptyPantry;
    private Button btnAddIngredient;
    private DatabaseHelper databaseHelper;
    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        databaseHelper = new DatabaseHelper(this);
        ingredientList = new ArrayList<>();

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        ingredientAdapter = new IngredientAdapter(
                this,
                ingredientList,
                new IngredientAdapter.OnIngredientActionListener() {
                    @Override
                    public void onEdit(Ingredient ingredient) {
                        Intent intent = new Intent(
                                MainActivity.this,
                                AddEditIngredientActivity.class
                        );

                        intent.putExtra("ingredient_id", ingredient.getId());
                        intent.putExtra("ingredient_name", ingredient.getName());
                        intent.putExtra("ingredient_quantity", ingredient.getQuantity());
                        intent.putExtra("ingredient_unit", ingredient.getUnit());
                        intent.putExtra("ingredient_expiry", ingredient.getExpiryDate());
                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(Ingredient ingredient) {
                        confirmDelete(ingredient);
                    }
                }
        );

        recyclerPantry.setAdapter(ingredientAdapter);

        btnAddIngredient.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        AddEditIngredientActivity.class
                ))
        );

        // Required working navigation element.
        bottomNavigation.setSelectedItemId(R.id.nav_pantry);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {
                return true;
            }

            if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(
                        MainActivity.this,
                        SuggestedRecipeActivity.class
                ));
                return true;
            }

            if (itemId == R.id.nav_settings) {
                startActivity(new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                ));
                return true;
            }

            return false;
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
        ingredientList.addAll(databaseHelper.getAllIngredients());
        ingredientAdapter.notifyDataSetChanged();

        boolean isEmpty = ingredientList.isEmpty();
        tvEmptyPantry.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerPantry.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    private void confirmDelete(Ingredient ingredient) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete " + ingredient.getName() + "?")
                .setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.deleteIngredient(ingredient.getId());
                                loadIngredients();
                            }
                        }
                )
                .setNegativeButton("Cancel", null)
                .show();
    }
}
