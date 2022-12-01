package jp.co.rootship.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * タスクテーブルへのデータアクセスクラス
 */
public class TaskTableAccess {

	/** ホスト */
	private static final String HOST = "jdbc:h2:~/test";
//	private static final String HOST = "jdbc:h2:mem:test"; // インメモリモード ※Connectionをcloseするとデータが消えるので注意
	
	/** ユーザー */
	private static final String USER_NAME = "sa";
	
	/** パスワード */
	private static final String PASSWORD = "";
	
	/** タスクテーブル作成クエリ */
	private static final String SQL_CREATE_TABLE = """
			CREATE TABLE T_TASK (
			ID INT AUTO_INCREMENT PRIMARY KEY,
			NAME VARCHAR(50) NOT NULL,
			IS_COMPLETED BOOLEAN NOT NULL DEFAULT FALSE,
			IS_DELETED BOOLEAN NOT NULL DEFAULT FALSE
			);
			""";
	
	/** タスクテーブル存在確認クエリ */
	private static final String SQL_EXISTS_TABLE = """
			SELECT *
			FROM INFORMATION_SCHEMA.TABLES
			WHERE TABLE_SCHEMA='PUBLIC' AND TABLE_NAME = 'T_TASK';
			""";
	/** タスクテーブル全検索クエリ */
	private static final String SQL_SELECT = """
			SELECT * FROM T_TASK WHERE IS_DELETED = FALSE ORDER BY ID ASC;
			""";
	
	/** タスク登録クエリ */
	private static final String SQL_INSERT = """
			INSERT INTO T_TASK (NAME)VALUES(''{0}'');
			""";
	
	/** タスク更新クエリ */
	private static final String SQL_UPDATE_BY_PK = """
			UPDATE T_TASK
			SET NAME = ''{1}'', IS_COMPLETED = {2}, IS_DELETED = {3}
			WHERE ID = {0};
			""";
	
	/**
	 * タスクテーブルが存在する場合にtrueを返す
	 * @return true:タスクテーブルが存在する、false:タスクテーブルが存在しない
	 */
	public static boolean existsTable() {

		@SuppressWarnings("unchecked")
		var rows = (List<Map<String, Object>>) executeQuery(SQL_EXISTS_TABLE);

		return rows.size() > 0;
	}
	
	/**
	 * タスクテーブルを作成する
	 */
	public static void createTable() {
		executeQuery(SQL_CREATE_TABLE);
	}

		/**
		 * タスクテーブルを全件検索する
		 * @return タスクテーブルの検索結果
		 */
	public static List<Task> selectAll() {

		if (!existsTable())
			return new ArrayList<>();

		@SuppressWarnings("unchecked")
		var rows = (List<Map<String, Object>>) executeQuery(SQL_SELECT);

		return rows.stream().map(row -> {
			return new Task(
					(int) row.get("ID"),
					(String) row.get("NAME"),
					(boolean) row.get("IS_COMPLETED"),
					(boolean) row.get("IS_DELETED"));
		})
				.collect(Collectors.toList());
	}

	/**
	 * タスクテーブルを更新する
	 * @param task タスク
	 * @return 更新件数
	 */
	public static int update(Task task) {
		
		String sql = MessageFormat.format(SQL_UPDATE_BY_PK, task.getId(), task.getName(), task.getIsCompleted(), task.getIsDeleted());

		return (int) executeQuery(sql);
	}

	/**
	 * タスクテーブルにデータを登録する
	 * @param task タスク
	 * @return 登録件数
	 */
	public static int insert(Task task) {
		if (!existsTable())
			createTable();
			
		String sql = MessageFormat.format(SQL_INSERT, task.getName());

		return (int) executeQuery(sql);
	}

	/**
	 * DBに接続してクエリを実行する
	 * @param sql クエリ
	 * @return クエリがSELECTの場合は検索結果。INSERT、DELETE、UPDATEの場合は更新件数。CREATEの場合はnullを返す。
	 */
	private static Object executeQuery(String sql) {

		try {
			Class.forName("org.h2.Driver");
			
		} catch (ClassNotFoundException e) {
			
			throw new RuntimeException(e);
		}

		try (Connection connection = DriverManager.getConnection(HOST, USER_NAME, PASSWORD);
				Statement statement = connection.createStatement();) {

			if (sql.startsWith("SELECT")) {

				ResultSet resultSet = statement.executeQuery(sql);

				return toList(resultSet);

			} else if (sql.startsWith("INSERT") || sql.startsWith("DELETE") || sql.startsWith("UPDATE")) {

				return statement.executeUpdate(sql);

			} else if (sql.startsWith("CREATE")) {

				statement.execute(sql);

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	/**
	 * 検索結果をResultSetからList<Map<String,Object>に変換する
	 * @param resultSet 検索結果
	 * @return List<Map<String,Object>の検索結果
	 * @throws SQLException データベースアクセスエラーの場合にスローされる
	 */
	private static List<Map<String, Object>> toList(ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> rows = new ArrayList<>();

		try (resultSet) {
			while (resultSet.next()) {

				Map<String, Object> row = new HashMap<>();

				ResultSetMetaData metaData = resultSet.getMetaData();

				for (int i = 1; i <= metaData.getColumnCount(); i++) {

					String columnName = metaData.getColumnLabel(i);

					row.put(columnName, resultSet.getObject(columnName));
				}
				rows.add(row);

			}
		} catch (SQLException e) {
			throw e;
		}

		return rows;

	}

}
