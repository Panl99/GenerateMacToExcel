package main;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author lp
 * @date 2021/6/12 0:37
 * @description
 **/
public class ParseFileConfirmActionListener {
    private JFrame frame;
    private JTextArea textArea;
    private String productId;
    private String cid;
    private String filePath;

    private final int maxTextLength = 64;

    public ParseFileConfirmActionListener(JFrame frame, JTextArea textArea, String productId, String cid, String filePath) {
        this.frame = frame;
        this.textArea = textArea;
        this.productId = productId;
        this.cid = cid;
        this.filePath = filePath;
        fileHandler();
    }

    private void fileHandler() {
        if (filePath == null || filePath.trim().length() == 0) {
            JOptionPane.showMessageDialog(frame, "请先选择文件", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!verifyText()) {
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】文本格式有误！prodId=" + productId + "，cid=" + cid + "\n");
            return;
        }

        File file = new File(filePath);
        String content = parseExcelInfo(file);
        if (content.trim().length() == 0) {
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】解析Excel错误! 退出!" + "\n");
            return;
        }

        // 写入txt
        writeToTxt(file.getParent(), content);

        textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
        textArea.append(content + "\n");
    }

    /**
     * 校验文本框内容格式
     * @return
     */
    private boolean verifyText() {
        if (productId == null || productId.trim().length() == 0) {
            JOptionPane.showMessageDialog(frame, "prodId内容不能为空", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Pattern.matches("[0-9a-zA-Z]+", productId)) {
            JOptionPane.showMessageDialog(frame, "prodId只支持数字和字母格式：prodId="+productId, "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】prodId格式有误！prodId=" + productId + "\n");
            return false;
        }
        if (productId.trim().length() > maxTextLength) {
            JOptionPane.showMessageDialog(frame, "prodId最长支持64位：prodId="+productId, "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】prodId格式有误！prodId=" + productId + "\n");
            return false;
        }


        if (cid == null || cid.trim().length() == 0) {
            JOptionPane.showMessageDialog(frame, "cid内容不能为空", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Pattern.matches("[0-9a-zA-Z]+", cid)) {
            JOptionPane.showMessageDialog(frame, "cid只支持数字和字母格式：cid="+cid, "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】cid格式有误！cid=" + cid + "\n");
            return false;
        }
        if (cid.trim().length() > maxTextLength) {
            JOptionPane.showMessageDialog(frame, "cid最长支持64位：cid="+cid, "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】cid格式有误！cid=" + cid + "\n");
            return false;
        }

        return true;
    }

    /**
     * 解析文件
     * @param file
     * @return
     */
    private String parseExcelInfo(File file) {
        if (!file.canRead()) {
            JOptionPane.showMessageDialog(frame, "文件不可读!", "Warning", JOptionPane.WARNING_MESSAGE);
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Hilink四元组").append("\n");
        // 要求用tab隔开 \t
        sb.append("prodId").append("\t").append("mac").append("\t").append("secret").append("\t").append("cid").append("\n");

        FileInputStream fis = null;
        try {
            String fileName = file.getName();
            Workbook sheets;
            if (fileName.endsWith(".xls")) {
                fis = new FileInputStream(file);
                sheets = new HSSFWorkbook(fis);
            } else if (fileName.endsWith(".xlsx")) {
                sheets = new XSSFWorkbook(file);
            } else {
                JOptionPane.showMessageDialog(frame, "不支持的文件格式!", "Warning", JOptionPane.WARNING_MESSAGE);
                return "";
            }

            // 解析
            Sheet sheet = sheets.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum() + 1;
            int lastRowIndex = sheet.getLastRowNum();

            for (int i = firstRowIndex; i <= lastRowIndex; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    // 要求用tab隔开 \t
                    sb.append(productId).append("\t").append(row.getCell(1)).append("\t").append(row.getCell(2)).append("\t").append(cid).append("\n");
                }
            }

        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(frame, "解析excel文件异常！", "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append(e + "\n");
            return "";
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                textArea.append("writeToTxt关闭文件流异常" + "\n");
                textArea.append(e + "\n");
            }

        }
        return sb.toString();
    }

    /**
     * 写入txt
     * @param content
     */
    private void writeToTxt(String filePath, String content) {
        BufferedWriter bw = null;
        try {
            String fileAddress = filePath + File.separator + "华为四元组.txt";
            File file = new File(fileAddress);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                fileAddress = filePath + File.separator + "华为四元组" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".txt";
                new File(fileAddress).createNewFile();
            }

            bw = new BufferedWriter(new FileWriter(fileAddress));
            bw.write(content);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "写入txt文件异常！", "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append(e + "\n");
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                    JOptionPane.showMessageDialog(frame, "写入Txt完成", "done", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                textArea.append("writeToTxt关闭文件流异常" + "\n");
                textArea.append(e + "\n");
            }
        }

    }
}
