package com.largepixels.webscrapeexamples.scrape_to_mysql;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class DbDao {

	private static final Logger log = Logger.getLogger(DbDao.class);

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://example.org/asdf";

	static final String USER = "root";
	static final String PASS = "password";

	public void addDomain(String domainStr) {

		URL domainUrl;
		try {
			domainUrl = new URL(domainStr); // this try catch is absurd

			if (alreadyHaveDomain(domainUrl) == false) {
				insertDomain(domainUrl);
			} else {
				updateDomainStatus(domainUrl);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	private void updateDomainStatus(URL domainUrl) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "UPDATE domain SET status = 3 WHERE name = '" + domainUrl.getHost() + "'";
			stmt.executeUpdate(sql);
			log.info(domainUrl.getHost() + " has been updated to 3 in db");

			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

	}

	private boolean alreadyHaveDomain(URL domainUrl) {

		Connection conn = null;
		Statement stmt = null;

		int total = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT count(*) FROM domain WHERE name = '"
					+ domainUrl.getHost() + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				total++;
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

		if (total > 0) {
			log.info(domainUrl.getHost() + " already exists in the db");
			return true;
		} else {
			log.info(domainUrl.getHost() + " does not exist in the db");
			return false;
		}
	}

	private void insertDomain(URL domainUrl) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "INSERT INTO domain SET name = '"
					+ domainUrl.getHost() + "', status = 2";
			stmt.executeUpdate(sql);
			log.info(domainUrl.getHost() + " has been inserted into the db");

			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
	}

}
