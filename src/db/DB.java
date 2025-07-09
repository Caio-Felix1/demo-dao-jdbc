package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
	// Connection: objeto de conexão com o banco de dados do JDBC
	
	// método para gerar a conexão com o banco de dados
	public static Connection getConnection() {
		if (conn == null) { // se for igual a nulo, não tem conexão ainda.
			try {
			Properties props = loadProperties();
			String url = props.getProperty("dburl");
			// "dburl" igual no arquivo db.properties
			
			// Para obter a conexão com o banco de dados é necessário passar a url do
			// banco de dados e as suas propriedades.
			conn = DriverManager.getConnection(url, props);
			// conectar no banco de dados no JDBC é instanciar um objeto do tipo Connection
			// como a variável conn é estática, ela vai ficar salva para os outros objetos.
			// Ou seja, a conexão vai estar salva.
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if (conn != null) { // testa se tem conexão
			try {
				conn.close(); // fecha a conexão
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	// método para pegar as propriedades para fazer a conexão com o banco de dados
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			// como o arquivo está na pasta raiz do projeto, só precisa colocar o nome dele.
			
			Properties props = new Properties();
			props.load(fs);
			// load faz a leitura do arquivo apontado pelo fs e vai guardar os dados dentro
			// do objeto props.
			
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
