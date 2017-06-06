
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;
import java.sql.* ;  
import java.util.Date;
import java.util.*;
import java.text.*;
import java.util.ArrayList;



public class MySqlDataStoreUtilities
{
static Connection conn = null;


public static void getConnection()
{
try
{
Class.forName("com.mysql.jdbc.Driver").newInstance();
conn=
DriverManager.getConnection("jdbc:mysql://localhost:3306/BestDealDatabase","root","root");
}
catch(Exception e)
{}
}

public static HashMap<String, User> selectUser(){
	HashMap<String, User> users =new HashMap<String, User>();
	
try{
getConnection();
String selectfromCustomerRegisterQuery = "SELECT * from  Registration;";
PreparedStatement pst = conn.prepareStatement(selectfromCustomerRegisterQuery);
ResultSet rs = pst.executeQuery();

while(rs.next())
{
	if(!users.containsKey(rs.getString("username")))
	{
		User u= new User(rs.getString("username"), rs.getString("password"),rs.getString("usertype"));
		users.put(rs.getString("username"), u);
	}
}
}
catch(Exception e){}
return users;
}

public static void insertUser(String username,String password,String repassword,String usertype){
try{
getConnection();
String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype)"
+ "VALUES (?,?,?,?);";
PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
pst.setString(1,username);
pst.setString(2,password);
pst.setString(3,repassword);
pst.setString(4,usertype);
pst.execute();
}
catch(Exception e){}
}


/*Load products onto db using SAX while loading the site*/
	public static void delTable()
{ 
try{ 
getConnection();

String delQuery ="truncate table Products";  
PreparedStatement pst = conn.prepareStatement(delQuery);
pst.executeUpdate();
}
catch(Exception e){}

}

public static void loadProductsWithSAX(String itemId,String itemName,String phone_condition,int price,String phone_img,String retailer){
try{
getConnection();

String insertIntoProductsLoadQuery = "INSERT INTO Products(itemId,itemName,phone_condition,price,phone_img,retailer) VALUES(?,?,?,?,?,?);";
PreparedStatement pst = conn.prepareStatement(insertIntoProductsLoadQuery);
pst.setString(1,itemId);
pst.setString(2,itemName);
pst.setString(3,phone_condition);
pst.setInt(4,price);
pst.setString(5,phone_img);
pst.setString(6,retailer);
pst.execute();
}
catch(Exception e){}
}


public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
{
HashMap<Integer,ArrayList<OrderPayment>> orderPayments=new HashMap<Integer,ArrayList<OrderPayment>>();
try{ 
getConnection();
String selectOrderQuery ="select * from customerorders";
PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
ResultSet rs = pst.executeQuery();
ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
while(rs.next())
{
	if(!orderPayments.containsKey(rs.getInt("OrderId")))
		{ 
	ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
	orderPayments.put(rs.getInt("orderId"), arr);
	}
	ArrayList<OrderPayment> listOrderPayment =orderPayments.get(rs.getInt("OrderId"));
	OrderPayment order= new OrderPayment(rs.getInt("OrderId"),rs.getString("userName"),rs.getString("deliveryDate"),rs.getDouble("orderPrice"),rs.getString("userAddress"));
	listOrderPayment.add(order);
	}
	}
	catch(Exception e){}
	return orderPayments;

}

private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}
	
	public static int getOrderId()
{ 
	
	int maxID = 0;
try{ 
getConnection();

String selectOrderQuery ="select MAX(orderId) from customerorders";  
PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
ResultSet rs = pst.executeQuery();
//ResultSet rs2 = s2.getResultSet();
while ( rs.next() ){
  maxID = rs.getInt(1);
}
}
catch(Exception e){}

return maxID;
}
	
	
	public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditcardNumber) //, 
{ 

 Date date1 = new Date();
  
Calendar cal = Calendar.getInstance();
cal.setTime(date1);
cal.add(Calendar.DATE,5); // add 5 days

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String date = sdf.format(cal.getTime());
 
	try
	{
		
	getConnection();
	String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,orderPrice,userAddress,creditcardNumber,deliveryDate) VALUES (?,?,?,?,?,?,?);"; //,deliveryDate
	PreparedStatement pst =conn.prepareStatement(insertIntoCustomerOrderQuery);
	pst.setInt(1,orderId);
	pst.setString(2,userName);
	pst.setString(3,orderName);
	pst.setDouble(4,orderPrice);
	pst.setString(5,userAddress);
	pst.setString(6,creditcardNumber);
	pst.setString(7,date);
	pst.execute();
	}
catch(Exception e){}
}

public static void deleteOrder(int orderId)
{ 
try
    {
      getConnection();
      
      String query = "delete from customerOrders where orderId = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, orderId);
      // execute the preparedstatement
      preparedStmt.execute();
      
    }
    catch (Exception e)
    {}
}

public static String getDeliveryDate(int orderId)
{ 
	String delDate=null;
try{ 
getConnection();

String selectOrderQuery ="select deliveryDate from customerorders";  
PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
ResultSet rs = pst.executeQuery();
//ResultSet rs2 = s2.getResultSet();
while ( rs.next() ){
  delDate = rs.getString("deliveryDate");
}
}
catch(Exception e){}

return delDate;

}
/*
public static void insertProduct(String itemId,String itemName,double price,String retailer,String itemCondition)
{
try{
getConnection();
String insertIntoProductRegisterQuery = "INSERT INTO Product(itemId ,itemName,phone_condition, price,phone_img,retailer,) VALUES (?,?,?,?,?);";
PreparedStatement pst = conn.prepareStatement(insertIntoProductRegisterQuery);
pst.setString(1,itemId);
pst.setString(2,itemName);
pst.setDouble(4,price);
pst.setString(4,retailer);
pst.setString(5,itemCondition);
pst.execute();
}
catch(Exception e){}
}
*/
public static void updateProducts(String itemId,double price,String retailer){
try{
getConnection();

 String query = "update products set price = ? , retailer=? where itemId = ?";
      PreparedStatement pst = conn.prepareStatement(query);
     pst.setString(3,itemId);
//pst.setString(2,itemName);
//pst.setString(3,phone_condition);
pst.setDouble(1,price);
//pst.setString(5,phone_img);
pst.setString(2,retailer);
pst.executeUpdate();
}
catch(Exception e){}
}
public static void deleteProduct(String itemId){
try{
getConnection();

 String query = "DELETE FROM products where itemId = ?";
      PreparedStatement pst = conn.prepareStatement(query);
     pst.setString(1,itemId);
	 pst.executeUpdate();
}
catch(Exception e){}
}
public static void deleteOrder(String orderId){
try{
getConnection();

 String query = "DELETE FROM customerOrders where orderId = ?";
      PreparedStatement pst = conn.prepareStatement(query);
     pst.setString(1,orderId);
	 pst.executeUpdate();
}
catch(Exception e){}
}
public static void updateOrder(String orderId,String address){
try{
getConnection();

     String query = "update customerOrders set userAddress = ? where orderId = ?";
     PreparedStatement pst = conn.prepareStatement(query);
     pst.setString(2,orderId);
     pst.setString(1,address);
	 pst.executeUpdate();
}
catch(Exception e){}
}




}