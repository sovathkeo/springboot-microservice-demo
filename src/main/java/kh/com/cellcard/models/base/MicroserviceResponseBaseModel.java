package kh.com.cellcard.models.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MicroserviceResponseBaseModel<TData extends MicroserviceResponseData> {

    public MicroserviceResponseMeta meta;

    public TData data;
}
