package com.search.ebaycatalog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogActivity extends AppCompatActivity {
    TextView showingResults;
    Integer resultsCount;
    JSONObject productDetails, searchResult, items;
    SwipeRefreshLayout swipeRefreshLayout;
    ActionBar actionBar;
    RequestQueue requestQueue;
    String url;
    String keyword;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    ProgressBar progressBar;
    LinearLayout productCatalog;
    TextView progressText;
    Intent intent;

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        productCatalog = findViewById(R.id.productCatalog);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        intent = getIntent();

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Search Results");

        requestQueue = Volley.newRequestQueue(this);

        url = intent.getStringExtra("url");
        keyword = intent.getStringExtra("keyword");

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProducts();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        getProducts();

    }

    public void getProducts() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        displayProducts(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void displayProducts(JSONObject response) {
        try {
            showingResults = findViewById(R.id.resultHeader);
            productDetails = (JSONObject) response.getJSONArray("findItemsAdvancedResponse").get(0);
            searchResult = (JSONObject) productDetails.getJSONArray("searchResult").get(0);


            JSONObject tempCond, shippingInfo, tempShipping, tempPrice;
            ArrayList<ItemData> products = new ArrayList<>();

            resultsCount = searchResult.getInt("@count");
            if(resultsCount == 0) {
                ((TextView) findViewById(R.id.noRecordsTxt)).setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(), R.string.noRecords, Toast.LENGTH_LONG);
                toast.show();
            }

            int i=0;
            while(i < resultsCount){
                if(i == 50)
                    break;

                items = (JSONObject) searchResult.getJSONArray("item").get(i);
                if(items.has("itemId") && items.has("galleryURL") && items.has("title") && items.has("condition") &&
                        items.has("topRatedListing") && items.has("shippingInfo") && items.has("sellingStatus")) {
                    if (!items.isNull("itemId") &&
                            !items.isNull("galleryURL") &&
                            !items.isNull("title") &&
                            !items.isNull("condition") &&
                            !items.isNull("topRatedListing") &&
                            !items.isNull("shippingInfo") &&
                            !items.isNull("sellingStatus")) {
                        tempCond = (JSONObject) items.getJSONArray("condition").get(0);
                        shippingInfo = (JSONObject) items.getJSONArray("shippingInfo").get(0);
                        tempPrice = (JSONObject) items.getJSONArray("sellingStatus").get(0);
                        if(shippingInfo.has("shippingServiceCost") && tempPrice.has("currentPrice")) {
                            if (!shippingInfo.isNull("shippingServiceCost") &&
                                    !tempPrice.isNull("currentPrice") &&
                                    !shippingInfo.isNull("shippingType") &&
                                    !shippingInfo.isNull("handlingTime") &&
                                    !shippingInfo.isNull("oneDayShippingAvailable") &&
                                    !items.isNull("country") &&
                                    !shippingInfo.isNull("shipToLocations") && !shippingInfo.isNull("expeditedShipping")) {
                                tempShipping = (JSONObject) shippingInfo.getJSONArray("shippingServiceCost").get(0);
                                tempPrice = (JSONObject) tempPrice.getJSONArray("currentPrice").get(0);
                                products.add(new ItemData(
                                        items.getJSONArray("itemId").getString(0),
                                        items.getJSONArray("galleryURL").getString(0),
                                        items.getJSONArray("title").getString(0),
                                        tempCond.getJSONArray("conditionDisplayName").getString(0),
                                        items.getJSONArray("topRatedListing").getString(0),
                                        tempShipping.getDouble("__value__"),
                                        tempPrice.getDouble("__value__"),
                                        shippingInfo.getJSONArray("shippingType").getString(0),
                                        shippingInfo.getJSONArray("handlingTime").getString(0),
                                        shippingInfo.getString("oneDayShippingAvailable"),
                                        items.getJSONArray("country").getString(0),
                                        shippingInfo.getJSONArray("shipToLocations").getString(0),
                                        shippingInfo.getString("expeditedShipping")
                                ));
                            }
                        }
                    }
                    i++;
                } else {
                    i++;
                    continue;
                }
            }
            resultsCount = i;
            showingResults.setText(Html.fromHtml("<h6>Showing <span style='color: #3377FF'>" + resultsCount.toString() + "</span> results for <span style='color: #3377FF'>" + keyword + "</span></h6>"));
            customAdapter = new CustomAdapter(CatalogActivity.this, products);

            progressBar.setVisibility(View.GONE);
            progressText.setVisibility(View.GONE);
            productCatalog.setVisibility(View.VISIBLE);

            recyclerView.setAdapter(customAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}