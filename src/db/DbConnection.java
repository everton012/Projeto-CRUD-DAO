package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbConnection {
	
	public static Connection conectar() {
		
		String classeDriver = "com.mysql.cj.jdbc.Driver";
		String usuario = "developer";
		String senha = "1234567";
		String urlServidor = "jdbc:mysql://localhost:3306/banco?useSSL=false";
		
		try {
			Class.forName(classeDriver);
			return DriverManager.getConnection(urlServidor, usuario, senha);
		}
		catch(Exception e) {
			if(e instanceof ClassNotFoundException) {
				System.out.println("Verifique o driver de conexão.");
				e.printStackTrace();
			}
			else {
				System.out.println("Verifique se o servidor está ativo.");
			}
		}
		return null;
	}
	
	public static void desconectar(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				System.out.println("Não foi possível fechar a conexão.");
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(PreparedStatement pstm) {
		if(pstm != null) {
			try {
				pstm.close();
			}
			catch(SQLException e) {
				throw new DbException("Não foi possível fechar Statement.");
			}
		}
	}

}
