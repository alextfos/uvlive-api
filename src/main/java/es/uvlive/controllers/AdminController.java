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

import es.uvlive.controllers.form.SetMerchantForm;
import es.uvlive.controllers.form.ValidateMerchantForm;
import es.uvlive.controllers.response.BaseResponse;
import es.uvlive.controllers.response.LogListResponse;
import es.uvlive.controllers.response.LogResponse;
import es.uvlive.controllers.response.SetMerchantResponse;
import es.uvlive.controllers.response.ValidateMerchantNameResponse;
import es.uvlive.exceptions.ValidationFormException;
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
	public @ResponseBody LogListResponse logger(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArrayList<LogRegister> logs = Logger.getLogs();
		LogListResponse logListResponse = new LogListResponse();
		ArrayList<LogResponse> logArrayList = new ArrayList();

		for (LogRegister log : logs) {
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

	@RequestMapping(value = "/admin/merchantName/exists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse validateUserName(@RequestBody ValidateMerchantForm validateMerchantForm,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValidateMerchantNameResponse validateMerchantNameResponse = new ValidateMerchantNameResponse();
		try {
			if (validateMerchantForm.isValid()) {
				validateMerchantNameResponse.setStatus(
						uvLiveModel.checkUserExists(getToken(request), validateMerchantForm.getUserName()));
			} else {
				throw new ValidationFormException();
			}
		} catch (Exception e) {
			validateMerchantNameResponse.setErrorCode(getErrorCode(e));
		}
		return validateMerchantNameResponse;
	}

	@RequestMapping(value = "/admin/merchant/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse registerMerchant(@RequestBody SetMerchantForm merchantForm,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SetMerchantResponse setMerchantResponse = new SetMerchantResponse();
		try {
			if (merchantForm.isValid()) {
				uvLiveModel.registerMerchant(getToken(request), merchantForm.getDni(),
				merchantForm.getFirstName(), merchantForm.getLastName(), merchantForm.getUserName(),
				merchantForm.getPassword());
			} else {
				throw new ValidationFormException();
			}
		} catch (Exception e) {
			setMerchantResponse.setErrorCode(getErrorCode(e));
		}
		return setMerchantResponse;
	}

	@RequestMapping(value = "/admin/merchant/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse updateMerchant(@RequestBody SetMerchantForm merchantForm,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SetMerchantResponse setMerchantResponse = new SetMerchantResponse();
		try {
			if (merchantForm.isValid()) {
				uvLiveModel.updateMerchant(getToken(request), merchantForm.getDni(),
				merchantForm.getFirstName(), merchantForm.getLastName(), merchantForm.getUserName(),
				merchantForm.getPassword());
			} else {
				throw new ValidationFormException();
			}
		} catch (Exception e) {
			setMerchantResponse.setErrorCode(getErrorCode(e));
		}
		return setMerchantResponse;
	}
}
