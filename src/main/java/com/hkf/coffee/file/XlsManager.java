package com.hkf.coffee.file;

import android.content.Context;

import com.hkf.coffee.file.base.XlsEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class XlsManager {
    /**
     * 读取指定目录xls文件的第一个表格的内容
     * @param context
     * @param path
     * @return
     */
    public static XlsEntry readXlsData(Context context, String path){
        return readXlsData(context,path,0);
    }

    /**
     * 读取指定目录xls文件的指定表格的内容
     * @param context
     * @param path
     * @param num  表格编号
     * @return
     */
    public static XlsEntry readXlsData(Context context, String path,int num){
        if(!".xls".equals(path.substring(path.length()-4,path.length()))){
            return null;
        }
        XlsEntry xe=null;
        try {
            xe=new XlsEntry();
            InputStream instream =new FileInputStream(path);
            Workbook readwb = Workbook.getWorkbook(instream);
            Sheet readsheet = readwb.getSheet(num);
            int rsRows=readsheet.getRows();
            int rsCols=readsheet.getColumns();
            for(int i=0;i<rsRows;i++){
                String[] temp=new String[rsCols];
                for(int j=0;j<rsCols;j++){
                    temp[j]=readsheet.getCell(j,i).getContents();
                }
                xe.getData().add(temp);
            }
            instream.close();
        } catch (Exception e) {
            return null;
        }
        return xe;
    }

    /**
     * 向指定目录写入xls文件及内容
     * @param context
     * @param path
     * @param isOverWrite  是否覆盖
     * @param entry
     * @return
     */
    public static boolean writeXlsData(Context context,String path,boolean isOverWrite,XlsEntry entry){
        return writeXlsData(context,path,isOverWrite,entry,0,"默认表");
    }

    /**
     * 向指定目录写入xls文件及内容
     * @param context
     * @param path
     * @param isOverWrite
     * @param entry
     * @param num  表格编号
     * @return
     */
    public static boolean writeXlsData(Context context,String path,boolean isOverWrite,XlsEntry entry,int num){
        return writeXlsData(context,path,isOverWrite,entry,num,"默认表");
    }

    /**
     * 向指定目录写入xls文件及内容
     * @param context
     * @param path
     * @param isOverWrite
     * @param entry
     * @param num    表格编号
     * @param tableName 默认表格名称
     * @return
     */
    public static boolean writeXlsData(Context context,String path,boolean isOverWrite,XlsEntry entry,int num,String tableName){
        Workbook workbook=null;
        WritableWorkbook wwb=null;
        WritableSheet sheet=null;
        int rsRows=0;
        try {
            File file=new File(path);
            boolean isNotNull= !file.exists();
            if(isNotNull){
                //获取excel文件
                workbook = Workbook.getWorkbook(new File(path));
                //打开文件的副本，并且制定数据写回到原文件
                wwb = Workbook.createWorkbook(new File(path), workbook);
                //修改工作表的数据
                sheet = wwb.getSheet(num);
            }else{
                //创建文件，并写入内容
                wwb=Workbook.createWorkbook(new FileOutputStream(path));
                sheet = wwb.createSheet(tableName, num); //默认设置xls的第一张表格名称为默认表   仅对此表操作
            }
            if(isNotNull&&!isOverWrite)rsRows=sheet.getRows();
            for(int i=0;i<entry.getData().size();i++){
                for(int j=0;j<entry.getData().get(i).length;j++){
                    Label label = new Label(j,i+rsRows,entry.getData().get(i)[j]);   //字符串
                    //jxl.write.Number number = new jxl.write.Number(1, 0, 123);   //数字
                    sheet.addCell(label);
                }
            }
            wwb.write();
            wwb.close();
            if(isNotNull&&workbook!=null){
               workbook.close();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 读取指定assets中的xls文件内容
     * @param context
     * @param name  文件名称
     * @return
     */
    public static XlsEntry readXlsDataFromAssets(Context context, String name){
        return readXlsDataFromAssets(context,name,0);
    }

    /**
     * 读取指定assets中的xls文件内容
     * @param context
     * @param name  文件名称
     * @param num  表格编号
     * @return
     */
    public static XlsEntry readXlsDataFromAssets(Context context, String name,int num){
        XlsEntry xe=null;
        try {
            xe=new XlsEntry();
            InputStream instream =context.getAssets().open(name);
            Workbook readwb = Workbook.getWorkbook(instream);
            Sheet readsheet = readwb.getSheet(num);
            int rsRows=readsheet.getRows();
            int rsCols=readsheet.getColumns();
            for(int i=0;i<rsRows;i++){
                String[] temp=new String[rsCols];
                for(int j=0;j<rsCols;j++){
                    temp[j]=readsheet.getCell(j,i).getContents();
                }
                xe.getData().add(temp);
            }
            instream.close();
        } catch (Exception e) {
            return null;
        }
        return xe;
    }
}
