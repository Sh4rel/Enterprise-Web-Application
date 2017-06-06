import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;
import java.sql.* ;  
import java.util.ArrayList;
import java.util.Iterator;

public class AjaxUtility {
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


 public static HashMap < String, Product > getData() {
  HashMap < String, Product > hm = new HashMap < String, Product > ();
  try {
   getConnection();
   Statement stmt = conn.createStatement();
   String selectCustomerQuery = "select * from products";
   ResultSet rs = stmt.executeQuery(selectCustomerQuery);
   while (rs.next()) {

    Product p = new Product(rs.getString("itemId"), rs.getString("itemName"), rs.getInt("price"), rs.getString("phone_img"));
    hm.put(rs.getString("itemId"), p);
   }
  }
catch(Exception e)
{}
  return hm;
 }


 public StringBuffer readdata(String searchId) {
  HashMap < String, Product > data;
  data = getData();
    StringBuffer sb = new StringBuffer();

  Iterator it = data.entrySet().iterator();
  while (it.hasNext()) {
   Map.Entry pi = (Map.Entry) it.next();
   Product p = (Product) pi.getValue();
   if (p.getName().toLowerCase().startsWith(searchId)) {
    sb.append("<product>");
    sb.append("<id>" + p.getId() + "</id>");
    sb.append("<productName>" + p.getName() + "</productName>");
    sb.append("</product>");
   }
  }
  return sb;
 }


}