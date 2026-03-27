package org.example.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
    // 静态方法读取CSV
    public static List<String[]> readCSV(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new RuntimeException("CSV文件路径不能为空");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在: " + filePath);
        }
        if (!file.isFile()) {
            throw new RuntimeException("不是有效文件: " + filePath);
        }

        List<String[]> csvData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) continue;
                csvData.add(trimmedLine.split(","));
            }
        } catch (IOException e) {
            throw new RuntimeException("读取CSV失败: " + e.getMessage(), e);
        }
        return csvData;
    }

    // 检查CSV格式（静态方法，方便调用）
    public static boolean checkCSVFormat(List<String[]> data) {
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("CSV数据为空");
        }
        int standardCol = data.get(0).length;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).length != standardCol) {
                throw new RuntimeException("第" + (i+1) + "行列数错误，期望" + standardCol + "列，实际" + data.get(i).length + "列");
            }
        }
        return true;
    }
}