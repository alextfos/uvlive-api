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

import es.uvlive.controllers.form.PermissionStudentForm;
import es.uvlive.controllers.response.BaseResponse;

@Controller
public class TeacherController extends BaseController {
	@RequestMapping(value = "/teacher/student/block", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse blockStudent(@RequestBody PermissionStudentForm permissionStudentForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	BaseResponse baseResponse = new BaseResponse();
    	try {
    		if (permissionStudentForm.isValid()) {
		    	String token = getToken(request);
		    	uvLiveModel.blockStudent(token, permissionStudentForm.getIdStudent());
    		}
    	} catch (Exception e) {
    		baseResponse.setErrorCode(getErrorCode(e));
    	}
    	
        return baseResponse;
    }
    
    @RequestMapping(value = "/teacher/student/unblock", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse unblockStudent(@RequestBody PermissionStudentForm permissionStudentForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	BaseResponse baseResponse = new BaseResponse();
    	try {
    		if (permissionStudentForm.isValid()) {
		    	String token = getToken(request);
		    	uvLiveModel.unblockStudent(token, permissionStudentForm.getIdStudent());
    		}
    	} catch (Exception e) {
    		baseResponse.setErrorCode(getErrorCode(e));
    	}
    	
        return baseResponse;
    }
}
