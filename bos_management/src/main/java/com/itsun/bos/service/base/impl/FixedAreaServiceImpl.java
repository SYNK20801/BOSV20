package com.itsun.bos.service.base.impl;

import com.itsun.bos.dao.base.FixedAreaRespository;
import com.itsun.bos.domain.base.FixedArea;
import com.itsun.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by SY on 2017-07-23.
 * on BOSV20
 * on 下午 01:00
 */
@Service
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRespository fixedAreaRespository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRespository.save(model);
    }
}
