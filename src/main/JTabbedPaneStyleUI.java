package main;

import javax.swing.*;
import java.awt.*;

/**
 * @author lp
 * @date 2021/6/13 13:39
 * @description
 **/
public class JTabbedPaneStyleUI {

    public void createGUI() {

        JFrame frame = new JFrame("BY LIUPAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 300, 500, 400);
        // 进制窗口拉伸
        frame.setResizable(false);

        //创建一个选项卡容器，添加到顶层容器中
        JTabbedPane tp = new JTabbedPane();
        frame.setContentPane(tp);

        JPanel generateMacPanel = new JPanel();
        JPanel generateTxtPanel = new JPanel();
        generateMacPanel.setLayout(null);
        generateTxtPanel.setLayout(null);

        JLabel macStartLabel = new JLabel("mac首地址:");
        macStartLabel.setBounds(10,20,80,25);
        generateMacPanel.add(macStartLabel);

        JTextField macStartText = new JTextField(20);
        macStartText.setBounds(100,20,165,25);
        generateMacPanel.add(macStartText);

        JLabel macEndLabel = new JLabel("mac尾地址:");
        macEndLabel.setBounds(10,50,80,25);
        generateMacPanel.add(macEndLabel);

        JTextField macEndText = new JTextField(20);
        macEndText.setBounds(100,50,165,25);
        generateMacPanel.add(macEndText);

        // 确定按钮
        JButton confirmButton = new JButton("生成mac地址");
        confirmButton.setBounds(10, 100, 120, 25);
        generateMacPanel.add(confirmButton);

//        confirmButton.addActionListener(new CreateMacConfirmActionListener(frame, null, macStartText.getText().trim(), macEndText.getText().trim()));


        //添加选项卡容器，并且设置其中每个选项卡的标签以及其是否可启用
        tp.addTab("generateMacPanel", generateMacPanel);
        tp.setEnabledAt(0, true);
        tp.setTitleAt(0, "生成mac地址");

        tp.addTab("generateTxtPanel", generateTxtPanel);
        tp.setEnabledAt(1, true);
        tp.setTitleAt(1,"生成四元组txt");

        //设置其大小以及其选项卡的位置方向
        tp.setPreferredSize(new Dimension(500, 300));
        tp.setTabPlacement(JTabbedPane.TOP);
        //设置选项卡在容器内的显示形式
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

//        //创建八个标签组件，将五个中间容器设置为流布局，并且将标签组件分别放入到其中
//        JLabel l1 = new JLabel("工资状况");
//        JLabel l2 = new JLabel("3000元/月");
//        JLabel l3 = new JLabel("奖金状况");
//        JLabel l4 = new JLabel("1500元/月");
//        JLabel l5 = new JLabel("津贴状况");
//        JLabel l6 = new JLabel("500元/月");
//        JLabel l7 = new JLabel("社保状况");
//        JLabel l8 = new JLabel("200元/月");
//        generateTxtPanel.setLayout(new FlowLayout());
//        panel3.setLayout(new FlowLayout());
//        panel4.setLayout(new FlowLayout());
//        panel5.setLayout(new FlowLayout());
//        generateTxtPanel.add(l1);
//        generateTxtPanel.add(l2);
//        panel3.add(l3);
//        panel3.add(l4);
//        panel4.add(l5);
//        panel4.add(l6);
//        panel5.add(l7);
//        panel5.add(l8);
//        frame.pack();

        // 窗口可见
        frame.setVisible(true);
    }
}
