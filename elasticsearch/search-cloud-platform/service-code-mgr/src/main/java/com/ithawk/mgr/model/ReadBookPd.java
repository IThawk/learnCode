package com.ithawk.mgr.model;

import com.ithawk.mgr.core.EsBaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;

@ApiModel
@Table(name = "read_book_pd")
public class ReadBookPd extends EsBaseQuery {
    @ApiModelProperty(value = "", name = "id")
    private Integer id;

    @ApiModelProperty(value = "", name = "name")
    private String name;

    @ApiModelProperty(value = "", name = "enName")
    private String enName;

    @ApiModelProperty(value = "", name = "author")
    private String author;

    @ApiModelProperty(value = "", name = "imgurl")
    private String imgurl;

    @ApiModelProperty(value = "", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "", name = "creator")
    private Integer creator;

    @ApiModelProperty(value = "", name = "updateTime")
    private Date updateTime;

    @ApiModelProperty(value = "1正常，-1删除，0下架", name = "status")
    private Integer status;

    @ApiModelProperty(value = "评论数", name = "commentNum")
    private Integer commentNum;

    @ApiModelProperty(value = "价格，分", name = "price")
    private Integer price;

    @ApiModelProperty(value = "", name = "category")
    private String category;

    @ApiModelProperty(value = "", name = "discription")
    private String discription;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return en_name
     */
    public String getEnName() {
        return enName;
    }

    /**
     * @param enName
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return imgurl
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return creator
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return comment_num
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * @param commentNum
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return discription
     */
    public String getDiscription() {
        return discription;
    }

    /**
     * @param discription
     */
    public void setDiscription(String discription) {
        this.discription = discription;
    }
}