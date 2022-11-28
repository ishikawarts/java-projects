package jp.co.rootship;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * H2データベースのサンプルコード
 */
public class H2Example {

	private static final String SQL_CREATE_USERS_TABLE = """
			CREATE  TABLE IF NOT EXISTS USERS (
				ID INT PRIMARY KEY,
				NAME VARCHAR(50) NOT NULL,
				AGE SMALLINT,
				UPDATE_TSTAMP TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
			);
			""";

	public static void main(String[] args) {
		String jdbcURL = "jdbc:h2:~/test";
		String userName = "sa";
		String password = "";

		try (Connection connection = DriverManager.getConnection(jdbcURL, userName, password);
				Statement statement = connection.createStatement();) {
			// ユーザーテーブル作成
			statement.execute(SQL_CREATE_USERS_TABLE);
			// ユーザー登録 太郎
			statement.executeUpdate("INSERT INTO USERS(ID, NAME, AGE)VALUES(1, 'TARO', 20);");
			// ユーザー登録 二郎
			statement.executeUpdate("INSERT INTO USERS(ID, NAME, AGE)VALUES(2, 'JIRO', 25);");
			// ユーザー登録 サンマ
			statement.executeUpdate("INSERT INTO USERS(ID, NAME, AGE)VALUES(3, 'SANMA', 27);");

			// ユーザーテーブル全件取得
			ResultSet users = statement.executeQuery("SELECT * FROM USERS;");

			// 取得結果全件表示
			printUsersRows(users);

			System.out.println("-------------------------------");
			// サンマの年齢を30に更新
			int countUpdate = statement.executeUpdate("UPDATE USERS SET AGE = 30 WHERE NAME = 'SANMA';");

			// 更新件数を表示
			System.out.println("countUpdate :" + countUpdate);

			// ユーザーテーブルからサンマのレコードを取得
			users = statement.executeQuery("SELECT * FROM USERS WHERE ID = 3;");

			// 取得結果表示
			printUsersRows(users);

			System.out.println("-------------------------------");

			// ユーザーテーブル全件削除
			int countDelete = statement.executeUpdate("DELETE FROM USERS;");

			// 削除件数表示
			System.out.println("countDelete :" + countDelete);

			// ユーザーテーブル全件取得
			users = statement.executeQuery("SELECT * FROM USERS;");

			// 全件表示 （削除済みのため表示さない）
			printUsersRows(users);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ユーザーテーブルの中身を全件表示する
	 * @param resultSet ユーザーテーブルの検索結果
	 * @throws SQLException データベースアクセスエラーの場合にスローされる 
	 */
	private static void printUsersRows(ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {
			var row = List.of(
					resultSet.getString("ID"),
					resultSet.getString("NAME"),
					resultSet.getString("age"),
					resultSet.getString("UPDATE_TSTAMP"));

			System.out.println(String.join(", ", row));
		}
	}

}
