import java.util.HashMap;
import java.util.List;
import java.util.Date;


public class OrderPayment implements java.io.Serializable{
    
    private  int orderId;
	 private String orderName;
    private  double orderPrice;
    private String userAddress;
    private  String creditCardNo;
	private String date;

    public OrderPayment(int orderId, String userName, String orderName, double orderPrice, String userAddress, String creditCardNo,String date) { // , String date
        this.orderId = orderId;
        this.userName = userName;
        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.userAddress = userAddress;
        this.creditCardNo = creditCardNo;
		this.date=date;
    }
	  public OrderPayment(int orderId, String userName, String orderName, double orderPrice, String userAddress, String creditCardNo) { // , String date
        this.orderId = orderId;
        this.userName = userName;
        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.userAddress = userAddress;
        this.creditCardNo = creditCardNo;
    }


	
	public OrderPayment(int orderId,String userName,String orderName)
	{
		this.orderId = orderId;
        this.userName = userName;
        this.orderName = orderName;
	}
	   public OrderPayment(int orderId, String userName, String orderName, double orderPrice, String userAddress) {
        this.orderId = orderId;
        this.userName = userName;
        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.userAddress = userAddress;
        
    }
	
    private String userName;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }
public String getDate() { 
return date; 
}

    public void setDate(String date) { 
	this.date = date; 
	}
   
}