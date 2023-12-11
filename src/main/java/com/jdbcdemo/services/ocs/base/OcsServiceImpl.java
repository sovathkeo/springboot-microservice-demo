package com.jdbcdemo.services.ocs.base;

import com.jdbcdemo.services.ocs.commands.OcsSubscribeService;
import com.jdbcdemo.services.ocs.queries.OcsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OcsServiceImpl implements OcsService{

    @Autowired
    public OcsQueryService query;

    @Autowired
    public OcsSubscribeService subscribe;
}
