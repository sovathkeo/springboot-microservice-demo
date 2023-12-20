package kh.com.cellcard.services.ocs.base;

import kh.com.cellcard.services.ocs.commands.OcsSubscribeService;
import kh.com.cellcard.services.ocs.queries.OcsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OcsServiceImpl implements OcsService {

    @Autowired
    public OcsQueryService query;

    @Autowired
    public OcsSubscribeService subscribe;
}
