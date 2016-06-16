package com.entity;


public class Product {

	private long Productid;
	private String Name;
	private String Category;
	private String Keywords;
	private String Introduce;
	private String SeriesName;
	private long SeriesTop;
	private String Standard;
	private String Color;
	private double MarketPrice;
	private double ActivePrice;
	private String ActiveBeginDate;
	private String ActiveEndDate;
	private int Ordernum;
	private int Purchase;
	private String Relation;
	
	public long getProductid() {
		return Productid;
	}
	public void setProductid(long productid) {
		Productid = productid;
	}
	public double getMarketPrice() {
		return MarketPrice;
	}
	public void setMarketPrice(double marketPrice) {
		MarketPrice = marketPrice;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public long getSeriesTop() {
		return SeriesTop;
	}
	public void setSeriesTop(long seriesTop) {
		SeriesTop = seriesTop;
	}
	public double getActivePrice() {
		return ActivePrice;
	}
	public void setActivePrice(double activePrice) {
		ActivePrice = activePrice;
	}
	public String getActiveBeginDate() {
		return ActiveBeginDate;
	}
	public void setActiveBeginDate(String activeBeginDate) {
		ActiveBeginDate = activeBeginDate;
	}
	public String getActiveEndDate() {
		return ActiveEndDate;
	}
	public void setActiveEndDate(String activeEndDate) {
		ActiveEndDate = activeEndDate;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getKeywords() {
		return Keywords;
	}
	public void setKeywords(String keywords) {
		Keywords = keywords;
	}
	public String getIntroduce() {
		return Introduce;
	}
	public void setIntroduce(String introduce) {
		Introduce = introduce;
	}
	public String getSeriesName() {
		return SeriesName;
	}
	public void setSeriesName(String seriesName) {
		SeriesName = seriesName;
	}
	public String getStandard() {
		return Standard;
	}
	public void setStandard(String standard) {
		Standard = standard;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public int getOrdernum() {
		return Ordernum;
	}
	public void setOrdernum(int ordernum) {
		Ordernum = ordernum;
	}
	public int getPurchase() {
		return Purchase;
	}
	public void setPurchase(int purchase) {
		Purchase = purchase;
	}
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
	}
	
}
