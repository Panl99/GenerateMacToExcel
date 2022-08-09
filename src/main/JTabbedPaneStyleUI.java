package main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lp
 * @date 2021/6/13 13:39
 * @description
 **/
public class JTabbedPaneStyleUI {
    private JFrame frame;
    private String fileNameDelimiter = "-";
    private String fileNameSuffix = ".xlsx";

    int initIndex = 1;

    public void createGUI() {

        frame = new JFrame();
        frame.setTitle("HiLink平台三元组自动生成工具_2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400, 100, 1200, 800);
        // 禁止窗口拉伸
//        frame.setResizable(false);

        //创建一个选项卡容器，添加到顶层容器中
        JTabbedPane tp = new JTabbedPane();
        frame.setContentPane(tp);

        //添加选项卡容器，并且设置其中每个选项卡的标签以及其是否可启用
        tp.addTab("generateMacPanel", createMacPanel());
        tp.setEnabledAt(0, true);
        tp.setTitleAt(0, "生成mac地址");

        tp.addTab("generateTxtPanel", createTxtPanel());
        tp.setEnabledAt(1, true);
        tp.setTitleAt(1, "生成四元组txt");

        //设置其大小以及其选项卡的位置方向
        tp.setPreferredSize(new Dimension(500, 300));
        tp.setTabPlacement(JTabbedPane.TOP);
        //设置选项卡在容器内的显示形式
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // 窗口可见
        frame.setVisible(true);
    }

    /**
     * 选项卡1: 生成mac地址
     */
    private JPanel createMacPanel() {
        JPanel generateMacPanel = new JPanel();
        generateMacPanel.setLayout(new GridBagLayout());

        // 1. mac地址栏
        JPanel macStartPanel = new JPanelFactory("mac地址", null).createPanel();
        JTextField macStartText = (JTextField) macStartPanel.getComponent(2);
        macStartText.setText("mac起始地址");
        macStartText.setForeground(Color.GRAY);

        JLabel showStartMacLengthLabel = new JLabel();

        JLabel macDelimiterLabel = new JLabel(" — ");

        JPanel macEndPanel = new JPanelFactory(null, null).createPanel();
        JTextField macEndText = (JTextField) macEndPanel.getComponent(2);
        macEndText.setText("mac结束地址");
        macEndText.setForeground(Color.GRAY);

        JLabel showEndMacLengthLabel = new JLabel();

        JPanel macPanel = new JPanel();
        macPanel.setLayout(new BoxLayout(macPanel, BoxLayout.X_AXIS));
        macPanel.add(Box.createVerticalStrut(10));
        macPanel.add(macStartPanel);
        macPanel.add(showStartMacLengthLabel);
        macPanel.add(macDelimiterLabel);
        macPanel.add(macEndPanel);
        macPanel.add(showEndMacLengthLabel);

        macStartText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (macStartText.getText().trim().equals("mac起始地址")) {
                    macStartText.setText("");
                    // 设置输入的字体颜色为黑色
                    macStartText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (macStartText.getText().trim().length() > 0) {
                    showStartMacLengthLabel.setText("" + macStartText.getText().trim().length());
                } else {
                    macStartText.setText("mac起始地址");
                    // 将提示文字设置为灰色
                    macStartText.setForeground(Color.GRAY);
                    showStartMacLengthLabel.setText(null);
                }
            }
        });
        macEndText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (macEndText.getText().trim().equals("mac结束地址")) {
                    macEndText.setText("");
                    macEndText.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (macEndText.getText().trim().length() > 0) {
                    showEndMacLengthLabel.setText("" + macEndText.getText().trim().length());
                } else {
                    macEndText.setText("mac结束地址");
                    macEndText.setForeground(Color.GRAY);
                    showEndMacLengthLabel.setText(null);
                }
            }
        });

        GridBagConstraints c00 = new GridBagConstraints();
        c00.gridx = 0;
        c00.gridy = 0;
        c00.weightx = 1;
        c00.weighty = 0;
        c00.fill = GridBagConstraints.NONE;
        c00.insets.top = 10;
        c00.insets.left = 5;
        c00.anchor = GridBagConstraints.WEST;
        generateMacPanel.add(macPanel, c00);

        // 2. pid栏
        GridBagConstraints c01 = new GridBagConstraints();
        c01.gridx = 0;
        c01.gridy = 1;
        c01.weightx = 1;
        c01.weighty = 1;
        c01.fill = GridBagConstraints.BOTH;
        c01.insets.top = 10;
        c01.insets.left = 5;
        c01.anchor = GridBagConstraints.WEST;

        JPanel pidListPanel = new JPanel();
        pidListPanel.setLayout(new BoxLayout(pidListPanel, BoxLayout.Y_AXIS));
        // init first pidContent
        JPanel pidContentPanel = getPidContentPanel(initIndex);
        pidListPanel.add(pidContentPanel);
        JScrollPane pidListScrollPane = new JScrollPane(pidListPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        generateMacPanel.add(pidListScrollPane, c01);

        // 3. 添加pid按钮栏
        GridBagConstraints c02 = new GridBagConstraints();
        c02.gridx = 0;
        c02.gridy = 2;
        c02.weightx = 1;
        c02.weighty = 0;
        c02.fill = GridBagConstraints.NONE;
        c02.insets.top = 10;
        c02.insets.left = 5;
        c02.anchor = GridBagConstraints.WEST;

        JButton addPidContentPanelButton = new JButton("添加品类");
        generateMacPanel.add(addPidContentPanelButton, c02);

        addPidContentPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pidListPanel.add(getPidContentPanel(++initIndex));
                refreshPane(pidListPanel, pidListScrollPane);
                // 得到垂直滚动条,位置设置到最下面
                JScrollBar jsb = pidListScrollPane.getVerticalScrollBar();
                jsb.setValue(jsb.getMaximum());
            }
        });

        // 4. 确定按钮栏
        GridBagConstraints c03 = new GridBagConstraints();
        c03.gridx = 0;
        c03.gridy = 3;
        c03.weightx = 1;
        c03.weighty = 0;
        c03.fill = GridBagConstraints.NONE;
        c03.insets.top = 10;
        c03.insets.left = 5;
        c03.anchor = GridBagConstraints.WEST;

        JButton confirmButton = new JButton("导出mac地址Excel");
        generateMacPanel.add(confirmButton, c03);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateMacConfirmActionListener(frame, macStartText.getText().trim(), macEndText.getText().trim(), pidListPanel).actionPerformed(e);
            }
        });

        // 5. 提示栏
        GridBagConstraints c04 = new GridBagConstraints();
        c04.gridx = 0;
        c04.gridy = 4;
        c04.weightx = 1;
        c04.weighty = 0;
        c04.fill = GridBagConstraints.HORIZONTAL;
        c04.insets.top = 20;
        c04.insets.left = 5;
        c04.insets.bottom = 20;
        c04.anchor = GridBagConstraints.WEST;

        JLabel remindLabel = new JLabel();
        remindLabel.setText("<html><body>提示：<br> 1. mac地址要求：非空，16进制字符，长度小于32位； <br>2. pid非空，只支持数字和字母，区分大小写，最长64位； <br> 3. pid会转换为16进制； <br> 4. 为保障工具可用性，限制mac一次最多生成65536条（即最大：***0000 - ***ffff）。<body></html>");
        remindLabel.setForeground(Color.GRAY);
        generateMacPanel.add(remindLabel, c04);

        return generateMacPanel;
    }

    Map<String, JComponent> componentMap = new HashMap<>();

    private JPanel getPidContentPanel(int index) {
        // 2.1 pid内容行
        JPanel pidContentPanel = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                Dimension d = getPreferredSize();
                d.width = Integer.MAX_VALUE;
                return d;
            }
        };
        pidContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // 0
        JLabel indexLabel = new JLabel(index + ". ");

        // 1
        String pidPanelKey = "pidPanel" + index;
        componentMap.put(pidPanelKey, new JPanelFactory("pid", null).createPanel());
        JTextField pidText = (JTextField) componentMap.get(pidPanelKey).getComponent(2);

        // 2
        String numPanelKey = "numPanel" + index;
        componentMap.put(numPanelKey, new JPanelFactory("个数", null).createPanel());
        JTextField numText = (JTextField) componentMap.get(numPanelKey).getComponent(2);

        // 3
        String fileNamePanelKey = "fileNamePanel" + index;
        componentMap.put(fileNamePanelKey, new JPanelFactory("文件名", null).createPanel());
        JTextField fileNameText = (JTextField) componentMap.get(fileNamePanelKey).getComponent(2);

        // 4
        String currentFileNameSuffixValue = fileNameDelimiter.concat(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).concat(fileNameSuffix);
        JLabel fileNameSuffixLabel = new JLabel();
        fileNameSuffixLabel.setText(currentFileNameSuffixValue);
        fileNameSuffixLabel.setForeground(Color.GRAY);
        String fileNameSuffixLabelKey = "fileNameSuffixLabel" + index;
        componentMap.put(fileNameSuffixLabelKey, fileNameSuffixLabel);
        JLabel currentLabel = (JLabel) componentMap.get(fileNameSuffixLabelKey);
        // 监听文本框内容显示到文件名后缀
        pidText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                try {
                    String text = document.getText(0, document.getLength()).trim();
                    if (text.length() > 0) {
                        currentLabel.setText(fileNameDelimiter.concat(text.trim()).concat(currentFileNameSuffixValue));
                    } else {
                        currentLabel.setText(currentFileNameSuffixValue);
                    }
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                try {
                    String text = document.getText(0, document.getLength()).trim();
                    if (text.length() > 0) {
                        currentLabel.setText(fileNameDelimiter.concat(text.trim()).concat(currentFileNameSuffixValue));
                    } else {
                        currentLabel.setText(currentFileNameSuffixValue);
                    }
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                Document document = e.getDocument();
                try {
                    String text = document.getText(0, document.getLength()).trim();
                    if (text.length() > 0) {
                        currentLabel.setText(fileNameDelimiter.concat(text.trim()).concat(currentFileNameSuffixValue));
                    } else {
                        currentLabel.setText(currentFileNameSuffixValue);
                    }
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
        });

        // 5
        String btnNameKey = "delPidContentPanelButton" + index;
        componentMap.put(btnNameKey, new JButton("删除"));
        // 删除一行
        ((JButton) componentMap.get(btnNameKey)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container listContainer = componentMap.get(btnNameKey).getParent().getParent();
                if (listContainer == null || listContainer.getComponentCount() < 1) {
//                    JOptionPane.showMessageDialog(frame, "再删就秃了", "Warning", JOptionPane.WARNING_MESSAGE);
                    refreshPane(listContainer, null);
                    return;
                }
                JPanel delPidContentPanelButtonParent = (JPanel) componentMap.get(btnNameKey).getParent();
                JLabel currentComponentNumLabel = (JLabel) delPidContentPanelButtonParent.getComponent(0);
                int currentComponentNum = Integer.parseInt(currentComponentNumLabel.getText().substring(0, currentComponentNumLabel.getText().indexOf(". ")));
                listContainer.remove(delPidContentPanelButtonParent);
                for (int i = --currentComponentNum; i < listContainer.getComponentCount(); i++) {
                    JLabel numLabel = (JLabel) ((JPanel) listContainer.getComponent(i)).getComponent(0);
                    String numStr = numLabel.getText().substring(0, numLabel.getText().indexOf(". "));
                    numLabel.setText(Integer.parseInt(numStr) - 1 + ". ");
                }
                initIndex = listContainer.getComponentCount();
                refreshPane(listContainer, null);
            }
        });

        pidContentPanel.add(indexLabel);
        pidContentPanel.add(componentMap.get(pidPanelKey));
        pidContentPanel.add(componentMap.get(numPanelKey));
        pidContentPanel.add(componentMap.get(fileNamePanelKey));
        pidContentPanel.add(currentLabel);
        pidContentPanel.add(componentMap.get(btnNameKey));

        return pidContentPanel;
    }

    // 刷新界面
    private void refreshPane(Container container, JScrollPane jsp) {
        // 添加或删除组件后,更新窗口
        SwingUtilities.updateComponentTreeUI(container);
        // 得到垂直滚动条,位置设置到最下面
//        JScrollBar jsb = jsp.getVerticalScrollBar();
//        jsb.setValue(jsb.getMaximum());
    }


    /**
     * 选项卡2: 生成四元组txt
     */
    private JPanel createTxtPanel() {
        JPanel generateTxtPanel = new JPanel();
        generateTxtPanel.setLayout(new GridBagLayout());

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.NONE;
        c1.insets.top = 10;
        c1.insets.left = 5;
        c1.anchor = GridBagConstraints.WEST;
        JPanel pidPanel = new JPanelFactory("pid", null).createPanel();
        generateTxtPanel.add(pidPanel, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.NONE;
        c2.insets.top = 10;
        c2.insets.left = 5;
        c2.anchor = GridBagConstraints.WEST;
        JPanel cidPanel = new JPanelFactory("cid", null).createPanel();
        generateTxtPanel.add(cidPanel, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 2;
        c3.weightx = 1.0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.NONE;
        c3.insets.top = 10;
        c3.insets.left = 5;
        c3.anchor = GridBagConstraints.WEST;
        JButton chooseFile = new JButton("选择三元组文件...");
        chooseFile.setBackground(new Color(238, 238, 238));
        generateTxtPanel.add(chooseFile, c3);

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 0;
        c4.gridy = 3;
        c4.weightx = 1.0;
        c4.weighty = 0;
        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.insets.top = 10;
        c4.insets.left = 5;
        c4.anchor = GridBagConstraints.WEST;
        JLabel showFileLabel = new JLabel();
        generateTxtPanel.add(showFileLabel, c4);

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
                }

            }
        });

        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 0;
        c5.gridy = 4;
        c5.weightx = 1.0;
        c5.weighty = 0;
        c5.fill = GridBagConstraints.NONE;
        c5.insets.top = 10;
        c5.insets.left = 5;
        c5.anchor = GridBagConstraints.WEST;
        // 确定按钮
        JButton telinkConfirmButton = new JButton("导出telink四元组txt");
        generateTxtPanel.add(telinkConfirmButton, c5);

        telinkConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pidTxtValue = ((JTextField) pidPanel.getComponent(2)).getText().trim();
                String cidTxtValue = ((JTextField) cidPanel.getComponent(2)).getText().trim();
                new ParseFileConfirmActionListener(frame, "telink", pidTxtValue, cidTxtValue, showFileLabel.getText());
            }
        });

        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 0;
        c6.gridy = 5;
        c6.weightx = 1.0;
        c6.weighty = 0;
        c6.fill = GridBagConstraints.NONE;
        c6.insets.top = 10;
        c6.insets.left = 5;
        c6.anchor = GridBagConstraints.WEST;
        // 确定按钮
        JButton realtekConfirmButton = new JButton("导出realtek三元组txt");
        //generateTxtPanel.add(Box.createVerticalStrut(20));
        generateTxtPanel.add(realtekConfirmButton, c6);

        realtekConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pidTxtValue = ((JTextField) pidPanel.getComponent(2)).getText().trim();
                String cidTxtValue = ((JTextField) cidPanel.getComponent(2)).getText().trim();
                new ParseFileConfirmActionListener(frame, "realtek", pidTxtValue, cidTxtValue, showFileLabel.getText());
            }
        });

        GridBagConstraints c7 = new GridBagConstraints();
        c7.gridx = 0;
        c7.gridy = 6;
        c7.weightx = 1.0;
        c7.weighty = 1.0;
        c7.fill = GridBagConstraints.BOTH;
        c7.insets.top = 10;
        c7.insets.left = 5;
        c7.anchor = GridBagConstraints.WEST;
        JLabel remindLabel2 = new JLabel();
        remindLabel2.setText("<html><body>提示：<br> 1. pid非空，只支持数字和字母，区分大小写，最长64位； <br> 2. telink方案pid会转换为16进制；realtek方案pid会转换为16进制的10进制； <br> 3. telink方案: cid非空，只支持数字和字母，区分大小写，最长64位；(realtek方案不处理cid) <br> 4. 点击按钮选择文件保存位置，输入文件名生成对应文件。 <body></html>");
        remindLabel2.setForeground(Color.GRAY);
        //generateTxtPanel.add(Box.createVerticalStrut(20));
        generateTxtPanel.add(remindLabel2, c7);

        return generateTxtPanel;
    }


    class JPanelFactory extends JPanel {
        public JTextField jtf;
        public JLabel jl;

        public JPanelFactory(String labelName, Integer index) {
            if (index == null) {
                jl = new JLabel(labelName + ": ");
            } else {
                jl = new JLabel(labelName + index + ": ");
            }
            if (labelName == null) {
                jl = new JLabel();
            }
            jtf = new JTextField(20);
            jtf.setMaximumSize(new Dimension(160, 25));
            jtf.setMinimumSize(new Dimension(160, 25));
//            this.setLayout(new FlowLayout(FlowLayout.LEFT));
//            this.add(jl);
//            this.add(jtf);

//            add(jl);
//            add(jtf);
        }

        public JPanel createPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            // 加入一个不可见的Strut，使Panel对顶部留出一定的空间
            panel.add(Box.createVerticalStrut(10));
            panel.add(jl);
            panel.add(jtf);
            return panel;
        }

        public String getJTFValue() {
            return jtf.getText();
        }

        public void setJTFValue(String value) {
            jtf.setText(value);
        }
    }

}
