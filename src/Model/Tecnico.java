/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import dao.TecnicoDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aluno
 */
public class Tecnico {
    private int Codigo = 0;
    private String nome;
    private double salario;
    private double valorHora;

    public Tecnico() {
    }

    public Tecnico(String nome, Double salario, Double valorHora) {
        setNome(nome);
        setSalario(salario);
        setValorHora(valorHora);
        grava();
    }
    public Tecnico(int codigo, String nome, Double salario, Double valorHora) {
        setCodigo(codigo);
        setNome(nome);
        setSalario(salario);
        setValorHora(valorHora);
    }
    

    public Tecnico(String n, double sal, double val) {
        this.nome = n;
        this.salario = sal;
        this.valorHora = val;
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        this.Codigo = codigo;
    }

    @Override
    public String toString() {
        String ret = null;
        ret = "[Nome: " + getNome()+
              "] - [Salario: " + getSalario()+
              "] - [Valor Hora: "+ getValorHora() +"]";
        return ret;
    }
    
    private void grava(){
        TecnicoDAO dao = new TecnicoDAO();
        int codigo = dao.create(this);
        if(codigo > 0 ) setCodigo(codigo);
    }
     
    public static DefaultTableModel getTableModel(){
        List<Tecnico> lista = TecnicoDAO.getInstance().read();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nome");
        modelo.addColumn("Salario");
        modelo.addColumn("Valor Hora");
        
        for(Tecnico t: lista){
            String [] reg = {String.valueOf(t.getCodigo()),t.getNome(),
                            String.valueOf(t.getSalario()), String.valueOf(t.getValorHora())};
            modelo.addRow(reg);
        }
        return modelo;
    }
    
}
