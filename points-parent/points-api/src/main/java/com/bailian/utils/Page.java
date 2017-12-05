package com.bailian.utils;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = -8123039538589235324L;
	protected int currentPage = 1;

	protected int pageSize = 10;
	protected long count;
	protected int pages;
	protected int start = 1;
	protected int end;
	private List<T> list;

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return this.count;
	}

	public void setCount(long count) {
		this.count = count;

		if (count > 0L) {
			this.pages = (int) (this.count / this.pageSize);

			if (this.count % this.pageSize > 0L) {
				this.pages += 1;
			}

			if (this.currentPage > this.pages) {
				this.currentPage = this.pages;
			}

			this.start = ((this.currentPage - 1) * this.pageSize + 1);
			this.end = (this.start + this.pageSize - 1);
		}
	}

	public int getPages() {
		return this.pages;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return this.end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}