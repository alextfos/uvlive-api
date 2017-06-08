package es.uvlive.controllers.permissions;

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
 * Class for managing user permissions
 */
@Controller
public class PermissionsController extends BaseController {
	
    @RequestMapping(value = "/permission/block", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse blockStudent(@RequestBody PermissionStudentForm permissionStudentForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	BaseResponse baseResponse = new BaseResponse();
    	try {
	    	// @Non-generated
	    	String token = getToken(request);
	    	uvLiveModel.blockStudent(token, permissionStudentForm.getIdStudent());
    	} catch (Exception e) {
    		baseResponse.setErrorCode(getErrorCode(e));
    	}
    	
        return baseResponse;
    }
    
    @RequestMapping(value = "/permission/unblock", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers={"Content-Type=application/json"})
    public @ResponseBody
    BaseResponse unblockStudent(@RequestBody PermissionStudentForm permissionStudentForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	BaseResponse baseResponse = new BaseResponse();
    	try {
	    	// @Non-generated
	    	String token = getToken(request);
	    	uvLiveModel.unblockStudent(token, permissionStudentForm.getIdStudent());
    	} catch (Exception e) {
    		baseResponse.setErrorCode(getErrorCode(e));
    	}
    	
        return baseResponse;
    }
}
