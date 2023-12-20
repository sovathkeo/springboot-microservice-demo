package kh.com.cellcard.models.ocs.bundle;

import kh.com.cellcard.common.constant.OcsBundleParamNameConstant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.w3c.dom.Node;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class OcsBundleModel {

    public String bundleId = "";
    public String bundleState = "";
    public String startDateTime = "";
    public String endDateTime = "";
    public String tariffPlanCOSPId = "";
    public String RTIME = "";
    public HashMap<String, String> bucketParams = new HashMap<>();

    public OcsBundleModel (Node paramListOfNode) {
        var paramOfNodeList = paramListOfNode.getChildNodes();
        var paramCount = paramOfNodeList.getLength();
        if (paramCount < 1) {
            return;
        }
        for(var i = 0; i < paramCount; i++ ) {
            var param = paramOfNodeList.item(i);
            var name = param.getFirstChild().getTextContent();
            var value = param.getLastChild().getTextContent();
            initializeField(name, value);
        }
    }

    private void initializeField(String name, String value){
        if (name.equalsIgnoreCase(OcsBundleParamNameConstant.BUNDLE_ID)) {
            bundleId = value;
        } else if (name.equalsIgnoreCase(OcsBundleParamNameConstant.BUNDLE_STATE)) {
            bundleState = value;
        } else if (name.equalsIgnoreCase(OcsBundleParamNameConstant.START_DATE_TIME)) {
            startDateTime = value;
        } else if (name.equalsIgnoreCase(OcsBundleParamNameConstant.END_DATE_TIME)) {
            endDateTime = value;
        } else if (name.equalsIgnoreCase(OcsBundleParamNameConstant.TARIFF_PLAN_COSP_ID)) {
            tariffPlanCOSPId = value;
        } else if (name.equalsIgnoreCase(OcsBundleParamNameConstant.RTIME)) {
            RTIME = value;
        } else if (name.startsWith("Bucket/")) {
            bucketParams.put(name, value);
        }
    }
}
