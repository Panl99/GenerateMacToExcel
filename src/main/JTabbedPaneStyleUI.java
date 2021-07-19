package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lp
 * @date 2021/6/13 13:39
 * @description
 **/
public class JTabbedPaneStyleUI {

    public void createGUI() {

        JFrame frame = new JFrame();
        frame.setTitle("HiLink平台三元组自动生成工具_2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 300, 800, 600);
        // 进制窗口拉伸
        frame.setResizable(false);

        //创建一个选项卡容器，添加到顶层容器中
        JTabbedPane tp = new JTabbedPane();
        frame.setContentPane(tp);

        // 选项卡1: 生成mac地址
        JPanel generateMacPanel = new JPanel();
        generateMacPanel.setLayout(null);
//        generateMacPanel.setBorder(BorderFactory.createTitledBorder("生成mac地址"));
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


        //添加选项卡容器，并且设置其中每个选项卡的标签以及其是否可启用
        tp.addTab("generateMacPanel", generateMacPanel);
        tp.setEnabledAt(0, true);
        tp.setTitleAt(0, "生成mac地址");


        // 选项卡2: 生成四元组txt
        JPanel generateTxtPanel = new JPanel();
        generateTxtPanel.setLayout(null);

        tp.addTab("generateTxtPanel", generateTxtPanel);
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
