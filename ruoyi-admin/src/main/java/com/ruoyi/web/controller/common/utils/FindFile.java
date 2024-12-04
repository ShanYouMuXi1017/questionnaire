package com.ruoyi.web.controller.common.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class FindFile {
    /**
     * 获取最新日期目录
     * @param rootDirectory
     * @return
     */
    public static File getLatestDirectory(File rootDirectory) {
        // 获取所有年份目录
        File[] yearDirs = rootDirectory.listFiles(File::isDirectory);
        if (yearDirs == null || yearDirs.length == 0) {
            return null; // 如果没有年份目录，返回 null
        }

        // 按照目录名（年份）排序，找到最新的年份目录
        Arrays.sort(yearDirs, Comparator.comparing(File::getName));

        // 获取最新的年份目录
        File latestYearDir = yearDirs[yearDirs.length - 1];
//        System.out.println("最新年份目录：" + latestYearDir.getAbsolutePath());

        // 获取所有月份目录
        File[] yearsDirs = latestYearDir.listFiles(File::isDirectory);
        if (yearsDirs == null || yearsDirs.length == 0) {
            return null;
        }
//        System.out.println("排序前的月份目录：" + Arrays.toString(yearsDirs));

        // 按照月份排序，找到最新的月份目录
        Arrays.sort(yearsDirs, Comparator.comparing(File::getName));
//        System.out.println("排序后的月份目录：" + Arrays.toString(yearsDirs));

        // 获取最新的年份目录
        File latestMonthDir = yearsDirs[yearsDirs.length - 1];
//        System.out.println("最新年份目录：" + latestMonthDir.getAbsolutePath());

        // 获取所有月份目录
        File[] dayDirs = latestMonthDir.listFiles(File::isDirectory);
//        System.out.println("日期月份目录列表：" + Arrays.toString(dayDirs));

        assert dayDirs != null;
        Arrays.sort(dayDirs, Comparator.comparing(File::getName));
        // 获取最新的日期目录
        File latestMonthDir1 = dayDirs[dayDirs.length - 1];
        File[] lastDayFiles = latestMonthDir1.listFiles(File::isDirectory);
//        System.out.println("最新日期目录2222222222222：" + Arrays.toString(lastDayFiles));
//        System.out.println("最新日期目录3333333333333333333：" + Arrays.toString(lastDayFiles));

        if (lastDayFiles == null || lastDayFiles.length == 0) {
            return null;
        }

        // 按照日期排序，找到最新的日期目录
        Arrays.sort(lastDayFiles, Comparator.comparing(File::getName));

        // 返回最新日期的目录
        return lastDayFiles[lastDayFiles.length - 1];
    }

    /**
     * 获取最新 Excel 文件
     * @param directory
     * @return
     */
    public static File getNewestExcelFile(File directory) {
        // 查找所有的 Excel 文件
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".xls") || name.endsWith(".xlsx"));

        // 打印文件路径，检查目录下是否有文件
        if (files != null && files.length > 0) {
//            for (File file : files) {
//                System.out.println(file.getAbsolutePath());  // 输出每个文件的绝对路径
//            }

            // 按照文件修改时间进行排序，找到最新的文件
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            return files[files.length - 1]; // 返回最新的文件
        } else {
//            System.out.println("No Excel files found in the directory: " + directory.getAbsolutePath());
            return null; // 如果没有 Excel 文件，返回 null
        }
    }
}
