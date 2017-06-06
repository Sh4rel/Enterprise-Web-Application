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

public class DataAnalyticsPerform extends HttpServlet {

 public void constructTableContent(DBCursor dbCursor,PrintWriter pw)
{
String tableData= "";
pw.print("<table class='gridtable'>");
while (dbCursor.hasNext())
{
BasicDBObject bobj= (BasicDBObject) dbCursor.next();
tableData= "<tr><td align='center' colspan='2'><h3>Review<h3></td></tr><tr><td>Name: </td><td>" + bobj.getString("productName") + "</td></tr>"
+ "<tr><td>Rating:</td><td>" + bobj.getString("reviewRating") + "</td></tr>"
+ "<tr><td>Date:</td><td>" + bobj.getString("reviewDate") + "</td></tr>"
+ "<tr><td>Review Text:</td><td>" + bobj.getString("reviewText")+"</td><tr>";
pw.print(tableData);
}
pw.print("</table>");
//No data found
if(dbCursor.count() == 0)
{
tableData= "<h2>No Data Found</h2>";
pw.print(tableData);
}
}

public void constructProductGroupByContent2(AggregationOutput aggregate, PrintWriter pw)
{
pw.println("<BR><h3><h3>");
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
    /*"<li class=" + "" + "><a href=" + "/BestDealHome/Phones" + ">Phones</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Tablets" + ">Tablets</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/Laptops" + ">Laptops</a></li>" +
    "<li class=" + "" + "><a href=" + "/BestDealHome/TvPage" + ">TvPage</a></li>" +*/
    "<div style=float:" + "right" + ">" +
    "<li class=" + "" + "><a href=" + "LoginPageServlet" + ">" + us + "</a></li>");
   // "<li class=" + "" + "><a href=" + "/BestDealHome/" + ">Cart(" + cart.getItemsOrdered().size() + ")</a></li></div>");
   out.println("</div></nav>");

   MongoDBDataStoreUtilities.getConnection();

   int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
   String product = request.getParameter("product");
   int cost=0;
   try{
	       cost = Integer.parseInt(request.getParameter("price"));

   }
catch(Exception e){}
   String city = request.getParameter("city");
   String groupby = request.getParameter("groupby");
   String filters[] = request.getParameterValues("compareRating");
     String filters1[] = request.getParameterValues("select1");
	  String filters2[] = request.getParameterValues("select2");
	  String filters3[] = request.getParameterValues("select4");
	  String filters4[] = request.getParameterValues("select5");


   BasicDBObject query = new BasicDBObject();
   boolean noFilter = false;
   boolean filterByRating = false;
   
   if ( filters != null)
   {
	   query.put("reviewRating", reviewRating);
   }
   
   if (filters1 != null)
   {
	   query.put("productName", product);
   }
     if (filters2 != null)
   {
	   query.put("cost", cost);
   }
      if (filters3 != null)
   {
	   query.put("retailercity", city);
   }
out.println("review rate"+product+"filter:"+filters);

/*
if(filters4 != null)
{
	out.println("<table id='bestproduct'>");
	BasicDBObject groupFields4= new BasicDBObject("_id", 0);
	//groupFields4.put("maxVal", new BasicDBObject("$max", "$reviewRating"));
	groupFields4.put("_id", "$groupby");
	DBObject group4 = new BasicDBObject("$group", groupFields4);
	BasicDBObject sort4 = new BasicDBObject();
	BasicDBObject projectFields4=new BasicDBObject("_id", 0);
	//projectFields4.put("value", "$");
	projectFields4.put("value1", "$_id");
	BasicDBObject project4 = new BasicDBObject("$project", projectFields4);
	sort4.put("value1",1);
	//sort4.put("value",-1);
	BasicDBObject orderby4=new BasicDBObject("$sort",sort4);
	//BasicDBObject limit4=new BasicDBObject("$limit",5);
	AggregationOutput aggregate4 = MongoDBDataStoreUtilities.myReviews.aggregate(group4,project4,orderby4);
	constructProductGroupByContent2(aggregate4,out);
	out.println("</table>");

}*/

   DBCursor dbCursor = MongoDBDataStoreUtilities.myReviews.find(query);
   constructTableContent(dbCursor, out);


   out.println("</BODY></HTML>");
  }


 }
}