package kh.com.cellcard.common.helper;

public abstract class RequestParameterHelper {



    public static String formatAccountId(String accountId) {
        final String FALSE_STRING = "FALSE";
        try {
            String accountIdFormatted;

            if(accountId.startsWith("+")){
                accountId = accountId.substring(1);
            }

            if(!isNumber(accountId)){
                return FALSE_STRING;
            }
            var length = accountId.length();
            if(accountId.startsWith("0")){
                if(length == 9 || length == 10){
                    accountIdFormatted="855"+accountId.substring(1);
                }else{
                    accountIdFormatted= FALSE_STRING;
                }
            } else if(accountId.startsWith("855")){
                if( length==11 || length == 12){
                    accountIdFormatted=accountId;
                } else if( length==8 || length == 9){
                    accountIdFormatted="855"+accountId;
                } else {
                    accountIdFormatted= FALSE_STRING;
                }
            } else {
                if( length==8 || length == 9) {
                    accountIdFormatted="855"+accountId;
                } else{
                    accountIdFormatted = FALSE_STRING;
                }
            }
            return accountIdFormatted;
        } catch (Exception ex) {
            return FALSE_STRING;
        }
    }

    public static boolean isNumber(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
