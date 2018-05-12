package com.em.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelIO {
    public static ExcelIO EXIO = new ExcelIO();

    // 导出
    public void outExcel(String file, String sheetValue, String[][] str) {
        sheetValue = sheetValue.isEmpty() ? "sheet" : sheetValue;
        // webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建sheet，对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetValue);
        // 添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        // 设置格式为文本
        style.setDataFormat(wb.createDataFormat().getFormat("@"));
        for (int i = 0; i < str.length; i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < str[i].length; j++) {
                sheet.setDefaultColumnStyle(j, style);
                row.createCell(j).setCellValue(str[i][j]);
                sheet.autoSizeColumn((short) j);
                sheet.setColumnWidth(j, sheet.getColumnWidth(j) + 2048);
            }
        }
        // 保存
        try {
            FileOutputStream fout = new FileOutputStream(file);
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(String file) {
        new File(file).delete();
    }

    // 导入
    public String[][] impExcel(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        String[][] str = null;
        if (file.getName().endsWith(".xlsx")) {
            str = readExcel2007(is);
        } else {
            str = readExcel2003(is);
        }
        
        is.close();
        file.delete();//立即删除
        
        return str;
    }
    
    private String[][] readExcel2003(InputStream is) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 工作表Sheet
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        String[][] str = new String[hssfSheet.getLastRowNum()][hssfSheet.getRow(0).getLastCellNum()];
        // 循环行Row
        for (int rowNum = 0; rowNum < hssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum + 1);
            if (hssfRow == null)
                continue;
            // 循环列Cell
            for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
                if (Util.isEmptyString(hssfRow.getCell(i) + ""))
                    continue;
                HSSFCell cell = hssfRow.getCell(i);
                if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        str[rowNum][i] = Util.getFormatDate(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        str[rowNum][i] = cell.getStringCellValue();
                    }
                } else {
                    str[rowNum][i] = cell.getStringCellValue();
                }
            }
        }
        return str;
    }
    
    private String[][] readExcel2007(InputStream is) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        // 工作表Sheet
        XSSFSheet sheet = workbook.getSheetAt(0);
        String[][] str = new String[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        // 循环行Row
        for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum + 1);
            if (row == null)
                continue;
            // 循环列Cell
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (Util.isEmptyString(row.getCell(i) + ""))
                    continue;
                XSSFCell cell = row.getCell(i);
                if (cell.getCellType() != HSSFCell.CELL_TYPE_STRING) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        str[rowNum][i] = Util.getFormatDate(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        str[rowNum][i] = cell.getStringCellValue();
                    }
                } else {
                    str[rowNum][i] = cell.getStringCellValue();
                }
            }
        }
        return str;
    }
    
    public String getRom() {
        return new Random().nextInt() + "";
    }

    public String subZeroAndDot(String s) {
        if (Util.isEmptyString(s))
            return s;
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 合并数组
     * 
     * @param first
     * @param second
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null)
            return second;
        if (second == null)
            return first;
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 清除重复
     * 
     * @param s
     * @return
     */
    public static <T> T[] cleanRep(T[] s) {
        List<T> list = Arrays.asList(s);
        HashSet<T> h = new HashSet<T>();
        h.addAll(list);
        return h.toArray(s);
    }
    
    public static <T> List<T> cleanList(List<T> s) {
        HashSet<T> h = new HashSet<T>();
        h.addAll(s);
        s.removeAll(s);
        s.addAll(h);
        return s;
    }

    public static <T> boolean eq(T[] a, T[] b) {
        if (!(a != null && b != null))
            return false;
        if (a.length != b.length)
            return false;
        int f = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i].equals(b[j]))
                    f++;
            }
            if (f != 1)
                return false;
            f = 0;
        }
        return true;
    }
    
    public static String getImagePath(File file) {
        String fileName = file.getName();
        String result = System.currentTimeMillis() + "" + (int)(1000 + Math.random() * (9999 - 1000 + 1)) + fileName.substring(fileName.indexOf("."));
        /*boolean newFile = */file.renameTo(new File(file.getPath().replace(fileName, "") + result));
        return result;
    }

    public static <T> int sel(T[] a, Object value) {
        if (a == null)
            return -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public static String delAppoint(String val, Object text) {
        String[] t = val.split(",");
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals(text))
                t[i] = null;
        }
        String temp = "";
        for (int i = 0; i < t.length; i++) {
            if (t[i] != null)
                temp += t[i] + ",";
        }
        return temp.substring(0, temp.lastIndexOf(","));
    }

    /*
     * public static void main(String[] args) { try { File file = new
     * File("c:1.txt"); InputStreamReader read = new InputStreamReader(new
     * FileInputStream(file),"gbk"); BufferedReader buf = new
     * BufferedReader(read); String temptxt = buf.readLine(); String lineTxt =
     * ""; while(temptxt != null) { if(Util.isEmptyString(temptxt)) {temptxt =
     * buf.readLine(); continue;} lineTxt+=temptxt+"\n"; temptxt =
     * buf.readLine(); }
     * 
     * String[] t = lineTxt.split("\n"); System.out.println(t.length); String[]
     * temp = null; for (int i = 0; i < t.length; i++) { temp =
     * t[i].split("----"); if(temp[0].length()<10)
     * System.out.println(temp[0]+"\t"+temp[1]); } } catch
     * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); } catch (FileNotFoundException e) { // TODO
     * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
     * // TODO Auto-generated catch block e.printStackTrace(); }
     * 
     * }
     */
}
