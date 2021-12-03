package cn.com.search.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class BookSearchResultVo {
	private Integer id;
	private String name;
	private Integer language;
	private Integer type;
	private Integer level;
	private String author;
	private String imgurl;
	private Integer score;

	private String publisher;
	private Date publishdate;
	private Integer wordCount;

	private BigDecimal praiseRate;

	private Integer pageCount;
	private BigDecimal price;
	private String isbn;
	private Integer readerNumber;
	private Integer recommendNumber;
	private Integer bookSize;
	private String discription;
	private Integer createTchId;
	private Integer state;
	private Byte isDelete;
	private Integer creator;
	private Date createTime;
	private Integer updator;
	private Date updateTime;
	
	private Map<String, String> highlightMaps;
	
}
