/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.newgen.leaveDemo;

import com.newgen.common.General;
import com.newgen.omniforms.FormConfig;
import com.newgen.omniforms.FormReference;
import com.newgen.omniforms.context.FormContext;
import com.newgen.omniforms.event.ComponentEvent;
import com.newgen.omniforms.event.FormEvent;
import com.newgen.omniforms.listener.FormListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author KuldeepPal
 */
public class Head implements FormListener {
FormReference formObject = null;
	FormConfig formConfig = null;
	String userId = null;
	String userName = null;
	int weekdays = 0;
	String from = null;
	String to = null;
	static SimpleDateFormat sdf = null;
        General objGeneral = null;
    @Override
    public void formLoaded(FormEvent fe) {
       formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
    }

    @Override
    public void formPopulated(FormEvent fe) {
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
        // TODO Auto-generated method stub     
        objGeneral = new General();
        System.out.println("----------------------Intiation Workstep Loaded from form populated.---------------------------");
        //-----------------------------------------------------------------------------------------------------
    }

    
     @Override
    public void eventDispatched(ComponentEvent pEvent) throws ValidatorException {
         // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
     if (pEvent.getType().name().equalsIgnoreCase("VALUE_CHANGED")) {
			if (pEvent.getSource().getName().equalsIgnoreCase("leave_from")
					|| pEvent.getSource().getName().equalsIgnoreCase("leave_to")) {
				System.out.println("leave to" + formObject.getNGValue("leave_to"));
				from = formObject.getNGValue("leave_from");
				to = formObject.getNGValue("leave_to");
				
				if(from !=null && to !=null) {
					weekdays = calculateDays(from, to);
					formObject.setNGValue("duration", weekdays);
					}
				
			}
			
		} //formObject.RaiseEvent("WFSave");
    }
    
    
    @Override
    public void saveFormStarted(FormEvent fe) throws ValidatorException {
        // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
    }

    @Override
    public void saveFormCompleted(FormEvent fe) throws ValidatorException {
       // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
    }

    @Override
    public void submitFormStarted(FormEvent fe) throws ValidatorException {
        // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
    }

    @Override
    public void submitFormCompleted(FormEvent fe) throws ValidatorException {
       // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
    }

   

    @Override
    public void continueExecution(String string, HashMap<String, String> hm) {
       // TODO Auto-generated method stub
        formObject = FormContext.getCurrentInstance().getFormReference();
        formConfig = FormContext.getCurrentInstance().getFormConfig();
    }

    @Override
    public void initialize() {
         // TODO Auto-generated method stub
    }

    @Override
    public String encrypt(String string) {
       // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String decrypt(String string) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    public static int calculateDays(String startdate, String enddate) {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		int workingDays = 0;
		try {
			                 Calendar start = Calendar.getInstance();
			start.setTime(sdf.parse(startdate));
			Calendar end = Calendar.getInstance();
			end.setTime(sdf.parse(enddate));

			while (!start.after(end)) {
				int day = start.get(Calendar.DAY_OF_WEEK);
				if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY))
					workingDays++;
				start.add(Calendar.DATE, 1);
			}
			System.out.println(workingDays);
			return workingDays;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

    
}
