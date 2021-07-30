package main;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
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
    private String actionSource;
    private String pid;
    private String cid;
    private String filePath;
    private static File defaultFilePath;

    private final int maxTextLength = 64;

    public ParseFileConfirmActionListener(JFrame frame, String actionSource, String pid, String cid, String filePath) {
        this.frame = frame;
        this.actionSource = actionSource;
        this.pid = pid;
        this.cid = cid;
        this.filePath = filePath;
        fileHandler();
    }

    private void fileHandler() {
        if (!isTelink() && !isRealtek()) {
            JOptionPane.showMessageDialog(frame, "未知的事件源！", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!verifyText()) {
            return;
        }

        if (filePath == null || filePath.trim().length() == 0) {
            JOptionPane.showMessageDialog(frame, "请先选择文件", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File file = new File(filePath);
        String content = parseExcelInfo(file);
        if (content.trim().length() == 0) {
            return;
        }

        // 写入txt
        writeToTxt(file.getParent(), content);
    }

    private boolean isTelink() {
        return "telink".equals(actionSource);
    }
    private boolean isRealtek() {
        return "realtek".equals(actionSource);
    }

    /**
     * 校验文本框内容格式
     * @return
     */
    private boolean verifyText() {
        if (pid == null || pid.length() == 0) {
            JOptionPane.showMessageDialog(frame, "pid内容不能为空", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Pattern.matches("[0-9a-zA-Z]+", pid)) {
            JOptionPane.showMessageDialog(frame, "pid只支持数字和字母格式：pid="+pid, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (pid.length() > maxTextLength) {
            JOptionPane.showMessageDialog(frame, "pid最长支持64位：pid="+pid, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (isTelink()) {
            if (cid == null || cid.length() == 0) {
                JOptionPane.showMessageDialog(frame, "cid内容不能为空", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!Pattern.matches("[0-9a-zA-Z]+", cid)) {
                JOptionPane.showMessageDialog(frame, "cid只支持数字和字母格式：cid="+cid, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (cid.trim().length() > maxTextLength) {
                JOptionPane.showMessageDialog(frame, "cid最长支持64位：cid="+cid, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
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
        if (isTelink()) {
            sb.append("Hilink四元组").append("\n");
            // 要求用tab隔开 \t
            sb.append("pid").append("\t").append("mac").append("\t").append("secret").append("\t").append("cid").append("\n");
        }

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
                    if (isTelink()) {
                        // 要求用tab隔开 \t
                        sb.append(ConvertUtil.ascii2hex(pid)).append("\t").append(row.getCell(1)).append("\t").append(row.getCell(2)).append("\t").append(cid).append("\n");
                    } else if (isRealtek()) {
                        // 要求用,隔开；转成16进制的10进制
                        sb.append(ConvertUtil.hex2decimal(ConvertUtil.ascii2hex(pid))).append(",").append(row.getCell(2)).append(",").append(row.getCell(1)).append("\n");
                    }
                }
            }

        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(frame, "解析excel文件异常！e = "+ e, "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "writeToTxt关闭文件流异常！e = "+ e, "Error", JOptionPane.ERROR_MESSAGE);
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

        if (defaultFilePath == null) {
//            defaultFilePath = FileSystemView.getFileSystemView().getHomeDirectory();
            defaultFilePath = new File(filePath);
        }

        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(defaultFilePath);
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("Txt(*.txt)", "txt"));
        if(JFileChooser.APPROVE_OPTION == jfc.showSaveDialog(frame)) {
            File saveFile = new File(jfc.getSelectedFile().toString().trim().endsWith(".txt") ? jfc.getSelectedFile().toString().trim() : jfc.getSelectedFile().toString().trim().concat(".txt"));
            try {
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
                bw = new BufferedWriter(new FileWriter(saveFile));
                bw.write(content);
                defaultFilePath = saveFile;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "写入txt文件异常！", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (bw != null) {
                        bw.flush();
                        bw.close();
                        JOptionPane.showMessageDialog(frame, "写入Txt完成", "done", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "writeToTxt关闭文件流异常!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
}
