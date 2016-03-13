package com.service;

import com.dto.wx.TokenResp;

import java.io.IOException;

/**
 * Created by Admin on 2016/2/21.
 */
public interface AccessTokenService {
    /**
     * 刷新token
     * @param account
     * @return
     */
    TokenResp getAccessToken(int account) throws IOException;
}
