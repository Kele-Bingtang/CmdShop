import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Order {

    private User user;  //用户信息
    private Product[] products;    //商品信息
    private Map<String,Integer> buyNum;         //购买数量
    private Map<String,Float> productPrice;     //商品单价
    private Map<String,Float> totalPrice;   //交付金额
    private Map<String, SimpleDateFormat> orderDate; //购买日期


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product[] getProduct() {
        return products;
    }

    public void setProduct(Product[] product) {
        this.products = product;
    }

    public Map<String, Integer> getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Map<String, Integer> buyNum) {
        this.buyNum = buyNum;
    }

    public Map<String, Float> getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Map<String, Float> productPrice) {
        this.productPrice = productPrice;
    }

    public Map<String, Float> getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Map<String, Float> totalPrice) {
        this.totalPrice = totalPrice;
    }


    public Map<String, SimpleDateFormat> getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Map<String, SimpleDateFormat> orderDate) {
        this.orderDate = orderDate;
    }
}
