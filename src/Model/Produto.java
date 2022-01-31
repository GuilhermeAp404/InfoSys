/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import dao.ProdutoDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tegon Faria
 */
public class Produto {
    private int codigo = 0;
    private String descricao = null;
    private int estoque;
    private boolean ativo = false;
    private double valor;
    private double custo=0;
    
    public Produto(String descricao,boolean ativo, int estoque, double custo, double valor) {
        setDescricao(descricao);
        setAtivo(ativo);
        setEstoque(estoque);
        setCusto(custo);
        setValor(valor);
        gravar();
    }
    
    public Produto(int codigo, String descricao, boolean ativo, int estoque, double custo, double valor) {
        setCodigo(codigo);
        setAtivo(ativo);
        setDescricao(descricao);
        setEstoque(estoque);
        setCusto(custo);
        setValor(valor);
    }

    public Produto() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    @Override
    public String toString() {
        String ret = null;
        if(isAtivo()) ret = "[Ativo] - ";
        else ret = "[Fora de Linha] - ";
        ret = ret + "[Item: " + getDescricao() +
                    "] - [Estoque: " + getEstoque()+
                    "] - [Custo: " + getCusto() +
                    "] - [Preço de Venda: " + getValor();
        return ret;
    }
    
    private void gravar(){
        ProdutoDAO dao = new ProdutoDAO();
        int codigo = dao.create(this);
        if(codigo > 0) setCodigo(codigo);
    }
    
    public static DefaultTableModel getTableModel(){
        List<Produto> lista = ProdutoDAO.getInstance().read();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descrição");
        modelo.addColumn("Estoque");
        modelo.addColumn("Custo");
        modelo.addColumn("Valor");
        for(Produto p: lista){
            String [] reg = {String.valueOf(p.getCodigo()),p.getDescricao(),String.valueOf(p.getEstoque()),
                            String.valueOf(p.getCusto()),String.valueOf(p.getValor())};
            modelo.addRow(reg);
        }
        return modelo;
    }
    
}
