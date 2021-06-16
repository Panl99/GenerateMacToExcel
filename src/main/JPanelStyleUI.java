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
        // 框架
//        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setTitle("BY LIUPAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //窗口位置为桌面中心
//        frame.setLocationRelativeTo(null);
//        frame.setSize(800, 600);
        // 设置窗口大小、位置
        frame.setBounds(500, 300, 800, 600);
        // 包裹组件，窗口大小为包裹组件，前边设置的大小无效
//        frame.pack();

        JTextArea textArea = new JTextArea();
//        textArea.setBounds(10,100, 600, 400);
        textArea.setLineWrap(true); //换行
        textArea.setWrapStyleWord(true); //以单词边缘为界换行
        textArea.setEditable(false);
        textArea.setBackground(new Color(238,238,238));

        JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10,100, 700, 500);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "日志", TitledBorder.CENTER, TitledBorder.TOP));

        // 生成mac地址面板
        JPanel generateMacPanel = new JPanel();
        generateMacPanel.setBorder(BorderFactory.createTitledBorder("生成mac地址"));
        generateMacPanel.setLayout(null);
        generateMacPanel.setSize(390, 130);
//        generateMacPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "生成mac地址", TitledBorder.CENTER, TitledBorder.TOP));

        JLabel macStartLabel = new JLabel("mac首地址:");
        macStartLabel.setBounds(10,20,80,25);
        generateMacPanel.add(macStartLabel);

        JTextField macStartText = new JTextField(20);
        macStartText.setBounds(90,20,160,25);
        generateMacPanel.add(macStartText);

        JLabel showStartMacLengthLabel = new JLabel();
        showStartMacLengthLabel.setBounds(260,20,20,20);
        generateMacPanel.add(showStartMacLengthLabel);

        JLabel macEndLabel = new JLabel("mac尾地址:");
        macEndLabel.setBounds(10,50,80,25);
        generateMacPanel.add(macEndLabel);

        JTextField macEndText = new JTextField(20);
        macEndText.setBounds(90,50,160,25);
        generateMacPanel.add(macEndText);

        JLabel showEndMacLengthLabel = new JLabel();
        showEndMacLengthLabel.setBounds(260,50,20,20);
        generateMacPanel.add(showEndMacLengthLabel);

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
        confirmButton.setBounds(10, 85, 120, 25);
        generateMacPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateMacConfirmActionListener(frame, textArea, macStartText.getText().trim(), macEndText.getText().trim()).actionPerformed(e);
            }
        });

        JLabel remindLabel = new JLabel("<html><body>提示：<br>1. mac地址格式为16进制字符；<br>2. mac地址最长支持32位。<br>3. 一次最多生成65536条，即***0000 - ***ffff<body></html>");
        remindLabel.setBounds(10,160,300,100);
        remindLabel.setForeground(Color.GRAY);
        generateMacPanel.add(remindLabel);



        // 生成txt面板
        JPanel generateTxtPanel = new JPanel();
        generateTxtPanel.setBorder(BorderFactory.createTitledBorder("生成四元组txt"));
        generateTxtPanel.setLayout(null);
        generateTxtPanel.setSize(390, 130);
//        generateTxtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "生成mac地址", TitledBorder.CENTER, TitledBorder.TOP));

        JLabel productIdLabel = new JLabel("productId:");
        productIdLabel.setBounds(10,20,80,25);
        generateTxtPanel.add(productIdLabel);

        JTextField productIdText = new JTextField(20);
        productIdText.setBounds(90,20,165,25);
        generateTxtPanel.add(productIdText);

        JLabel cIdLabel = new JLabel("cid:");
        cIdLabel.setBounds(10,50,80,25);
        generateTxtPanel.add(cIdLabel);

        JTextField cIdText = new JTextField(20);
        cIdText.setBounds(90,50,165,25);
        generateTxtPanel.add(cIdText);

        JButton chooseFile = new JButton("选择文件...");
        chooseFile.setBounds(10, 80, 100, 25);
        chooseFile.setBackground(new Color(238,238,238));
        generateTxtPanel.add(chooseFile);

        JLabel showFileLabel = new JLabel();
        showFileLabel.setBounds(10, 100, 400,20);
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
                jfc.addChoosableFileFilter(new FileNameExtensionFilter("Excel(*.xls,*.xlsx)", "*.xls", "*.xlsx"));
                int isChooseFile = jfc.showOpenDialog(frame);
                if (isChooseFile == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    defaultFilePath = file;
                    showFileLabel.setText(file.getAbsolutePath());
                    textArea.append("【文件路径】" + file.getAbsolutePath() + "\n");
                }

            }
        });

        // 确定按钮
        JButton confirmButton2 = new JButton("生成四元组");
        confirmButton2.setBounds(10, 125, 120, 25);
        generateTxtPanel.add(confirmButton2);

        confirmButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ParseFileConfirmActionListener(frame, textArea, productIdText.getText().trim(), cIdText.getText().trim(), showFileLabel.getText());
            }
        });

        // 将子面板加到容器中
        Container c = new Container();
        c.setLayout(new GridLayout(1, 2,10,10));
        c.add(generateMacPanel);
        c.add(generateTxtPanel);

        Container c2 = frame.getContentPane();
        c2.setLayout(new GridLayout(2, 1,10,10));
        c2.add(c);
        c2.add(scrollPane);

        // 设置窗口可见
        frame.setVisible(true);

    }
}
