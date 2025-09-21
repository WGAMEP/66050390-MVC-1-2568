// ... package และ imports เดิม (ไม่ต้องเปลี่ยน)
package View;

import Model.Job;
import Model.Candidate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminJobView {
    private JFrame frame;
    private JTable jobsTable;
    private DefaultTableModel jobsModel;
    private JTable candTable;
    private DefaultTableModel candModel;
    private JTextField txtCandidateId;
    private JTextField txtGrade;
    private JButton btnUpdateGrade;
    private JButton btnLogout;
    private JLabel lblMessage;

    public AdminJobView() {
        frame = new JFrame("Admin Panel - Jobs & Grades");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        String[] jobCols = {"Job ID", "Title", "Company", "Deadline", "Type", "Status", "Applicants"};
        jobsModel = new DefaultTableModel(jobCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        jobsTable = new JTable(jobsModel);
        JScrollPane jobsScroll = new JScrollPane(jobsTable);
        jobsScroll.setBorder(BorderFactory.createTitledBorder("Jobs"));
        jobsScroll.setPreferredSize(new Dimension(980, 250));

        String[] candCols = {"Candidate ID", "First Name", "Last Name", "Status", "Grade"};
        candModel = new DefaultTableModel(candCols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        candTable = new JTable(candModel);
        JScrollPane candScroll = new JScrollPane(candTable);
        candScroll.setBorder(BorderFactory.createTitledBorder("Candidates"));
        candScroll.setPreferredSize(new Dimension(980, 220));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(jobsScroll);
        centerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(candScroll);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        bottom.setBorder(BorderFactory.createTitledBorder("Admin Actions"));

        bottom.add(new JLabel("Candidate ID:"));
        txtCandidateId = new JTextField();
        txtCandidateId.setPreferredSize(new Dimension(120, 24));
        bottom.add(txtCandidateId);

        bottom.add(new JLabel("Grade (A, B+, B, C+, C, D+, D, F):"));
        txtGrade = new JTextField();
        txtGrade.setPreferredSize(new Dimension(80, 24));
        bottom.add(txtGrade);

        btnUpdateGrade = new JButton("Update Grade");
        bottom.add(btnUpdateGrade);

        btnLogout = new JButton("Logout");
        bottom.add(btnLogout);

        lblMessage = new JLabel("");
        lblMessage.setPreferredSize(new Dimension(400, 24));
        bottom.add(lblMessage);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void displayJobs(List<Job> jobs, java.util.function.Function<String,String> companyIdToName) {
        jobsModel.setRowCount(0);
        if (jobs == null) return;
        for (Job j : jobs) {
            String companyName = j.getCompanyId();
            try { companyName = companyIdToName.apply(j.getCompanyId()); } catch (Exception ex) { /* fallback */ }
            String applicants = String.join(",", j.getApplicantIds());
            jobsModel.addRow(new Object[]{j.getId(), j.getTitle(), companyName, j.getDeadline(), j.getType(), j.getStatus(), applicants});
        }
    }

    public void displayCandidates(List<Candidate> candidates) {
    candModel.setRowCount(0);
    if (candidates == null) return;
    for (Candidate c : candidates) {
        if (!"STUDENT".equals(c.getStatus())) continue; // กรองเฉพาะ STUDENT
        String gradeText = (c.getGrade() == null || c.getGrade().isEmpty()) ? "-" : c.getGrade();
        candModel.addRow(new Object[]{c.getId(), c.getFirstName(), c.getLastName(), c.getStatus(), gradeText});
    }
}

    public String getCandidateIdInput() { return txtCandidateId.getText().trim(); }
    public String getGradeInput() { return txtGrade.getText().trim(); }

    public void addUpdateGradeListener(java.awt.event.ActionListener l) { btnUpdateGrade.addActionListener(l); }
    public void addLogoutListener(java.awt.event.ActionListener l) { btnLogout.addActionListener(l); }

    public void showMessage(String msg) {
        lblMessage.setText(msg);
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, msg));
    }

    public void clearInputs() {
        txtCandidateId.setText("");
        txtGrade.setText("");
    }

    public void close() { frame.dispose(); }
}
