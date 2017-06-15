package es.uvlive.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.controllers.form.SetBusinessmanForm;
import es.uvlive.controllers.form.ValidateBusinessmanForm;
import es.uvlive.controllers.response.BaseResponse;
import es.uvlive.controllers.response.LogListResponse;
import es.uvlive.controllers.response.LogResponse;
import es.uvlive.controllers.response.SetBusinessmanResponse;
import es.uvlive.controllers.response.ValidateBusinessmanResponse;
import es.uvlive.utils.LogRegister;
import es.uvlive.utils.Logger;

@Controller
public class AdminController extends BaseController {
	
	/**
    *
    * @param loginForm
    * @param result
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/admin/logs", method = RequestMethod.POST)
   public @ResponseBody LogListResponse logger(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
       ArrayList<LogRegister> logs = Logger.getLogs();
       LogListResponse logListResponse = new LogListResponse();
       ArrayList<LogResponse> logArrayList = new ArrayList();
       
       for (LogRegister log: logs) {
           LogResponse logResponse = new LogResponse();
           logResponse.setTimeStamp(log.getTimeStamp());
           logResponse.setLevel(log.getLevel());
           logResponse.setClazz(log.getClazz());
           logResponse.setMessage(log.getMessage());
           logArrayList.add(logResponse);
       }
       logListResponse.setLogs(logArrayList);
       return logListResponse;
   }
   
	   @RequestMapping(value = "/admin/merchantName/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
		"Content-Type=application/json" })
	public @ResponseBody BaseResponse validateUserName(@RequestBody ValidateBusinessmanForm validateBusinessmanForm,
		BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
	ValidateBusinessmanResponse validateBusinessmanResponse = new ValidateBusinessmanResponse();
	try {
		if (validateBusinessmanForm.isValid()) {
			validateBusinessmanResponse
					.setStatus(uvLiveModel.checkUserExists(getToken(request), validateBusinessmanForm.getUserName()));
		}
	} catch (Exception e) {
		validateBusinessmanResponse.setErrorCode(getErrorCode(e));
	}
	return validateBusinessmanResponse;
	}
	
	@RequestMapping(value = "/admin/merchant/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
		"Content-Type=application/json" })
	public @ResponseBody BaseResponse registerBusinessman(@RequestBody SetBusinessmanForm businessmanForm,
		BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
	SetBusinessmanResponse setBusinesmanResponse = new SetBusinessmanResponse();
	try {
		if (businessmanForm.isValid()) {
			uvLiveModel.registerBusinessman(getToken(request), businessmanForm.getDni(), businessmanForm.getFirstName(),
			businessmanForm.getLastName(), businessmanForm.getUserName(), businessmanForm.getPassword());
		}
	} catch (Exception e) {
		setBusinesmanResponse.setErrorCode(getErrorCode(e));
	}
	return setBusinesmanResponse;
	}
	
	@RequestMapping(value = "/admin/merchant/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
		"Content-Type=application/json" })
	public @ResponseBody BaseResponse updateBusinessman(@RequestBody SetBusinessmanForm businessmanForm,
		BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
	SetBusinessmanResponse setBusinessmanResponse = new SetBusinessmanResponse();
	try {
		if (businessmanForm.isValid()) {
			uvLiveModel.updateBusinessman(getToken(request), businessmanForm.getDni(), businessmanForm.getFirstName(),
			businessmanForm.getLastName(), businessmanForm.getUserName(), businessmanForm.getPassword());
		}
	} catch (Exception e) {
		setBusinessmanResponse.setErrorCode(getErrorCode(e));
	}
	return setBusinessmanResponse;
	}
	
	
}
