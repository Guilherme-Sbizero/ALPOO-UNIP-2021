package dao;

import Model.Cliente;
import Model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements Persistencia<Produto>{
    
    private static ProdutoDAO dao = null;
    
    public ProdutoDAO(){   //alterado de private para public
    }

    public static ProdutoDAO getInstence(){
        if(dao == null) dao = new ProdutoDAO();
        return dao;     
    }
    
    @Override
    public int create(Produto obj) {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Produtos (Descricao, Ativo, Estoque, Custo, Valor) VALUES (?,?,?,?,?)";
        try{
            pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, obj.getDescricao());
            pst.setBoolean(2, obj.isAtivo());
            pst.setInt(3, obj.getEstoque());
            pst.setDouble(4, obj.getCusto());
            pst.setDouble(5, obj.getValor());
            pst.execute();
            rs = pst.getGeneratedKeys();
            if (rs.next()){
                id = rs.getInt(1);
            }
        }catch(SQLException ex){
            id = 0;
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return id;
    }  

    @Override
    public Produto findByCodigo(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Produto p = null;
        String sql = "SELECT * FROM Produtos where Codigo = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            while (rs.next()){
                int codigo = rs.getInt("Codigo");
                String descricao = rs.getString("Descricao");
                boolean ativo = rs.getBoolean("Ativo");
                int estoque = rs.getInt("Estoque");
                double custo = rs.getDouble("Custo");
                double valor = rs.getDouble("Valor");
                p = new Produto(codigo,descricao,ativo,estoque,custo,valor);
            }
            
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Select");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return p;
    }

    @Override
    public void delete(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "DELETE from Produtos where codigo = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            pst.execute();
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Delete");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
    }  


    @Override
    public void update(Produto p) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "UPDATE Produtos set descricao=?,ativo=?,estoque=?,custo=?,valor=? where codigo=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, p.getDescricao());
            pst.setBoolean(2, p.isAtivo());
            pst.setInt(3, p.getEstoque());
            pst.setDouble(4, p.getCusto());
            pst.setDouble(5, p.getValor());
            pst.setInt(6,p.getCodigo());
            pst.execute();
        }catch(SQLException ex){
            throw new RuntimeException("Erro no update");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
    } 


    @Override
    public List<Produto> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Produtos ORDER BY Descricao";
        List<Produto> lista = new ArrayList<Produto>();
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                int codigo = rs.getInt("Codigo");
                String descricao = rs.getString("Descricao");
                boolean ativo = rs.getBoolean("Ativo");
                int estoque = rs.getInt("Estoque");
                double custo = rs.getDouble("Custo");
                double valor = rs.getDouble("Valor");
                lista.add(new Produto(codigo,descricao,ativo,estoque,custo,valor));
            }
            
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Select");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return lista;
    }


}
