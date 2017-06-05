/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.businessman;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.controllers.BaseController;
import es.uvlive.controllers.BaseResponse;

/**
 *
 * @author alextfos
 */
@Controller
public class BusinessmanManagementController extends BaseController {

	@RequestMapping(value = "/businessman/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse validateUserName(@RequestBody ValidateBusinessmanForm validateBusinessmanForm,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValidateBusinessmanResponse validateBusinessmanResponse = new ValidateBusinessmanResponse();
		try {
			validateBusinessmanResponse
					.setStatus(uvLiveModel.checkUserExists(getToken(request), validateBusinessmanForm.getUserName()));
		} catch (Exception e) {
			validateBusinessmanResponse.setErrorCode(getErrorCode(e));
		}
		return validateBusinessmanResponse;
	}

	@RequestMapping(value = "/businessman/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse registerBusinessman(@RequestBody SetBusinessmanForm businessmanForm,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SetBusinessmanResponse setBusinesmanResponse = new SetBusinessmanResponse();
		try {
			uvLiveModel.registerBusinessman(getToken(request), businessmanForm.getDni(), businessmanForm.getFirstName(),
				businessmanForm.getLastName(), businessmanForm.getUserName(), businessmanForm.getPassword());
		} catch (Exception e) {
			setBusinesmanResponse.setErrorCode(getErrorCode(e));
		}
		return setBusinesmanResponse;
	}

	@RequestMapping(value = "/businessman/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse updateBusinessman(@RequestBody SetBusinessmanForm businessmanForm,
			BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SetBusinessmanResponse setBusinessmanResponse = new SetBusinessmanResponse();
		try {
			uvLiveModel.updateBusinessman(getToken(request), businessmanForm.getDni(), businessmanForm.getFirstName(),
					businessmanForm.getLastName(), businessmanForm.getUserName(), businessmanForm.getPassword());
		} catch (Exception e) {
			setBusinessmanResponse.setErrorCode(getErrorCode(e));
		}
		return setBusinessmanResponse;
	}
	
	// TODO @Non-generated
	@RequestMapping(value = "/broadcast/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
	"Content-Type=application/json" })
	public @ResponseBody BaseResponse registerBroadcast(@RequestBody BroadcastForm broadcastForm,
		BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			String token = request.getHeader("Authorization");
			uvLiveModel.registerBroadcast(token, broadcastForm.getBroadcastMessage());
		} catch (Exception e) {
			new BaseResponse().setErrorCode(getErrorCode(e));
		}
		return new BaseResponse();
	}
}
