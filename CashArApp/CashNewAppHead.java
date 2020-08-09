/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newgen.CashArApp;

import com.newgen.common.PicklistListenerHandler_thrmx;
import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Admin
 */
public class CashNewAppHead implements FormListener {

    FormReference formObject = null;
    FormConfig formConfig = null;
    String activityName = null;
    String engineName = null;
    String sessionId = null;
    String processid = null;
    String folderId = null;
    String FILE = null;
    String processInstanceId = null;
    String workItemId = null;
    String userName = null;
    String input = "";
    PicklistListenerHandler_thrmx objPicklistListenerHandler;

    @Override
    public void continueExecution(String arg0, HashMap<String, String> arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void eventDispatched(ComponentEvent pEvent) throws ValidatorException {
        // TODO Auto-generated method stub
        System.out.println("Value Change Event :" + pEvent);
        System.out.println("pEvent.getType() :" + pEvent.getType());
        System.out.println("pEvent.getType().name() :" + pEvent.getType().name());
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        activityName = formConfig.getConfigElement("ActivityName");
        objPicklistListenerHandler = new PicklistListenerHandler_thrmx();
        if (pEvent.getType().name().equalsIgnoreCase("VALUE_CHANGED")) {
            System.out.println("inside value changed  ");
            if (pEvent.getSource().getName().equalsIgnoreCase("ca_company_name")) {
                System.out.println("Change in entity");
                formObject.clear("ca_division_code");
                formObject.clear("division_control");
                System.out.println("ca_company_name  " + formObject.getNGValue("ca_company_name"));
                divisionCode("D");
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
                    formObject.setEnabled("liq_list", true);
                    formObject.setEnabled("Frame16", false);
                    formObject.setEnabled("Frame17", false);
                    formObject.setEnabled("Frame2", false);
                    formObject.setEnabled("Frame9", false);
                    formObject.setEnabled("Frame8", false);
                    formObject.setEnabled("Frame15", false);
                    formObject.setEnabled("Frame14", false);
                    formObject.setEnabled("Frame12", false);
                    formObject.setEnabled("Frame3", false);
                    formObject.setEnabled("Frame10", false);
                    formObject.setEnabled("Frame13", false);
                    formObject.setEnabled("Frame7", false);
                    formObject.setEnabled("Frame8", true);
                    //  formObject.setSheetEnable("Tab1", 0, true);
                    //  formObject.setSheetEnable("Tab1", 1, true);
                    liqTranssction("D");
                } else if ("cheque".equalsIgnoreCase(paymenttpye)) {
                    formObject.setVisible("Frame4", false);      // frame4 online payment
                    formObject.setVisible("Frame6", true);
                    formObject.setEnabled("liq_list", false);
                    formObject.setEnabled("Frame16", true);
                    formObject.setEnabled("Frame17", true);
                    formObject.setEnabled("Frame2", true);
                    formObject.setEnabled("Frame9", true);
                    formObject.setEnabled("Frame8", true);
                    formObject.setEnabled("Frame15", true);
                    formObject.setEnabled("Frame14", true);
                    formObject.setEnabled("Frame12", true);
                    formObject.setEnabled("Frame3", true);
                    formObject.setEnabled("Frame10", true);
                    formObject.setEnabled("Frame13", false);
                    formObject.setEnabled("Frame7", true);
                    formObject.setEnabled("total_applied_amt", false);
                    formObject.setSheetEnable("Tab1", 0, true);
                    formObject.setSheetEnable("Tab1", 1, true);
                    formObject.setEnabled("multidedu_cn1", false);
                    formObject.setEnabled("multideduc_cn1", false);
                    formObject.setEnabled("multideduc_cn2", false);
                    formObject.setEnabled("multideduc_cn3", false);
                    formObject.setEnabled("multideduc_cn4", false);
                    formObject.setEnabled("multideduc_cn5", false);
                    formObject.setEnabled("cn_wi_no1", false);
                    formObject.setEnabled("cn_wi_no2", false);
                    formObject.setEnabled("cn_wi_no3", false);
                    formObject.setEnabled("cn_wi_no4", false);
                    formObject.setEnabled("cn_wi_no5", false);
                    // BAAN Field Disable
                    String[] miscDisableOracle = {"exception", "employee_control", "ca_bank_code", "ca_receipt_method", "businessPart_control", "misc_control", "diemention_control", "ca_bank_receipt_no", "receipt_date", "own_gst_reg_no_control", "cust_gst_reg_no_control"};
                    for (int i = 0; i < miscDisableOracle.length; i++) {
                        formObject.setEnabled(miscDisableOracle[i], false);
                    }
                    formObject.addComboItem("ca_user_decision", "--Select--", "");
                    formObject.addComboItem("ca_user_decision", "Send For Processing", "sendforprocessing");
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
                setDocCategory();
            } else if (pEvent.getSource().getName().equalsIgnoreCase("liq_list")) {// Liquice transsaction data
                activityName = formConfig.getConfigElement("ActivityName");
                String liqListLock = "";
                String liqList = formObject.getNGValue("liq_list");
                System.out.println("srno  " + liqList);
                //SELECT * FROM mdm_ar WHERE lock NOT IN ('Y')
                String Query_mdm_currency_code = "select bank,transaction_ref,narration,value_date,amount,account_no,date,seq_no,sr_no from mdm_ar_liquidice_report where sr_no='" + liqList + "'"; // mdm table  and date like '"+hu+"'
                System.out.println("query1  " + Query_mdm_currency_code);
                List<List<String>> result15 = null;
                result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
                System.out.println("queryllist  " + "(TO_DATE('" + result15.get(0).get(3) + "','dd/mm/yyyy'))");
                String dateformate = "(TO_DATE('" + result15.get(0).get(3) + "','dd/mm/yyyy'))";
                System.out.println("hh  " + dateformate);
                String dateValue = null;
                double amtValue;
                String amtValue1 = "";
                try {
                    SimpleDateFormat sdfIn = new SimpleDateFormat("dd-MMM-yy");
                    SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
                    input = result15.get(0).get(3);
                    System.out.println("date value input  " + input);
                    Date date = sdfIn.parse(input);
                    dateValue = sdfOut.format(date);
                    System.out.println(sdfOut.format(date));
                    System.out.println("date value   " + dateValue);
                } catch (Exception e) {
                    System.out.println("exception e" + e);
                }
                amtValue = Double.parseDouble(result15.get(0).get(4));
                // System.out.println("amt value report " + amtValue1);
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                //   String firstNumberAsString = decimalFormat.format(a);
                amtValue1 = decimalFormat.format(amtValue);
                System.out.println("amt value report " + amtValue1);
                formObject.setNGValue("bank_name", result15.get(0).get(0));
                formObject.setNGValue("transaction_ref", result15.get(0).get(1));
                String narration = result15.get(0).get(2);
                if(narration.length()>190)
                {
                 narration = narration.substring(0,88);
                }
                formObject.setNGValue("ca_narration_n", narration);
                formObject.setNGValue("ca_reveived_date", dateValue);
                formObject.setNGValue("ca_deposit_date", dateValue);
                formObject.setNGValue("amount", amtValue1);
                formObject.setNGValue("account_no", result15.get(0).get(5));
                formObject.setNGValue("ca_cheque_date", dateValue);
                formObject.setNGValue("sr_liq_report_no", result15.get(0).get(8));
                formObject.setEnabled("ca_cheque_date", false);
                formObject.setEnabled("ca_reveived_date", false);
                formObject.setEnabled("ca_deposit_date", false);
                if (activityName.equalsIgnoreCase("Initiator")) {
                    liqListLock = "update mdm_ar_liquidice_report set lock='Y' where sr_no='" + result15.get(0).get(8) + "'";
                    // liqListLock= liqListLock + result15.get(0).get(7);
                    System.out.println("update row data in table y " + liqListLock);
                    formObject.saveDataIntoDataSource(liqListLock);
                    System.out.println("update row data in table y done");
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_transaction_type")) {   //  ca_mode_of_business
                System.out.println("Change ca_transaction_type");
                String transactionType = formObject.getNGValue("ca_transaction_type");
                System.out.println("transactionType  " + transactionType);
                if ("export".equalsIgnoreCase(transactionType)) {
                    formObject.clear("ca_rate_type");
                    formObject.clear("ca_rate");
                    formObject.setEnabled("ca_currency_type", true);
                    formObject.setEnabled("ca_rate", true);
                    formObject.setEnabled("ca_rate_date", true);
                    System.out.println("set setCurrencyCode call");
                    formObject.clear("ca_currency_type");
                    setCurrencyCode();
                    formObject.addComboItem("ca_rate_type", "Select", "");
                    formObject.addComboItem("ca_rate_type", "Corporate : Corporate Exchange Rate", "Corporate");
                    formObject.addComboItem("ca_rate_type", "Spot : Spot Exchange Rate", "Spot");
                    formObject.addComboItem("ca_rate_type", "User : User Specified Rate", "User");
                    formObject.addComboItem("ca_rate_type", "Import Payment : Import Payment Rate - User Defined", "Import Payment");
                    formObject.addComboItem("ca_rate_type", "Export Receipt : Export Receipt Rate - User Defined", "Export Receipt");
                    formObject.addComboItem("ca_rate_type", "BOE Rate : Custom Rate", "BOE Rate");
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
                    //setSalesOrderNo("D");
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("type_of_transaction")) {//type transection multideduction
                System.out.println("** transection of multideduction **");
                String typeOfTrans = formObject.getNGValue("type_of_transaction");
                if (typeOfTrans.equalsIgnoreCase("general") || typeOfTrans.equalsIgnoreCase("multidivision")) {
                    // formObject.setSheetEnable("Tab1", 0, false);
                    formObject.clear("deduc_accept1");
                    formObject.clear("deduc_accept2");
                    formObject.clear("deduc_accept3");
                    formObject.clear("deduc_accept4");
                    formObject.clear("multideduc_bu1");
                    formObject.clear("multideduc_bu2");
                    formObject.clear("multideduc_bu3");
                    formObject.clear("multideduc_bu4");
                    formObject.clear("deduc_amt1");
                    formObject.clear("deduc_amt2");
                    formObject.clear("deduc_amt3");
                    formObject.clear("deduc_amt4");
                    formObject.setSheetEnable("Tab1", 1, false);
                } else {
                    formObject.setSheetEnable("Tab1", 0, true);
                    formObject.setSheetEnable("Tab1", 1, true);
                    formObject.setEnabled("multidedu_cn1", false);
                    formObject.setEnabled("multideduc_cn1", false);
                    formObject.setEnabled("multideduc_cn2", false);
                    formObject.setEnabled("multideduc_cn3", false);
                    formObject.setEnabled("multideduc_cn4", false);
                    formObject.setEnabled("multideduc_cn5", false);
                    formObject.setEnabled("cn_wi_no1", false);
                    formObject.setEnabled("cn_wi_no2", false);
                    formObject.setEnabled("cn_wi_no3", false);
                    formObject.setEnabled("cn_wi_no4", false);
                    formObject.setEnabled("cn_wi_no5", false);
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
                    formObject.setEnabled("Frame15", false);
                    formObject.setEnabled("Frame12", true);
                    formObject.setEnabled("Frame17", true);
                    formObject.setEnabled("Frame3", true);
                    formObject.setEnabled("Frame14", true);
                    formObject.setEnabled("gst_applicable_control", true);
                    formObject.setEnabled("ca_payment_method", true);
                    formObject.setEnabled("ca_advance_amount", true);
                    formObject.setEnabled("ca_cmi", true);
                    formObject.setEnabled("ca_unallocated", true);
                    formObject.setEnabled("ca_ar_amount", true);
                    formObject.setEnabled("Frame10", true);
                    formObject.setEnabled("total_applied_amt", false);
                    formObject.setEnabled("Text3", false);

                    //  setSalesOrderNo("D");
                    settaxCat();
                    Oragnization();
                    OrgLoc();
                    setDeductionType();
                    formObject.addComboItem("gst_applicable_control", "Select", "");
                    formObject.addComboItem("gst_applicable_control", "Yes", "yes");
                    formObject.addComboItem("gst_applicable_control", "No", "no");

                    // BAAN Field Disable
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);
                } else if ("MISC".equalsIgnoreCase(receiptofType)) {
                    formObject.setEnabled("Frame15", true);
                    formObject.setEnabled("Frame12", false);
                    formObject.setEnabled("Frame17", false);
                    formObject.setEnabled("Frame3", false);
                    formObject.setEnabled("Frame12", false);
                    formObject.setEnabled("Frame14", false);
                    formObject.setEnabled("ca_payment_method", false);
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", false);
                    formObject.setEnabled("Frame10", false);
                    formObject.setEnabled("amount_control", true);

                    System.out.println("set project code");
                    setProjectCode();
                    costCenter();
                    //  setPU();
                    //  divisionCode("D");

                    // BAAN Field Disable
                    formObject.setEnabled("employee_control", false);
                    formObject.setEnabled("ca_bank_code", false);
                    formObject.setEnabled("ca_receipt_method", false);
                    formObject.setEnabled("businessPart_control", false);
                    formObject.setEnabled("misc_control", false);
                    formObject.setEnabled("diemention_control", false);
                    formObject.setEnabled("ca_bank_receipt_no", false);
                    formObject.setEnabled("receipt_date", false);
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);
                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("gst_applicable_control")) {   //  gst applicable  control   yes/no
                System.out.println("Change gst applicable");
                String gstApplicable = formObject.getNGValue("gst_applicable_control");
                System.out.println("gstApplicable  " + gstApplicable);
                if ("no".equalsIgnoreCase(gstApplicable)) {
                    formObject.clear("orgnization_control");
                    formObject.clear("location_code_control");
                    formObject.clear("tax_amt_control");
                    formObject.setEnabled("Frame3", false);

                } else if ("yes".equalsIgnoreCase(gstApplicable)) {
                    formObject.setEnabled("Frame3", true);
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);
                    settaxCat();
                    Oragnization();
                    OrgLoc();
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
//                        result_curr = formObject.getDataFromDataSource(getCurrencyRate);
//                        System.out.println("result_curr.get(0).get(0) : " + result_curr.get(0).get(0));
//                        curry_rate = result_curr.get(0).get(0);
                        //  formObject.setNGValue("ca_rate_type", curry_rate);
                        // formObject.setEnabled("ca_rate_type", false);
                    } catch (Exception E) {
                        System.out.println(" Exception in currency E " + E);
                    }
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
                    formObject.clear("q_ar_open_invoice");
                    formObject.clear("Text3");
                    formObject.clear("Text4");
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", true);
                    formObject.setEnabled("Frame3", false);//gst frame
                    formObject.setEnabled("Frame12", false);//advance frame
                    formObject.setEnabled("Frame10", true);//linking section true	
                    formObject.setEnabled("total_applied_amt", false);

                    formObject.addComboItem("Combo1", "Select", "");
                    formObject.addComboItem("Combo1", "Yes", "Yes");
                    formObject.addComboItem("Combo1", "No", "No");

                } else if ("advance".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_cmi");
                    formObject.clear("ca_unallocated");
                    formObject.clear("q_ar_open_invoice");
                    formObject.clear("Text3");
                    formObject.clear("Text4");
                    formObject.clear("amount_control");
                    formObject.setEnabled("ca_advance_amount", true);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", false);

                    formObject.setEnabled("Frame3", true);//gst frame
                    formObject.setEnabled("Frame12", true);//advance frame
                    formObject.setEnabled("Frame10", false);//linking section true
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);

                } else if ("cmi".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_advance_amount");
                    formObject.clear("ca_unallocated");
                    formObject.clear("q_ar_open_invoice");
                    formObject.clear("Text3");
                    formObject.clear("Text4");
                    formObject.clear("amount_control");
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", true);
                    formObject.setEnabled("ca_unallocated", false);
                    formObject.setEnabled("ca_ar_amount", false);

                    formObject.setEnabled("Frame3", true);//gst frame
                    formObject.setEnabled("Frame12", true);//advance frame
                    formObject.setEnabled("Frame10", false);//linking section true
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);

                } else if ("unallocated".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_advance_amount");
                    formObject.clear("ca_cmi");
                    formObject.clear("q_ar_open_invoice");
                    formObject.clear("Text3");
                    formObject.clear("Text4");
                    formObject.clear("amount_control");
                    formObject.setEnabled("ca_advance_amount", false);
                    formObject.setEnabled("ca_cmi", false);
                    formObject.setEnabled("ca_unallocated", true);
                    formObject.setEnabled("ca_ar_amount", false);
                    formObject.setEnabled("Frame3", true);//gst frame
                    formObject.setEnabled("Frame12", true);//advance frame
                    formObject.setEnabled("Frame10", false);//linking section true
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);

                } else if ("combined".equalsIgnoreCase(paymentMethod)) {
                    formObject.clear("ca_ar_amount");
                    formObject.clear("ca_advance_amount");
                    formObject.clear("ca_cmi");
                    formObject.clear("Combo1");
                    formObject.clear("q_ar_open_invoice");
                    formObject.clear("amount_control");
                    formObject.setEnabled("ca_advance_amount", true);
                    formObject.setEnabled("ca_cmi", true);
                    formObject.setEnabled("ca_unallocated", true);
                    formObject.setEnabled("ca_ar_amount", true);
                    formObject.setEnabled("Frame3", true);//gst frame
                    formObject.setEnabled("Frame12", true);//advance frame
                    formObject.setEnabled("Frame10", true);//linking section true 
                    formObject.setEnabled("own_gst_reg_no_control", false);
                    formObject.setEnabled("cust_gst_reg_no_control", false);
                    formObject.setEnabled("total_applied_amt", false);

                    formObject.addComboItem("Combo1", "Select", "");
                    formObject.addComboItem("Combo1", "Yes", "Yes");
                    formObject.addComboItem("Combo1", "No", "No");

                }
            } else if (pEvent.getSource().getName().equalsIgnoreCase("ca_user_decision")) {//   exception combo enable and desable
                String useraction = formObject.getNGValue("ca_user_decision");
                String modeofpayment = formObject.getNGValue("ca_mode_of_payment");
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
            }

        } else if (pEvent.getType().name().equalsIgnoreCase("MOUSE_CLICKED")) {
            System.out.print("------------Inside Mouse Click------------------");
//            if (pEvent.getSource().getName().equalsIgnoreCase("send")) {// send id send 
//                System.out.println("Inside save work item !!");
//                formObject.RaiseEvent("WFSave", true);
//               // formObject.RaiseEvent("SAVE");
//                formObject.RaiseEvent("WFDone", true);
//            }
            if (pEvent.getSource().getName().equalsIgnoreCase("Button5")) {
                formObject.ExecuteExternalCommand("NGAddRow", "q_cash_app_ar");// for double line error   
                // formObject.RaiseEvent("WFSave", true);
                formObject.setNGValue("show_result", "Data inserted!!!");
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button1")) {
                // formObject.ExecuteExternalCommand("NGModifyRow", "q_cash_app_ar");// for double line error   and
                formObject.setNGValue("show_result", "Data Updated !!!");
                // formObject.RaiseEvent("WFSave", true);
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button4")) {
                // formObject.ExecuteExternalCommand("NGAddRow", "q_cash_app_ar");// for double line error  data delete               
                formObject.setNGValue("show_result", "Data Delete !!!");
            } else if (pEvent.getSource().getName().equalsIgnoreCase("Button2")) {  //comment button save id Button2
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
                Querymdm = "select customer_number,customer_name,customer_site_name from ar_mdm_customer_master where bu_code like '" + divisionCode + "'";
                System.out.println("customer code   " + Querymdm);
                objPicklistListenerHandler.openPickList("ca_customer_code", "customer_number,customer_name,customer_site_name", "Customer Details", 70, 70, Querymdm);
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
                    double finalCombindAmt = 0;
                    double ar_Amount = 0;
                    double total_Amount = 0;
                    double advance_Amt1 = 0;
                    double cmi_Amt1 = 0;
                    double unallocated_Amt1 = 0;
                    String arAmount1 = "";
                    String advanceAmt1 = "";
                    String cmiAmt1 = "";
                    String unallocatedAmt1 = "";
                    arAmount1 = formObject.getNGValue("ca_ar_amount").trim();
                    // String totalAmount = formObject.getNGValue("ca_received_amt").trim();
                    advanceAmt1 = formObject.getNGValue("ca_advance_amount").trim();
                    cmiAmt1 = formObject.getNGValue("ca_cmi").trim();
                    unallocatedAmt1 = formObject.getNGValue("ca_unallocated").trim();
                    if (unallocatedAmt1.equalsIgnoreCase("")) {
                        unallocatedAmt1 = "0";
                    }
                    if (cmiAmt1.equalsIgnoreCase("")) {
                        cmiAmt1 = "0";
                    }
                    if (advanceAmt1.equalsIgnoreCase("")) {
                        advanceAmt1 = "0";
                    }
                    if (arAmount1.equalsIgnoreCase("")) {
                        arAmount1 = "0";
                    }
                    ar_Amount = Double.parseDouble(arAmount1);
                    total_Amount = Double.parseDouble(totalAmount);
                    advance_Amt1 = Double.parseDouble(advanceAmt1);
                    cmi_Amt1 = Double.parseDouble(cmiAmt1);
                    unallocated_Amt1 = Double.parseDouble(unallocatedAmt1);
                    System.out.println("ar amount total  " + ar_Amount + "  advance " + advance_Amt1 + "  cmi" + cmi_Amt1 + "  unallocated_Amt1" + unallocated_Amt1);
                    finalCombindAmt = ar_Amount + advance_Amt1 + cmi_Amt1 + unallocated_Amt1;
                    System.out.println("combined amount " + finalCombindAmt + "combind " + total_Amount);
                    if (finalCombindAmt == total_Amount) {
                        System.out.println("combind total  " + finalCombindAmt);
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
                String combo1 = formObject.getNGValue("Combo1");
                if (combo1.equalsIgnoreCase("")) {
                    throw new ValidatorException(new FacesMessage("Apply Yes/No is Entry Missing, Please check ", ""));
                }
                String datePick = formObject.getNGValue("DatePicker2");
                if (datePick.equalsIgnoreCase("")) {
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

            //  save button call function on click  data insert    
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
            // serverUrl = formConfig.getConfigElement("ServletPath");
            //ServletUrl = serverUrl + "/servlet/ExternalServlet";
            processInstanceId = formConfig.getConfigElement("ProcessInstanceId");
            workItemId = formConfig.getConfigElement("WorkitemId");
            userName = formConfig.getConfigElement("UserName");
            // processDefId = formConfig.getConfigElement("ProcessDefId");

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
        // activityName = formConfig.getConfigElement("ActivityName");
        formObject.setNGValue("activity_name_control", activityName);
        formObject.setNGValue("raised_by_control", userName);

        try {
            // method call
            setLegalEntity();
            setPU();
            // setProjectCode();
            if (activityName.equalsIgnoreCase("Initiator")) {
                formObject.setVisible("ng_wi_id", false);
                formObject.setNGValue("ng_wi_id", processInstanceId);
                formObject.setEnabled("liq_list", false);
                formObject.setEnabled("Frame16", false);
                formObject.setEnabled("Frame17", false);
                formObject.setEnabled("Frame2", false);
                formObject.setEnabled("Frame9", false);
                formObject.setEnabled("Frame8", false);
                formObject.setEnabled("Frame15", false);
                formObject.setEnabled("Frame14", false);
                formObject.setEnabled("Frame12", false);
                formObject.setEnabled("Frame3", false);
                formObject.setEnabled("Frame10", false);
                formObject.setEnabled("Frame13", false);
                formObject.setEnabled("Frame7", false);

            } else if (activityName.equalsIgnoreCase("RO_Commercial")) {
                formObject.clear("exception");
                formObject.clear("ca_user_decision");
                formObject.setVisible("ng_wi_id", false);

                formObject.setEnabled("Frame13", false);
                formObject.setEnabled("Frame11", false);

                formObject.setEnabled("Frame9", false);
                formObject.setEnabled("Frame16", true);
                formObject.setEnabled("Frame17", true);
                formObject.setEnabled("Frame2", true);
                formObject.setEnabled("Frame7", true);

                formObject.setEnabled("Frame8", true);
                formObject.setEnabled("ca_drawn_bank", false);

//                formObject.setEnabled("Frame15", false);
//                formObject.setEnabled("Frame14", false);
//                formObject.setEnabled("Frame12", false);
//                formObject.setEnabled("Frame3", false);
//                formObject.setEnabled("Frame10", false);
                // ro commercial
//                formObject.setEnabled("multidedu_cn1", false);
//                formObject.setEnabled("multideduc_cn1", false);
//                formObject.setEnabled("multideduc_cn2", false);
//                formObject.setEnabled("multideduc_cn3", false);
//                formObject.setEnabled("multideduc_cn4", false);
//                formObject.setEnabled("multideduc_cn5", false);
//                formObject.setEnabled("cn_wi_no1", false);
//                formObject.setEnabled("cn_wi_no2", false);
//                formObject.setEnabled("cn_wi_no3", false);
//                formObject.setEnabled("cn_wi_no4", false);
//                formObject.setEnabled("cn_wi_no5", false);
                formObject.setEnabled("ca_bank_code", false);
                formObject.setEnabled("ca_receipt_method", false);
                formObject.setEnabled("tax_amt_control", false);
                formObject.setEnabled("ca_deposit_date", false);
                formObject.setEnabled("ca_cheque_date", false);
                formObject.setEnabled("ca_reveived_date", false);
                formObject.setEnabled("total_applied_amt", false);
                formObject.setEnabled("tax_amt_control", false);
                formObject.setEnabled("exception", false);

                formObject.addComboItem("ca_user_decision", "Select", "");
                formObject.addComboItem("ca_user_decision", "Send For Processing", "sendforprocessing");
                exceptionraised();
            } else if (activityName.equalsIgnoreCase("TFS Post")) {
                formObject.clear("exception");
                formObject.clear("ca_user_decision");
//                formObject.clear("location_control");
//                formObject.clear("cost_center_control");
//                formObject.clear("gl_code_control");
//                formObject.clear("future2_control");
                formObject.setEnabled("Frame13", false);
                formObject.setEnabled("Frame11", false);
                formObject.setEnabled("Frame2", false);
                formObject.setVisible("ng_wi_id", false);
                formObject.setEnabled("Frame17", false);
                formObject.setEnabled("Frame3", false);
                formObject.setEnabled("Frame14", false);
                formObject.setEnabled("Frame15", false);
                formObject.setEnabled("Frame12", false);
                formObject.setEnabled("Frame10", false);
                formObject.setEnabled("Frame9", false);
                formObject.setEnabled("Frame16", false);
                formObject.setEnabled("send", true);
                formObject.setEnabled("Frame7", true);
                formObject.setEnabled("exception", false);
                formObject.setEnabled("ca_narration", true);
                formObject.setEnabled("ca_booking_date", true);
                formObject.setEnabled("ca_documnt_cetegory", true);

                // tfs post  formObject.setEnabled("Sheet1", false);
                formObject.setSheetEnable("Tab1", 0, false);
                formObject.setSheetEnable("Tab1", 1, false);

                formObject.addComboItem("ca_user_decision", "--Select--", "");
                formObject.addComboItem("ca_user_decision", "Post", "post");
                formObject.addComboItem("ca_user_decision", "Raised Exception", "raiseexception");
                // costCenter();
                System.out.println("end code tfs post ");
            } else if (activityName.equalsIgnoreCase("Posting_response")) {
                System.out.println("inside posting response ");
                formObject.clear("exception");
                formObject.clear("ca_user_decision");
                formObject.setVisible("ng_wi_id", false);
                formObject.setEnabled("exception", false);
                formObject.setEnabled("Frame11", false);
                formObject.setEnabled("Frame16", false);
                formObject.setEnabled("Frame17", false);
                formObject.setEnabled("Frame2", false);
                formObject.setEnabled("Frame9", false);
                formObject.setEnabled("Frame8", false);
                formObject.setEnabled("Frame15", false);
                formObject.setEnabled("Frame14", false);
                formObject.setEnabled("Frame12", false);
                formObject.setEnabled("Frame3", false);
                formObject.setEnabled("Frame10", false);
                formObject.setEnabled("Frame13", false);
                formObject.setEnabled("Frame7", false);
                formObject.setSheetEnable("Tab1", 0, false);
                formObject.setSheetEnable("Tab1", 1, false);
                formObject.setEnabled("send", true);
                // processInstanceId = formConfig.getConfigElement("ProcessInstanceId");
                String flags = formObject.getNGValue("ca_division_type");
                String ngId = formObject.getNGValue("ng_wi_id");
                System.out.println("flags " + flags + " ngId  " + ngId);
                getResponse getresponse = new getResponse();
                getresponse.getres(flags, ngId);
                //String postStatus=formObject.getNGValue("posting_status");
                formObject.setEnabled("ca_user_decision", true);
                formObject.addComboItem("ca_user_decision", "Select", "");
                formObject.addComboItem("ca_user_decision", "Send", "send");
                System.out.println("posting code end ");
            } else if (activityName.equalsIgnoreCase("CN_Entry")) {
                System.out.println("inside posting response ");
                formObject.clear("exception");
                formObject.setVisible("ng_wi_id", false);
                formObject.setEnabled("exception", false);
                formObject.setEnabled("Frame11", false);
                formObject.setEnabled("Frame16", false);
                formObject.setEnabled("Frame17", false);
                formObject.setEnabled("Frame2", false);
                formObject.setEnabled("Frame9", false);
                formObject.setEnabled("Frame8", false);
                formObject.setEnabled("Frame15", false);
                formObject.setEnabled("Frame14", false);
                formObject.setEnabled("Frame12", false);
                formObject.setEnabled("Frame3", false);
                formObject.setEnabled("Frame10", false);
                formObject.setEnabled("Frame13", false);
                formObject.setEnabled("Frame7", false);
                formObject.setSheetEnable("Tab1", 0, false);
                formObject.setSheetEnable("Tab1", 1, false);
                // CN_ENTRY data enble and desable
                String deducAccept1 = formObject.getNGValue("deduc_accept1");
                String deducAccept2 = formObject.getNGValue("deduc_accept2");
                String deducAccept3 = formObject.getNGValue("deduc_accept3");
                String deducAccept4 = formObject.getNGValue("deduc_accept4");
                String deducAccept5 = formObject.getNGValue("deduc_accept5");
                if ("accepted".equalsIgnoreCase(deducAccept1)) {
                    formObject.setEnabled("multideduc_cn1", true);
                    formObject.setEnabled("cn_wi_no1", true);
                } else if ("accepted".equalsIgnoreCase(deducAccept2)) {
                    formObject.setEnabled("multideduc_cn2", true);
                    formObject.setEnabled("cn_wi_no2", true);
                } else if ("accepted".equalsIgnoreCase(deducAccept3)) {
                    formObject.setEnabled("multideduc_cn3", true);
                    formObject.setEnabled("cn_wi_no3", true);
                } else if ("accepted".equalsIgnoreCase(deducAccept4)) {
                    formObject.setEnabled("multideduc_cn4", true);
                    formObject.setEnabled("cn_wi_no4", true);
                } else if ("accepted".equalsIgnoreCase(deducAccept5)) {
                    formObject.setEnabled("multideduc_cn5", true);
                    formObject.setEnabled("cn_wi_no5", true);
                }
                formObject.setEnabled("send", true);
            } else {
                formObject.setEnabled("ca_user_decision", false);
            }
        } catch (Exception e) {
            System.out.println("Exception in FieldValueBagSet::::" + e.getMessage());
        }

        // type of transaction 
        formObject.addComboItem("type_of_transaction", "Select", "");
        formObject.addComboItem("type_of_transaction", "GENERAL", "general");
        formObject.addComboItem("type_of_transaction", "DEDUCTIONS", "multideduction");
        formObject.addComboItem("type_of_transaction", "MULTI DIVISION", "multidivision");
        // calling method
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
        System.out.println("******activityName****" + activityName);
        String useraction = "";
        String modeofPayment = "";
        useraction = formObject.getNGValue("ca_user_decision");
        // modeofPayment = formObject.getNGValue("ca_mode_of_payment");
        if (activityName.equalsIgnoreCase("Initiator")) {
            modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("user  " + useraction);
            if ("online".equalsIgnoreCase(modeofPayment)) {
                String srNo = formObject.getNGValue("sr_liq_report_no");
                // String liqListLock = "";
                String liqListLock = "update mdm_ar_liquidice_report set Processed='Y' where sr_no='" + srNo + "'";
                System.out.println("update row data in table y " + liqListLock);
                formObject.saveDataIntoDataSource(liqListLock);
                System.out.println("update row data in table y done");
            }
        }
        //user action decision mandatory 
        if (activityName.equalsIgnoreCase("Initiator")) {
            modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("user  " + useraction);
            if ("online".equalsIgnoreCase(modeofPayment)) {
                System.out.println("user  " + modeofPayment);
            } else if ("cheque".equalsIgnoreCase(modeofPayment)) {
                fieldMadatry();
                if ("".equalsIgnoreCase(useraction)) {
                    throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
                }
            }
            custValidat();
        }
        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("user  " + useraction);
            if ("online".equalsIgnoreCase(modeofPayment)) {
                System.out.println("user  " + modeofPayment);
                fieldMadatry();
                if ("".equalsIgnoreCase(useraction)) {
                    throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
                }
            }
            custValidat();
        }
        if (activityName.equalsIgnoreCase("TFS Post")) {
            String except = formObject.getNGValue("exception");
            if ("".equalsIgnoreCase(useraction)) {
                throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
            } else if ("".equalsIgnoreCase(except) && "raiseexception".equalsIgnoreCase(useraction)) {
                throw new ValidatorException(new FacesMessage("Please Take Exception !!!"));
            }
        }

        //posting response
        if (activityName.equalsIgnoreCase("Posting_response")) {
            if ("".equalsIgnoreCase(useraction)) {
                throw new ValidatorException(new FacesMessage("Please Take decision !!!"));
            }
            if (formObject.getNGValue("deduc_accept1").equalsIgnoreCase("accepted") || formObject.getNGValue("deduc_accept2").equalsIgnoreCase("accepted") || formObject.getNGValue("deduc_accept3").equalsIgnoreCase("accepted") || formObject.getNGValue("deduc_accept4").equalsIgnoreCase("accepted") || formObject.getNGValue("deduc_accept5").equalsIgnoreCase("accepted")) {
                System.out.println("muldi deduct doone ");
                formObject.setNGValue("multi_deduct", "Y");
            } else {
                formObject.setNGValue("multi_deduct", "N");
            }
        }

        // ro line entry missing code 
        if (activityName.equalsIgnoreCase("RO_Commercial") && (useraction.equalsIgnoreCase("sendforprocessing"))) {
            System.out.println("inside ro for line entry");
            int rowcount = formObject.getLVWRowCount("q_cash_app_ar");
            System.out.println("row count " + rowcount);
            if (rowcount < 1) {
                if (formObject.getNGValue("ca_payment_method").equalsIgnoreCase("ar") && (formObject.getNGValue("ca_type_adv_ar_unallocated").equalsIgnoreCase("CASH"))) {
                    System.out.println("payment ar " + formObject.getNGValue("ca_payment_method") + " stand  " + formObject.getNGValue("ca_type_adv_ar_unallocated"));

                } else {
                    throw new ValidatorException(new FacesMessage("Line Details Entry Missing, Please check ", ""));
                }
            }
        }
        if (activityName.equalsIgnoreCase("Initiator") && (useraction.equalsIgnoreCase("sendforprocessing"))) {
            System.out.println("inside Initiator for line entry");
            int rowcount = formObject.getLVWRowCount("q_cash_app_ar");
            System.out.println("row count " + rowcount);
            if (rowcount < 1) {
                if (formObject.getNGValue("ca_payment_method").equalsIgnoreCase("ar") && (formObject.getNGValue("ca_type_adv_ar_unallocated").equalsIgnoreCase("CASH"))) {
                    System.out.println("payment ar " + formObject.getNGValue("ca_payment_method") + " stand  " + formObject.getNGValue("ca_type_adv_ar_unallocated"));

                } else {
                    throw new ValidatorException(new FacesMessage("Line Details Entry Missing, Please check ", ""));
                }
            }
        }

        if (activityName.equalsIgnoreCase("Initiator")) {
            System.out.println("Checking for Document list and Doc category");
            System.out.println(" cheque document " + formObject.getNGValue("CheckBox16"));
            System.out.println(" cheque document " + formObject.getNGValue("CheckBox1"));
            modeofPayment = formObject.getNGValue("ca_mode_of_payment");
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
            modeofPayment = formObject.getNGValue("ca_mode_of_payment");
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

        //functional amount and liquidice amount is  equal  code
        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            modeofPayment = formObject.getNGValue("ca_mode_of_payment");
            if ("online".equalsIgnoreCase(modeofPayment)) {
                String liqAmount = formObject.getNGValue("amount").trim();
                String functionalAmt = formObject.getNGValue("ca_functional_amt").trim();
                System.out.println("liq amt " + liqAmount + "  function  " + functionalAmt);
                if (liqAmount.equalsIgnoreCase(functionalAmt)) {
                    System.out.println("Done  kuldeep" + functionalAmt + " " + liqAmount);
                } else {
                    throw new ValidatorException(new FacesMessage("Functional Amount and liquidice Amount is not equal!., Please check.", ""));
                }
            }
        }

        // line details total amount alert
        if (activityName.equalsIgnoreCase("Initiator")) {
            System.out.println("inside line code");
            String modeOfPayment = "";
            String stand = "";
            modeOfPayment = formObject.getNGValue("ca_mode_of_payment");
            stand = formObject.getNGValue("ca_type_adv_ar_unallocated");
            System.out.println("chequ date   " + modeOfPayment);
            if (modeOfPayment.equalsIgnoreCase("cheque") && (stand.equalsIgnoreCase("CASH"))) {
                // fieldMadatry();
                lineAmount();
            } else if (modeOfPayment.equalsIgnoreCase("cheque") && (stand.equalsIgnoreCase("MISC"))) {
                lineAmount();
            }
            System.out.println("inside end code");
        }
        if (activityName.equalsIgnoreCase("RO_Commercial")) {
            System.out.println("inside line code");
            String modeOfPayment = "";
            String stand = "";
            stand = formObject.getNGValue("ca_type_adv_ar_unallocated");
            modeOfPayment = formObject.getNGValue("ca_mode_of_payment");
            System.out.println("chequ date   " + modeOfPayment);
            if (modeOfPayment.equalsIgnoreCase("online") && (stand.equalsIgnoreCase("CASH"))) {
                //  fieldMadatry();
                lineAmount();
                deleteLiqSeq();
            } else if (modeOfPayment.equalsIgnoreCase("online") && (stand.equalsIgnoreCase("MISC"))) {
                lineAmount();
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

        if (activityName.equalsIgnoreCase("CN_Entry")) {
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

    private void setLegalEntity() {
        System.out.println("legal entity");

        String Query_mdm_currency_code = "select distinct(legal_entity) from mdm_bu;"; // mdm table
        System.out.println("1legal +" + Query_mdm_currency_code);
        List<List<String>> result15 = null;
        try {
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            System.out.println("legal " + result15);
            formObject.addComboItem("ca_company_name", "Select", "");
            for (int i = 0; i <= result15.size(); i++) {
                String legalEntity = result15.get(i).get(0);
                formObject.addComboItem("ca_company_name", legalEntity, legalEntity);
            }
            //   formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after mdm_performance_unit");
    }

    private void divisionCode(String flag) {
        try {
            String queryBU = "";
            String entity = formObject.getNGValue("ca_company_name");
            System.out.println("entity name : " + entity);
            queryBU = "select bu_desc, bu_code from mdm_bu where legal_entity like '" + entity + "' order by bu_code";
            System.out.println("entity name : " + queryBU);
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
            //  formObject.RaiseEvent("SAVE");

        } catch (Exception e) {
            System.out.println("company name unit error " + e);
        }
    }

    private void setPU() {
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
            //   formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after mdm_performance_unit");
    }

    private void setProjectCode() {
        List<List<String>> result1 = null;
        String getProjectCode = "";
        // formObject.clear("project_code_control");
        System.out.println("inside project code");
        getProjectCode = "select project_code from mdm_ar_project_code order by project_code";//mdm_project_wbs_code
        System.out.println("project code query ***** " + getProjectCode);
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

    // customer liquidice transaction data
    private void liqTranssction(String flag) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String Query_mdm_currency_code = "";
        //  Query_mdm_currency_code = "select date,seq_no from mdm_ar_liquidice_report where lock NOT IN ('Y')"; // mdm table
        List<List<String>> result15 = null;
        try {
            Query_mdm_currency_code = "select value_date,seq_no,sr_no from mdm_ar_liquidice_report where lock NOT IN ('Y')";

            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            formObject.addComboItem("liq_list", "select", "");
            // formObject.addComboItem("type_deduction_control", "NA", "NA");
            for (int i = 0; i <= result15.size(); i++) {
                String sequenceDate = result15.get(i).get(0) + " : " + result15.get(i).get(1);
                String sequenceNo = result15.get(i).get(1);
                String srNo = result15.get(i).get(2);

                //  System.out.println("description and liq_list : " + sequenceDate + ", " + sequenceNo);
                formObject.addComboItem("liq_list", sequenceDate, srNo);
            }
            //   formObject.RaiseEvent("SAVE");
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after liq_list type");
    }

    private void setDocCategory() {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        List<List<String>> result1 = null;
        String getsetDocument = "";
        formObject.clear("ca_documnt_category");
        System.out.println("inside ___ setDOcument");

        getsetDocument = "select document_category,bu_code from ar_mdm_doc_category where bu_code like '" + bucode + "'";
        System.out.println("getsetDocument query ***** " + getsetDocument);
        try {
            result1 = formObject.getDataFromDataSource(getsetDocument);
            formObject.addComboItem("ca_documnt_category", "select", "");
            for (int i = 0; i < result1.size(); i++) {
                // System.out.println("all document data");
                formObject.addComboItem("ca_documnt_category", result1.get(i).get(0), result1.get(i).get(0));
            }
        } catch (Exception e) {
            System.out.println("project code name unit error " + e);
        }
    }

    private void setDeductionType() {
        String Query_mdm_currency_code = "select type_of_deduction from mdm_ar_type_of_deduction"; // mdm table
        List<List<String>> result15 = null;
        try {
            System.out.println("inside try");
            result15 = formObject.getDataFromDataSource(Query_mdm_currency_code);
            // formObject.addComboItem("type_deduction_control", "select", "");
            // formObject.addComboItem("type_deduction_control", "NA", "NA");
            for (int i = 0; i <= result15.size(); i++) {
                String deductionType = result15.get(i).get(0);
                //  System.out.println("description and deduction : " + deductionType + ", " + deductionType);

                // formObject.addComboItem("type_deduction_control", deductionType, deductionType);
                //deduction type
                formObject.addComboItem("multideduc_bu1", deductionType, deductionType);
                formObject.addComboItem("multideduc_bu2", deductionType, deductionType);
                formObject.addComboItem("multideduc_bu3", deductionType, deductionType);
                formObject.addComboItem("multideduc_bu4", deductionType, deductionType);
                formObject.addComboItem("multideduc_bu5", deductionType, deductionType);
            }
            System.out.println("before catch");
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after deduction type");
    }

    private void OrgLoc() {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        System.out.println("bu code " + bucode);
        List<List<String>> result1 = null;
        String getOrgLoc = "";
        formObject.clear("location_code_control");
        getOrgLoc = "select distinct(inv_location) from mdm_thermax_gstn where bu_code like '" + bucode + "'";

        System.out.println("getOrgLoc query ***** " + getOrgLoc);
        try {
            result1 = formObject.getDataFromDataSource(getOrgLoc);
            formObject.addComboItem("location_code_control", "select", "");

            for (int i = 0; i < result1.size(); i++) {
                // System.out.println("all getOrgLoc data");
                formObject.addComboItem("location_code_control", result1.get(i).get(0), result1.get(i).get(0));
            }
        } catch (Exception e) {
            System.out.println("org location code name uniterror  " + e);
        }
    }

    private void Oragnization() {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        System.out.println("division code " + bucode);
        List<List<String>> result1 = null;
        String getOrgLoc = "";
        formObject.clear("orgnization_control");

        getOrgLoc = "select distinct(inv_org) from mdm_thermax_gstn where bu_code like '" + bucode + "'";

        System.out.println("getOrgLoc query ***** " + getOrgLoc);
        try {
            result1 = formObject.getDataFromDataSource(getOrgLoc);
            formObject.addComboItem("orgnization_control", "select", "");
            for (int i = 0; i < result1.size(); i++) {
                // System.out.println("all getOrgLoc data");
                formObject.addComboItem("orgnization_control", result1.get(i).get(0), result1.get(i).get(0));
            }
        } catch (Exception e) {
            System.out.println("project code name unit error " + e);
        }
    }

    private void settaxCat() {
        String bucode = "";
        bucode = formObject.getNGValue("ca_division_code");
        List<List<String>> result11 = null;
        String getOrgLoc = "";
        formObject.clear("tax_cat_control");
        getOrgLoc = "select gst_category from mdm_gst_tax_master where bu_code like '" + bucode + "'";
        System.out.println("getOrgLoc query ***** " + getOrgLoc);
        try {
            result11 = formObject.getDataFromDataSource(getOrgLoc);
            formObject.addComboItem("tax_cat_control", "select", "");
            for (int i = 0; i < result11.size(); i++) {
                //System.out.println("all Tax category data");
                formObject.addComboItem("tax_cat_control", result11.get(i).get(0), result11.get(i).get(0));
            }
        } catch (Exception e) {
            System.out.println("project code name unit error " + e);
        }
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
        } catch (Exception e) {
            System.out.println("Exception e " + e.getMessage());
        }
        System.out.println("after cost center");
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
        String bankReceiptDate = formObject.getNGValue("receipt_date");
        String depositDate = formObject.getNGValue("ca_deposit_date");
        String narration = formObject.getNGValue("ca_narration_n");
        // add new data
        String chequeDate = formObject.getNGValue("ca_cheque_date");
        String chequeNo = formObject.getNGValue("ca_cheque_no");
        String exchangerateDate = formObject.getNGValue("ca_rate_date");
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

        System.out.println("exchange rate    " + exchangeRate + "flag " + ratType + " " + salesOrderNo + "exchangerateDate name " + exchangerateDate + " " + chequeNo + " " + drawnBank + " " + currencyType + " " + bankReceiptDate + " " + depositDate + " " + narration);
        String arPayment = formObject.getNGValue("ca_payment_method");

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
            String amount = "";
            String gstApplicable="";
            String arPaymentType = formObject.getNGValue("ca_payment_method");
            String standMisc = formObject.getNGValue("ca_type_adv_ar_unallocated");
            gstApplicable = formObject.getNGValue("gst_applicable_control");
            System.out.println("ar  " + arPayment + "stand  " + standMisc);
            if (arPaymentType.equalsIgnoreCase("ar") && standMisc.equalsIgnoreCase("CASH")) {
                System.out.println("cmplx data " + exchangeRate + "rate type   " + ratType + " " + segma1 + " " + segma2 + " " + segma3);
                values = "('" + divisionCode + "','" + receiptMethodDoc + "','" + typeofReceipt + "','" + chequeNo + "',(TO_DATE('" + chequeDate + "','dd/mm/yyyy')),'" + ReceviedAmt + "',(TO_DATE('" + depositDate + "','dd/mm/yyyy')),'" + drawnBank + "','"
                        + currencyType + "','" + activityName1 + "','" + narration + "','" + segma1 + "','" + segma2 + "','"
                        + segma3 + "','" + segma4 + "','" + segma5 + "','" + segma6 + "','" + segma7 + "','" + exchangeRate + "','" + ratType + "','" + customerName + "','" + customerSite + "','" + customerCode + "','" + Action + "','" + salesOrderNo + "','" + projectCode + "','" + processInstanceId + "',(TO_DATE('" + glDate + "','dd/mm/yyyy')),'" + divisionType + "',(TO_DATE('" + exchangerateDate + "','dd/mm/yyyy')))";

                String query_column = "(OPERATING_UNIT,METHOD_SOURCE,TYPE,AR_NUMBER,CHEQUE_DATE,RECEIPT_AMOUNT,DEPOSIT_DATE,BANK_NAME,CURRENCY_CODE,ACTIVITY_NAME,COMMENT_NARRATION,GL_ACCOUNT1, "
                        + "GL_ACCOUNT2,GL_ACCOUNT3,GL_ACCOUNT4,GL_ACCOUNT5,GL_ACCOUNT6,GL_ACCOUNT7,EXCHANGE_RATE,RATE_TYPE,CUSTOMER_NAME,CUSTOMER_SITE,CUSTOMER_NUMBER,ACTION,ATTRIBUTE1,ATTRIBUTE2,ATTRIBUTE10,RECEIPT_DATE,MULTIDIVISON_FLAG,EXCHANGE_RATE_DATE)";

                String sql = "Insert into xxtmx_ar_receipt_stg_tbl" + query_column + " values " + values;
                System.out.println("query " + sql);
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.executeUpdate(sql);
                insertFlag = "Y";
                System.out.println("record inserted");
                con.close();
            } else {
                int i=0;
                for (i = 0; i < result3.size(); i++) {
                    taxAmount = result3.get(i).get(0);
                    taxCategory = result3.get(i).get(1);
                    segma1 = result3.get(i).get(2);
                    segma2 = result3.get(i).get(3);
                    segma3 = result3.get(i).get(4);
                    segma4 = result3.get(i).get(5);
                    segma5 = result3.get(i).get(6);
                    segma6 = result3.get(i).get(7);
                    segma7 = result3.get(i).get(8);
                    amount = result3.get(i).get(12);

                    System.out.println("cmplx data " + amount + "rate type   " + i + " " + segma1 + " " + segma2 + " " + segma3);
                    values = "('" + i + "','" + amount + "','" + divisionCode + "','" + receiptMethodDoc + "','" + typeofReceipt + "','" + chequeNo + "',(TO_DATE('" + chequeDate + "','dd/mm/yyyy')),'" + ReceviedAmt + "',(TO_DATE('" + depositDate + "','dd/mm/yyyy')),'" + drawnBank + "','"
                            + currencyType + "','" + activityName1 + "','" + narration + "','" + segma1 + "','" + segma2 + "','"
                            + segma3 + "','" + segma4 + "','" + segma5 + "','" + segma6 + "','" + segma7 + "','" + exchangeRate + "','" + ratType + "','" + customerName + "','" + customerSite + "','" + customerCode + "','" + Action + "','" + salesOrderNo + "','" + projectCode + "','" + processInstanceId + "',(TO_DATE('" + glDate + "','dd/mm/yyyy')),'" + divisionType + "',(TO_DATE('" + exchangerateDate + "','dd/mm/yyyy')))";

                    String query_column = "(LINE_NUMBER,UNIT_PRICE,OPERATING_UNIT,METHOD_SOURCE,TYPE,AR_NUMBER,CHEQUE_DATE,RECEIPT_AMOUNT,DEPOSIT_DATE,BANK_NAME,CURRENCY_CODE,ACTIVITY_NAME,COMMENT_NARRATION,GL_ACCOUNT1, "
                            + "GL_ACCOUNT2,GL_ACCOUNT3,GL_ACCOUNT4,GL_ACCOUNT5,GL_ACCOUNT6,GL_ACCOUNT7,EXCHANGE_RATE,RATE_TYPE,CUSTOMER_NAME,CUSTOMER_SITE,CUSTOMER_NUMBER,ACTION,ATTRIBUTE1,ATTRIBUTE2,ATTRIBUTE10,RECEIPT_DATE,MULTIDIVISON_FLAG,EXCHANGE_RATE_DATE)";

                    String sql = "Insert into xxtmx_ar_receipt_stg_tbl" + query_column + " values " + values;
                    System.out.println("query " + sql);
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.executeUpdate(sql);
                    insertFlag = "Y";
                    System.out.println("record inserted");
                }
                con.close();
            }
            /*
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

                    System.out.println("cmplx data " + exchangeRate + "rate type   " + ratType + " " + segma1 + " " + segma2 + " " + segma3);
                    values = "('" + divisionCode + "','" + receiptMethodDoc + "','" + typeofReceipt + "','" + chequeNo + "',(TO_DATE('" + chequeDate + "','dd/mm/yyyy')),'" + ReceviedAmt + "',(TO_DATE('" + depositDate + "','dd/mm/yyyy')),'" + drawnBank + "','"
                            + currencyType + "','" + activityName1 + "','" + narration + "','" + segma1 + "','" + segma2 + "','"
                            + segma3 + "','" + segma4 + "','" + segma5 + "','" + segma6 + "','" + segma7 + "','" + exchangeRate + "','" + ratType + "','" + customerName + "','" + customerSite + "','" + customerCode + "','" + Action + "','" + salesOrderNo + "','" + projectCode + "','" + processInstanceId + "',(TO_DATE('" + glDate + "','dd/mm/yyyy')),'" + divisionType + "',(TO_DATE('" + exchangerateDate + "','dd/mm/yyyy')))";

                    String query_column = "(OPERATING_UNIT,METHOD_SOURCE,TYPE,AR_NUMBER,CHEQUE_DATE,RECEIPT_AMOUNT,DEPOSIT_DATE,BANK_NAME,CURRENCY_CODE,ACTIVITY_NAME,COMMENT_NARRATION,GL_ACCOUNT1, "
                            + "GL_ACCOUNT2,GL_ACCOUNT3,GL_ACCOUNT4,GL_ACCOUNT5,GL_ACCOUNT6,GL_ACCOUNT7,EXCHANGE_RATE,RATE_TYPE,CUSTOMER_NAME,CUSTOMER_SITE,CUSTOMER_NUMBER,ACTION,ATTRIBUTE1,ATTRIBUTE2,ATTRIBUTE10,RECEIPT_DATE,MULTIDIVISON_FLAG,EXCHANGE_RATE_DATE)";

                    String sql = "Insert into xxtmx_ar_receipt_stg_tbl" + query_column + " values " + values;
                    System.out.println("query " + sql);
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.executeUpdate(sql);
                    insertFlag = "Y";
                    System.out.println("record inserted");
                }
             */

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
        double defoultValue = 1.00;
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
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String functionAmount = decimalFormat.format(total_amount);
        formObject.setNGValue("ca_functional_amt", functionAmount);
//        String newStr = TotalAmt.replace("$", "");
//        String newStr = TotalAmt.replace("$", "").replace(",", "");
        //  System.out.println("kpal " + total_amount);

    }

    //line details amount field 
    private void lineAmount() {
        String paymentType = "";
        String getadvanceAmount = "";
        String getcmi = "";
        String getUnallocated = "";
        String misc = "";
        String receviedamt = "";
        float total_line_amount = 1;
        float getadvance_Amount = 0;
        float getcmi_amt = 0;
        float getUnallocated_amt = 0;
        float recivedAmt = 0;
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
        misc = formObject.getNGValue("ca_type_adv_ar_unallocated");
        receviedamt = formObject.getNGValue("ca_received_amt");
        if ("CASH".equalsIgnoreCase(misc)) {
            if ("combined".equalsIgnoreCase(paymentType)) {
                float finalCombindAmt = 0.0f;
                float advance_Amt1 = 0.0f;
                float cmi_Amt1 = 0.0f;
                float unallocated_Amt1 = 0.0f;
                String advanceAmt1 = "";
                String cmiAmt1 = "";
                String unallocatedAmt1 = "";
                advanceAmt1 = formObject.getNGValue("ca_advance_amount").trim();
                cmiAmt1 = formObject.getNGValue("ca_cmi").trim();
                unallocatedAmt1 = formObject.getNGValue("ca_unallocated").trim();
                if (unallocatedAmt1.equalsIgnoreCase("")) {
                    unallocatedAmt1 = "0";
                }
                if (cmiAmt1.equalsIgnoreCase("")) {
                    cmiAmt1 = "0";
                }
                if (advanceAmt1.equalsIgnoreCase("")) {
                    advanceAmt1 = "0";
                }
                advance_Amt1 = Float.parseFloat(advanceAmt1);
                cmi_Amt1 = Float.parseFloat(cmiAmt1);
                unallocated_Amt1 = Float.parseFloat(unallocatedAmt1);
                finalCombindAmt = advance_Amt1 + cmi_Amt1 + unallocated_Amt1;
                System.out.println("combined amount " + finalCombindAmt + "total ");
                total_line_amount = Float.parseFloat(resultcash.get(0).get(0));
                if (finalCombindAmt == total_line_amount) {
                    System.out.println("combind total  " + finalCombindAmt);
                } else {
                    throw new ValidatorException(new FacesMessage("Combined Amount or Total Line Amount is not equal!., Please check.", ""));
                }
            } else if ("advance".equalsIgnoreCase(paymentType)) {
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
                    throw new ValidatorException(new FacesMessage("Line level amount is not matching with CMI amount, Please check ", ""));
                }
            } else if ("unallocated".equalsIgnoreCase(paymentType)) {
                getUnallocated_amt = Float.parseFloat(getUnallocated);
                total_line_amount = Float.parseFloat(resultcash.get(0).get(0));
                if (total_line_amount == getUnallocated_amt) {
                    System.out.println("total line amount " + total_line_amount + " advance amount amount " + getadvance_Amount);
                } else {
                    throw new ValidatorException(new FacesMessage("Line level amount is not matching with Unallocated amount, Please check ", ""));
                }
            }
        } else if ("MISC".equalsIgnoreCase(misc)) {
            recivedAmt = Float.parseFloat(receviedamt);
            total_line_amount = Float.parseFloat(resultcash.get(0).get(0));
            if (total_line_amount == recivedAmt) {
                System.out.println("total line amount " + total_line_amount + " recivedAmt amount amount " + recivedAmt);
            } else {
                throw new ValidatorException(new FacesMessage("Line level amount is not matching with Received Amount, Please check ", ""));
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
        validationMap.put("ca_transaction_type", "Transaction Type is a mandatory filed , kindly fill");
        validationMap.put("ca_booking_date", "GL Date is a mandatory filed , kindly fill");
        validationMap.put("ca_payment_method", "Payment Type is a mandatory filed , kindly fill");
        validationMap.put("ca_pu_name", "PU Name Type is a mandatory filed , kindly fill");
        validationMap.put("ca_cheque_date", "Cheque Date is a mandatory filed , kindly fill");
        validationMap.put("ca_cheque_no", "Cheque No is a mandatory filed , kindly fill");
        validationMap.put("type_of_transaction", "Type Of Transaction is a mandatory filed , kindly fill");
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

    private void custValidat() {
        String custCode = formObject.getNGValue("ca_customer_code");
        String stand = formObject.getNGValue("ca_type_adv_ar_unallocated");
        System.out.println("cust  " + custCode + " stand " + stand);
        if ("CASH".equalsIgnoreCase(stand)) {
            System.out.println("cust  " + stand);
            if ("".equalsIgnoreCase(custCode)) {
                throw new ValidatorException(new FacesMessage("Customer Code is not enter detail., Please check.", ""));
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
            String daccept1 = formObject.getNGValue("deduc_accept1");
            String daccept2 = formObject.getNGValue("deduc_accept2");
            String daccept3 = formObject.getNGValue("deduc_accept3");
            String daccept4 = formObject.getNGValue("deduc_accept4");
            String daccept5 = formObject.getNGValue("deduc_accept5");
            if ("accepted".equalsIgnoreCase(daccept1)) {
                System.out.println("daccept1   " + daccept1);
                String mdcn1 = formObject.getNGValue("multideduc_cn1");
                if ("".equalsIgnoreCase(mdcn1)) {
                    throw new ValidatorException(new FacesMessage("CN1 is not enter detail., Please check.", ""));
                }
            }
            if ("accepted".equalsIgnoreCase(daccept2)) {
                System.out.println("daccept2   " + daccept2);
                String mdcn2 = formObject.getNGValue("multideduc_cn2");
                if ("".equalsIgnoreCase(mdcn2)) {
                    throw new ValidatorException(new FacesMessage("CN2 is not enter detail., Please check.", ""));
                }
            }

            if ("accepted".equalsIgnoreCase(daccept3)) {
                System.out.println("daccept3   " + daccept3);
                String mdcn3 = formObject.getNGValue("multideduc_cn3");
                if ("".equalsIgnoreCase(mdcn3)) {
                    throw new ValidatorException(new FacesMessage("CN3 is not enter detail., Please check.", ""));
                }
            }
            if ("accepted".equalsIgnoreCase(daccept4)) {
                System.out.println("daccept4   " + daccept4);
                String mdcn4 = formObject.getNGValue("multideduc_cn4");
                if ("".equalsIgnoreCase(mdcn4)) {
                    throw new ValidatorException(new FacesMessage("CN4 is not enter detail., Please check.", ""));
                }
            }
            if ("accepted".equalsIgnoreCase(daccept5)) {
                System.out.println("daccept5   " + daccept5);
                String mdcn5 = formObject.getNGValue("multideduc_cn5");
                if ("".equalsIgnoreCase(mdcn5)) {
                    throw new ValidatorException(new FacesMessage("CN5 is not enter detail., Please check.", ""));
                }
            }
        }
    }

    // raised exception data 
    private void exceptionraised() {
         System.out.println("inside ro validation kp ");
        String transactionType = "";
        String typeOfTrans = "";
        String divisionType = "";
        String paymentMethod = "";
        String receiptofType = "";
        String gstApplicable = "";
        gstApplicable = formObject.getNGValue("gst_applicable_control");
        receiptofType = formObject.getNGValue("ca_type_adv_ar_unallocated");
        paymentMethod = formObject.getNGValue("ca_payment_method");
        divisionType = formObject.getNGValue("ca_division_type");
        typeOfTrans = formObject.getNGValue("type_of_transaction");
        transactionType = formObject.getNGValue("ca_transaction_type");
        System.out.println("paymentMethod  " + paymentMethod);
        if ("ar".equalsIgnoreCase(paymentMethod)) {
             System.out.println("inside ro validation kp2 "+paymentMethod);
            formObject.setEnabled("ca_advance_amount", false);
            formObject.setEnabled("ca_cmi", false);
            formObject.setEnabled("ca_unallocated", false);
            formObject.setEnabled("ca_ar_amount", true);
            formObject.setEnabled("Frame3", false);//gst frame
            formObject.setEnabled("Frame12", false);//advance frame
            // formObject.setEnabled("Frame10", true);//linking section true	
            formObject.setEnabled("total_applied_amt", false);
        } else if ("advance".equalsIgnoreCase(paymentMethod)) {
            formObject.setEnabled("ca_advance_amount", true);
            formObject.setEnabled("ca_cmi", false);
            formObject.setEnabled("ca_unallocated", false);
            formObject.setEnabled("ca_ar_amount", false);
            formObject.setEnabled("Frame3", true);//gst frame
            formObject.setEnabled("Frame12", true);//advance frame
            formObject.setEnabled("Frame10", false);//linking section true
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
        } else if ("cmi".equalsIgnoreCase(paymentMethod)) {
            formObject.setEnabled("ca_advance_amount", false);
            formObject.setEnabled("ca_cmi", true);
            formObject.setEnabled("ca_unallocated", false);
            formObject.setEnabled("ca_ar_amount", false);
            formObject.setEnabled("Frame3", true);//gst frame
            formObject.setEnabled("Frame12", true);//advance frame
            formObject.setEnabled("Frame10", false);//linking section true
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
        } else if ("unallocated".equalsIgnoreCase(paymentMethod)) {
            formObject.setEnabled("ca_advance_amount", false);
            formObject.setEnabled("ca_cmi", false);
            formObject.setEnabled("ca_unallocated", true);
            formObject.setEnabled("ca_ar_amount", false);
            formObject.setEnabled("Frame3", true);//gst frame
            formObject.setEnabled("Frame12", true);//advance frame
            formObject.setEnabled("Frame10", false);//linking section true
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
        } else if ("combined".equalsIgnoreCase(paymentMethod)) {
             System.out.println("inside ro validation kp combind "+paymentMethod);
            formObject.setEnabled("ca_advance_amount", true);
            formObject.setEnabled("ca_cmi", true);
            formObject.setEnabled("ca_unallocated", true);
            formObject.setEnabled("ca_ar_amount", true);
            formObject.setEnabled("Frame3", true);//gst frame
            formObject.setEnabled("Frame12", true);//advance frame
            formObject.setEnabled("Frame10", true);//linking section true 
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
            formObject.setEnabled("total_applied_amt", false);
        } 
        //  type of transaction
        if ("export".equalsIgnoreCase(transactionType)) {
            formObject.setEnabled("ca_currency_type", true);
            formObject.setEnabled("ca_rate", true);
            formObject.setEnabled("ca_rate_date", true);
            System.out.println("set setCurrencyCode call");
        } else if ("domestic".equalsIgnoreCase(transactionType)) {
            formObject.setEnabled("ca_rate_type", false);
            formObject.setEnabled("ca_rate", false);
            formObject.setEnabled("ca_rate_date", false);
            formObject.addComboItem("ca_currency_type", "INR", "INR");
        } 
        
        if (typeOfTrans.equalsIgnoreCase("general") || typeOfTrans.equalsIgnoreCase("multidivision")) {
            formObject.setSheetEnable("Tab1", 1, false);
        } else if (typeOfTrans.equalsIgnoreCase("multideduction")) {
            formObject.setSheetEnable("Tab1", 0, true);
            formObject.setSheetEnable("Tab1", 1, true);
            formObject.setEnabled("multidedu_cn1", false);
            formObject.setEnabled("multideduc_cn1", false);
            formObject.setEnabled("multideduc_cn2", false);
            formObject.setEnabled("multideduc_cn3", false);
            formObject.setEnabled("multideduc_cn4", false);
            formObject.setEnabled("multideduc_cn5", false);
            formObject.setEnabled("cn_wi_no1", false);
            formObject.setEnabled("cn_wi_no2", false);
            formObject.setEnabled("cn_wi_no3", false);
            formObject.setEnabled("cn_wi_no4", false);
            formObject.setEnabled("cn_wi_no5", false);
        } 
        
        if ("Y".equalsIgnoreCase(divisionType)) {
            formObject.setEnabled("ca_multidivision_name", true);
        } else if ("N".equalsIgnoreCase(divisionType)) {
            formObject.setEnabled("ca_multidivision_name", false);
        } 
        if ("CASH".equalsIgnoreCase(receiptofType)) {
            //  setModeOfBusiness(pEvent);
            formObject.setEnabled("Frame15", false);
            formObject.setEnabled("total_applied_amt", false);
            formObject.setEnabled("Text3", false);
            // BAAN Field Disable
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
        } else if ("MISC".equalsIgnoreCase(receiptofType)) {
            formObject.setEnabled("Frame15", true);
            formObject.setEnabled("Frame12", false);
            formObject.setEnabled("Frame17", false);
            formObject.setEnabled("Frame3", false);
            formObject.setEnabled("Frame12", false);
            formObject.setEnabled("Frame14", false);
            formObject.setEnabled("ca_payment_method", false);
            formObject.setEnabled("ca_advance_amount", false);
            formObject.setEnabled("ca_cmi", false);
            formObject.setEnabled("ca_unallocated", false);
            formObject.setEnabled("ca_ar_amount", false);
            formObject.setEnabled("Frame10", false);
            formObject.setEnabled("amount_control", true);

            // BAAN Field Disable
            formObject.setEnabled("employee_control", false);
            formObject.setEnabled("ca_bank_code", false);
            formObject.setEnabled("ca_receipt_method", false);
            formObject.setEnabled("businessPart_control", false);
            formObject.setEnabled("misc_control", false);
            formObject.setEnabled("diemention_control", false);
            formObject.setEnabled("ca_bank_receipt_no", false);
            formObject.setEnabled("receipt_date", false);
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
        } 
        if ("no".equalsIgnoreCase(gstApplicable)) {
            formObject.setEnabled("Frame3", false);
        } else if ("yes".equalsIgnoreCase(gstApplicable)) {
            formObject.setEnabled("Frame3", true);
            formObject.setEnabled("own_gst_reg_no_control", false);
            formObject.setEnabled("cust_gst_reg_no_control", false);
        } 
        
        if (formObject.getNGValue("ca_mode_of_business").equalsIgnoreCase("nonproject")) {
            formObject.setEnabled("projectbtn", false);
            formObject.setEnabled("ca_so_no", false);
            formObject.setEnabled("Button9", false);
            formObject.setEnabled("ca_project_code", false);
        } else if (formObject.getNGValue("ca_mode_of_business").equalsIgnoreCase("project")) {
            formObject.setEnabled("projectbtn", true);
            formObject.setEnabled("ca_so_no", true);
            formObject.setEnabled("Button9", true);
            formObject.setEnabled("ca_project_code", true);
            //setSalesOrderNo("D");
        }
    }
//   end code 
    
}
