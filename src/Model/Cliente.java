/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import dao.ClienteDAO;
import java.util.List;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tegon Faria
 */
public class Cliente {

    private int Codigo = 0;
    private String nome = null;
    private String cpf = null;
    private String fone = null;
    private String celular = null;
    private String email = null;

    public Cliente() {
    
    }
    
    public Cliente(int codigo, String nome, String cpf, String fone, String celular, String email) {
        setCodigo(codigo);
        setNome(nome);
        setCpf(cpf);
        setFone(fone);
        setCelular(celular);
        setEmail(email);
    }
    
    public Cliente(String nome, String cpf, String fone, String celular, String email) {
        setNome(nome);
        setCpf(cpf);
        setFone(fone);
        setCelular(celular);
        setEmail(email);
        grava();
    }
    
    public int getCodigo() {
        return Codigo;
    }
    
    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getFone() {
        return fone;
    }
    
    public void setFone(String fone) {
        this.fone = fone;
    }
    
    public String getCelular() {
        return celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        String ret = null;
        ret = "Nome......" + getNome() + "\n"
                + "Cpf......" + getCpf() + "\n"
                + "Telefone......" + getFone() + "\n"
                + "Celular......" + getCelular() + "\n"
                + "Email......" + getEmail() + "\n";
        return ret;
        
    }

    private void grava() {
        ClienteDAO dao = new ClienteDAO();
        int codigo = dao.create(this);
        if (codigo > 0) {
            setCodigo(codigo);
        }
    }
   
    public static DefaultTableModel getTableModel(){
        List<Cliente> lista = ClienteDAO.getInstance().read();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Telefone");
        modelo.addColumn("Celular");
        modelo.addColumn("Email");
        for(Cliente c: lista){
            String [] reg = {c.getNome(),c.getFone(),c.getCelular(),c.getEmail()};
            modelo.addRow(reg);
        }
        return modelo;
    }
}
