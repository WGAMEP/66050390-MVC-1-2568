package View;

import Model.Job;
import Model.CompanyRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * CandidateJobView
 * - แสดงตารางตำแหน่งงาน (รวมชื่อบริษัท)
 * - ปุ่ม Apply, Logout
 * - popup (showMessage) จะเด้งตรงกลางหน้าต่าง
 */
public class CandidateJobView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton applyButton;
    private JButton logoutButton;

    public CandidateJobView() {
        frame = new JFrame("Candidate - Open Jobs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 450);
        frame.setLayout(new BorderLayout());

        // Table model: เพิ่มคอลัมน์ CompanyName
        String[] columns = {"Job ID", "Title", "Company", "Deadline", "Type", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            // ไม่ให้แก้ข้อมูลในตาราง
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        frame.add(scroll, BorderLayout.CENTER);

        // ปุ่มด้านล่าง
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        applyButton = new JButton("Apply");
        logoutButton = new JButton("Logout");
        bottom.add(applyButton);
        bottom.add(logoutButton);
        frame.add(bottom, BorderLayout.SOUTH);

        // ให้หน้าต่างขึ้นตรงกลางจอ
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * แสดงรายการงานโดยใช้ CompanyRepository เพื่อหา company name
     * - แนะนำเรียกแบบนี้จาก Controller
     */
    public void displayJobs(List<Job> jobs, CompanyRepository companyRepo) {
        tableModel.setRowCount(0);
        if (jobs == null) return;
        for (Job j : jobs) {
            String companyName = j.getCompanyId();
            if (companyRepo != null) {
                try {
                    if (companyRepo.findById(j.getCompanyId()) != null) {
                        companyName = companyRepo.findById(j.getCompanyId()).getName();
                    }
                } catch (Exception ex) {
                    // ถ้า repo มีโครงต่างกัน ให้ fallback เป็น companyId
                    companyName = j.getCompanyId();
                }
            }
            Object[] row = new Object[]{
                    j.getId(),
                    j.getTitle(),
                    companyName,
                    j.getDeadline(),
                    j.getType(),
                    j.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    /**
     * สำรอง: แสดงรายการงานโดยไม่ใช้ CompanyRepository (จะแสดง companyId แทนชื่อ)
     */
    public void displayJobs(List<Job> jobs) {
        displayJobs(jobs, null);
    }

    /**
     * คืน jobId ของแถวที่ถูกเลือก หรือ null ถ้าไม่เลือก
     */
    public String getSelectedJobId() {
        int r = table.getSelectedRow();
        if (r == -1) return null;
        Object val = tableModel.getValueAt(r, 0);
        return val != null ? val.toString() : null;
    }

    public void addApplyListener(ActionListener listener) {
        applyButton.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    /**
     * แสดง popup ข้อความ โดย center อยู่บน frame เสมอ
     */
    public void showMessage(String msg) {
        // ใช้ invokeLater เพื่อให้แน่ใจว่าเรียกบน EDT
        SwingUtilities.invokeLater(() -> {
            // title และ ICON type สามารถปรับได้ตามต้องการ
            JOptionPane.showMessageDialog(frame, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * ปิดหน้าต่าง
     */
    public void close() {
        // ปลอดภัยต่อ EDT
        SwingUtilities.invokeLater(() -> {
            frame.dispose();
        });
    }

    /**
     * ให้ Controller สามารถตั้งตำแหน่ง window (optional)
     */
    public void setLocationRelativeTo(Component c) {
        frame.setLocationRelativeTo(c);
    }
}
