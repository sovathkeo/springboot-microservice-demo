package kh.com.cellcard.models.ocs.bundle;

import kh.com.cellcard.common.constant.OcsBundleParamNameConstant;
import kh.com.cellcard.common.constant.OcsDateTimeConstant;
import kh.com.cellcard.common.enums.datetime.DatetimeUnit;
import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.common.wrapper.DateTimeWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.w3c.dom.Node;

import java.util.Date;
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

    public Date getStartDatetime() {
        if (StringHelper.isNullOrEmpty(this.startDateTime)) {
            return DateTimeWrapper.defaultDate();
        }

        return DateTimeWrapper.fromString(startDateTime, OcsDateTimeConstant.OCS_DATETIME_FORMAT);
    }

    public String getStartDatetimeAsString() {
        return DateTimeWrapper.toString(getStartDatetime(), "yyyy-MM-dd HH:mm:ss");
    }

    public String getStartTimeAsString() {
        return this.getEndTimeAsString();
    }

    public String getStartDateAsString(){
        return DateTimeWrapper.toString(this.getStartDatetime(), OcsDateTimeConstant.OCS_DATE_FORMAT);
    }

    public String calculateStartDateAsString(int periodOfSub) {
        var newStartDatetime =  DateTimeWrapper.addDate(this.getEndDatetime(), periodOfSub, DatetimeUnit.DAY);
        return DateTimeWrapper.toString(newStartDatetime, OcsDateTimeConstant.OCS_DATE_FORMAT);
    }

    public Date getEndDatetime() {
        if (StringHelper.isNullOrEmpty(this.endDateTime)) {
            return DateTimeWrapper.defaultDate();
        }
        return DateTimeWrapper.fromString(this.endDateTime, OcsDateTimeConstant.OCS_DATETIME_FORMAT);
    }

    public String getEndDateTimeAsString(String format) {
        if (StringHelper.isNullOrEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        return DateTimeWrapper.toString(this.getEndDatetime(), format);
    }

    public String getEndDateAsString() {
        return DateTimeWrapper.toString(this.getEndDatetime(), OcsDateTimeConstant.OCS_DATE_FORMAT);
    }

    public String getEndTimeAsString(){
        return DateTimeWrapper.toString(this.getEndDatetime(), OcsDateTimeConstant.OCS_TIME_FORMAT);
    }

    public boolean isEndDatetimeLessThanEqualNowPlus2Minutes(){
        var nearestDate = DateTimeWrapper.addDate(DateTimeWrapper.now(), 2, DatetimeUnit.MINUTE);
        return DateTimeWrapper.isBeforeOrEqualOther(this.getEndDatetime(), nearestDate);
    }
}