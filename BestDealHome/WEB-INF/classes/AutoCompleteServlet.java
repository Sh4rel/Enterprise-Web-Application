import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class AutoCompleteServlet  extends HttpServlet{
	    private ServletContext context;

	
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }
	

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String action = request.getParameter("action");
        String searchId = request.getParameter("searchId");
		

       
 try {
	 
  StringBuffer sb = new StringBuffer();
  boolean namesAdded = false;
  
  if (action.equals("complete")) {
	  
   if (!searchId.equals("")) {
	   
    AjaxUtility a = new AjaxUtility();
	 sb = a.readdata(searchId);
 
    if (sb != null || !sb.equals("")) {
				
     namesAdded = true;
    }
    if (namesAdded) {
	response.setContentType("text/xml");
     response.setCharacterEncoding("UTF-8");
     response.getWriter().write("<products>" + sb.toString() + "</products>");
 
    }
   }
 }
  
   if (action.equals("lookup")) {
            if ((searchId != null) ) {
	        context.getRequestDispatcher("/SearchDisplay").forward(request, response);
            }
        }
  
 }
 catch(Exception e){}
 }
}