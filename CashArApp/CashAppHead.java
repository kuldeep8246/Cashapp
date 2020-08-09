/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.CashArApp;

import com.newgen.common.General;
import com.newgen.common.PicklistListenerHandler_thrmx;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.PickList;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author KuldeepPal
 */
public class CashAppHead implements FormListener {

    FormReference formObject = null;
    FormConfig formConfig = null;
    String activityName = null;
    String engineName = null;
    String sessionId = null;
    String processid = null;
    String folderId = null;
    String FILE = null;
    String serverUrl = null;
    String processInstanceId = null;
    String workItemId = null;
    String userName = null;
    String processDefId = null;
    String Query = null;
    String outputxml = null;
    List<List<String>> result, result1;
    PickList objPicklist;
    General objGeneral = null;
    String totalamount = null;
    String partycode = null;
    String salesorderno = null;
    String checuenortgs = null;
    String projectcode = null;
    String formLog = "";
    int listvalue;
    int listcount;
    FormReference formObject1;

    PicklistListenerHandler_thrmx objPicklistListenerHandler;

    @Override
    public void continueExecution(String arg0, HashMap<String, String> arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void eventDispatched(ComponentEvent pEvent)
            throws ValidatorException {
        // TODO Auto-generated method stub

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        System.out.println("kdeep");
        System.out.println("TYPE-->" + pEvent.getType().name());
        System.out.println("NAME-->" + pEvent.getSource().getName());

        if (pEvent.getType().name().equalsIgnoreCase("VALUE_CHANGED")) {
//****************************************************************************************************************** 
            System.out.println("inside value changed  ");

            if (pEvent.getSource().getName().equalsIgnoreCase("ca_company_name")) {
                System.out.println("Change in entity");
                formObject.clear("ca_division_code");
                formObject.clear("division_control");
                System.out.println("ca_company_name  " + formObject.getNGValue("ca_company_name"));
                divisionCode("D");
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_user_decision")) {//   exception combo enable and desable

                String useraction = formObject.getNGValue("ca_user_decision");
                String modeofpayment = formObject.getNGValue("ca_mode_of_payment");
                System.out.println("user  " + useraction);
                System.out.println("user  " + useraction);

                if ("raiseexception".equalsIgnoreCase(useraction)) {
                    formObject.setEnabled("exception", true);
                    System.out.println("set setexception call");
                    formObject.addComboItem("exception", "---Select---", "");
                    formObject.addComboItem("exception", "Incorrect Attachment", "IncorrectAttachment");
                    formObject.addComboItem("exception", "Incorrect Information entered in manual text fields", "Informationenteredinmanual");
                    formObject.addComboItem("exception", "Incorrect Division Code for Multi Division Bank Receipts", "DivisionCodeforMultiDBReceipts");
                    formObject.addComboItem("exception", "Incorrect GL code", "Inctglcode");
                    formObject.addComboItem("exception", "Incorrect rate for Export", "InctrateforExport");
                    formObject.addComboItem("exception", "Incorrect Bank Details in offline deposit WI ", "InctBankDetailsofflinedepositWI");
                    formObject.addComboItem("exception", "Incorrect Export Documents", "InctExportDocuments");
                    formObject.addComboItem("exception", "Incorrect GST Category", "InctGSTCategory");
                    formObject.addComboItem("exception", "Incorrect Tax Code Entered", "InctTaxCodeEntered");
                    formObject.addComboItem("exception", "Incorrect Inventory Organization", "InctInventoryOrganization");
                    formObject.addComboItem("exception", "Incorrect customer code   and site ", "Inctcustomercodeandsite ");
                    formObject.addComboItem("exception", "Incorrect type of Deduction", "IncttypeofDeduction");
                    formObject.addComboItem("exception", "Incorrect Invoice Linking", "InctInvoiceLinking");
                    formObject.addComboItem("exception", "Incorrect Attachment", "IncorrectAttachment");
                    formObject.addComboItem("exception", "Wrong Amount  Applied", "wrongAmountApplied");

                    // setexception();
                } else if ("cheque".equalsIgnoreCase(modeofpayment) || "online".equalsIgnoreCase(modeofpayment) || "post".equalsIgnoreCase(useraction)) {
                    formObject.clear("exception");
                    formObject.setEnabled("exception", false);
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_currency_type")) {//   currency type 
                String currencytype = formObject.getNGValue("ca_currency_type");
                List<List<String>> result_curr = null;
                if (currencytype.equalsIgnoreCase("INR")) {
                    formObject.clear("ca_rate_type");
                    // formObject.clear("foreign_amt");
                    formObject.setEnabled("ca_rate_type", false);
                } else {
                    formObject.setEnabled("ca_rate_type", true);
                    System.out.println("Currency ***  rate set****");

                    String curry_rate = "";
                    try {
                        String getCurrencyRate = "select conversion_rate from mdm_exchange_rate where from_currency like '"
                                + currencytype + "'";
                        System.out.println("** Curreny updated ***getCurrencyRate :" + getCurrencyRate);
                        result_curr = formObject.getDataFromDataSource(getCurrencyRate);
                        System.out.println("result_curr.get(0).get(0) : " + result_curr.get(0).get(0));
                        curry_rate = result_curr.get(0).get(0);
                        //  formObject.setNGValue("ca_rate_type", curry_rate);
                        // formObject.setEnabled("ca_rate_type", false);
                    } catch (Exception E) {
                        System.out.println(" Exception in currency E " + E);
                    }
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_division_code")) {
                System.out.println("Update master as per BU ---- ");
                formObject.clear("ca_documnt_category");
                formObject.clear("ca_so_no");
                formObject.clear("orgnization_control");
                formObject.clear("location_code_control");
                formObject.clear("tax_cat_control");
                formObject.clear("project_code_control");
                formObject.clear("ca_customer_code");
                formObject.clear("ca_customer_name");
                formObject.clear("ca_customer_site");
                formObject.clear("ca_project_code");
                formObject.clear("ca_so_no");
                setDocCategory("D");
                //  setSalesOrderNo("D");
                // settaxCat("D");
//                Oragnization("D");
//                OrgLoc("D");

            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_mode_of_payment")) {// online/cheque mode of decision
                String paymenttpye = "";
                paymenttpye = formObject.getNGValue("ca_mode_of_payment");
                System.out.println("paymenttpye  " + paymenttpye + " activityName");

                if ("online".equalsIgnoreCase(paymenttpye)) {
                    formObject.clear("ca_reveived_date");
                    formObject.clear("ca_deposit_date");
                    formObject.clear("ca_cheque_date");
                    formObject.clear("liq_list");
                    formObject.setVisible("Frame6", false);  //  frame6 offline payment
                    formObject.setVisible("Frame4", true);
                    formObject.setEnabled("ca_drawn_bank", false);
                    formObject.setEnabled("ca_reveived_date", false);
                    formObject.setEnabled("ca_deposit_date", false);
                    formObject.setEnabled("ca_deposit_date", false);
                    formObject.setEnabled("liq_list", true);
                    formObject.setEnabled("Frame2", false);
                    // formObject.setEnabled("Frame3", false);
                    formObject.setEnabled("Frame9", false);
                    formObject.setEnabled("Frame8", false);
                    formObject.setEnabled("Frame7", false);
                    formObject.setEnabled("Button5", false);
                    formObject.setEnabled("Button1", false);
                    formObject.setEnabled("Button4", false);
                    formObject.setEnabled("Button7", false);
                    formObject.setEnabled("Button8", false);
                    formObject.setEnabled("clearinvoice", false);
                    formObject.setEnabled("projectbtn", false);
                    formObject.setEnabled("Button6", false);
                    formObject.setEnabled("calculaterate", false);
                    formObject.setEnabled("Button2", false);
                    formObject.setEnabled("Button3", false);
                    formObject.setEnabled("Frame13", false);
                    liqTranssction("D");
                } else if ("cheque".equalsIgnoreCase(paymenttpye)) {
                    formObject.setVisible("Frame4", false);      // frame4 online payment
                    formObject.setVisible("Frame6", true);
                    formObject.setEnabled("ca_rate_type", false);
                    formObject.setEnabled("ca_rate", false);
                    formObject.setEnabled("ca_drawn_bank", true);
                    formObject.setEnabled("ca_reveived_date", false);
                    formObject.setEnabled("ca_deposit_date", true);
                    formObject.clear("liq_list");
                    formObject.setEnabled("liq_list", false);
                    formObject.clear("bank_name");
                    formObject.clear("account_no");
                    formObject.clear("ca_narration_n");
                    formObject.clear("transaction_ref");
                    formObject.clear("amount");
                    formObject.clear("ca_cheque_date");
                    formObject.setEnabled("ca_cheque_date", true);
                    formObject.setEnabled("Frame2", true);
                    // formObject.setEnabled("Frame3", true);
                    formObject.setEnabled("Frame9", true);
                    formObject.setEnabled("Frame8", true);
                    formObject.setEnabled("Frame7", true);
                    formObject.setEnabled("Button5", true);
                    formObject.setEnabled("Button1", true);
                    formObject.setEnabled("Button4", true);
                    formObject.setEnabled("Button7", true);
                    formObject.setEnabled("Button8", true);
                    formObject.setEnabled("projectbtn", true);
                    formObject.setEnabled("Button6", true);
                    formObject.setEnabled("calculaterate", true);
                    formObject.setEnabled("clearinvoice", true);
                    // BAAN Field Disable
                    String[] miscDisableOracle = {"exception", "Frame13", "employee_control", "ca_bank_code", "ca_receipt_method", "businessPart_control", "misc_control", "diemention_control", "ca_bank_receipt_no", "ca_bank_receipt_date", "own_gst_reg_no_control", "cust_gst_reg_no_control"};
                    for (int i = 0; i < miscDisableOracle.length; i++) {
                        formObject.setEnabled(miscDisableOracle[i], false);
                    }
                    formObject.addComboItem("ca_user_decision", "--Select--", "");
                    formObject.addComboItem("ca_user_decision", "Send For Processing", "sendforprocessing");
                }

            } else if (pEvent.getSource().getName().equalsIgnoreCase("type_deduction_control")) { // deduction type NA
                String typeDeduction = "";
                typeDeduction = formObject.getNGValue("type_deduction_control");
                System.out.println("typeDeduction  " + typeDeduction);

                if ("NA".equalsIgnoreCase(typeDeduction)) {
                    formObject.clear("deduction_accept_not_control");
                    formObject.clear("deduction_control");
                    formObject.setEnabled("deduction_accept_not_control", false);
                    formObject.setEnabled("deduction_control", false);
                } else {
                    formObject.setEnabled("deduction_accept_not_control", true);
                    formObject.setEnabled("deduction_control", true);
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("liq_list")) {// Liquice transsaction data
                activityName = formConfig.getConfigElement("ActivityName");
                String liqListLock = "";
                String liqList = formObject.getNGValue("liq_list");
                System.out.println("typeDeduction  " + liqList);
                //
                //SELECT * FROM mdm_ar WHERE lock NOT IN ('Y')
                String Query_mdm_currency_code = "select bank,transaction_ref,narration,value_date,amount,account_no,date,seq_no from mdm_ar_liquidice_report where seq_no like '" + liqList + "'"; // mdm table
                System.out.println("query1  " + Query_mdm_currency_code);

                List<List<String>> result15 = null;
                result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
                System.out.println("queryllist  " + "(TO_DATE('" + result15.get(0).get(3) + "','dd/mm/yyyy'))");
                String dateformate = "(TO_DATE('" + result15.get(0).get(3) + "','dd/mm/yyyy'))";
                System.out.println("hh  " + dateformate);
                String dateValue = null;
                try {
                    SimpleDateFormat sdfIn = new SimpleDateFormat("dd-MMM-yy");
                    SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
                    String input = result15.get(0).get(3);
                    System.out.println("date value input  " + input);
                    Date date = sdfIn.parse(input);
                    dateValue = sdfOut.format(date);
                    System.out.println(sdfOut.format(date));
                    System.out.println("date value   " + dateValue);
                } catch (Exception e) {

                }
                formObject.setNGValue("bank_name", result15.get(0).get(0));
                formObject.setNGValue("transaction_ref", result15.get(0).get(1));
                formObject.setNGValue("ca_narration_n", result15.get(0).get(2));
                formObject.setNGValue("ca_reveived_date", dateValue);
                formObject.setNGValue("ca_deposit_date", dateValue);
                formObject.setNGValue("amount", result15.get(0).get(4));
                formObject.setNGValue("account_no", result15.get(0).get(5));
                formObject.setNGValue("ca_cheque_date", dateValue);
                formObject.setEnabled("ca_cheque_date", false);
                formObject.setEnabled("ca_reveived_date", false);
                formObject.setEnabled("ca_deposit_date", false);
                if (activityName.equalsIgnoreCase("Initiator")) {
                    liqListLock = "update mdm_ar_liquidice_report set lock='Y' where seq_no='" + result15.get(0).get(7) + "'";
                    // liqListLock= liqListLock + result15.get(0).get(7);
                    System.out.println("update row data in table y " + liqListLock);
                    formObject.saveDataIntoDataSource(liqListLock);
                    System.out.println("update row data in table y done");
                }

            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_payment_method")) {     //  payment type 
                System.out.println("Change payment type");
                String paymentMethod = formObject.getNGValue("ca_payment_method");
                System.out.println("paymentMethod  " + paymentMethod);
                if ("ar".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_advance_amount");
                    formObject.clear("ca_cmi");
                    formObject.clear("ca_unallocated");
                    formObject.clear("Combo1");
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", true);
                    String[] arDisablefieldsName = {"orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control",
                        "sales_order_control", "Button10", "amount_control", "gst_applicable_control", "basicamtbtn", "gst_applicable_control"};
                    String[] arClaerDate = {"q_ar_open_invoice", "Text3", "Text4", "amount_control", "basic_amount_control", "sac_sn_code_control", "sac_control"};

                    for (int i = 0; i < arDisablefieldsName.length; i++) {
                        formObject.setEnabled(arDisablefieldsName[i], false);
                    }
                    // clear data 
                    for (int i = 0; i < arClaerDate.length; i++) {
                        formObject.clear(arClaerDate[i]);
                    }
                    //Linking section true                    
                    String[] arDisablefield = {"Text3", "Text4", "Combo1", "Button7", "Button8", "clearinvoice"};
                    for (int i = 0; i < arDisablefield.length; i++) {
                        formObject.setEnabled(arDisablefield[i], true);
                    }
                    formObject.addComboItem("Combo1", "Select", "");
                    formObject.addComboItem("Combo1", "Yes", "Yes");
                    formObject.addComboItem("Combo1", "No", "No");

                } else if ("advance".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_cmi");
                    formObject.clear("ca_unallocated");
                    formObject.setEnabled("ca_advance_amount", true);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", false);

                    String[] advanceEnablefieldsName = {"orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control",
                        "sales_order_control", "Button10", "amount_control", "gst_applicable_control", "basicamtbtn", "gst_applicable_control"};
                    String[] adClaerDate = {"q_ar_open_invoice", "Text3", "Text4", "amount_control", "basic_amount_control", "sac_sn_code_control", "sac_control"};

                    for (int i = 0; i < advanceEnablefieldsName.length; i++) {
                        formObject.setEnabled(advanceEnablefieldsName[i], true);
                    }
                    // clear data 
                    for (int i = 0; i < adClaerDate.length; i++) {
                        formObject.clear(adClaerDate[i]);
                    }
                    // linking section disble code
                    String[] advanceDisablefield = {"Text3", "Text4", "Combo1", "Button7", "Button8", "clearinvoice"};
                    for (int i = 0; i < advanceDisablefield.length; i++) {
                        formObject.setEnabled(advanceDisablefield[i], false);
                    }
                } else if ("cmi".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_advance_amount");
                    formObject.clear("ca_unallocated");
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", true);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", false);

                    String[] cmiEnablefieldsName = {"orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control",
                        "sales_order_control", "Button10", "amount_control", "gst_applicable_control", "basicamtbtn", "gst_applicable_control"
                    };
                    String[] cmiClaerDate = {"q_ar_open_invoice", "Text3", "Text4", "amount_control", "basic_amount_control", "sac_sn_code_control", "sac_control"};

                    for (int i = 0; i < cmiEnablefieldsName.length; i++) {
                        formObject.setEnabled(cmiEnablefieldsName[i], true);
                    }
                    // clear data 
                    for (int i = 0; i < cmiClaerDate.length; i++) {
                        formObject.clear(cmiClaerDate[i]);
                    }
                    String[] cmiDisablefield = {"Text3", "Text4", "Combo1", "Button7", "Button8", "clearinvoice"};
                    for (int i = 0; i < cmiDisablefield.length; i++) {
                        formObject.setEnabled(cmiDisablefield[i], false);
                    }
                } else if ("unallocated".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_advance_amount");
                    formObject.clear("ca_cmi");
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", true);
                    formObject.setEnabled("ca_ar_amount", false);

                    String[] uallowEnablefieldsName = {"orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control",
                        "sales_order_control", "Button10", "amount_control", "gst_applicable_control", "basicamtbtn", "gst_applicable_control"};
                    String[] cmiClaerDate = {"q_ar_open_invoice", "Text3", "Text4", "amount_control", "basic_amount_control", "sac_sn_code_control", "sac_control"};

                    for (int i = 0; i < uallowEnablefieldsName.length; i++) {
                        formObject.setEnabled(uallowEnablefieldsName[i], true);
                    }
                    // clear data 
                    for (int i = 0; i < cmiClaerDate.length; i++) {
                        formObject.clear(cmiClaerDate[i]);
                    }
                    String[] cmiDisablefield = {"Text3", "Text4", "Combo1", "Button7", "Button8", "clearinvoice"};
                    for (int i = 0; i < cmiDisablefield.length; i++) {
                        formObject.setEnabled(cmiDisablefield[i], false);
                    }
                } else if ("combined".equalsIgnoreCase(paymentMethod)) {
                    formObject.setEnabled("ca_advance_amount", true);
                    formObject.setEnabled("ca_cmi", true);
                    formObject.setEnabled("ca_unallocated", true);
                    formObject.setEnabled("ca_ar_amount", true);

                    String[] uallowEnablefieldsName = {"orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control",
                        "sales_order_control", "Button10", "amount_control", "gst_applicable_control", "basicamtbtn", "gst_applicable_control"};
                    String[] cmiClaerDate = {"q_ar_open_invoice", "amount_control", "basic_amount_control", "sac_sn_code_control", "sac_control"};

                    for (int i = 0; i < uallowEnablefieldsName.length; i++) {
                        formObject.setEnabled(uallowEnablefieldsName[i], true);
                    }
                    // clear data 
                    for (int i = 0; i < cmiClaerDate.length; i++) {
                        formObject.clear(cmiClaerDate[i]);
                    }
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_transaction_type")) {   //  ca_mode_of_business
                System.out.println("Change ca_transaction_type");
                String transactionType = formObject.getNGValue("ca_transaction_type");
                System.out.println("transactionType  " + transactionType);
                if ("export".equalsIgnoreCase(transactionType)) {
                    formObject.clear("ca_rate_type");
                    formObject.clear("ca_rate");
                    formObject.setEnabled("ca_currency_type", true);
                    // formObject.setEnabled("ca_rate_type", false);
                    formObject.setEnabled("ca_rate", true);
                    formObject.setEnabled("ca_rate_date", true);
                    System.out.println("set setCurrencyCode call");
                    formObject.clear("ca_currency_type");
                    setCurrencyCode();
                    formObject.addComboItem("ca_rate_type", "Select", "");
                    formObject.addComboItem("ca_rate_type", "Corporate : Corporate Exchange Rate", "corporate");
                    formObject.addComboItem("ca_rate_type", "Spot : Spot Exchange Rate", "spot");
                    formObject.addComboItem("ca_rate_type", "User : User Specified Rate", "user");
                    formObject.addComboItem("ca_rate_type", "Import Payment : Import Payment Rate - User Defined", "import payment");
                    formObject.addComboItem("ca_rate_type", "Export Receipt : Export Receipt Rate - User Defined", "export receipt");
                    formObject.addComboItem("ca_rate_type", "BOE Rate : Custom Rate", "boe rate");
                    // formObject.setEnabled("ca_currency_type", false);  
                } else if ("domestic".equalsIgnoreCase(transactionType)) {
                    formObject.clear("ca_currency_type");
                    formObject.clear("ca_rate_type");
                    formObject.clear("ca_rate");
                    formObject.setEnabled("ca_rate_type", false);
                    formObject.setEnabled("ca_rate", false);
                    formObject.setEnabled("ca_rate_date", false);
                    formObject.addComboItem("ca_currency_type", "INR", "INR");
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_mode_of_business")) {
                System.out.println("** mode of business **");
                if (formObject.getNGValue("ca_mode_of_business").equalsIgnoreCase("nonproject")) {
                    formObject.clear("ca_so_no");
                    formObject.setNGValue("ca_project_code", "110001");
                    formObject.setEnabled("projectbtn", false);
                    formObject.setEnabled("ca_so_no", false);
                    formObject.setEnabled("Button9", false);
                    formObject.setEnabled("ca_project_code", false);
                } else {
                    formObject.clear("ca_project_code");
                    formObject.setEnabled("projectbtn", true);
                    formObject.setEnabled("ca_so_no", true);
                    formObject.setEnabled("Button9", true);
                    formObject.setEnabled("ca_project_code", true);
                    // setSalesOrderNo("D");
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_division_type")) {   //  multi division == Y or single   == N
                System.out.println("Change in division type");
                String divisionType = formObject.getNGValue("ca_division_type");
                System.out.println("paymentTpye  " + divisionType);
                if ("Y".equalsIgnoreCase(divisionType)) {
                    formObject.setEnabled("ca_multidivision_name", true);
                } else if ("N".equalsIgnoreCase(divisionType)) {
                    formObject.setEnabled("ca_multidivision_name", false);
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_type_adv_ar_unallocated")) {   //  receipt of type stand/misc
                System.out.println("Change receipt type");
                String receiptofType = formObject.getNGValue("ca_type_adv_ar_unallocated");
                System.out.println("receiptofType  " + receiptofType);
                if ("CASH".equalsIgnoreCase(receiptofType)) {
                    //  setModeOfBusiness(pEvent);

                    String[] standEnablefieldsName = {"ca_so_no", "ca_project_code", "projectbtn", "ca_customer_code", "ca_customer_name", "ca_customer_site", "Button6",
                        "orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control", "basicamtbtn",
                        "sales_order_control", "Button10", "type_deduction_control", "deduction_accept_not_control",
                        "deduction_control", "gst_applicable_control"};
                    String[] standDisablefieldsName = {"gst_applicable_control", "division_control", "location_control", "cost_center_control", "gl_code_control", "project_code_control", "pu_code_f1", "future2_control"};
                    //  clear combo
                    for (int i = 0; i < standDisablefieldsName.length; i++) {
                        formObject.clear(standDisablefieldsName[i]);
                    }

                    for (int i = 0; i < standDisablefieldsName.length; i++) {
                        formObject.setEnabled(standDisablefieldsName[i], false);
                    }
                    for (int i = 0; i < standEnablefieldsName.length; i++) {
                        formObject.setEnabled(standEnablefieldsName[i], true);
                    }

                    //  setSalesOrderNo("D");
                    settaxCat("D");
                    Oragnization("D");
                    OrgLoc("D");
                    setDeductionType();
                    formObject.addComboItem("gst_applicable_control", "Select", "");
                    formObject.addComboItem("gst_applicable_control", "Yes", "yes");
                    formObject.addComboItem("gst_applicable_control", "No", "no");
                } else if ("MISC".equalsIgnoreCase(receiptofType)) {

                    String[] miscDisablefieldsName = {"ca_so_no", "ca_project_code", "projectbtn", "ca_customer_code", "ca_customer_name", "ca_customer_site", "Button6",
                        "orgnization_control", "location_code_control", "sac_control", "sac_sn_code_control", "tax_cat_control", "basic_amount_control", "tax_amt_control",
                        "sales_order_control", "Button10", "basicamtbtn", "type_deduction_control", "deduction_accept_not_control",
                        "deduction_control", "gst_applicable_control"};
                    String[] miscEnablefieldsName = {"division_control", "location_control", "cost_center_control", "gl_code_control", "project_code_control", "pu_code_f1", "future2_control"};
//  clear combo
                    for (int i = 0; i < miscDisablefieldsName.length; i++) {
                        formObject.clear(miscDisablefieldsName[i]);
                    }
                    for (int i = 0; i < miscDisablefieldsName.length; i++) {
                        formObject.setEnabled(miscDisablefieldsName[i], false);
                    }
                    for (int i = 0; i < miscEnablefieldsName.length; i++) {
                        formObject.setEnabled(miscEnablefieldsName[i], true);
                    }
                    System.out.println("set project code");
                    setProjectCode();
                    costCenter();
                    //  setPU();
                    //  divisionCode("D");
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("gst_applicable_control")) {   //  gst applicable  control   yes/no
                System.out.println("Change gst applicable");
                String gstApplicable = formObject.getNGValue("gst_applicable_control");
                System.out.println("gstApplicable  " + gstApplicable);
                if ("no".equalsIgnoreCase(gstApplicable)) {
                    formObject.clear("orgnization_control");
                    formObject.clear("location_code_control");
                    formObject.clear("tax_amt_control");
                    formObject.setEnabled("orgnization_control", false);
                    formObject.setEnabled("location_code_control", false);
                    formObject.setEnabled("sac_control", false);
                    formObject.setEnabled("sac_sn_code_control", false);
                    formObject.setEnabled("tax_cat_control", false);
                    formObject.setEnabled("basic_amount_control", false);
                    formObject.setEnabled("tax_amt_control", false);
                    formObject.setEnabled("basicamtbtn", false);

                } else if ("yes".equalsIgnoreCase(gstApplicable)) {
                    formObject.setEnabled("orgnization_control", true);
                    formObject.setEnabled("location_code_control", true);
                    formObject.setEnabled("sac_control", true);
                    formObject.setEnabled("sac_sn_code_control", true);
                    formObject.setEnabled("tax_cat_control", true);
                    formObject.setEnabled("basic_amount_control", true);
                    formObject.setEnabled("tax_amt_control", true);
                    settaxCat("D");
                    Oragnization("D");
                    OrgLoc("D");

                }
            }
        }
        if (pEvent.getType().name().equalsIgnoreCase("TAB_CLICKED")) {
            System.out.println("------------Inside Tab------------------");

            if (pEvent.getSource().getName().equalsIgnoreCase("Tab1")) {
            }
        }

        if (pEvent.getType().name().equalsIgnoreCase("MOUSE_CLICKED")) {
            System.out.print("------------Inside Mouse Click------------------");

            if (pEvent.getSource().getName().equalsIgnoreCase("Button5")) {
                formObject.ExecuteExternalCommand("NGAddRow", "q_cash_app_ar");// for double line error
                formObject.setNGValue("show_result", "Data inserted!!!");
            }

            if (pEvent.getSource().getName().equalsIgnoreCase("Button1")) {
                // formObject.ExecuteExternalCommand("NGModifyRow", "q_cash_app_ar");// for double line error   and 
                formObject.RaiseEvent("SAVE");
                formObject.setNGValue("show_result", "Data Updated !!!");

            }

            if (pEvent.getSource().getName().equalsIgnoreCase("Button4")) {
                // formObject.ExecuteExternalCommand("NGAddRow", "q_cash_app_ar");// for double line error  data delete
                formObject.RaiseEvent("SAVE");
                formObject.setNGValue("show_result", "Data Delete !!!");

            }

            //  save button call function on click  data insert    
            if (pEvent.getSource().getName().equalsIgnoreCase("Button2")) {
                System.out.println("Button2 Clicked for List view_kk");
                String commentsControl = formObject.getNGValue("comments_control");
                System.out.println("comment " + commentsControl);
                String userDecision = formObject.getNGValue("ca_user_decision");
                System.out.println("user  " + userDecision);
                if ("raiseexception".equalsIgnoreCase(userDecision)) {
                    if ("".equalsIgnoreCase(commentsControl)) {
                        throw new ValidatorException(new FacesMessage("Comment cannot be empty"));
                    }
                }
                formObject.ExecuteExternalCommand("NGAddRow", "q_ar_comment");
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button6")) {
                String divisionCode = formObject.getNGValue("ca_division_code");
                System.out.println("customer code   " + divisionCode);
                String Querymdm = "";
                Querymdm = "select customer_number,customer_name,customer_site_number from ar_mdm_customer_master where bu_code like '" + divisionCode + "'";
                System.out.println("customer code   " + Querymdm);
                objPicklistListenerHandler.openPickList("ca_customer_code", "customer_number,customer_name,customer_site_number", "Customer Details", 70, 70, Querymdm);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("projectbtn")) {
                //  String divisionCode = formObject.getNGValue("ca_division_code");
                String Querymdm = "";
                Querymdm = "select distinct(project_code) from mdm_ar_project_code";//mdm_project_wbs_code
                objPicklistListenerHandler.openPickList("ca_project_code", "project_code", "Project Code", 70, 70, Querymdm);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button9")) {
                String divisionCode = formObject.getNGValue("ca_division_code");
                String Querymdm = "";
                Querymdm = "select distinct(sales_order_no) from mdm_ar_sales_order_no where bu_code like '" + divisionCode + "' order by sales_order_no";
                objPicklistListenerHandler.openPickList("ca_so_no", "sales_order_no", "Sales Order Number", 70, 70, Querymdm);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button10")) {
                String divisionCode = formObject.getNGValue("ca_division_code");
                String Querymdm = "";
                Querymdm = "select distinct(sales_order_no) from mdm_ar_sales_order_no where bu_code like '" + divisionCode + "' order by sales_order_no";
                objPicklistListenerHandler.openPickList("sales_order_control", "sales_order_no", "Sales Order Number", 70, 70, Querymdm);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("calculaterate")) {
                System.out.println(" calculate total***");
                calculateCurrency();
                String totalAmount = "";
                String paymentType = "";
                String liqAmount = "";
                String newreveivedAmt = "";
                String advanceAmt = "";
                String cmiAmt = "";
                String unallocatedAmt = "";
                double recevied_amount = 0;
                double ar_amount = 0;
                double advance_Amt = 0;
                double cmi_Amt = 0;
                double unallocated_Amt = 0;
                paymentType = formObject.getNGValue("ca_payment_method");
                totalAmount = formObject.getNGValue("ca_received_amt").trim();
                liqAmount = formObject.getNGValue("amount").trim();
                advanceAmt = formObject.getNGValue("ca_advance_amount").trim();
                cmiAmt = formObject.getNGValue("ca_cmi").trim();
                unallocatedAmt = formObject.getNGValue("ca_unallocated").trim();

                System.out.println("paymentType  " + paymentType + " total amount " + totalAmount + "  liqAmount" + liqAmount);

                String arAmount = formObject.getNGValue("ca_ar_amount").trim();

//                NumberFormat currency = NumberFormat.getCurrencyInstance();
//                String TotalAmt = currency.format(recevied_amount);
//                newreveivedAmt = TotalAmt.replace("$", "");
                System.out.println("ar amount  " + ar_amount + " received  " + recevied_amount);
                if ("ar".equalsIgnoreCase(paymentType)) {
                    recevied_amount = Double.parseDouble(totalAmount);
                    ar_amount = Double.parseDouble(arAmount);
                    if (ar_amount == recevied_amount) {
                        System.out.println("ar amount  " + ar_amount + " received  " + recevied_amount);
                    } else {
                        throw new ValidatorException(new FacesMessage("AR Amount or Total Amount Received  is not equal!., Please check.", ""));
                    }
                } else if ("advance".equalsIgnoreCase(paymentType)) {
                    advance_Amt = Double.parseDouble(advanceAmt);
                    recevied_amount = Double.parseDouble(totalAmount);
                    if (advance_Amt == recevied_amount) {
                        System.out.println("Done  ");
                    } else {
                        throw new ValidatorException(new FacesMessage("Advance Amount or Total Amount Received  is not equal!., Please check.", ""));
                    }
                } else if ("cmi".equalsIgnoreCase(paymentType)) {
                    cmi_Amt = Double.parseDouble(cmiAmt);
                    recevied_amount = Double.parseDouble(totalAmount);
                    if (cmi_Amt == recevied_amount) {
                        System.out.println("Done  ");
                    } else {
                        throw new ValidatorException(new FacesMessage("Advance Against CMI Amount or Total Amount Received  is not equal!., Please check.", ""));
                    }
                } else if ("unallocated".equalsIgnoreCase(paymentType)) {
                    unallocated_Amt = Double.parseDouble(unallocatedAmt);
                    recevied_amount = Double.parseDouble(totalAmount);
                    if (unallocated_Amt == recevied_amount) {
                        System.out.println("Done  ");
                    } else {
                        throw new ValidatorException(new FacesMessage("Unallocated Amount or Total Amount Received  is not equal!., Please check.", ""));
                    }
                } else if ("combined".equalsIgnoreCase(paymentType)) {
                    String arAmount1 = formObject.getNGValue("ca_ar_amount").trim();
                    //String totalAmount = formObject.getNGValue("ca_received_amt").trim();
                    String advanceAmt1 = formObject.getNGValue("ca_advance_amount").trim();
                    String cmiAmt1 = formObject.getNGValue("ca_cmi").trim();
                    String unallocatedAmt1 = formObject.getNGValue("ca_unallocated").trim();
                    float ar_Amount = Float.parseFloat(arAmount1);
                    float total_Amount = Float.parseFloat(totalAmount);
                    float advance_Amt1 = Float.parseFloat(advanceAmt1);
                    float cmi_Amt1 = Float.parseFloat(cmiAmt1);
                    float unallocated_Amt1 = Float.parseFloat(unallocatedAmt1);
                    String combinedTotal = arAmount1 + advanceAmt1 + cmiAmt1 + unallocatedAmt1;
                    float combinedTotal1 = ar_Amount + advance_Amt1 + cmi_Amt1 + unallocated_Amt1;
                    System.out.println("combined amount " + combinedTotal);
                    System.out.println("combined amount " + combinedTotal1);
                    if (arAmount1.equalsIgnoreCase("") || advanceAmt1.equalsIgnoreCase("") || cmiAmt1.equalsIgnoreCase("") || unallocatedAmt1.equalsIgnoreCase("")) {
                        throw new ValidatorException(new FacesMessage(" Amount or Total Amount is not equal!., Please check.", ""));
                    }
                    if (combinedTotal1 == total_Amount) {
                        System.out.println("combind total  " + combinedTotal1);
                    } else {
                        throw new ValidatorException(new FacesMessage("Combined Amount or Total Amount is not equal!., Please check.", ""));

                    }
                }
                System.out.println(" calculate total***");
                calculateCurrency();
                //end code face msg
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button8")) {
                System.out.println("open invoices total***");
                String customerCode = formObject.getNGValue("ca_customer_code");
                String bu = formObject.getNGValue("ca_division_code");
                if (customerCode.equalsIgnoreCase("") || bu.equalsIgnoreCase("")) {
                    throw new ValidatorException(new FacesMessage("Customer code & Bu is not Select, Please check ", ""));
                }
                CashApp_OpenInvoices oi = new CashApp_OpenInvoices();
                oi.getOpenInvoice(customerCode, bu);
                System.out.println("Method call done for cash app");
                formObject.setEnabled("Button8", false);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("clearinvoice")) {
                System.out.println("re open invoices total***");
                formObject.clear("q_ar_open_invoice");
                formObject.clear("total_applied_amt");
                formObject.clear("Text3");
                formObject.clear("Text4");
                formObject.clear("DatePicker2");
                formObject.setEnabled("Button8", true);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button7")) {// linking apply button7
                formObject.clear("");
                float arAmt = 0.0f;
                float appliedAmt = 0.0f;
                System.out.println("Applied Amount  : " + appliedAmt);
                float total_applied_amt = 0.0f;
                float sum = 0.0f;
                float total_line_amount = 0.0f;
                String applyNo = formObject.getNGValue("Combo1");
                if ("No".equalsIgnoreCase(applyNo)) {
                    float total_applied_amt1 = Float.parseFloat(formObject.getNGValue("total_applied_amt").trim()); // 40
                    float appliedAmt1 = Float.parseFloat(formObject.getNGValue("Text4").trim());
                    System.out.println("total no  " + total_applied_amt1 + "  applied  " + appliedAmt1);
                    float sum1 = total_applied_amt1 - appliedAmt1;
                    System.out.println("sum no  " + sum1);
                    formObject.clear("total_applied_amt");
                    formObject.setNGValue("total_applied_amt", sum1);
                    // System.out.println("sum no  "+ formObject.setNGValue("total_applied_amt", sum));
                    formObject.clear("Text4");
                    formObject.setNGValue("Text4", "0");
                    System.out.println("sum no  done");
                }
                String datePick = formObject.getNGValue("DatePicker2");
                if (datePick.equalsIgnoreCase("")) {
                    System.out.println("Invoice Done ....  ");
                    throw new ValidatorException(new FacesMessage("Apply Date is Entry Missing, Please check ", ""));
                }
                appliedAmt = Float.parseFloat(formObject.getNGValue("Text4").trim());
                // total_applied_amt = Float.parseFloat(formObject.getNGValue("total_applied_amt").trim()); 
                String tam = formObject.getNGValue("total_applied_amt").trim();
                if (tam.equalsIgnoreCase("")) {
                    total_applied_amt = appliedAmt;
                } else {
                    total_applied_amt = Float.parseFloat(formObject.getNGValue("total_applied_amt").trim()); // 40
                    total_applied_amt = total_applied_amt + appliedAmt;
                }
                // total_applied_amt = total_applied_amt + appliedAmt;
                System.out.println("Applied total_applied_amt  : " + total_applied_amt);

                arAmt = Float.parseFloat(formObject.getNGValue("ca_ar_amount").trim());
                if (arAmt >= total_applied_amt) {
                    sum = total_applied_amt;
                    formObject.setNGValue("total_applied_amt", sum);
                    System.out.println("Invoice Done ....  ");
                } else {
                    sum = total_applied_amt - appliedAmt;
                    // sum=sum-appliedAmt;
                    formObject.clear("total_applied_amt");
                    formObject.setNGValue("total_applied_amt", sum);
                    throw new ValidatorException(new FacesMessage("Applied amount  can not be greater than 'AR' Amount  Please check ", ""));
                }
                formObject.ExecuteExternalCommand("NGModifyRow", "q_ar_open_invoice");
            }
        }
    }

    @Override
    public void formLoaded(FormEvent arg0) {
        // TODO Auto-generated method stub
        System.out.println(" -------------------Intiation Workstep Loaded from formloaded.----------------");
        // TODO Auto-generated method stub

        System.out.println("form Loaded called");
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("formObject :" + formObject);
        System.out.println("formConfig :" + formConfig);
        try {
            activityName = formObject.getWFActivityName();
            engineName = formConfig.getConfigElement("EngineName");
            sessionId = formConfig.getConfigElement("DMSSessionId");
            folderId = formConfig.getConfigElement("FolderId");
            serverUrl = formConfig.getConfigElement("ServletPath");
            //ServletUrl = serverUrl + "/servlet/ExternalServlet";
            processInstanceId = formConfig.getConfigElement("ProcessInstanceId");
            workItemId = formConfig.getConfigElement("WorkitemId");
            userName = formConfig.getConfigElement("UserName");
            processDefId = formConfig.getConfigElement("ProcessDefId");

            System.out.println("ProcessInstanceId===== " + processInstanceId);
            System.out.println("Activityname=====" + activityName);
            System.out.println("CabinetName====" + engineName);
            System.out.println("sessionId====" + sessionId);
            System.out.println("Username====" + userName);
            System.out.println("workItemId====" + workItemId);

//  ************************************************************************************
        } catch (Exception e) {
            System.out.println("Exception in FieldValueBagSet::::" + e.getMessage());
        }
    }

    @Override
    public void formPopulated(FormEvent arg0) {
        System.out.println("Inside formPopulated");
        String pcode = "";
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
//        activityName = formConfig.getConfigElement("ActivityName");
//        formObject.setNGValue("activity_name_control", activityName);
//        formObject.setNGValue("raised_by_control", userName);
//        formObject.setVisible("activity_name_control", false);
//        formObject.setVisible("raised_by_control", false);
//        formObject.setVisible("raised_on_control", false);
        try {
            System.out.println("set legal entity call");
            setLegalEntity();
            System.out.println("set project code");
            setProjectCode();
            System.out.println("set division code");
            divisionCode("P");
            System.out.println("set pu type entity call");
            setPU();

            if (activityName.equalsIgnoreCase("Initiator")) {
                formObject.setNGValue("ng_wi_id", processInstanceId);
                formObject.clear("exception");
                formObject.setVisible("ng_wi_id", false);
                formObject.setEnabled("exception", false);
                formObject.setEnabled("liq_list", false);
                formObject.setEnabled("Frame2", false);
                // formObject.setEnabled("Frame3", false);
                formObject.setEnabled("Frame9", false);
                formObject.setEnabled("Frame8", false);
                formObject.setEnabled("Frame7", false);
                formObject.setEnabled("Button5", false);
                formObject.setEnabled("Button1", false);
                formObject.setEnabled("Button4", false);
                formObject.setEnabled("Button7", false);
                formObject.setEnabled("Button8", false);
                formObject.setEnabled("clearinvoice", false);
                formObject.setEnabled("projectbtn", false);
                formObject.setEnabled("Button6", false);
                formObject.setEnabled("Button2", false);
                formObject.setEnabled("Button3", false);
                formObject.setEnabled("calculaterate", false);
                formObject.setEnabled("Frame13", false);
                formObject.setEnabled("Frame10", false);
                formObject.setEnabled("Button3", true);
            } /* else if (activityName.equalsIgnoreCase("RO_Commercial")) {
                formObject.clear("exception");
                formObject.clear("ca_user_decision");
                formObject.setVisible("ng_wi_id", false);
                formObject.setEnabled("Frame13", false);
                String[] onlineEnable = {"Frame2", "DatePicker2", "Button9", "Frame9", "Frame8", "Frame7", "Button5", "Button1", "Button4",
                    "Button7", "Button8", "clearinvoice", "projectbtn", "Button6", "Button2", "Button3", "calculaterate"};

                for (int i = 0; i < onlineEnable.length; i++) {
                    formObject.setEnabled(onlineEnable[i], true);
                }
                // ro Field Disable
                String[] roDisableOracle = {"exception", "total_applied_amt", "ca_drawn_bank", "ca_mode_of_payment", "liq_list", "tax_amt_control", "ca_deposit_date", "ca_cheque_date", "ca_reveived_date"};
                for (int i = 0; i < roDisableOracle.length; i++) {
                    formObject.setEnabled(roDisableOracle[i], false);
                }

                // BAAN Field Disable
                String[] miscDisableOracle = {"employee_control", "ca_bank_code", "ca_receipt_method", "businessPart_control", "misc_control", "diemention_control", "ca_bank_receipt_no", "ca_bank_receipt_date", "own_gst_reg_no_control", "cust_gst_reg_no_control"};
                for (int i = 0; i < miscDisableOracle.length; i++) {
                    formObject.setEnabled(miscDisableOracle[i], false);
                }
                formObject.addComboItem("ca_user_decision", "Select", "");
                formObject.addComboItem("ca_user_decision", "Send For Processing", "sendforprocessing");

            } else if (activityName.equalsIgnoreCase("TFS Post")) {
                formObject.clear("exception");
                formObject.clear("ca_user_decision");
                formObject.clear("location_control");
                formObject.clear("cost_center_control");
                formObject.clear("gl_code_control");
                formObject.clear("future2_control");
                formObject.setEnabled("Frame13", false);
                formObject.setVisible("ng_wi_id", false);
                String[] onlineEnable = {"Frame2", "Frame10", "Frame9", "Frame8", "Frame7", "Button5", "Button1", "Button4",
                    "Button7", "Button8", "clearinvoice", "projectbtn", "Button6", "Button2", "Button3", "calculaterate"};

                for (int i = 0; i < onlineEnable.length; i++) {
                    formObject.setEnabled(onlineEnable[i], true);
                }

                // TFS Field Disable
                String[] tfsDisableOracle = {"exception", "location_code_control", "tax_cat_control", "sac_sn_code_control", "Text4", "Combo1", "Button9", "total_applied_amt", "Button5", "Button1", "Button4", "basic_amount_control",
                    "Button7", "Button8", "clearinvoice", "projectbtn", "Button6", "Button2", "Button3", "calculaterate", "amount_control", "liq_list", "ca_mode_of_business", "ca_payment_method", "ca_type_adv_ar_unallocated", "ca_bank_receipt_no", "ca_bank_receipt_date", "ca_company_name", "ca_transaction_type",
                    "ca_division_code", "ca_mode_of_payment", "ca_transaction", "ca_pu_name", "ca_so_no", "ca_project_code", "ca_type_receipt", "ca_division_type", "ca_multidivision_name", "ca_payment_type", "ca_advance_amount",
                    "ca_cmi", "ca_ar_amount", "ca_unallocated", "ca_received_amt", "ca_customer_code", "ca_customer_name", "ca_customer_site", "Button6", "Button7", "Button8",
                    "ca_currency_type", "ca_rate_type", "ca_rate", "ca_reveived_date", "ca_deposit_date", "orgnization_control", "location_control", "sac_control", "sac_sn_control", "sales_order_control", "Button10", "deduction_accept_not_control",
                    "deduction_control", "type_deduction_control", "ca_drawn_bank", "tax_amt_control", "own_gst_reg_no_control", "cust_gst_no_control", "division_control", "gst_applicable_control", "location_control", "cost_center_control", "gl_code_control", "project_code_control", "pu_code_f1", "future2_control"};
                for (int i = 0; i < tfsDisableOracle.length; i++) {
                    formObject.setEnabled(tfsDisableOracle[i], false);
                }

                String[] tfsEditablOracle = {"Button3", "ca_narration", "ca_booking_date"};
                for (int i = 0; i < tfsEditablOracle.length; i++) {
                    formObject.setEnabled(tfsEditablOracle[i], true);
                }

                // ro Field Disable
                String[] roDisableOracle = {"exception", "DatePicker2", "ca_mode_of_payment", "liq_list", "tax_amt_control", "ca_deposit_date", "ca_cheque_date", "ca_reveived_date"};
                for (int i = 0; i < roDisableOracle.length; i++) {
                    formObject.setEnabled(roDisableOracle[i], false);
                }

                // BAAN Field Disable
                String[] miscDisableOracle = {"employee_control", "ca_bank_code", "ca_receipt_method", "businessPart_control", "misc_control", "diemention_control", "ca_bank_receipt_no", "ca_bank_receipt_date", "own_gst_reg_no_control", "cust_gst_reg_no_control"};
                for (int i = 0; i < miscDisableOracle.length; i++) {
                    formObject.setEnabled(miscDisableOracle[i], false);
                }

                formObject.addComboItem("ca_user_decision", "--Select--", "");
                formObject.addComboItem("ca_user_decision", "Post", "post");
                formObject.addComboItem("ca_user_decision", "Raised Exception", "raiseexception");
                costCenter();
                System.out.println("end code tfs post ");
            } else if (activityName.equalsIgnoreCase("Posting_response")) {
                System.out.println("inside posting response ");
                formObject.setVisible("ng_wi_id", false);

                String[] onlineEnable = {"exception", "Button9", "Button10", "Frame10", "ca_mode_of_payment", "liq_list", "Frame2", "Frame13", "Frame9", "Frame8", "Frame7", "Button5", "Button1", "Button4",
                    "Button7", "Button8", "clearinvoice", "projectbtn", "Button6", "Button2", "Button3", "calculaterate"};

                for (int i = 0; i < onlineEnable.length; i++) {
                    formObject.setEnabled(onlineEnable[i], false);
                }
                formObject.setEnabled("Button3", true);
                // processInstanceId = formConfig.getConfigElement("ProcessInstanceId");
                String flags = formObject.getNGValue("ca_division_type");
                String ngId = formObject.getNGValue("ng_wi_id");
                System.out.println("flags " + flags + " ngId  " + ngId);
                getResponse getresponse = new getResponse();
                getresponse.getres(flags, ngId);
                System.out.println("posting code end ");
            }
             */ else {
                formObject.setEnabled("ca_user_decision", false);
            }
        } catch (Exception e) {
            System.out.println("Exception in FieldValueBagSet::::" + e.getMessage());
        }

        /*
        System.out.println("set setDocCategory call");
        setDocCategory("P");
        System.out.println("set setPU call");
        Oragnization("P");
        System.out.println("set division");
        //  setSalesOrderNo("P");
        System.out.println("set pu type entity call");
        OrgLoc("P");

        System.out.println("set division code");
        // divisionCode("P");

        System.out.println("set tax cat type entity call");
        //  settaxCat("P");
        System.out.println("code end response ");

        formObject.addComboItem("deduction_accept_not_control", "Select", "");
        formObject.addComboItem("deduction_accept_not_control", "ACCEPTED", "accepted");
        formObject.addComboItem("deduction_accept_not_control", "NOT ACCEPTED", "notaccepted");

        // type of transaction 
        formObject.addComboItem("type_of_transaction", "Select", "");
        formObject.addComboItem("type_of_transaction", "GENERAL", "general");
        formObject.addComboItem("type_of_transaction", "MULTI DEDUCTION", "multideduction");
        formObject.addComboItem("type_of_transaction", "MULTI DIVISION", "multidivision");
         */
    }

    @Override
    public void saveFormCompleted(FormEvent arg0) throws ValidatorException {
        // TODO Auto-generated method stub
        System.out.print("-------------------save form completed---------");
    }

    @Override
    public void saveFormStarted(FormEvent arg0) throws ValidatorException {
        // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

    }

    @Override
    public void submitFormCompleted(FormEvent arg0) throws ValidatorException {
        // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

    }

    @Override
    public void submitFormStarted(FormEvent arg0) throws ValidatorException {
        // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String useraction = "";
        useraction = formObject.getNGValue("ca_user_decision");

        //user action decision mandatory 
        if (activityName.equalsIgnoreCase("Initiator")) {
            String modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("user  " + useraction);
            if ("online".equalsIgnoreCase(modeofPayment)) {
                System.out.println("user  " + modeofPayment);
            } else if ("cheque".equalsIgnoreCase(modeofPayment)) {
                if ("".equalsIgnoreCase(useraction)) {
                    throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
                }
            }
        }

        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            String modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("user  " + useraction);
            if ("online".equalsIgnoreCase(modeofPayment)) {
                System.out.println("user  " + modeofPayment);
                if ("".equalsIgnoreCase(useraction)) {
                    throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
                }
            }
        }

        if (activityName.equalsIgnoreCase("TFS Post")) {
            if ("".equalsIgnoreCase(useraction)) {
                throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
            }
        }

        if (activityName.equalsIgnoreCase("Initiator")) {
            System.out.println("Checking for Document list and Doc category");
            System.out.println(" cheque document " + formObject.getNGValue("CheckBox16"));
            System.out.println(" cheque document " + formObject.getNGValue("CheckBox1"));
            String modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            if ("cheque".equalsIgnoreCase(modeofPayment)) {
                if (formObject.getNGValue("CheckBox16").equalsIgnoreCase("true")
                        && formObject.getNGValue("CheckBox1").equalsIgnoreCase("false")
                        && formObject.getNGValue("CheckBox2").equalsIgnoreCase("false")) {
                    throw new ValidatorException(new FacesMessage("Please Take All document select. ", ""));
                } else if (formObject.getNGValue("CheckBox16").equalsIgnoreCase("true")
                        && formObject.getNGValue("CheckBox1").equalsIgnoreCase("true")
                        && formObject.getNGValue("CheckBox2").equalsIgnoreCase("true")) {
                    // throw new ValidatorException(new FacesMessage("Please Take All document select. ", ""));
                } else if (formObject.getNGValue("CheckBox16").equalsIgnoreCase("false")
                        && formObject.getNGValue("CheckBox1").equalsIgnoreCase("false")
                        && formObject.getNGValue("CheckBox2").equalsIgnoreCase("false")) {
                    throw new ValidatorException(new FacesMessage("Please check Document check list, before submission", ""));
                } else if (formObject.getNGValue("CheckBox16").equalsIgnoreCase("true")) {
                    throw new ValidatorException(new FacesMessage("Please Take All document select. ", ""));
                } else if (formObject.getNGValue("CheckBox1").equalsIgnoreCase("true")) {
                    throw new ValidatorException(new FacesMessage("Please Take All document select. ", ""));
                } else if (formObject.getNGValue("CheckBox2").equalsIgnoreCase("true")) {
                    throw new ValidatorException(new FacesMessage("Please Take All document select. ", ""));
                }
            }
        }
        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            String modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            if ("online".equalsIgnoreCase(modeofPayment)) {
                if (formObject.getNGValue("CheckBox9").equalsIgnoreCase("false")
                        && formObject.getNGValue("CheckBox11").equalsIgnoreCase("false")
                        && formObject.getNGValue("CheckBox13").equalsIgnoreCase("false")) {
                    throw new ValidatorException(new FacesMessage("Please Take only one Document.", ""));
                } else if (formObject.getNGValue("CheckBox9").equalsIgnoreCase("true")
                        || formObject.getNGValue("CheckBox11").equalsIgnoreCase("true")
                        || formObject.getNGValue("CheckBox13").equalsIgnoreCase("true")) {
                    System.out.println("Done");
                    // throw new ValidatorException(new FacesMessage("Only one  Document is selected.", ""));
                } else if (formObject.getNGValue("CheckBox9").equalsIgnoreCase("true")) {
                    System.out.println("Done");
                    // throw new ValidatorException(new FacesMessage("Only one  Document is selected.", ""));
                } else if (formObject.getNGValue("CheckBox11").equalsIgnoreCase("true")) {
                    System.out.println("Done");
                    // throw new ValidatorException(new FacesMessage("Only one  Document is selected.", ""));
                } else if (formObject.getNGValue("CheckBox13").equalsIgnoreCase("true")) {
                    System.out.println("Done");
                    // throw new ValidatorException(new FacesMessage("Only one  Document is selected.", ""));
                }
            }
        }

        if (activityName.equalsIgnoreCase("Initiator")) {
            String modeOfPayment = "";
            modeOfPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("chequ date   " + modeOfPayment);
            if (modeOfPayment.equalsIgnoreCase("cheque")) {

                long diff;
                int diffDays = 0;
                try {
                    //String date1 = "07/4/2020";
                    String userdate = formObject.getNGValue("ca_cheque_date");
                    String format = "dd/MM/yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    Date userdateObj1 = sdf.parse(userdate);
                    Date currentdate = Calendar.getInstance().getTime();

                    System.out.println(userdateObj1);
                    System.out.println(currentdate + "\n");
                    diff = currentdate.getTime() - userdateObj1.getTime();
                    diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    System.out.println("difference between days: " + diffDays);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (diffDays > 90) {
                    System.out.println("difference between 90 ++ " + diffDays);
                    throw new ValidatorException(new FacesMessage("Check DD /UTR Date is not select before 3 months., Please check.", ""));
                } else {
                    System.out.println("difference between 90-- " + diffDays);
                    // throw new ValidatorException(new FacesMessage("Please Take only one Document.", ""));

                }
            }
        }
        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            String modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            if ("online".equalsIgnoreCase(modeofPayment)) {
                String liqAmount = formObject.getNGValue("amount").trim();
                String functionalAmt = formObject.getNGValue("ca_functional_amt").trim();
                System.out.println("liq amt " + liqAmount + "  function  " + functionalAmt);
                double liq_Amount = Double.parseDouble(liqAmount);
                double functional_Amt = Double.parseDouble(functionalAmt);
                System.out.println("liq amt " + liq_Amount + "  function  " + functional_Amt);
                if (liq_Amount == functional_Amt) {
                    System.out.println("Done  kuldeep" + liq_Amount);
                } else {
                    throw new ValidatorException(new FacesMessage("Functional Amount and liquidice Amount is not equal!., Please check.", ""));
                }
            }
        }
        //functional amount and liquidice amount is  equal  code
        // line details total amount alert
        if (activityName.equalsIgnoreCase("Initiator")) {
            System.out.println("inside line code");
            String modeOfPayment = "";
            modeOfPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("chequ date   " + modeOfPayment);
            if (modeOfPayment.equalsIgnoreCase("cheque")) {
                fieldMadatry();
                lineAmount();

            }
            System.out.println("inside end code");
        }
        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            System.out.println("inside line code");
            String modeOfPayment = "";
            modeOfPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("chequ date   " + modeOfPayment);
            if (modeOfPayment.equalsIgnoreCase("online")) {
                fieldMadatry();
                lineAmount();
                deleteLiqSeq();
            }
            System.out.println("inside end code");
        }

        if (activityName.equalsIgnoreCase("TFS Post") && (useraction.equalsIgnoreCase("post"))) {
            System.out.println("Checking for line entry");
            List<List<String>> result_lineamt = null;
            String customerName = formObject.getNGValue("ca_customer_name");
            String customerCode = formObject.getNGValue("ca_customer_code");
            String divisionCode = formObject.getNGValue("ca_division_code");
            String chequeNo = formObject.getNGValue("ca_cheque_no");
            System.out.println("before db connect");
            String response = "N";
            int openinvoicerow = formObject.getLVWRowCount("q_ar_open_invoice");
            if (openinvoicerow > 0) {
                System.out.println("Calling openinvoice payment");
                CashApp_OpenInvoices ca_oi = new CashApp_OpenInvoices();
                response = ca_oi.setPrapay(customerCode, customerName, divisionCode, chequeNo);
                if (response.equalsIgnoreCase("Y")) {
                    System.out.println("Getting response ***** response : " + response);
                } else {
                    System.out.println("Getting response ***** response : " + response);
                    throw new ValidatorException(new FacesMessage("Line Level Entry Missing, Please check ", ""));
                }
                System.out.println("open invoice  entry done ");
            }

            System.out.println("Calling connect()  method");
            connect();
            System.out.println("after db connect");
        }

        if (activityName.equalsIgnoreCase("RO_Commercial") && (useraction.equalsIgnoreCase("sendforprocessing"))) {
            System.out.println("Checking for line entry");
            List<List<String>> result_lineamt = null;
            int rowcount = formObject.getLVWRowCount("q_cash_app_ar");
            System.out.println("row count " + rowcount);

            if (rowcount < 1) {
                throw new ValidatorException(new FacesMessage("Line Details Entry Missing, Please check ", ""));
            }
        }

        if (activityName.equalsIgnoreCase("Posting_response")) {
            System.out.println("inside deduction call...");
            multiDeductionValidation();
        }

//  end code  ro 
    }

    @Override
    public void initialize() {
        System.out.print("-------------------initialize form completed---------");
    }

    @Override
    public String encrypt(String string) {
        System.out.print("-------------------encrypt form completed---------");
        return null;
    }

    @Override
    public String decrypt(String string) {
        System.out.print("-------------------decrypt form completed---------");
        return null;
    }

    //   MDm data fetch on combo...
    private void setCurrencyCode() {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String Query_mdm_currency_code = "select from_currency from mdm_exchange_rate"; //bu mdm table
        List<List<String>> result15 = null;
        try {
            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            formObject.addComboItem("ca_currency_type", "Select", "");
            for (int i = 0; i <= result15.size(); i++) {
                String company = result15.get(i).get(0);
                //  System.out.println("description and bucode : " + company + ", " + company);
                formObject.addComboItem("ca_currency_type", company, company);
            }
            formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after mdm_currency_code");
    }

    // mdm_cc    cost center get data from mdm
    private void costCenter() {
        System.out.println("cost center inside kp");
        String Query_mdm_currency_code = "select location, cost_center, gl_code, future2 from mdm_segma"; // mdm table
        List<List<String>> result15 = null;
        try {
            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            formObject.addComboItem("location_control", "select", "");
            formObject.addComboItem("cost_center_control", "select", "");
            formObject.addComboItem("gl_code_control", "select", "");
            formObject.addComboItem("future2_control", "select", "");

            for (int i = 0; i <= result15.size(); i++) {
                String location = result15.get(i).get(0);
                String cost_center = result15.get(i).get(1);
                String gl_code = result15.get(i).get(2);
                String future1 = result15.get(i).get(3);

                formObject.addComboItem("location_control", location, location);
                formObject.addComboItem("cost_center_control", cost_center, cost_center);
                formObject.addComboItem("gl_code_control", gl_code, gl_code);
                formObject.addComboItem("future2_control", future1, future1);
            }
            //  formObject.RaiseEvent("SAVE");

        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after cost center");
    }

    //mdm_doc_category  doc category get data from mdm
    private void setDocCategory(String flag) {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        List<List<String>> result1 = null;
        String getsetDocument = "";
        formObject.clear("ca_documnt_category");
        System.out.println("inside ___ setDOcument");
        if (flag.equalsIgnoreCase("P")) {
            if (bucode.equalsIgnoreCase("")) {
                System.out.println(" Bu code is null");
                getsetDocument = "select document_category,bu_code from ar_mdm_doc_category";
            } else {
                System.out.println(" BU code is not Null");
                getsetDocument = "select document_category,bu_code from ar_mdm_doc_category where bu_code like '" + bucode + "'";
            }
        } else {
            bucode = formObject.getNGValue("ca_division_code");
            getsetDocument = "select document_category,bu_code from ar_mdm_doc_category where bu_code like '" + bucode + "'";
        }
        System.out.println("getsetDocument query ***** " + getsetDocument);
        result1 = formObject.getDataFromDataSource(getsetDocument);
        formObject.addComboItem("ca_documnt_category", "select", "");
        for (int i = 0; i < result1.size(); i++) {
            // System.out.println("all document data");
            formObject.addComboItem("ca_documnt_category", result1.get(i).get(0), result1.get(i).get(0));
        }
    }

    private void setSalesOrderNo(String flag) {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        List<List<String>> result1 = null;
        String getSalesOrderNo = "";
        formObject.clear("ca_so_no");
        formObject.clear("sales_order_control");
        System.out.println("inside ___ so no");
        if (flag.equalsIgnoreCase("P")) {
            if (bucode.equalsIgnoreCase("")) {
                System.out.println(" Bu code is null");
                getSalesOrderNo = "select distinct(sales_order_no) from mdm_ar_sales_order_no order by sales_order_no";
            } else {
                System.out.println(" BU code is not Null");
                getSalesOrderNo = "select distinct(sales_order_no) from mdm_ar_sales_order_no where bu_code like '" + bucode + "' order by sales_order_no";
            }
        } else {
            bucode = formObject.getNGValue("ca_division_code");
            getSalesOrderNo = "select distinct(sales_order_no) from mdm_ar_sales_order_no where bu_code like '" + bucode + "' order by sales_order_no";
        }
        System.out.println("getSalesOrderNo query ***** " + getSalesOrderNo);
        result1 = formObject.getDataFromDataSource(getSalesOrderNo);
        formObject.addComboItem("ca_so_no", "select", "");
        formObject.addComboItem("sales_order_control", "select", "");
        for (int i = 0; i < result1.size(); i++) {
            // System.out.println("all getSalesOrderNo data");
            formObject.addComboItem("ca_so_no", result1.get(i).get(0), result1.get(i).get(0));
            formObject.addComboItem("sales_order_control", result1.get(i).get(0), result1.get(i).get(0));
        }
    }

    private void OrgLoc(String flag) {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        System.out.println("bu code " + bucode);
        List<List<String>> result1 = null;
        String getOrgLoc = "";
        formObject.clear("location_code_control");
        System.out.println("inside ___ org loc");
        if (flag.equalsIgnoreCase("P")) {
            if (bucode.equalsIgnoreCase("")) {
                System.out.println("division code is location");
                getOrgLoc = "select distinct(inv_location) from mdm_thermax_gstn";
            } else {
                System.out.println("bu code " + bucode);
                getOrgLoc = "select distinct(inv_location) from mdm_thermax_gstn where bu_code like '" + bucode + "'";

                System.out.println("bu code " + bucode + " query " + getOrgLoc);
            }
        } else {

            System.out.println("org  data --" + bucode);
            getOrgLoc = "select distinct(inv_location) from mdm_thermax_gstn where bu_code like '" + bucode + "'";
            System.out.println("org data --" + getOrgLoc);
        }

        System.out.println("getOrgLoc query ***** " + getOrgLoc);
        result1 = formObject.getDataFromDataSource(getOrgLoc);
        formObject.addComboItem("location_code_control", "select", "");

        for (int i = 0; i < result1.size(); i++) {
            // System.out.println("all getOrgLoc data");
            formObject.addComboItem("location_code_control", result1.get(i).get(0), result1.get(i).get(0));
        }
    }

    private void Oragnization(String flag) {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        System.out.println("division code " + bucode);
        List<List<String>> result1 = null;
        String getOrgLoc = "";
        formObject.clear("orgnization_control");
        System.out.println("inside ___ org loc");
        if (flag.equalsIgnoreCase("P")) {
            if (bucode.equalsIgnoreCase("")) {
                System.out.println("org cod null");
                getOrgLoc = "select distinct(inv_org) from mdm_thermax_gstn";
            } else {
                System.out.println("div code " + bucode);
                getOrgLoc = "select distinct(inv_org) from mdm_thermax_gstn where bu_code like '" + bucode + "'";

                System.out.println("bu code " + bucode + " query " + getOrgLoc);
            }
        } else {
            bucode = formObject.getNGValue("ca_division_code");
            System.out.println("org  data --" + bucode);
            getOrgLoc = "select distinct(inv_org) from mdm_thermax_gstn where bu_code like '" + bucode + "'";
            System.out.println("org data --" + getOrgLoc);
        }
        System.out.println("getOrgLoc query ***** " + getOrgLoc);
        result1 = formObject.getDataFromDataSource(getOrgLoc);
        formObject.addComboItem("orgnization_control", "select", "");
        for (int i = 0; i < result1.size(); i++) {
            // System.out.println("all getOrgLoc data");
            formObject.addComboItem("orgnization_control", result1.get(i).get(0), result1.get(i).get(0));
        }
    }

    private void setProjectCode() {
        List<List<String>> result1 = null;
        String getProjectCode = "";
        // formObject.clear("project_code_control");
        System.out.println("inside ___ getProjectCode");
        getProjectCode = "select project_code from mdm_ar_project_code order by project_code";//mdm_project_wbs_code
        System.out.println("getProjectCode query ***** " + getProjectCode);
        try {
            result1 = formObject.getDataFromDataSource(getProjectCode);
            formObject.addComboItem("project_code_control", "select", "");
            for (int i = 0; i < result1.size(); i++) {
                // System.out.println("all getProjectCode data");
                formObject.addComboItem("project_code_control", result1.get(i).get(0), result1.get(i).get(0));
            }
        } catch (Exception e) {
            System.out.println("project code name unit error " + e);
        }
    }

    //setPU table  mdm_performance_unit get data from mdm
    private void setPU() {

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        String Query_mdm_currency_code = "select pu_code from mdm_performance_unit"; // mdm table
        System.out.println("pu " + Query_mdm_currency_code);
        List<List<String>> result15 = null;
        try {
            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            formObject.addComboItem("pu_code_f1", "Select", "");
            for (int i = 0; i <= result15.size(); i++) {
                String description = result15.get(i).get(0) + " : " + result15.get(i).get(0);
                String company = result15.get(i).get(0);
                // System.out.println("description and pu : " + description + ", " + company);
                formObject.addComboItem("ca_pu_name", company, company);
                formObject.addComboItem("pu_code_f1", company, company);
            }
            formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after mdm_performance_unit");
    }

    private void setLegalEntity() {
        System.out.println("legal entity");
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String Query_mdm_currency_code = "select distinct(legal_entity) from mdm_bu;"; // mdm table
        System.out.println("1legal +" + Query_mdm_currency_code);
        List<List<String>> result15 = null;
        try {
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            System.out.println("legal " + result15);
            formObject.addComboItem("ca_company_name", "Select", "");
            for (int i = 0; i <= result15.size(); i++) {
                String legalEntity = result15.get(i).get(0);
                // System.out.println("description and bu : " + legalEntity + ", " + legalEntity);
                formObject.addComboItem("ca_company_name", legalEntity, legalEntity);
            }
            formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after mdm_performance_unit");
    }

    private void settaxCat(String flag) {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        List<List<String>> result11 = null;
        String getOrgLoc = "";
        formObject.clear("tax_cat_control");
        System.out.println("inside ___ tax category");
        if (flag.equalsIgnoreCase("P")) {
            if (bucode.equalsIgnoreCase("")) {
                System.out.println(" Bu code is null");
                getOrgLoc = "select gst_category from mdm_gst_tax_master";
            } else {
                System.out.println(" BU code is not Null");
                getOrgLoc = "select gst_category from mdm_gst_tax_master where bu_code like '" + bucode + "'";
            }
        } else {
            bucode = formObject.getNGValue("ca_division_code");
            getOrgLoc = "select gst_category from mdm_gst_tax_master where bu_code like '" + bucode + "'";
        }
        System.out.println("getOrgLoc query ***** " + getOrgLoc);
        result11 = formObject.getDataFromDataSource(getOrgLoc);
        formObject.addComboItem("tax_cat_control", "select", "");
        for (int i = 0; i < result11.size(); i++) {
            //System.out.println("all Tax category data");
            formObject.addComboItem("tax_cat_control", result11.get(i).get(0), result11.get(i).get(0));
        }
    }

    private void setDeductionType() {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String Query_mdm_currency_code = "select type_of_deduction from mdm_ar_type_of_deduction"; // mdm table
        List<List<String>> result15 = null;
        try {
            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            formObject.addComboItem("type_deduction_control", "select", "");
            formObject.addComboItem("type_deduction_control", "NA", "NA");
            for (int i = 0; i <= result15.size(); i++) {
                String deductionType = result15.get(i).get(0);
                //  System.out.println("description and deduction : " + deductionType + ", " + deductionType);

                formObject.addComboItem("type_deduction_control", deductionType, deductionType);
            }
            formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after deduction type");
    }

    private void divisionCode(String flag) {
        try {
            formObject = FormContext.getCurrentInstance().getFormReference();
            formConfig = FormContext.getCurrentInstance().getFormConfig();
            String queryBU = "";
            String entity = formObject.getNGValue("ca_company_name");
            System.out.println("entity name : " + entity);
            if (flag.equalsIgnoreCase("P")) {
                System.out.println("division code is null");
                queryBU = "select bu_desc, bu_code from mdm_bu order by bu_code";
//            if (entity.equalsIgnoreCase("")) {
//                System.out.println("division code is null");
//                queryBU = "select bu_desc, bu_code from mdm_bu order by bu_code";
//            } 
//            else {
//                System.out.println("division code code is not Null");
//                queryBU = "select bu_desc, bu_code from mdm_bu where legal_entity like '" + entity + "' order by bu_code";
//            }

            } else {
                queryBU = "select bu_desc, bu_code from mdm_bu where legal_entity like '" + entity + "' order by bu_code";
                System.out.println("entity name : " + queryBU);
            }

            System.out.println("queryBU : " + queryBU);
            List<List<String>> result = null;
            result = formObject.getDataFromDataSource(queryBU);
            formObject.addComboItem("ca_division_code", "Select", "");
            formObject.addComboItem("division_control", "Select", "");
            for (int i = 0; i < result.size(); i++) {
                String description3 = result.get(i).get(1) + " : " + result.get(i).get(0);
                String loc_code = result.get(i).get(1);
                formObject.addComboItem("ca_division_code", description3, loc_code);
                formObject.addComboItem("division_control", description3, loc_code);
            }
            formObject.RaiseEvent("SAVE");

        } catch (Exception e) {
            System.out.println("company name unit error " + e);
        }
    }

    // customer liquidice transaction data
    private void liqTranssction(String flag) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String Query_mdm_currency_code = "";
        //  Query_mdm_currency_code = "select date,seq_no from mdm_ar_liquidice_report where lock NOT IN ('Y')"; // mdm table
        List<List<String>> result15 = null;
        try {
            if (flag.equalsIgnoreCase("P")) {
                Query_mdm_currency_code = "select date,seq_no from mdm_ar_liquidice_report where lock NOT IN ('Y')";
            } else {
                Query_mdm_currency_code = "select date,seq_no from mdm_ar_liquidice_report where lock NOT IN ('Y')";
            }
            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            formObject.addComboItem("liq_list", "select", "");
            // formObject.addComboItem("type_deduction_control", "NA", "NA");
            for (int i = 0; i <= result15.size(); i++) {
                String sequenceDate = result15.get(i).get(0) + " : " + result15.get(i).get(1);
                String sequenceNo = result15.get(i).get(1);
                //  System.out.println("description and liq_list : " + sequenceDate + ", " + sequenceNo);

                formObject.addComboItem("liq_list", sequenceDate, sequenceNo);
            }
            formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after liq_list type");
    }

    // oracle db connect method 
    public void connect() {

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        workItemId = formConfig.getConfigElement("WorkitemId");
        System.out.println("work item id  " + workItemId);

        String values = "";
        String insertFlag = "";

        String org_id = formObject.getNGValue("org_id");

        //	String invoice_number = formObject.getNGValue("invoicenumber");
        //	String invoice_date = formObject.getNGValue("invoicedate");// invoice date
        String payment_term = "";
        String lib_combination = "";
        String intended_use = "";
        String Action = "CREATE_RECEIPT";
        String activityName1 = "";
        String workItemId_no = formObject.getNGValue("processid");
        activityName1 = formObject.getWFActivityName();
        List<List<String>> result3 = null;
        // cash app get data from form
        String divisionCode = formObject.getNGValue("ca_division_code");  //lov
        String receiptMethodDoc = formObject.getNGValue("ca_documnt_category");  // lov   ca_documnt_category
        String typeofReceipt = formObject.getNGValue("ca_type_adv_ar_unallocated");  // ca_type_adv_ar_unallocated
        String bankReceiptNo = formObject.getNGValue("ca_bank_receipt_no");
        String ReceviedAmt = formObject.getNGValue("ca_received_amt");
        String drawnBank = formObject.getNGValue("ca_drawn_bank");
        String currencyType = formObject.getNGValue("ca_currency_type");
        String bankReceiptDate = formObject.getNGValue("ca_bank_receipt_date");
        String depositDate = formObject.getNGValue("ca_deposit_date");
        String narration = formObject.getNGValue("ca_narration_n");
        // add new data
        String chequeDate = formObject.getNGValue("ca_cheque_date");
        String chequeNo = formObject.getNGValue("ca_cheque_no");
        // String activityname = formObject.getNGValue("Text60");
        String division = formObject.getNGValue("division_control");
        String exchangeRate = formObject.getNGValue("ca_rate");
        String ratType = formObject.getNGValue("ca_rate_type");
        String customerName = formObject.getNGValue("ca_customer_name");
        String customerSite = formObject.getNGValue("ca_customer_site");
        String customerCode = formObject.getNGValue("ca_customer_code");
        String salesOrderNo = formObject.getNGValue("ca_so_no");
        String projectCode = formObject.getNGValue("ca_project_code");
        String glDate = formObject.getNGValue("ca_booking_date");
        String divisionType = formObject.getNGValue("ca_division_type");

        System.out.println("customerCode Code " + customerCode + "flag " + divisionType + " " + salesOrderNo + "activity name " + activityName1 + " " + chequeNo + " " + drawnBank + " " + currencyType + " " + bankReceiptDate + " " + depositDate + " " + narration);

        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.100.1.232:1601:R12UAT", "apps", "r12uatapps");

            String row_count = "select tax_amount,tax_category_code,business_unit, location,cost_center, gl_code, wbs_code, future1, future2 from cmplx_cash_app_ar where procid like '"
                    + processInstanceId + "'";
            System.out.println(" query to get the list view data , row count query sql :" + row_count);
            result3 = formObject.getDataFromDataSource(row_count);
            System.out.println(" size of result3 : " + result3.size());
            String taxAmount = "";
            String taxCategory = "";
            String segma1 = "";
            String segma2 = "";
            String segma3 = "";
            String segma4 = "";
            String segma5 = "";
            String segma6 = "";
            String segma7 = "";
            for (int i = 0; i < result3.size(); i++) {
                taxAmount = result3.get(i).get(0);
                taxCategory = result3.get(i).get(1);
                segma1 = result3.get(i).get(2);
                segma2 = result3.get(i).get(3);
                segma3 = result3.get(i).get(4);
                segma4 = result3.get(i).get(5);
                segma5 = result3.get(i).get(6);
                segma6 = result3.get(i).get(7);
                segma7 = result3.get(i).get(8);

                System.out.println("cmplx data " + taxAmount + " " + taxCategory + " " + segma1 + " " + segma2 + " " + segma3);
                values = "('" + divisionCode + "','" + receiptMethodDoc + "','" + typeofReceipt + "','" + chequeNo + "',(TO_DATE('" + chequeDate + "','dd/mm/yyyy')),'" + ReceviedAmt + "',(TO_DATE('" + depositDate + "','dd/mm/yyyy')),'" + drawnBank + "','"
                        + currencyType + "','" + activityName1 + "','" + narration + "','" + segma1 + "','" + segma2 + "','"
                        + segma3 + "','" + segma4 + "','" + segma5 + "','" + segma6 + "','" + segma7 + "','" + exchangeRate + "','" + "" + "','" + customerName + "','" + customerSite + "','" + customerCode + "','" + Action + "','" + salesOrderNo + "','" + projectCode + "','" + processInstanceId + "',(TO_DATE('" + glDate + "','dd/mm/yyyy')),'" + divisionType + "')";

                String query_column = "(OPERATING_UNIT,METHOD_SOURCE,TYPE,AR_NUMBER,CHEQUE_DATE,RECEIPT_AMOUNT,DEPOSIT_DATE,BANK_NAME,CURRENCY_CODE,ACTIVITY_NAME,COMMENT_NARRATION,GL_ACCOUNT1, "
                        + "GL_ACCOUNT2,GL_ACCOUNT3,GL_ACCOUNT4,GL_ACCOUNT5,GL_ACCOUNT6,GL_ACCOUNT7,EXCHANGE_RATE,RATE_TYPE,CUSTOMER_NAME,CUSTOMER_SITE,CUSTOMER_NUMBER,ACTION,ATTRIBUTE1,ATTRIBUTE2,ATTRIBUTE10,RECEIPT_DATE,MULTIDIVISON_FLAG)";

                String sql = "Insert into xxtmx_ar_receipt_stg_tbl" + query_column + " values " + values;
                System.out.println("query " + sql);
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.executeUpdate(sql);
                insertFlag = "Y";
                System.out.println("record inserted");
            }
            con.close();
        } catch (SQLException e) {
            insertFlag = "N";
            System.out.println("connection exception" + e);
        }

        if (insertFlag.equalsIgnoreCase("N") || insertFlag.equalsIgnoreCase("")) {
            throw new ValidatorException(new FacesMessage(" There is some connectivity problem with Oralce staging table, Please try again after some time", ""));
        }
    }

    private void calculateCurrency() {
        System.out.println("*** Calculating currency*** ");
        formObject.clear("ca_functional_amt");
        double total_amount;
        double defoultValue = 1;
        String totalAmt = formObject.getNGValue("ca_received_amt").trim();
        String exchangerate = formObject.getNGValue("ca_rate").trim();
        System.out.println("totalAmt***  " + totalAmt);
        System.out.println("exchangerate **" + exchangerate);
        if (totalAmt.equalsIgnoreCase("")) {
            throw new ValidatorException(new FacesMessage("Amount and exchange rate is missing!,Please check", ""));
        }
        double totalamt = Double.parseDouble(totalAmt);
        double exchange_rate;
        // float defoultVal=Float.parseFloat(defoultValue);
        System.out.println("Values in float " + totalamt + " , " + defoultValue);
        if (exchangerate.equalsIgnoreCase("")) {
            total_amount = totalamt * defoultValue;
        } else {
            exchange_rate = Double.parseDouble(exchangerate);
            total_amount = totalamt * exchange_rate;
        }
        double roundOff = Math.round(total_amount * 100.0) / 100.0;
        System.out.println("total_amount " + roundOff);
//        NumberFormat currency = NumberFormat.getCurrencyInstance();
//        String TotalAmt = currency.format(total_amount);
//        String newStr = TotalAmt.replace("$", "");
//        String newStr = TotalAmt.replace("$", "").replace(",", "");
      //  System.out.println("kpal " + total_amount);
        formObject.setNGValue("ca_functional_amt", roundOff);

    }

    //line details amount field 
    private void lineAmount() {
        String paymentType = "";
        String getadvanceAmount = "";
        String getcmi = "";
        String getUnallocated = "";
        float total_line_amount = 1;
        float getadvance_Amount = 0;
        float getcmi_amt = 0;
        float getUnallocated_amt = 0;
        String Query_cmplx_cash_app = "select SUM(CAST(amount AS FLOAT)) from cmplx_cash_app_ar where procid like '" + processInstanceId + "'";
        List<List<String>> resultcash = null;
        System.out.println("cmplx  " + Query_cmplx_cash_app);
        try {
            System.out.println("inside try");
            resultcash = formObject.getDataFromDataSource(Query_cmplx_cash_app);
            System.out.println("cash app   " + resultcash);
            total_line_amount = Float.parseFloat(resultcash.get(0).get(0));

            System.out.println("kp 82 done");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }

        System.out.println("total amount total_line_amount " + total_line_amount);
        getadvanceAmount = formObject.getNGValue("ca_advance_amount").trim();
        getcmi = formObject.getNGValue("ca_cmi");
        getUnallocated = formObject.getNGValue("ca_unallocated");
        paymentType = formObject.getNGValue("ca_payment_method");

        //  System.out.println("get advance  " + getadvance_Amount + " get cmi  " + getcmi_amt + " unallocated  " + getUnallocated_amt);
        if ("advance".equalsIgnoreCase(paymentType)) {
            getadvance_Amount = Float.parseFloat(getadvanceAmount);
            total_line_amount = Float.parseFloat(resultcash.get(0).get(0));
            if (total_line_amount == getadvance_Amount) {
                System.out.println("total line amount " + total_line_amount + " advance amount amount " + getadvance_Amount);
            } else {
                throw new ValidatorException(new FacesMessage("Line level amount is not matching with advance amount, Please check ", ""));
            }

        } else if ("cmi".equalsIgnoreCase(paymentType)) {
            getcmi_amt = Float.parseFloat(getcmi);
            total_line_amount = Float.parseFloat(resultcash.get(0).get(0));
            if (total_line_amount == getcmi_amt) {
                System.out.println("total line amount " + total_line_amount + " advance amount amount " + getadvance_Amount);
            } else {
                throw new ValidatorException(new FacesMessage("Line level amount is not matching with advance amount, Please check ", ""));
            }
        } else if ("unallocated".equalsIgnoreCase(paymentType)) {
            getUnallocated_amt = Float.parseFloat(getUnallocated);
            total_line_amount = Float.parseFloat(resultcash.get(0).get(0));
            if (total_line_amount == getUnallocated_amt) {
                System.out.println("total line amount " + total_line_amount + " advance amount amount " + getadvance_Amount);
            } else {
                throw new ValidatorException(new FacesMessage("Line level amount is not matching with advance amount, Please check ", ""));
            }
        }
    }

    //fieldMadatry field alert
    private void fieldMadatry() {

        Map<String, String> validationMap = new HashMap<String, String>();

        validationMap.put("ca_company_name", "Entity Name is a mandatory filed , kindly fill");
        validationMap.put("ca_division_code", "Division Code is a mandatory filed , kindly fill");
        validationMap.put("ca_mode_of_business", "Mode Of Business is a mandatory filed , kindly fill");
        validationMap.put("ca_documnt_category", "Document Category is a mandatory filed , kindly fill");
        validationMap.put("ca_type_adv_ar_unallocated", "Receipt Type is a mandatory filed , kindly fill");
        validationMap.put("ca_division_type", "Multidivion or Single is a mandatory filed , kindly fill");
        validationMap.put("ca_transaction_type", "Transection Type is a mandatory filed , kindly fill");
        //  validationMap.put("ca_type_receipt", "Receipt Type is a mandatory filed , kindly fill");
        validationMap.put("ca_payment_method", "Payment Type is a mandatory filed , kindly fill");
        validationMap.put("ca_pu_name", "PU Name Type is a mandatory filed , kindly fill");
        validationMap.put("ca_cheque_date", "Cheque Date is a mandatory filed , kindly fill");
        validationMap.put("ca_cheque_no", "Cheque No is a mandatory filed , kindly fill");
        //  validationMap.put("type_deduction_control", "Deduction Type is a mandatory filed , kindly fill");

        for (Map.Entry m : validationMap.entrySet()) {
            String idName = m.getKey().toString();
            String validationMEssage = m.getValue().toString();

            if (formObject.getNGValue(idName) == null || 0 == formObject.getNGValue(idName).length()) {
                throw new ValidatorException(new FacesMessage(
                        validationMEssage,
                        ""));

            }
        }

    }

    private void deleteLiqSeq() {
        String seq_no = "";
        seq_no = formObject.getNGValue("liq_list");
        System.out.println("seq no ro  " + seq_no);
        String Query_DeleteLiqSeq = "delete from mdm_ar_liquidice_report where seq_no='" + seq_no + "'";
        List<List<String>> resultDeleteSeq = null;
        System.out.println("seq data  " + Query_DeleteLiqSeq);
        // resultDeleteSeq= formObject.saveDataIntoDataSource(Query_DeleteLiqSeq);
    }

    // multi deduction code
    private void multiDeductionValidation() {

        String typeOfTrans = formObject.getNGValue("type_of_transaction");
        if ("multideduction".equalsIgnoreCase(typeOfTrans)) {
            String mdbu1 = formObject.getNGValue("multideduc_bu1");
            String mdbu2 = formObject.getNGValue("multideduc_bu2");
            String mdbu3 = formObject.getNGValue("multideduc_bu3");
            String mdbu4 = formObject.getNGValue("multideduc_bu4");
            String mdbu5 = formObject.getNGValue("multideduc_bu5");
            if ("".equalsIgnoreCase(mdbu1)) {
                System.out.println("mdbu1   " + mdbu1);
            } else {
                String mdcn1 = formObject.getNGValue("multideduc_cn1");
                if ("".equalsIgnoreCase(mdcn1)) {
                    throw new ValidatorException(new FacesMessage("CN1 is not enter detail., Please check.", ""));
                }
            }

            if ("".equalsIgnoreCase(mdbu2)) {
                System.out.println("mdbu3   " + mdbu2);
            } else {
                String mdcn2 = formObject.getNGValue("multideduc_cn2");
                if ("".equalsIgnoreCase(mdcn2)) {
                    throw new ValidatorException(new FacesMessage("CN2 is not enter detail., Please check.", ""));
                }
            }

            if ("".equalsIgnoreCase(mdbu3)) {
                System.out.println("mdbu3   " + mdbu3);
            } else {
                String mdcn3 = formObject.getNGValue("multideduc_cn3");
                if ("".equalsIgnoreCase(mdcn3)) {
                    throw new ValidatorException(new FacesMessage("CN3 is not enter detail., Please check.", ""));
                }
            }

            if ("".equalsIgnoreCase(mdbu4)) {
                System.out.println("mdbu4   " + mdbu4);
            } else {
                String mdcn4 = formObject.getNGValue("multideduc_cn4");
                if ("".equalsIgnoreCase(mdcn4)) {
                    throw new ValidatorException(new FacesMessage("CN4 is not enter detail., Please check.", ""));
                }
            }
            if ("".equalsIgnoreCase(mdbu5)) {
                System.out.println("mdbu5   " + mdbu5);
            } else {
                String mdcn5 = formObject.getNGValue("multideduc_cn5");
                if ("".equalsIgnoreCase(mdcn5)) {
                    throw new ValidatorException(new FacesMessage("CN5 is not enter detail., Please check.", ""));
                }
            }

        }

    }

}
