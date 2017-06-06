import com.mongodb.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

public class TrendingServlet extends HttpServlet {

public void constructGroupByContent(AggregationOutput aggregate, PrintWriter pw)
{
	pw.println("<BR><h3>Top 5 zipcodes based on max products review<h3>");
	 pw.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "<TH>Zipcode<TH>Max Products reviewed\n");

for (DBObject result : aggregate.results()) {
BasicDBObject bobj= (BasicDBObject) result;
String tableData= "<tr><td> "+bobj.getString("value")+"</td>&nbsp"+"<td>"+bobj.getString("ReviewValue")+"</td></tr>";
pw.println(tableData);
}
}



public void constructProductGroupByContent(AggregationOutput aggregate, PrintWriter pw)
{
pw.println("<BR><h3>Top 5 products based on review count<h3>");
	 pw.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "<TH>Product Name<TH>Max reviews\n");

for (DBObject result : aggregate.results()) {
BasicDBObject bobj= (BasicDBObject) result;
String tableData= "<tr><td> "+bobj.getString("value")+"</td>&nbsp"+"<td>"+bobj.getString("ReviewValue")+"</td></tr>";
pw.println(tableData);
}
}

public void constructProductGroupByContent2(AggregationOutput aggregate, PrintWriter pw)
{
pw.println("<BR><h3>Top 5 products based on highest review rating<h3>");
	 pw.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "<TH>Product Name<TH>Highest review\n");

for (DBObject result : aggregate.results()) {
BasicDBObject bobj= (BasicDBObject) result;
String tableData= "<tr><td> "+bobj.getString("value1")+"</td>&nbsp"+"<td>"+bobj.getString("value")+"</td></tr>";
pw.println(tableData);
}
}

  HashMap < String, ArrayList < Review >> reviewHashmap = new HashMap < String, ArrayList < Review >> ();
 public void doGet(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {
  String checkoutURL, us;

  HttpSession session = request.getSession();
  ShoppingCart cart;
  synchronized(session) {
   String userid = (String) session.getAttribute("userid");
   
   if (userid != null && userid.length() != 0)
    userid = userid.trim();
	
   cart = (ShoppingCart) session.getAttribute("shoppingCart");
   if (userid == null) {
    us = "Login";
   } else {
    us = "Hello, " + userid;
   }

   if (cart == null) {
    cart = new ShoppingCart();
    session.setAttribute("shoppingCart", cart);
   }
   String itemID = request.getParameter("itemID");
   if (itemID != null) {
    String numItemsString =
     request.getParameter("numItems");
    if (numItemsString == null) {
     cart.addItem(itemID);
    } else {
     int numItems;
     try {
      numItems = Integer.parseInt(numItemsString);
     } catch (NumberFormatException nfe) {
      numItems = 1;
     }
     cart.setNumOrdered(itemID, numItems);
    }
   }
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   Date date = new Date();
   String title = "Trending View";
   String docType =
    "<!DOCTYPE HTML>\n";
   out.println(docType +
    "<HTML>\n" +
    "<HEAD><TITLE>" + title + "</TITLE>\n" +
    "<meta http-equiv=" + "Content-Type" + " content=" + "text/html; charset=utf-8" + " />\n" +
    "<link rel=" + "stylesheet" + " href=" + "styles.css" + " type=" + "text/css" + " />\n" +
    "</head>" +
    "<body>" +
    "<div id=" + "container" + ">\n");
   out.println("<nav>");
   out.println("<ul>");
   out.println("<li class=" + "start selected" + "><a href=" + "/BestDealHome/HomeServlet" + ">Home</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Phones" + ">Phones</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Tablets" + ">Tablets</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Laptops" + ">Laptops</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/TvPage" + ">TvPage</a></li>" +
    "<div style=float:" + "right" + ">" +
    "<li class=" + "" + "><a href=" + "LoginPageServlet" + ">" + us + "</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/" + ">Cart(" + cart.getItemsOrdered().size() + ")</a></li></div>");
   out.println("</nav>");
 
 MongoDBDataStoreUtilities.getConnection();
 
    out.println("<table id='bestseller'>");
	BasicDBObject groupFields= new BasicDBObject("_id", 0);
	groupFields.put("count",new BasicDBObject("$sum",1));
	groupFields.put("_id", "$retailerzip");
	DBObject group = new BasicDBObject("$group", groupFields);
	BasicDBObject sort = new BasicDBObject();
	BasicDBObject projectFields=new BasicDBObject("_id", 0);
	projectFields.put("value", "$_id");
	projectFields.put("ReviewValue","$count");
	BasicDBObject project = new BasicDBObject("$project", projectFields);
	sort.put("ReviewValue",-1);
	BasicDBObject orderby=new BasicDBObject("$sort",sort);
	BasicDBObject limit=new BasicDBObject("$limit",5);
	AggregationOutput aggregate = MongoDBDataStoreUtilities.myReviews.aggregate(group,project,orderby,limit);
	constructGroupByContent(aggregate,out);
	
	out.println("</table>");
	
	out.println("<table id='bestproduct'>");
	BasicDBObject groupFields1= new BasicDBObject("_id", 0);
	groupFields1.put("count",new BasicDBObject("$sum",1));
	groupFields1.put("_id", "$productName");
	DBObject group1 = new BasicDBObject("$group", groupFields1);
	BasicDBObject sort1 = new BasicDBObject();
	BasicDBObject projectFields1=new BasicDBObject("_id", 0);
	projectFields1.put("value", "$_id");
	projectFields1.put("ReviewValue","$count");
	BasicDBObject project1 = new BasicDBObject("$project", projectFields1);
	sort1.put("ReviewValue",-1);
	BasicDBObject orderby1=new BasicDBObject("$sort",sort1);
	BasicDBObject limit1=new BasicDBObject("$limit",5);
	
	//AggregationOutput aggregate1 = MongoDBDataStoreUtilities.myReviews.aggregate({$group:{_id:"$productName", "maxValue": {$max:"$value"}}});
	
	AggregationOutput aggregate1 = MongoDBDataStoreUtilities.myReviews.aggregate(group1,project1,orderby1,limit1);
	constructProductGroupByContent(aggregate1,out);
	out.println("</table>");

	
	//start
	
	out.println("<table id='bestproduct'>");
	BasicDBObject groupFields4= new BasicDBObject("_id", 0);
	groupFields4.put("maxVal", new BasicDBObject("$max", "$reviewRating"));
	groupFields4.put("_id", "$productName");
	DBObject group4 = new BasicDBObject("$group", groupFields4);
	BasicDBObject sort4 = new BasicDBObject();
	BasicDBObject projectFields4=new BasicDBObject("_id", 0);
	projectFields4.put("value", "$maxVal");
	projectFields4.put("value1", "$_id");
	BasicDBObject project4 = new BasicDBObject("$project", projectFields4);
	sort4.put("value1",1);
	sort4.put("value",-1);
	BasicDBObject orderby4=new BasicDBObject("$sort",sort4);
	BasicDBObject limit4=new BasicDBObject("$limit",5);
	AggregationOutput aggregate4 = MongoDBDataStoreUtilities.myReviews.aggregate(group4,project4,orderby4,limit4);
	constructProductGroupByContent2(aggregate4,out);
	out.println("</table>");

	//end
	
	out.println("</table>");
	
	/*out.println("<table id='bestreviews'>");
	int returnLimit= 8;
	BasicDBObject query = new BasicDBObject();
    DBObject sort2 = new BasicDBObject();
	sort2.put("productName",1);
	sort2.put("reviewRating",-1);
  // query.put("reviewRating", new BasicDBObject("$gt", 3));
	
    DBCursor dbCursor= MongoDBDataStoreUtilities.myReviews.find().sort(sort2).limit(1);
	out.println("<h3>Top 5 products based on review rating<h3>");
	 out.println("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
    "<TR BGCOLOR=\"#FFAD00\">\n" +
    "<TH>Product Name<TH>Max Review Rating\n");
	
	while (dbCursor.hasNext()) {
   BasicDBObject bobj = (BasicDBObject) dbCursor.next();
   
   String tableData= "<tr><td> "+bobj.getString("productName")+"</td>&nbsp"+"<td>"+bobj.getString("reviewRating")+"</td></tr>";
out.println(tableData);
 	}
		out.println("</table>"); */
	
	  out.println("</BODY></HTML>");
  }


 }
}