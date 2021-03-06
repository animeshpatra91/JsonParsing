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
 * Created by ANIMESH PATRA on 26-04-2017.
 */

public class ProductCategories extends AppCompatActivity {
    ListView lv_parser;
    String contentName, contentID, contentImage;
    String value = "";
    String url = "";
    ProgressDialog progressDialog;
    JSONParser jsonParser;
    DataModel dataModel;
    ArrayList<DataModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        lv_parser = (ListView) findViewById(R.id.lv_product_details);
        value = getIntent().getExtras().getString("CID");
        url = "http://220.225.80.177/onlineshoppingapp/show.asmx/getsubcat?catide="+value;
        jsonParser = new JSONParser();
        progressDialog = new ProgressDialog(ProductCategories.this);
        progressDialog.setTitle("Please Wait....");
        progressDialog.setMessage("Wait for the process to finish");
        progressDialog.setCancelable(false);
        new fetchData().execute();

        lv_parser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent productDetailsIntent = new Intent(ProductCategories.this, ProductDetails.class);
                productDetailsIntent.putExtra("CID", arrayList.get(position).getID());
                startActivity(productDetailsIntent);
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
                Toast.makeText(ProductCategories.this, "No data at all", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject jsonObject = jsonParser.getJsonFromURL(url);
                JSONArray jsonArray = jsonObject.getJSONArray("Category");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jObject = jsonArray.getJSONObject(i);
                    contentName = jObject.getString("Cat_Name");
                    contentID = jObject.getString("Cat_Id");
                    contentImage = jObject.getString("Cat_Image");
                    dataModel = new DataModel();
                    dataModel.setName(contentName);
                    dataModel.setID(contentID);
                    dataModel.setImage(contentImage);
                    arrayList.add(dataModel);
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
            convertView = getLayoutInflater().inflate(R.layout.row_view,parent,false);
            ImageView iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            TextView tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            tv_name.setText(arrayList.get(position).getName());
            tv_id.setText(arrayList.get(position).getID());
            Picasso.with(ProductCategories.this).load(arrayList.get(position).getImage()).into(iv_image);
            return convertView;
        }
    }
}
