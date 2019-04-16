package com.nets.sample.enetssdkquickstart;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TxnRequestBuilder
 * Created by jason on 15/3/19.
 */
public class TxnRequestBuilder {

    public static final String TAG = "ENETS_SAMPLE_" + TxnRequestBuilder.class.getSimpleName();

    //Use your own URLs/Other Params in order to receive the host response
    private static final String DUMMY_B2S_URL = "http://localhost:8088/MerchantApp/redirectServlet";
    private static final String DUMMY_S2S_URL = "https://sit2.enets.sg/MerchantApp/rest/s2sTxnEnd";
    private static final String DUMMY_IPADDR = "172.18.20.161";

    public static String createTxnRequest(@NonNull String umid, @NonNull String amountInCents, @NonNull String txnRef,
                                   @NonNull String b2sUrl, @NonNull String s2sUrl, @NonNull String ipAddr){
        String out = null;

        try {
            JSONObject json = new JSONObject();
            JSONObject msg  = new JSONObject();
            json.put("ss", "1");

            msg.put("netsMid", umid);

            msg.put("submissionMode", "B");
            msg.put("txnAmount", amountInCents);

            msg.put("merchantTxnRef", txnRef);

            SimpleDateFormat merchantTxnDtmFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
            msg.put("merchantTxnDtm", merchantTxnDtmFormatter.format(new Date()));
            msg.put("paymentType", "SALE");
            msg.put("currencyCode", "SGD");
            msg.put("merchantTimeZone", "+8:00");

            msg.put("b2sTxnEndURL", b2sUrl);
            msg.put("s2sTxnEndURL", s2sUrl);
            msg.put("clientType", "S");
            msg.put("netsMidIndicator", "U");
            msg.put("ipAddress", ipAddr);
            msg.put("language", "en");
            msg.put("ss", "1");

            msg.put("mobileOS", "ANDROID");
            json.put("msg", msg);
            out = json.toString();


        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return out;
    }

    public static String createSampleTxnRequest(@NonNull String umid, @NonNull String amountInCents){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss:SS", Locale.getDefault());
        String dummyTxnReference = sdf.format(new Date());

        return createTxnRequest(umid,
                                amountInCents,
                                dummyTxnReference,
                                DUMMY_B2S_URL,
                                DUMMY_S2S_URL,
                                DUMMY_IPADDR);
    }

}
