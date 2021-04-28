package com.ithawk.learn.springboot.service;

import com.ithawk.learn.springboot.entity.ShareEmailDetail;

import java.util.List;

/**
 *
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-11 21:06
 */
public interface ShareEmailService {

    List<ShareEmailDetail> selectAllShareEmailDetail();
}
