package com.anupam.testecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anupam.testecommerce.ProductActivity;

import com.anupam.testecommerce.R;
import com.anupam.testecommerce.modals.Product;

import java.util.ArrayList;


public class CategoriesItemAdaptor extends BaseAdapter {
    Context context;
    ArrayList<Product> products;

    public CategoriesItemAdaptor(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        Product product = products.get(i);
        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.horizontal_item, null, false);
        }

        TextView textView = (TextView) itemView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        textView.setText(product.getName());
        imageView.setImageDrawable(HorizontalCategoriesAdaptor.getDrawable(product.getName()));
        itemView.setTag(product.getId());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("productId", id);
                context.startActivity(intent);
            }
        });
        return itemView;
    }
}
