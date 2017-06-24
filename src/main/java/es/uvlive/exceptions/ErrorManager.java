package es.uvlive.exceptions;


import es.uvlive.utils.Logger;
import org.springframework.util.StringUtils;

public class ErrorManager {

    public static final int UNKNOWN_ERROR_CODE = 1;
    public static final int OK_CODE = 0;
    public static final int WRONG_CREDENTIALS_CODE = -1;
    public static final int UNAUTHORIZED_CODE = -2;
    public static final int TOKEN_EXPIRED = -3;
    public static final int USER_DEFINED = -4;
    public static final int USER_NOT_DEFINED = -5;
    public static final int VALIDATION_FORM_EXCEPTION = -6;
    public static final int CONVERSATION_NOT_CREATED_EXCEPTION = -7;

    public static int getErrorCode(Class clazz, Exception e) {
        System.err.println("Generic error treatment: "+e.getMessage());

        Logger.putError(clazz,e);
        e.printStackTrace();
        int code = UNKNOWN_ERROR_CODE;
        if (!StringUtils.isEmpty(e.getMessage())) {
            switch (e.getMessage()) {
                case UnauthorizedException.MESSAGE:
                    code = UNAUTHORIZED_CODE;
                    break;
                case WrongCredentialsException.MESSAGE:
                    code = WRONG_CREDENTIALS_CODE;
                    break;
                case TokenExpiredException.MESSAGE:
                    code = TOKEN_EXPIRED;
                    break;
                case UserDefinedException.MESSAGE:
                    code = USER_DEFINED;
                    break;
                case UserNotDefinedException.MESSAGE:
                    code = USER_NOT_DEFINED;
                    break;
                case ValidationFormException.MESSAGE:
                    code = VALIDATION_FORM_EXCEPTION;
                    break;
                case ConversationNotCreatedException.MESSAGE:
                    code = CONVERSATION_NOT_CREATED_EXCEPTION;
            }
        }
        return code;
    }
}
