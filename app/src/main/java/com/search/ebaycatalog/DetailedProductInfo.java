package com.search.ebaycatalog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DetailedProductInfo extends AppCompatActivity {
    ActionBar actionBar;
    Intent intent;
    ViewPager viewPager;
    TabLayout tabLayout;
    RequestQueue requestQueue;
    ItemData itemData;
    String url;
    JSONObject singleItemData;
    String itemUrl;
    ProgressBar progressBar;
    TextView progressBarText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.redirect:
                (findViewById(R.id.redirect)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ebay.com/"));
                        startActivity(intent);
                    }
                });
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product_info);

        intent = getIntent();
        itemData = (ItemData) intent.getSerializableExtra("itemData");
        requestQueue = Volley.newRequestQueue(this);
        url = "https://ebay-express-server-hw8.wl.r.appspot.com/getSingleProduct?productId=" + itemData.getItemId();

        progressBar = findViewById(R.id.progressBarDetail);
        progressBarText = (TextView) findViewById(R.id.progressTextDetail);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(itemData.getTitle());

        getSingleProduct();
    }

    public void getSingleProduct() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            display(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void display(JSONObject response) throws JSONException {
        singleItemData = response.getJSONObject("Item");
        Log.i("response: ", response.toString());
        DetailedItemData detailedItemData = new DetailedItemData();

        if (singleItemData.has("ItemSpecifics"))
            detailedItemData.setItemSpecifics(singleItemData.getJSONObject("ItemSpecifics"));

        if(singleItemData.has("ViewItemURLForNaturalSearch")) {
            itemUrl = singleItemData.getString("ViewItemURLForNaturalSearch");
            (findViewById(R.id.redirect)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl));
                    startActivity(intent);
                }
            });
            detailedItemData.setItemUrl(singleItemData.getString("ViewItemURLForNaturalSearch"));
        }
        if(singleItemData.has("Seller"))
            detailedItemData.setSeller(singleItemData.getJSONObject("Seller"));

        if(singleItemData.has("ReturnPolicy"))
            detailedItemData.setReturnPolicy(singleItemData.getJSONObject("ReturnPolicy"));

        if(singleItemData.has("PictureURL"))
            detailedItemData.setPictureUrl(singleItemData.getJSONArray("PictureURL"));

        if(singleItemData.has("Subtitle"))
            detailedItemData.setSubtitle(singleItemData.getString("Subtitle"));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        ProductTab productTab = new ProductTab();
        SellerInfoTab sellerInfoTab = new SellerInfoTab();
        ShippingTab shippingTab = new ShippingTab();

        Bundle bundle = new Bundle();
        bundle.putSerializable("itemData", itemData);
        bundle.putSerializable("detailedItemData", detailedItemData);

        productTab.setArguments(bundle);
        sellerInfoTab.setArguments(bundle);
        shippingTab.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(productTab, "PRODUCT");
        adapter.addFragment(sellerInfoTab, "SELLER INFO");
        adapter.addFragment(shippingTab, "SHIPPING");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.information_variant_selected);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_seller_info);
        tabLayout.getTabAt(2).setIcon(R.drawable.truck_delivery_selected);

        viewPager.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBarText.setVisibility(View.GONE);
    }
}