import java.util.Date;

public class Order {

    private User user;  //用户信息
    private Product[] products;    //商品信息
    private int productAmmount; //商品价格
    private float totalPrice;   //交付金额
    private float finalDay;
    private Date orderDate; //购买日期

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

    public int getProductAmmount() {
        return productAmmount;
    }

    public void setProductAmmount(int productAmmount) {
        this.productAmmount = productAmmount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getFinalDay() {
        return finalDay;
    }

    public void setFinalDay(float finalDay) {
        this.finalDay = finalDay;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
