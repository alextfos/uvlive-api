package es.uvlive.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.uvlive.controllers.form.BroadcastForm;
import es.uvlive.controllers.response.BaseResponse;
import es.uvlive.exceptions.ValidationFormException;

@Controller
public class MerchantController extends BaseController {

	@RequestMapping(value = "/merchant/broadcast/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = {
			"Content-Type=application/json" })
	public @ResponseBody BaseResponse registerBroadcast(@RequestBody BroadcastForm broadcastForm, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			uvLiveModel.registerBroadcast(getToken(request), broadcastForm.getBroadcastMessage());
			if (broadcastForm.isValid()) {
				String token = getToken(request);
				uvLiveModel.registerBroadcast(token, broadcastForm.getBroadcastMessage());
			} else {
				throw new ValidationFormException();
			}
		} catch (Exception e) {
			baseResponse.setErrorCode(getErrorCode(e));
		}
		return baseResponse;
	}
}
