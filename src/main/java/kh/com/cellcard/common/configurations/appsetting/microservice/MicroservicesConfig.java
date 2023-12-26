package kh.com.cellcard.common.configurations.appsetting.microservice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MicroservicesConfig {
    public OcsServiceConfig ocsService;
    public SmsPushServiceConfig smsService;
    public BillingServiceConfig billingService;
    public BillingServiceConfig smsCatalogService;
}
