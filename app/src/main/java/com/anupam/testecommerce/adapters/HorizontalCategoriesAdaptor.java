package com.anupam.testecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.anupam.testecommerce.CategoryItemsActivity;
import com.anupam.testecommerce.R;
import com.anupam.testecommerce.modals.Category;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class HorizontalCategoriesAdaptor extends RecyclerView.Adapter<HorizontalCategoriesAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Category> categories;

    public HorizontalCategoriesAdaptor(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public static Drawable getDrawable(String name) {
        name = name.trim();
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
        String character;

        character = ("" + (name.charAt(0))).toUpperCase();

        Drawable drawable = TextDrawable.builder()
                .beginConfig().textColor(Color.WHITE)
                .width(40)
                .height(40)
                .endConfig()
                .buildRound(character, color);
        return drawable;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.textView.setText(category.getName());
        holder.textView.setTag(category.getId());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                Intent intent = new Intent(context, CategoryItemsActivity.class);
                intent.putExtra("categoryId", id);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        Drawable drawable = getDrawable(category.getName());
        holder.imageView.setImageDrawable(drawable);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.textView.callOnClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
