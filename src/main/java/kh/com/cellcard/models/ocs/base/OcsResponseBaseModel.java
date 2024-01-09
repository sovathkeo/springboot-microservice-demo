package kh.com.cellcard.models.ocs.base;

import kh.com.cellcard.common.constant.OcsXpathExpressionConstant;
import kh.com.cellcard.common.enums.ocs.OcsResponseParam;
import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.models.base.xmlresponse.XmlResponseBaseModel;
import lombok.Getter;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

@Getter
public class OcsResponseBaseModel extends XmlResponseBaseModel {

    protected final String result;

    protected final String errorCode;

    protected final String errorMsg;


    public OcsResponseBaseModel(String response) throws ParserConfigurationException {
        try {
            if (StringHelper.isNullOrEmpty(response)) {
                throw new RuntimeException("OCS response xml string can not be null or empty");
            }
            responseDoc = builder.parse(new InputSource(new StringReader(response)));
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

    public String getXmlValue(String expression) {
        if (this.responseDoc == null) {
            throw new RuntimeException("parsing xml response to xml document error, ocsResponseDoc is null");
        }
        try {
            XPathExpression expr = xPath.compile(expression);
            Object result = expr.evaluate(this.responseDoc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            return nodes.item(0).getTextContent();
        } catch (Exception e) {
            return "null";
        }
    }

    protected Optional<NodeList> getNodeLIst(String expression) {
        if (this.responseDoc == null) {
            throw new RuntimeException("parsing xml response to xml document error, ocsResponseDoc is null");
        }
        try {
            XPathExpression expr = xPath.compile(expression);
            Object result = expr.evaluate(this.responseDoc, XPathConstants.NODESET);
            return Optional.of((NodeList) result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}