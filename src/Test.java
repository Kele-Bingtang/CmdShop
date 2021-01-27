import java.io.InputStream;
import java.util.*;

public class Test {
    static Scanner sc;
    static boolean isExits = true;
    InputStream inUser;
    InputStream inProduct = null;

    ReadProductExcel readProductExcel = null;
    Product[] carts = new Product[3];
    static int count = 0;  //购物车内容

    static int buyProductNum;

    static boolean isLogin = false;    //判断是否游客登陆

    static Map<String, Integer> buyNum = new HashMap<>();   //加入购买数量

    public static void main(String[] args) throws Exception{
        Test test = new Test();
        User oneUser = test.login();       //登录,获取User信息
        while(oneUser == null && isLogin){     //如果密码错误，则重新输入
            System.out.println("用户名或密码错误,请重新输入用户名和密码！");
            oneUser = test.login();
        }
        if (!isLogin) {
            System.out.println("您以游客登陆，仅可以查看商品，无法进行操作");
            System.out.println();
        }
        test.lookProducts();    //查询商品


        if(isLogin){        //用户名登陆
            Product[] buyProducts = test.addCarts();    //获得购物车信息
            while(isExits){
                int choose = test.chooseBuyAndPush();   //进行商品和购物车选择

                if(choose == 1){        //1、把想要的商品加入购物车
                    buyProducts = test.addCarts();
                }
                else if(choose == 2){       // 2、查询购物车内容
                    test.lookCarts();
                }else if(choose == 3){      // 3、结账购物车内的商品

                    /*
                     * 加入用户信息和购物车信息
                     */
                    Order order = new Order();
                    if(oneUser != null && buyProducts != null){ //
                        order.setUser(oneUser);
                        order.setProduct(buyProducts);
                    }


                    for (Product buyProduct : buyProducts) {
                        /*
                        加入购买商品数量
                         */
                        order.setBuyNum(buyNum);
                        /*
                         * 加入购买商品总金额和实付款金额
                         */
                        order.setTotalPrice(buyProduct.getProductPrice());
                        order.setFinalPrice(buyProduct.getProductPrice());       //假设没有优惠券，实际付款
                        //应该赋值完马上存，不然会覆盖原来的
                    }
                    /*
                     * 加入日期
                     */
                    order.setOrderDate(new Date());

                    System.out.println("用户信息：" + order.getUser());
                    System.out.println("购物车信息：" + Arrays.toString(order.getProduct()));
                    System.out.println("提交总金额：" + order.getTotalPrice());
                    System.out.println("购买日期：" + order.getOrderDate());

                    //下订单
                    CreateOrder.createOrder(order);

                }
                else if(choose == 4){
                    isExits = false;
                    System.exit(0);
                }

            }
        }

    }

    /**
     * 登录界面
     * @return 用户信息
     */
    public User login() throws Exception {
        sc = new Scanner(System.in);

        System.out.println("游客身份登陆请按 0 ，用户名密码登陆请按 1 ");

        int freeOrUser = sc.nextInt();
        System.out.println();
        if(freeOrUser == 1){
            isLogin = true;
            System.out.println("请输入用户名：");

            sc = new Scanner(System.in);
            String username = sc.next();//阻塞方法
            System.out.println("你输入的用户名：" + username);

            System.out.println("请输入密码：");
            String password = sc.next();

            System.out.println("你输入的密码是：" + password);

            //File file=new File("C:\\Users\\Administrator\\IdeaProjects\\ConsoleShop\\src\\users.xlsx");
            inUser = Class.forName("Test").getResourceAsStream("/users.xlsx");//表示的就是classpath
            ReadUserExcel readUserExcel = new ReadUserExcel();//创建对象
            User[] users = readUserExcel.readExcel(inUser);

            for (User user : users) {
                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    System.out.println("登录成功");

                    return user;
                } else {
                    System.out.println("登陆失败");
                }
            }
        }else if(freeOrUser == 0){
            isLogin = false;
        }
        else {
            System.out.println("请正确根据提示输入！");
            login();
        }
        return null;
    }

    /**
     * 商品内容界面
     */
    public void lookProducts() throws Exception{
        inProduct = Class.forName("Test").getResourceAsStream("/product.xlsx");//表示的就是classpath
        readProductExcel = new ReadProductExcel();//创建对象
        Product[] products = readProductExcel.getAllProducts(inProduct);

        System.out.println("商品内容：");
        for(Product p : products){
            System.out.println("Id：" + p.getProductId());
            System.out.println("名称：" + p.getProductName());
            System.out.println("价格：" + p.getProductPrice());
            System.out.println("描述：" + p.getProductdesc());
            System.out.println();
        }
    }

    /**
     * 选择界面
     * @return 选择
     */
    public int chooseBuyAndPush(){
        System.out.println("请输入你要查询的功能：\n1、把想要的商品加入购物车\n2、查询购物车内容\n3、结账购物车内的商品\n4、退出");
        return sc.nextInt();
    }

    /**
     * 加入商品到购物车
     * 导出购物车内容，以便结账
     * @return 购物车内容
     */
    public Product[] addCarts() throws Exception{
            System.out.println("请输入商品ID把该商品加入购物车");
            /*
            以【商品id,购买数量】为模板
             */
            String idAndBuyNum = sc.next();
            String[] idAndBuyNums = idAndBuyNum.split(",");
            String pId = idAndBuyNums[0];
            buyProductNum = Integer.parseInt(idAndBuyNums[1]);   //购买商品数量（String转换为int）

            inProduct = null;
            inProduct = Class.forName("Test").getResourceAsStream("/product.xlsx");//表示的就是classpath
            Product product = readProductExcel.getProductByID(pId,inProduct);
            System.out.println("要购买的商品价格：" + product.getProductPrice());
            carts[count++] = product;
            System.out.println("加入商品成功！");
            if(count >= 3){
                    System.out.println("购物车容量超出！");
            }

        /*
         * Carts容量为3，不一定装满，如果为2，那么剩下的1为null，引发错误，所以需要重新创建一个Product存储
         */
        Product[] productCart = new Product[count];

            for(int i =0;i < count;i++){
                if(carts != null){
                    productCart[i] = carts[i];

                    buyNum.put(pId,buyProductNum);  //把商品ID和商品数量放入Map
                }
            }

            return productCart;
        }

    /**
     * 查询购物车商品内容
     */
    public void lookCarts(){
        for(Product c : carts){
            if(c != null){
                System.out.println("购物车内容：");
                System.out.println("Id：" + c.getProductId());
                System.out.println("名称：" + c.getProductName());
                System.out.println("价格：" + c.getProductPrice());
                System.out.println("描述：" + c.getProductdesc());
                break;
            }else{
                System.out.println("购物车为空");
                break;
            }
        }
    }

}