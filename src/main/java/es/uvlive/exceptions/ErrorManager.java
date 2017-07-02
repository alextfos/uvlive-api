package es.uvlive.exceptions;


import es.uvlive.utils.Logger;
import org.springframework.util.StringUtils;

public class ErrorManager {

    public static final int UNKNOWN_ERROR_EXCEPTION = 1;
    public static final int OK_CODE = 0;
    public static final int WRONG_CREDENTIALS_EXCEPTION = -1;
    public static final int UNAUTHORIZED_EXCEPTION = -2;
    public static final int TOKEN_EXPIRED_EXCEPTION = -3;
    public static final int USER_DEFINED_EXCEPTION = -4;
    public static final int USER_NOT_DEFINED_EXCEPTION = -5;
    public static final int VALIDATION_FORM_EXCEPTION = -6;
    public static final int CONVERSATION_NOT_CREATED_EXCEPTION = -7;
    public static final int USER_BLOCKED_EXCEPTION = -8;

    public static int getErrorCode(Class clazz, Exception e) {
        System.err.println("Generic error treatment: "+e.getMessage());

        Logger.putError(clazz,e);
        e.printStackTrace();
        int code = UNKNOWN_ERROR_EXCEPTION;
        if (!StringUtils.isEmpty(e.getMessage())) {
            switch (e.getMessage()) {
                case UnauthorizedException.MESSAGE:
                    code = UNAUTHORIZED_EXCEPTION;
                    break;
                case WrongCredentialsException.MESSAGE:
                    code = WRONG_CREDENTIALS_EXCEPTION;
                    break;
                case TokenExpiredException.MESSAGE:
                    code = TOKEN_EXPIRED_EXCEPTION;
                    break;
                case UserDefinedException.MESSAGE:
                    code = USER_DEFINED_EXCEPTION;
                    break;
                case UserNotDefinedException.MESSAGE:
                    code = USER_NOT_DEFINED_EXCEPTION;
                    break;
                case ValidationFormException.MESSAGE:
                    code = VALIDATION_FORM_EXCEPTION;
                    break;
                case ConversationNotCreatedException.MESSAGE:
                    code = CONVERSATION_NOT_CREATED_EXCEPTION;
                    break;
                case UserBlockedException.MESSAGE:
                    code = USER_BLOCKED_EXCEPTION;
                    break;
            }
        }
        return code;
    }
}
