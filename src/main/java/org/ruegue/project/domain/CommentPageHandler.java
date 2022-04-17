package org.ruegue.project.domain;

import java.util.List;

public class CommentPageHandler {

	private List<CommentDto> list;
	private int totalCnt; // 총 계시물 수
	private int pageSize = 5;
	private int page = 1;
	private int naviSize =10; // 페이지 네비게이션 갯수
	private int totalPage; //총 패이지 수
	private int beginPage;
	private int endPage;
	private boolean showPrev;
	private boolean showNext;
	private int bno;

	public CommentPageHandler(int bno){
		this.bno = bno;

		totalPage = (int) Math.ceil(totalCnt/(double)pageSize);
		beginPage = (page-1)/naviSize * naviSize + 1;
		endPage = Math.min(beginPage + naviSize-1,totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	}

	public CommentPageHandler(int bno, int page){
		this.page = page;
		this.bno = bno;

		totalPage = (int) Math.ceil(totalCnt/(double)pageSize);
		beginPage = (page-1)/naviSize * naviSize + 1;
		endPage = Math.min(beginPage + naviSize-1,totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	}

	public CommentPageHandler(int totalCnt, List<CommentDto> list , int page , int bno){
		this.totalCnt = totalCnt;
		this.list = list;
		this.page = page;
		this.bno = bno;

		totalPage = (int) Math.ceil(totalCnt/(double)pageSize);
		beginPage = (page-1)/naviSize * naviSize + 1;
		endPage = Math.min(beginPage + naviSize-1,totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	}

	public CommentPageHandler(int totalCnt, List<CommentDto> list, int bno){
		this.totalCnt = totalCnt;
		this.list = list;
		this.bno = bno;

		totalPage = (int) Math.ceil(totalCnt/(double)pageSize);
		beginPage = (page-1)/naviSize * naviSize + 1;
		endPage = Math.min(beginPage + naviSize-1,totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	}


	public List<CommentDto> getList() {
		return list;
	}

	public void setList(List<CommentDto> list) {
		this.list = list;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNaviSize() {
		return naviSize;
	}

	public void setNaviSize(int naviSize) {
		this.naviSize = naviSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isShowPrev() {
		return showPrev;
	}

	public void setShowPrev(boolean showPrev) {
		this.showPrev = showPrev;
	}

	public boolean isShowNext() {
		return showNext;
	}

	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public Integer getOffset() {
		return (page-1)*pageSize;
	}

	@Override
	public String toString() {
		return "CommentPageHandler{" +
				"list=" + list +
				", totalCnt=" + totalCnt +
				", pageSize=" + pageSize +
				", page=" + page +
				", naviSize=" + naviSize +
				", totalPage=" + totalPage +
				", beginPage=" + beginPage +
				", endPage=" + endPage +
				", showPrev=" + showPrev +
				", showNext=" + showNext +
				", bno=" + bno +
				'}';
	}
}
