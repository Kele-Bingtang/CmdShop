import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class ReadProductExcel {
    int firstNum = 0;       //第一个商品数量
    int secondNum = 0;      //第二个商品数量
    public Product[] getAllProducts(InputStream file) {
        Product products[] = null;
        try {
            XSSFWorkbook xw = new XSSFWorkbook(file);
            XSSFSheet xs = xw.getSheetAt(0);
            products = new Product[xs.getLastRowNum()];
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
                Product product = new Product();//每循环一次就把电子表格的一行的数据给对象赋值
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell == null)
                        continue;
                    if (k == 0) {
                        product.setProductId(this.getValue(cell));//给username属性赋值
                    } else if (k == 1) {
                        product.setProductName(this.getValue(cell));//给password属性赋值
                    } else if (k == 2) {
                        String str = this.getValue(cell);
                        int value = Integer.parseInt(str);
                        product.setProductPrice(value);//给address属性赋值
                    } else if (k == 3) {
                        product.setProductdesc(this.getValue(cell));//给phone属性赋值
                    }
                }
                products[j-1] = product;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductByID(String id,InputStream in) {

        Product products[] = null;
        try {
            XSSFWorkbook xw = new XSSFWorkbook(in);
            XSSFSheet xs = xw.getSheetAt(0);
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
                Product product = new Product();//每循环一次就把电子表格的一行的数据给对象赋值
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell == null)
                        continue;
                    if (k == 0) {
                        product.setProductId(this.getValue(cell));//给productId属性赋值
                    } else if (k == 1) {
                        product.setProductName(this.getValue(cell));//给productName属性赋值
                    } else if (k == 2) {
                        String str = this.getValue(cell);
                        int value = Integer.parseInt(str);
                        product.setProductPrice(value);//给productPrice属性赋值
                    } else if (k == 3) {
                        product.setProductdesc(this.getValue(cell));//给productdesc属性赋值
                    }
                }
                if(id.equals(product.getProductId())){
                    /*try {
                        if(id.equals(getFirstProductID(in))){
                            firstNum++;
                            product.setNum(firstNum);
                        }else if(id.equals(getSecondProductID(in))){
                            secondNum++;
                            product.setNum(secondNum);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    return product;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFirstProductID(InputStream in) throws Exception{
        XSSFWorkbook xw = new XSSFWorkbook(in);
        XSSFSheet xs = xw.getSheetAt(0);
        XSSFRow row = xs.getRow(1);
        XSSFCell cell = row.getCell(0);
        String id = this.getValue(cell);//给productId属性赋值

        return id;
    }

    public String getSecondProductID(InputStream in) throws Exception{
        XSSFWorkbook xw = new XSSFWorkbook(in);
        XSSFSheet xs = xw.getSheetAt(0);
        XSSFRow row = xs.getRow(1);
        XSSFCell cell = row.getCell(0);
        String id = this.getValue(cell);//给productId属性赋值

        return id;
    }

    private String getValue(XSSFCell cell) {
        String value;
        CellType type = cell.getCellType();
        switch (type) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BLANK:
                value = "";
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            case NUMERIC:
                DecimalFormat df=new DecimalFormat("#");
                value=df.format(cell.getNumericCellValue());
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case ERROR:
                value = "非法字符";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }
}