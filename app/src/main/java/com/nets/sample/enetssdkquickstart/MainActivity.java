package com.nets.sample.enetssdkquickstart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.nets.enets.exceptions.InvalidPaymentRequestException;
import com.nets.enets.listener.PaymentCallback;
import com.nets.enets.network.PaymentRequestManager;
import com.nets.enets.utils.CryptoUtils;
import com.nets.enets.utils.result.DebitCreditPaymentResponse;
import com.nets.enets.utils.result.NETSError;
import com.nets.enets.utils.result.NonDebitCreditPaymentResponse;
import com.nets.enets.utils.result.PaymentResponse;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ENETS_SAMPLE_" + MainActivity.class.getSimpleName();

    private EditText etUmid;
    private EditText etApiKey;
    private EditText etSecretKey;
    private EditText etAmount;
    private Button   btnPayment;

    AlertDialog alertDialog = null;

    //----------------------------------------------------------------
    // SET YOUR MERCHANT CREDENTIALS HERE
    // Sample credentials
    // Ensure that hyphens are included in the key strings
    private static final String UMID       = "UMID_877772003";
    private static final String API_KEY    = "154eb31c-0f72-45bb-9249-84a1036fd1ca ";
    private static final String SECRET_KEY = "38a4b473-0295-439d-92e1-ad26a8c60279";
    //----------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUmid = findViewById(R.id.et_umid);
        etApiKey = findViewById(R.id.et_apikey);
        etSecretKey = findViewById(R.id.et_secretkey);
        etAmount = findViewById(R.id.et_amount);
        btnPayment = findViewById(R.id.btn_payment);

        setMerchantConfiguration();

        btnPayment.setOnClickListener(view -> makeTxnRequest());
    }

    /**
     * Set the Merchant Keys, Details, etc.
     */
    private void setMerchantConfiguration() {
        etUmid.setText(UMID);
        etApiKey.setText(API_KEY);
        etSecretKey.setText(SECRET_KEY);
    }

    /**
     * Send a payment request
     */
    private void makeTxnRequest() {
        if (etUmid.getText().toString().isEmpty()
            || etSecretKey.getText().toString().isEmpty()
            || etApiKey.getText().toString().isEmpty()
            || etAmount.getText().toString().isEmpty()) {
            showAlertDialog("Warning", "A Field is empty. Fill in all fields before making payment request");
            return;
        }

        //Fetch the Merchant & Txn Details
        String amtCents  = etAmount.getText().toString();
        String umid      = etUmid.getText().toString();
        String apiKey    = etApiKey.getText().toString();
        String secretKey = etSecretKey.getText().toString();

        //Create the Txn Request
        String txnRequest = TxnRequestBuilder.createSampleTxnRequest(umid, amtCents);
        //Generate the hmac
        String hmac = CryptoUtils.getHMAC(txnRequest + secretKey);

        final PaymentRequestManager paymentRequestManager = PaymentRequestManager.getSharedInstance();

        //Make a payment
        try{
            paymentRequestManager.sendPaymentRequest(apiKey,
                                                     hmac,
                                                     txnRequest,
                                                     getSimplePaymentCallback(),
                                                     MainActivity.this);
        } catch (InvalidPaymentRequestException e) {
            Log.e(TAG, "Txn Request Error: " + e.getMessage());
        }

    }

    /**
     * Create an instance of a PaymentCallback to handle the response from ENETS SDK
     */
    private PaymentCallback getSimplePaymentCallback(){
        return new PaymentCallback() {

            //Handle the success case
            @Override
            public void onResult(PaymentResponse paymentResponse) {
                //Check if  the paymentResponse is a DebitCreditPaymentResponse or NonDebitCreditPaymentResponse
                //Then handle accordingly
                if (paymentResponse instanceof DebitCreditPaymentResponse){
                    DebitCreditPaymentResponse debitCreditResponse = (DebitCreditPaymentResponse) paymentResponse;

                    String txnRes = debitCreditResponse.txnResp;
                    String txn_HMAC = debitCreditResponse.hmac;
                    String txn_KeyID = debitCreditResponse.keyId;

                    showAlertDialog("Debit-Credit Response",
                                    String.format("Key ID : %s \nHmac : %s \nResponse: %s", txn_KeyID, txn_HMAC, txnRes));
                } else if (paymentResponse instanceof NonDebitCreditPaymentResponse) {

                    NonDebitCreditPaymentResponse nonDebitCreditResponse = (NonDebitCreditPaymentResponse) paymentResponse;

                    String app = nonDebitCreditResponse.app;
                    String status = nonDebitCreditResponse.status;

                    showAlertDialog("Non Debit-Credit Response",
                                    String.format("App : %s \nStatus : %s", app, status));
                }
            }

            //Handle the failure case
            @Override
            public void onFailure(NETSError netsError) {
                //Obtain and handle the error code
                String txn_ActionCode = netsError.actionCode;
                String txn_ResponseCode = netsError.responeCode;
                String errorMessage = ErrorTranslator.translateResponse(txn_ResponseCode);

                showAlertDialog("Error",
                                String.format("Action Code : %s\nResponse Code : %s(%s)\n",
                                              txn_ActionCode,
                                              txn_ResponseCode,
                                              errorMessage));
            }
        };
    }

    private void showAlertDialog(@NonNull final String title, @NonNull final String message) {
        runOnUiThread(() -> {
            dismissAlertDialog();

            alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle(title)
                                .setMessage(message)
                                .setCancelable(true)
                                .setPositiveButton("Dismiss", (dialogInterface, i) -> {
                                    // Just to show a dismiss button
                                })
                                .create();
            if (!isFinishing() && !isDestroyed()) {
                alertDialog.show();
            }
        });
    }

    private void dismissAlertDialog() {
        runOnUiThread(() -> {
            if (isDestroyed()) {
                return;
            }
            if (alertDialog == null || !alertDialog.isShowing()) {
                return;
            }
            alertDialog.dismiss();
            alertDialog = null;
        });
    }
}
