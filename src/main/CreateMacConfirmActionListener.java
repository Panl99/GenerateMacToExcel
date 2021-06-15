package main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lp
 * @date 2021/6/12 20:55
 * @description
 **/
public class CreateMacConfirmActionListener {
    private JFrame frame;
    private JTextArea textArea;
    private String startMac;
    private String endMac;

    private final int defaultHexStrLength = 12;
    private final int maxHexStrLength = 32;

    private static final char[] HEX_CHAR_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] HEX_CHAR_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public CreateMacConfirmActionListener(JFrame frame, JTextArea textArea, String startMac, String endMac) {
        this.frame = frame;
        this.textArea = textArea;
        this.startMac = startMac;
        this.endMac = endMac;
    }

    public void actionPerformed(ActionEvent e) {
        if (!verifyMac()) {
//            JOptionPane.showMessageDialog(frame, "mac地址输入格式错误", "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】请检查mac地址是否正确！  mac首地址：" + startMac + "  mac尾地址：" + endMac + "\n");
            return;
        }

        List<String> macList = generateMacList();
        if (macList == null || macList.size() == 0) {
            textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
            textArea.append("【Error】发生错误，生成mac列表为空! 退出！" + "\n");
            return;
        }

        // 创建mac地址Excel默认路径桌面
        String defaultPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        // 创建Excel文件
        String fileAddress = defaultPath + File.separator + "mac地址.xlsx";
        File file = new File(fileAddress);
        if (file.exists()) {
            fileAddress = defaultPath + File.separator + "mac地址" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xlsx";
            file = new File(fileAddress);
        }
        writeToExcel(file, macList);

        textArea.append("【时间】" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\n");
        textArea.append("【文件路径】" + file.getAbsolutePath() + "\n");
        textArea.append("个数:" + macList.size() + " >> " + macList + "\n");
    }

    /**
     * 校验输入的mac地址是否有效
     *
     * @return
     */
    private boolean verifyMac() {
        if (startMac == null || startMac.trim().length() == 0 || endMac == null || endMac.trim().length() == 0) {
            JOptionPane.showMessageDialog(frame, "mac地址输入为空", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (startMac.trim().length() != endMac.trim().length()) {
            JOptionPane.showMessageDialog(frame, "mac首地址跟mac尾地址长度不一致！", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

//        if (startMac.trim().length() != defaultHexStrLength || endMac.trim().length() != defaultHexStrLength) {
//            JOptionPane.showMessageDialog(frame, "确认mac地址长度非12位？(不影响mac地址生成)", "Warning", JOptionPane.WARNING_MESSAGE);
////            return false;
//        }

        if (startMac.trim().length() > maxHexStrLength) {
            JOptionPane.showMessageDialog(frame, "mac地址最大32位", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String hexMatcher = "[0-9a-fA-F]{" + startMac.trim().length() + "}";

        if (!startMac.trim().matches(hexMatcher) || !endMac.trim().matches(hexMatcher)) {
            JOptionPane.showMessageDialog(frame, "请检查mac地址是否为十六进制格式", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

//        if (startMac.trim().equalsIgnoreCase(endMac.trim())) {
//            JOptionPane.showMessageDialog(frame, "mac首尾地址相同", "Warning", JOptionPane.WARNING_MESSAGE);
//            return false;
//        }

        for (int i = 0; i < startMac.length(); ++i) {
            char c = startMac.charAt(i);
            char c2 = endMac.charAt(i);

            int result = compareCharSize(c2, c);
            if (result > 0) {
                break;
            } else if (result < 0) {
                JOptionPane.showMessageDialog(frame, "mac尾地址不能小于首地址[index=" + i + "]", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    /**
     * 生成mac地址列表
     */
    private List<String> generateMacList() {

        List<String> macList = new ArrayList<>();
        macList.add(startMac);
        String currentMac = startMac.toLowerCase();

        while (true) {
            if (endMac.toLowerCase().equals(currentMac)) {
                break;
            }
            currentMac = hexIncreaseOperator(currentMac, 1);
            if (currentMac == null || currentMac.trim().length() == 0) {
                return null;
            }

            macList.add(currentMac);
        }
        return macList;
    }


    /**
     * 写入excel
     *
     * @param file 创建excel
     * @param macList  mac地址列表
     */
    private void writeToExcel(File file, List macList) {
        FileOutputStream fos = null;
        try {
            // 创建Excel
            XSSFWorkbook sheets = new XSSFWorkbook();
            // 创建工作表sheet
            Sheet sheet = sheets.createSheet();
            // 设置列宽
            sheet.setColumnWidth(0, 16 * 256);
            sheet.setColumnWidth(1, 32 * 256);

            //标题字体样式设置
            XSSFFont titleFont = sheets.createFont();
            titleFont.setFontHeightInPoints((short) 14);
            titleFont.setColor(XSSFFont.COLOR_NORMAL);
            titleFont.setFontName(" 宋体 ");
            titleFont.setBold(true);

            // 设置单元格风格为文本格式
            XSSFCellStyle xssfCellStyle = sheets.createCellStyle();
            xssfCellStyle.setDataFormat(sheets.createDataFormat().getFormat("@"));
            xssfCellStyle.setFont(titleFont);
            xssfCellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

            // 创建第一行
            Row row = sheet.createRow(0);
            row.setHeight((short) 450);

            // 创建单元格，写入title
            Cell cell = row.createCell(0);
            cell.setCellValue("序号");
            cell.setCellStyle(xssfCellStyle);
            cell = row.createCell(1);
            cell.setCellValue("MAC地址");
            // 设置标题栏风格
            cell.setCellStyle(xssfCellStyle);

            // 写入数据内容
            for (int i = 1; i <= macList.size(); ++i) {
                Row nextRow = sheet.createRow(i);
                cell = nextRow.createCell(0);
                cell.setCellValue(i);

                cell = nextRow.createCell(1);
                cell.setCellValue(String.valueOf(macList.get(i-1)));
            }

//            sheet.getRow(0).getCell(0).setCellStyle(xssfCellStyle);
//            sheet.getRow(0).getCell(1).setCellStyle(xssfCellStyle);

            file.createNewFile();
            fos = new FileOutputStream(file);
            sheets.write(fos);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "写入Excel文件异常！", "Error", JOptionPane.ERROR_MESSAGE);
            textArea.append(e + "\n");
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                    JOptionPane.showMessageDialog(frame, "写入Excel完成", "done", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                textArea.append("writeToExcel关闭文件流异常" + "\n");
                textArea.append(e + "\n");
            }
        }
    }

    /**
     * 计算十六进制的增加
     * @param hexStr 十六进制字符串
     * @param step 增加步长,不支持小于等于0
     * @return
     */
    private String hexIncreaseOperator(String hexStr, int step) {
        if (step <= 0) {
            JOptionPane.showMessageDialog(frame, "自增步长应大于0", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }

        int len = hexStr.length() - 1;
        //是否需要进位
        boolean b = true;
        //参与了运算的位运算后的结果，从右向左运算，先是最后一位参与运算，如果需要进位，那么倒数第二位也要参与运算。以此类推
        String change = "";
        //最终结果 = 未参与运算的位 + 参与运算的位
        String result = "";
        while (b && len >= 0) {
            char c = hexStr.charAt(len);
            int index = indexOfChars(HEX_CHAR_LOWER, c);
            if (index == -1) {
                JOptionPane.showMessageDialog(frame, "获取字符在数组中的下表时错误[char=" + c + "],联系作者解决", "Error", JOptionPane.ERROR_MESSAGE);
                return "";
            } else {
                int sum = index + step;
                if (sum < 16) {
                    change = HEX_CHAR_LOWER[sum] + change;
                    result = hexStr.substring(0, len) + change;
                    b = false;
                } else {
                    change = HEX_CHAR_LOWER[sum % 16] + change;
                    step = sum / 16;
                }
            }
            --len;
        }
        while (b) {
            if (step < 16) {
                change = HEX_CHAR_LOWER[step] + change;
                result = change;
                b = false;
            } else {
                change = HEX_CHAR_LOWER[step % 16] + change;
                step /= 16;
            }
        }

        return result;
    }


    /**
     * 比较十六进制字符大小
     *
     * @param c1
     * @param c2
     * @return
     */
    private int compareCharSize(char c1, char c2) {
        int digit1 = Character.digit(c1, 16);
        int digit2 = Character.digit(c2, 16);
        return digit1 - digit2;
    }

    /**
     * 获取字符在数组中的下表
     *
     * @param chars 字符数组
     * @param c     目标字符
     * @return
     */
    public static int indexOfChars(char[] chars, char c) {
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == c) {
                return i;
            }
        }
        return -1;
    }

}
