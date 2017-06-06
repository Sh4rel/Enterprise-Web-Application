import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.util.Date;

public class SaveReview extends HttpServlet {

String userid;
 //Storing Reviews
 public void storeReview(String productname, String producttype, int reviewrating, String reviewdate, String reviewtext,
								 String brand,double cost,String retailername,String retailerzip,String retailercity,String retailerstate,
								 String productonsale,String manufacturername,String manufacturerrebate,int userage,String gender,String occupation) {
  HashMap < String, ArrayList < Review >> reviews = new HashMap < String, ArrayList < Review >> ();
  try {
   reviews = MongoDBDataStoreUtilities.selectReview();
  } catch (Exception e) {}
  if (!reviews.containsKey(productname)) {
   ArrayList < Review > arr = new ArrayList < Review > ();
   reviews.put(productname, arr);
  }
  ArrayList < Review > listReview = reviews.get(productname);
  Review review = new Review(productname, userid, producttype, reviewrating, reviewdate, reviewtext);
  listReview.add(review);
  try {
   MongoDBDataStoreUtilities.insertReview(productname, userid, producttype, reviewrating, reviewdate, reviewtext,brand,cost,retailername,retailerzip,retailercity,retailerstate,productonsale,manufacturername,manufacturerrebate,userage,gender,occupation);
  } catch (Exception e) {}
 }


 public void doGet(HttpServletRequest request,
  HttpServletResponse response)
 throws ServletException, IOException {
  String checkoutURL, us;

  
  
  HttpSession session = request.getSession();
  ShoppingCart cart;
  synchronized(session) {
int ratings=Integer.parseInt(request.getParameter("ratings"));
String reviewtext=request.getParameter("reviewtext"); 
String reviewdate=request.getParameter("date");
String productname=request.getParameter("productname");  
String producttype=request.getParameter("producttype"); 

String brand=request.getParameter("brand");   
double cost= Double.parseDouble(request.getParameter("cost"));   
String retailername=request.getParameter("retailername");   
String retailerzip=request.getParameter("retailerzip");   
String retailercity=request.getParameter("retailercity");  
String retailerstate=request.getParameter("retailerstate");
String productonsale=request.getParameter("productonsale");
String manufacturername=request.getParameter("manufacturername");
String manufacturerrebate=request.getParameter("manufacturerrebate"); 
int userage=Integer.parseInt(request.getParameter("userage"));
String gender=request.getParameter("gender"); 
String occupation=request.getParameter("occupation"); 

  userid = (String) session.getAttribute("userid");
   cart = (ShoppingCart) session.getAttribute("shoppingCart");
   if (userid == null) {
    us = "Login";
   } else {
    us = "Hello, " + userid;
   }


   // New visitors get a fresh shopping cart.
   // Previous visitors keep using their existing cart.
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

   storeReview(productname, producttype, ratings, reviewdate, reviewtext,brand,cost,retailername,retailerzip,retailercity,retailerstate,productonsale,manufacturername,manufacturerrebate,userage,gender,occupation);
   
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   Date date = new Date();
   
   
   String title = "Save Review";
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
   out.println("We have received your review.Thank you..");
   out.println("</BODY></HTML>");
  }


 }
}