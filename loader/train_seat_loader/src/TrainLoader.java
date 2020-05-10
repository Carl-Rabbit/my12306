import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TrainLoader {
	private static final int BATCH_SIZE = 500;
	private static String cnfPath = "loader.cnf";

	private static Connection con = null;
	private static Statement stmt0 = null;
	private static PreparedStatement stmt = null;

	private static void openDB(String host, String dbname,
	                           String user, String pwd) {
		try {
			//
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
			stmt = con.prepareStatement("insert into students(studentid,name)"
					+ " values(?,?)");
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

	private static void loadData(String studentid, String name)
			throws SQLException {
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

			while (data.next()) {
				String trainCode = data.getString("train_code");
				System.out.println(trainCode);
			}
			closeDB();

//			start = System.currentTimeMillis();
//			openDB(prop.getProperty("host"), prop.getProperty("database"),
//					prop.getProperty("user"), prop.getProperty("password"));
//			while ((line = infile.readLine()) != null) {
//
//			}
//			if (cnt % BATCH_SIZE != 0) {
//				stmt.executeBatch();
//			}
//			con.commit();
//			stmt.close();
//			closeDB();
//			end = System.currentTimeMillis();
//			System.out.println(cnt + " records successfully loaded");
//			System.out.println("Loading speed : "
//					+ (cnt * 1000) / (end - start)
//					+ " records/s");
		} catch (SQLException se) {
			System.err.println("SQL error: " + se.getMessage());
			try {
				con.rollback();
				stmt.close();
			} catch (Exception e2) {
			}
			closeDB();
			System.exit(1);
		}
		closeDB();
	}
}

