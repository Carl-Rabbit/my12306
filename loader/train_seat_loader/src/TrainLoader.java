import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TrainLoader {
	private static final int BATCH_SIZE = 5000;
	private static String cnfPath = "loader.cnf";

	private static Connection con = null;
	private static Statement stmt0 = null;
	private static PreparedStatement stmt = null;

	private static void openDB(String host, String dbname,
	                           String user, String pwd) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
			System.exit(1);
		}

		String url = "jdbc:postgresql://" + host + "/" + dbname;
		Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", pwd);
		try {
			con = DriverManager.getConnection(url, props);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("Database connection failed");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		try {
			stmt = con.prepareStatement("insert into seats (train_no, carriage, " +
					"location, code, class, type) " +
					"values(?, ?, ?, ?, ?, ?)");
		} catch (SQLException e) {
			System.err.println("Insert statement failed");
			System.err.println(e.getMessage());
			closeDB();
			System.exit(1);
		}
	}

	private static void closeDB() {
		if (con != null) {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (stmt0 != null) {
					stmt0.close();
				}
				con.close();
				con = null;
			} catch (Exception e) {
				// Forget about it
			}
		}
	}

	private static void loadTrainSeatsG(String trainNo)
			throws SQLException {
		/*
		level 1: 1 sleeper/seat carriage
		level 2: 2 sleeper/seat carriage
		level 3: 5 sleeper/seat carriage
		 */

		// level 1
		// sleeper: 一节车厢共有6个房间。一个房间2个铺位
		for (int i = 1; i <= 6; i++) {
			addSeat(trainNo, 1, i, "1", "A", "W");
			addSeat(trainNo, 1, i, "2", "A", "W");
		}
		// seat: 一节车厢共有8排。一排3个座位
		for (int i = 1; i <= 8; i++) {
			addSeat(trainNo, 2, i, "A", "A", "Z");
			addSeat(trainNo, 2, i, "C", "A", "Z");
			addSeat(trainNo, 2, i, "F", "A", "Z");
		}

		// level 2
		// sleeper: 3-4车。一节车厢共有8个房间。一个房间4个铺位
		for (int c = 3; c <= 4; c++) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 4; j++) {
					addSeat(trainNo, c, i, String.valueOf(j), "B", "W");
				}
			}
		}
		// seat: 5-6车。一节车厢共有16排。一排4个座位
		for (int c = 5; c <= 6; c++) {
			for (int i = 1; i <= 16; i++) {
				addSeat(trainNo, c, i, "A", "B", "Z");
				addSeat(trainNo, c, i, "C", "B", "Z");
				addSeat(trainNo, c, i, "D", "B", "Z");
				addSeat(trainNo, c, i, "F", "B", "Z");
			}
		}

		// level 3
		// sleeper: 7-11车。一节车厢共有8个房间。一个房间6个铺位
		for (int c = 7; c <= 11; c++) {
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 6; j++) {
					addSeat(trainNo, c, i, String.valueOf(j), "C", "W");
				}
			}
		}
		// seat: 12-16车。一节车厢共有16排。一排5个座位
		for (int c = 12; c <= 16; c++) {
			for (int i = 1; i <= 16; i++) {
				addSeat(trainNo, c, i, "A", "C", "Z");
				addSeat(trainNo, c, i, "B", "C", "Z");
				addSeat(trainNo, c, i, "C", "C", "Z");
				addSeat(trainNo, c, i, "D", "C", "Z");
				addSeat(trainNo, c, i, "F", "C", "Z");
			}
		}
	}

	private static void loadTrainSeatsN(String trainNo)
			throws SQLException {
		/*
		level 1: None
		level 2: 2 sleeper/seat carriage
		level 3: 5 sleeper/seat carriage
		 */

		// level 2
		// sleeper: 1-3车。一节车厢共有11个房间。一个房间4个铺位
		for (int c = 1; c <= 3; c++) {
			for (int i = 1; i <= 11; i++) {
				for (int j = 1; j <= 4; j++) {
					addSeat(trainNo, c, i, String.valueOf(j), "B", "W");
				}
			}
		}
		// seat: 4-6车。一节车厢共有80个座位, 2 + 2
		for (int c = 4; c <= 6; c++) {
			for (int i = 1; i <= 80; i++) {
				addSeat(trainNo, c, i, "", "B", "Z");
			}
		}

		// level 3
		// sleeper: 7-11车。一节车厢共有11个房间。一个房间6个铺位
		for (int c = 7; c <= 11; c++) {
			for (int i = 1; i <= 11; i++) {
				for (int j = 1; j <= 6; j++) {
					addSeat(trainNo, c, i, String.valueOf(j), "C", "W");
				}
			}
		}
		// seat: 12-16车。一节车厢共有118个座位, 3 + 2
		for (int c = 12; c <= 16; c++) {
			for (int i = 1; i <= 118; i++) {
				addSeat(trainNo, c, i, "", "C", "Z");
			}
		}

	}

	private static void addSeat(String trainNo, int carriage, int location,
	                            String code, String cls, String type)
			throws SQLException {
		if (con != null) {
			stmt.setString(1, trainNo);
			stmt.setInt(2, carriage);
			stmt.setInt(3, location);
			stmt.setString(4, code);
			stmt.setString(5, cls);
			stmt.setString(6, type);
			stmt.addBatch();
		}
	}

	public static void main(String[] args) {
		Properties prop = new Properties();
		try (BufferedReader conf
				     = new BufferedReader(new FileReader(cnfPath))) {
			prop.load(conf);
		} catch (IOException e) {
			// Ignore
			System.err.println("No configuration file (loader.cnf) found");
		}
		try {
			long start;
			long end;
			ResultSet data = null;
			int cnt = 0;
			// Empty target table
			openDB(prop.getProperty("host"), prop.getProperty("database"),
					prop.getProperty("user"), prop.getProperty("password"));
			if (con != null) {
				stmt0 = con.createStatement();
				data = stmt0.executeQuery("select * from route_schedule");
			}

			if (data == null) {
				System.err.println("Data is null");
				System.exit(1);
			}

			start = System.currentTimeMillis();
			int n = 0;
			while (data.next() && n++ < 2) {
				char ch = data.getString("train_code").charAt(0);
				switch (ch) {
					case 'C':
					case 'D':
					case 'G':
					case 'P':
					case 'S':
					case 'Y':
						loadTrainSeatsG(data.getString("train_no"));
						break;
					default:
						loadTrainSeatsN(data.getString("train_no"));
				}
				cnt ++;
			}
			if (cnt % BATCH_SIZE != 0) {
				stmt.executeBatch();
			}
			con.commit();
			end = System.currentTimeMillis();
			closeDB();

//			openDB(prop.getProperty("host"), prop.getProperty("database"),
//					prop.getProperty("user"), prop.getProperty("password"));
//			while ((line = infile.readLine()) != null) {
//
//			}
//			if (cnt % BATCH_SIZE != 0) {
//				stmt.executeBatch();
//			}
			System.out.println("Loading speed : "
					+ (cnt * 1000) / (end - start)
					+ " trains/s");
		} catch (SQLException se) {
			System.err.println("SQL error: " + se.getMessage());
			try {
				con.rollback();
			} catch (Exception ignored) {
			}
			closeDB();
			System.exit(1);
		}
		closeDB();
	}
}

