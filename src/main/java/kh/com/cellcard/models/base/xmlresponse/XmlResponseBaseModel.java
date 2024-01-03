package kh.com.cellcard.models.base.xmlresponse;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public abstract class XmlResponseBaseModel {

    protected DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    protected DocumentBuilder builder;
    {
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    protected Document responseDoc;
    protected XPath xPath = XPathFactory.newInstance().newXPath();

    protected String getXmlValue(String expression) {
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
}
