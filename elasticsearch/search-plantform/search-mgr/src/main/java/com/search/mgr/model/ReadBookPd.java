package com.search.mgr.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "read_book_pd")
public class ReadBookPd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    
    @Column(name = "en_name")
    private String enName;

    private String author;

    private String imgurl;

    @Column(name = "create_time")
    private Date createTime;

    private Integer creator;

    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "comment_num")
    private Integer commentNum;
    @Column
    private Integer price;
    @Column(name = "category")
    private String category;

    /**
     * 1正常，-1删除，0下架
     */
    private Integer status;

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
     * 获取1正常，-1删除，0下架
     *
     * @return status - 1正常，-1删除，0下架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1正常，-1删除，0下架
     *
     * @param status 1正常，-1删除，0下架
     */
    public void setStatus(Integer status) {
        this.status = status;
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

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}