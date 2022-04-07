package org.ruegue.project.domain;

public class PageHandler {
	private int totalCnt; // 총 계시물 수
	private SearchCondition sc;
	private int naviSize =10; // 페이지 네비게이션 갯수
	private int totalPage; //총 패이지 수
	private int beginPage;
	private int endPage;
	private boolean showPrev;
	private boolean showNext;
	
	
	
	public PageHandler(int totalCnt, SearchCondition sc) {
		this.totalCnt = totalCnt;
		this.sc = sc;
		
		doPaging(totalCnt, sc);
	}

	public void doPaging(int totalCnt, SearchCondition sc) {
		this.totalCnt = totalCnt;
		
		totalPage = (int) Math.ceil(totalCnt/(double)sc.getPageSize());
		beginPage = (sc.getPage()-1)/naviSize * naviSize + 1;
		endPage = Math.min(beginPage + naviSize-1,totalPage);
		showPrev = beginPage != 1;
		showNext = endPage != totalPage;
	}

	
	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public SearchCondition getSc() {
		return sc;
	}

	public void setSc(SearchCondition sc) {
		this.sc = sc;
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

	@Override
	public String toString() {
		return "PageHandler [totalCnt=" + totalCnt + ", sc=" + sc + ", naviSize=" + naviSize + ", totalPage="
				+ totalPage + ", beginPage=" + beginPage + ", endPage=" + endPage + ", showPrev=" + showPrev
				+ ", showNext=" + showNext + "]";
	}

}
