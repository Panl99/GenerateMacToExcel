package main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

/**
 * @author lp
 * @date 2021/6/13 13:47
 * @description
 **/
public class JPanelStyleUI {

    public void createGUI() {
        // 窗口
        JFrame frame = new JFrame();
        frame.setTitle("HiLink平台三元组自动生成工具");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口大小、位置
        frame.setBounds(500, 300, 800, 600);

        JTextArea textArea = new JTextArea();
        // 自动换行
        textArea.setLineWrap(true);
        // 以单词边缘为界换行
        textArea.setWrapStyleWord(true);
        // 组件不可编辑
        textArea.setEditable(false);
        // 背景色跟主界面一致
        textArea.setBackground(new Color(238, 238, 238));

        JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "日志", TitledBorder.CENTER, TitledBorder.TOP));

        // 生成mac地址面板
        JPanel generateMacPanel = new JPanel();
        generateMacPanel.setBorder(BorderFactory.createTitledBorder("生成mac地址"));
        generateMacPanel.setLayout(null);
        generateMacPanel.setSize(360, 240);

        JLabel macStartLabel = new JLabel("mac起始地址:");
        macStartLabel.setBounds(10, 20, 80, 25);
        generateMacPanel.add(macStartLabel);

        JTextField macStartText = new JTextField(20);
        macStartText.setBounds(100, 20, 200, 25);
        generateMacPanel.add(macStartText);

        JLabel showStartMacLengthLabel = new JLabel();
        showStartMacLengthLabel.setBounds(300, 20, 20, 20);
        generateMacPanel.add(showStartMacLengthLabel);

        JLabel macEndLabel = new JLabel("mac结束地址:");
        macEndLabel.setBounds(10, 50, 80, 25);
        generateMacPanel.add(macEndLabel);

        JTextField macEndText = new JTextField(20);
        macEndText.setBounds(100, 50, 200, 25);
        generateMacPanel.add(macEndText);

        JLabel showEndMacLengthLabel = new JLabel();
        showEndMacLengthLabel.setBounds(300, 50, 20, 20);
        generateMacPanel.add(showEndMacLengthLabel);

        JLabel prodIdLabel = new JLabel("prodId:");
        prodIdLabel.setBounds(10, 80, 80, 25);
        generateMacPanel.add(prodIdLabel);

        JTextField prodIdText = new JTextField(20);
        prodIdText.setBounds(100, 80, 200, 25);
        generateMacPanel.add(prodIdText);

        macStartText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (macStartText.getText().trim().length() > 0) {
                    showStartMacLengthLabel.setText("" + macStartText.getText().trim().length());
                } else {
                    showStartMacLengthLabel.setText(null);
                }
            }
        });
        macEndText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (macEndText.getText().trim().length() > 0) {
                    showEndMacLengthLabel.setText("" + macEndText.getText().trim().length());
                } else {
                    showEndMacLengthLabel.setText(null);
                }
            }
        });

        // 确定按钮
        JButton confirmButton = new JButton("生成mac地址");
        confirmButton.setBounds(10, 120, 120, 25);
        generateMacPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateMacConfirmActionListener(frame, textArea, macStartText.getText().trim(), macEndText.getText().trim(), prodIdText.getText().trim()).actionPerformed(e);
            }
        });

        JLabel remindLabel = new JLabel();
        remindLabel.setBounds(10, 150, 300, 120);
        remindLabel.setText("<html><body>提示：<br> 1. mac地址要求：非空，16进制字符，长度小于32位； <br>2. prodId非空，只支持数字和字母，最长64位； <br> 3. 为保障工具可用性，限制mac一次最多生成65536条（即最大：***0000 - ***ffff）。<body></html>");
        remindLabel.setForeground(Color.GRAY);
        generateMacPanel.add(remindLabel);


        // 生成txt面板
        JPanel generateTxtPanel = new JPanel();
        generateTxtPanel.setBorder(BorderFactory.createTitledBorder("生成四元组txt"));
        generateTxtPanel.setLayout(null);
        generateTxtPanel.setSize(360, 240);

        JLabel productIdLabel = new JLabel("prodId:");
        productIdLabel.setBounds(10, 20, 80, 25);
        generateTxtPanel.add(productIdLabel);

        JTextField productIdText = new JTextField(20);
        productIdText.setBounds(90, 20, 200, 25);
        generateTxtPanel.add(productIdText);

        JLabel cIdLabel = new JLabel("cid:");
        cIdLabel.setBounds(10, 50, 80, 25);
        generateTxtPanel.add(cIdLabel);

        JTextField cIdText = new JTextField(20);
        cIdText.setBounds(90, 50, 200, 25);
        generateTxtPanel.add(cIdText);

        JButton chooseFile = new JButton("选择三元组文件...");
        chooseFile.setBounds(10, 80, 150, 25);
        chooseFile.setBackground(new Color(238, 238, 238));
        generateTxtPanel.add(chooseFile);

        JLabel showFileLabel = new JLabel();
        showFileLabel.setBounds(10, 105, 800, 20);
        generateTxtPanel.add(showFileLabel);

        chooseFile.addActionListener(new ActionListener() {
            private File defaultFilePath;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (defaultFilePath == null) {
                    defaultFilePath = FileSystemView.getFileSystemView().getHomeDirectory();
                }

                JFileChooser jfc = new JFileChooser();
                jfc.setCurrentDirectory(defaultFilePath);
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Excel(*.xls,*.xlsx)", "xls", "xlsx"));
                int isChooseFile = jfc.showOpenDialog(frame);
                if (isChooseFile == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    defaultFilePath = file;
                    showFileLabel.setText(file.getAbsolutePath());
                    textArea.append("【三元组秘钥文件】" + file.getAbsolutePath() + " \n");
                }

            }
        });

        // 确定按钮
        JButton confirmButton2 = new JButton("生成四元组");
        confirmButton2.setBounds(10, 130, 120, 25);
        generateTxtPanel.add(confirmButton2);

        confirmButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ParseFileConfirmActionListener(frame, textArea, productIdText.getText().trim(), cIdText.getText().trim(), showFileLabel.getText());
            }
        });

        JLabel remindLabel2 = new JLabel();
        remindLabel2.setBounds(10, 160, 300, 60);
        remindLabel2.setText("<html><body>提示：<br> 1. prodId非空，只支持数字和字母，最长64位； <br> 2. cid非空，只支持数字和字母，最长64位。<body></html>");
        remindLabel2.setForeground(Color.GRAY);
        generateTxtPanel.add(remindLabel2);

        // 清除日志按钮
        JButton cleanLogButton = new JButton("清除日志");
        cleanLogButton.setBounds(10, 240, 100, 25);
        generateTxtPanel.add(cleanLogButton);

        cleanLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(null);
            }
        });

        // 将子面板加到容器中
        Container c = new Container();
        c.setLayout(new GridLayout(1, 2, 10, 10));
        c.add(generateMacPanel);
        c.add(generateTxtPanel);

        Container c2 = frame.getContentPane();
        c2.setLayout(new GridLayout(2, 1, 10, 10));
        c2.add(c);
        c2.add(scrollPane);

        // 设置窗口可见
        frame.setVisible(true);

    }

}
