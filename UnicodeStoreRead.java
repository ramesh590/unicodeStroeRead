package com.lingualspeech.mysql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UnicodeStoreRead {

	private Connection connection;

	public UnicodeStoreRead() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		this("localhost","3306", "lingual", "root","mysql");
	}

	/**
	 * Unicode data store class constructor
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public UnicodeStoreRead(String serverName, String serverPort, String databaseName, String databaseUname,String databasePwd) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://"+serverName+":"+serverPort+"/"+databaseName+"?useUnicode=true&characterEncoding=UTF-8";//getting url
		connection = DriverManager.getConnection(url,databaseUname,databasePwd);//create connection
	}

	/**
	 * Writer method for Unicode Storer
	 * @param unicodeStr
	 * @throws SQLException
	 */
	public void writeStrToDatabase(int hid,String unicodeStr,byte flag) throws SQLException {
		Statement stmt = this.connection.createStatement();
		stmt.executeUpdate("insert into tbl3 values('"+hid+"','"+unicodeStr+"','"+flag+"')");
	}

	/**
	 * execution of query
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getQueryResult(String query) throws SQLException {
		Statement stmt = this.connection.createStatement();
		return stmt.executeQuery(query);
	}

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {


		UnicodeStoreRead unicodeWriter = new UnicodeStoreRead();
		Scanner scanner = new Scanner(new File("D:\\llll.txt"), "UTF-8");

		String line;
		int i=0;
		//boolean flag=true;
		//String flag="true";
		byte flag=1;
		while (scanner.hasNextLine()) {
			//i++;
			line = scanner.nextLine();
			unicodeWriter.writeStrToDatabase(i,line,flag);
		//	System.out.println(line);
		}
		scanner.close();

		String query = "select 	* from tbl3";
		ResultSet rs = unicodeWriter.getQueryResult(query);
		while(rs.next())
		{
			System.out.print(rs.getInt("id")+" ");
			System.out.print(rs.getString("data")+" ");
			System.out.print(rs.getBoolean("access")+"\n");
		}

	}
}
