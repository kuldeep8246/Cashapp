package com.newgen.common;

import com.newgen.dmsapi.DMSXmlResponse;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.ListView;
import com.newgen.omniforms.component.Panel;
import com.newgen.omniforms.context.FormContext;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import javax.faces.component.UIComponent;

public class General implements Serializable {

    FormReference formObject = null;
    FormConfig formConfig = null;
    General objGeneral = null;
    String Query = null, assign = null;
    Calendar c = Calendar.getInstance();
    Date currentDate = new Date();
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    List<List<String>> resultarray, userarray, historyarray;
    public static NGEjbClient ngEjbClient = null;
    XMLParser xmlParser = new XMLParser();

    public String calculateTax(String line_total, String TAX_CODE) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("Inside calculate tax" + line_total + " - " + TAX_CODE);

        if (line_total.contains(".") && !line_total.contains("E")) {
            int index_of = line_total.indexOf('.');
            int decLength = line_total.substring(index_of, line_total.length()).length();
            if (decLength > 2) {
                line_total = line_total.substring(0, index_of + 3);
            } else {
                line_total = line_total;
            }
        } else {
            try {
                float linetotaltemp = Float.parseFloat(line_total);
                System.out.println("Line total temp : " + linetotaltemp);
                BigDecimal decimal = new BigDecimal(linetotaltemp);
                System.out.println("Decimal-> " + decimal);
                line_total = String.valueOf(decimal);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception : " + e.getMessage());
            }
        }

        System.out.println("Final value : " + line_total);

        String tax_val = "";
        String inputXml = sapInvokeXML("ZBAPI_AP_AUTOMATION_TAX_AMOUNT");
        inputXml = inputXml + "<Parameters>"
                + "<ImportParameters>"
                + "<COMP_CODE>" + formObject.getNGValue("comp_code") + "</COMP_CODE>"
                + "<TAX_CODE>" + TAX_CODE + "</TAX_CODE>"
                + "<TAX_JURIS></TAX_JURIS>"
                + "<CURR_KEY>" + formObject.getNGValue("currency") + "</CURR_KEY>"
                + "<NET_AMT>" + line_total + "</NET_AMT>"
                + "</ImportParameters>"
                + "</Parameters>"
                + "</WFSAPInvokeFunction_Input>";
        System.out.println("Input xml: " + inputXml);
        String outputXml = executeWithCallBroker(inputXml);
        xmlParser.setInputXML(outputXml);
        WFXmlResponse objXmlResponse = new WFXmlResponse(outputXml);
        System.out.println("After xml response");
        if (xmlParser.getValueOf("MainCode").equalsIgnoreCase("0")) {
            System.out.println("Main Code == 0");
            tax_val = xmlParser.getValueOf("TAX_DEDUCT");
            //formObject.setNGValue("Text62", tax_val);
        } else {
            tax_val = "";
            System.out.println("Main Code !=0");
        }
        return tax_val;
    }

    public String calculateBatch(String Item) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("Inside calculate batch");
        String batch_val = "";
        String inputXml = sapInvokeXML("BAPI_BATCH_CREATE");
        inputXml = inputXml + "<Parameters>"
                + "<ImportParameters>"
                + "<MATERIAL>" + Item + "</MATERIAL>"
                + "</ImportParameters>"
                + "</Parameters>"
                + "</WFSAPInvokeFunction_Input>";
        System.out.println("Input XML : " + inputXml);
        String outputXml = objGeneral.executeWithCallBroker(inputXml);
        xmlParser.setInputXML(outputXml);
        WFXmlResponse objXmlResponse = new WFXmlResponse(outputXml);
        System.out.println("After xml response");
        if (xmlParser.getValueOf("MainCode").equalsIgnoreCase("0")) {
            System.out.println("BAPI_BATCH_CREATE / Main Code ==0");
            batch_val = xmlParser.getValueOf("BATCH");
            System.out.println("Batch value : " + batch_val);
            /* if ("".equalsIgnoreCase(batch_val)) {
                        formObject.setEnabled("Text65", false);
                        formObject.setNGValue("Text64", "N");
                    } else {
                        formObject.setNGValue("Text65", batch_val);
                        formObject.setEnabled("Text65", true);
                        formObject.setNGValue("Text64", "Y");
                    } */
        } else {
            System.out.println("BAPI_BATCH_CREATE / Main Code !=0");
        }
        return batch_val;
    }

    public String convertnamedatetosapdate(String aDate) {

        aDate = aDate.replace("/", ".");
        return aDate;
    }

    public void setPOHistory(String pid) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        //Query = "Select distinct MAT_DOC from complex_orient_poitem_history where pinstanceid = '" + pid + "'";
        Query = "select po_item , item from complex_orient_invoice_item where pinstanceid = '" + pid + "'";
        resultarray = formObject.getDataFromDataSource(Query);
        if (resultarray.size() > 0) {
            for (int i = 0; i < resultarray.size(); i++) {
                Query = "Select distinct REF_DOC_NO,MATERIAL,QUANTITY,MAT_DOC,DOC_DATE,VAL_LOCCUR,MOVE_TYPE,PO_ITEM,"
                        + "REF_DOC_YR,"
                        + "REF_DOC_IT,TAX_CODE,PO_ITEM,REF_DOC from complex_orient_poitem_history "
                        + "where pinstanceid = '" + pid + "' and "
                        + "PO_ITEM = '" + resultarray.get(i).get(0) + "' and "
                        + "MATERIAL like '%" + resultarray.get(i).get(1) + "' and "
                        + "REF_DOC_NO = '" + formObject.getNGValue("InvoiceNo") + "' ";
                System.out.println("Po History Query:" + Query);
                historyarray = formObject.getDataFromDataSource(Query);
                String xmlnew = "";
                if (historyarray.size() > 0) {
                    for (int j = 0; j < historyarray.size(); j++) {
                        xmlnew = xmlnew + "<ListItem><SubItem>" + historyarray.get(j).get(0)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(1)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(2)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(3)
                                + "</SubItem><SubItem>" + convertSqlDateTonewgenDate(historyarray.get(j).get(4))
                                + "</SubItem><SubItem>" + historyarray.get(j).get(5)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(6)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(7)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(8)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(9)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(10)
                                + "</SubItem><SubItem>" + ""
                                + "</SubItem><SubItem>" + ""
                                + "</SubItem><SubItem>" + historyarray.get(j).get(12)
                                + "</SubItem></ListItem>";
                    }
                    System.out.println("XML : " + xmlnew);
                    formObject.NGAddListItem("ListView6", xmlnew);
                }
            }
            String movtype = "";
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    movtype = "103";
                } else {
                    movtype = "105";
                }
                Query = "select max (mat_doc) from  complex_orient_poitem_history where pinstanceid = '" + pid + "' "
                        + "and move_type = '" + movtype + "' and REF_DOC_NO  = '" + formObject.getNGValue("InvoiceNo") + "'";
                resultarray = formObject.getDataFromDataSource(Query);
                String mat_doc = resultarray.get(0).get(0);
                System.out.println("Max mat doc : " + mat_doc);
                if (null != mat_doc) {
                    ListView lv0 = (ListView) formObject.getComponent("ListView6");
                    int rowcount = lv0.getRowCount();
                    int selectrow = 0;
                    for (int r = 0; r < rowcount; r++) {
                        if (mat_doc.equalsIgnoreCase(formObject.getNGValue("ListView6", r, 3))) {
                            selectrow = r;
                            for (int i = 0; i < 14; i++) {
                                formObject.setNGCellBackColor("ListView6", selectrow, i, Color.green);
                            }
                        }
                    }

                }
            }
            //new service list view *************************************************
            for (int i = 0; i < resultarray.size(); i++) {
                Query = "Select distinct REF_DOC_NO,MATERIAL,QUANTITY,MAT_DOC,DOC_DATE,VAL_LOCCUR,MOVE_TYPE,PO_ITEM,REF_DOC_YR,REF_DOC_IT,TAX_CODE,REF_DOC,"
                        + "SERIAL_NO,PROCESS_ID,DOC_YEAR,MATDOC_ITM,HIST_TYPE,PSTNG_DATE,"
                        + "VAL_FORCUR,CURRENCY,CL_VAL_LOC,BLOCKED_QY,BL_QTY,DB_CR_IND,VAL_TYPE,NO_MORE_GR,MOVE_REAS,"
                        + "ENTRY_DATE,ENTRY_TIME,IVVAL_LOC,IVVAL_FOR,PLANT,CONF_SER,CONDITION_1,DELIV_QTY,DELIV_UNIT,PUR_MAT,LOC_CURR,BATCH,"
                        + "CURRENCY_ISO,LOC_CURR_ISO,DELIV_UNIT_ISO,MATERIAL_EXTERNAL,MATERIAL_GUID,MATERIAL_VERSION,PUR_MAT_EXTERNAL,PUR_MAT_GUID,"
                        + "PUR_MAT_VERSION,REF_DOC_NO_LONG,STK_SEGMENT "
                        + "from complex_orient_poitem_history his "
                        + "where pinstanceid='" + pid + "'";
                System.out.println("Po History Query:" + Query);
                historyarray = formObject.getDataFromDataSource(Query);
                String xmlnew = "";
                if (historyarray.size() > 0) {
                    for (int j = 0; j < historyarray.size(); j++) {
                        xmlnew = xmlnew + "<ListItem><SubItem>" + historyarray.get(j).get(0)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(1)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(2)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(3)
                                + "</SubItem><SubItem>" + convertSqlDateTonewgenDate(historyarray.get(j).get(4))
                                + "</SubItem><SubItem>" + historyarray.get(j).get(5)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(6)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(7)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(8)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(9)
                                + "</SubItem><SubItem>" + historyarray.get(j).get(10)
                                + "</SubItem><SubItem>" + ""
                                + "</SubItem><SubItem>" + ""
                                + "</SubItem><SubItem>" + historyarray.get(j).get(12)
                                + "</SubItem></ListItem>";
                    }
                    System.out.println("XML : " + xmlnew);
                    formObject.NGAddListItem("ListView7", xmlnew);
                }
            }

        }

    }

    public String getQACheck(String material) {
        String qa_required = "", matdoc = "", return_status = "";
        String inputXml = sapInvokeXML("ZBAPI_AP_AUTOMATION_QI_CHECK");
        inputXml = inputXml + "<Parameters>"
                + "<ImportParameters>"
                + "<MAT_NO>" + material + "</MAT_NO>"
                + "</ImportParameters>"
                + "</Parameters>"
                + "</WFSAPInvokeFunction_Input>";
        System.out.println("Input XML : " + inputXml);
        String outputXml = executeWithCallBroker(inputXml);
        xmlParser.setInputXML(outputXml);
        WFXmlResponse objXmlResponse = new WFXmlResponse(outputXml);
        System.out.println("After xml response");
        if (xmlParser.getValueOf("MainCode").equalsIgnoreCase("0")) {
            matdoc = xmlParser.getValueOf("QI_DOC");
            return_status = xmlParser.getValueOf("TYPE");
            System.out.println("MatDoc : " + matdoc + "Return Type :" + return_status);
            if (matdoc.equalsIgnoreCase(material) || return_status.equalsIgnoreCase("S")) {
                qa_required = "Y";
            } else {
                qa_required = "N";
            }
        }
        return qa_required;
    }

    public String setUserforDOA(String inv_amount, String purch_group) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("Inside setUserforDOA");
        float inv_amount_check = 0;
        float inv_amount_int = Float.parseFloat(inv_amount);
        System.out.println("Parse Float value " + inv_amount_int);
        String amount_status = "";
        //Select Query to fetch all values_doa for that particular purch_group
        System.out.println("Before query");
        Query = "Select values_doa from complex_orient_doa_usermapping where purch_group ='" + purch_group + "'";
        System.out.println("Query for doa values : " + Query);
        resultarray = formObject.getDataFromDataSource(Query);
        System.out.println("Result size :" + resultarray.size());

        for (int i = 0; i < resultarray.size(); i++) {
            String temp_values_doa = resultarray.get(i).get(0);
            System.out.println("-1" + temp_values_doa);
            String[] split_values_doa = temp_values_doa.split(" ");
            System.out.println("-2" + split_values_doa.length);
            String check_val = split_values_doa[0];
            System.out.println("-3" + check_val);
            String val_1 = split_values_doa[1];
            System.out.println("-4" + val_1);
            String val_type_1 = split_values_doa[2];
            System.out.println("-3" + val_type_1);

            if (check_val == null || check_val.equalsIgnoreCase("")) {
                check_val = "default";
            }

            switch (check_val) {
                case "Upto":
                    System.out.println("Inside Upto");
                    inv_amount_check = calculateDOAAmount(val_1, val_type_1);
                    System.out.println("-Inv Amount Check" + inv_amount_check);
                    if (inv_amount_int < inv_amount_check) {
                        System.out.println("Inside if upto");
                        return temp_values_doa;
                    }
                    break;

                case "From":
                    System.out.println("Inside From");
                    String val_4 = split_values_doa[4];
                    String val_type_5 = split_values_doa[5];
                    inv_amount_check = calculateDOAAmount(val_1, val_type_1);
                    float inv_amount_check_2 = calculateDOAAmount(val_4, val_type_5);

                    if (inv_amount_check <= inv_amount_int && inv_amount_check_2 >= inv_amount_int) {
                        return temp_values_doa;
                    }
                    break;

                //int length_split_values_doa = split_values_doa.length;
                case "Above":
                    System.out.println("Inside Above");
                    inv_amount_check = calculateDOAAmount(val_1, val_type_1);
                    if (inv_amount_int > inv_amount_check) {
                        return temp_values_doa;
                    }
                    break;

                default:
                    temp_values_doa = "";
                    break;
            }
            System.out.println("End Switch");
        }

        return amount_status;
    }

    public static String executeWithCallBroker(String inputXml) {
        String outputXml = "";
        try {
            System.out.println("INPUT: " + inputXml);
            // outputXml = DMSCallBroker.execute(inputXml, "192.168.10.59", Short.parseShort("3333"), 0);
            ngEjbClient = NGEjbClient.getSharedInstance();
            //  outputXml = ngEJBClient.makeCall(serverIP, serverPort, serverType, inputXml);
            outputXml = ngEjbClient.makeCall("192.168.10.59", "8080", "JBOSSEAP", inputXml);//makeCall(inputXml);
            System.out.println("OUTPUT: " + outputXml);
        } catch (NGException ex) {
            //   Logger.getLogger(General.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputXml;
    }

    public String sapInvokeXML(String funcName) {
        String sapinvokexml = "<WFSAPInvokeFunction_Input>"
                + "<Option>WFSAPInvokeFunction</Option>"
                + "<SAPConnect>"
                + "<SAPHostName>OEECCQV</SAPHostName>"
                + "<SAPInstance>00</SAPInstance"
                + "><SAPClient>800</SAPClient>"
                + "<SAPUserName>AWCDMS</SAPUserName>"
                + "<SAPPassword>Sapsupport@123</SAPPassword>"
                + "<SAPLanguage>EN</SAPLanguage>"
                + "</SAPConnect>"
                + "<SAPFunctionName>" + funcName + "</SAPFunctionName>";
        return sapinvokexml;
    }

    public static String convertNewgenDateToSapDate(String sDate) {
        try {
            String formatDate = "";
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate_temp = formatter.parse(sDate);
            System.out.println("Date parse : " + sDate_temp);
            formatDate = formatter1.format(sDate_temp);
            System.out.println("Date format : " + formatDate);
            return formatDate;
        } catch (ParseException ex) {
            System.out.println("Error while convertin date: " + ex);
            return null;
        }
    }

    public static String convertSqlDateTonewgenDate(String sDate) {
        try {
            String formatDate = "";
            DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
            DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date sDate_temp = formatter1.parse(sDate);
            System.out.println("Date parse : " + sDate_temp);
            formatDate = formatter.format(sDate_temp);
            System.out.println("Date format : " + formatDate);
            return formatDate;
        } catch (ParseException ex) {
            System.out.println("Error while convertin date: " + ex);
            return null;
        }
    }

    public String DMSAdapterfolderplant(String plant, String po, String invoice) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        System.out.println("inside method of folder");
        Query = "select type,loc,address from complex_orient_orgmaster where plant='" + plant + "'";
        System.out.println("Query : " + Query);
        userarray = formObject.getDataFromDataSource(Query);
        String path = "Orient Electric\\";
        System.out.println("size userarray : " + userarray.size());
        if (userarray.size() > 0) {
            for (int i = 0; i < 3; i++) {

                String usertemp = userarray.get(0).get(i);
                System.out.println("usertemp : " + usertemp);
//            if (i == userarray.size() - 1) {
//                path = path + usertemp;
//            } else {
                path = path + usertemp + "\\";
//        }
                System.out.println("Path upto : " + path);
            }
        }
        path = path + po + "\\" + invoice;
        System.out.println("PATH : " + path);
        return path;
    }

    public String DMSPathforDepot(String plant, String division) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String path = "Orient Electric\\Depot\\", type = "", loc = "";
        Query = "select division_name from complex_orient_divisionmaster where division_code='" + division + "'";
        userarray = formObject.getDataFromDataSource(Query);
        type = userarray.get(0).get(0);
        System.out.println("Type : " + type);

        Query = "select location from complex_orient_depot_master where plant='" + plant + "'";
        System.out.println("Query : " + Query);
        userarray = formObject.getDataFromDataSource(Query);

        loc = userarray.get(0).get(0);
        System.out.println("Location : " + loc);
        path = path + type + "\\" + loc;
        return path;
    }

    public String DivisionOfPo(String Material) {
        String division = "";

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        String inputXml = sapInvokeXML("BAPI_MATERIAL_GET_DETAIL");
        inputXml = inputXml + "<Parameters>"
                + "<ImportParameters>"
                + "<MATERIAL>" + Material + "</MATERIAL>"
                + "</ImportParameters>"
                + "</Parameters>"
                + "</WFSAPInvokeFunction_Input>";
        String outputXml = executeWithCallBroker(inputXml);
        xmlParser.setInputXML(outputXml);
        WFXmlResponse objXmlResponse = new WFXmlResponse(outputXml);
        System.out.println("After xml response outputxml : " + outputXml);

        if (xmlParser.getValueOf("MainCode").equalsIgnoreCase("0")) {
            division = xmlParser.getValueOf("DIVISION");

        }
        System.out.println("Division : " + division);

        return division;
    }

    public String getVendorGSTN(String VendorCode) {
        String gstn = "";

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        String inputXml = sapInvokeXML("ZBAPI_AP_AUTOMATION_VENDOR_DET");
        inputXml = inputXml + "<Parameters>"
                + "<ImportParameters>"
                + "<VENDOR>" + VendorCode + "</VENDOR>"
                + "</ImportParameters>"
                + "</Parameters>"
                + "</WFSAPInvokeFunction_Input>";
        String outputXml = executeWithCallBroker(inputXml);
        xmlParser.setInputXML(outputXml);
        WFXmlResponse objXmlResponse = new WFXmlResponse(outputXml);
        System.out.println("After xml response outputxml : " + outputXml);

        if (xmlParser.getValueOf("MainCode").equalsIgnoreCase("0")) {
            gstn = xmlParser.getValueOf("GST_NO");
        }
        return gstn;
    }

    public String getVendorPAN(String VendorCode) {
        String pan = "";

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();

        String inputXml = sapInvokeXML("ZBAPI_AP_AUTOMATION_VENDOR_PAN");
        inputXml = inputXml + "<Parameters>"
                + "<ImportParameters>"
                + "<VENDOR>" + VendorCode + "</VENDOR>"
                + "</ImportParameters>"
                + "</Parameters>"
                + "</WFSAPInvokeFunction_Input>";
        String outputXml = executeWithCallBroker(inputXml);
        xmlParser.setInputXML(outputXml);
        WFXmlResponse objXmlResponse = new WFXmlResponse(outputXml);
        System.out.println("After xml response outputxml : " + outputXml);

        if (xmlParser.getValueOf("MainCode").equalsIgnoreCase("0")) {
            pan = xmlParser.getValueOf("PAN_NO");
        }
        return pan;
    }

    public float calculateDOAAmount(String val, String val_type) {
        float total = 0;
        System.out.println("Inside calculate");
        if (val_type.equalsIgnoreCase("Thousands")) {
            System.out.println("Inside  Thousand");
            total = Float.parseFloat(val) * 1000;

        } else if (val_type.equalsIgnoreCase("Lacs")) {
            System.out.println("Inside Lacs");
            total = Float.parseFloat(val) * 100000;
        }
        System.out.println("Total --" + total);
        return total;
    }

    public String getEmailId(String usernamedoa) {

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String mail = "";
        Query = "select MailId from PDBUser where UserName='" + usernamedoa + "'";
        System.out.println("Query for mail : " + Query);
        resultarray = formObject.getDataFromDataSource(Query);
        mail = resultarray.get(0).get(0);
        System.out.println("Mail : " + mail);

        return mail;
    }

    public String getEmailBody(String usernamedoa, String processid) {

        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String body = "", userindex = "";
        Query = "select UserIndex from PDBUser where UserName='" + usernamedoa + "'";
        System.out.println("Query for userindex : " + Query);
        resultarray = formObject.getDataFromDataSource(Query);
        userindex = resultarray.get(0).get(0);
        System.out.println("userindex : " + userindex);
        body = "http://192.168.10.58:8080/webdesktop/login/loginapp.jsf?WDDomHost=192.168.10.58:8080"
                + "&CalledFrom=OPENWI&"
                + "CabinetName=orient_uat&"
                + "UserName=" + usernamedoa + "&"
                + "UserIndex=" + userindex + "&"
                + "pid=" + processid + "&"
                + "wid=1&OAPDomHost=192.168.10.58:8080";

        return body;
    }

    public String connectFlow() {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        objGeneral = new General();
        String outputXml = null;
        String sessionId = "";
        String inputXml = "<? xml version=\"1.0\"?>"
                + "<NGOConnectCabinet_Input>"
                + "<Option>NGOConnectCabinet</Option><CabinetName>"
                + formConfig.getConfigElement("EngineName") + "</CabinetName><UserName>"
                + "supervisor" + "</UserName><UserPassword>"
                + "supervisor351" + "</UserPassword>"
                + "<Scope>ADMIN</Scope>"
                + "<UserExist>N</UserExist><CurrentDateTime></CurrentDateTime><UserType>U</UserType>"
                + "<MainGroupIndex>0</MainGroupIndex><Locale></Locale></NGOConnectCabinet_Input>";

        try {
            System.out.println("Creating flow server connection..");
            outputXml = objGeneral.executeWithCallBroker(inputXml);
            System.out.println("output xml " + outputXml);
            xmlParser.setInputXML(outputXml);
            if (xmlParser.getValueOf("Status").equalsIgnoreCase("0")) {
                sessionId = xmlParser.getValueOf("UserDBId");
                System.out.println(sessionId);
                return sessionId;
            } else {
                System.out.println("Error in making successful flow server connection");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error while executing product connection call. " + e);
            return null;
        }
    }

    public boolean isRepRowDeleted(String frameName, int index) {
        // FormReference formObject = FormContext.getCurrentInstance().getFormReference();        
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        UIComponent objComp = formObject.getComponent(frameName);

        if (objComp instanceof Panel) {
            Panel objPanel = (Panel) objComp;
            if (index > -1) {
                if (objPanel.getObjRowStatusMap() != null && objPanel.getObjRowStatusMap().containsKey(index)) {
                    String[] ar = objPanel.getObjRowStatusMap().get(index);
                    String insertionOrdId = ar[1];
                    if (insertionOrdId != null && !"".equals(insertionOrdId)) {
                        int insertOrdId = Integer.parseInt(insertionOrdId);
                        if (insertOrdId < 0) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public void setTransactionLogs() {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("Inside set transaction");
        String processDefId = formConfig.getConfigElement("ProcessDefId");
        String processInstanceId = formConfig.getConfigElement("ProcessInstanceId");
        String userName = formConfig.getConfigElement("UserName");
        String activityName = "";//formObject.getWFActivityName();

        try {
            System.out.println("Inside setAuditLogs--------------------");
            String file_status;
            Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String today = formatter.format(date);
            System.out.println("Printing date time @ng_orient_transaction_log--" + date);
            System.out.println("Printing processdefid @ng_orient_transaction_log--" + processDefId);
            System.out.println("Printing processInstanceId @ng_orient_transaction_log--" + processInstanceId);
            System.out.println("Printing Submitted by @ng_orient_transaction_log--" + userName);
            System.out.println("Printing submitted on @ng_orient_transaction_log--" + today);

            if (activityName.equalsIgnoreCase("Gate Entry")) {
                // Query for Next WorkItem                
                Query = "insert into ng_orient_transaction_log(status,transactionid,pinstanceid,processdefid,acted_on,acted_by,received_on,activity_name)"
                        + "values ('" + formObject.getNGValue("gate_decision") + "','','" + processInstanceId + "','" + processDefId + "','" + today + "','" + userName + "','','" + activityName + "')";
                System.out.println("Query 1/GE : " + Query);
                formObject.saveDataIntoDataSource(Query);
                // Query for Current WorkItem
                Query = "insert into ng_orient_transaction_log(status,transactionid,pinstanceid,processdefid,acted_on,acted_by,received_on,activity_name)"
                        + "values ('Pending','','" + processInstanceId + "','" + processDefId + "','','','" + today + "','Store')";
                System.out.println("Query 2/GE : " + Query);
                formObject.saveDataIntoDataSource(Query);

            } else if (activityName.equalsIgnoreCase("Store")) {

                // Getting form status
                file_status = formObject.getNGValue("store_decision");
                // Query for Next WorkItem
                Query = "insert into ng_orient_transaction_log(status,transactionid,pinstanceid,processdefid,acted_on,acted_by,received_on,activity_name)"
                        + "values ('Pending','','" + processInstanceId + "','" + processDefId + "','" + today + "','" + userName + "','','')";
                System.out.println("Query 1/Store : " + Query);
                formObject.saveDataIntoDataSource(Query);
                // Query for Current Workitem
                Query = "update ng_orient_transaction_log set status='" + file_status + "' and acted_on = '" + today + "' where status = 'Pending' "
                        + "and pinstanceid = '" + processInstanceId + "'";
                System.out.println("Query 2/Store : " + Query);
                formObject.saveDataIntoDataSource(Query);

            } else if (activityName.equalsIgnoreCase("Quality")) {

                // Getting form status
                file_status = formObject.getNGValue("quality_decision");
                // Query for Next WorkItem
                Query = "insert into ng_orient_transaction_log(status,transactionid,pinstanceid,processdefid,acted_on,acted_by,received_on,activity_name)"
                        + "values ('Pending','','" + processInstanceId + "','" + processDefId + "','" + today + "','" + userName + "','','')";
                formObject.saveDataIntoDataSource(Query);
                // Query for Current Workitem
                Query = "update ng_orient_transaction_log set status='" + file_status + "' and acted_on = '" + today + "' where status = 'Pending' and pinstanceid = '" + processInstanceId + "'";
                formObject.saveDataIntoDataSource(Query);

            } else if (activityName.equalsIgnoreCase("Accounts")) {

                // Getting form status
                // Query for Next WorkItem
                Query = "insert into ng_orient_transaction_log(status,transactionid,pinstanceid,processdefid,acted_on,acted_by,received_on,activity_name)"
                        + "values ('Pending','','" + processInstanceId + "','" + processDefId + "','" + today + "','" + userName + "','','')";
                formObject.saveDataIntoDataSource(Query);
                // Query for Current Workitem
                Query = "update ng_orient_transaction_log set status='Parked' and acted_on = '" + today + "' where status = 'Pending' and pinstanceid = '" + processInstanceId + "'";
                formObject.saveDataIntoDataSource(Query);

            } else if (activityName.equalsIgnoreCase("Finance")) {

                // Getting form status
                // Query for Previous WorkItem
                Query = "update ng_orient_transaction_log set status='Posted' and acted_on = '" + today + "' where status = 'Pending' and pinstanceid = '" + processInstanceId + "'";
                formObject.saveDataIntoDataSource(Query);

            }

        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }

    }

    public String MaterialAppend(String mnumber) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        System.out.println("Inside set MaterialAppend");
        int difference = 0;
        String material_temp = mnumber, materialnumber = "";
        difference = 18 - material_temp.length();
        System.out.println("Difference: " + difference);
        if (difference > 0) {
            System.out.println("Inside differencce material");
            materialnumber = String.format("%0" + (18 - material_temp.length()) + "d%s", 0, material_temp);
            System.out.println("material " + materialnumber);
        } else {
            materialnumber = material_temp;
        }

        return materialnumber;

    }

    public String checkSpace(String checkString) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        String data = "";
        System.out.println(" inside checkSpace");
        System.out.println(checkString);
        if (checkString.contains(".")) {
            System.out.println("1");
            if (checkString.contains(" ")) {
                System.out.println("2");
                data = checkString.replaceAll(" ", "");
            } else {
                data = checkString;
            }
        } else if (checkString.contains(" ")) {
            System.out.println("3");
            data = checkString.replaceAll(" ", ".");
        } else {
            System.out.println("4");
            data = checkString;
        }
        System.out.println(data);
        return data;
    }

}
