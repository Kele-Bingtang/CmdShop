import java.io.InputStream;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws Exception{
        boolean bool = true;
        Product[] carts = new Product[3];;
        while(bool){
            System.out.println("请输入用户名：");

            Scanner sc = new Scanner(System.in);
            String username = sc.next();//阻塞方法
            System.out.println("你输入的用户名："+username);

            System.out.println("请输入密码：");
            String password = sc.next();

            System.out.println("你输入的密码是：" + password);

            //File file=new File("C:\\Users\\Administrator\\IdeaProjects\\ConsoleShop\\src\\users.xlsx");
            InputStream inUser = Class.forName("Test").getResourceAsStream("/users.xlsx");//表示的就是classpath
            ReadUserExcel readUserExcel = new ReadUserExcel();//创建对象
            User users[] = readUserExcel.readExcel(inUser);



            for(int i=0;i<users.length;i++){
                if(username.equals(users[i].getUsername()) && password.equals(users[i].getPassword())){
                    System.out.println("登录成功");

                    InputStream inProduct = Class.forName("Test").getResourceAsStream("/product.xlsx");//表示的就是classpath
                    ReadProductExcel readProductExcel = new ReadProductExcel();//创建对象
                    Product products[] = readProductExcel.getAllProducts(inProduct);

                    System.out.println("商品内容：");
                    for(Product p : products){
                        System.out.println("Id：" + p.getProductId());
                        System.out.println("名称：" + p.getProductName());
                        System.out.println("价格：" + p.getProductPrice());
                        System.out.println("描述：" + p.getProductdesc());
                    }

                    System.out.println("请输入你要查询的功能：\n1、把商品加入购物车\n2、查询购物车内容");
                    int choose  = sc.nextInt();

                    if(choose == 1){
                        System.out.println("请输入商品ID把该商品加入购物车");
                        String pId = sc.next();
                        int count = 0;

                        inProduct = null;
                        inProduct = Class.forName("Test").getResourceAsStream("/product.xlsx");//表示的就是classpath
                        Product product = readProductExcel.getProductByID(pId,inProduct);
                        System.out.println("要购买的商品价格：" + product.getProductId());
                        if(product != null){
                            carts[count++] = product;
                            System.out.println("加入商品成功！");
                        }
                    }else if(choose == 2){
                        for(Product c : carts){
                            System.out.println("购物车内容：");
                            System.out.println("Id：" + c.getProductId());
                            System.out.println("名称：" + c.getProductName());
                            System.out.println("价格：" + c.getProductPrice());
                            System.out.println("描述：" + c.getProductdesc());
                        }
                    }





                    bool = false;
                    break;
                }else{
                    System.out.println("登录失败");
                }
            }
        }
    }
}