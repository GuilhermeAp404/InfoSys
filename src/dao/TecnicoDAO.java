/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Model.Tecnico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tegon Faria
 */
public class TecnicoDAO implements Persistencia<Tecnico>{
    
    public static TecnicoDAO dao = null;
    
    public TecnicoDAO(){
        
    }
    
    public static TecnicoDAO getInstance(){
        if(dao == null) dao = new TecnicoDAO();
        return dao;
    }
    
    @Override
    public int create(Tecnico t) {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Tecnicos (Nome_tec,Salario,Valor_hora) VALUES(?,?,?)";
        try {
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, t.getNome());
            pst.setDouble(2, t.getSalario());
            pst.setDouble(3, t.getValorHora());
            pst.execute();
            rs = pst.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        }catch(SQLException e){
            id = 0;
        } finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return id;
    }

    @Override
    public Tecnico findByCodigo(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Tecnico t = null;
        String sql = "SELECT * FROM Tecnicos where Codigo_Tec = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            while (rs.next()){
                int codigo = rs.getInt("Codigo_Tec");
                String nome = rs.getString("Nome_tec");
                double salario = rs.getDouble("Salario");
                double valHora = rs.getDouble("Valor_hora");
                t = new Tecnico(codigo, nome, salario, valHora);
            }
            
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Select");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return t;
    }

    @Override
    public void delete(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "DELETE from Tecnicos where Codigo_Tec = ?";
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
    public void update(Tecnico t) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "UPDATE Tecnicos set Nome_tec=?, Salario=?,Valor_hora=?  where Codigo_Tec = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, t.getNome());
            pst.setDouble(2, t.getSalario());
            pst.setDouble(3, t.getValorHora());
            pst.setInt(4, t.getCodigo());
            pst.execute();
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Update");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
    }

    @Override
    public List<Tecnico> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Tecnicos ORDER BY Nome_tec";
        List<Tecnico> lista = new ArrayList<Tecnico>();
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                int codigo = rs.getInt("Codigo_Tec");
                String nome = rs.getString("Nome_tec");
                Double salario = rs.getDouble("Salario");
                Double valor_hora = rs.getDouble("Valor_hora");
                lista.add(new Tecnico(codigo, nome, salario, valor_hora));
            }    
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Select.");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return lista;
    }


    
}
