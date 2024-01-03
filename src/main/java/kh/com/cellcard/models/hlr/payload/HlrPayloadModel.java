package kh.com.cellcard.models.hlr.payload;

import kh.com.cellcard.common.wrapper.DateTimeWrapper;

public abstract class HlrPayloadModel {
    public static String querySubscriberByMSISDN(String transactionId, String timeStamp, String accountId) {
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                         <soapenv:Header>
                             <ns1:Username soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns1="http://ispp.com.cn/ispp_npi/"></ns1:Username>
                             <ns2:Password soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns2="http://ispp.com.cn/ispp_npi/"></ns2:Password>
                             <ns3:Address soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns3="http://ispp.com.cn/ispp_npi/"/>
                             <ns4:VHLRID soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns4="http://ispp.com.cn/ispp_npi/"/>
                             <ns5:MessageID soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns5="http://ispp.com.cn/ispp_npi/">%s</ns5:MessageID>
                         </soapenv:Header>
                         <soapenv:Body>
                             <root xmlns="http://ispp.com.cn/ispp_npi/">
                                 <msg_head xmlns="">
                                     <time>%s</time>
                                     <from>ispp</from>
                                     <to>npi</to>
                                     <msg_type>call</msg_type>
                                     <serial>00000001</serial>
                                 </msg_head>
                                 <interface_msg xmlns="">
                                     <directive>
                                         <SubscriberClass Name="wSuMSubscriberProfile">
                                             <Method Name="GetMoAttributes">
                                                <msisdn>%s</msisdn>
                                             </Method>
                                         </SubscriberClass>
                                     </directive>
                                 </interface_msg>
                             </root>
                         </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(transactionId,timeStamp,accountId);
    }

    public static String createSubscriber(String transactionId, String timeStamp, String imsi, String accountId,String profile){
        // <!-- Profile: 10 for prepaid: 4G -->
        return """
               <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <soapenv:Header>
                        <ns1:Username soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns1="http://ispp.com.cn/ispp_npi/">1</ns1:Username>
                        <ns2:Password soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns2="http://ispp.com.cn/ispp_npi/">2</ns2:Password>
                        <ns3:Address soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns3="http://ispp.com.cn/ispp_npi/"/>
                        <ns4:VHLRID soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns4="http://ispp.com.cn/ispp_npi/"/>
                        <ns5:MessageID soapenv:actor="http://schemas.xmlsoap.org/soap/actor/next" soapenv:mustUnderstand="0" xmlns:ns5="http://ispp.com.cn/ispp_npi/">%s</ns5:MessageID>
                    </soapenv:Header>
                    <soapenv:Body>
                        <root xmlns="http://ispp.com.cn/ispp_npi/">
                            <msg_head xmlns="">
                                <time>%s</time>
                                <from>CApp</from>
                                <to>npi</to>
                                <msg_type>call</msg_type>
                                <serial>00000002</serial>
                            </msg_head>
                            <interface_msg xmlns="">
                                <directive>
                                    <SubscriberClass Name="wSuMSubscriberProfile">
                                        <Method Name="CreateMO">
                                            <MSISDN>%s</MSISDN>
                                            <IMSI>%s</IMSI>
                                            <Profile>%s</Profile>
                                        </Method>
                                    </SubscriberClass>
                                </directive>
                            </interface_msg>
                        </root>
                    </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(transactionId,timeStamp,imsi,accountId,profile);
    }
}
