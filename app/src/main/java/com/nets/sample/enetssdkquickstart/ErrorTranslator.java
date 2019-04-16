package com.nets.sample.enetssdkquickstart;

/**
 * ErrorTranslator
 * Created by jason on 15/3/19.
 */
public class ErrorTranslator {
    public static String translateResponse(String responseCode) {

        String stageMessage = "Unknown";
        String errorCodeMsg = "Unknown";

        String[] split = responseCode.split("-");
        if (split.length == 2) {
            String stateCode = split[0];


            if (stateCode.equalsIgnoreCase("1000")) {
                stageMessage = "SDK setup";

            } else if (stateCode.equalsIgnoreCase("2100")) {
                stageMessage = "Service List";

            } else if (stateCode.equalsIgnoreCase("2200")) {
                stageMessage = "Debit Page";

            } else if (stateCode.equalsIgnoreCase("2300")) {
                stageMessage = "Credit Page";

            } else if (stateCode.equalsIgnoreCase("2400")) {
                stageMessage = "Credit Non 3DS Page";

            } else if (stateCode.equalsIgnoreCase("2500")) {
                stageMessage = "QR Code";

            } else if (stateCode.equalsIgnoreCase("5100")) {
                stageMessage = "Inter appliation";
            }

            String errorCode = split[1];
            if (errorCode.equalsIgnoreCase("68001")) {
                errorCodeMsg = "Setup error";

            } else if (errorCode.equalsIgnoreCase("69001")) {
                errorCodeMsg = "Network not available";

            } else if (errorCode.equalsIgnoreCase("69002")) {
                errorCodeMsg = "Server Time out";

            } else if (errorCode.equalsIgnoreCase("69003")) {
                errorCodeMsg = "Server error";

            } else if (errorCode.equalsIgnoreCase("69004")) {
                errorCodeMsg = "Invalid data request";

            } else if (errorCode.equalsIgnoreCase("69005")) {
                errorCodeMsg = "Invalid data response";

            } else if (errorCode.equalsIgnoreCase("69006")) {
                errorCodeMsg = "User cancel transaction";

            }

            // Service list api
            else if (errorCode.equalsIgnoreCase("69021")) {
                errorCodeMsg = "Service List empty";

            } else if (errorCode.equalsIgnoreCase("69022")) {
                errorCodeMsg = "Invalid credential";

            } else if (errorCode.equalsIgnoreCase("69023")) {
                errorCodeMsg = "Invalid Signature";
            }

            // Debit flow
            else if (errorCode.equalsIgnoreCase("69031")) {
                errorCodeMsg = "Page loading fail";

            } else if (errorCode.equalsIgnoreCase("69032")) {
                errorCodeMsg = "Bank List redirect fail";

            } else if (errorCode.equalsIgnoreCase("69033")) {
                errorCodeMsg = "Invalid reponse data";

            } else if (errorCode.equalsIgnoreCase("69034")) {
                errorCodeMsg = "Bank login loading";

            } else if (errorCode.equalsIgnoreCase("69035")) {
                errorCodeMsg = "Bank login redirect fail";

            } else if (errorCode.equalsIgnoreCase("69036")) {
                errorCodeMsg = "Invalid response";

            }

            // Credit flow
            else if (errorCode.equalsIgnoreCase("69041")) {
                errorCodeMsg = "Crard encryption fail";

            } else if (errorCode.equalsIgnoreCase("69042")) {
                errorCodeMsg = "ACS url invalid";

            } else if (errorCode.equalsIgnoreCase("69043")) {
                errorCodeMsg = "ACS page loading fail";

            } else if (errorCode.equalsIgnoreCase("49044")) {
                errorCodeMsg = "ACS redirect fail";

            } else if (errorCode.equalsIgnoreCase("69045")) {
                errorCodeMsg = "ACS data response invalid";

            } else if (errorCode.equalsIgnoreCase("69046")) {
                errorCodeMsg = "Non 3DS data response invalid";
            }
            // QR Code
            else if (errorCode.equalsIgnoreCase("69061")) {
                errorCodeMsg = "Invalid QR response";

            } else if (errorCode.equalsIgnoreCase("69071")) {
                errorCodeMsg = "NETSPay not installed";

            } else if (errorCode.equalsIgnoreCase("69072")) {
                errorCodeMsg = "Invalid QR";

            } else if (errorCode.equalsIgnoreCase("69073")) {
                errorCodeMsg = "Authentication failure";

            } else if (errorCode.equalsIgnoreCase("69074")) {
                errorCodeMsg = "Txn Not success";

            } else if (errorCode.equalsIgnoreCase("69075")) {
                errorCodeMsg = "Service not available";

            } else if (errorCode.equalsIgnoreCase("69076")) {
                errorCodeMsg = "Unknown reason";

            } else if (errorCode.equalsIgnoreCase("69077")) {
                errorCodeMsg = "Card not available";

            } else if (errorCode.equalsIgnoreCase("69078")) {
                errorCodeMsg = "User cancel";

            } else {
                errorCodeMsg = "Unknown error";
            }
        }
        return stageMessage + " - " + errorCodeMsg;
    }
}
