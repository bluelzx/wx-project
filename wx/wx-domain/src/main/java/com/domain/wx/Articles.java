package com.domain.wx;

import java.util.Date;

public class Articles {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.id
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.title
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.mediaid
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private String mediaid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.createtime
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.remark
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.accountid
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private Integer accountid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column articles.domain
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    private Integer domain;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.id
     *
     * @return the value of articles.id
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.id
     *
     * @param id the value for articles.id
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.title
     *
     * @return the value of articles.title
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.title
     *
     * @param title the value for articles.title
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.mediaid
     *
     * @return the value of articles.mediaid
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public String getMediaid() {
        return mediaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.mediaid
     *
     * @param mediaid the value for articles.mediaid
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setMediaid(String mediaid) {
        this.mediaid = mediaid == null ? null : mediaid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.createtime
     *
     * @return the value of articles.createtime
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.createtime
     *
     * @param createtime the value for articles.createtime
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.remark
     *
     * @return the value of articles.remark
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.remark
     *
     * @param remark the value for articles.remark
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.accountid
     *
     * @return the value of articles.accountid
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public Integer getAccountid() {
        return accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.accountid
     *
     * @param accountid the value for articles.accountid
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column articles.domain
     *
     * @return the value of articles.domain
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public Integer getDomain() {
        return domain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column articles.domain
     *
     * @param domain the value for articles.domain
     *
     * @mbggenerated Fri Dec 02 20:45:26 CST 2016
     */
    public void setDomain(Integer domain) {
        this.domain = domain;
    }
}