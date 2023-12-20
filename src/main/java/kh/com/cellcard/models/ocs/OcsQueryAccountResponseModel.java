package kh.com.cellcard.models.ocs;

import kh.com.cellcard.common.constant.OcsXpathExpressionConstant;
import kh.com.cellcard.models.ocs.base.OcsResponseBaseModel;

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