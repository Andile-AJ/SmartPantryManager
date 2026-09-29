# Smart Pantry Manager

Smart Pantry Manager is a Java Android application developed for the Mobile App Development 700 practical assignment.

The purpose of the application is to help users reduce food waste by keeping track of ingredients they already have at home and suggesting recipes that can be prepared using only those available ingredients.

The app follows a strict recipe-matching rule: a recipe is only suggested when every required ingredient is available in the pantry in sufficient quantity.

## Main Features

- Add new pantry ingredients
- View all pantry ingredients
- Edit existing pantry ingredients
- Delete pantry ingredients
- Store ingredient name, quantity, unit, and optional expiry date
- Display pantry items using a RecyclerView
- Suggest recipes based on current pantry contents
- Strict recipe matching
- Recipe detail screen with ingredients and preparation steps
- Settings screen
- Input validation
- Local data persistence
- Bottom navigation between main sections
- Feedback when no recipes match the pantry

## Technology Used

- Android Studio
- Java
- XML layouts
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Custom Adapters
- Intents
- SharedPreferences

## Database Choice

This application uses SQLite through `SQLiteOpenHelper`.

SQLite was selected because it is suitable for a small offline Android application and does not require an internet connection or external server.

It also allows pantry information to remain stored after the application is closed and reopened.

The database stores pantry items and recipe information.

## CRUD Functionality

The application supports full CRUD operations for pantry ingredients:

- Create: users can add new ingredients
- Read: users can view pantry ingredients in a list
- Update: users can edit existing ingredients
- Delete: users can remove ingredients

## Strict Recipe Matching

The strict-matching logic checks every ingredient required by a recipe.

A recipe is only shown in the Suggested Recipes screen when:

- every required ingredient exists in the pantry
- the available quantity is enough
- basic unit differences such as grams and kilograms are handled
- simple ingredient-name differences such as singular and plural forms are handled

If even one required ingredient is missing, the recipe is not displayed.

## Screens

The application includes the following main screens:

1. Pantry List
2. Add/Edit Ingredient
3. Suggested Recipes
4. Recipe Detail
5. Settings

## Project Structure

Important classes include:

- `MainActivity.java`
- `AddEditIngredientActivity.java`
- `SuggestedRecipesActivity.java`
- `RecipeDetailActivity.java`
- `SettingsActivity.java`
- `DatabaseHelper.java`
- `IngredientAdapter.java`
- `RecipeAdapter.java`
- `MatchingUtils.java`

## How to Run the Project

1. Download or clone the repository.
2. Open Android Studio.
3. Select **Open**.
4. Select the Smart Pantry Manager project folder.
5. Allow Gradle to sync.
6. Click **Build > Assemble Project** to confirm the project builds successfully.
7. Start an Android emulator or connect a physical Android device.
8. Click **Run** to launch the application.

## Low-RAM Computer Note

On computers with limited memory, such as 4 GB RAM, the Android emulator may be slow.

If necessary:

- close other applications
- use a lightweight virtual device
- reduce emulator RAM
- build the project before launching the emulator

## Example Strict-Matching Test

Add the following pantry items:

- Bread: 2 pieces
- Tomato: 1 piece

A recipe requiring bread and tomato should appear in Suggested Recipes.

If the tomato is deleted, that recipe should disappear because the strict-matching rule is no longer satisfied.

## Purpose

The main goal of Smart Pantry Manager is to reduce unnecessary food waste by helping users make better use of ingredients they already have available.