package com.jdbcdemo.common.logging;

import com.jdbcdemo.common.helper.logging.ApplicationLog;
import com.jdbcdemo.services.shareservice.ShareServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLogging implements ApplicationLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ShareServiceImpl shareService;

    protected ApplicationLog applicationLog;

    public void initApplicationLogging() {
        if (shareService == null) {
            return;
        }
        var appLog = shareService.getObject(ApplicationLog.class);
        if(appLog.isEmpty()){
            logger.error("getting share object[ApplicationLog] from http context null");
            return ;
        }
        applicationLog = (ApplicationLog) appLog.get();
    }

    private void ensureApplicationLogInitialize() {
        if (this.applicationLog != null) {
            return;
        }
        initApplicationLogging();
    }

    public void logInfo(){
        logger.info(this.applicationLog.getLogMessage());
    }

    public void logDebug(Exception e){
        logger.debug(this.applicationLog.getLogMessage(), e);
    }

    public void logError(Exception e){
        logger.error(this.applicationLog.getLogMessage(), e);
    }

    @Override
    public void setLogParams( String action, String step, String nei, String result, String api, String errorCode, String errorMessage ) {
        this.ensureApplicationLogInitialize();
        this.applicationLog.setLogParams(action, step, nei, result, api, errorCode, errorMessage);
    }

    @Override
    public void setRequestLogParams( String step, String nei, String api ) {
        this.ensureApplicationLogInitialize();
        this.applicationLog.setRequestLogParams(step, nei, api);
    }

    @Override
    public void setResponseLogParams(String step, String nei, String api, String result, String errorCode, String errorMessage ) {
        this.ensureApplicationLogInitialize();
        this.applicationLog.setResponseLogParams(step, nei, api, result, errorCode, errorMessage);
    }

    public void setLastResponseLogParams(String step, String result, String errorCode, String errorMessage) {
        this.ensureApplicationLogInitialize();
        this.applicationLog.setLastResponseLogParams(step, this.applicationLog.nei, this.applicationLog.api, result, errorCode, errorMessage);
    }

    public void setUnexpectedErrorLogParams(String result, String errorCode, String errorMessage) {
        this.ensureApplicationLogInitialize();
        this.applicationLog.setLastResponseLogParams(this.applicationLog.step, this.applicationLog.nei, this.applicationLog.api, result, errorCode, errorMessage);
    }
}