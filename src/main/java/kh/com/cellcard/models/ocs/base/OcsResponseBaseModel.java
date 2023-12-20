package kh.com.cellcard.models.ocs.base;

import kh.com.cellcard.common.constant.OcsXpathExpressionConstant;
import kh.com.cellcard.common.enums.ocs.OcsResponseParam;
import kh.com.cellcard.common.helper.StringHelper;
import lombok.Getter;
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
import java.util.Optional;

public class OcsResponseBaseModel {

    protected DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    protected DocumentBuilder builder = factory.newDocumentBuilder();
    protected Document ocsResponseDoc;
    protected XPath xPath = XPathFactory.newInstance().newXPath();

    @Getter
    protected final String result;

    @Getter
    protected final String errorCode;

    @Getter
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

    protected Optional<NodeList> getNodeLIst(String expression) {
        if (this.ocsResponseDoc == null) {
            throw new RuntimeException("parsing xml response to xml document error, ocsResponseDoc is null");
        }
        try {
            XPathExpression expr = xPath.compile(expression);
            Object result = expr.evaluate(this.ocsResponseDoc, XPathConstants.NODESET);
            return Optional.of((NodeList) result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}