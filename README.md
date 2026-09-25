# Smart Pantry Manager

## Overview

Smart Pantry Manager is a Java-based Android application developed for the Mobile App Development 700 practical assignment.

The purpose of the application is to help users reduce food waste by keeping track of ingredients available in their pantry and suggesting recipes that can be prepared using only those ingredients.

A recipe is only suggested when every required ingredient is available in the user's pantry in a sufficient quantity.

## Main Features

- Add pantry ingredients
- View pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- Store ingredient quantity and unit
- Store an optional expiry date
- Display pantry items using a RecyclerView
- Store recipes in the application database
- Preload 20 recipes
- Suggest recipes based on pantry contents
- Strict recipe matching
- Display recipe details
- Display recipe ingredients and preparation instructions
- Settings screen
- Input validation
- Persistent local data storage
- Persistent settings using SharedPreferences
- Expiry-soon and expired ingredient warnings
- Calender date picker for ingredient expiry dates

## Strict Recipe Matching

The Smart Pantry Manager follows a strict matching rule.

A recipe is only shown in the Suggested Recipes screen when all the ingredients required by the recipe are available in the pantry in at least the required quantities.

For example, if a recipe requires:

- 2 eggs
- 1 tomato
- 2 slices of bread

the recipe will only be suggested if the user has all three ingredients in the required quantities.

Recipes with missing ingredients are not included in the main Suggested Recipes list.

The application also normalises basic ingredient names to reduce simple matching problems such as singular and plural variations.

## Database

The application uses SQLite through `SQLiteOpenHelper`.

SQLite was selected because:

- it works locally on the Android device
- no internet connection is required
- it is suitable for a small mobile application
- it supports persistent data storage
- it supports full CRUD operations
- it is straightforward to integrate with Java and Android Studio

The database contains tables for:

- Pantry ingredients
- Recipes
- Recipe ingredients

The pantry table stores:

- Ingredient ID
- Ingredient name
- Quantity
- Unit
- Expiry date

The recipe table stores:

- Recipe ID
- Recipe name
- Preparation instructions

The recipe ingredient table stores:

- Recipe ingredient ID
- Recipe ID
- Ingredient name
- Required quantity
- Unit

## CRUD Functionality

The application supports full CRUD functionality for pantry ingredients.

### Create

Users can add a new ingredient using the Add Ingredient screen.

### Read

All pantry ingredients are retrieved from SQLite and displayed using a RecyclerView.

### Update

Users can edit an existing pantry ingredient and update its details.

### Delete

Users can delete pantry ingredients using the Delete button. A confirmation dialog is displayed before deletion.

## Application Screens

The application currently includes the following screens:

1. My Pantry
2. Add/Edit Ingredient
3. Suggested Recipes
4. Recipe Detail
5. Settings

Navigation between screens is implemented using Android Intents.

## Technology Used

- Android Studio
- Java
- XML
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Custom RecyclerView Adapters
- Android Intents
- Git
- GitHub

## Project Structure

Important Java classes include:

- `MainActivity.java`
- `AddEditIngredientActivity.java`
- `SuggestedRecipeActivity.java`
- `RecipeDetailActivity.java`
- `SettingsActivity.java`
- `DatabaseHelper.java`
- `Ingredient.java`
- `IngredientAdapter.java`
- `Recipe.java`
- `RecipeIngredient.java`
- `RecipeAdapter.java`

## Setup and Run Instructions

1. Install Android Studio.
2. Clone or download this repository.
3. Open the project in Android Studio.
4. Allow Gradle to sync and download any required dependencies.
5. Make sure an Android SDK is installed.
6. Use Android API 34 or another compatible Android version.
7. Create or select an Android virtual device.
8. Build the project.
9. Run the application on the emulator or a compatible Android device.

To build from the command line on Windows:

```powershell
.\gradlew.bat assembleDebug

A successful build should display:
BUILD SUCCESSFUL

Development Status
Current implemented functionality includes:
- Pantry management
- SQLite database
- CRUD operations
- Input validation
- Recipe database
- 20 seeded recipes
- Strict recipe matching logic
- Suggested Recipes screen
- Recipe Detail screen
- Settings screen
Further testing and UI improvements will continue during development.
Assignment
Module: Mobile App Development 700
Project: Smart Pantry Manager
Application Type: Java Android Application
