package kh.com.cellcard.models.ocs.payload;


import kh.com.cellcard.common.helper.StringHelper;
import kh.com.cellcard.common.wrapper.UuidWrapper;
import kh.com.cellcard.models.ocs.bundle.OcsBundleModel;

public class OcsPayloadModel {

    public static String querySubscriberAccount(String reqId,String accountId){
        return """
                <RetrieveRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                    <SessionInfo>
                        <sessionId>1</sessionId>
                    </SessionInfo>
                    <RequestInfo>
                        <ReqID>%s</ReqID>
                    </RequestInfo>
                    <NERoutingInfo>
                        <NeName/>
                        <NeGroupName/>
                        <DistributionKey/>
                    </NERoutingInfo>
                    <TaskList>
                        <Task>
                            <Name>Query Subscriber Account</Name>
                            <QueryCriteria>
                                <Param>
                                    <Name>Account ID</Name>
                                    <Value>%s</Value>
                                </Param>
                            </QueryCriteria>
                        </Task>
                    </TaskList>
                </RetrieveRequest>
                """.formatted(reqId,accountId);
    }

    public static String queryBundle(String accountId) {
        return queryBundle(accountId, UuidWrapper.uuidAsString(), UuidWrapper.uuidAsString());
    }
    public static String queryBundle(String accountId, String requestId, String transactionId){
        return """
                <RetrieveRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                     <SessionInfo>
                         <sessionId>1</sessionId>
                     </SessionInfo>
                     <RequestInfo>
                         <ReqID>%s</ReqID>
                     </RequestInfo>
                     <NERoutingInfo>
                         <NeName/>
                         <NeGroupName/>
                         <DistributionKey/>
                     </NERoutingInfo>
                     <TaskList>
                         <Task>
                             <Name>Query Bundle</Name>
                             <QueryCriteria>
                                 <Param>
                                     <Name>Account ID</Name>
                                     <Value>%s</Value>
                                 </Param>
                                 <Param>
                                     <Name>Account Type</Name>
                                     <Value>Subscriber</Value>
                                 </Param>
                                 <Param>
                                     <Name>Operation Type</Name>
                                     <Value>Query All</Value>
                                 </Param>
                                 <Param>
                                     <Name>Transaction ID</Name>
                                     <Value>%s</Value>
                                 </Param>
                             </QueryCriteria>
                         </Task>
                     </TaskList>
                 </RetrieveRequest>""".formatted(requestId,accountId,transactionId);
    }

    public String deductBalance(String reqId, String accountId, String purchaseFee, String transactionId) {
        return """
                <SubmitRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                    <SessionInfo>
                        <sessionId>1</sessionId>
                    </SessionInfo>
                    <RequestInfo>
                        <ReqID>%s</ReqID>
                    </RequestInfo>
                    <NERoutingInfo>
                        <NeName/>
                        <NeGroupName/>
                        <DistributionKey/>
                    </NERoutingInfo>
                    <TaskList>
                        <Task>
                            <Name>Adjust Balance</Name>
                            <ParamList>
                                <Param>
                                    <Name>Account ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Account Type</Name>
                                    <Value>Subscriber</Value>
                                </Param>
                                <Param>
                                    <Name>Amount</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Balance</Name>
                                    <Value>Primary</Value>
                                </Param>
                                <Param>
                                    <Name>Method</Name>
                                    <Value>DECR</Value>
                                </Param>
                                <Param>
                                    <Name>Generate AMA</Name>
                                    <Value>Y</Value>
                                </Param>
                                <Param>
                                    <Name>Recharge</Name>
                                    <Value>Y</Value>
                                </Param>
                                <Param>
                                    <Name>UCL</Name>
                                    <Value>N</Value>
                                </Param>
                                <Param>
                                    <Name>Transaction ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Negative Credit</Name>
                                    <Value>N</Value>
                                </Param>
                            </ParamList>
                        </Task>
                    </TaskList>
                </SubmitRequest>""".formatted(reqId, accountId, purchaseFee, transactionId);
    }

    public String adjustBalanceDGID(String reqId, String accountId, String DGID,String transactionId) {
        return """
                <SubmitRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                    <SessionInfo>
                        <sessionId>1</sessionId>
                    </SessionInfo>
                    <RequestInfo>
                        <ReqID>%s</ReqID>
                    </RequestInfo>
                    <NERoutingInfo>
                        <NeName></NeName>
                        <NeGroupName></NeGroupName>
                        <DistributionKey></DistributionKey>
                    </NERoutingInfo>
                    <TaskList>
                        <Task>
                            <Name>Adjust Balance</Name>
                            <ParamList>
                                <Param>
                                    <Name>Account ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Amount</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Balance</Name>
                                    <Value>Primary</Value>
                                </Param>
                                <Param>
                                    <Name>Method</Name>
                                    <Value>INCR</Value>
                                </Param>
                                <Param>
                                    <Name>Recharge</Name>
                                    <Value>Y</Value>
                                </Param>
                                <Param>
                                    <Name>Transaction ID</Name>
                                    <Value>%s</Value>
                                </Param>
                            </ParamList>
                        </Task>
                    </TaskList>
                </SubmitRequest>
                """.formatted(reqId,accountId, DGID,transactionId);
    }

    public String depositBundleFee(String reqId,String accountId,String bundleId,String bundleFee,String transactionId) {
        return """
                <SubmitRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                    <SessionInfo>
                        <sessionId>1</sessionId>
                    </SessionInfo>
                    <RequestInfo>
                        <ReqID>%s</ReqID>
                    </RequestInfo>
                    <NERoutingInfo>
                        <NeName></NeName>
                        <NeGroupName></NeGroupName>
                        <DistributionKey></DistributionKey>
                    </NERoutingInfo>
                    <TaskList>
                        <Task>
                            <Name>Subscribe Bundle</Name>
                            <ParamList>
                                <Param>
                                    <Name>Account ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Bundle ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Extra Bucket Amount</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Period of Sub</Name>
                                    <Value>1</Value>
                                </Param>
                                <Param>
                                    <Name>Bundle Update Flag</Name>
                                    <Value>Y</Value>
                                </Param>
                                <Param>
                                    <Name>Transaction ID</Name>
                                    <Value>%s</Value>
                                </Param>
                            </ParamList>
                        </Task>
                    </TaskList>
                </SubmitRequest>
                """.formatted(reqId,accountId,bundleId,bundleFee,transactionId);
    }
    public String subscribeBundle(String reqId,String accountId,String bundleId,String transactionId){
        if (StringHelper.isNullOrEmpty(reqId)) {
            reqId = UuidWrapper.uuidAsString();
        }
        return """
                <SubmitRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                    <SessionInfo>
                        <sessionId>1</sessionId>
                    </SessionInfo>
                    <RequestInfo>
                        <ReqID>%s</ReqID>
                    </RequestInfo>
                    <NERoutingInfo>
                        <NeName></NeName>
                        <NeGroupName></NeGroupName>
                        <DistributionKey></DistributionKey>
                    </NERoutingInfo>
                    <TaskList>
                        <Task>
                            <Name>Subscribe Bundle</Name>
                            <ParamList>
                                <Param>
                                    <Name>Account ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Bundle ID</Name>
                                    <Value>%s</Value>
                                </Param>
                                <Param>
                                    <Name>Transaction ID</Name>
                                    <Value>%s</Value>
                                </Param>
                            </ParamList>
                        </Task>
                    </TaskList>
                </SubmitRequest>
                """.formatted(reqId, accountId, bundleId, transactionId);
    }

    public static String subscribeBundleNA(String accountId, OcsBundleModel currentBundle, int periodOfSub, String newBundleName, String transactionId){
        var startDate = currentBundle.calculateStartDateAsString(-periodOfSub);
        var startTime = currentBundle.getStartTimeAsString();
        var endDate = currentBundle.getEndDateAsString();
        var endTime = currentBundle.getEndTimeAsString();
        var reqId = UuidWrapper.uuidAsString();
        return """
                <SubmitRequest xmlns="http://alcatel-lucent.com/esm/ws/svcmgr/V2_0">
                    <SessionInfo>
                        <sessionId>1</sessionId>
                    </SessionInfo>
                    <RequestInfo>
                        <ReqID>{REQUEST_ID}</ReqID>
                    </RequestInfo>
                    <NERoutingInfo>
                        <NeName></NeName>
                        <NeGroupName></NeGroupName>
                        <DistributionKey></DistributionKey>
                    </NERoutingInfo>
                    <TaskList>
                        <Task>
                           <Name>Subscribe Bundle</Name>
                           <ParamList>
                               <Param>
                                   <Name>Account ID</Name>
                                   <Value>{ACCOUNT_ID}</Value>
                               </Param>
                               <Param>
                                   <Name>Bundle ID</Name>
                                   <Value>{NEW_BUNDLE}</Value>
                               </Param>
                               <Param>
                                   <Name>Bundle Fee</Name>
                                   <Value>0</Value>
                               </Param>
                               <Param>
                                   <Name>Payment Source</Name>
                                   <Value>Account</Value>
                               </Param>
                               <Param>
                                   <Name>Old Bundle ID</Name>
                                   <Value>{OLD_BUNDLE}</Value>
                               </Param>
                               <Param>
                                   <Name>Start Date</Name>
                                   <Value>{START_DATE}</Value>
                               </Param>
                               <Param>
                                   <Name>Start Time</Name>
                                   <Value>{START_TIME}</Value>
                               </Param>
                               <Param>
                                   <Name>End Date</Name>
                                   <Value>{END_DATE}</Value>
                               </Param>
                               <Param>
                                   <Name>End Time</Name>
                                   <Value>{END_TIME}</Value>
                               </Param>
                               <Param>
                                   <Name>Transaction ID</Name>
                                   <Value>{TRANSACTION_ID}</Value>
                               </Param>
                           </ParamList>
                       </Task>
                    </TaskList>
                </SubmitRequest>
                """
                .replace("{REQUEST_ID}", reqId)
                .replace("{ACCOUNT_ID}", accountId)
                .replace("{NEW_BUNDLE}", newBundleName)
                .replace("{OLD_BUNDLE}", currentBundle.bundleId)
                .replace("{START_DATE}", startDate)
                .replace("{START_TIME}", startTime)
                .replace("{END_DATE}", endDate)
                .replace("{END_TIME}", endTime)
                .replace("{TRANSACTION_ID}", transactionId);
    }
}


