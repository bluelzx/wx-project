package com.service.api.impl;

import com.data.AccountMapper;
import com.data.JsapiticketMapper;
import com.data.QrCodeMapper;
import com.domain.wx.Account;
import com.domain.wx.Jsapiticket;
import com.domain.wx.QrCode;
import com.service.api.AccessTokenService;
import com.service.api.WxCustomMsgService;
import com.service.api.WxMessageService;
import com.utils.*;
import com.models.wx.token.JsapiticketResp;
import com.models.wx.token.TokenResp;
import com.models.wx.token.WxServerIpsResp;
import com.models.wx.user.GetUserInfoReq;
import com.models.wx.user.QrCodeTicketResp;
import com.models.wx.user.UserInfoListResp;
import com.models.wx.user.UserInfoResp;
import com.service.wxutil.ReceiveMsg;
import com.service.wxutil.WxUrlUtils;
import com.service.wxutil.WxSignUtils;
import org.dom4j.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Admin on 2016/2/23.
 */
@Service("wxMessageService")
public class WxMessageServiceImpl implements WxMessageService {

    @Resource
    AccessTokenService accessTokenService;
    @Resource
    QrCodeMapper qrCodeMapper;
    @Resource
    AccountMapper accountMapper;
    @Resource
    JsapiticketMapper jsapiticketMapper;
    @Resource
    WxCustomMsgService wxMsgReplyService;

    public String reply(String body) {

        Document doc;
        try {
            if (StringUtils.isEmpty(body)) {
                return "";
            }
            doc = XmlParseUtils.getDocumentByXML(body);
            ReceiveMsg receive = new ReceiveMsg(doc);
            String msgType = receive.getMsgType();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public String reply(String signature, String timestamp, String nonce, String echostr)
            throws NoSuchAlgorithmException {
        System.out.println("signature:" + signature);
        String tmp = SHA1.gen("2016", timestamp, nonce);
        System.out.println("tmp:" + tmp);
        if (signature.equals(tmp)) {
            return echostr;
        }
        return "";
    }

    public List<String> getWxServerIp(int accountId) throws IOException {
        String url = WxUrlUtils.getInstance().getWxserveripservice();
        TokenResp token = accessTokenService.getAccessToken(accountId);
        url = String.format(url, token.getAccess_token());
        String json = HttpUtils.doGet(url, AcceptTypeEnum.json);
        WxServerIpsResp ips = JsonUtils.Deserialize(json, WxServerIpsResp.class);

        return ips != null && ips.getIp_list() != null
                ? Arrays.asList(ips.getIp_list())
                : new ArrayList<String>();
    }

    public String getQrCode(String param, int expireTime, int accountId) throws Exception {
        if (StringUtils.isEmpty(param)) return "";
        QrCode code = qrCodeMapper.getCodeByParam(param);
        String qrcodeUrl = WxUrlUtils.getInstance().getQrcode();
        if (code != null && code.getExpiredtime().after(new Date())) {
            return String.format(qrcodeUrl, code.getTicket());
        }
        String ticketUrl = WxUrlUtils.getInstance().getQrticket();
        TokenResp token = accessTokenService.getAccessToken(accountId);
        ticketUrl = String.format(ticketUrl, token.getAccess_token());
        int p = 0;
        String ticket = "";
        try {
            p = Integer.valueOf(param);
        } catch (Exception ex) {
            p = -1;
        }
        if (expireTime != 0 && p != -1) {
            ticket = getTicket(p, expireTime, ticketUrl, accountId);
        } else {
            ticket = getTicket(param, ticketUrl, accountId);
        }
        if (StringUtils.isEmpty(ticket)) {
            throw new Exception("获取ticket失败");
        }
        return String.format(qrcodeUrl, ticket);
    }

    public String getJsapiticket(int accountId) throws Exception {

        Jsapiticket ticket = jsapiticketMapper.getTicketByAccount(accountId);
        if (ticket != null && ticket.getExpiredtime().after(new Date())) {
            return ticket.getTicket();
        }

        Account account = accountMapper.getAccountById(accountId);
        if (account == null) {
            throw new Exception("获取微信账户失败");
        }
        TokenResp token = accessTokenService.getAccessToken(accountId);
        if (token == null || !StringUtils.isEmpty(token.getErrcode())) {
            throw new Exception("获取token失败");
        }
        String url = WxUrlUtils.getInstance().getJsapiticket();
        url = String.format(url, token.getAccess_token());
        String str = HttpUtils.doGet(url, AcceptTypeEnum.json);
        JsapiticketResp dto = JsonUtils.Deserialize(str, JsapiticketResp.class);
        if (dto == null || dto.getErrcode() != 0) {
            throw new Exception("ticket");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, dto.getExpires_in());
        ticket = new Jsapiticket(accountId, dto.getTicket(), calendar.getTime());
        jsapiticketMapper.addTicket(ticket);
        return ticket.getTicket();
    }

    public Map<String, String> getJsapiSignPackage(int accountid, String url) throws Exception {
        String ticket = getJsapiticket(accountid);
        if (StringUtils.isEmpty(ticket)) {
            throw new Exception("获取ticket失败");
        }
        Map<String, String> map = WxSignUtils.sign(ticket, url);
        Account account = accountMapper.getAccountById(accountid);
        map.put("appid", account.getAppid());
        return map;
    }

    public UserInfoResp getUserInfo(int accountid, String openid) throws Exception {
        String url = WxUrlUtils.getInstance().getUserInfo();
        if (StringUtils.isEmpty(url)) {
            throw new Exception("url未初始化");
        }
        TokenResp token = accessTokenService.getAccessToken(accountid);
        if (token == null) {
            throw new Exception("获取token失败");
        }
        String t = token.getAccess_token();
        url = String.format(url, t, openid);
        String json = HttpUtils.doGet(url, AcceptTypeEnum.json);
        if (StringUtils.isEmpty(json)) {
            throw new Exception("获取用户信息失败");
        }
        UserInfoResp dto = JsonUtils.Deserialize(json, UserInfoResp.class);
        return dto;
    }

    public List<UserInfoResp> getUserInfoBatch(int accountid, List<String> openids) throws Exception {
        String url = WxUrlUtils.getInstance().getUserinfoBatch();
        if (StringUtils.isEmpty(url)) {
            throw new Exception("url未初始化");
        }
        TokenResp token = accessTokenService.getAccessToken(accountid);
        if (token == null) {
            throw new Exception("获取token失败");
        }
        String t = token.getAccess_token();
        url = String.format(url, t);
        GetUserInfoReq args = new GetUserInfoReq();
        GetUserInfoReq.UserInfoArg ui;
        for (String item : openids) {
            ui = args.new UserInfoArg(item, "zh-CN");
            args.addUserInfoArg(ui);
        }
        String param = JsonUtils.Serialize(args);
        String json = HttpUtils.doPost(url, param, AcceptTypeEnum.json);
        if (StringUtils.isEmpty(json) || json.contains("errcode")) {
            throw new Exception(json);
        }
        UserInfoListResp dto = JsonUtils.Deserialize(json, UserInfoListResp.class);

        return dto != null ? dto.getUser_info_list() : new ArrayList<UserInfoResp>();
    }

    private String getTicket(int param, int expireTime, String url, int accountid) throws Exception {
        String str;
        //永久二维码
        if (expireTime == 0) {
            str = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + param + "}}}";
        } else {
            str = "{\"expire_seconds\": " + expireTime + ", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + param + "}}}";
        }
        String json = HttpUtils.doPost(url, str, AcceptTypeEnum.json);
        QrCodeTicketResp result = JsonUtils.Deserialize(json, QrCodeTicketResp.class);
        if (result == null) return "";
        if (!StringUtils.isEmpty(result.getErrcode())) {
            throw new Exception(result.getErrmsg());
        }
        QrCode code = qrCodeMapper.getCodeByParam(String.valueOf(param));
        if (code != null && expireTime != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, result.getExpire_seconds());
            code.setExpiredtime(calendar.getTime());
            qrCodeMapper.updateQrCode(code);
        } else {
            code = new QrCode();
            code.setParam(String.valueOf(param));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, result.getExpire_seconds());
            code.setExpiredtime(calendar.getTime());
            code.setTicket(result.getTicket());
            code.setCreatetime(new Date());
            code.setAccountid(accountid);
            qrCodeMapper.addQrCode(code);
        }

        return result.getTicket();
    }

    private String getTicket(String param, String url, int accountid) throws Exception {
        String str = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + param + "\"}}}";
        String json = HttpUtils.doPost(url, str, AcceptTypeEnum.json);
        QrCodeTicketResp result = JsonUtils.Deserialize(json, QrCodeTicketResp.class);
        if (result == null) return "";
        if (!StringUtils.isEmpty(result.getErrcode())) {
            throw new Exception(result.getErrmsg());
        }

        QrCode code = new QrCode();
        code.setParam(String.valueOf(param));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1000);
        code.setExpiredtime(calendar.getTime());
        code.setTicket(result.getTicket());
        code.setCreatetime(new Date());
        qrCodeMapper.addQrCode(code);

        return result.getTicket();
    }
}
