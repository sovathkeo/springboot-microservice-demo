package kh.com.cellcard.models.hlr.base;

import kh.com.cellcard.common.constant.HlrXpathExpressionConstant;
import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.models.base.xmlresponse.XmlResponseBaseModel;
import lombok.Getter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

@Getter
public class HlrResponseBaseModel extends XmlResponseBaseModel {

    protected final String resultCode;
    protected final String resultDescription;

    public HlrResponseBaseModel(String xmlResponse) {
        try {
            if (StringHelper.isNullOrEmpty(xmlResponse)) {
                throw new RuntimeException("OCS response xml string can not be null or empty");
            }
            responseDoc = builder.parse(new InputSource(new StringReader(xmlResponse)));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        this.resultCode = getXmlValue(HlrXpathExpressionConstant.Result_CODE);
        this.resultDescription = getXmlValue(HlrXpathExpressionConstant.Result_Descr);
    }

    public boolean isSuccess() {
        return StringHelper.isNullOrEmpty(this.resultCode) && this.resultCode.equals("0");
    }
}
