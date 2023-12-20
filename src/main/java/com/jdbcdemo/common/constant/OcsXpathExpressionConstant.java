package com.jdbcdemo.common.constant;

public abstract class OcsXpathExpressionConstant {
    public static final String RESULT  = "//TaskResponse/Result";
    public static final String ERROR_CODE  = "//ErrorInfo/ErrorCode";
    public static final String ERROR_MSG  = "//ErrorInfo/ErrorMsg";
    public static final String CLASS_OF_SERVICE_ID  = "//ParamList/Param[Name/text()='Class of Service ID']/Value/text()";
    public static final String LANGUAGE  = "//ParamList/Param[Name/text()='Language Label']/Value/text()";
    public static final String QUERY_ACCOUNT_PARAM_LIST = "//ParamList/Param[Name/text()='Language Label']/Value/text()";
    public static final String PARAM_LIST = "//ParamList";
}
