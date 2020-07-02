package com.search.ebaycatalog;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

public class ShippingTab extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shipping_tab, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ItemData itemData = (ItemData) getArguments().getSerializable("itemData");
//        JSONObject singleItemData = (JSONObject) getArguments().getSerializable("singleItemData");
        ((TextView) view.findViewById(R.id.shippingInfoHeader)).setText(Html.fromHtml("<h3>Shipping Information"));
        ((TextView) view.findViewById(R.id.shippingList)).setText(Html.fromHtml("<ul><li><b>&nbsp;Handling Time</b> : " + itemData.getHandlingTime() + "</li>" +
                "<li><b>&nbsp;One Day Shipping Available</b> : " + (itemData.getOneDayShippingAvailable().equals("true") ? "Yes" : "No") + "</li>" +
                "<li><b>&nbsp;Shippping Type</b> : " + itemData.getShippingType() + "</li>" +
                "<li><b>&nbsp;Shipping From</b> : " + itemData.getShippingFrom() + "</li>" +
                "<li><b>&nbsp;Ship To Locations</b> : " + itemData.getShipToLocations() + "</li>" +
                "<li><b>&nbsp;Expedited Shipping</b> : " + (itemData.getExpeditedShipping().equals("true") ? "Yes" : "No") + "</li>" +
                "</ul>"));
    }
}
