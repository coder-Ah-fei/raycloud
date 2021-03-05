package hqsc.ray.wcc.jpa.form.basic;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yang
 */
public class PageForm {
	
	/**
	 * 默认页面大小
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 默认当前页
	 */
	public static final int DEFAULT_PAGE_NOW = -1;
	
	/**
	 * 默认页面大小
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	
	/**
	 * 当前页码
	 */
	private int pageNow = DEFAULT_PAGE_NOW;
	
	/**
	 * 数据总条数
	 */
	private long rowCount;
	
	/**
	 * 总页数
	 */
	private int totalPages;
	
	/**
	 * 设置控件显示的页码数.即：类型为"page"的操作按钮的数量。
	 */
	private int numberOfPages = 5;
	
	
	/**
	 * 标记，判断加载表格式是否需要携带查询条件
	 */
	private boolean mark;
	
	// 兼容layui表格组件用
	private int page;
	private int limit;
	// 兼容layui表格组件用
	
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
		this.pageNow = page;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
		this.pageSize = limit;
	}
	
	public boolean isMark() {
		return mark;
	}
	
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	public PageForm() {
	}

//	public Page(long rowCount, int pageSize, int pageNow, int pageDisplaySize,
//			String formId) {
//	}
	
	public long getRowCount() {
		return rowCount;
	}
	
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}
	
	public int getNumberOfPages() {
		return numberOfPages;
	}
	
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public int getPageSize() {
		if (pageSize < 1) {
			return DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageNow() {
		if (pageNow < 1) {
			return DEFAULT_PAGE_NOW;
		}
		return pageNow;
	}
	
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	
	public int getFirstRow() {
		return getPageSize() * (getPageNow() - 1);
	}
	
	public int getTotalPages() {
		// 如果总记录数小于默认页面大小则只有一页
		if (rowCount < pageSize) {
			return DEFAULT_PAGE_NOW;
		}
		long _totalPages = rowCount % pageSize == 0 ? rowCount / pageSize : (rowCount / pageSize) + 1;
		totalPages = Integer.parseInt(String.valueOf(_totalPages));
		return totalPages;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public int getRowBegin() {
		return getFirstRow();
	}
	
	public int getRowEnd() {
		return getRowBegin() + getPageSize();
	}
	
	public int getPageEnd() {
		return 0;
	}
	
	public int getPageDisplaySize() {
		return 0;
	}
	
	public boolean isHeadOmit() {
		return false;
	}
	
	public boolean isTailOmit() {
		return false;
	}
	
	public boolean isSelectPluginStarted() {
		return false;
	}
	
	public boolean isTextPluginStarted() {
		return false;
	}
	
	public void sendPage(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("pageSupport", this);
	}
}
