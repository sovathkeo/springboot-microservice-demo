package com.jdbcdemo.models.ocs.base;

import com.jdbcdemo.common.constant.OcsXpathExpressionConstant;
import com.jdbcdemo.common.enums.ocs.OcsResponseParam;
import com.jdbcdemo.common.helper.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

public class OcsResponseBaseModel {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document ocsResponseDoc;
    XPath xPath = XPathFactory.newInstance().newXPath();
    protected final String result;
    protected final String errorCode;
    protected final String errorMsg;

    protected OcsResponseBaseModel(String response) throws ParserConfigurationException {
        try {
            if (StringHelper.isNullOrEmpty(response)) {
                throw new RuntimeException("OCS response xml string can not be null or empty");
            }
            ocsResponseDoc = builder.parse(new InputSource(new StringReader(response)));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        this.result = getXmlValue(OcsXpathExpressionConstant.RESULT);
        this.errorCode = getXmlValue(OcsXpathExpressionConstant.ERROR_CODE);
        this.errorMsg = getXmlValue(OcsXpathExpressionConstant.ERROR_MSG);
    }

    public boolean isResultSuccess() {
        return this.result.equalsIgnoreCase(OcsResponseParam.SUCCESS_RESULT.getKey());
    }


    public String getResult() {
        return result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    protected String getXmlValue(String expression) {
        if (this.ocsResponseDoc == null) {
            throw new RuntimeException("parsing xml response to xml document error, ocsResponseDoc is null");
        }
        try {
            XPathExpression expr = xPath.compile(expression);
            Object result = expr.evaluate(this.ocsResponseDoc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            return nodes.item(0).getTextContent();
        } catch (Exception e) {
            return "null";
        }
    }
}