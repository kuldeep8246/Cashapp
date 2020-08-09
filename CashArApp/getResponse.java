package com.newgen.CashArApp;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class getResponse {

    FormReference formObject = FormContext.getCurrentInstance().getFormReference();
    FormConfig formConfig = FormContext.getCurrentInstance().getFormConfig();

    public void getres(String flagNo, String workItemId_no) {
        List<List<String>> resultgetRes = null;
        System.out.println("getres ******** " + flagNo + "    process id   " + workItemId_no);
        String entry_seq = formObject.getNGValue("entry_seq");
        String response_msg = "";
        String post_status = "";
//        String own_Sequence = workItemId_no.replace("NonPo-", ""); // removing NonPo- at the begining in workitem no
//        own_Sequence = own_Sequence.replace("Invoices", ""); // removing Invoices- at the end in workitem no
//        own_Sequence = own_Sequence + entry_seq; // append seq entry number

        String toFetchResponse = "select STATUS, ERROR_STATUS,AR_INVOICE_NUMBER,AR_NUMBER_DATE,ACTION from  xxtmx_ar_receipt_stg_tbl where ATTRIBUTE10 like '" + workItemId_no + "'";
        String flagstg = "";
        String error = null;
        String arInvoiceNO = "";
        String arInvoiceDate = "";
        String advance_status = "";
        // String tdsamount = "";     MULTIDIVISON_FLAG like '" + flagNo + "' and
        System.out.println("query to get the fetchresponse***** : " + toFetchResponse);
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.100.1.232:1601:R12UAT", "apps", "r12uatapps");
            PreparedStatement pstmt = con.prepareStatement(toFetchResponse);
            ResultSet rs = pstmt.executeQuery(toFetchResponse);
            //System.out.println("rs.getString(STG_STATUS):" +rs.getString("STG_STATUS"));
            while (rs.next()) {
                flagstg = rs.getString("STATUS");
                error = rs.getString("ERROR_STATUS");
                arInvoiceNO = rs.getString("AR_INVOICE_NUMBER");
                arInvoiceDate = rs.getString("AR_NUMBER_DATE");
                if (rs.getString("ACTION").equalsIgnoreCase("RECEIPT_APPLY")) {
                    if (flagstg.equalsIgnoreCase("S")) {
                        advance_status ="Invoice Posted Successfully";
                    } else if (flagstg.equalsIgnoreCase("E")) {
                        advance_status ="Error "+error;
                    }
                } else if (rs.getString("ACTION").equalsIgnoreCase("CREATE_RECEIPT")) {
                    if (flagstg.equalsIgnoreCase("S")) {
                        response_msg = "Posted Successfully";
                      //  posting_status = "Y";
                    } else if (flagstg.equalsIgnoreCase("E")) {
                        response_msg = "Error " + error;
                        //posting_status = "E";
                    }
                }
                System.out.println("advance_status invoice ka" + advance_status);
                System.out.println("response_msg 12 stg, Error : " + response_msg + ", " + arInvoiceDate);
            }

            if (flagstg.equalsIgnoreCase("S")) {
                System.out.println("flagstg.equalsIgnoreCase(\"I\") *** IF ***");
               // formObject.setNGValue("cash_ap_response", advance_status);
                formObject.setNGValue("cash_ap_response", response_msg +"   Linking Status    "+advance_status);

                formObject.clear("ca_bank_receipt_no");
                formObject.clear("receipt_date");
                formObject.setNGValue("ca_bank_receipt_no", arInvoiceNO);
                formObject.setNGValue("receipt_date", arInvoiceDate);
                post_status = "S";
            } else {
                if (flagstg.equalsIgnoreCase("E")) {
                    System.out.println("flagstg.equalsIgnoreCase(\"E\") ***ELSE IF ***");
                   formObject.setNGValue("cash_ap_response", response_msg);
                    post_status = "E";
                } else {
                    System.out.println("flagstg.equalsIgnoreCase(\"E\") *** ELSE else ***");
                    response_msg = "In Process: Creation is in progress!";
                }
                formObject.setNGValue("cash_ap_response", response_msg);
            }
            formObject.setNGValue("posting_status", post_status);

        } catch (Exception e) {
            System.out.println("In side exception  :: " + e);
        }

    }

}
