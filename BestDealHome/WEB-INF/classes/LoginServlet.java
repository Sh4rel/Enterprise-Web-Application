import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
 /** 
  * Initializes the servlet with some usernames/password
  */
 public void init() {
 }

 public void processRequest(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, java.io.IOException {


  String password = request.getParameter("password");
  String userid = request.getParameter("userid");
  String usertype = request.getParameter("usertype");
  HttpSession session = request.getSession();
  ShoppingCart cart;

  synchronized(session) {
 cart = (ShoppingCart)session.getAttribute("shoppingCart");
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
          } catch(NumberFormatException nfe) {
            numItems = 1;
          }
          cart.setNumOrdered(itemID, numItems);
        }
      }
	
	 
   session.setAttribute("userid", userid);
   session.setAttribute("usertype", usertype);


   if (userid != null && userid.length() != 0) {
    userid = userid.trim();
   }
   if (password != null && password.length() != 0) {
    password = password.trim();
   }
    if (usertype != null && usertype.length() != 0) {
    usertype = usertype.trim();
   }
   if (userid != null &&
    password != null) {

    //Get from sql to validate
    HashMap < String, User > hm = new HashMap < String, User > ();
    try {
     hm = MySqlDataStoreUtilities.selectUser();
    } catch (Exception e) {
     //e.PrintStacktrace();
    }
    if (hm.containsKey(userid)) {

     User u = hm.get(userid);
     String realpassword = (String) u.getPassword();


     if (usertype.equals("customer") && realpassword != null &&
      realpassword.equals(password)) 
     {
		 if(cart.getItemsOrdered().size() > 0 )
			 response.sendRedirect("http://localhost/BestDealHome/CheckoutServlet");
			 else
				 response.sendRedirect("http://localhost/BestDealHome/HomeServlet");
      
      //showPage(response, "Login Success!"+ userid);
     } else if (usertype.equals("storemanager") && realpassword != null &&
      realpassword.equals(password)) {

       response.sendRedirect("http://localhost/BestDealHome/AddProducts");     
	
	 // showPage(response, "You are Manager, you can Modify Store products!");

     } else /*Check if the cart is empty , if yes go to homepage*/
     if (usertype.equals("salesman") && realpassword != null &&
      realpassword.equals(password)) {

     response.sendRedirect("http://localhost/BestDealHome/CreateCustomerAccounts");     
	
     }

    }
    /*Check if the cart is empty , if yes go to homepage*/
    else {

     //response.sendRedirect("http://localhost/BestDealHome/LoginPageServlet");
     showPage(response, "Login Failure! Username or password is incorrect!");
    }
   }

   //}
   else {
    // response.sendRedirect("http://localhost/BestDealHome/LoginPageServlet");
    showPage(response, "Login Failure! Username or password is incorrect.");
   }
  }
 }

 /**
  * Actually shows the <code>HTML</code> result page
  */
 protected void showPage(HttpServletResponse response, String message)
 throws ServletException, java.io.IOException {
  response.setContentType("text/html");
  java.io.PrintWriter out = response.getWriter();
  out.println("<html>");
  out.println("<head>");
  out.println("<title>Login Servlet Result</title>");
  out.println("</head>");
  out.println("<body>");
  out.println("<h2>" + message + "</h2>");
  out.println("</body>");
  out.println("</html>");
  out.close();

 }



 protected void doGet(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, java.io.IOException {



  processRequest(request, response);
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, java.io.IOException {
  processRequest(request, response);
 }
}