package com.example.smartpantrymanager;

import java.util.Locale;

/**
 * Small utility class used by the strict recipe-matching logic.
 * It normalises common singular/plural ingredient names and converts
 * compatible units to a shared base unit before quantities are compared.
 */
public final class MatchingUtils {

    private MatchingUtils() {
        // Utility class - no instances.
    }

    public static String normalizeIngredientName(String name) {
        if (name == null) {
            return "";
        }

        String value = name.trim().toLowerCase(Locale.ROOT);

        // Common real-world plural forms used in this app.
        if (value.endsWith("atoes")) {       // tomatoes -> tomato, potatoes -> potato
            value = value.substring(0, value.length() - 2);
        } else if (value.endsWith("ies") && value.length() > 3) {
            value = value.substring(0, value.length() - 3) + "y";
        } else if (value.endsWith("s") && !value.endsWith("ss") && value.length() > 3) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

    public static UnitAmount toBaseUnit(double quantity, String unit) {
        String value = unit == null ? "" : unit.trim().toLowerCase(Locale.ROOT);

        switch (value) {
            case "kg":
            case "kilogram":
            case "kilograms":
                return new UnitAmount(quantity * 1000.0, "mass");

            case "g":
            case "gram":
            case "grams":
                return new UnitAmount(quantity, "mass");

            case "mg":
                return new UnitAmount(quantity / 1000.0, "mass");

            case "l":
            case "litre":
            case "litres":
            case "liter":
            case "liters":
                return new UnitAmount(quantity * 1000.0, "volume");

            case "ml":
            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
                return new UnitAmount(quantity, "volume");

            case "pcs":
            case "pc":
            case "piece":
            case "pieces":
            case "slice":
            case "slices":
            case "unit":
            case "units":
                return new UnitAmount(quantity, "count");

            default:
                // Unknown units are kept separate so unlike units cannot match accidentally.
                return new UnitAmount(quantity, "custom:" + value);
        }
    }

    public static final class UnitAmount {
        private final double quantity;
        private final String family;

        public UnitAmount(double quantity, String family) {
            this.quantity = quantity;
            this.family = family;
        }

        public double getQuantity() {
            return quantity;
        }

        public String getFamily() {
            return family;
        }
    }
}
