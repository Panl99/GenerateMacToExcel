package main;

import javax.swing.*;

/**
 * @author lp
 * @date 2021/6/11 23:27
 * @description
 **/
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 设置窗口风格
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                new JPanelStyleUI().createGUI();
//                new JTabbedPaneStyleUI().createGUI();
            }
        });

    }
}
