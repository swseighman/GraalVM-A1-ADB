/* Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.*/
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class DataSourceSample {

  // For OCI Version
  final static String DB_URL= "jdbc:oracle:thin:@db202105260820_medium?TNS_ADMIN=/home/sseighma/code/graalvm/GraalVM-A1-ADB/wallet";
  final static String DB_USER = "ADMIN";
  final static String DB_PASSWORD = "Spl!nt3r!!!!";

  public static void main(String args[]) throws SQLException {
    String query = "SELECT * FROM DUAL";
    if(args.length > 0) {
      query = args[0];
    }

    Properties info = new Properties();
    info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
    info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
    info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");

    OracleDataSource ods = new OracleDataSource();
    ods.setURL(DB_URL);
    ods.setConnectionProperties(info);

    try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
      DatabaseMetaData dbmd = connection.getMetaData();
      System.out.println("Driver Name: " + dbmd.getDriverName());
      System.out.println("Driver Version: " + dbmd.getDriverVersion());
      System.out.println("Default Row Prefetch Value is: " +
         connection.getDefaultRowPrefetch());
      System.out.println("Database Username is: " + connection.getUserName());
      System.out.println();

      ResultSet resultSet = connection.createStatement().executeQuery(query);
      resultSet.next();
      String X = resultSet.getString(1);
      System.out.println("'" + query + "' returned: " + X);
    }
  }
}
