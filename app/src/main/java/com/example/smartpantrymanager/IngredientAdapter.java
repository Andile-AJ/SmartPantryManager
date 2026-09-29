package com.example.smartpantrymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final Context context;
    private final List<Ingredient> ingredientList;
    private final OnIngredientActionListener listener;

    private final SharedPreferences sharedPreferences;

    private static final String PREFS_NAME =
            "SmartPantrySettings";

    private static final String KEY_EXPIRY_ALERTS =
            "expiry_alerts_enabled";

    public interface OnIngredientActionListener {

        void onEdit(Ingredient ingredient);

        void onDelete(Ingredient ingredient);
    }

    public IngredientAdapter(
            Context context,
            List<Ingredient> ingredientList,
            OnIngredientActionListener listener
    ) {

        this.context = context;
        this.ingredientList = ingredientList;
        this.listener = listener;

        sharedPreferences =
                context.getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                );
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view =
                LayoutInflater
                        .from(context)
                        .inflate(
                                R.layout.item_ingredient,
                                parent,
                                false
                        );

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder,
            int position
    ) {

        Ingredient ingredient =
                ingredientList.get(position);

        holder.tvIngredientName.setText(
                ingredient.getName()
        );

        holder.tvIngredientQuantity.setText(
                "Quantity: "
                        + ingredient.getQuantity()
                        + " "
                        + ingredient.getUnit()
        );

        showExpiryInformation(
                holder,
                ingredient
        );

        holder.btnEdit.setOnClickListener(
                v -> listener.onEdit(ingredient)
        );

        holder.btnDelete.setOnClickListener(
                v -> listener.onDelete(ingredient)
        );
    }

    private void showExpiryInformation(
            IngredientViewHolder holder,
            Ingredient ingredient
    ) {

        String expiryDate =
                ingredient.getExpiryDate();

        if (
                expiryDate == null
                        || expiryDate.trim().isEmpty()
        ) {

            holder.tvIngredientExpiry.setText(
                    "Expiry: Not specified"
            );

            return;
        }

        boolean alertsEnabled =
                sharedPreferences.getBoolean(
                        KEY_EXPIRY_ALERTS,
                        false
                );

        if (!alertsEnabled) {

            holder.tvIngredientExpiry.setText(
                    "Expiry: " + expiryDate
            );

            return;
        }

        long daysRemaining =
                calculateDaysRemaining(
                        expiryDate
                );

        if (daysRemaining == Long.MIN_VALUE) {

            holder.tvIngredientExpiry.setText(
                    "Expiry: "
                            + expiryDate
                            + " (Check date format)"
            );

        } else if (daysRemaining < 0) {

            holder.tvIngredientExpiry.setText(
                    "Expiry: "
                            + expiryDate
                            + " ⚠ EXPIRED"
            );

        } else if (daysRemaining == 0) {

            holder.tvIngredientExpiry.setText(
                    "Expiry: "
                            + expiryDate
                            + " ⚠ Expires today"
            );

        } else if (daysRemaining <= 3) {

            holder.tvIngredientExpiry.setText(
                    "Expiry: "
                            + expiryDate
                            + " ⚠ Expiring soon"
            );

        } else {

            holder.tvIngredientExpiry.setText(
                    "Expiry: " + expiryDate
            );
        }
    }

    private long calculateDaysRemaining(
            String expiryDate
    ) {

        Date expiry =
                parseDate(expiryDate);

        if (expiry == null) {
            return Long.MIN_VALUE;
        }

        Calendar todayCalendar =
                Calendar.getInstance();

        todayCalendar.set(
                Calendar.HOUR_OF_DAY,
                0
        );

        todayCalendar.set(
                Calendar.MINUTE,
                0
        );

        todayCalendar.set(
                Calendar.SECOND,
                0
        );

        todayCalendar.set(
                Calendar.MILLISECOND,
                0
        );

        Calendar expiryCalendar =
                Calendar.getInstance();

        expiryCalendar.setTime(expiry);

        expiryCalendar.set(
                Calendar.HOUR_OF_DAY,
                0
        );

        expiryCalendar.set(
                Calendar.MINUTE,
                0
        );

        expiryCalendar.set(
                Calendar.SECOND,
                0
        );

        expiryCalendar.set(
                Calendar.MILLISECOND,
                0
        );

        long difference =
                expiryCalendar.getTimeInMillis()
                        - todayCalendar.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays(
                difference
        );
    }

    private Date parseDate(
            String dateText
    ) {

        String[] supportedFormats = {
                "dd/MM/yyyy",
                "yyyy-MM-dd"
        };

        for (String format : supportedFormats) {

            SimpleDateFormat dateFormat =
                    new SimpleDateFormat(
                            format,
                            Locale.getDefault()
                    );

            dateFormat.setLenient(false);

            try {

                return dateFormat.parse(
                        dateText.trim()
                );

            } catch (ParseException ignored) {

            }
        }

        return null;
    }

    @Override
    public int getItemCount() {

        return ingredientList.size();
    }

    static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientQuantity;
        TextView tvIngredientExpiry;

        Button btnEdit;
        Button btnDelete;

        public IngredientViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            tvIngredientName =
                    itemView.findViewById(
                            R.id.tvIngredientName
                    );

            tvIngredientQuantity =
                    itemView.findViewById(
                            R.id.tvIngredientQuantity
                    );

            tvIngredientExpiry =
                    itemView.findViewById(
                            R.id.tvIngredientExpiry
                    );

            btnEdit =
                    itemView.findViewById(
                            R.id.btnEdit
                    );

            btnDelete =
                    itemView.findViewById(
                            R.id.btnDelete
                    );
        }
    }
}