package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lp
 * @date 2021/6/13 13:39
 * @description
 **/
public class JTabbedPaneStyleUI {
    private JFrame frame;

    public void createGUI() {

        frame = new JFrame();
        frame.setTitle("HiLink平台三元组自动生成工具_2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 300, 800, 600);
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

    // 选项卡1: 生成mac地址
    private JPanel createMacPanel() {
        JPanel generateMacPanel = new JPanel();
        generateMacPanel.setLayout(null);
        generateMacPanel.setSize(800, 600);

        JLabel macStartLabel = new JLabel("mac地址:");
        macStartLabel.setBounds(10, 20, 60, 25);
        generateMacPanel.add(macStartLabel);

        JTextField macStartText = new JTextField(20);
        macStartText.setText("mac起始地址");
        macStartText.setForeground(Color.GRAY);
        macStartText.setBounds(80, 20, 160, 25);
        generateMacPanel.add(macStartText);

        JLabel showStartMacLengthLabel = new JLabel();
        showStartMacLengthLabel.setBounds(240, 20, 20, 20);
        generateMacPanel.add(showStartMacLengthLabel);

        JLabel macDelimiterLabel = new JLabel(" — ");
        macDelimiterLabel.setBounds(260, 20, 20, 25);
        generateMacPanel.add(macDelimiterLabel);


        JTextField macEndText = new JTextField(20);
        macEndText.setText("mac结束地址");
        macEndText.setForeground(Color.GRAY);
        macEndText.setBounds(300, 20, 160, 25);
        generateMacPanel.add(macEndText);

        JLabel showEndMacLengthLabel = new JLabel();
        showEndMacLengthLabel.setBounds(460, 20, 20, 20);
        generateMacPanel.add(showEndMacLengthLabel);

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
/*
        // 动态添加品类行
        JPanel pidContentPanel = new JPanel();
        pidContentPanel.setLayout(new BoxLayout(pidContentPanel, BoxLayout.Y_AXIS));
//        pidContentPanel.setSize(800, 25);
        pidContentPanel.setBounds(10, 50, 800, 400);

        Container pidContainer = new Container();
        pidContainer.setLayout(new BoxLayout(pidContainer, BoxLayout.X_AXIS));
        pidContainer.setSize(new Dimension(800, 25));

        JScrollPane pidContentScrollPane = new JScrollPane(pidContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "日志", TitledBorder.CENTER, TitledBorder.TOP));
        pidContentScrollPane.setBounds(10, 50, 800, 400);

        JLabel prodIdLabel = new JLabel("pid:");
//        prodIdLabel.setBounds(10, 50, 50, 25);
        pidContainer.add(prodIdLabel);

        JTextField prodIdText = new JTextField(20);
//        prodIdText.setBounds(60, 50, 160, 25);
        pidContainer.add(prodIdText);

        JLabel pidNumLabel = new JLabel("个数:");
//        pidNumLabel.setBounds(220, 50, 50, 25);
        pidContainer.add(pidNumLabel);

        JTextField pidNumText = new JTextField(20);
        pidNumText.setSize(new Dimension(50, 25));
//        pidNumText.setBounds(270, 50, 50, 25);
        pidContainer.add(pidNumText);

        JLabel exportFileNameLabel = new JLabel("导出文件名:");
//        exportFileNameLabel.setBounds(320, 50, 80, 25);
        pidContainer.add(exportFileNameLabel);

        JTextField exportFileNameText = new JTextField(20);
//        exportFileNameText.setBounds(400, 50, 120, 25);
        pidContainer.add(exportFileNameText);

        JLabel exportFileNameSuffixLabel = new JLabel();
        exportFileNameSuffixLabel.setText("-" + prodIdText.getText().trim() + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx");
//        exportFileNameSuffixLabel.setBounds(520, 50, 200, 25);
        pidContainer.add(exportFileNameSuffixLabel);

        // 删除pid容器
        JButton delPidContainerButton = new JButton("删除");
//        delPidContainerButton.setBounds(720, 50, 80, 25);
        pidContainer.add(delPidContainerButton);

        pidContentPanel.add(pidContentScrollPane);
        generateMacPanel.add(pidContentPanel);*/


//        // 添加品类按钮
//        JButton addPidContentPanelButton = new JButton("添加品类");
//        addPidContentPanelButton.setBounds(10, 70, 120, 25);
//        generateMacPanel.add(addPidContentPanelButton);


//        frame.add(PidFrame.createPidContainer());


        // 确定按钮
        JButton confirmButton = new JButton("生成mac地址");
        confirmButton.setBounds(10, 500, 120, 25);
        generateMacPanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new CreateMacConfirmActionListener(frame, null, macStartText.getText().trim(), macEndText.getText().trim(), prodIdText.getText().trim()).actionPerformed(e);
            }
        });

        return generateMacPanel;
    }


    // 选项卡2: 生成四元组txt
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
        generateTxtPanel.add(new JPanelFactory("pid", null).createPanel(), c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.NONE;
        c2.insets.top = 10;
        c2.insets.left = 5;
        c2.anchor = GridBagConstraints.WEST;
        generateTxtPanel.add(new JPanelFactory("cid", null).createPanel(), c2);

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
        JButton telinkConfirmButton = new JButton("生成telink四元组");
        generateTxtPanel.add(telinkConfirmButton, c5);

        telinkConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pidTxtValue = ((JPanelFactory) generateTxtPanel.getComponent(0)).getJTFValue().trim();
                String cidTxtValue = ((JPanelFactory) generateTxtPanel.getComponent(1)).getJTFValue().trim();
                new ParseFileConfirmActionListener(frame, null, pidTxtValue, cidTxtValue, showFileLabel.getText());
                JOptionPane.showMessageDialog(frame, "telinkConfirmButton", "Warning", JOptionPane.WARNING_MESSAGE);
                // TODO save file
                String ss = "aaaaaa \n tttttt";
                JFileChooser jfc = new javax.swing.JFileChooser();
                if(JFileChooser.APPROVE_OPTION == jfc.showSaveDialog(new JPanel())) {
                    File saveFile = jfc.getSelectedFile();
                    try {
                        if (!saveFile.exists()) {
                            saveFile.createNewFile();
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
                        bw.write(ss);
                        bw.close();

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "保存telink文件发生错误！", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
        JButton realtekConfirmButton = new JButton("生成realtek三元组");
        //generateTxtPanel.add(Box.createVerticalStrut(20));
        generateTxtPanel.add(realtekConfirmButton, c6);

        realtekConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new ParseFileConfirmActionListener(frame, null, productIdText.getText().trim(), cIdText.getText().trim(), showFileLabel.getText());
                JOptionPane.showMessageDialog(frame, "realtekConfirmButton", "Warning", JOptionPane.WARNING_MESSAGE);
                // TODO save file
                String ss = "aaaaaa \n tttttt";
                JFileChooser jfc = new javax.swing.JFileChooser();
                if(JFileChooser.APPROVE_OPTION == jfc.showSaveDialog(new JPanel())) {
                    File saveFile = jfc.getSelectedFile();
                    try {
                        if (!saveFile.exists()) {
                            saveFile.createNewFile();
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
                        bw.write(ss);
                        bw.close();

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "保存realtek文件发生错误！", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
        remindLabel2.setText("<html><body>提示：<br> 1. pid非空，只支持数字和字母，区分大小写，最长64位； <br> 2. 生成realtek方案的三元组文件时，pid会转换为10进制；(telink方案pid不变) <br> 3. telink方案: cid非空，只支持数字和字母，区分大小写，最长64位；(realtek方案不处理cid) <br> 4. 点击按钮选择文件保存位置，输入文件名生成对应文件。 <body></html>");
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
            jtf = new JTextField(20);
//            jtf.setSize(160, 25);
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







    JPanel jpc;//存放组件的面板
    JScrollPane jsp;//滚动面板
    JButton jbAdd, jbRemove, jbReset;// 增加,删除按钮
    int index = 1;//开始的字符
    public Container createPidContainer() {
        jpc = new JPanel();
        // 盒子布局.从上到下
        jpc.setLayout(new BoxLayout(jpc, BoxLayout.Y_AXIS));
        // 滚动面板
        jsp = new JScrollPane(jpc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Container c = new Container();
        c.add(jsp);

        jbAdd = new JButton("增加");
        jbAdd.addActionListener(new PidJBListener());
        jbRemove = new JButton("删除");
        jbRemove.addActionListener(new PidJBListener());
        jbReset = new JButton("重置");
        jbReset.addActionListener(new PidJBListener());

        return c;
    }

    class PidJBListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jb = (JButton) e.getSource();
            if (jb == jbAdd) {//当点击添加按钮时
                jpc.add(new MyJPanel(index));//添加1个自己定义的面板组件
                index++;//自加1
                myUpdateUI();//刷新界面
            } else if (jb == jbRemove) {//当点击删除按钮时
                if (jpc.getComponentCount() > 0) { // 得到jpc里的MyJPanel的组件数量
                    jpc.remove(jpc.getComponentCount() - 1);//删除末尾的一个组件 ,
                    index -= 1;
                    myUpdateUI();
                }
            } else if (jb == jbReset) {
                for (int i = 0; i < jpc.getComponentCount(); i++) {
                    MyJPanel mjp = (MyJPanel) jpc.getComponent(i);
                    //也就是说取值,可以根据文本框所在的位置来取
                    System.out.println("第" + (i + 1) + "个文本框的值是" + mjp.getJTFValue());
                    mjp.setJTFValue("");//清空,重置
                    System.out.println("第" + (i + 1) + "个文本框的值已清空重置");
                }
            }
        }

        //刷新界面函数
        private void myUpdateUI() {
            SwingUtilities.updateComponentTreeUI(jsp);//添加或删除组件后,更新窗口
            JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
            jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
        }
    }

    //本类继承自JFrame 实现了 ActionListener接口
    static class PidFrame extends JFrame implements ActionListener {
        JPanel jpc;//存放组件的面板
        JScrollPane jsp;//滚动面板
        JButton jbAdd, jbRemove, jbReset;// 增加,删除按钮
        int index = 1;//开始的字符

        //构造函数
        public PidFrame() {
            jpc = new JPanel();
            jpc.setLayout(new BoxLayout(jpc, BoxLayout.Y_AXIS));//盒子布局.从上到下
            jsp = new JScrollPane(jpc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(jsp);

            jbAdd = new JButton("增加");
            jbAdd.addActionListener(this);
            jbRemove = new JButton("删除");
            jbRemove.addActionListener(this);
            jbReset = new JButton("重置");
            jbReset.addActionListener(this);
            JPanel jps = new JPanel();
            jps.add(jbAdd);
            jps.add(jbRemove);
            jps.add(jbReset);
            add(jps, BorderLayout.SOUTH);
            setTitle("增删组件");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(300, 220);//大小
            setLocationRelativeTo(null);//居中
        }
        //main函数
//        public static void main(String[] args) {
//            new DemoFrame1().setVisible(true);//初始化并可见
//        }

        public static JPanel createPidContainer() {
            return new PidFrame().jpc;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jb = (JButton) e.getSource();
            if (jb == jbAdd) {//当点击添加按钮时
                jpc.add(new MyJPanel(index));//添加1个自己定义的面板组件
                index++;//自加1
                myUpdateUI();//刷新界面
            } else if (jb == jbRemove) {//当点击删除按钮时
                if (jpc.getComponentCount() > 0) { // 得到jpc里的MyJPanel的组件数量
                    jpc.remove(jpc.getComponentCount() - 1);//删除末尾的一个组件 ,
                    index -= 1;
                    myUpdateUI();
                }
            } else if (jb == jbReset) {
                for (int i = 0; i < jpc.getComponentCount(); i++) {
                    MyJPanel mjp = (MyJPanel) jpc.getComponent(i);
                    //也就是说取值,可以根据文本框所在的位置来取
                    System.out.println("第" + (i + 1) + "个文本框的值是" + mjp.getJTFValue());
                    mjp.setJTFValue("");//清空,重置
                    System.out.println("第" + (i + 1) + "个文本框的值已清空重置");
                }
            }

        }

        //刷新界面函数
        private void myUpdateUI() {
            SwingUtilities.updateComponentTreeUI(this);//添加或删除组件后,更新窗口
            JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
            jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
        }

    }

    //自定义一个JPanle类
    static class MyJPanel extends JPanel {
        public JTextField jtf;

        public MyJPanel(int index) {
            JLabel jl = new JLabel("字符" + index);
            jtf = new JTextField(15);
            add(jl);
            add(jtf);
        }

        //获取文本框的值
        public String getJTFValue() {
            return jtf.getText();
        }

        //设置文本框的值
        public void setJTFValue(String value) {
            jtf.setText(value);
        }
    }
}
