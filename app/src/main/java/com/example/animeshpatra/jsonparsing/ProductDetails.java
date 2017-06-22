package com.example.animeshpatra.jsonparsing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ANIMESH PATRA on 08-05-2017.
 */

public class ProductDetails extends AppCompatActivity {
    ListView lv_parser;
    String productID = "", categoryID = "", itemName = "", itemDescription ="", marketPrice = "", webPrice = "", avalibility = "", productImage = "";
    String value = "";
    String url = "";
    ProgressDialog progressDialog;
    JSONParser jsonParser;
    FetchProductDetails fetchProductDetails;
    ArrayList<FetchProductDetails> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        lv_parser = (ListView) findViewById(R.id.lv_product_details);
        value = getIntent().getExtras().getString("CID");
        url = "http://220.225.80.177/onlineshoppingapp/show.asmx/GetProduct?catId="+value;
        jsonParser = new JSONParser();
        progressDialog = new ProgressDialog(ProductDetails.this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setMessage("Wait for the process to finish");
        progressDialog.setCancelable(false);
        new fetchData().execute();
        lv_parser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent singleProductDetails = new Intent(ProductDetails.this, ProductDescription.class);
                singleProductDetails.putExtra("ID", arrayList.get(position).getProductID());
                singleProductDetails.putExtra("CatID", arrayList.get(position).getCategoryID());
                singleProductDetails.putExtra("ItemName", arrayList.get(position).getItemName());
                singleProductDetails.putExtra("ItemDesc", arrayList.get(position).getItemDescription());
                singleProductDetails.putExtra("MarketPrice", arrayList.get(position).getMarketPrice());
                singleProductDetails.putExtra("WebPrice", arrayList.get(position).getWebPrice());
                singleProductDetails.putExtra("Avalibility", arrayList.get(position).getAvalibitily());
                singleProductDetails.putExtra("ProductImage", arrayList.get(position).getProductImage());
                startActivity(singleProductDetails);
            }
        });
    }

    public class fetchData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (arrayList.size() > 0){
                lv_parser.setAdapter(new MyAdaptor());
            }
            else{
                Toast.makeText(ProductDetails.this, "No data at all", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject jsonObject = jsonParser.getJsonFromURL(url);
                JSONArray jsonArray = jsonObject.getJSONArray("Products");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jObject = jsonArray.getJSONObject(i);
                    productID = jObject.getString("Product_Id");
                    categoryID = jObject.getString("Category_Id");
                    itemName = jObject.getString("Item_Name");
                    itemDescription = jObject.getString("Item_Desc");
                    marketPrice = jObject.getString("Market_Price");
                    webPrice = jObject.getString("Web_Price");
                    avalibility = jObject.getString("Availability");
                    productImage = jObject.getString("Product_Image");

                    fetchProductDetails = new FetchProductDetails();
                    fetchProductDetails.setProductID(productID);
                    fetchProductDetails.setCategoryID(categoryID);
                    fetchProductDetails.setItemName(itemName);
                    fetchProductDetails.setItemDescription(itemDescription);
                    fetchProductDetails.setMarketPrice(marketPrice);
                    fetchProductDetails.setWebPrice(webPrice);
                    fetchProductDetails.setAvalibitily(avalibility);
                    fetchProductDetails.setProductImage(productImage);
                    arrayList.add(fetchProductDetails);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public class MyAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.product_details,parent,false);
            ImageView iv_productImage = (ImageView) convertView.findViewById(R.id.iv_productImage);
            TextView tv_productID = (TextView) convertView.findViewById(R.id.tv_productID);
            TextView tv_categiryID = (TextView) convertView.findViewById(R.id.tv_categoryID);
            TextView tv_itemName = (TextView) convertView.findViewById(R.id.tv_itemName);
            TextView tv_itemDescription = (TextView) convertView.findViewById(R.id.tv_itemDescription);
            TextView tv_marketPrice = (TextView) convertView.findViewById(R.id.tv_marketPrice);
            TextView tv_webPrice = (TextView) convertView.findViewById(R.id.tv_webPrice);
            TextView tv_availibility = (TextView) convertView.findViewById(R.id.tv_avalibility);

            tv_productID.setText(arrayList.get(position).getProductID());
            tv_categiryID.setText(arrayList.get(position).getCategoryID());
            tv_itemName.setText(arrayList.get(position).getItemName());
            tv_itemDescription.setText(arrayList.get(position).getItemDescription());
            tv_marketPrice.setText(arrayList.get(position).getMarketPrice());
            tv_webPrice.setText(arrayList.get(position).getWebPrice());
            tv_availibility.setText(arrayList.get(position).getAvalibitily());
            Picasso.with(ProductDetails.this).load(arrayList.get(position).getProductImage()).into(iv_productImage);
            return convertView;
        }
    }
}
