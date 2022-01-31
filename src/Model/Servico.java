/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import dao.ServicoDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tegon Faria
 */
public class Servico {
    private int Codigo = 0;
    private Cliente cliente;
    private Tecnico tecnico;
    private String endereco;
    
    public Servico() {
        
    }
    
    public Servico(Cliente cliente, Tecnico tecnico, String endereco) {
        setCliente(cliente);
        setTecnico(tecnico);
        setEndereco(endereco);
        grava();
    }

    public Servico(int codigo, Cliente cliente, Tecnico tecnico, String endereco) {
        setCodigo(codigo);
        setCliente(cliente);
        setTecnico(tecnico);
        setEndereco(endereco);
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        String ret = null;
        ret = "Nome Cliente: "+ getCliente().getNome()
              +"\n"+ "Nome do Tecnico: "+ getTecnico().getNome()
              +"\n"+ "Enderço: "+ getEndereco();
        return ret;
    }
    
    private void grava() {
        ServicoDAO dao = new ServicoDAO();
        int codigo = dao.create(this);
        if (codigo > 0) {
            setCodigo(codigo);
        }
    }
    
    public static DefaultTableModel getTableModel(){
        List<Servico> lista = ServicoDAO.getInstance().read();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Cliente");
        modelo.addColumn("Tecnico");
        modelo.addColumn("Endereço");
        for(Servico s: lista){
            String [] reg = {String.valueOf(s.getCodigo()), s.getCliente().getNome(),
                            s.getTecnico().getNome(), s.getEndereco()};
            modelo.addRow(reg);
        }
        return modelo;
    }
    
}
