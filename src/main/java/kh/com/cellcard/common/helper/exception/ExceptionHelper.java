package kh.com.cellcard.common.helper.exception;

public class ExceptionHelper {

    public static boolean isExceptionContain(Exception e, Class<?> targetClass) {
        Throwable tr = e;
        while (tr != null) {
            if (tr.getClass() == targetClass){
                return true;
            }
            tr = tr.getCause();
        }
        return false;
    }
}
