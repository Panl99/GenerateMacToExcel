package main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lp
 * @date 2021/7/19 16:23
 **/
public class Test {

 /*   public static void main(String[] args) {
        // 动态添加品类行
        JPanel pidContentPanel = new JPanel();
        Container c = new Container();
        c.setLayout(new BoxLayout(c, BoxLayout.LINE_AXIS));

        String[] titleName = { "pid", "个数", "文件名", "操作"};
        String[][] rowData = { {"","","",""} };
        // 创建表格
        JTable table = new JTable(new DefaultTableModel(rowData, titleName));
        // 创建包含表格的滚动窗格
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 定义 topPanel 的布局为 BoxLayout，BoxLayout 为垂直排列
        pidContentPanel.setLayout(new BoxLayout(pidContentPanel, BoxLayout.Y_AXIS));
        // 先加入一个不可见的 Strut，从而使 topPanel 对顶部留出一定的空间
        pidContentPanel.add(Box.createVerticalStrut(10));
        // 加入包含表格的滚动窗格
        pidContentPanel.add(scrollPane);
        // 再加入一个不可见的 Strut，从而使 topPanel 和 middlePanel 之间留出一定的空间
        pidContentPanel.add(Box.createVerticalStrut(10));

        // 创建窗体
        JFrame frame = new JFrame("Boxlayout 演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        pidContentPanel.setOpaque(true);
        frame.setLocation(500,300);
        frame.setSize(new Dimension(800, 600));
        frame.setContentPane(pidContentPanel);
        frame.setVisible(true);
    }*/

    //本类继承自JFrame 实现了 ActionListener接口
    public static class DemoFrame1 extends JFrame implements ActionListener {
        JPanel jpc ;//存放组件的面板
        JScrollPane jsp;//滚动面板
        JButton jbAdd ,jbRemove,jbReset;// 增加,删除按钮
        int index = 1;//开始的字符

        //构造函数
        public DemoFrame1() {
            jpc = new JPanel();
            jpc.setLayout(new BoxLayout(jpc,  BoxLayout.Y_AXIS));//盒子布局.从上到下
            jsp = new JScrollPane(jpc,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
            add(jps,BorderLayout.SOUTH);
            setTitle("增删组件");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(300, 220);//大小
            setLocationRelativeTo(null);//居中
        }
        //main函数
        public static void main(String[] args) {
            new DemoFrame1().setVisible(true);//初始化并可见
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jb = (JButton) e.getSource();
            if(jb==jbAdd) {//当点击添加按钮时
                jpc.add(new MyJPanel(index));//添加1个自己定义的面板组件
                index++;//自加1
                myUpdateUI();//刷新界面
            }else if(jb ==jbRemove) {//当点击删除按钮时
                if(jpc.getComponentCount()>0) { // 得到jpc里的MyJPanel的组件数量
                    jpc.remove(jpc.getComponentCount()-1);//删除末尾的一个组件 ,
                    index-=1;
                    myUpdateUI();
                }
            }else if(jb==jbReset) {
                for (int i = 0; i < jpc.getComponentCount(); i++) {
                    MyJPanel mjp = (MyJPanel) jpc.getComponent(i);
                    //也就是说取值,可以根据文本框所在的位置来取
                    System.out.println("第"+(i+1)+"个文本框的值是"+mjp.getJTFValue());
                    mjp.setJTFValue("");//清空,重置
                    System.out.println("第"+(i+1)+"个文本框的值已清空重置");
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
    static class MyJPanel extends JPanel{
        public JTextField jtf;
        public MyJPanel(int index) {
            JLabel jl = new JLabel("字符"+index);
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


    /**
     *
     */
    public static void boxLayoutTest() {
//        // 创建 topPanel
//        createTopPanel();
//        // 创建 middlePanel
//        createMiddlePanel();
//        // 创建 bottomPanel
//        createBottomPanel();
        // 创建包含 topPanel，middlePanel 和 bottomPanel 的 panelContainer
        JPanel panelContainer = new JPanel();
        //panelContainer 的布局为 GridBagLayout
        panelContainer.setLayout(new GridBagLayout());

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1.0;
        c1.weighty = 1.0;
        c1.fill = GridBagConstraints.BOTH ;
        // 加入 topPanel
        panelContainer.add(createTopPanel(), c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.HORIZONTAL ;
        // 加入 middlePanel
        panelContainer.add(createMiddlePanel(), c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 2;
        c3.weightx = 1.0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.HORIZONTAL ;
        // 加入 bottomPanel
        panelContainer.add(createBottomPanel(), c3);

        // 创建窗体
        JFrame frame = new JFrame("Boxlayout 演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        panelContainer.setOpaque(true);
        frame.setSize(new Dimension(480, 320));
        frame.setContentPane(panelContainer);
        frame.setVisible(true);
    }

    static JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        String[] columnName = { "姓名", "性别", "单位", "参加项目", "备注" };
        String[][] rowData = { { "张三", "男", "计算机系", "100 米 ,200 米", "" },
                { "李四", "男", "化学系", "100 米，铅球", "" },
        };
        // 创建表格
        JTable table = new JTable(new DefaultTableModel(rowData, columnName));
        // 创建包含表格的滚动窗格
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // 定义 topPanel 的布局为 BoxLayout，BoxLayout 为垂直排列
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        // 先加入一个不可见的 Strut，从而使 topPanel 对顶部留出一定的空间
        topPanel.add(Box.createVerticalStrut(10));
        // 加入包含表格的滚动窗格
        topPanel.add(scrollPane);
        // 再加入一个不可见的 Strut，从而使 topPanel 和 middlePanel 之间留出一定的空间
        topPanel.add(Box.createVerticalStrut(10));
        return topPanel;
    }

    static JPanel createMiddlePanel() {
        // 创建 middlePanel
        JPanel middlePanel = new JPanel();
        // 采用水平布局
        middlePanel .setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS ));
        // 创建标签运动会项目
        JLabel sourceLabel = new JLabel("运动会项目：");
        sourceLabel.setAlignmentY(Component.TOP_ALIGNMENT );
        sourceLabel.setBorder(BorderFactory.createEmptyBorder (4, 5, 0, 5));
        // 创建列表运动会项目
        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("100 米");
        listModel.addElement("200 米");
        listModel.addElement("400 米");
        listModel.addElement("跳远");
        listModel.addElement("跳高");
        listModel.addElement("铅球");
        JList sourceList = new JList(listModel);
        sourceList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        sourceList.setVisibleRowCount(5);
        JScrollPane sourceListScroller = new JScrollPane(sourceList);
        sourceListScroller.setPreferredSize(new Dimension(120, 80));
        sourceListScroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        sourceListScroller.setAlignmentY(Component.TOP_ALIGNMENT );
        // 创建最左边的 Panel
        JPanel sourceListPanel = new JPanel();
        // 最左边的 Panel 采用水平布局
        sourceListPanel.setLayout(new BoxLayout(sourceListPanel,
                BoxLayout.X_AXIS ));
        // 加入标签到最左边的 Panel
        sourceListPanel.add(sourceLabel);
        // 加入列表运动会项目到最左边的 Panel
        sourceListPanel.add(sourceListScroller);
        sourceListPanel.setAlignmentY(Component.TOP_ALIGNMENT );
        sourceListPanel.setBorder(BorderFactory.createEmptyBorder (0, 0, 0, 30));
        // 将最左边的 Panel 加入到 middlePanel
        middlePanel .add(sourceListPanel);
        // 定义中间的两个按钮
        JButton toTargetButton = new JButton(">>");
        JButton toSourceButton = new JButton("<<");
        // 定义中间的 Panel
        JPanel buttonPanel = new JPanel();
        // 中间的 Panel 采用水平布局
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS ));
        // 将按钮 >> 加入到中间的 Panel
        buttonPanel.add(toTargetButton);

//两个按钮之间加入一个不可见的 rigidArea
        buttonPanel.add(Box.createRigidArea (new Dimension(15, 15)));
        // 将按钮 << 加入到中间的 Panel
        buttonPanel.add(toSourceButton);
        buttonPanel.setAlignmentY(Component.TOP_ALIGNMENT );
        buttonPanel.setBorder(BorderFactory.createEmptyBorder (15, 5, 15, 5));
        // 将中间的 Panel 加入到 middlePanel
        middlePanel .add(buttonPanel);
        // 创建标签查询项目
        JLabel targetLabel = new JLabel("查询项目：");
        targetLabel.setAlignmentY(Component.TOP_ALIGNMENT );
        targetLabel.setBorder(BorderFactory.createEmptyBorder (4, 5, 0, 5));

// 创建列表查询项目
        DefaultListModel targetListModel = new DefaultListModel();
        targetListModel.addElement("100 米");
        JList targetList = new JList(targetListModel);
        targetList
                .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        targetList.setVisibleRowCount(5);
        JScrollPane targetListScroller = new JScrollPane(targetList);
        targetListScroller.setPreferredSize(new Dimension(120, 80));
        targetListScroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        targetListScroller.setAlignmentY(Component.TOP_ALIGNMENT );
        // 创建最右边的 Panel
        JPanel targetListPanel = new JPanel();
        // 设置最右边的 Panel 为水平布局
        targetListPanel.setLayout(new BoxLayout(targetListPanel,
                BoxLayout.X_AXIS ));
        // 将标签查询项目加到最右边的 Panel
        targetListPanel.add(targetLabel);
        // 将列表查询项目加到最右边的 Panel
        targetListPanel.add(targetListScroller);
        targetListPanel.setAlignmentY(Component.TOP_ALIGNMENT );
        targetListPanel.setBorder(BorderFactory.createEmptyBorder (0, 30, 0, 0));
        // 最后将最右边的 Panel 加入到 middlePanel
        middlePanel.add(targetListPanel);

        return middlePanel;
    }


    static JPanel createBottomPanel() {
        // 创建查询按钮
        JButton actionButton = new JButton("查询");
        // 创建退出按钮
        JButton closeButton = new JButton("退出");
        // 创建 bottomPanel
        JPanel bottomPanel = new JPanel();
        // 设置 bottomPanel 为垂直布局
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS ));
        // 创建包含两个按钮的 buttonPanel
        JPanel buttonPanel = new JPanel();
        // 设置 bottomPanel 为水平布局
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS ));
        // 将查询按钮加入到 buttonPanel
        buttonPanel.add(actionButton);

//加入一个 glue, glue 会挤占两个按钮之间的空间
        buttonPanel.add(Box.createHorizontalGlue ());
        // 将退出按钮加入到 buttonPanel
        buttonPanel.add(closeButton);
        // 加入一个 Strut，从而使 bottomPanel 和 middlePanel 上下之间留出距离
        bottomPanel .add(Box.createVerticalStrut (10));
        // 加入 buttonPanel
        bottomPanel .add(buttonPanel);
        // 加入一个 Strut，从而使 bottomPanel 和底部之间留出距离
        bottomPanel.add(Box.createVerticalStrut (10));
        return bottomPanel;
    }


    /**
     *
     */
    /*JPanel contentPanel=(JPanel) this.getContentPane();

    JPanel jp=new JPanel();

    JPanel jp1=new JPanel();

    JButton jb=new JButton("add_JTextField");

    public Main() throws Exception {
        super("myFrame");

        this.setSize(800,600);

        this.setResizable(false);

        this.setLocation(this.getToolkit().getScreenSize().width/2-400,this.getToolkit().getScreenSize().height/2-300);

        contentPanel.setLayout(new BorderLayout());

        contentPanel.add("Center",jp);

        jp.setBackground(Color.white);

        jp.setLayout(new BoxLayout(jp,BoxLayout.PAGE_AXIS));

        contentPanel.add("South",jp1);

        jp1.add(jb);

        jb.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((JButton)e.getSource()==jb) {
            jp.add(new JTextField("",20));

            jp.validate();

        }

    }

    public static void main(String[] args) throws Exception {
        new Main();

    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);

        }

    }*/
}
