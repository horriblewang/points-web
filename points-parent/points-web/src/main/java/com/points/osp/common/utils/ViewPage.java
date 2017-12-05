package com.points.osp.common.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

public class ViewPage<T> {
	private int pageNo = 1;
	private int pageSize = 10;
	private long count;
	private int first;
	private int last;
	private int prev;
	private int next;
	private boolean firstPage;
	private boolean lastPage;
	private int length = 8;
	private int slider = 1;

	private List<T> list = new ArrayList();

	private String orderBy = "";

	private String funcName = "page";

	private String message = "";

	public ViewPage() {
		this.pageSize = -1;
	}

	public ViewPage(HttpServletRequest request, HttpServletResponse response) {
		this(request, response, -2);
	}

	public ViewPage(HttpServletRequest request, HttpServletResponse response,
			int defaultPageSize) {
		String no = request.getParameter("pageNo");
		if (!StringUtils.isNotBlank(no)) {
			no = (String) request.getAttribute("pageNo");
		}
		if (StringUtils.isNumeric(no)) {
			CookieUtils.setCookie(response, "pageNo", no);
			setPageNo(Integer.parseInt(no));
		} else if (request.getParameter("repage") != null) {
			no = CookieUtils.getCookie(request, "pageNo");
			if (StringUtils.isNumeric(no)) {
				setPageNo(Integer.parseInt(no));
			}
		}

		String size = request.getParameter("pageSize");
		if (!StringUtils.isNotBlank(size)) {
			size = (String) request.getAttribute("pageSize");
		}
		if (StringUtils.isNumeric(size)) {
			CookieUtils.setCookie(response, "pageSize", size);
			setPageSize(Integer.parseInt(size));
		} else if (request.getParameter("repage") != null) {
			no = CookieUtils.getCookie(request, "pageSize");
			if (StringUtils.isNumeric(size))
				setPageSize(Integer.parseInt(size));
		} else if (defaultPageSize != -2) {
			this.pageSize = defaultPageSize;
		}

		String orderBy = request.getParameter("orderBy");
		if (StringUtils.isNotBlank(orderBy))
			setOrderBy(orderBy);
	}

	public ViewPage(int pageNo, int pageSize) {
		this(pageNo, pageSize, 0L);
	}

	public ViewPage(int pageNo, int pageSize, long count) {
		this(pageNo, pageSize, count, new ArrayList());
	}

	public ViewPage(int pageNo, int pageSize, long count, List<T> list) {
		setCount(count);
		setPageNo(pageNo);
		setPageSize(pageSize);
		getList().addAll(list);
	}

	public void initialize() {
		this.first = 1;

		this.last = (int) (this.count
				/ (this.pageSize < 1 ? 20 : this.pageSize) + this.first - 1L);

		if ((this.count % this.pageSize != 0L) || (this.last == 0)) {
			this.last += 1;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}

		if (this.pageNo <= 1) {
			this.pageNo = this.first;
			this.firstPage = true;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
			this.lastPage = true;
		}

		if (this.pageNo < this.last - 1)
			this.next = (this.pageNo + 1);
		else {
			this.next = this.last;
		}

		if (this.pageNo > 1)
			this.prev = (this.pageNo - 1);
		else {
			this.prev = this.first;
		}

		if (this.pageNo < this.first) {
			this.pageNo = this.first;
		}

		if (this.pageNo > this.last)
			this.pageNo = this.last;
	}

	public String toString() {
		initialize();

		StringBuilder sb = new StringBuilder();

		if (this.pageNo == this.first)
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>\n");
		else {
			sb.append(new StringBuilder()
					.append("<li><a href=\"javascript:\" onclick=\"")
					.append(this.funcName).append("(").append(this.prev)
					.append(",").append(this.pageSize)
					.append(");\">&#171; 上一页</a></li>\n").toString());
		}

		int begin = this.pageNo - this.length / 2;

		if (begin < this.first) {
			begin = this.first;
		}

		int end = begin + this.length - 1;

		if (end >= this.last) {
			end = this.last;
			begin = end - this.length + 1;
			if (begin < this.first) {
				begin = this.first;
			}
		}

		if (begin > this.first) {
			int i = 0;
			for (i = this.first; (i < this.first + this.slider) && (i < begin); i++) {
				sb.append(new StringBuilder()
						.append("<li><a href=\"javascript:\" onclick=\"")
						.append(this.funcName).append("(").append(i)
						.append(",").append(this.pageSize).append(");\">")
						.append(i + 1 - this.first).append("</a></li>\n")
						.toString());
			}

			if (i < begin) {
				sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			}
		}

		for (int i = begin; i <= end; i++) {
			if (i == this.pageNo) {
				sb.append(new StringBuilder()
						.append("<li class=\"active\"><a href=\"javascript:\">")
						.append(i + 1 - this.first).append("</a></li>\n")
						.toString());
			} else {
				sb.append(new StringBuilder()
						.append("<li><a href=\"javascript:\" onclick=\"")
						.append(this.funcName).append("(").append(i)
						.append(",").append(this.pageSize).append(");\">")
						.append(i + 1 - this.first).append("</a></li>\n")
						.toString());
			}

		}

		if (this.last - end > this.slider) {
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">...</a></li>\n");
			end = this.last - this.slider;
		}

		for (int i = end + 1; i <= this.last; i++) {
			sb.append(new StringBuilder()
					.append("<li><a href=\"javascript:\" onclick=\"")
					.append(this.funcName).append("(").append(i).append(",")
					.append(this.pageSize).append(");\">")
					.append(i + 1 - this.first).append("</a></li>\n")
					.toString());
		}

		if (this.pageNo == this.last)
			sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>\n");
		else {
			sb.append(new StringBuilder()
					.append("<li><a href=\"javascript:\" onclick=\"")
					.append(this.funcName).append("(").append(this.next)
					.append(",").append(this.pageSize).append(");\">")
					.append("下一页 &#187;</a></li>\n").toString());
		}

		sb.append("<li class=\"disabled controls\"><a href=\"javascript:\">当前 ");

		sb.append(new StringBuilder()
				.append("<input type=\"text\" value=\"")
				.append(this.pageNo)
				.append("\" onkeypress=\"var _tmpPageNo=this.value;(function(){var e=window.event||this;var c=e.keyCode||e.which||arguments.callee.caller.arguments[0].which;if(c==13)")
				.toString());

		sb.append(new StringBuilder().append(this.funcName)
				.append("(_tmpPageNo,").append(this.pageSize)
				.append(");})();\" onclick=\"this.select();\"/> 页,每页 ")
				.toString());

		sb.append(new StringBuilder()
				.append("<input type=\"text\" value=\"")
				.append(this.pageSize)
				.append("\" onkeypress=\"var _tmpPageSzie=this.value;(function(){var e=window.event||this;var c=e.keyCode||e.which||arguments.callee.caller.arguments[0].which;if(c==13)")
				.toString());

		sb.append(new StringBuilder()
				.append(this.funcName)
				.append("(")
				.append(this.pageNo)
				.append(",_tmpPageSzie);})();\" onclick=\"this.select();\"/> 条，")
				.toString());
		sb.append(new StringBuilder().append("共 ").append(this.count)
				.append(" 条").append(this.message != null ? this.message : "")
				.append("</a><li>\n").toString());

		sb.insert(0, "<ul>\n").append("</ul>\n");

		sb.append("<div style=\"clear:both;\"></div>");

		return sb.toString();
	}

	public String getHtml() {
		return toString();
	}

	public long getCount() {
		return this.count;
	}

	public void setCount(long count) {
		this.count = count;
		if (this.pageSize >= count)
			this.pageNo = 1;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = (pageSize <= 0 ? 10 : pageSize);
	}

	@JsonIgnore
	public int getFirst() {
		return this.first;
	}

	@JsonIgnore
	public int getLast() {
		return this.last;
	}

	@JsonIgnore
	public int getTotalPage() {
		return getLast();
	}

	@JsonIgnore
	public boolean isFirstPage() {
		return this.firstPage;
	}

	@JsonIgnore
	public boolean isLastPage() {
		return this.lastPage;
	}

	@JsonIgnore
	public int getPrev() {
		if (isFirstPage()) {
			return this.pageNo;
		}
		return this.pageNo - 1;
	}

	@JsonIgnore
	public int getNext() {
		if (isLastPage()) {
			return this.pageNo;
		}
		return this.pageNo + 1;
	}

	public List<T> getList() {
		if (this.list == null) {
			this.list = Lists.newArrayList();
		}
		return this.list;
	}

	@JsonIgnore
	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@JsonIgnore
	public String getFuncName() {
		return this.funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonIgnore
	public boolean isDisabled() {
		return this.pageSize == -1;
	}

	@JsonIgnore
	public boolean isNotCount() {
		return this.count == -1L;
	}

	public int getFirstResult() {
		int firstResult = (getPageNo() - 1) * getPageSize();
		if (firstResult >= getCount()) {
			firstResult = 0;
		}
		return firstResult;
	}

	public int getLastResult() {
		int lastResult = getFirstResult() + getMaxResults();
		if (lastResult > getCount()) {
			lastResult = (int) getCount();
		}
		return lastResult;
	}

	public int getMaxResults() {
		return getPageSize();
	}
}