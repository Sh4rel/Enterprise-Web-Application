public class Review {

    private String productname;
    private String username;
    private String producttype;
    private int reviewrating;
    private String reviewdate;
    private  String reviewtext;
    //new columns
    private String brand;
    private double cost;
    private String retailername;
    private String retailerzip;
    private String retailercity;
    private String retailerstate;
private String productonsale;

    public Review(String productname, String username, String producttype, int reviewrating, String reviewdate, String reviewtext, String brand, double cost, String retailername, String retailerzip, String retailercity, String retailerstate, String productonsale, String manufacturername, String manufacturerrebate, int userage, String gender, String occupation) {
        this.productname = productname;
        this.username = username;
        this.producttype = producttype;
        this.reviewrating = reviewrating;
        this.reviewdate = reviewdate;
        this.reviewtext = reviewtext;
        this.brand = brand;
        this.cost = cost;
        this.retailername = retailername;
        this.retailerzip = retailerzip;
        this.retailercity = retailercity;
        this.retailerstate = retailerstate;
        this.productonsale = productonsale;
        this.manufacturername = manufacturername;
        this.manufacturerrebate = manufacturerrebate;
        this.userage = userage;
        this.gender = gender;
        this.occupation = occupation;
    }

    

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRetailername() {
        return retailername;
    }

    public void setRetailername(String retailername) {
        this.retailername = retailername;
    }

    public String getRetailerzip() {
        return retailerzip;
    }

    public void setRetailerzip(String retailerzip) {
        this.retailerzip = retailerzip;
    }

    public String getRetailercity() {
        return retailercity;
    }

    public void setRetailercity(String retailercity) {
        this.retailercity = retailercity;
    }

    public String getRetailerstate() {
        return retailerstate;
    }

    public void setRetailerstate(String retailerstate) {
        this.retailerstate = retailerstate;
    }

    public String getProductonsale() {
        return productonsale;
    }

    public void setProductonsale(String productonsale) {
        this.productonsale = productonsale;
    }

    public String getManufacturername() {
        return manufacturername;
    }

    public void setManufacturername(String manufacturername) {
        this.manufacturername = manufacturername;
    }

    public String getManufacturerrebate() {
        return manufacturerrebate;
    }

    public void setManufacturerrebate(String manufacturerrebate) {
        this.manufacturerrebate = manufacturerrebate;
    }

    public int getUserage() {
        return userage;
    }

    public void setUserage(int userage) {
        this.userage = userage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    private String manufacturername;
    private String manufacturerrebate;
    private int userage;
    private String gender;
    private String occupation;


    public Review(String productname, String username, String producttype,int reviewrating, String reviewdate, String reviewtext) { 
        this.productname = productname;
        this.username = username;
        this.producttype = producttype;
      this.reviewrating = reviewrating;
        this.reviewdate = reviewdate;
        this.reviewtext = reviewtext;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public int getReviewrating() {
        return reviewrating;
    }

    public void setReviewrating(int reviewrating) {
        this.reviewrating = reviewrating;
    }

    public String getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(String reviewdate) {
        this.reviewdate = reviewdate;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }

}




