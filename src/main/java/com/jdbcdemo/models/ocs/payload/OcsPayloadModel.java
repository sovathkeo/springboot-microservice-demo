package com.jdbcdemo.models.ocs.payload;


import com.jdbcdemo.common.helper.StringHelper;
import com.jdbcdemo.common.wrapper.UuidWrapper;

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
}


