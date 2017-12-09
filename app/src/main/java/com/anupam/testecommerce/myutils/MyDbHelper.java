package com.anupam.testecommerce.myutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anupam.testecommerce.modals.Category;
import com.anupam.testecommerce.modals.Product;
import com.anupam.testecommerce.modals.ProductVariant;
import com.anupam.testecommerce.modals.Tax;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "ecommerce";
    private static final String TABLE_MOST_VIEW_PRODUCTS = "most_view_products";
    private static final String COLUMN_NAME_KEY = "id";
    private static final String COLUMN_NAME_COUNT = "viewCount";
    private static final String TABLE_MOST_ORDERED_PRODUCTS = "most_ordered_products";
    private static final String TABLE_MOST_SHARED_PRODUCTS = "most_shared_products";
    private static final String SQL_CREATE_TABLE_MVP =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MOST_VIEW_PRODUCTS +
                    " ( " +
                    COLUMN_NAME_KEY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_COUNT + " INTEGER NOT NULL, " +
                    " PRIMARY KEY ( " + COLUMN_NAME_KEY + " ) " +
                    " )";
    private static final String SQL_CREATE_TABLE_MOP =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MOST_ORDERED_PRODUCTS +
                    " ( " +
                    COLUMN_NAME_KEY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_COUNT + " INTEGER NOT NULL, " +
                    " PRIMARY KEY ( " + COLUMN_NAME_KEY + " ) " +
                    " )";
    private static final String SQL_CREATE_TABLE_MSP =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MOST_SHARED_PRODUCTS +
                    " ( " +
                    COLUMN_NAME_KEY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_COUNT + " INTEGER NOT NULL, " +
                    " PRIMARY KEY ( " + COLUMN_NAME_KEY + " ) " +
                    " )";

    private final String TABLE_CATEGORIES = "categories";
    private final String COLUMN_NAME_CATEGORIES_ID = "id";
    private final String COLUMN_NAME_CATEGORIES_NAME = "name";
    private final String COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES = "child";

    private final String TABLE_PRODUCTS = "products";
    private final String COLUMN_NAME_PRODUCTS_ID = "id";
    private final String COLUMN_NAME_PRODUCTS_NAME = "name";
    private final String COLUMN_NAME_PRODUCTS_DATE_ADDED = "date_added";
    private final String COLUMN_NAME_PRODUCTS_TAX = "tax";
    private final String COLUMN_NAME_PRODUCTS_CATEGORIES_ID = "categories_id";
    private final String TABLE_VARIANTS = "variants";
    private final String COLUMN_NAME_VARIANTS_ID = "id";
    private final String COLUMN_NAME_VARIANTS_COLOR = "color";
    private final String COLUMN_NAME_VARIANTS_SIZE = "size";
    private final String COLUMN_NAME_VARIANTS_PRICE = "price";
    private final String COLUMN_NAME_VARIANTS_PRODUCT_ID = "productId";
    private final String SQL_CREATE_TABLE_CATEGORIES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES +
                    " ( " +
                    COLUMN_NAME_CATEGORIES_ID + " INTEGER NOT NULL, " +
                    COLUMN_NAME_CATEGORIES_NAME + " TEXT NOT NULL, " +
                    COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES + " TEXT , " +
                    " PRIMARY KEY ( " + COLUMN_NAME_VARIANTS_ID + " ) " +
                    " )";
    private final String SQL_CREATE_TABLE_PRODUCTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS +
                    " ( " +
                    COLUMN_NAME_PRODUCTS_ID + " INTEGER NOT NULL, " +
                    COLUMN_NAME_PRODUCTS_NAME + " TEXT NOT NULL, " +
                    COLUMN_NAME_PRODUCTS_DATE_ADDED + " TEXT , " +
                    COLUMN_NAME_PRODUCTS_TAX + " TEXT NOT NULL, " +
                    COLUMN_NAME_PRODUCTS_CATEGORIES_ID + " TEXT NOT NULL, " +
                    " PRIMARY KEY ( " + COLUMN_NAME_PRODUCTS_ID + " ) " +
                    " )";
    private final String CREATE_TABLE_VARIANTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_VARIANTS +
                    " ( " +
                    COLUMN_NAME_VARIANTS_ID + " INTEGER NOT NULL, " +
                    COLUMN_NAME_VARIANTS_COLOR + " TEXT NOT NULL, " +
                    COLUMN_NAME_VARIANTS_SIZE + " INTEGER NOT NULL, " +
                    COLUMN_NAME_VARIANTS_PRICE + " INTEGER NOT NULL, " +
                    COLUMN_NAME_VARIANTS_PRODUCT_ID + " INTEGER NOT NULL, " +
                    " PRIMARY KEY ( " + COLUMN_NAME_VARIANTS_ID + " ) " +
                    " )";
    private final String SQL_DROP_TABLE_CATEGORIES =
            "DROP TABLE IF EXISTS " + TABLE_CATEGORIES;

    private final String SQL_DROP_TABLE_PRODUCTS =
            "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;

    private final String SQL_DROP_TABLE_VARIANTS =
            "DROP TABLE IF EXISTS " + TABLE_VARIANTS;

    private final String SQL_DROP_TABLE_MSP =
            "DROP TABLE IF EXISTS " + TABLE_MOST_SHARED_PRODUCTS;

    private final String SQL_DROP_TABLE_MOP =
            "DROP TABLE IF EXISTS " + TABLE_MOST_ORDERED_PRODUCTS;

    private final String SQL_DROP_TABLE_MVP =
            "DROP TABLE IF EXISTS " + TABLE_MOST_VIEW_PRODUCTS;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOP);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MVP);
        sqLiteDatabase.execSQL(CREATE_TABLE_VARIANTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MSP);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PRODUCTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CATEGORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // resetDatabase();
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOP);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MVP);
        sqLiteDatabase.execSQL(CREATE_TABLE_VARIANTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MSP);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PRODUCTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        //sqLiteDatabase.close();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public void resetDatabase() {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL(SQL_DROP_TABLE_CATEGORIES);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_PRODUCTS);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_VARIANTS);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_MOP);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_MVP);
        sqLiteDatabase.execSQL(SQL_DROP_TABLE_MSP);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.close();
    }

    public boolean insertCategory(Category category, JSONArray productJsonArray) {

        SQLiteDatabase sqLiteDatabase = null;
        boolean isSuccessful = false;
        try {
            sqLiteDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_CATEGORIES_ID, category.getId());
            values.put(COLUMN_NAME_CATEGORIES_NAME, category.getName());
            values.put(COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES, category.getChild().toString());

            long newRowId;
            newRowId = sqLiteDatabase.insert(
                    TABLE_CATEGORIES,
                    null,
                    values);

            isSuccessful = newRowId != -1;
        } catch (Exception e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            try {
                if (sqLiteDatabase != null)
                    sqLiteDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isSuccessful) {

            for (int i = 0; i < productJsonArray.length(); i++) {
                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject jsonObject = productJsonArray.getJSONObject(i);
                    Log.e("sdsf", jsonObject.toString());
                    Product product = gson.fromJson(jsonObject.toString(), Product.class);
                    insertProduct(product, category.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return isSuccessful;
    }

    public boolean insertProduct(Product product, int categoryId) {

        SQLiteDatabase sqLiteDatabase = null;
        boolean isSuccessful = false;
        try {
            sqLiteDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_PRODUCTS_ID, product.getId());
            values.put(COLUMN_NAME_PRODUCTS_NAME, product.getName());
            values.put(COLUMN_NAME_PRODUCTS_DATE_ADDED, product.getDate_added());
            values.put(COLUMN_NAME_PRODUCTS_TAX, product.getTax().toString());
            values.put(COLUMN_NAME_PRODUCTS_CATEGORIES_ID, categoryId);

            long newRowId;
            newRowId = sqLiteDatabase.insert(
                    TABLE_PRODUCTS,
                    null,
                    values);

            isSuccessful = newRowId != -1;
        } catch (Exception e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            try {
                if (sqLiteDatabase != null)
                    sqLiteDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isSuccessful) {
            ArrayList<ProductVariant> variants = product.getVariants();
            for (ProductVariant variant : variants) {
                insertProductVariant(variant, product.getId());
            }
        }
        return isSuccessful;
    }

    public boolean insertProductVariant(ProductVariant variants, int productId) {

        SQLiteDatabase sqLiteDatabase = null;
        boolean isSuccessful = false;
        try {
            sqLiteDatabase = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_VARIANTS_ID, variants.getId());
            values.put(COLUMN_NAME_VARIANTS_SIZE, variants.getSize());
            values.put(COLUMN_NAME_VARIANTS_COLOR, variants.getColor());
            values.put(COLUMN_NAME_VARIANTS_PRICE, variants.getPrice());
            values.put(COLUMN_NAME_VARIANTS_PRODUCT_ID, productId);

            long newRowId;
            newRowId = sqLiteDatabase.insert(
                    TABLE_VARIANTS,
                    null,
                    values);

            isSuccessful = newRowId != -1;
        } catch (Exception e) {
            isSuccessful = false;
            e.printStackTrace();
        } finally {
            try {
                if (sqLiteDatabase != null)
                    sqLiteDatabase.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSuccessful;
    }

    public ArrayList<Category> getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_CATEGORIES_ID,
                COLUMN_NAME_CATEGORIES_NAME,
                COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES
        };

        String sortOrder =
                COLUMN_NAME_CATEGORIES_ID + " DESC";

        Cursor cursor = db.query(
                TABLE_CATEGORIES,                  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<Category> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CATEGORIES_ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORIES_NAME)));
                try {
                    JSONArray jsonArray = new JSONArray(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES)));
                    category.setChild(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                categories.add(category);

            } while (cursor.moveToNext());
        }

        try {
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return categories;
    }

    public Category getCategoryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_CATEGORIES_ID,
                COLUMN_NAME_CATEGORIES_NAME,
                COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES
        };
        String condition = COLUMN_NAME_PRODUCTS_ID + " ==  '" + id + "'";
        String sortOrder =
                COLUMN_NAME_CATEGORIES_ID + " DESC";

        Cursor cursor = db.query(
                TABLE_CATEGORIES,                  // The table to query
                projection,                               // The columns to return
                condition,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (cursor.moveToFirst()) {

            Category category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_CATEGORIES_ID)));
            category.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORIES_NAME)));
            try {
                JSONArray jsonArray = new JSONArray(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CATEGORIES_CHILD_CATEGORIES)));
                category.setChild(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return category;


        }

        try {
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return new Category();
    }

    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_PRODUCTS_ID,
                COLUMN_NAME_PRODUCTS_NAME,
                COLUMN_NAME_PRODUCTS_DATE_ADDED,
                COLUMN_NAME_PRODUCTS_TAX
        };
        String condition = COLUMN_NAME_PRODUCTS_ID + " ==  '" + productId + "'";
        String sortOrder =
                COLUMN_NAME_PRODUCTS_ID + " DESC";

        Cursor cursor = db.query(
                TABLE_PRODUCTS,                  // The table to query
                projection,                               // The columns to return
                condition,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        try {
            if (cursor.moveToFirst()) {
                Product product = new Product();
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_ID));
                product.setId(id);
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_NAME)));
                Gson gson = new GsonBuilder().create();
                Tax tax = gson.fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_TAX)), Tax.class);
                product.setTax(tax);
                product.setDate_added(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_DATE_ADDED)));
                product.setVariants(getVariantsByProductId(id));
                return product;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return new Product();
    }

    public ArrayList<Product> getProductsByCategoryId(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_PRODUCTS_ID,
                COLUMN_NAME_PRODUCTS_NAME,
                COLUMN_NAME_PRODUCTS_DATE_ADDED,
                COLUMN_NAME_PRODUCTS_TAX
        };
        String condition = COLUMN_NAME_PRODUCTS_CATEGORIES_ID + " ==  '" + categoryId + "'";
        String sortOrder =
                COLUMN_NAME_PRODUCTS_ID + " DESC";

        Cursor cursor = db.query(
                TABLE_PRODUCTS,                  // The table to query
                projection,                               // The columns to return
                condition,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<Product> products = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_ID));
                product.setId(id);
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_NAME)));
                Gson gson = new GsonBuilder().create();
                Tax tax = gson.fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_TAX)), Tax.class);
                product.setTax(tax);
                product.setDate_added(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PRODUCTS_DATE_ADDED)));

                product.setVariants(getVariantsByProductId(id));

                products.add(product);

            } while (cursor.moveToNext());
        }

        try {
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return products;
    }

    public ArrayList<ProductVariant> getVariantsByProductId(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_VARIANTS_ID,
                COLUMN_NAME_VARIANTS_COLOR,
                COLUMN_NAME_VARIANTS_PRICE,
                COLUMN_NAME_VARIANTS_SIZE
        };
        String condition = COLUMN_NAME_VARIANTS_PRODUCT_ID + " ==  '" + productId + "'";
        String sortOrder =
                COLUMN_NAME_VARIANTS_PRODUCT_ID + " DESC";

        Cursor cursor = db.query(
                TABLE_VARIANTS,                  // The table to query
                projection,                               // The columns to return
                condition,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<ProductVariant> variants = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ProductVariant product = new ProductVariant();
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_VARIANTS_ID));
                product.setId(id);
                product.setSize(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_VARIANTS_SIZE)));
                product.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_VARIANTS_PRICE)));
                product.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_VARIANTS_COLOR)));

                variants.add(product);

            } while (cursor.moveToNext());
        }

        try {
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return variants;

    }

}