/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Model.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Aluno
 */
public class ClienteDAO implements dao.Persistencia<Cliente> {
    
    private static ClienteDAO dao = null;
    
    public ClienteDAO() {
        
    }
    
    public static ClienteDAO getInstance() {
        if (dao == null) {
            dao = new ClienteDAO();
        }
        return dao;
    }
    
    @Override
    public int create(Cliente c) {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Clientes (Nome, CPF, Fone,Celular,Email) VALUES(?,?,?,?,?)";
        try {
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, c.getNome());
            pst.setString(2, c.getCpf());
            pst.setString(3, c.getFone());
            pst.setString(4, c.getCelular());
            pst.setString(5, c.getEmail());
            pst.execute();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            id = 0;
        } finally {
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return id;
    }
    
    @Override
    public Cliente findByCodigo(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Cliente c = null;
        String sql = "SELECT * FROM Clientes WHERE Codigo_Cl = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("Codigo_Cl");
                String nome = rs.getString("Nome");
                String cpf = rs.getString("CPF");
                String fone = rs.getString("Fone");
                String celular = rs.getString("Celular");
                String email = rs.getString("Email");
                c = new Cliente(codigo, nome, cpf, fone, celular, email);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro no SELECT");
        }finally {
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return c;
    }
    
    @Override
    public void delete(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "DELETE from Clientes where Codigo_Cl = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro no Delete");
        } finally {
            ConnectionFactory.closeConnection(con, pst, rs);
        }
    }
    
    @Override
    public void update(Cliente c) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "UPDATE Clientes set nome=?, cpf=?, fone=?, celular=?, email = ? where Codigo_Cl = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, c.getNome());
            pst.setString(2, c.getCpf());
            pst.setString(3, c.getFone());
            pst.setString(4, c.getCelular());
            pst.setString(5, c.getEmail());
            pst.setInt(6, c.getCodigo());
            pst.execute();
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Update");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
    }

    @Override
    public List<Cliente> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        
        ResultSet rs = null;
        String sql = "SELECT * FROM Clientes ORDER BY nome";
        List<Cliente> lista = new ArrayList<Cliente>();
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                int codigo = rs.getInt("Codigo_Cl");
                String nome = rs.getString("Nome");
                String cpf = rs.getString("CPF");
                String fone = rs.getString("Fone");
                String celular = rs.getString("Celular");
                String email = rs.getString("Email");
                lista.add(new Cliente(codigo, nome, cpf, fone, celular, email));
            }    
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Select.22");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return lista;
    }
}