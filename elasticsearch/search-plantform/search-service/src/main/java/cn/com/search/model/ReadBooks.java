package cn.com.search.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "read_books")
public class ReadBooks {
    /**
     * 书籍编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 书籍名称
     */
    private String name;

    /**
     * 语种1中文2英语9其他
     */
    private Integer language;

    /**
     * 分类
     */
    private Integer type;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 作者
     */
    private String author;

    /**
     * 书籍图片地址
     */
    private String imgurl;

    /**
     * 阅读积分值：关联到积分配置数据
     */
    private Integer score;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 出版日期
     */
    private Date publishdate;

    /**
     * 字数，单位：万
     */
    @Column(name = "word_count")
    private Integer wordCount;

    /**
     * 好评率,百分比
     */
    @Column(name = "praise_rate")
    private BigDecimal praiseRate;

    /**
     * 页数
     */
    @Column(name = "page_count")
    private Integer pageCount;

    /**
     * 定价
     */
    private BigDecimal price;

    /**
     * ISBN编号
     */
    private String isbn;

    /**
     * 阅读人数
     */
    @Column(name = "reader_number")
    private Integer readerNumber;

    /**
     * 被推荐次数
     */
    @Column(name = "recommend_number")
    private Integer recommendNumber;

    /**
     * 开本
     */
    @Column(name = "book_size")
    private Integer bookSize;

    /**
     * 书籍描述
     */
    private String discription;

    /**
     * 创建老师编号
     */
    @Column(name = "create_tch_id")
    private Integer createTchId;

    /**
     * 1有效0无效
     */
    private Integer state;

    /**
     * 是否删除，0：否，1：是
     */
    @Column(name = "is_delete")
    private Byte isDelete;

    /**
     * 创建者
     */
    private Integer creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改者
     */
    private Integer updator;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取书籍编号
     *
     * @return id - 书籍编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置书籍编号
     *
     * @param id 书籍编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取书籍名称
     *
     * @return name - 书籍名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置书籍名称
     *
     * @param name 书籍名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取语种1中文2英语9其他
     *
     * @return language - 语种1中文2英语9其他
     */
    public Integer getLanguage() {
        return language;
    }

    /**
     * 设置语种1中文2英语9其他
     *
     * @param language 语种1中文2英语9其他
     */
    public void setLanguage(Integer language) {
        this.language = language;
    }

    /**
     * 获取分类
     *
     * @return type - 分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置分类
     *
     * @param type 分类
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取等级
     *
     * @return level - 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置等级
     *
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取书籍图片地址
     *
     * @return imgurl - 书籍图片地址
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置书籍图片地址
     *
     * @param imgurl 书籍图片地址
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    /**
     * 获取阅读积分值：关联到积分配置数据
     *
     * @return score - 阅读积分值：关联到积分配置数据
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置阅读积分值：关联到积分配置数据
     *
     * @param score 阅读积分值：关联到积分配置数据
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取出版社
     *
     * @return publisher - 出版社
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 设置出版社
     *
     * @param publisher 出版社
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 获取出版日期
     *
     * @return publishdate - 出版日期
     */
    public Date getPublishdate() {
        return publishdate;
    }

    /**
     * 设置出版日期
     *
     * @param publishdate 出版日期
     */
    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    /**
     * 获取字数，单位：万
     *
     * @return word_count - 字数，单位：万
     */
    public Integer getWordCount() {
        return wordCount;
    }

    /**
     * 设置字数，单位：万
     *
     * @param wordCount 字数，单位：万
     */
    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * 获取好评率,百分比
     *
     * @return praise_rate - 好评率,百分比
     */
    public BigDecimal getPraiseRate() {
        return praiseRate;
    }

    /**
     * 设置好评率,百分比
     *
     * @param praiseRate 好评率,百分比
     */
    public void setPraiseRate(BigDecimal praiseRate) {
        this.praiseRate = praiseRate;
    }

    /**
     * 获取页数
     *
     * @return page_count - 页数
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * 设置页数
     *
     * @param pageCount 页数
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 获取定价
     *
     * @return price - 定价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置定价
     *
     * @param price 定价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取ISBN编号
     *
     * @return isbn - ISBN编号
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 设置ISBN编号
     *
     * @param isbn ISBN编号
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 获取阅读人数
     *
     * @return reader_number - 阅读人数
     */
    public Integer getReaderNumber() {
        return readerNumber;
    }

    /**
     * 设置阅读人数
     *
     * @param readerNumber 阅读人数
     */
    public void setReaderNumber(Integer readerNumber) {
        this.readerNumber = readerNumber;
    }

    /**
     * 获取被推荐次数
     *
     * @return recommend_number - 被推荐次数
     */
    public Integer getRecommendNumber() {
        return recommendNumber;
    }

    /**
     * 设置被推荐次数
     *
     * @param recommendNumber 被推荐次数
     */
    public void setRecommendNumber(Integer recommendNumber) {
        this.recommendNumber = recommendNumber;
    }

    /**
     * 获取开本
     *
     * @return book_size - 开本
     */
    public Integer getBookSize() {
        return bookSize;
    }

    /**
     * 设置开本
     *
     * @param bookSize 开本
     */
    public void setBookSize(Integer bookSize) {
        this.bookSize = bookSize;
    }

    /**
     * 获取书籍描述
     *
     * @return discription - 书籍描述
     */
    public String getDiscription() {
        return discription;
    }

    /**
     * 设置书籍描述
     *
     * @param discription 书籍描述
     */
    public void setDiscription(String discription) {
        this.discription = discription;
    }

    /**
     * 获取创建老师编号
     *
     * @return create_tch_id - 创建老师编号
     */
    public Integer getCreateTchId() {
        return createTchId;
    }

    /**
     * 设置创建老师编号
     *
     * @param createTchId 创建老师编号
     */
    public void setCreateTchId(Integer createTchId) {
        this.createTchId = createTchId;
    }

    /**
     * 获取1有效0无效
     *
     * @return state - 1有效0无效
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置1有效0无效
     *
     * @param state 1有效0无效
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取是否删除，0：否，1：是
     *
     * @return is_delete - 是否删除，0：否，1：是
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除，0：否，1：是
     *
     * @param isDelete 是否删除，0：否，1：是
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取创建者
     *
     * @return creator - 创建者
     */
    public Integer getCreator() {
        return creator;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改者
     *
     * @return updator - 修改者
     */
    public Integer getUpdator() {
        return updator;
    }

    /**
     * 设置修改者
     *
     * @param updator 修改者
     */
    public void setUpdator(Integer updator) {
        this.updator = updator;
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
}