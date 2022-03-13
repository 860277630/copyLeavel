package com.example.demo.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;


import org.junit.Test;
import org.springframework.beans.BeanUtils;


public class copyTest {

	@Test
	public void copyTest() {
		// 首先按照视频实现深复制

		// 1.构造函数方式
		Computer computer_1 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer_2 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		// 使用get都会影响到引用 所以这么做是浅复制 get其实就是将引用传过来
		Computer computer_3 = new Computer(computer_1.getUserName(), computer_1.getPrices(), computer_1.getCompany(),
				computer_1.getInstructions());
		computer_3.setUserName("jone");
		StringBuilder company_3 = computer_3.getCompany();
		company_3.append("公司");
		System.out.println("==computer_1==" + computer_1.toString());
		System.out.println("==computer_2==" + computer_2.toString());
		System.out.println("==computer_3==" + computer_3.toString());
		// 2.重写clone方法
        //浅复制
		Computer computer_4 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer_5 = computer_4.clone();
		computer_5.setUserName("jone");
		StringBuilder company = computer_5.getCompany();
		company.append("公司");
		System.out.println("==computer_4==" + computer_4.toString());
		
		
		//深复制
		Computer2 computer2_1 = new Computer2("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer2 computer2_2 = computer2_1.clone();
		computer2_2.setUserName("jone");
		StringBuilder company2 = computer2_2.getCompany();
		company2.append("公司");
		System.out.println("==computer2_1=="+computer2_1.toString());
		
	}

	// 赋值、深复制浅复制测试  以及get set对其的影响
	// 只针对实体，list，map来测试
	//要记住：get永远得到的是引用，set是分配新的工作空间
	//new和基本类型和不可变类的重新赋值有同样功效，都是创建新值
	//关于get  set的探讨
	
	//赋值
	@Test
	public void copyForEntity() {
		//实体
		//实体类型下，赋值  get  set都会影响到被复制的值 如：
		//基本类型和不可变量不可以用get方试修改，因为get得到的是引用，但是基本类型和不可变量却创建了新值
		//set
		Computer computer_1 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		
		Computer computer_2 = computer_1;
		//赋值不论是基本变量还是引用变量，修改都会影响被复制的值
		computer_2.setUserName("lisa");
		computer_2.setPrices(78.88);
		computer_2.setCompany(new StringBuilder("cocaCola"));
		System.out.println("==computer_1=="+computer_1.toString());
		//get
		Computer computer_3 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer_4 = computer_3;
		String userName_1 = computer_4.getUserName();
		StringBuilder company_1 = computer_4.getCompany();
		//首先是错误的修改方法
		userName_1 = "lisa";
		//get在得到后，用new的方式会将引用指向新见的变量上
		company_1 = new StringBuilder("cocaCola");
		//可以看到，结果并没有改变，不能用new的方式，这也是为什么基本变量和不可变类不能用get方式修改的原因
		System.out.println("==computer_4=="+computer_4.toString());
		//正确的修改方式
		StringBuilder company_2 = computer_4.getCompany();
		company_2.append("公司");
		computer_4.setUserName("lisa");
		System.out.println("==computer_3=="+computer_3.toString());
		System.out.println("==computer_4=="+computer_4.toString());
		
		//浅复制
		//大部分的工具类 如：BeanUtils，mapStruct,未进行深复制处理的clone，或者是用构造函数，但是构造函数参数用的是get方式得到的
		//浅复制对于基本类型和不可变类来说已经是深复制了，但是对于其他引用类型（必须用new来创建的）是浅复制
		//浅复制中set可以保持被复制的内容不变，但是get会改变被复制的内容
		//以下是用为进行深复制的clone来做如：
		//首先介绍构造函数get参数导致的浅复制
		Computer computer_5 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		// 使用get都会影响到引用 所以这么做是浅复制 get其实就是将引用传过来
		Computer computer_6 = new Computer(computer_5.getUserName(), computer_5.getPrices(), computer_5.getCompany(),
				computer_5.getInstructions());
		computer_6.setUserName("jone");//set重新创建了新的对象，所以不会改变被复制的内容
		StringBuilder company_3 = computer_6.getCompany();
		company_3.append("公司");//get的方式修改引用，所以
		System.out.println("==computer_5==" + computer_5.toString());
		System.out.println("==computer_6==" + computer_6.toString());
		//然后用浅复制的clone
		Computer computer_7 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer_8 = computer_7.clone();
		//set
		computer_8.setCompany(new StringBuilder("cocaCola"));
		computer_8.setInstructions(new Books(2, "产品说明书", 25.66, 50, 5));
		System.out.println("==computer_7=="+computer_7.toString());
		System.out.println("==computer_8=="+computer_8.toString());
		//get
		Computer computer_9 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer_10 = computer_9.clone();
		StringBuilder company_4 = computer_10.getCompany();
		company_4.append("公司");
		Books instructions = computer_10.getInstructions();
		instructions.setBookname("钢铁是怎样炼成的");//这里需要进一步研究为什么二级目录的set会影响引用
		System.out.println("==computer_9=="+computer_9.toString());
		System.out.println("==computer_10=="+computer_10.toString());
		//深复制
		//产生深复制的手段一般有  构造函数（参数不能用其他地方get来的，因为传进去的是引用，造成浅复制），深复制处理后的clone，序列化（很慢，实现很复杂，不建议）
		//在深复制中，get，set都不会影响到被复制变量的值（相当于只有基本类型和不可变量的浅复制）
		// 1.构造函数方式
		Computer computer_11 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer_12 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		//2.clone
		Computer2 computer2_1 = new Computer2("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer2 computer2_2 = computer2_1.clone();
		computer2_2.setUserName("jone");
		StringBuilder company2 = computer2_2.getCompany();
		company2.append("公司");
		System.out.println("==computer2_1=="+computer2_1.toString());
		System.out.println("==computer2_2=="+computer2_2.toString());
		//3.序列化
		Computer2 computer2_3 = new Computer2("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer2 computer2_4 = deepCopy(computer2_3);
		computer2_4.getCompany().append("公司");
		System.out.println("==computer2_4=="+computer2_4.toString());
		System.out.println("==computer2_3=="+computer2_3.toString());
		//4.json流深复制
		Computer2 computer2_5 = new Computer2("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer2 computer2_6 = (Computer2) deepCopy(computer2_5,Computer2.class);
		computer2_6.getCompany().append("公司");
		System.out.println("==computer2_5=="+computer2_5.toString());
		System.out.println("==computer2_6=="+computer2_6.toString());

	}
	
	
	//list  赋值  深复制 浅复制   get  set的表现
	//不论是实体，list，还是map，赋值公用的是1级地址，浅复制公用的是二级地址，深复制没有共用地址
	@Test
	public void copyForList() {
		//赋值
		//赋值的话，即使基本变量和不可变量量，源数据也会受到影响
		//且set对原数据有影响
		List<Integer> list1 = buildBaseList_1();
		List<Integer> list2 = list1;
		list2.set(0, 9);
		System.out.println("==list1=="+list1.toString());
		//浅复制
		//浅复制基本变量和不可变量是不受影响的，相当于深复制
		//对于含有实体来说，同实体的浅复制，即对于只含基本类型和不可变量的实体，相当于列表的深复制
		//对于含有引用类型，即必须通过new创建的属性，相当于列表的浅复制，浅复制的表现到每个实体的浅复制
		//也就是说list的set和get然后set一个新值是不会影响的，只有实体级别的get才会影响
		List<Integer> list3 = buildBaseList_1();
		List<Integer> list4 = new ArrayList<>();
		list4.addAll(list3);
		list4.add(6);
		System.out.println("==list3=="+list3.toString());
		//浅复制对于实体对象
		List<Computer> list5 = buildEntityList_1();
		List<Computer> list6 = new ArrayList<>();
		list6.addAll(list5);
		//list级别的set不会影响源数据
		list6.set(0, new Computer("jone", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "钢铁是怎样炼成的", 25.66, 50, 5)));
		//list级别的get然后set不会影响源数据
		Computer computer_1 = list6.get(1);
		computer_1.setUserName("lisa");
		computer_1.setCompany(new StringBuilder("cocaCola"));
		System.out.println("==list5=="+list5.toString());
		//list级别的get，然后实体级别的get，然后用非创建新值的办法（new的办法）会影响源数据
		list6.get(1).getCompany().append("公司");
		list6.get(1).getInstructions().setBookname("格列佛游记");
		System.out.println("==list5=="+list5.toString());
		//list级别的get，然后实体级别的get/set，然后用创建新值的办法（new的办法）不会影响源数据
		//做个新数据
		List<Computer> list7 = buildEntityList_1();
		List<Computer> list8 = new ArrayList<>();
		list8.addAll(list7);
		Computer computer = list8.get(0);
		computer.setPrices(899.55);
		computer.setCompany(new StringBuilder("amarzon"));
		Books instructions = computer.getInstructions();
		instructions = new Books(2, "康熙王朝", 25.66, 50, 5);
		System.out.println("==list7=="+list7.toString());
		//深复制
		//浅复制对于基本类型和不可变量，以及只含基本变量和不可变量的实体来说已经是深复制
		//深复制对于含有引用类型的实体（必须用new来创建的属性）是深复制
		//深复制实现的方式1.单个实体实现序列化，并创建深复制方法 2.单个实体实现深复制clone，然后循环的复制到新list中
		//在深复制中，get/set无论哪个级别，那个操作都不会影响数值的改变
		List<Computer2>  list9 = buildEntityList_2();
		List<Computer2>  list10 = depCopy(list9);
		//改变值查看源数据
		System.out.println("==list10=="+list10.toString());
		list10.get(0).getCompany().append("公司");
		System.out.println("==list9=="+list9.toString());
		//clone方法
		List<Computer2>  list11 = new ArrayList<>();
		for(Computer2 c :list9) {
			list11.add(c.clone());
		}
		list11.get(0).getCompany().append("公司");
		list11.get(0).getInstructions().setBookname("武林外传");
		System.out.println("==list9=="+list9.toString());
	}
	//map
	//赋值  浅复制  深复制  以及get  put在里面的表现
	@Test
	public void copyForMap() {
		//赋值
		//只含有基本类型和不可变量
		//对于赋值来将，put  get都会影响源数据的值
		//简言之就是无论本数据怎么动，源数据都会动
		Map<String,Integer> map_1 =   buildBaseMap_1();
		Map<String,Integer> map_2 = map_1;
		//put会影响源数据的值
		map_2.put("1", 2);
		System.out.println("==map_1=="+map_1.toString());
		Map<String,Computer> map_3 =  buildEntityMap_1();
		Map<String,Computer> map_4 = map_3; 
		Computer computer = map_4.get("1");
		computer.setUserName("lisa");
		//get会影响源数据额值
		System.out.println("==map_3=="+map_3.toString());
		//浅复制   putAll

		Map<String,Integer> map_5 =   buildBaseMap_1();
		Map<String,Integer> map_6 = new HashMap<>();
		map_6.putAll(map_5);
		map_6.put("1", 2);
		//浅复制对于二级地址（只含有基本类型和不可变量是没有能力改变的）//感觉是通用的，任何类型
		System.out.println("==map_5=="+map_5.toString());

		Map<String,Computer> map_7 =  buildEntityMap_1();
		Map<String,Computer> map_8 = new HashMap<>();
		map_8.putAll(map_7);
		Computer computer1 = map_8.get("1");
		//浅复制对于二级目录有引用类型的
		//如果引用类型只含有基本类型和不可变量 那么是深复制
		//如果引用类型还有引用属性（必须用new方式创建） 那么是浅复制
		//用引用类型的set(new x())的方法  是不会改变源数据的值的
		//用引用类型的get,然后引用属性的set，append方法是会影响原数据的值的
		//用引用类型的get,然后 = new的方法，是错误的修改方法  本数据和源数据都不会受影响
		computer1.setUserName("lisa");//set  new x()
		computer1.getCompany().append("公司");  //get set
		Books instructions = computer1.getInstructions();
		instructions = new Books();//错误的修改方法
		System.out.println("==map_7=="+map_7.toString());
		//深复制
		//简言之就是无论本数据怎么动，源数据都不会动
		//深复制的实现方式是序列化
		HashMap<String,Computer> map_9 =  (HashMap<String, Computer>) buildEntityMap_1();
		Map<String,Computer> map_10 = new HashMap<>();
		System.out.println("==map_9=="+map_9.toString());
		map_10 = deepCopy(map_9);
		Computer computer2 = map_10.get("1");
		computer2.getCompany().append("公司");
		System.out.println("==map_9=="+map_9.toString());
		System.out.println("==map_10=="+map_10.toString());
		
		
		
	}
	
	
	private List<Computer>  buildEntityList_1(){
		List<Computer> list = new ArrayList<>();
		list.add(new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		list.add(new Computer("Adrian", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		list.add(new Computer("Elvis", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		return list;
	}
	
	private List<Computer2>  buildEntityList_2(){
		List<Computer2> list = new ArrayList<>();
		list.add(new Computer2("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		list.add(new Computer2("Adrian", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		list.add(new Computer2("Elvis", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		return list;
	}
	
	private List<Integer>  buildBaseList_1(){
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		return list;
	}
	

	private Map<String,Integer>  buildBaseMap_1(){
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 4);
		return map;
	}
	
	private Map<String,Computer>  buildEntityMap_1(){
		Map<String,Computer> map = new HashMap<String,Computer>();
		map.put("1", new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		map.put("2", new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		map.put("3", new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5)));
		return map;
	}
	//实现序列化后的list深复制
	private static <T> List<T> depCopy(List<T> srcList) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(srcList);
 
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream inStream = new ObjectInputStream(byteIn);
			List<T> destList = (List<T>) inStream.readObject();
			return destList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//hashMap序列化深复制工具类  不能序列化map因为map没有实现序列化接口
	//同时也可以深复制实体（实现序列化接口）
	public static <T extends Serializable> T deepCopy(T obj) {
		T clonedObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			clonedObj = (T) ois.readObject();
			ois.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clonedObj;
	}

	//json进行深复制
	public static  Object deepCopy(Object obj,Class cls){
		String json = JSON.toJSONString(obj);
		return JSON.parseObject(json, cls);
	}
	@Test
	public void test() {

		Computer computer = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		Computer computer2 = new Computer();
		Computer computer3 = new Computer("peter", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "产品使用说明书", 25.66, 50, 5));
		BeanUtils.copyProperties(computer, computer2);
		//set赋予新值  不会影响浅复制下复制的一方
		computer2.setUserName("lisa");
		computer2.setInstructions(new Books());
		//从computer3引用的stringBuilder   computer3该属性变了，它也会跟着变
		computer2.setCompany(computer3.getCompany());
		//改变computer3的值，注意是改变引用，所以用get  用set（new的方法就不会改变了）
		StringBuilder company = computer3.getCompany();
		company.append("公司");
		System.out.println("=computer="+computer.toString());
		System.out.println("=computer2="+computer2.toString());
		
		
		//list/Map形式

		List<Computer> list1 = buildEntityList_1();
		List<Computer> list2 = new ArrayList<>();
		list2.addAll(list1);
		//list级别的set不会影响拷贝的一方
		list2.set(0, new Computer("jone", 7895.25, new StringBuilder("lenovo"),
				new Books(2, "钢铁是怎样炼成的", 25.66, 50, 5)));
		//但是，浅复制下用内部的set就会收到影响 因为内部是赋值
		Computer computer4 = list2.get(1);
		computer4.setUserName("jodan");
		computer4.setCompany(new StringBuilder("zhouheiya"));
		System.out.println("=list1="+list1.toString());
		System.out.println("=list2="+list2.toString());
		
		
		Map<String,Computer> map_7 =  buildEntityMap_1();
		Map<String,Computer> map_8 = new HashMap<>();
		map_8.putAll(map_7);
		//put new的办法
		map_8.put("1", new Computer());
		//浅复制下用内部的set就会收到影响 因为内部是赋值
		Computer computer5 = map_8.get("2");
		computer5.setUserName("rainy");
		computer5.setCompany(new StringBuilder("老干妈"));
		System.out.println("=map_7="+map_7.toString());
		System.out.println("=map_8="+map_8.toString());
		
	}
}
