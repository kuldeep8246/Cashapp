/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.CashArApp;
import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Admin
 */
public class CashApp_OpenInvoices {
    
    FormReference formObject = FormContext.getCurrentInstance().getFormReference();
    FormConfig formConfig = FormContext.getCurrentInstance().getFormConfig();
    public void getOpenInvoice(String customer_code, String bu,String glDate) {
        System.out.println("inside getCash APP ********");
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
               
        String class_doc = "";
        String invoicedueDate = "";
        String soNo = "";
        String oraclBaanDocNo = "";
        String CurrencyCode = "";
        String invoiceDueAmt = "";
        String invDnDate = "";
        String invoicenumber = "";
        String install = "";
        String invoicedate="";
        
        String advance_flag = "N";
        String inputxml = "N";                
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.100.1.232:1601:R12UAT", "apps", "r12uatapps");
            // String query_column = "(OPERATING_UNIT, INVOICE_SOURCE, INVOICE_TYPE, INVOICE_NUMBER, INVOICE_DATE, GL_DATE, INVOICE_AMOUNT, VENDOR_NAME, VENDOR_SITE_CODE, INVOICE_CURRENCY_CODE, PAYMENT_CURRENCY_CODE, PAYMENT_TERM, PAYMENT_TERM_DATE, EXCHANGE_RATE_TYPE, EXCHANGE_RATE, EXCHANGE_DATE, PAY_GROUP, INVOICE_DESCRIPTION, LINE_TYPE_LOOKUP_CODE, STG_INVOICE_NUMBER, STG_PAYMENT_METHOD_LOOKUP_CODE, STG_ATTRIBUTE1, LINE_NUMBER, SECTION_CODE, TAX_CATEGORY_NMAE, INV_ORG_NAME, LINE_AMOUNT, TDS_TAX_CATEGORY_NAME, ATTRIBUTE_CATEGORY,  ATTRIBUTE4, ATTRIBUTE6, ATTRIBUTE7, ATTRIBUTE11,  ATTRIBUTE14, ATTRIBUTE15, PREPAY_NUM, SAC_CODE, HSN_CODE )";
            // String values = " ('bu','Manual Invoice Entry','Standard', '" + invoice_number + "',(TO_DATE('" + invoice_date + "','dd/mm/yyyy')), (TO_DATE('" + gl_date + "','dd/mm/yyyy')),'" + invoice_amt + "','" + vendor_name + "', '" + vendor_site_code + "','" + invoice_cuurn_code + "','INR','" + payment_term + "','29-DEC-19','User', '1','31-DEC-19','Standard','ERS-07-FEB-2020-TEST-17','ITEM', '" + invoice_number + "', 'CHECK','07-101000-1311-80420-110001-000000-000000', '1', 'Sec. 194(C)', 'S07GST-18%', 'Cooling Excise', '" + invoice_amt + "','" + tdscode + "','Migration','21-FEB-20','" + workItemId_no + "','barcode1234','21-02-2020','" + gst_no_supplier + "','test21feb','" + prepaymentNum + "')";
          String sql="SELECT  distinct \n" +
                        "(select meaning from fnd_lookup_values\n" +
                        "   where lookup_code=TYPE\n" +
                        "   and lookup_type='TRX TYPES'\n" +
                        "   and enabled_flag='Y'\n" +
                        "and LANGUAGE='US') TRX_CLASS,\n" +
                        "INTERFACE_HEADER_ATTRIBUTE1 SO_NUMBER\n" +
                        " ,ra.trx_number doc_num\n" +
                        ",ra.invoice_currency_code currency\n"+
                        " ,(select distinct TAX_INVOICE_NUM from JAI_TAX_DET_FACTORS\n" +
                        "   where TRX_ID=ra.CUSTOMER_TRX_ID\n" +
                        "   and EVENT_CLASS_CODE='INVOICE'\n" +
                        "   and ENTITY_CODE='TRANSACTIONS') tax_invoice_num,\n" +
                        "   hc.ACCOUNT_NUMBER\n" +
                        ",hp.party_name\n" +
                        ",rct.GL_DATE\n" +
                        "     ,TERMS_SEQUENCE_NUMBER INSTALLMENT_NUMBER\n" +
                        ",hc.account_number         customer_number\n" +
                        ", aps.DUE_DATE\n" +
                        "   ,aps.amount_due_remaining   amount_due_remaining\n" +
                        "      ,aps.amount_due_original   amount_due_original\n" +
                        "  FROM ra_customer_trx_all       ra,\n" +
                        "       ra_customer_trx_lines_all rl,\n" +
                        "       ar_payment_schedules_all  aps,\n" +
                        "       ra_cust_trx_types_all     rt,\n" +
                        "       hz_cust_accounts          hc,\n" +
                        "       hz_parties                hp,\n" +
                        "       hz_cust_acct_sites_all    hcasa_bill,\n" +
                        "       hz_cust_site_uses_all     hcsua_bill,\n" +
                        "       hz_party_sites            hps_bill,\n" +
                        "       ra_cust_trx_line_gl_dist_all rct\n" +
                        " WHERE ra.customer_trx_id           = rl.customer_trx_id\n" +
                        "   AND ra.customer_trx_id           = aps.customer_trx_id\n" +
                        "   AND ra.org_id                    = aps.org_id\n" +
                        "   AND rct.customer_trx_id          = aps.customer_trx_id\n" +
                        "   AND rct.customer_trx_id          = ra.customer_trx_id\n" +
                        "   AND rct.customer_trx_id          = rl.customer_trx_id\n" +
                        "   AND rct.customer_trx_line_id     = rl.customer_trx_line_id\n" +
                        "   AND ra.complete_flag             = 'Y'\n" +
                        "   AND rl.line_type                 IN ('FREIGHT', 'LINE')\n" +
                        "   AND ra.cust_trx_type_id          = rt.cust_trx_type_id\n" +
                        "   AND ra.bill_to_customer_id       = hc.cust_account_id\n" +
                        "   AND hc.status                    = 'A'\n" +
                        "   AND hp.party_id                  = hc.party_id\n" +
                        "   AND hcasa_bill.cust_account_id   = ra.bill_to_customer_id\n" +
                        "   AND hcasa_bill.cust_acct_site_id = hcsua_bill.cust_acct_site_id\n" +
                        "   AND hcsua_bill.site_use_code     = 'BILL_TO'\n" +
                        "   AND hcsua_bill.site_use_id       = ra.bill_to_site_use_id\n" +
                        "   AND hps_bill.party_site_id       = hcasa_bill.party_site_id\n" +
                        "   AND hcasa_bill.status            = 'A'\n" +
                        "   AND hcsua_bill.status            = 'A'\n" +
                        "   AND aps.amount_due_remaining     <> 0\n" +
                        "   AND aps.status                   = 'OP'\n" +
                        "   and TYPE='INV'\n" +
                        "   and hc.ACCOUNT_NUMBER='"+customer_code+"'\n" +
                        "   and exists (select 1 from  tl_apps.xxtmx_le_bu_mapping a\n" +
                        "   where a.org_id= ra.org_id\n" +
                        "   and \n" +
                        "                    BU_CODE='"+bu+"') order by aps.DUE_DATE\n";
            System.out.println("query " + sql);
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);

            while (rs.next()) {

                System.out.println("Data from query ----- : " + rs.getString("TAX_INVOICE_NUM"));

               // vendorcode = rs.getString("DUE_DATE");
                class_doc = rs.getString("TRX_CLASS");
                oraclBaanDocNo = rs.getString("doc_num");               
                soNo = rs.getString("SO_NUMBER");
                CurrencyCode = rs.getString("currency");
                invoiceDueAmt = rs.getString("amount_due_remaining");
                install = rs.getString("INSTALLMENT_NUMBER");
                invoicedate = rs.getString("DUE_DATE");
                invoicedueDate = rs.getString("DUE_DATE");
                invoicenumber = rs.getString("tax_invoice_num");
                
                

//                if (vendorName.length() > 49) {
//                    vendorName = vendorName.substring(0, 48);
//                }
                advance_flag = "Y";

                inputxml = inputxml + " <ListItem>"
                        + "<SubItem></SubItem>" // applyer
                        + "<SubItem>" + class_doc + "</SubItem>" //invoice number                       
                        + "<SubItem>"+soNo+"</SubItem>" //doc number
                         + "<SubItem>"+oraclBaanDocNo+"</SubItem>" //advance date   
                         + "<SubItem>"+invoicenumber+"</SubItem>"//
                         + "<SubItem>" + install + "</SubItem>"// tds code
                        + "<SubItem>" + CurrencyCode + "</SubItem>" //total amount  
                        + "<SubItem>" + invoicedate + "</SubItem>"//tds amount
                        + "<SubItem>"+invoicedueDate+"</SubItem>"//
                        + "<SubItem>"+invoiceDueAmt+"</SubItem>" // 
                        + "<SubItem></SubItem>" //applied amount 
                        + "<SubItem>"+glDate+"</SubItem>" //appliy date 
                        + "</ListItem>";
            }
            System.out.println("Final input xml for insertion " + inputxml);
            formObject.NGAddListItem("q_ar_open_invoice", inputxml);
            System.out.println("record inserted");
            con.close();

        } catch (Exception e) {
            System.out.println("connection exception" + e);
        }
        if (advance_flag.equalsIgnoreCase("N")) {
            throw new ValidatorException(new FacesMessage("No Open Invoice Cash App ! ", ""));
        }
    }
    
     public String setPrapay(String cust_code,String cust_name,String opreating_unit,String arNumber) {
        System.out.println("*** setPrePay ***");
        List<List<String>> resultapply = null;
        String values = "";
        int rowcount = formObject.getLVWRowCount("q_ar_open_invoice");
        System.out.println("row count of q_ar_open_invoice cash app  rowcount****" + rowcount);        
        String cashappinsertflag = "N";
        String currentdate="";
        String glDate = formObject.getNGValue("ca_booking_date");
        String ngId = formObject.getNGValue("ng_wi_id");
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
//        Date date = new Date();  
//        currentdate=formatter.format(date);
//        System.out.println("current date    "+formatter.format(date)); 
        try {

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.100.1.232:1601:R12UAT", "apps", "r12uatapps");

            for (int i = 0; i < rowcount; i++) {
                System.out.println(" data " + formObject.getNGValue("q_ar_open_invoice", i, 0));
                String pre_apply = formObject.getNGValue("q_ar_open_invoice", i, 0);
                
              // formObject.getNGValue("q_ar_open_invoice", i, 0)

                if (pre_apply.equalsIgnoreCase("Yes")) {
                    values = "(CUSTOMER_NUMBER,CUSTOMER_NAME,OPERATING_UNIT,ACTION, TRX_CLASS, ATTRIBUTE1,TRX_NUMBER, AR_NUMBER, TRANSACTION_NUMBER,INSTALLMENT_NUMBER, CURRENCY_CODE,TRX_AMOUNT,AMOUNT_APPLIED,APPLY_DATE,RECEIPT_DATE,ATTRIBUTE10)"
                            + " VALUES ('" + cust_code + "','" + cust_name + "','" + opreating_unit + "','"+"RECEIPT_APPLY"+"','" + formObject.getNGValue("q_ar_open_invoice", i, 1) + "','" + formObject.getNGValue("q_ar_open_invoice", i, 2) + "','" + formObject.getNGValue("q_ar_open_invoice", i, 3) + "','" + arNumber + "','" + formObject.getNGValue("q_ar_open_invoice", i, 4) + "','" + formObject.getNGValue("q_ar_open_invoice", i, 5) + "','" + formObject.getNGValue("q_ar_open_invoice", i, 6) + "','" + formObject.getNGValue("q_ar_open_invoice", i, 9) + "','" + formObject.getNGValue("q_ar_open_invoice", i, 10) + "',(TO_DATE('" + formObject.getNGValue("q_ar_open_invoice", i, 11) + "','dd/mm/yyyy')),(TO_DATE('" + glDate + "','dd/mm/yyyy')),'" + ngId + "')";

                        String sql = "Insert into xxtmx_ar_receipt_stg_tbl " + values;

                    System.out.println("open invoice insert query " + sql);
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.executeUpdate(sql);
                    cashappinsertflag = "Y";
                    System.out.println("record inserted");
                }
            }
        } catch (Exception E) {
            cashappinsertflag = "E";
            System.out.println("Entry for cash app ar is not done " + E);
        }
        return cashappinsertflag;
    }
    
}
