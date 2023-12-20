package kh.com.cellcard.models.ocs.bundle;

import kh.com.cellcard.models.ocs.base.OcsResponseBaseModel;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public class OcsQueryBundleResponseModel extends OcsResponseBaseModel {

    public ArrayList<OcsBundleModel> bundles = new ArrayList<>();
    public OcsQueryBundleResponseModel(String response) throws ParserConfigurationException {
        super(response);
        initializeBundles();
    }

    private void initializeBundles() {
        var nodeListOps = super.getNodeLIst("//ParamList");
        if (nodeListOps.isEmpty()) {
            return;
        }
        var nodeList = nodeListOps.get();
        var nodeLength = nodeList.getLength();
        for (int i = 0 ; i < nodeLength ; i++) {
            bundles.add(new OcsBundleModel(nodeList.item(i)));
        }
    }
}