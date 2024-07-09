package com.example.billingproject.database;

public class BillingDbSchema {

    public static final class BillingTable
    {
        public static final String NAME="billing"; //BillingTable.NAME

        public static final class Cols
        {
            public static final String CLIENT_ID="client_Id";
            public static final String CLIENT_NAME="client_Name";
            public static final String PRODUCT_NAME="product_Name";
            public static final String PRODUCT_PRICE="prd_Price";
            public static final String PRODUCT_QUANTITY="prd_Qty";
        }
    }
}
