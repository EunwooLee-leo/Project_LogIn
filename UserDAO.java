package LogIn;

import java.sql.*;

public class UserDAO {

    private String userID;
    private String userPassword;
    private String userEmail;
    private String userPhone;
    private Connection c; // DB에 접근하게 해주는 객체
    public Connection getC() {
        return c;
    }

    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2; // 정보를 담을 객체

    UserDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/user";
            String dbID = "root";
            String dbPassword = "1234";
            Class.forName("com.mysql.cj.jdbc.Driver"); //  mysql에 접속하게 해줌
            c = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int login(String userID, String userPassword) {
        String SQL = "SELECT userPassword FROM user WHERE userID = ?"; // 각 ID에 해당하는 비밀번호 조회

        try {
            ps = c.prepareStatement(SQL);
            ps.setString(1, userID); // 위  ?에 적용할 ID
            rs = ps.executeQuery(); // 쿼리 실행 결과를 객체에 저장

            if (rs.next()) {
                if (rs.getString(1).equals(userPassword)) {
                    return 1;
                } else {
                    return 0;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }

    public String getFoundAccountInfo(String insertedName, String insertedPhone) {
        String accountInfo = "";

        try {
            String query = "SELECT userID, userPassword FROM user WHERE userName = ? AND userPhone = ?";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1, insertedName); // foundName은 일치하는 이름
            preparedStatement.setString(2, insertedPhone); // foundPhone은 일치하는 전화번호

            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과를 문자열로 포맷
            while (resultSet.next()) {
                String userID = resultSet.getString("userID"); // userID로 수정
                String userPassword = resultSet.getString("userPassword");

                accountInfo += "계정 아이디: " + userID + "\n"; // userID 사용
                accountInfo += "비밀번호: " + userPassword + "\n";
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountInfo;
    }

    public static void main(String[] args) {
        new UserDAO();
    }
}
