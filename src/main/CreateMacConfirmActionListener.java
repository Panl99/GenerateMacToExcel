package main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author lp
 * @date 2021/6/12 20:55
 * @description
 **/
public class CreateMacConfirmActionListener {

    private JFrame frame;
    private String startMac;
    private String endMac;
    private JPanel pidListPanel;

    private final int defaultHexStrLength = 12;
    private final int maxHexStrLength = 32;
    private final int maxTextLength = 64;

    private static final char[] HEX_CHAR_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] HEX_CHAR_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public CreateMacConfirmActionListener(JFrame frame, String startMac, String endMac, JPanel pidListPanel) {
        this.frame = frame;
        this.startMac = startMac;
        this.endMac = endMac;
        this.pidListPanel = pidListPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (!verifyMac()) {
            return;
        }

        if (!verifyText()) {
            return;
        }

        // 放前边，需要计算个数供校验
        List<List<String>> macList = generateMacList();
        int macListSize = 0;
        for (List list : macList) {
            macListSize += list.size();
        }

        if (!verifyNumSum(macListSize)) {
            return;
        }

        if (macListSize == 0) {
            JOptionPane.showMessageDialog(frame, "生成mac列表错误! 空列表！", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        writeToExcel(macList);
    }

    /**
     * 校验文本框内容格式
     *
     * @return
     */
    private boolean verifyText() {
        List<String> pidList = new ArrayList<>(pidListPanel.getComponentCount());

        for (int i = 0; i < pidListPanel.getComponentCount(); i++) {
            // 校验pid内容
            JTextField pidText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(1)).getComponent(2);
            String pid = pidText.getText();
            if (pid.trim().length() == 0) {
                JOptionPane.showMessageDialog(frame, "pid内容不能为空", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!Pattern.matches("[0-9a-zA-Z]+", pid)) {
                JOptionPane.showMessageDialog(frame, "pid只支持数字和字母格式", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (pid.trim().length() > maxTextLength) {
                JOptionPane.showMessageDialog(frame, "pid最长支持64位", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (pidList.contains(pid.trim())) {
                JOptionPane.showMessageDialog(frame, "pid不能相同，pid = " + pid.trim(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            pidList.add(pid.trim());

            // 校验个数内容
            JTextField numText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(2)).getComponent(2);
            String num = numText.getText();
            if (num.trim().length() == 0) {
                JOptionPane.showMessageDialog(frame, "个数不能为空", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!Pattern.matches("[0-9]+", num.trim())) {
                JOptionPane.showMessageDialog(frame, "个数只支持数字", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (Integer.parseInt(num.trim()) < 1) {
                JOptionPane.showMessageDialog(frame, "个数不能小于1，行号：" + ++i + " | 当前个数：" + num.trim(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // 校验文件名内容
            JTextField fileNameText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(3)).getComponent(2);
            String fileName = fileNameText.getText();
            if (fileName.trim().length() == 0) {
                JOptionPane.showMessageDialog(frame, "请输入文件名，行号：" + ++i, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (fileName.trim().length() > 128) {
                JOptionPane.showMessageDialog(frame, "文件名最长支持128位，行号：" + ++i + " | 当前长度：" + fileName.trim().length(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        }
        return true;
    }

    private boolean verifyNumSum(int macListSize) {
        int numSum = 0;
        for (int i = 0; i < pidListPanel.getComponentCount(); i++) {
            // 校验个数内容
            JTextField numText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(2)).getComponent(2);
            int num = "".equals(numText.getText().trim()) ? 0 : Integer.parseInt(numText.getText().trim());
            if (num > macListSize) {
                JOptionPane.showMessageDialog(frame, "个数超过了mac地址总数，行号：" + ++i + " | 当前个数：" + num + " | mac总数：" + macListSize, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            numSum += num;
        }
        if (numSum > macListSize) {
            JOptionPane.showMessageDialog(frame, "个数总和超过了mac地址总数，个数总和：" + numSum + " | mac总数：" + macListSize, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
            JOptionPane.showMessageDialog(frame, "mac起始地址跟mac结束地址长度不一致！", "Error", JOptionPane.ERROR_MESSAGE);
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

        // 转16进制后的校验
        if (!checkMacHex()) {
            return false;
        }

        return true;
    }

    public boolean checkMacHex() {
        for (int i = 0; i < startMac.length(); ++i) {
            char c = startMac.charAt(i);
            char c2 = endMac.charAt(i);

            int result = compareCharSize(c2, c);
            if (result > 0) {
                // 判断索引i不在后四位
                if (startMac.length() > 4 && (startMac.length() - 1 - i) >= 4) {
                    JOptionPane.showMessageDialog(frame, "不支持生成mac条数大于65536条！[index=" + i + "，字符（" + c + " | " + c2 + "）]", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                break;
            } else if (result < 0) {
                JOptionPane.showMessageDialog(frame, "mac结束地址不能小于起始地址[index=" + i + "，字符（" + c + " | " + c2 + "）]", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * 生成mac地址列表
     */
    private List<List<String>> generateMacList() {
        List<List<String>> lists = new ArrayList<>(pidListPanel.getComponentCount());
        String currentMac = startMac.toLowerCase();
        for (int i = 0; i < pidListPanel.getComponentCount(); i++) {
            JTextField numText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(2)).getComponent(2);
            int num = Integer.parseInt(numText.getText());
            List<String> currentMacList = new ArrayList<>(num);
            currentMacList.add(currentMac);
            for (int j = 1; j < num; j++) {
                if (endMac.toLowerCase().equals(currentMac)) {
                    break;
                }
                currentMac = hexIncreaseOperator(currentMac, 1);
                if (currentMac == null || currentMac.trim().length() == 0) {
                    return null;
                }
                currentMacList.add(currentMac);
            }
            lists.add(currentMacList);
            if (endMac.toLowerCase().equals(currentMac)) {
                break;
            }
            currentMac = hexIncreaseOperator(currentMac, 1);
        }
        return lists;
    }


    /**
     * 写入excel
     *
     * @param macList mac地址列表
     */
    private static File defaultFilePath;

    private void writeToExcel(List<List<String>> macList) {
        // 创建mac地址Excel默认路径桌面
        if (defaultFilePath == null) {
            defaultFilePath = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
        }

        FileOutputStream fos = null;
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(defaultFilePath);
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (JFileChooser.APPROVE_OPTION == jfc.showSaveDialog(frame)) {
                for (int i = 0; i < pidListPanel.getComponentCount(); i++) {
                    JTextField fileNameText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(3)).getComponent(2);
                    JLabel fileNameSuffixText = (JLabel) ((JPanel) pidListPanel.getComponent(i)).getComponent(4);
                    String fileName = fileNameText.getText().concat(fileNameSuffixText.getText());
                    List<String> currentMacList = macList.get(i);
                    File currentFile = new File(jfc.getSelectedFile() + File.separator + fileName);

                    // 创建Excel
                    XSSFWorkbook sheets = new XSSFWorkbook();
                    // 创建工作表sheet
                    Sheet sheet = sheets.createSheet();
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
                    cell.setCellValue("prodId");
                    cell.setCellStyle(xssfCellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue("MAC地址");
                    cell.setCellStyle(xssfCellStyle);

                    // 写入数据内容
                    for (int d = 1; d <= currentMacList.size(); ++d) {
                        Row nextRow = sheet.createRow(d);
                        cell = nextRow.createCell(0);

                        JTextField pidText = (JTextField) ((JPanel) ((JPanel) pidListPanel.getComponent(i)).getComponent(1)).getComponent(2);
                        String pid = pidText.getText();
                        // pid转16进制
                        cell.setCellValue(ConvertUtil.ascii2hex(pid));

                        cell = nextRow.createCell(1);
                        cell.setCellValue(String.valueOf(currentMacList.get(d - 1)));
                    }

                    currentFile.createNewFile();
                    fos = new FileOutputStream(currentFile);
                    sheets.write(fos);
                    defaultFilePath = currentFile;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "写入Excel文件异常！", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (fos != null) {
                    fos.flush();
                    fos.close();
                    JOptionPane.showMessageDialog(frame, "写入Excel完成", "done", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "writeToExcel关闭文件流异常！", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * 计算十六进制的增加
     *
     * @param hexStr 十六进制字符串
     * @param step   增加步长,不支持小于等于0
     * @return
     */
    private static String hexIncreaseOperator(String hexStr, int step) {
        if (step <= 0) {
//            JOptionPane.showMessageDialog(frame, "自增步长应大于0", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }

        // hexStr:起始mac地址
        // 运算下标长度
        int len = hexStr.length() - 1;
        // 进位标志
        boolean carryFlag = true;
        // 参与了运算的位运算后的结果，从右向左运算，先是最后一位参与运算，如果需要进位，那么倒数第二位也要参与运算。以此类推
        String change = "";
        // 最终结果 = 未参与运算的位 + 参与运算的位
        String result = "";
        while (carryFlag && len >= 0) {
            // mac地址第[len]位字符，先从最后一位开始
            char c = hexStr.charAt(len);
            int index = indexOfChars(HEX_CHAR_LOWER, c);
            if (index == -1) {
//                JOptionPane.showMessageDialog(frame, "获取字符在数组中的下标时错误[char=" + c + "],联系作者解决", "Error", JOptionPane.ERROR_MESSAGE);
                return "";
            } else {
                // step：自增步长
                int sum = index + step;
                if (sum < 16) {
                    change = HEX_CHAR_LOWER[sum] + change;
                    result = hexStr.substring(0, len) + change;
                    carryFlag = false;
                } else {
                    change = HEX_CHAR_LOWER[sum % 16] + change;
                    step = sum / 16;
                }
            }
            --len;
        }
        while (carryFlag) {
            if (step < 16) {
                change = HEX_CHAR_LOWER[step] + change;
                result = change;
                carryFlag = false;
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
     * 获取字符在数组中的下标
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
