package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    // =========================
    // INGREDIENT TABLE
    // =========================

    private static final String TABLE_INGREDIENTS = "ingredients";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_UNIT = "unit";
    private static final String COL_EXPIRY = "expiry_date";

    // =========================
    // RECIPE TABLE
    // =========================

    private static final String TABLE_RECIPES = "recipes";

    private static final String RECIPE_COL_ID = "id";
    private static final String RECIPE_COL_NAME = "name";
    private static final String RECIPE_COL_INSTRUCTIONS = "instructions";

    // =========================
    // RECIPE INGREDIENT TABLE
    // =========================

    private static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    private static final String RI_COL_ID = "id";
    private static final String RI_COL_RECIPE_ID = "recipe_id";
    private static final String RI_COL_INGREDIENT_NAME =
            "ingredient_name";
    private static final String RI_COL_QUANTITY = "quantity";
    private static final String RI_COL_UNIT = "unit";

    // =========================
    // CONSTRUCTOR
    // =========================

    public DatabaseHelper(Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    // =========================
    // CREATE DATABASE
    // =========================

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COL_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        COL_NAME +
                        " TEXT NOT NULL, " +

                        COL_QUANTITY +
                        " REAL NOT NULL, " +

                        COL_UNIT +
                        " TEXT NOT NULL, " +

                        COL_EXPIRY +
                        " TEXT" +
                        ")";

        db.execSQL(createIngredientsTable);

        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        RECIPE_COL_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        RECIPE_COL_NAME +
                        " TEXT NOT NULL, " +

                        RECIPE_COL_INSTRUCTIONS +
                        " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable =
                "CREATE TABLE " +
                        TABLE_RECIPE_INGREDIENTS + " (" +

                        RI_COL_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        RI_COL_RECIPE_ID +
                        " INTEGER NOT NULL, " +

                        RI_COL_INGREDIENT_NAME +
                        " TEXT NOT NULL, " +

                        RI_COL_QUANTITY +
                        " REAL NOT NULL, " +

                        RI_COL_UNIT +
                        " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);

        seedRecipes(db);
    }

    // =========================
    // UPGRADE DATABASE
    // =========================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_RECIPE_INGREDIENTS
        );

        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_RECIPES
        );

        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_INGREDIENTS
        );

        onCreate(db);
    }

    // ============================================================
    // PANTRY CRUD
    // ============================================================

    // =========================
    // CREATE INGREDIENT
    // =========================

    public long addIngredient(
            Ingredient ingredient
    ) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COL_NAME,
                ingredient.getName()
        );

        values.put(
                COL_QUANTITY,
                ingredient.getQuantity()
        );

        values.put(
                COL_UNIT,
                ingredient.getUnit()
        );

        values.put(
                COL_EXPIRY,
                ingredient.getExpiryDate()
        );

        long result = db.insert(
                TABLE_INGREDIENTS,
                null,
                values
        );

        db.close();

        return result;
    }

    // =========================
    // READ INGREDIENTS
    // =========================

    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredientList =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        COL_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COL_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        COL_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COL_UNIT
                                )
                        );

                String expiry =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        COL_EXPIRY
                                )
                        );

                Ingredient ingredient =
                        new Ingredient(
                                id,
                                name,
                                quantity,
                                unit,
                                expiry
                        );

                ingredientList.add(
                        ingredient
                );

            } while (
                    cursor.moveToNext()
            );
        }

        cursor.close();
        db.close();

        return ingredientList;
    }

    // =========================
    // UPDATE INGREDIENT
    // =========================

    public int updateIngredient(
            Ingredient ingredient
    ) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                COL_NAME,
                ingredient.getName()
        );

        values.put(
                COL_QUANTITY,
                ingredient.getQuantity()
        );

        values.put(
                COL_UNIT,
                ingredient.getUnit()
        );

        values.put(
                COL_EXPIRY,
                ingredient.getExpiryDate()
        );

        int result = db.update(
                TABLE_INGREDIENTS,
                values,
                COL_ID + "=?",
                new String[]{
                        String.valueOf(
                                ingredient.getId()
                        )
                }
        );

        db.close();

        return result;
    }

    // =========================
    // DELETE INGREDIENT
    // =========================

    public int deleteIngredient(
            int id
    ) {

        SQLiteDatabase db =
                getWritableDatabase();

        int result = db.delete(
                TABLE_INGREDIENTS,
                COL_ID + "=?",
                new String[]{
                        String.valueOf(id)
                }
        );

        db.close();

        return result;
    }

    // ============================================================
    // RECIPE SEEDING
    // ============================================================

    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String instructions,
            String[][] ingredients
    ) {

        ContentValues recipeValues =
                new ContentValues();

        recipeValues.put(
                RECIPE_COL_NAME,
                name
        );

        recipeValues.put(
                RECIPE_COL_INSTRUCTIONS,
                instructions
        );

        long recipeId = db.insert(
                TABLE_RECIPES,
                null,
                recipeValues
        );

        for (
                String[] ingredient :
                ingredients
        ) {

            ContentValues ingredientValues =
                    new ContentValues();

            ingredientValues.put(
                    RI_COL_RECIPE_ID,
                    recipeId
            );

            ingredientValues.put(
                    RI_COL_INGREDIENT_NAME,
                    ingredient[0]
            );

            ingredientValues.put(
                    RI_COL_QUANTITY,
                    Double.parseDouble(
                            ingredient[1]
                    )
            );

            ingredientValues.put(
                    RI_COL_UNIT,
                    ingredient[2]
            );

            db.insert(
                    TABLE_RECIPE_INGREDIENTS,
                    null,
                    ingredientValues
            );
        }
    }

    private void seedRecipes(
            SQLiteDatabase db
    ) {

        // 1
        addRecipe(
                db,
                "Scrambled Eggs",
                "Beat the eggs. Heat a pan and cook the eggs until set.",
                new String[][]{
                        {
                                "egg",
                                "2",
                                "pcs"
                        }
                }
        );

        // 2
        addRecipe(
                db,
                "Tomato Omelette",
                "Beat the eggs, chop the tomato and cook together in a pan.",
                new String[][]{
                        {
                                "egg",
                                "2",
                                "pcs"
                        },
                        {
                                "tomato",
                                "1",
                                "pcs"
                        }
                }
        );

        // 3
        addRecipe(
                db,
                "Toast",
                "Toast the bread until golden brown.",
                new String[][]{
                        {
                                "bread",
                                "2",
                                "slices"
                        }
                }
        );

        // 4
        addRecipe(
                db,
                "Cheese Toast",
                "Place cheese on bread and toast until the cheese melts.",
                new String[][]{
                        {
                                "bread",
                                "2",
                                "slices"
                        },
                        {
                                "cheese",
                                "50",
                                "g"
                        }
                }
        );

        // 5
        addRecipe(
                db,
                "Banana Smoothie",
                "Blend banana and milk until smooth.",
                new String[][]{
                        {
                                "banana",
                                "1",
                                "pcs"
                        },
                        {
                                "milk",
                                "250",
                                "ml"
                        }
                }
        );

        // 6
        addRecipe(
                db,
                "Tomato Sandwich",
                "Slice the tomato and place it between slices of bread.",
                new String[][]{
                        {
                                "bread",
                                "2",
                                "slices"
                        },
                        {
                                "tomato",
                                "1",
                                "pcs"
                        }
                }
        );

        // 7
        addRecipe(
                db,
                "Egg Sandwich",
                "Cook the eggs and place them between slices of bread.",
                new String[][]{
                        {
                                "egg",
                                "2",
                                "pcs"
                        },
                        {
                                "bread",
                                "2",
                                "slices"
                        }
                }
        );

        // 8
        addRecipe(
                db,
                "Cheese Sandwich",
                "Place cheese between two slices of bread.",
                new String[][]{
                        {
                                "bread",
                                "2",
                                "slices"
                        },
                        {
                                "cheese",
                                "50",
                                "g"
                        }
                }
        );

        // 9
        addRecipe(
                db,
                "Boiled Eggs",
                "Boil the eggs until cooked through.",
                new String[][]{
                        {
                                "egg",
                                "2",
                                "pcs"
                        }
                }
        );

        // 10
        addRecipe(
                db,
                "Rice and Tomato",
                "Cook the rice and serve with chopped tomato.",
                new String[][]{
                        {
                                "rice",
                                "100",
                                "g"
                        },
                        {
                                "tomato",
                                "1",
                                "pcs"
                        }
                }
        );

        // 11
        addRecipe(
                db,
                "Rice and Egg",
                "Cook the rice and serve with cooked egg.",
                new String[][]{
                        {
                                "rice",
                                "100",
                                "g"
                        },
                        {
                                "egg",
                                "1",
                                "pcs"
                        }
                }
        );

        // 12
        addRecipe(
                db,
                "Banana Toast",
                "Slice banana and place it on toasted bread.",
                new String[][]{
                        {
                                "banana",
                                "1",
                                "pcs"
                        },
                        {
                                "bread",
                                "2",
                                "slices"
                        }
                }
        );

        // 13
        addRecipe(
                db,
                "Milk and Cereal",
                "Pour cereal into a bowl and add milk.",
                new String[][]{
                        {
                                "cereal",
                                "50",
                                "g"
                        },
                        {
                                "milk",
                                "200",
                                "ml"
                        }
                }
        );

        // 14
        addRecipe(
                db,
                "Cheese Omelette",
                "Beat eggs, add cheese and cook in a pan.",
                new String[][]{
                        {
                                "egg",
                                "2",
                                "pcs"
                        },
                        {
                                "cheese",
                                "50",
                                "g"
                        }
                }
        );

        // 15
        addRecipe(
                db,
                "Tomato Rice",
                "Cook rice and mix with chopped tomato.",
                new String[][]{
                        {
                                "rice",
                                "100",
                                "g"
                        },
                        {
                                "tomato",
                                "2",
                                "pcs"
                        }
                }
        );

        // 16
        addRecipe(
                db,
                "Banana Milk",
                "Blend banana and milk together.",
                new String[][]{
                        {
                                "banana",
                                "1",
                                "pcs"
                        },
                        {
                                "milk",
                                "200",
                                "ml"
                        }
                }
        );

        // 17
        addRecipe(
                db,
                "Egg and Tomato Toast",
                "Cook egg and tomato and serve on toasted bread.",
                new String[][]{
                        {
                                "egg",
                                "1",
                                "pcs"
                        },
                        {
                                "tomato",
                                "1",
                                "pcs"
                        },
                        {
                                "bread",
                                "2",
                                "slices"
                        }
                }
        );

        // 18
        addRecipe(
                db,
                "Cheese and Tomato Sandwich",
                "Place tomato and cheese between slices of bread.",
                new String[][]{
                        {
                                "bread",
                                "2",
                                "slices"
                        },
                        {
                                "cheese",
                                "50",
                                "g"
                        },
                        {
                                "tomato",
                                "1",
                                "pcs"
                        }
                }
        );

        // 19
        addRecipe(
                db,
                "Egg Fried Rice",
                "Cook the rice, then fry it together with egg.",
                new String[][]{
                        {
                                "rice",
                                "100",
                                "g"
                        },
                        {
                                "egg",
                                "2",
                                "pcs"
                        }
                }
        );

        // 20
        addRecipe(
                db,
                "Banana Cereal Bowl",
                "Add sliced banana to cereal and pour milk over it.",
                new String[][]{
                        {
                                "banana",
                                "1",
                                "pcs"
                        },
                        {
                                "cereal",
                                "50",
                                "g"
                        },
                        {
                                "milk",
                                "200",
                                "ml"
                        }
                }
        );
    }
    // =========================
// GET ALL RECIPES
// =========================

    public List<Recipe> getAllRecipes() {

        List<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                RECIPE_COL_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                RECIPE_COL_ID
                        )
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                RECIPE_COL_NAME
                        )
                );

                String instructions = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                RECIPE_COL_INSTRUCTIONS
                        )
                );

                recipeList.add(
                        new Recipe(
                                id,
                                name,
                                instructions
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipeList;
    }
    // =========================
// GET INGREDIENTS FOR RECIPE
// =========================

    public List<RecipeIngredient> getRecipeIngredients(
            int recipeId
    ) {

        List<RecipeIngredient> list =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                RI_COL_RECIPE_ID + "=?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                RI_COL_ID
                        )
                );

                String ingredientName =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        RI_COL_INGREDIENT_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        RI_COL_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        RI_COL_UNIT
                                )
                        );

                list.add(
                        new RecipeIngredient(
                                id,
                                recipeId,
                                ingredientName,
                                quantity,
                                unit
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
    // =========================
// STRICT RECIPE MATCHING
// =========================

    public List<Recipe> getSuggestedRecipes() {

        List<Recipe> suggestedRecipes =
                new ArrayList<>();

        List<Recipe> allRecipes =
                getAllRecipes();

        List<Ingredient> pantryItems =
                getAllIngredients();

        for (Recipe recipe : allRecipes) {

            List<RecipeIngredient> requiredIngredients =
                    getRecipeIngredients(
                            recipe.getId()
                    );

            boolean canMakeRecipe = true;

            for (
                    RecipeIngredient required :
                    requiredIngredients
            ) {

                boolean ingredientFound = false;

                for (
                        Ingredient pantry :
                        pantryItems
                ) {

                    String pantryName =
                            normalizeIngredientName(
                                    pantry.getName()
                            );

                    String requiredName =
                            normalizeIngredientName(
                                    required.getIngredientName()
                            );

                    if (
                            pantryName.equals(
                                    requiredName
                            )
                    ) {

                        if (
                                pantry.getQuantity()
                                        >=
                                        required.getQuantity()
                        ) {

                            ingredientFound = true;
                            break;
                        }
                    }
                }

                if (!ingredientFound) {

                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {

                suggestedRecipes.add(
                        recipe
                );
            }
        }

        return suggestedRecipes;
    }
    // =========================
// NORMALISE INGREDIENT NAMES
// =========================

    private String normalizeIngredientName(
            String name
    ) {

        if (name == null) {
            return "";
        }

        String normalized =
                name.trim()
                        .toLowerCase();

        if (
                normalized.endsWith("es")
                        &&
                        normalized.length() > 3
        ) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 2
                    );

        } else if (
                normalized.endsWith("s")
                        &&
                        normalized.length() > 2
        ) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 1
                    );
        }

        return normalized;
    }
}