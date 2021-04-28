package com.ithawk.learn.springboot.service.impl;

import com.ithawk.learn.springboot.entity.ShareEmailDetail;
import com.ithawk.learn.springboot.mapper.ShareEmailDetailMapper;
import com.ithawk.learn.springboot.service.ShareEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-11 21:08
 */
@Service
public class ShareEmailServiceImpl implements ShareEmailService {

    @Autowired
    private ShareEmailDetailMapper shareEmailDetailMapper;

    @Override
    public List<ShareEmailDetail> selectAllShareEmailDetail() {
        return shareEmailDetailMapper.selectAllShareEmailDetail();
    }
}
