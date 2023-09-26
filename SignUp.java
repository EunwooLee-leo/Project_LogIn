package LogIn;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp {
    private String email, name, phone;
    private char[] password = new char[0];


    public SignUp() {
        JFrame frame = new JFrame("회원가입");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
            }
        });


        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(0, 0, 400, 60);
        p1.setBackground(new Color(255, 255, 255));

        JTextField emailField = new JTextField("아이디 또는 이메일");
        emailField.setBounds(45, 70, 300, 35);
        MatteBorder bottomBorder = new MatteBorder(0, 0, 1, 0, Color.lightGray); // 하단 테두리, 두께 2, 검은색
        emailField.setBorder(bottomBorder);
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("아이디 또는 이메일")) {
                    emailField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("아이디 또는 이메일");
                }
            }
        });
        p1.add(emailField);
        JPasswordField passwordField = new JPasswordField("비밀번호");
        passwordField.setBounds(45, 140, 300, 35);
        MatteBorder bottomBorder2 = new MatteBorder(0, 0, 1, 0, Color.lightGray); // 하단 테두리, 두께 2, 검은색
        passwordField.setBorder(bottomBorder2);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("비밀번호")) {
                    passwordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setText("비밀번호");
                }
            }
        });
        p1.add(passwordField);
        JTextField nameField = new JTextField("이름");
        nameField.setBounds(45, 210, 300, 35);
        MatteBorder bottomBorder3 = new MatteBorder(0, 0, 1, 0, Color.lightGray); // 하단 테두리, 두께 2, 검은색
        nameField.setBorder(bottomBorder3);
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("이름")) {
                    nameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("이름");
                }
            }
        });
        p1.add(nameField);
        JTextField phoneField = new JTextField("전화번호");
        phoneField.setBounds(45, 280, 300, 35);
        MatteBorder bottomBorder4 = new MatteBorder(0, 0, 1, 0, Color.lightGray); // 하단 테두리, 두께 2, 검은색
        phoneField.setBorder(bottomBorder4);
        phoneField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (phoneField.getText().equals("전화번호")) {
                    phoneField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (phoneField.getText().isEmpty()) {
                    phoneField.setText("전화번호");
                }
            }
        });
        p1.add(phoneField);
        JButton submitButton = new JButton("회원가입");
        submitButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        submitButton.setFocusable(false);
        submitButton.setBorderPainted(false);
        submitButton.setBackground(new Color(252, 227, 0, 255));
        submitButton.setBounds(45, 400, 300, 35);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserDAO userDAO = new UserDAO(); // UserDAO 인스턴스 생성

                    String insertQuery = "INSERT INTO user (userID, userPassword, userName, userPhone) VALUES (?, ?, ?, ?)";

                    email = emailField.getText();
                    password = passwordField.getPassword(); // getPassword() 메서드는 char[] 반환
                    name = nameField.getText();
                    phone = phoneField.getText();

                    // PreparedStatement 생성
                    PreparedStatement preparedStatement = userDAO.getC().prepareStatement(insertQuery); // UserDAO에서 Connection 가져오기
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, new String(password)); // char[]을 String으로 변환
                    preparedStatement.setString(3, name);
                    preparedStatement.setString(4, phone);

                    // INSERT 쿼리 실행
                    int affectedRows = preparedStatement.executeUpdate();

                    QRCodeGenerator generator = new QRCodeGenerator(email, password);
                    generator.generate();

                    // INSERT 성공 여부 확인
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "회원가입에 실패했습니다.");
                    }

                    // 리소스 정리
                    preparedStatement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "회원가입 중 오류가 발생했습니다.");
                }
            }
        });


        p1.add(submitButton);
        frame.add(p1);
        frame.setVisible(true);

    }
}
