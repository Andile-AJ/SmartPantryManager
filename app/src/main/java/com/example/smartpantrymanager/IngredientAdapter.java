package com.example.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final Context context;
    private final List<Ingredient> ingredientList;
    private final OnIngredientActionListener listener;

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
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder,
            int position
    ) {

        Ingredient ingredient = ingredientList.get(position);

        holder.tvIngredientName.setText(ingredient.getName());

        holder.tvIngredientQuantity.setText(
                "Quantity: "
                        + ingredient.getQuantity()
                        + " "
                        + ingredient.getUnit()
        );

        String expiryDate = ingredient.getExpiryDate();

        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            holder.tvIngredientExpiry.setText("Expiry: Not specified");
        } else {
            holder.tvIngredientExpiry.setText(
                    "Expiry: " + expiryDate
            );
        }

        holder.btnEdit.setOnClickListener(v ->
                listener.onEdit(ingredient)
        );

        holder.btnDelete.setOnClickListener(v ->
                listener.onDelete(ingredient)
        );
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