package com.example.billingproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.billingproject.model.Billing;

import java.util.ArrayList;

public class BillingBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATABASE_NAME="billingBase.db";

    public BillingBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //The table is going to be created locally (in the mobile phone)
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BillingDbSchema.BillingTable.NAME + "(" + BillingDbSchema.BillingTable.Cols.CLIENT_ID + " INTEGER PRIMARY KEY, " +
                BillingDbSchema.BillingTable.Cols.CLIENT_NAME + " TEXT, " +
                BillingDbSchema.BillingTable.Cols.PRODUCT_NAME + " TEXT, " +
                BillingDbSchema.BillingTable.Cols.PRODUCT_PRICE + " REAL, " +
                BillingDbSchema.BillingTable.Cols.PRODUCT_QUANTITY + " INTEGER) "
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues getContentValues(Billing billing){

        ContentValues values = new ContentValues();

        values.put(BillingDbSchema.BillingTable.Cols.CLIENT_ID, billing.getClient_Id());
        values.put(BillingDbSchema.BillingTable.Cols.CLIENT_NAME, billing.getClient_Name());
        values.put(BillingDbSchema.BillingTable.Cols.PRODUCT_NAME, billing.getProduct_Name());
        values.put(BillingDbSchema.BillingTable.Cols.PRODUCT_PRICE, billing.getPrd_Price());
        values.put(BillingDbSchema.BillingTable.Cols.PRODUCT_QUANTITY, billing.getPrd_Qty());

        return values;
    }

    //CreateBilling
    public void createBilling(Billing billing){

        //writing data into database
        SQLiteDatabase db = this.getWritableDatabase();

        //creating values from ContentValues (method)
        ContentValues values = getContentValues(billing);

        //Insert values into table row
        db.insert(BillingDbSchema.BillingTable.NAME, null, values);

        //Close the database
        //db.close();
    }

    //To read (view) from the table created
    public ArrayList<Billing> readBilling(){

        //Reading data from the database
        SQLiteDatabase db = this.getReadableDatabase();

        //Create the Cursor variable
        Cursor cursorBilling = db.rawQuery( "select * from " + BillingDbSchema.BillingTable.NAME, null);

        //Create ArrayList
        ArrayList<Billing> billingModalArrayList = new ArrayList<>();

        //Move the cursor to the first position
        if(cursorBilling.moveToFirst())
        {
            do {
                billingModalArrayList.add(new Billing(cursorBilling.getInt(0),
                                            cursorBilling.getString(1),
                                            cursorBilling.getString(2),
                                            cursorBilling.getDouble(3),
                                            cursorBilling.getInt(4)));
            }while(cursorBilling.moveToNext());
        }

        //Close the cursor and return
        cursorBilling.close();
        //db.close();
        return billingModalArrayList;
    }

    public void updateBilling(Billing billing)
    {
        String client_idString = String.valueOf(billing.getClient_Id());

        //Creating values from ContentValues
        ContentValues values = getContentValues(billing);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(BillingDbSchema.BillingTable.NAME, values, BillingDbSchema.BillingTable.Cols.CLIENT_ID + "=?" , new String[]{client_idString});
        //The "?" is the parameter (replace with the parameter --> new String[]{client_idString})

        //db.close();
    }

    public void deleteBilling(Billing billing) {

        String client_idString = String.valueOf(billing.getClient_Id());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BillingDbSchema.BillingTable.NAME, BillingDbSchema.BillingTable.Cols.CLIENT_ID + "=?", new String[]{client_idString});
        //db.close();
    }

    //VERIFICAR
    public Billing searchBilling(Billing billing) {
        SQLiteDatabase db = this.getReadableDatabase();

        String client_idString = String.valueOf(billing.getClient_Id());

        Cursor cursorBilling = db.query(BillingDbSchema.BillingTable.NAME, null,
                BillingDbSchema.BillingTable.Cols.CLIENT_ID + "=?", new String[]{client_idString},
                null, null, null);

        if (cursorBilling != null && cursorBilling.moveToFirst()) {
            Billing foundBilling = new Billing(cursorBilling.getInt(0), cursorBilling.getString(1),
                    cursorBilling.getString(2), cursorBilling.getDouble(3), cursorBilling.getInt(4));
            cursorBilling.close();
            //db.close();
            return foundBilling;
        } else {
            return null;
        }

    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table "+ BillingDbSchema.BillingTable.NAME);
    }


}
