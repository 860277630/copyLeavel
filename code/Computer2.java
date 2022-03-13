package com.example.demo.test;

import java.io.Serializable;

public class Computer2 implements Cloneable,Serializable{//实现Cloneable接口，然后重写clone方法，将其改为public即可
	
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
	public Computer2 clone() {
		// TODO Auto-generated method stub
		Computer2 com = null;
		try {
			com = (Computer2) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		com.company = new StringBuilder(this.getCompany());//stringBuilder的构造方法
		com.instructions = this.getInstructions().clone();//这里books这个类实现了cloneAble接口
		//由于books里面都是基本变量和不可变类，所以，即使使用get来获取值，因为final类的存在，也可以看作是深复制来使用，
		//所以下面的使用方式也是正确的，但是只限类中只包含基本类型和不可变类		
		//Books b = this.getInstructions();
		//com.instructions = new Books(b.getId(),b.getBookname(),b.getPrices(),b.getCounts(),b.getTypeid());
		
		return com;
	}
	public Computer2(String userName, double prices, StringBuilder company, Books instructions) {
		super();
		this.userName = userName;
		this.prices = prices;
		this.company = company;
		this.instructions = instructions;
	}
	
	





	public Computer2() {
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
