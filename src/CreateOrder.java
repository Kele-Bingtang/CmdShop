import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.util.Map;


public class CreateOrder {
    //Excel 文件要存放的位置，假定在F盘下

    public static String outputFile = "F:\\order.xls";

    public static void createOrder(Order order) throws Exception {

        try {

            // 创建新的Excel 工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 在Excel工作簿中建一工作表，其名为缺省值
            // 如要新建一名为"效益指标"的工作表，其语句为：
            HSSFSheet sheet = workbook.createSheet("订单");

            CellStyle style = workbook.createCellStyle();
            //关键点 IndexedColors.AQUA.getIndex() 对应颜色
            style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            HSSFRow firstRow = sheet.createRow((short) 0);

            HSSFCell cell01 = firstRow.createCell((short) 0);
            HSSFCell cell02 = firstRow.createCell((short) 1);
            HSSFCell cell03 = firstRow.createCell((short) 2);
            HSSFCell cell04 = firstRow.createCell((short) 3);
            HSSFCell cell05 = firstRow.createCell((short) 4);
            HSSFCell cell06 = firstRow.createCell((short) 5);
            HSSFCell cell07 = firstRow.createCell((short) 6);

            cell01.setCellValue("用户");
            cell02.setCellValue("商品ID");
            cell03.setCellValue("商品名字");
            cell04.setCellValue("购买数量");
            cell05.setCellValue("商品总价");
            cell06.setCellValue("实付款");
            cell07.setCellValue("下单时间");

            for (int i = 0; i < order.getProduct().length; i++) {
                // 在索引0的位置创建行（最顶端的行）
                HSSFRow row = sheet.createRow((short) i + 1);
                String productId = order.getProduct()[i].getProductId();
                for (int j = 0; j < 7; j++) {
                    HSSFCell cell = row.createCell((short) j);
                    // 在单元格中输入一些内容
                    if (j == 0) {               //用户信息
                        cell.setCellValue(order.getUser().getUsername());
                        //cell.setCellStyle(style);//设置背景色
                    } else if (j == 1) {        //商品ID
                        cell.setCellValue(productId);
                    } else if (j == 2) {          //商品名字
                        cell.setCellValue(order.getProduct()[i].getProductName());
                    } else if (j == 3) {          //购买数量
                        Map<String,Integer> buyNum = order.getBuyNum();
                        cell.setCellValue(buyNum.get(productId));
                    } else if (j == 4) {          //商品总价
                        cell.setCellValue(order.getTotalPrice());
                    } else if (j == 5) {          //实付款
                        cell.setCellValue(order.getFinalPrice());
                    } else if (j == 6) {          //下单时间
                        cell.setCellValue(order.getOrderDate().toString());
                    }
                }
            }
            // 新建一输出文件流
            FileOutputStream fOut = new FileOutputStream(outputFile);
            // 把相应的Excel 工作簿存盘
            workbook.write(fOut);
            fOut.flush();
            // 操作结束，关闭文件
            fOut.close();
            System.out.println("文件生成...");
        } catch(Exception e){
            System.out.println("已运行 xlCreate() : " + e);
        }
    }
}