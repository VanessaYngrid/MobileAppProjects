package com.example.billingproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Billing implements Parcelable {

    private int client_Id;
    private String client_Name;
    private String product_Name;
    private double prd_Price;
    private int prd_Qty;

    public static double Fed_Tax=0.075;
    public static double Prv_Tax=0.06;

    public Billing() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Billing(int client_Id, String client_Name, String product_Name, double prd_Price,
                   int prd_Qty) {
        super();
        this.client_Id = client_Id;
        this.client_Name = client_Name;
        this.product_Name = product_Name;
        this.prd_Price = prd_Price;
        this.prd_Qty = prd_Qty;
    }

    protected Billing(Parcel in) {
        client_Id = in.readInt();
        client_Name = in.readString();
        product_Name = in.readString();
        prd_Price = in.readDouble();
        prd_Qty = in.readInt();
    }

    public static final Creator<Billing> CREATOR = new Creator<Billing>() {
        @Override
        public Billing createFromParcel(Parcel in) {
            return new Billing(in);
        }

        @Override
        public Billing[] newArray(int size) {
            return new Billing[size];
        }
    };

    public int getClient_Id() {
        return client_Id;
    }

    public void setClient_Id(int client_Id) {
        this.client_Id = client_Id;
    }

    public String getClient_Name() {
        return client_Name;
    }

    public void setClient_Name(String client_Name) {
        this.client_Name = client_Name;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public double getPrd_Price() {
        return prd_Price;
    }

    public void setPrd_Price(double prd_Price) {
        this.prd_Price = prd_Price;
    }

    public int getPrd_Qty() {
        return prd_Qty;
    }

    public void setPrd_Qty(int prd_Qty) {
        this.prd_Qty = prd_Qty;
    }

    public double totalBilling()
    {
        double T_Billing;
        T_Billing = (prd_Price * prd_Qty) + (prd_Price * prd_Qty) * Fed_Tax + (prd_Price * prd_Qty) * Prv_Tax;

        return T_Billing;
    }

    @Override
    public String toString() {
        return "Client: " + client_Id + ", " + client_Name
                + ", Product: " + product_Name + " is " +  String.format("%.2f",totalBilling()) +"$" +  "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(client_Id);
        dest.writeString(client_Name);
        dest.writeString(product_Name);
        dest.writeDouble(prd_Price);
        dest.writeInt(prd_Qty);
    }
}
