package kh.com.cellcard.common.configurations.appsetting.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseMicroserviceConfig {
    public String baseUrl;
    public int requestTimeoutMillisecond;
}
