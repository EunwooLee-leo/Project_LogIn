package LogIn;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import static LogIn.QRReader.decodeQRCode;

public class layout {

    static JFrame f;
    static JPanel p1, p2, p3;
    static JLabel l1, l2, l3;
    static JTextField idField;
    static JPasswordField passwordField;
    private static String email;
    private static char[] password;
    static String insertedId;
    static char[] insertedPassword;

    static void form() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setTitle("Log In");
        f.setLayout(null);
        f.setBounds(0, 0, 400, 600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = f.getWidth();
        int frameHeight = f.getHeight();
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        f.setLocation(x, y);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(0, 0, 400, 60);
        p1.setBackground(new Color(255, 255, 255));


        p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(0, 60, 400, 440);
        p2.setBackground(new Color(255, 255, 255));
        p2.setBorder(new LineBorder(Color.lightGray, 1));

        p3 = new JPanel();
        p3.setLayout(null);
        p3.setBounds(100, 210, 300, 35);
        p3.setBackground(Color.gray);


        l1 = new JLabel("kakao", JLabel.CENTER);
        l1.setBounds(0, 10, 400, 30);
        l1.setFont(new Font("arial unicode ms", Font.PLAIN, 25));
        p1.add(l1);
        f.add(p1);
        l1.setForeground(Color.black);

        l2 = new JLabel("--------------------------또는--------------------------", JLabel.CENTER);
        l2.setBounds(45, 285, 300, 30);
        l2.setFont(new Font("맑은 고딕", Font.BOLD, 11));
        p2.add(l2);
        f.add(p2);
        l2.setForeground(new Color(194, 195, 194, 255));

        l3 = new JLabel("Copyright © Kakao Corp. All rights reserved.", JLabel.CENTER);
        l3.setFont(new Font("arial unicode ms", Font.PLAIN, 11));
        p3.add(l3);
        f.add(l3);
        l2.setForeground(new Color(194, 195, 194, 255));
        l3.setBounds(45, 520, 300, 30);

        idField = new JTextField("아이디, 이메일, 전화번호");
        idField.setBounds(45, 70, 300, 35);
        MatteBorder bottomBorder = new MatteBorder(0, 0, 1, 0, new Color(0x181818)); // 하단 테두리, 두께 2, 검은색
        idField.setBorder(bottomBorder);
        idField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idField.getText().equals("아이디, 이메일, 전화번호")) {
                    idField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setText("아이디, 이메일, 전화번호");
                }
            }
        });

        passwordField = new JPasswordField("비밀번호");
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

        p2.add(idField);
        p2.add(passwordField);
        f.add(p2);

        JButton button = new JButton("로그인");
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(252, 227, 0, 255));
        button.setBounds(45, 250, 300, 35);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertedId = idField.getText();
                insertedPassword = passwordField.getPassword();
                String password = new String(insertedPassword);

                UserDAO userDAO = new UserDAO();
                int result = userDAO.login(insertedId, password);

                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                } else if (result == 0) {
                    JOptionPane.showMessageDialog(null, "로그인 실패. 비밀번호가 일치하지 않습니다.");
                } else if (result == -1) {
                    JOptionPane.showMessageDialog(null, "로그인 실패. 아이디가 존재하지 않습니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "로그인 실패. 데이터베이스 오류");
                }
            }
        });

        JRadioButton radioButton = new JRadioButton("아이디 입력 유지");
        radioButton.setBounds(45, 190, 300, 30);
        radioButton.setFocusable(false);
        radioButton.setBorderPainted(false);
        radioButton.setBackground(Color.white);
        radioButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText(email);
            }
        });


        JButton button2 = new JButton("QR코드 로그인");
        button2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button2.setFocusable(false);
        button2.setBorderPainted(false);
        button2.setBackground(new Color(238, 238, 238, 255));
        button2.setBounds(45, 320, 300, 35);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose QR Code Image");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg"));

                int userSelection = fileChooser.showOpenDialog(f);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    try {
                        String decodedText = decodeQRCode(selectedFile);
                        String[] parts = decodedText.split(":");
                        if (parts.length == 2) {
                            String id = parts[0];
                            String password = parts[1];
                            layout.idField.setText(id);
                            layout.passwordField.setText(password);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(f, "QR Code를 스캔할 수 없습니다. Error: " + ex.getMessage(), "QR Code Result", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(f, "QR Code 파일을 찾을 수 없습니다", "QR Code Result", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

                JButton button3 = new JButton("회원가입");
                button3.setFont(new Font("맑은 고딕", Font.BOLD, 11));
                button3.setFocusable(false);
                button3.setBorderPainted(false);
                button3.setBackground(Color.white);
                button3.setBounds(30, 390, 80, 20);
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new SignUp();
                        f.setVisible(true);
                    }
                });

                JButton button4 = new JButton("계정찾기");
                button4.setFont(new Font("맑은 고딕", Font.BOLD, 11));
                button4.setFocusable(false);
                button4.setBorderPainted(false);
                button4.setBackground(Color.white);
                button4.setBounds(280, 390, 80, 20);
                button4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new FindAccount();
                        f.setVisible(true);
                    }
                });

                p2.add(button);
                p2.add(radioButton);
                p2.add(button2);
                p2.add(button3);
                p2.add(button4);


                f.setVisible(true);
            }

        }

