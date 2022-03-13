package com.example.demo.test;

import java.io.Serializable;

public class Computer implements Cloneable,Serializable{//实现Cloneable接口，然后重写clone方法，将其改为public即可	
	//不可变类
	private String userName;	
	//基本类型
	private double prices;	
	//可变类
	private StringBuilder company;	
	//内联实体
	private Books instructions;
	
	//实现Cloneable接口，然后重写clone方法，将其改为public,并且返回类型改为当前类型
	@Override
	public Computer clone() {
		// TODO Auto-generated method stub
		Computer com = null;
		try {
			com = (Computer) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return com;
	}
	public Computer(String userName, double prices, StringBuilder company, Books instructions) {
		super();
		this.userName = userName;
		this.prices = prices;
		this.company = company;
		this.instructions = instructions;
	}



	public Computer() {
		super();
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getPrices() {
		return prices;
	}

	public void setPrices(double prices) {
		this.prices = prices;
	}

	public StringBuilder getCompany() {
		return company;
	}

	public void setCompany(StringBuilder company) {
		this.company = company;
	}

	public Books getInstructions() {
		return instructions;
	}

	public void setInstructions(Books instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return "Computer [userName=" + userName + ", prices=" + prices + ", company=" + company + ", instructions="
				+ instructions + "]";
	}

}
