package com.ruoyi.common.utils.pdf;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;

import java.io.*;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilePdfUtils {

    /**
     * 转换文件成pdf
     *
     * @param fromFileInputStream:
     * @throws IOException
     */
    public static String file2pdf(InputStream fromFileInputStream, String type,String pdfPath) throws IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeSuffix = sdf.format(date);
        String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            docFileName = "doc_" + timeSuffix + ".doc";
            htmFileName = "doc_" + timeSuffix + ".pdf";
        }else if("docx".equals(type)){
            docFileName = "docx_" + timeSuffix + ".docx";
            htmFileName = "docx_" + timeSuffix + ".pdf";
        }else if("xls".equals(type)){
            docFileName = "xls_" + timeSuffix + ".xls";
            htmFileName = "xls_" + timeSuffix + ".pdf";
        }else if("ppt".equals(type)){
            docFileName = "ppt_" + timeSuffix + ".ppt";
            htmFileName = "ppt_" + timeSuffix + ".pdf";
        }else if("xlsx".equals(type)){
            docFileName = "xlsx_" + timeSuffix + ".xlsx";
            htmFileName = "xlsx_" + timeSuffix + ".pdf";
        }else if("pptx".equals(type)){
            docFileName = "pptx_" + timeSuffix + ".pptx";
            htmFileName = "pptx_" + timeSuffix + ".pdf";
        }
        else if("pdf".equals(type)){
            docFileName = "pdf_" + timeSuffix + ".pdf";
            htmFileName = "pdf_" + timeSuffix + ".pdf";
        }else{
            return null;
        }


        check_folder(pdfPath);

        File htmlOutputFile = new File(pdfPath + File.separatorChar + htmFileName);
        File docInputFile = new File(pdfPath + File.separatorChar + docFileName);
        if (htmlOutputFile.exists())
            htmlOutputFile.delete();
        htmlOutputFile.createNewFile();
        if (docInputFile.exists())
            docInputFile.delete();
        docInputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
        try {
            OutputStream os = new FileOutputStream(docInputFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.close();
            fromFileInputStream.close();
        } catch (IOException e) {
        }

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {
            connection.connect();
        } catch (ConnectException e) {
            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
            return "文件转换出错，请检查OpenOffice服务是否启动。";
        }

        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(docInputFile, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
        docInputFile.delete();
        System.out.println(htmFileName);
        return htmFileName;
    }

    public static void check_folder(String path) {
        File dir = new File(path);
        // 判断文件夹是否存在
        if (dir.isDirectory()) {
        } else {
            dir.mkdirs();
        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);//获取旧的文件File对象
        File file = new File(newPath + oldFile.separator + StringUtils.substringAfterLast(oldPath, "/"));  //获取新的文件File对象并生成文件
        FileInputStream in = new FileInputStream(oldFile);  //
        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer=new byte[2097152];
        int readByte = 0;
        //读取旧文件的流写入新文件里
        while((readByte = in.read(buffer)) != -1){
            out.write(buffer, 0, readByte);
        }

        in.close();
        out.close();
    }

}
