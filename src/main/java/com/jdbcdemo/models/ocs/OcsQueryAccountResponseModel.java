package com.jdbcdemo.models.ocs;

import com.jdbcdemo.common.constant.OcsXpathExpressionConstant;
import com.jdbcdemo.models.ocs.base.OcsResponseBaseModel;

import javax.xml.parsers.ParserConfigurationException;

public class OcsQueryAccountResponseModel extends OcsResponseBaseModel {

    public final String language;
    public final String classOfServiceId;

    public OcsQueryAccountResponseModel(String response) throws ParserConfigurationException {
        super(response);
        this.language = super.getXmlValue(OcsXpathExpressionConstant.LANGUAGE);
        this.classOfServiceId = super.getXmlValue(OcsXpathExpressionConstant.CLASS_OF_SERVICE_ID);
    }
}