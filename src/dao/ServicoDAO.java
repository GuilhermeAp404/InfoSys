/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import Model.Cliente;
import Model.Servico;
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
public class ServicoDAO implements dao.Persistencia<Servico> {

    private static ServicoDAO dao = null;
    
    public ServicoDAO() {
    
    }
    
    public static ServicoDAO getInstance(){
        if(dao == null) dao = new ServicoDAO();
        return dao;
    }
    
    @Override
    public int create(Servico s) {
        int id = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "INSERT INTO Servicos(Cliente,Tecnico,Local) VALUES(?,?,?)";
        try {
            pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setInt(1, s.getCliente().getCodigo());
            pst.setInt(2, s.getTecnico().getCodigo());
            pst.setString(3, s.getEndereco());
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
    public Servico findByCodigo(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Servico s = null;
        String sql = "SELECT * FROM Servicos s INNER JOIN Clientes c "
                   + "INNER JOIN Tecnicos t ON s.Cliente = c.Codigo_Cl "
                   + "AND s.Tecnico = t.Codigo_Tec  WHERE s.Codigo = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("Codigo");
                String endereco = rs.getString("local");
                //Cliente
                int codigo_cl = rs.getInt("Codigo_Cl");
                String cl_nome = rs.getString("Nome");
                String cl_cpf = rs.getString("CPF");
                String cl_fone = rs.getString("Fone");
                String cl_celular = rs.getString("Celular");
                String cl_Email = rs.getString("Email");
                Cliente c = new Cliente(codigo_cl, cl_nome, cl_cpf, cl_fone, cl_celular, cl_Email);
                //Tecnico
                int codigo_tc = rs.getInt("Codigo_Tec");
                String nome_tc = rs.getString("Nome_tec");
                Double salario_tc = rs.getDouble("Salario");
                Double ValH_tc = rs.getDouble("Valor_hora");
                Tecnico t = new Tecnico(codigo_tc, nome_tc, salario_tc, ValH_tc);
                
                //Preenche o Objeto Serviço
                s = new Servico(codigo, c, t, endereco);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro no SELECT");
        } finally {
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return s;
    }

    @Override
    public void delete(int id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "DELETE from Servicos where Codigo = ?";
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
    public void update(Servico s) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "UPDATE Servicos set Cliente=?, Tecnico=?, Local=?  where Codigo = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1, s.getCliente().getCodigo());
            pst.setInt(2, s.getTecnico().getCodigo());
            pst.setString(3, s.getEndereco());
            pst.setInt(4, s.getCodigo());
            pst.execute();
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Update");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
    }

    @Override
    public List<Servico> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "Select * FROM Servicos s inner join Clientes c inner join Tecnicos t "
                     + "On s.Cliente = c.Codigo_Cl and s.Tecnico = t.Codigo_Tec;";
        List<Servico> lista = new ArrayList<Servico>();
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                //Tecnico
                int codigo_tc = rs.getInt("Codigo_Tec");
                String nome_tc = rs.getString("Nome_tec");
                Double salario_tc = rs.getDouble("Salario");
                Double ValH_tc = rs.getDouble("Valor_hora");
                Tecnico t = new Tecnico(codigo_tc, nome_tc, salario_tc, ValH_tc);
                //Cliente
                int codigo_cl = rs.getInt("Codigo_Cl");
                String cl_nome = rs.getString("Nome");
                String cl_cpf = rs.getString("CPF");
                String cl_fone = rs.getString("Fone");
                String cl_celular = rs.getString("Celular");
                String cl_Email = rs.getString("Email");
                Cliente c = new Cliente(codigo_cl, cl_nome, cl_cpf, cl_fone, cl_celular, cl_Email);
                //Servico
                int codigo = rs.getInt("Codigo");
                String local = rs.getString("Local");
                lista.add(new Servico(codigo, c, t, local));
            }    
        }catch(SQLException ex){
            throw new RuntimeException("Erro no Select.");
        }finally{
            ConnectionFactory.closeConnection(con, pst, rs);
        }
        return lista;
    }
    
}
