package com.search.ebaycatalog;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductTab extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_tab, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ItemData itemData = (ItemData) getArguments().getSerializable("itemData");
        DetailedItemData detailedItemData = (DetailedItemData) getArguments().getSerializable("detailedItemData");
        Boolean subtitlePresent = true;
        Boolean brandPresent = true;

        try {
            LinearLayout linearLayout = view.findViewById(R.id.imageLayout);
            for (int i=0; i<detailedItemData.getPictureUrl().length(); i++) {
                LinearLayout tempLayout = new LinearLayout(getContext());
                tempLayout.setLayoutParams(new LinearLayout.LayoutParams(1500, 900));
                tempLayout.setGravity(Gravity.CENTER);

                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(900, 900));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Picasso.with(getContext()).load(detailedItemData.getPictureUrl().getString(i)).into(imageView);

                tempLayout.addView(imageView);
                linearLayout.addView(tempLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.productTitle)).setText(Html.fromHtml(itemData.getTitle()));

        if(itemData.getFreeShipping() > 0)
            ((TextView) view.findViewById(R.id.productPrice)).setText("$" + itemData.getPrice().toString() + " Ships for $" + itemData.getFreeShipping());
        else
            ((TextView) view.findViewById(R.id.productPrice)).setText(Html.fromHtml("$" + itemData.getPrice().toString()));

        ((TextView) view.findViewById(R.id.productFeatures)).setText(Html.fromHtml("<h3>&nbsp;Product Features</h3>"));

        if(detailedItemData.getSubtitle() != null)
            ((TextView) view.findViewById(R.id.subtitleValue)).setText(Html.fromHtml( detailedItemData.getSubtitle()));
        else {
            view.findViewById(R.id.sub).setVisibility(View.GONE);
            view.findViewById(R.id.subtitleValue).setVisibility(View.GONE);
            subtitlePresent = false;
        }
        try {
            JSONArray specsList = detailedItemData.getItemSpecifics().getJSONArray("NameValueList");
            if(((JSONObject) specsList.get(0)).getString("Name").equals("Brand"))
                ((TextView) view.findViewById(R.id.brandValue)).setText(Html.fromHtml((((JSONObject) specsList.get(0)).getJSONArray("Value")).getString(0)));
            else {
                view.findViewById(R.id.brandText).setVisibility(View.GONE);
                view.findViewById(R.id.brandValue).setVisibility(View.GONE);
                brandPresent = false;
            }

            if(!subtitlePresent && !brandPresent) {
                view.findViewById(R.id.divider2).setVisibility(View.GONE);
                view.findViewById(R.id.productFeatures).setVisibility(View.GONE);
            }

            StringBuilder specHtml = new StringBuilder("<ul>");
            for(int i=1; i < 6; i++) {
                specHtml.append("<li>&nbsp;").append((((JSONObject) specsList.get(i)).getJSONArray("Value")).get(0)).append("</li>");
            }
            specHtml.append("</ul>");
            ((TextView) view.findViewById(R.id.specsList)).setText(Html.fromHtml(specHtml.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.specifications)).setText(Html.fromHtml("<h3>&nbsp;Specifications:</h3>"));
    }
}
