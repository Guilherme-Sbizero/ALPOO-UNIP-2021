package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:D:\\versoes_infosys\\InfoSys_parte6\\db\\InfoSysDB.db";
    
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL);
            
        }catch(SQLException e){
            throw new RuntimeException("Erro na conexão");
        }
    }
    
    public static void closeConnection(Connection con){
        try{
            if (con != null) con.close();
        }catch (SQLException e){
            throw new RuntimeException("Erro no fechamento da conexao");
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement pst){
        try{
            if (pst != null) pst.close();
            closeConnection(con);
        }catch (SQLException e){
            throw new RuntimeException("Erro no fechamento da conexao");
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement pst, ResultSet rs){
        try{
            if (rs != null) rs.close();
            closeConnection(con,pst);
        }catch (SQLException e){
            throw new RuntimeException("Erro no fechamento da conexao");
        }
    }   
}
