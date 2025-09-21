package View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * LoginView - หน้า Login แบบเรียบง่าย
 * ปรับให้เด้งมาที่กลางหน้าจอทุกครั้งเมื่อแสดง
 */
public class LoginView {
    private JFrame frame;
    private JTextField idField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginView(){
        frame = new JFrame("Job Fair Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,220);
        frame.setLayout(null);

        JLabel idLabel = new JLabel("User ID:");
        idLabel.setBounds(50,50,80,25);
        frame.add(idLabel);

        idField = new JTextField();
        idField.setBounds(140,50,200,25);
        frame.add(idField);

        loginButton = new JButton("Login");
        loginButton.setBounds(140,100,100,30);
        frame.add(loginButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50,140,300,25);
        frame.add(messageLabel);

        // วางตำแหน่งหน้าต่างตรงกลางหน้าจอ
        frame.setLocationRelativeTo(null);

        // แสดงหน้าต่าง (ต้องอยู่หลัง setLocationRelativeTo เพื่อให้กลางจอถูกต้อง)
        frame.setVisible(true);
    }

    // ให้ Controller เพิ่ม listener (ปุ่ม Login)
    public void addLoginListener(ActionListener listener){
        loginButton.addActionListener(listener);
    }

    public String getUserId(){
        return idField.getText().trim();
    }

    public void setMessage(String msg){
        messageLabel.setText(msg);
    }

    public void close(){
        frame.dispose();
    }
}
