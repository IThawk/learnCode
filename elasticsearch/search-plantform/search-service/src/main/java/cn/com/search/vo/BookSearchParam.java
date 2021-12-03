package cn.com.search.vo;

import lombok.Data;

@Data
public class BookSearchParam {

	private String desc;
	private String bookName;
	private Double price;
	
	private Integer pageIndex = 1;
	private Integer pageSize = 5;
	
	private String statField = "level";		//分组统计字段
	
	private String highlightedField = "discription";
	
	private String searchType = "and";		//也可以设置or
	@Override
	public String toString() {
		return "BookSearchParam [desc=" + desc + ", bookName=" + bookName + ", price=" + price + ", pageIndex="
				+ pageIndex + ", pageSize=" + pageSize + "]";
	}
	
	
	
}
