package LogIn;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class FindAccount {
    static JTextField nameField;
    JTextField phone;

   public FindAccount() {
        JFrame frame = new JFrame("검색");
       frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       frame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               frame.setVisible(false);
           }
       });
        frame.setSize(400, 450);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(0, 0, 400, 60);
        p1.setBackground(new Color(255, 255, 255));

        nameField = new JTextField("가입 시 작성했던 이름을 입력하세요");
        nameField.setBounds(45, 70, 300, 35);
        MatteBorder bottomBorder = new MatteBorder(0, 0, 1, 0, new Color(0x181818)); // 하단 테두리, 두께 2, 검은색
        nameField.setBorder(bottomBorder);
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("가입 시 작성했던 이름을 입력하세요")) {
                    nameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("가입 시 작성했던 이름을 입력하세요");
                }
            }
        });
        p1.add(nameField);

        JTextField phoneField = new JTextField("가입 시 작성했던 전화번호를 입력하세요");
        phoneField.setBounds(45, 140, 300, 35);
        MatteBorder bottomBorder4 = new MatteBorder(0, 0, 1, 0, Color.lightGray); // 하단 테두리, 두께 2, 검은색
        phoneField.setBorder(bottomBorder4);
        phoneField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (phoneField.getText().equals("가입 시 작성했던 전화번호를 입력하세요")) {
                    phoneField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (phoneField.getText().isEmpty()) {
                    phoneField.setText("가입 시 작성했던 전화번호를 입력하세요");
                }
            }
        });
        p1.add(phoneField);

        JButton submitButton = new JButton("확인");
        submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        submitButton.setFocusable(false);
        submitButton.setBorderPainted(false);
        submitButton.setBackground(new Color(252, 227, 0, 255));
        submitButton.setBounds(45, 250, 300, 35);
        submitButton.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               String name = nameField.getText();
                                               String phone = phoneField.getText();

                                               UserDAO userDAO = new UserDAO();

                                               String accountInfo = userDAO.getFoundAccountInfo(name, phone);

                                               if (!accountInfo.isEmpty()) {
                                                   JOptionPane.showMessageDialog(null, "계정 찾기 성공\n" + accountInfo);
                                               } else {
                                                   JOptionPane.showMessageDialog(null, "계정 찾기 실패. 다시 시도해 주세요.");
                                               }
                                           }
                                       });
        p1.add(submitButton);
        frame.add(p1);

        frame.setVisible(true);
    }

}
