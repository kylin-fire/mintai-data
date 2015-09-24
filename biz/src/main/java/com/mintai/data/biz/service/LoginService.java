package com.mintai.data.biz.service;

import com.alibaba.common.lang.StringUtil;
import com.mintai.data.common.helper.CommonHelper;
import org.springframework.stereotype.Service;

/**
 * 文件描述：登录服务
 *
 * @author leiteng
 *         Date 2015/9/23.
 */
@Service
public class LoginService {
    public boolean login(String userName, String password) {
        return StringUtil.isNotBlank(userName) && StringUtil.isNotBlank(password);
    }
}
