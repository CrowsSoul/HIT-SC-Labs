package DutyRosterApp;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@SuppressWarnings("serial")
public class DutyRosterWindow extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public DutyRosterWindow(List<Object[]> data) {
        setTitle("Duty Roster Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // 创建表头
        String[] columnNames = {"日期", "值班人", "职位", "手机号码"};

        // 创建表格模型
        model = new DefaultTableModel(columnNames, 0);
        
        //这里对接收的数据按照日期从小到大排序
        Collections.sort(data, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                LocalDate date1 = (LocalDate) o1[0];
                LocalDate date2 = (LocalDate) o2[0];
                return date1.compareTo(date2);
            }
        });

        // 将数据添加到表格模型
        for (Object[] row : data) {
            model.addRow(row);
        }

        // 创建表格并设置模型
        table = new JTable(model);

        // 将表格放在滚动窗格中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动窗格添加到窗口中
        add(scrollPane);

        setVisible(true);
    }
}


