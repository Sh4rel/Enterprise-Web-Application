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
import java.util.HashMap;
import java.util.*;

public class MongoDBDataStoreUtilities {
 static DBCollection myReviews;
 public static void getConnection() {
  MongoClient mongo;
  mongo = new MongoClient("localhost", 27017);
  DB db = mongo.getDB("CustomerReviews");
  myReviews = db.getCollection("myReviews");
 }


 //selecting review data in hashmap
 public static HashMap < String, ArrayList < Review >> selectReview() {
  getConnection();
  HashMap < String, ArrayList < Review >> reviewHashmap = new HashMap < String, ArrayList < Review >> ();
  DBCursor cursor = myReviews.find();
  while (cursor.hasNext()) {
   BasicDBObject obj = (BasicDBObject) cursor.next();
   if (!reviewHashmap.containsKey(obj.getString("productName"))) {
    ArrayList < Review > arr = new ArrayList < Review > ();
    reviewHashmap.put(obj.getString("productName"), arr);
   }
   ArrayList < Review > listReview = reviewHashmap.get(obj.getString("productName"));
  // String age=obj.getString("userage");
   //int age1=Integer.parseInt(age);
   
// Review review = new Review("iPhone","Ross","Cellphone",Integer.parseInt(obj.getString("reviewRating")), "15-08-2016","Best buy deal");
   // Review review = new Review(obj.getString("productName"),obj.getString("userName"),obj.getString("productType"),5, obj.getString("reviewDate"),obj.getString("reviewText"));
   Review review = new Review(obj.getString("productName"), obj.getString("userName"), obj.getString("productType"), Integer.parseInt(obj.getString("reviewRating")), obj.getString("reviewDate"), obj.getString("reviewText"),
                               obj.getString("brand"),Double.parseDouble(obj.getString("reviewRating")) , obj.getString("retailername"), obj.getString("retailerzip"), obj.getString("retailercity"), 
							   obj.getString("retailerstate"), obj.getString("productonsale"),
							   obj.getString("manufacturername"), obj.getString("manufacturerrebate"), Integer.parseInt(obj.getString("reviewRating")), 
							   obj.getString("gender"), obj.getString("occupation")); 
   listReview.add(review);
  }
  return reviewHashmap;
 }

 //Writing reviews to db
 public static void insertReview(String productname, String username, String producttype, int reviewrating, String reviewdate, String reviewtext,
								 String brand,double cost,String retailername,String retailerzip,String retailercity,String retailerstate,
								 String productonsale,String manufacturername,String manufacturerrebate,int userage,String gender,String occupation) {
  getConnection();
  BasicDBObject doc = new BasicDBObject("title", "myReviews").
  append("userName", username).
  append("productName", productname).
  append("productType", producttype).
  append("reviewRating", reviewrating).
  append("reviewDate", reviewdate).
  append("reviewText", reviewtext).
  append("brand", brand).
  append("cost", cost).
  append("retailername", retailername).
  append("retailerzip", retailerzip).
  append("retailercity", retailercity).
  append("retailerstate", retailerstate).
  append("productonsale", productonsale).
  append("manufacturername", manufacturername).
  append("manufacturerrebate", manufacturerrebate).
  append("userage", userage).
  append("gender", gender).
  append("occupation", occupation);
  myReviews.insert(doc);
 }


}