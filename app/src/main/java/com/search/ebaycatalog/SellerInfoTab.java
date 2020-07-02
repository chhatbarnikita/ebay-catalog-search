package com.search.ebaycatalog;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class SellerInfoTab extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seller_info_tab, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ItemData itemData = (ItemData) getArguments().getSerializable("itemData");
        DetailedItemData detailedItemData = (DetailedItemData) getArguments().getSerializable("detailedItemData");
        ((TextView) view.findViewById(R.id.sellerInfoHeader)).setText(Html.fromHtml("<h3>Seller Information</h3>"));

        try {
            StringBuilder sellerHtml = new StringBuilder("<ul>");
            if (!detailedItemData.getSeller().isNull("FeedbackScore"))
                sellerHtml.append("<li><b>&nbsp;Feedback Score: </b>" + detailedItemData.getSeller().getString("FeedbackScore") + "</li>");
            if (!detailedItemData.getSeller().isNull("UserID"))
                sellerHtml.append("<li><b>&nbsp;User I D: </b>" + detailedItemData.getSeller().getString("UserID") + "</li>");
            if (!detailedItemData.getSeller().isNull("PositiveFeedbackPercent"))
                sellerHtml.append("<li><b>&nbsp;Positive Feedback Percent: </b>" + detailedItemData.getSeller().getString("PositiveFeedbackPercent") + "</li>");
            if (!detailedItemData.getSeller().isNull("FeedbackRatingStar"))
                sellerHtml.append("<li><b>&nbsp;Feedback Rating Bar: </b>" + detailedItemData.getSeller().getString("FeedbackRatingStar") + "</li>");
            sellerHtml.append("</ul>");
            ((TextView) view.findViewById(R.id.sellerInfoList)).setText(Html.fromHtml(sellerHtml.toString()));

            ((TextView) view.findViewById(R.id.returnPoliciesHeader)).setText(Html.fromHtml("<h3>Return Policies</h3>"));

            StringBuilder returnHtml = new StringBuilder("<ul>");
            if (detailedItemData.getReturnPolicy().has("Refund"))
                returnHtml.append("<li><b>&nbsp;Refund: </b>" + detailedItemData.getReturnPolicy().getString("Refund") + "</li>");
            if (detailedItemData.getReturnPolicy().has("ReturnsWithin"))
                returnHtml.append("<li><b>&nbsp;Returns Within: </b>" + detailedItemData.getReturnPolicy().getString("ReturnsWithin") + "</li>");
            if (detailedItemData.getReturnPolicy().has("ShippingCostPaidBy"))
                returnHtml.append("<li><b>&nbsp;Shipping Cost Paid By: </b>" + detailedItemData.getReturnPolicy().getString("ShippingCostPaidBy") + "</li>");
            if (detailedItemData.getReturnPolicy().has("ReturnsAccepted"))
                returnHtml.append("<li><b>&nbsp;Returns Accepted: </b>" + detailedItemData.getReturnPolicy().getString("ReturnsAccepted") + "</li>");
            returnHtml.append("</ul>");
            ((TextView) view.findViewById(R.id.returnPoliciesList)).setText(Html.fromHtml(returnHtml.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}