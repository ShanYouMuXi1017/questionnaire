package com.ruoyi.web.controller.tool.excel;

import com.ruoyi.system.domain.QuestParam.AnswerParam;
import com.ruoyi.system.domain.QuestRouter;
import com.ruoyi.system.domain.QuestWritExcel;
import com.ruoyi.system.domain.QuestWritUserInfo;
import com.ruoyi.system.domain.enums.WritColumnRange;
import com.ruoyi.system.service.IQuestAnswerService;
import com.ruoyi.system.service.IQuestRouterService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class WritExcel {
    @Resource
    private  IQuestAnswerService questAnswerService;

    @Resource
    private  ISysUserService sysUserService;

    @Resource
    private IQuestRouterService questRouterService;

    public  XSSFWorkbook writeAnswerToExcel(Long routerId) throws IOException {
        //查询路线名称
        QuestRouter router = questRouterService.selectQuestRouterByRouterId(routerId);

        List<Integer> integers = questAnswerService.selectUserId(routerId);

        //保存每个用户对该条路线问卷的结果
        ArrayList<List<QuestWritExcel>> userAnswerLists = new ArrayList<>();
        for (Integer userId : integers) {
            AnswerParam answerParam = new AnswerParam();
            answerParam.setUserId(userId);
            answerParam.setRouterId(routerId);
            List<QuestWritExcel> questWritExcels = questAnswerService.selectQuestAnswerListExcel(answerParam);
            userAnswerLists.add(questWritExcels);
        }
//        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        System.out.println(userAnswerLists.toString());
        //写入文件
        String filePath = "excel/路线用户问卷汇总表.xlsx"; // 指定文件路径
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);

        //写入数据
        for (int i = 0; i < userAnswerLists.size(); i++) {
            List<QuestWritExcel> writExcels = userAnswerLists.get(i);
            for (int j = 0; j < writExcels.size(); j++) {
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                System.out.println(writExcels.get(j).getCellPosition()+"   "+writExcels.get(j).getAnswer());
                if(writExcels.get(j).getCellPosition() < 37){
                    setValue(sheet, i+3, writExcels.get(j).getCellPosition(), Double.parseDouble(writExcels.get(j).getAnswer()));
                }
                else {
                    setValue(sheet, i+3, writExcels.get(j).getCellPosition(), writExcels.get(j).getAnswer());
                }
            }
            //求和
            sumCells(sheet, i+3, WritColumnRange.RANGE1.getStartCol(), WritColumnRange.RANGE1.getEndCol(), WritColumnRange.RANGE1.getAimCol());
            sumCells(sheet, i+3,WritColumnRange.RANGE2.getStartCol(), WritColumnRange.RANGE2.getEndCol(), WritColumnRange.RANGE2.getAimCol());
            sumCells(sheet, i+3,WritColumnRange.RANGE3.getStartCol(), WritColumnRange.RANGE3.getEndCol(), WritColumnRange.RANGE3.getAimCol());
            sumCells(sheet, i+3,WritColumnRange.RANGE4.getStartCol(), WritColumnRange.RANGE4.getEndCol(), WritColumnRange.RANGE4.getAimCol());
            sumCells(sheet, i+3,WritColumnRange.RANGE5.getStartCol(), WritColumnRange.RANGE5.getEndCol(), WritColumnRange.RANGE5.getAimCol());

            //合计
            sumDiscreteCells(sheet,i+3,WritColumnRange.RANGE1.getAimCol(),WritColumnRange.RANGE2.getAimCol(),WritColumnRange.RANGE3.getAimCol(),WritColumnRange.RANGE4.getAimCol(),WritColumnRange.RANGE5.getAimCol(),WritColumnRange.RANGE5.getAimCol()+1);

            //用户基本信息
            QuestWritUserInfo userInfo = sysUserService.getQuestWritUserInfo(writExcels.get(0).getUserId());
            setValue(sheet, i+3, 0, userInfo.getUserId().doubleValue());
            if(userInfo.getSex() != null)
                setValue(sheet, i+3, 1, userInfo.getSex());
            if(userInfo.getAgeDuan() != null)
                setValue(sheet, i+3, 2, userInfo.getAgeDuan().doubleValue());
            if(userInfo.getRidingAge() != null)
                setValue(sheet, i+3, 3, userInfo.getRidingAge().doubleValue());

            //写入文件标题
            setValue(sheet, 0, 0, router.getRouteName() + "_路线用户问卷汇总表");
        }


        // 保存 Excel 文件
//        String outputFilePath = "D:\\Project\\wenjuan\\10_31\\questionnaire\\excel\\xxxx路线用户问卷汇总表test.xlsx"; // 指定保存路径
//        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {
//            workbook.write(fileOutputStream); // 把工作簿写入输出流
//        } catch (IOException e) {
//            e.printStackTrace(); // 异常处理
//        }
        // 关闭工作簿
//        workbook.close();
//        fileInputStream.close();

        return workbook;

    }

    /**
     * 求和单元格
     * @param sheet
     * @param row
     * @param startCol
     * @param endCol
     * @param aimCol
     */
    public  void sumCells(Sheet sheet, int row, int startCol, int endCol, int aimCol) {
        // 初始化求和结果
        double sum = 0;

        // 获取指定行
        Row targetRow = sheet.getRow(row);
        if (targetRow != null) {
            // 遍历指定列范围进行求和
            for (int col = startCol; col <= endCol; col++) {
                Cell cell = targetRow.getCell(col);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    sum += cell.getNumericCellValue(); // 累加当前单元格的数值
                }
            }
        }

        // 将求和结果写入到目标列
        Cell aimCell = targetRow.getCell(aimCol);
        if (aimCell == null) {
            aimCell = targetRow.createCell(aimCol);
        }
        aimCell.setCellValue(sum); // 写入求和结果

        // 创建新的单元格样式
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

        // 设置水平和垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row aimrow = sheet.getRow(row);
        Cell cell = aimrow.getCell(aimCol);
        // 将样式应用到当前单元格
        cell.setCellStyle(cellStyle);
    }

    /**
     * 求和离散单元格
     * @param sheet
     * @param row
     * @param col1
     * @param col2
     * @param col3
     * @param col4
     * @param col5
     * @param aimCol
     */
    public  void sumDiscreteCells(Sheet sheet, int row, int col1, int col2, int col3, int col4, int col5, int aimCol) {
        // 初始化求和结果
        double sum = 0;

        // 获取指定行
        Row targetRow = sheet.getRow(row);
        if (targetRow != null) {
            // 定义要累加的列索引
            int[] colsToSum = {col1, col2, col3, col4, col5};

            // 遍历指定的离散列进行求和
            for (int col : colsToSum) {
                Cell cell = targetRow.getCell(col);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    sum += cell.getNumericCellValue(); // 累加当前单元格的数值
                }
            }
        }

        // 将求和结果写入到目标列
        Cell aimCell = targetRow.getCell(aimCol);
        if (aimCell == null) {
            aimCell = targetRow.createCell(aimCol);
        }
        aimCell.setCellValue(sum); // 写入求和结果


        // 创建新的单元格样式
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

        // 设置水平和垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row aimrow = sheet.getRow(row);
        Cell cell = aimrow.getCell(aimCol);
        // 将样式应用到当前单元格
        cell.setCellStyle(cellStyle);
    }

    /**
     * 设置单元格值
     * @param sheet
     * @param rowIndex
     * @param colIndex
     * @param value
     * @return
     * @param <T>
     */
    public  <T> Cell setValue(Sheet sheet, int rowIndex, int colIndex, T value) {
        Cell cell;
        Row row = sheet.getRow(rowIndex);
        if (null == row) {
            row = sheet.createRow(rowIndex);
        }
        switch (value.getClass().getSimpleName()) {
            case "Byte":
            case "Long":

            case "Short":
            case "Integer":
            case "Float":
            case "Double":
                row.createCell(colIndex, CellType.NUMERIC)
                        .setCellValue((Double) value);
                cell = row.getCell(colIndex);

//                CellStyle cellStyle = cell.getCellStyle();

                // 创建新的单元格样式
                CellStyle cellStyle = sheet.getWorkbook().createCellStyle();

                // 设置边框
//                cellStyle.setBorderTop(BorderStyle.THIN);
//                cellStyle.setBorderRight(BorderStyle.THIN);
//                cellStyle.setBorderBottom(BorderStyle.THIN);
//                cellStyle.setBorderLeft(BorderStyle.THIN);

                // 设置水平和垂直居中
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                // 将样式应用到当前单元格
                cell.setCellStyle(cellStyle);
                break;
            case "Date":
                row.createCell(colIndex, CellType.NUMERIC)
                        .setCellValue((Date) value);
                break;
            case "LocalDateTime":
                row.createCell(colIndex, CellType.NUMERIC)
                        .setCellValue((LocalDateTime) value);
                break;
            case "LocalDate":
                row.createCell(colIndex, CellType.NUMERIC)
                        .setCellValue((LocalDate) value);
            case "Calendar":
                row.createCell(colIndex, CellType.NUMERIC)
                        .setCellValue((Calendar) value);
            case "Character":
            case "String":
                assert value instanceof String;
                row.createCell(colIndex, CellType.STRING)
                        .setCellValue((String) value);
                cell = row.getCell(colIndex);
                // 创建新的单元格样式
                CellStyle StrCellStyle = sheet.getWorkbook().createCellStyle();

                // 设置边框
//                StrCellStyle.setBorderTop(BorderStyle.THIN);
//                StrCellStyle.setBorderRight(BorderStyle.THIN);
//                StrCellStyle.setBorderBottom(BorderStyle.THIN);
//                StrCellStyle.setBorderLeft(BorderStyle.THIN);

                // 设置水平和垂直居中
                StrCellStyle.setAlignment(HorizontalAlignment.CENTER);
                StrCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                // 将样式应用到当前单元格
                cell.setCellStyle(StrCellStyle);
                break;
            case "RichTextString":
                row.createCell(colIndex, CellType.STRING)
                        .setCellValue((RichTextString) value);
                break;
            case "Boolean":
                row.createCell(colIndex, CellType.BOOLEAN)
                        .setCellValue((Boolean) value);
                break;
            default:
                row.createCell(colIndex, CellType.BLANK)
                        .setBlank();
        }
        return row.getCell(colIndex);
    }
}
