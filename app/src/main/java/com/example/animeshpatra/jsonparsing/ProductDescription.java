package com.example.animeshpatra.jsonparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ProductDescription extends AppCompatActivity {

    //ListView lv_parser;
    ImageView iv_singleProductImage;
    TextView tv_productID, tv_categoryID, tv_itemName, tv_itemDescription, tv_marketPrice, tv_webPrice, tv_avalibility;
    String productID = "", categoryID = "", itemName = "", itemDescription ="", marketPrice = "", webPrice = "", avalibility = "", productImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product_layout);
        productID = getIntent().getExtras().getString("ID");
        categoryID = getIntent().getExtras().getString("CatID");
        itemName = getIntent().getExtras().getString("ItemName");
        itemDescription = getIntent().getExtras().getString("ItemDesc");
        marketPrice = getIntent().getExtras().getString("MarketPrice");
        webPrice = getIntent().getExtras().getString("WebPrice");
        avalibility = getIntent().getExtras().getString("Avalibility");
        productImage = getIntent().getExtras().getString("ProductImage");
        tv_productID = (TextView) findViewById(R.id.tv_productID);
        tv_categoryID = (TextView) findViewById(R.id.tv_categoryID);
        tv_itemName = (TextView) findViewById(R.id.tv_itemName);
        tv_itemDescription = (TextView) findViewById(R.id.tv_itemDescription);
        tv_marketPrice = (TextView) findViewById(R.id.tv_marketPrice);
        tv_webPrice = (TextView) findViewById(R.id.tv_webPrice);
        tv_avalibility = (TextView) findViewById(R.id.tv_avalibility);
        iv_singleProductImage = (ImageView) findViewById(R.id.iv_singleProductImage);
        tv_productID.setText(productID);
        tv_categoryID.setText(categoryID);
        tv_itemName.setText(itemName);
        tv_itemDescription.setText(itemDescription);
        tv_marketPrice.setText(marketPrice);
        tv_webPrice.setText(webPrice);
        tv_avalibility.setText(avalibility);
        Picasso.with(ProductDescription.this).load(productImage).into(iv_singleProductImage);
    }
}
