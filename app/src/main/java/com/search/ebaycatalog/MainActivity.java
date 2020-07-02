package com.search.ebaycatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText keyword, minPrice, maxPrice;
    CheckBox newCondition, usedCondition, unspecifiedCondition;
    Spinner sortCategory;
    Button search, clear;
    TextView keywordError, priceError;
    boolean errorPresent;
    static String url;
    ArrayList<String> conditionList;
    Intent intent;
    HashMap<String, String> sortCategoryMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortCategoryMap = new HashMap<>();
        sortCategoryMap.put("Best Match", "BestMatch");
        sortCategoryMap.put("Price: Highest first", "CurrentPriceHighest");
        sortCategoryMap.put("Price + Shipping: Highest first", "PricePlusShippingHighest");
        sortCategoryMap.put("Price + Shipping: Lowest first", "PricePlusShippingLowest");

        keyword = (EditText) findViewById(R.id.keyword);
        minPrice = (EditText) findViewById(R.id.minPrice);
        maxPrice = (EditText) findViewById(R.id.maxPrice);
        newCondition = (CheckBox) findViewById(R.id.newCondition);
        usedCondition = (CheckBox) findViewById(R.id.usedCondition);
        unspecifiedCondition = (CheckBox) findViewById(R.id.unspecifiedCondition);
        sortCategory = (Spinner) findViewById(R.id.sortCategory);
        search = (Button) findViewById(R.id.search);
        clear = (Button) findViewById(R.id.clear);
        keywordError = (TextView) findViewById(R.id.keywordError);
        priceError = (TextView) findViewById(R.id.priceError);
        intent = new Intent(this, CatalogActivity.class);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorPresent = false;
                conditionList = new ArrayList<>();
                url = "https://ebay-express-server-hw8.wl.r.appspot.com/getProducts?";

                if(keyword.getText().toString().isEmpty()) {
                    keywordError.setText(R.string.keywordErrorDisplay);
                    keywordError.setVisibility(View.VISIBLE);
                    errorPresent = true;
                }

                if(priceInvalid()) {
                    priceError.setText(R.string.priceErrorDisplay);
                    priceError.setVisibility(View.VISIBLE);
                    errorPresent = true;
                }

                if(errorPresent) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toastError, Toast.LENGTH_LONG);
                    toast.show();
                }

                if(newCondition.isChecked())
                    conditionList.add("New");
                if(usedCondition.isChecked())
                    conditionList.add("Used");
                if(unspecifiedCondition.isChecked())
                    conditionList.add("Unspecified");

                String selectedCondition = String.join(", ", conditionList);

                if(!errorPresent) {
                    keywordError.setVisibility(View.GONE);
                    priceError.setVisibility(View.GONE);
                    url += "keywords=" + keyword.getText().toString();
                    url += "&minPrice=" + minPrice.getText().toString();
                    url += "&maxPrice=" + maxPrice.getText().toString();
                    url += "&condition=" + selectedCondition;
                    url += "&sortCategory=" + sortCategoryMap.get(sortCategory.getSelectedItem().toString());
                    Log.i("all items url: ", url);
                    intent.putExtra("url", url);
                    intent.putExtra("keyword", keyword.getText().toString());
                    startActivity(intent);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword.setText("");
                minPrice.setText("");
                maxPrice.setText("");
                newCondition.setChecked(false);
                usedCondition.setChecked(false);
                unspecifiedCondition.setChecked(false);
                sortCategory.setSelection(0);
                keywordError.setVisibility(View.GONE);
                priceError.setVisibility(View.GONE);
            }
        });
    }

    private boolean priceInvalid() {
        if(!minPrice.getText().toString().isEmpty()) {
            if(Float.parseFloat(minPrice.getText().toString()) < 0)
                return true;
        }

        if(!maxPrice.getText().toString().isEmpty()) {
            if(Float.parseFloat(maxPrice.getText().toString()) < 0)
                return true;
        }

        if(!minPrice.getText().toString().isEmpty() && !maxPrice.getText().toString().isEmpty())
            return Float.parseFloat(minPrice.getText().toString()) >= Float.parseFloat(maxPrice.getText().toString());

        return false;
    }
}