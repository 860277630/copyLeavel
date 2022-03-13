package com.example.demo.test;

import java.io.Serializable;

public class Books implements Cloneable,Serializable{
    private Integer id;

    private String bookname;

    private Double prices;

    private Integer counts;

    private int typeid;

	public Books() {
		super();
	}

	public Books(Integer id, String bookname, Double prices, Integer counts, int typeid) {
		super();
		this.id = id;
		this.bookname = bookname;
		this.prices = prices;
		this.counts = counts;
		this.typeid = typeid;
	}
	
	@Override
	public Books clone() {
		// TODO Auto-generated method stub
		Books com = null;
		try {
			com = (Books) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return com;
	}
	
	
	
	
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname == null ? null : bookname.trim();
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }


	public Books(Integer id, String bookname) {
		super();
		this.id = id;
		this.bookname = bookname;
	}

	@Override
	public String toString() {
		return "Books [id=" + id + ", bookname=" + bookname + ", prices=" + prices + ", counts=" + counts + ", typeid="
				+ typeid + "]";
	}
    
    
}