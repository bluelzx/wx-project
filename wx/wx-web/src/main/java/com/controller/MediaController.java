package com.controller;

import com.model.PagerParam;
import com.models.web.*;
import com.models.web.media.*;
import com.service.web.AccountService;
import com.service.web.ArticleService;
import com.service.web.ImageMsgService;
import com.service.web.MediaService;
import com.utils.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/5/15.
 */
@RequestMapping("media")
@Controller
public class MediaController {

    @Resource
    MediaService mediaService;
    @Resource
    AccountService accountService;
    @Resource
    ImageMsgService imageMsgService;
    @Resource
    ArticleService articleService;

    @RequestMapping("media.html")
    public ModelAndView media() {
        ModelAndView view = new ModelAndView("media/media");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "getMediaList", method = RequestMethod.POST)
    public DataListResp getMediaList(PagerParam data) {
        DataListResp map = mediaService.getMediaList(data.getPageSize(), data.getPageIndex(),
                data.getArgs());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "addMedia", method = RequestMethod.POST)
    public BaseResp addMedia(SaveMediaInfo data,
                             HttpServletRequest req) {
        UserInfo user = CookieUtil.GetCurrentUser(req);
        String path = req.getServletContext().getRealPath("/upload");
        data.setFilename(path + File.separator + data.getFilename());
        return mediaService.addMedia(data, user);
    }

    @ResponseBody
    @RequestMapping(value = "getAccountSelect", method = RequestMethod.POST)
    public List<AccountSelectInfo> getAccountSelect(HttpServletRequest req) {
        UserInfo user = CookieUtil.GetCurrentUser(req);
        return accountService.getAccountSelect(user.getDomain());
    }

    @ResponseBody
    @RequestMapping(value = "deleteMedia", method = RequestMethod.POST)
    public BaseResp deleteMedia(@RequestBody List<Integer> data) {
        return mediaService.deleteMedia(data);
    }

    @ResponseBody
    @RequestMapping(value = "addImageMsg", method = RequestMethod.POST)
    public BaseResp addImageMsg(AddImageMsgReq data,
                                HttpServletRequest req) {
        String path = req.getServletContext().getRealPath("/upload");
        data.setFileName(path + File.separator + data.getFileName());
        return imageMsgService.addImageMsg(data);
    }

    @RequestMapping("imgList.html")
    public ModelAndView image() {
        ModelAndView view = new ModelAndView("media/imgList");

        return view;
    }

    @ResponseBody
    @RequestMapping(value = "getImageList", method = RequestMethod.POST)
    public DataListResp getImageList(PagerParam data) {
        return imageMsgService.getImageMsgList(data.getPageSize(),
                data.getPageIndex(), data.getDomain(), data.getArgs());
    }

    @ResponseBody
    @RequestMapping(value = "deleteImageMsg", method = RequestMethod.POST)
    public BaseResp deleteImageMsg(@RequestBody List<Integer> data) {
        return imageMsgService.deleteImageMsg(data);
    }

    @RequestMapping("articleList.html")
    public ModelAndView articleList() {
        ModelAndView view = new ModelAndView("media/articleList");
        return view;
    }

    @ResponseBody
    @RequestMapping("getArticle")
    public ArticleInfo getArticle(Integer id) {
        return articleService.getArticle(id);
    }

    @ResponseBody
    @RequestMapping("addArticle")
    public BaseResp addArticle(AddArticleReq data) {
        return articleService.addArticle(data);
    }

    @ResponseBody
    @RequestMapping(value = "getArticleList", method = RequestMethod.POST)
    public DataListResp getArticleList(PagerParam data) {
        return articleService.getArticleList(data.getPageSize(), data.getPageIndex(),
                data.getDomain(), data.getArgs());
    }

    @ResponseBody
    @RequestMapping(value = "deleteArticle", method = RequestMethod.POST)
    public BaseResp deleteArticle(@RequestBody DeleteArticleReq req) {
        return articleService.deleteArticle(req.getData(), req.isDeleteWx());
    }

    @RequestMapping("articleItem.html")
    public ModelAndView articleItem(@RequestParam("aid") Integer aid) {
        ModelAndView view = new ModelAndView("media/articleItem");
        view.addObject("aid", aid);
        return view;
    }
}
