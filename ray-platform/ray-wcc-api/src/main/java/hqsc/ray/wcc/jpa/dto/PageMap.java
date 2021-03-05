package hqsc.ray.wcc.jpa.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * ResultMap
 *
 * @author yangy
 * @date 2020-02-13
 */
public class PageMap<T> {
	@ApiModelProperty(value = "数据总条数")
	private Long count;
	@ApiModelProperty(value = "数据列表")
	private List<T> list;
	
	public static PageMap of(Long count, List list) {
		return new PageMap(count, list);
	}
	
	public PageMap() {
	}
	
	public PageMap(Long count, List<T> list) {
		this.count = count;
		this.list = list;
	}
	
	public Long getCount() {
		return count;
	}
	
	public PageMap setCount(Long count) {
		this.count = count;
		return this;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public PageMap setList(List<T> list) {
		this.list = list;
		return this;
	}
}
