package com.newgen.common;

import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.component.Form;
import com.newgen.omniforms.component.PickList;
import com.newgen.omniforms.component.behavior.EventListenerImplementor;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.util.Constant;
import com.newgen.omniforms.util.Constant.EVENT;
import java.awt.Color;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;

public class PicklistListenerHandler_thrmx
        extends EventListenerImplementor
        implements Serializable {

    FormReference formObject = null;
    FormConfig formConfig = null;
    PickList objPicklist = null;
    List<List<String>> result;
    String controlName = "";
    String filter_value = "";
    String query = "";
    String processName = null;
    static String control = "";
    static List<String> feild_map;
    static String qry = "";
    static String qry1 = "";
    static String search = "";
    static String search2 = "";
    static String orderBy = "";

    public static void debitvalues(List<String> debit_feild, String debcontrol, String debsearch, 
            String debsearch2, String debquery, String debquery1, String order) {
        control = debcontrol.trim();
        feild_map = debit_feild;
        search = debsearch;
        search2 = debsearch2;
        qry = debquery;
        qry1 = debquery1;
        orderBy = order;
        System.out.println(" @ Sourabh debit value method ");
    }
//              = (qry1 + " (upper(" + search.trim() + ") like '%" + this.filter_value.trim().toUpperCase()
//                      + "%' or upper(" + search2.trim() + ") like '%" + this.filter_value.trim().toUpperCase() + "%' ) " + orderBy);

    public PicklistListenerHandler_thrmx() {
    }

    public PicklistListenerHandler_thrmx(String picklistid) {
        super(picklistid);
    }

    public PicklistListenerHandler_thrmx(String picklistid, Constant.EVENT compId) {
        super(picklistid, compId);
    }

    public void btnOk_Clicked(ActionEvent ae) {
        this.formObject = FormContext.getCurrentInstance().getFormReference();
        this.formConfig = FormContext.getCurrentInstance().getFormConfig();
        PickList m_objPickList = FormContext.getCurrentInstance().getDefaultPickList();
        Form obj = (Form) FormContext.getCurrentInstance().getFormReference();
        this.controlName = m_objPickList.getAssociatedTxtCtrl();
        this.processName = this.formConfig.getConfigElement("ProcessName");
        System.out.println("inside btnOk_Clicked button method");
        System.out.println("processName : " + this.processName);
        System.out.println("Control Name : " + this.controlName);
        System.out.println("Sourabh debit Sourabh control: " + control);
        System.out.println("Sourabh debit  query  : " + qry);
        System.out.println("Sourabh debit  search  : " + search);
        System.out.println("debit feild map  : " + feild_map);
        if ("ProjectNumber".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("ProjectNumber");
            this.formObject.setNGValue("ProjectNumber", m_objPickList.getSelectedValue().get(0));
        } else if ("SupplierCode".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("SupplierCode");
            this.formObject.clear("SupplierName");
            this.formObject.clear("SupplierSite");
            this.formObject.clear("GSTNOSupplier");
            this.formObject.clear("PANSupplier");
            this.formObject.clear("sup_glcode");
            this.formObject.setNGValue("SupplierCode", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("SupplierName", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("SupplierSite", m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("GSTNOSupplier", m_objPickList.getSelectedValue().get(4));
            this.formObject.setNGValue("PANSupplier", m_objPickList.getSelectedValue().get(3));
            this.formObject.setNGValue("Lib_GLCode", m_objPickList.getSelectedValue().get(9));
            this.formObject.setNGValue("PaymentTerms", "Immediate");

            this.formObject.setNGValue("Lib_DivisionCode", m_objPickList.getSelectedValue().get(6));
            this.formObject.setNGValue("Lib_Location", m_objPickList.getSelectedValue().get(7));
            this.formObject.setNGValue("Lib_CostCenter", m_objPickList.getSelectedValue().get(8));

            this.formObject.setNGValue("Lib_Future2", m_objPickList.getSelectedValue().get(12));

            this.formObject.setNGValue("DivisionCode", m_objPickList.getSelectedValue().get(13));
            this.formObject.setNGValue("Location", m_objPickList.getSelectedValue().get(14));
            this.formObject.setNGValue("ProjectCode", m_objPickList.getSelectedValue().get(17));
            this.formObject.setNGValue("CostCenter", m_objPickList.getSelectedValue().get(15));
        } else if ("Location".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("Location");
            this.formObject.setNGValue("Location", m_objPickList.getSelectedValue().get(0));
        } else if ("DivisionCode".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("DivisionCode");
            this.formObject.setNGValue("DivisionCode", m_objPickList.getSelectedValue().get(0));
        } else if ("ProjectCode".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("ProjectCode");
            this.formObject.setNGValue("ProjectCode", m_objPickList.getSelectedValue().get(1));
        } else if ("BuyerId".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("buyerId");
            this.formObject.clear("btn_buyerName");
            this.formObject.clear("BuyerEmail");

            this.formObject.setNGValue("buyerId", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("btn_buyerName", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("BuyerEmail", m_objPickList.getSelectedValue().get(3));
        } else if ("TDSSection".equalsIgnoreCase(this.controlName)) {
            this.formObject.clear("TDSSection");
            this.formObject.clear("TDSCategory");
            this.formObject.clear("TDSPercent");
            this.formObject.setNGValue("TDSSection", m_objPickList.getSelectedValue().get(3));
            this.formObject.setNGValue("TDSCategory", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("TDSPercent", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("suppliercode")) {
            System.out.println("chal jamere bhai");
            this.formObject.clear("suppliername");
            this.formObject.clear("suppliersite");
            this.formObject.clear("pannumbersupplier");
            this.formObject.clear("gst_no_supplier");

            this.formObject.setNGValue("suppliercode", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("suppliername", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("suppliersite", m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("pannumbersupplier", m_objPickList.getSelectedValue().get(3));
            this.formObject.setNGValue("gst_no_supplier", m_objPickList.getSelectedValue().get(4));
            this.formObject.setNGValue("paymentterms", m_objPickList.getSelectedValue().get(5));
            this.formObject.setNGValue("lib_divisioncode", m_objPickList.getSelectedValue().get(6));
            this.formObject.setNGValue("lib_location", m_objPickList.getSelectedValue().get(7));
            this.formObject.setNGValue("lib_costcenter", m_objPickList.getSelectedValue().get(8));
            this.formObject.setNGValue("lib_glcode", m_objPickList.getSelectedValue().get(9));

            this.formObject.setNGValue("lib_pu", m_objPickList.getSelectedValue().get(11));
            this.formObject.setNGValue("lib_future2", m_objPickList.getSelectedValue().get(12));
            System.out.println(
                    "jugaad ke baad m_objPickList.getSelectedValue().get(7); m_objPickList.getSelectedValue().get(6) "
                    + (String) m_objPickList.getSelectedValue().get(6) + ","
                    + (String) m_objPickList.getSelectedValue().get(7));
        } else if (this.controlName.equalsIgnoreCase("buyerempid")) {
            System.out.println("Buyer id");
            this.formObject.clear("buyername");
            this.formObject.clear("costcenter");
            System.out.println(" buyer id empid : " + (String) m_objPickList.getSelectedValue().get(0));
            System.out.println(" buyer id name : " + (String) m_objPickList.getSelectedValue().get(1));
            System.out.println(" buyer id costcenter : " + (String) m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("buyerempid", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("buyername", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("costcenter", m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("buyeremail", m_objPickList.getSelectedValue().get(3));
        } else if (this.controlName.equalsIgnoreCase("inventoryorgn")) {
            System.out.println("Buyer id inventorylocation");
            System.out.println("Invenotry values 1:" + (String) m_objPickList.getSelectedValue().get(0) + ", 2: "
                    + (String) m_objPickList.getSelectedValue().get(1));

            this.formObject.clear("inventoryorgn");
            this.formObject.clear("inventorylocation");
            this.formObject.setNGValue("inventoryorgn", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("inventorylocation", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("thermaxgstno", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("tdssection")) {
            System.out.println("TDS section pick list");
            this.formObject.clear("tdssection");
            this.formObject.clear("tdscode");
            System.out.println("TDS details 1 " + (String) m_objPickList.getSelectedValue().get(0) + ", 2: "
                    + (String) m_objPickList.getSelectedValue().get(1) + ", "
                    + (String) m_objPickList.getSelectedValue().get(2));

            this.formObject.setNGValue("tdssection", m_objPickList.getSelectedValue().get(3));
            this.formObject.setNGValue("tdscode", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("tdspercent", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("gl_code")) {
            System.out.println(" GL code *** ");
            this.formObject.clear("gl_code");
            System.out.println("GL Code **** gl code " + (String) m_objPickList.getSelectedValue().get(0)
                    + " Expense Type : " + (String) m_objPickList.getSelectedValue().get(1));

            this.formObject.setNGValue("gl_code", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("projectnumber")) {
            System.out.println(" GL code *** ");
            System.out.println("Project number **** " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("projectnumber", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("expendituretype")) {
            System.out.println(" expendituretype *** ");
            this.formObject.clear("expendituretype");
            System.out.println("expendituretype **** " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("expendituretype", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("project_code")) {
            System.out.println(" Project code *** ");
            System.out.println("Project number **** " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("project_code", m_objPickList.getSelectedValue().get(1));
        } else if (this.controlName.equalsIgnoreCase("fc_project_code")) {
            System.out.println(" **** fc_project_code  Button *** ");
            System.out.println("Project number **** " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("fc_project_code", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("fc_projectid", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("wbscodetaslcode")) {
            System.out.println(" **** WBS  Button *** ");
            System.out.println("Project number **** " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("wbscodetaslcode", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("fc_wbsid", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("location_new")) {
            System.out.println(" **** Lcoation of segma *** ");
            this.formObject.clear("location_new");
            System.out.println("new location **** " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("location_new", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("divisioncode")) {
            System.out.println("Division code");
            System.out.println("Division code 1:" + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("divisioncode");
            this.formObject.setNGValue("divisioncode", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("expend_org")) {
            System.out.println("expend_org code");
            System.out.println("expend_org  1:" + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("expend_org");
            this.formObject.setNGValue("expend_org", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("po_tdssection")) {
            System.out.println("TDS section pick list");
            this.formObject.clear("po_tdssection");
            this.formObject.clear("po_tdscat");
            this.formObject.clear("po_tdspercent");
            System.out.println("TDS details 1 " + (String) m_objPickList.getSelectedValue().get(0) + ", 2: "
                    + (String) m_objPickList.getSelectedValue().get(1) + ", "
                    + (String) m_objPickList.getSelectedValue().get(2));

            this.formObject.setNGValue("po_tdssection", m_objPickList.getSelectedValue().get(3));
            this.formObject.setNGValue("po_tdscat", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("po_tdspercent", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("buyer_emp_id")) {
            System.out.println("Buyer id");
            this.formObject.clear("buyername");
            this.formObject.clear("costcenter");
            System.out.println(" buyer id empid : " + (String) m_objPickList.getSelectedValue().get(0));
            System.out.println(" buyer id name : " + (String) m_objPickList.getSelectedValue().get(1));
            System.out.println(" buyer id costcenter : " + (String) m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("buyer_emp_id", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("buyer_name", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("buyer_phone_number", m_objPickList.getSelectedValue().get(3));
        } else if (this.controlName.equalsIgnoreCase("ca_customer_code")) {
            System.out.println("customer code");
            this.formObject.clear("ca_customer_code");
            this.formObject.clear("ca_customer_name");
            this.formObject.clear("ca_customer_site");
            System.out.println(" ca_customer_code : " + (String) m_objPickList.getSelectedValue().get(0));
            System.out.println(" ca_customer_name : " + (String) m_objPickList.getSelectedValue().get(1));
            System.out.println(" ca_site_code : " + (String) m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("ca_customer_code", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("ca_customer_name", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("ca_customer_site", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("ca_project_code")) {
            System.out.println("project code");
            this.formObject.clear("ca_projct_code");
            System.out.println(" ca_project_code : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("ca_project_code", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("ca_so_no")) {
            System.out.println("sales order no code");
            this.formObject.clear("ca_so_no");
            System.out.println(" ca_so_no : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("ca_so_no", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("sales_order_control")) {
            System.out.println("sales order no code");
            this.formObject.clear("sales_order_control");
            System.out.println(" sales_order_control : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("sales_order_control", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase(control.trim())) {
            System.out.println(control);
            for (int i = 0; i < feild_map.size(); i++) {
                this.formObject.clear(((String) feild_map.get(i)).trim());

                this.formObject.setNGValue(((String) feild_map.get(i)).trim(), m_objPickList.getSelectedValue().get(i));
            }
        } else if (this.controlName.equalsIgnoreCase("cr_doc_category")) {
            System.out.println("cr_document_category :----");
            this.formObject.clear("cr_doc_category");
            this.formObject.clear("cr_division_code");

            System.out.println(" doc_category : " + (String) m_objPickList.getSelectedValue().get(1));
            System.out.println(" cr_division_code : " + (String) m_objPickList.getSelectedValue().get(0));

            this.formObject.setNGValue("cr_doc_category", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("cr_division_code", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("cr_project_code")) {
            System.out.println("cr_project_code :----");
            this.formObject.clear("cr_project_code");

            System.out.println(" cr_project_code : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("cr_project_code", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("cr_customer_code_bil")) {
            System.out.println("cr_customer_code_bil :----");
            this.formObject.clear("cr_customer_code_bil");
            this.formObject.clear("cr_customer_name_bil");
            this.formObject.clear("cr_customer_site_bil");

            this.formObject.setNGValue("cr_customer_code_bil", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("cr_customer_name_bil", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("cr_customer_site_bil", m_objPickList.getSelectedValue().get(2));
        } else if (this.controlName.equalsIgnoreCase("cr_customer_code_shi")) {
            System.out.println("cr_customer_code_bil ");
            this.formObject.clear("cr_customer_code_shi");
            this.formObject.clear("cr_customer_name_shi");
            this.formObject.clear("cr_customer_site_shi");

            this.formObject.setNGValue("cr_customer_code_shi", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("cr_customer_name_shi", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("cr_customer_site_shi", m_objPickList.getSelectedValue().get(2));

            System.out.println("cr_customer_code_bil ");
        } else if (this.controlName.equalsIgnoreCase("cr_location")) {
            System.out.println("cr_location : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("cr_location");

            this.formObject.setNGValue("cr_customer_code_shi", m_objPickList.getSelectedValue().get(0));

            System.out.println("cr_location : " + (String) m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("cr_cost_center")) {
            System.out.println("cr_gl_code : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("cr_cost_center");

            this.formObject.setNGValue("cr_cost_center", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("cr_gl_code")) {
            System.out.println("cr_gl_code : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("cr_gl_code");

            this.formObject.setNGValue("cr_gl_code", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("cr_wbs_code")) {
            System.out.println("cr_wbs_code : " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("cr_wbs_code");

            this.formObject.setNGValue("cr_wbs_code", m_objPickList.getSelectedValue().get(0));
        } else if (this.controlName.equalsIgnoreCase("cr_pu_code_future1")) {
            System.out.println("cr_pu_code_future1 :" + (String) m_objPickList.getSelectedValue().get(0)
                    + " cr_future2 :" + (String) m_objPickList.getSelectedValue().get(1));

            this.formObject.clear("cr_pu_code_future1");
            this.formObject.clear("cr_future2");

            this.formObject.setNGValue("cr_pu_code_future1", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("cr_future2", m_objPickList.getSelectedValue().get(1));

            System.out.println("division code debit note");
        } else if (this.controlName.equalsIgnoreCase("cr_employee")) {
            System.out.println("cr_employee :" + (String) m_objPickList.getSelectedValue().get(0));
            System.out.println(" cr_business_partner :" + (String) m_objPickList.getSelectedValue().get(1));
            System.out.println(" cr_miscellaneous :" + (String) m_objPickList.getSelectedValue().get(2));
            System.out.println(" cr_dimension6 :" + (String) m_objPickList.getSelectedValue().get(3));

            this.formObject.clear("cr_employee");
            this.formObject.clear("cr_business_partner");
            this.formObject.clear("cr_miscellaneous");
            this.formObject.clear("cr_dimension6");

            this.formObject.setNGValue("cr_employee", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("cr_business_partner", m_objPickList.getSelectedValue().get(1));
            this.formObject.setNGValue("cr_miscellaneous", m_objPickList.getSelectedValue().get(2));
            this.formObject.setNGValue("cr_dimension6", m_objPickList.getSelectedValue().get(3));
        }
        btn_Ok_Clicked_For_Credit_Note(ae, m_objPickList, this.controlName);
    }

    public void btnNext_Clicked(ActionEvent ae) {
    }

    public void btnSearch_Clicked(ActionEvent ae) {
        this.formObject = FormContext.getCurrentInstance().getFormReference();
        this.formConfig = FormContext.getCurrentInstance().getFormConfig();
        this.objPicklist = FormContext.getCurrentInstance().getDefaultPickList();
        this.controlName = this.objPicklist.getAssociatedTxtCtrl();
        this.filter_value = this.objPicklist.getSearchFilterValue();
        String bu = this.formObject.getNGValue("business_unit");
        String entityname = this.formObject.getNGValue("entity_name");
        this.processName = this.formConfig.getConfigElement("ProcessName");
        System.out.println("inside btnSearch_Clicked button method");
        System.out.println("processName : " + this.processName);
        System.out.println("Control Name : " + this.controlName);

        String strBussinessUnit = null;
        if ("ProjectNumber".equalsIgnoreCase(this.controlName)) {
            strBussinessUnit = this.formObject.getNGValue("BussinessUnit");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select distinct project_code from mdm_project_wbs_code  where  upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%' order by project_code asc ");
            } else {
                this.query = "select distinct project_code from mdm_project_wbs_code order by project_code asc ";
            }
        } else if ("SupplierCode".equalsIgnoreCase(this.controlName)) {
            strBussinessUnit = this.formObject.getNGValue("BussinessUnit");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select vendor_code,vendor_name,vendor_site_code, pan_no, gst_number, payterm, liability_div, liability_loc,liability_cc,liability_gl_code, liability_proj_code, liability_future1, liability_future2, prepay_div, prepay_loc, prepay_cc, prepay_gl_code, prepay_proj_code, prepay_future1, prepay_future2 from mdm_vendor_master where bu_code = '" + strBussinessUnit + "' and upper(vendor_name) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(vendor_code) like '%" + this.filter_value.trim().toUpperCase() + "%' and  bu_code = '" + strBussinessUnit + "'");
            } else {
                this.query
                        = ("select vendor_code,vendor_name,vendor_site_code, pan_no, gst_number, payterm, liability_div, liability_loc,liability_cc,liability_gl_code, liability_proj_code, liability_future1, liability_future2, prepay_div, prepay_loc, prepay_cc, prepay_gl_code, prepay_proj_code, prepay_future1, prepay_future2 from mdm_vendor_master where bu_code = '" + strBussinessUnit + "' ");
            }
        } else if ("DivisionCode".equalsIgnoreCase(this.controlName)) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select distinct division_code from mdm_segma where legal_entity like '" + entityname + "' order by division_code");
            } else {
                this.query
                        = ("select distinct division_code from mdm_segma where legal_entity like '" + entityname + "' order by division_code");
            }
        } else if ("Location".equalsIgnoreCase(this.controlName)) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = (" select distinct location from mdm_segma where location is not null and upper(location) like '%" + this.filter_value.trim().toUpperCase() + "%'");
            } else {
                this.query = "select distinct location from mdm_segma where location is not null";
            }
        } else if ("ProjectCode".equalsIgnoreCase(this.controlName)) {
            strBussinessUnit = this.formObject.getNGValue("BussinessUnit");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select bu_code,project_code from mdm_project_wbs_code where upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + strBussinessUnit + "'");
            } else {
                this.query
                        = ("select bu_code,project_code from mdm_project_wbs_code WHERE bu_code = '" + strBussinessUnit + "'");
            }
        } else if ("BuyerId".equalsIgnoreCase(this.controlName)) {
            strBussinessUnit = this.formObject.getNGValue("BussinessUnit");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select emno, ename, cost_center, email_id from mdm_customer_master where upper(emno) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(ename) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + strBussinessUnit + "'");
            } else {
                this.query
                        = ("select emno, ename, cost_center, email_id from mdm_customer_master WHERE bu_code = '" + strBussinessUnit + "'");
            }
        } else if ("TDSSection".equalsIgnoreCase(this.controlName)) {
            strBussinessUnit = this.formObject.getNGValue("BussinessUnit");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select tds_section, tds_tax_category, tax_percent, tds_section_code from mdm_tds_tax where upper(tds_section) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(tds_tax_category) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + strBussinessUnit + "'");
            } else {
                this.query
                        = ("select tds_section, tds_tax_category, tax_percent, tds_section_code from mdm_tds_tax where bu_code = '" + strBussinessUnit + "'");
            }
        } else if (this.controlName.equalsIgnoreCase("pick")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select name,city from testkp where upper(name) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(name) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query = "select name,city from testkp order by name asc";
                System.out.println("query of tds else");
            }
        } else if (this.controlName.equalsIgnoreCase("Business_Unit")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select bu_desc,bu_code from mdm_bu where upper(description) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(description) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query = "select bu_desc,bu_code from mdm_bu order by description asc";
                System.out.println("query of tds else");
            }
        } else if (this.controlName.equalsIgnoreCase("suppliercode")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select vendor_code,vendor_name,vendor_site_code, pan_no, gst_number, payterm, liability_div, liability_loc,liability_cc,liability_gl_code, liability_proj_code, liability_future1, liability_future2 from mdm_vendor_master where bu_code = '" + bu + "' and upper(vendor_name) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(vendor_code) like '%" + this.filter_value.trim().toUpperCase() + "%' and  bu_code = '" + bu + "'");

                System.out.println("query of suppliercode group if--- " + this.query);
            } else {
                this.query
                        = ("select vendor_code,vendor_name,vendor_site_code, pan_no, gst_number, payterm, liability_div, liability_loc,liability_cc,liability_gl_code, liability_proj_code, liability_future1, liability_future2 from mdm_vendor_master where bu_code = '" + bu + "' ");

                System.out.println("query of suppliercode else" + this.query);
            }
        } else if (this.controlName.equalsIgnoreCase("buyerempid")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select emno, ename, cost_center, email_id from mdm_customer_master where upper(emno) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(ename) like '%" + this.filter_value.trim().toUpperCase() + "%' ");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query = "select emno, ename, cost_center, email_id from mdm_customer_master ";
                System.out.println("emno, ename, costcenter");
            }
        } else if (this.controlName.equalsIgnoreCase("inventoryorgn")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select inv_org, inv_location, gst_no from mdm_thermax_gstn where  upper(inv_org) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + bu + "' or upper(inv_location) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + bu + "'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query
                        = ("select inv_org, inv_location, gst_no from mdm_thermax_gstn where  bu_code = '" + bu + "' ");
                System.out.println("inventory_org, inventory_location");
            }
        } else if (this.controlName.equalsIgnoreCase("location_new")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = (" select distinct location from mdm_segma where location is not null and upper(location) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of location group if--- " + this.query);
            } else {
                this.query = "select distinct location from mdm_segma where location is not null";
                System.out.println("location");
            }
        } else if (this.controlName.equalsIgnoreCase("project_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select bu_code,project_code from mdm_ar_project_code where upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query = "select bu_code,project_code from mdm_ar_project_code ";
                System.out.println("bu_code,project_code");
            }
        } else if (this.controlName.equalsIgnoreCase("fc_project_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select bu, project_code, project_id from mdm_project_wbs where bu = '" + bu + "' and upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of wbscodetaslcode if--- " + this.query);
            } else {
                this.query = ("select bu, project_code, project_id from mdm_project_wbs where bu = '" + bu + "'");
                System.out.println("project_code,project_id ");
            }
        } else if (this.controlName.equalsIgnoreCase("wbscodetaslcode")) {
            String project_code = this.formObject.getNGValue("fc_project_code");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select project_code,wbs_code, wbs_id from mdm_project_wbs where project_code like '" + project_code + "' and upper(wbs_code) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(wbs_id) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of wbscodetaslcode if--- " + this.query);
            } else {
                this.query
                        = ("select project_code,wbs_code, wbs_id from mdm_project_wbs where project_code like '" + project_code + "'");

                System.out.println("project_code,wbs_code ");
            }
        } else if (this.controlName.equalsIgnoreCase("gl_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select gl_code, expenditure_type_gl_code from mdm_segma where  upper(gl_code) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(expenditure_type_gl_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query = "select gl_code, expenditure_type_gl_code from mdm_segma ";
                System.out.println("gl_code, expenditure_type_gl_code from mdm_segma" + this.query);
            }
        } else if (this.controlName.equalsIgnoreCase("tdssection")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select tds_section, tds_tax_category, tax_percent, tds_section_code from mdm_tds_tax where upper(tds_section) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(tds_tax_category) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + bu + "'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query
                        = ("select tds_section, tds_tax_category, tax_percent, tds_section_code from mdm_tds_tax where bu_code = '" + bu + "'");

                System.out.println("TDS Section");
            }
        } else if (this.controlName.equalsIgnoreCase("expend_org")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select expenditure_org from mdm_expenditure_org where upper(expenditure_org) like '%" + this.filter_value.trim().toUpperCase() + "%'  and bu_code = '" + bu + "'");

                System.out.println("query of expenditure_org if--- " + this.query);
            } else {
                this.query = ("select expenditure_org from mdm_expenditure_org where bu_code like '" + bu + "'");
                System.out.println("expenditure_org");
            }
        } else if (this.controlName.equalsIgnoreCase("projectnumber")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select project_code from mdm_project_wbs_code where  upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query = "select project_code from mdm_project_wbs_code ";
                System.out.println("bu_code,project_code");
            }
        } else if (this.controlName.equalsIgnoreCase("expendituretype")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select expenditure_type from mdm_expenditure_type where  upper(expenditure_type) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of expendituretype ***po if--- " + this.query);
            } else {
                this.query = "select expenditure_type from mdm_expenditure_type";
                System.out.println("expenditure_type");
            }
        } else if (this.controlName.equalsIgnoreCase("divisioncode")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select distinct division_code from mdm_segma where legal_entity like '" + entityname + "' order by division_code");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query
                        = ("select distinct division_code from mdm_segma where legal_entity like '" + entityname + "' order by division_code");

                System.out.println("divisioncode");
            }
        } else if (this.controlName.equalsIgnoreCase("po_tdssection")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select distinct division_code from mdm_segma where legal_entity like '" + entityname + "' order by division_code");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query
                        = ("select distinct division_code from mdm_segma where legal_entity like '" + entityname + "' order by division_code");

                System.out.println("po_tdssection");
            }
        } else if (this.controlName.equalsIgnoreCase("buyer_emp_id")) {
            String po_bu = this.formObject.getNGValue("businessunit");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select emno, ename, cost_center, email_id from mdm_customer_master where upper(emno) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(ename) like '%" + this.filter_value.trim().toUpperCase() + "%' and bu_code = '" + po_bu + "'");

                System.out.println("query of tds group if--- " + this.query);
            } else {
                this.query
                        = ("select emno, ename, cost_center, email_id from mdm_customer_master where bu_code = '" + po_bu + "'");

                System.out.println("po_tdssection");
            }
        } else if (this.controlName.equalsIgnoreCase("ca_customer_code")) {
            String divisionCode = this.formObject.getNGValue("ca_division_code");
            System.out.println("pick division code " + divisionCode.trim());
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                System.out.println("queryAR");

                this.query
                        = ("select customer_number, customer_name, customer_site_name from ar_mdm_customer_master where bu_code like '%" + this.formObject.getNGValue("ca_division_code").trim() + "%' and upper(customer_number) like '%" + this.filter_value.trim().toUpperCase() + "%' or bu_code like '%" + this.formObject.getNGValue("ca_division_code").trim() + "%' and upper(customer_name) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("customer query of ar group if---  " + this.query);
            } else {
                System.out.println("queryAR1");
                this.query = "select customer_number,customer_name,customer_site_name from ar_mdm_customer_master";
                System.out.println("query customer details of tds else");
            }
        } else if (this.controlName.equalsIgnoreCase("ca_project_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                System.out.println("queryproject code");
                this.query
                        = ("select distinct(project_code) from mdm_project_wbs_code where upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of ar group if---  " + this.query);
            } else {
                System.out.println("queryAR1");
                this.query = "select distinct(project_code) from mdm_project_wbs_code";
                System.out.println("query project_code details of tds else");
            }
        } else if (this.controlName.equalsIgnoreCase("ca_so_no")) {
            String divisionCode = this.formObject.getNGValue("ca_division_code");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                System.out.println("ca_so_no code");
                this.query
                        = ("select distinct(sales_order_no) from mdm_ar_sales_order_no where bu_code like '%" + this.formObject.getNGValue("ca_division_code").trim() + "%' and upper(sales_order_no) like '%" + this.filter_value.trim().toUpperCase() + "%' or bu_code like '%" + this.formObject.getNGValue("ca_division_code").trim() + "%' and upper(sales_order_no) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of ar group if---  " + this.query);
            } else {
                System.out.println("queryAR1");
                this.query = "select distinct(sales_order_no) from mdm_ar_sales_order_no";
                System.out.println("query project_code details of tds else");
            }
        } else if (this.controlName.equalsIgnoreCase("sales_order_control")) {
            String divisionCode = this.formObject.getNGValue("ca_division_code");
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                System.out.println("sales_order_control code");
                this.query
                        = ("select distinct(sales_order_no) from mdm_ar_sales_order_no where bu_code like '%" + this.formObject.getNGValue("ca_division_code").trim() + "%' and upper(sales_order_no) like '%" + this.filter_value.trim().toUpperCase() + "%' or bu_code like '%" + this.formObject.getNGValue("ca_division_code").trim() + "%' and upper(sales_order_no) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of ar group if---  " + this.query);
            } else {
                System.out.println("queryAR1");
                this.query = "select distinct(sales_order_no) from mdm_ar_sales_order_no";
                System.out.println("query project_code details of tds else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_doc_category")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select division_code, doc_category, org_id from mdm_doc_category where upper(doc_category) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of division code --- " + this.query);
            } else {
                this.query = "select division_code, doc_category, org_id from mdm_mdm_doc_category";
                System.out.println("query of tds else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_project_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select project_code from mdm_project_master where upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_project_code code --- " + this.query);
            } else {
                this.query = "select project_code from mdm_project_master";
                System.out.println("query of cr_project_code else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_customer_code_bil")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select org_id, ename, legal_entity from mdm_customer_master where upper(org_id) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(org_id) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_customer_code_bil code --- " + this.query);
            } else {
                this.query = "select org_id, ename, legal_entity from mdm_customer_master";
                System.out.println("query of cr_customer_code_bil else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_customer_code_shi")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select org_id, ename, legal_entity from mdm_customer_master where upper(org_id) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(org_id) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_customer_code_shi code --- " + this.query);
            } else {
                this.query = "select org_id, ename, legal_entity from mdm_customer_master";
                System.out.println("query of cr_customer_code_shi else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_location")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select description from mdm_location_master where upper(description) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(description) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_location --- " + this.query);
            } else {
                this.query = "select description from mdm_location_master";
                System.out.println("query of cr_location else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_cost_center")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select cost_center from mdm_customer_master where upper(cost_center) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(cost_center) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_cost_center code --- " + this.query);
            } else {
                this.query = "select cost_center from mdm_customer_master";
                System.out.println("query of cr_cost_center else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_gl_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select gl_code from mdm_gl_code where upper(gl_code) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(gl_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_gl_code code --- " + this.query);
            } else {
                this.query = "select gl_code from mdm_gl_code";
                System.out.println("query of cr_gl_code else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_wbs_code")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select project_code from mdm_project_wbs_code where upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(project_code) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_wbs_code code --- " + this.query);
            } else {
                this.query = "select project_code from mdm_project_wbs_code";
                System.out.println("query of cr_wbs_code else");
            }
        }
        if (this.controlName.equalsIgnoreCase("cr_employee")) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select ename, legal_entity, cost_center, org_id from mdm_customer_master where upper(ename) like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(ename) like '%" + this.filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_employee code --- " + this.query);
            } else {
                this.query = "select ename, legal_entity, cost_center, org_id from mdm_customer_master ";
                System.out.println("query of cr_employee else");
            }
        }
        if (this.controlName.equalsIgnoreCase(control.trim())) {
            if ((!this.filter_value.equalsIgnoreCase("")) && (!this.filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = (qry1 + " (upper(" + search.trim() + ") like '%" + this.filter_value.trim().toUpperCase() + "%' or upper(" + search2.trim() + ") like '%" + this.filter_value.trim().toUpperCase() + "%' ) " + orderBy);

                System.out.println("Sourabh debit Note query " + this.query);
            } else {
                this.query = qry.trim();

                System.out.println(" Sourabh - Debit Note ");
            }
        }
        btnSearch_Clicked_For_Credit_Note(ae, this.controlName, this.filter_value);

        System.out.println("outside if " + this.query);
        this.objPicklist.setBatchRequired(true);
        System.out.println("inside true");
        this.objPicklist.setBatchSize(10);
        System.out.println("batch size");
        this.objPicklist.populateData(this.query);
        System.out.println("kp query =" + this.query);
        this.objPicklist.setVisible(true);
    }

    public void openPickList(String ControlName, String Columns, String WindowTitle, int Width, int Height, String PopulateDataQuery) {
        this.formObject = FormContext.getCurrentInstance().getFormReference();
        this.objPicklist = this.formObject.getNGPickList(ControlName, Columns, true, 50);
        this.objPicklist.setWindowTitle(WindowTitle);
        this.objPicklist.setWidth(Integer.valueOf(Width));
        this.objPicklist.setHeight(Integer.valueOf(Height));
        this.objPicklist.setPicklistHeaderBGColor(new Color(204, 102, 0));
        this.objPicklist.setPicklistHeaderFGColor(Color.WHITE);
        this.objPicklist.setColumnHeaderBackColor(new Color(33, 116, 115));
        this.objPicklist.setColumnHeaderForeColor(Color.WHITE);
        this.objPicklist.setButtonFontStyle("Arial", 12, 0, Character.lowSurrogate(1));
        this.objPicklist.setFontStyle("Arial", 10, 2, Character.lowSurrogate(1));
        this.objPicklist.addPickListListener(new PicklistListenerHandler_thrmx(this.objPicklist.getClientId()));
        this.objPicklist.populateData(PopulateDataQuery);
        this.objPicklist.setVisible(true);
    }

    private void btn_Ok_Clicked_For_Credit_Note(ActionEvent ae, PickList m_objPickList, String controlName) {
        this.formObject = FormContext.getCurrentInstance().getFormReference();
        this.formConfig = FormContext.getCurrentInstance().getFormConfig();
/*        if (controlName.equalsIgnoreCase("ap_supplier_code")) {
            System.out.println("ap_supplier_code 2 :" + (String) m_objPickList.getSelectedValue().get(0));

            this.formObject.clear("ap_supplier_code");
            this.formObject.clear("ap_supplier_site");
            this.formObject.setNGValue("ap_supplier_code", m_objPickList.getSelectedValue().get(0));
            this.formObject.setNGValue("ap_supplier_site", m_objPickList.getSelectedValue().get(2));
        } else if (controlName.equalsIgnoreCase("ap_project_code")) {
            System.out.println("ap_project_code 2:" + (String) m_objPickList.getSelectedValue().get(0));

            this.formObject.clear("ap_project_code");
            this.formObject.setNGValue("ap_project_code", m_objPickList.getSelectedValue().get(0));
        } else if (controlName.equalsIgnoreCase("ap_location")) {
            System.out.println("ap_location 2: " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("ap_location");

            this.formObject.setNGValue("ap_location", m_objPickList.getSelectedValue().get(0));

            System.out.println("ap_location : " + (String) m_objPickList.getSelectedValue().get(0));
        } else if (controlName.equalsIgnoreCase("ap_gl_code")) {
            System.out.println("ap_gl_code 2: " + (String) m_objPickList.getSelectedValue().get(0));
            this.formObject.clear("ap_gl_code");

            this.formObject.setNGValue("ap_gl_code", m_objPickList.getSelectedValue().get(0));
        }*/
    }

    private void btnSearch_Clicked_For_Credit_Note(ActionEvent ae, String controlName, String filter_value) {
        this.formObject = FormContext.getCurrentInstance().getFormReference();
        this.formConfig = FormContext.getCurrentInstance().getFormConfig();
/*        if (controlName.equalsIgnoreCase("cr_employee")) {
            if ((!filter_value.equalsIgnoreCase("")) && (!filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select ename, legal_entity, cost_center, org_id from mdm_customer_master where upper(ename) like '%" + filter_value.trim().toUpperCase() + "%' or upper(ename) like '%" + filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of cr_employee code --- " + this.query);
            } else {
                this.query = "select ename, legal_entity, cost_center, org_id from mdm_customer_master ";
                System.out.println("query of cr_employee else");
            }
        } else if (controlName.equalsIgnoreCase("ap_supplier_code")) {
            if ((!filter_value.equalsIgnoreCase("")) && (!filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select emno, ename,org_id from mdm_customer_master where upper(emno) like '%" + filter_value.trim().toUpperCase() + "%' or upper(emno) like '%" + filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of ap_supplier_code code --- " + this.query);
            } else {
                this.query = "select emno, ename,org_id from mdm_customer_master";
                System.out.println("query of ap_supplier_code else");
            }
        }
        if (controlName.equalsIgnoreCase("ap_project_code")) {
            if ((!filter_value.equalsIgnoreCase("")) && (!filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select project_code from mdm_project_wbs_code where upper(project_code) like '%" + filter_value.trim().toUpperCase() + "%' or upper(project_code) like '%" + filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of ap_project_code code --- " + this.query);
            } else {
                this.query = "select project_code from mdm_project_wbs_code";
                System.out.println("query of ap_project_code else");
            }
        } else if (controlName.equalsIgnoreCase("ap_buyer_code")) {
            if ((!filter_value.equalsIgnoreCase("")) && (!filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select emno, ename, cost_center, email_id from mdm_customer_master where upper(emno) like '%" + filter_value.trim().toUpperCase() + "%' or upper(emno) like '%" + filter_value.trim().toUpperCase() + "%'");

                System.out.println("query of ap_buyer_code code --- " + this.query);
            } else {
                this.query = "select emno, ename, cost_center, email_id from mdm_customer_master";
                System.out.println("query of ap_buyer_code else");
            }
        } else if (controlName.equalsIgnoreCase("ap_gl_code")) {
            if ((!filter_value.equalsIgnoreCase("")) && (!filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select gl_code from mdm_gl_code where upper(gl_code) like '%" + filter_value.trim().toUpperCase() + "%' or upper(gl_code) like '%" + filter_value.trim().toUpperCase() + "%'");
                System.out.println("query of cr_gl_code code --- " + this.query);
            } else {
                this.query = "select gl_code from mdm_gl_code";
                System.out.println("query of cr_gl_code else");
            }
        } else if (controlName.equalsIgnoreCase("ap_project_code")) {
            if ((!filter_value.equalsIgnoreCase("")) && (!filter_value.equalsIgnoreCase("*"))) {
                this.query
                        = ("select project_code from mdm_project_master where upper(project_code) like '%" + filter_value.trim().toUpperCase() + "%' or upper(project_code) like '%" + filter_value.trim().toUpperCase() + "%'");
                System.out.println("query of ap_project_code code --- " + this.query);
            } else {
                this.query = "select project_code from mdm_project_master";
                System.out.println("query of ap_project_code else");
            }
        }*/
    }
}
