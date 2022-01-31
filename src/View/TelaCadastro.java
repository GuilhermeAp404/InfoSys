/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Cliente;
import Model.Produto;
import Model.Servico;
import Model.Tecnico;
import Util.Converte;
import Util.Mascara;
import dao.ProdutoDAO;
import dao.TecnicoDAO;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Tegon Faria
 */
public class TelaCadastro extends javax.swing.JFrame {

    private void setMask() {
        tfCPF.setFormatterFactory(Mascara.getCpfMask());
        tfTelefone.setFormatterFactory(Mascara.getFoneMask());
        tfCel.setFormatterFactory(Mascara.getCelMask());
        tfProCusto.setFormatterFactory(Mascara.getValorMask());
        tfProValor.setFormatterFactory(Mascara.getValorMask());
        tfTecSalario.setFormatterFactory(Mascara.getValorMask());
        tfTecValorHora.setFormatterFactory(Mascara.getValorMask());
        tfServTelefoneCl.setFormatterFactory(Mascara.getFoneMask());
        tfServCelularCl.setFormatterFactory(Mascara.getCelMask());
    }

    private void limparCliente() {
        tfNome.setText("");
        tfCPF.setText("");
        tfTelefone.setText("");
        tfEmail.setText("");
        tfCel.setText("");
        tfNome.requestFocus();
    }

    private void gravarCliente() {
        String nome = tfNome.getText();
        String Cpf = tfCPF.getText();
        String fone = tfTelefone.getText();
        String email = tfEmail.getText();
        String cel = tfCel.getText();
        System.out.println(new Cliente(nome, Cpf, fone, cel, email));
        limparCliente();
    }
    
    private void consultaCliente(){
        try{
            Cliente cliente = new Cliente();
            cliente = dao.ClienteDAO.getInstance().findByCodigo(Integer.parseInt(tfCod.getText()));
            tfNome.setText(cliente.getNome());
            tfCPF.setText(cliente.getCpf());
            tfTelefone.setText(cliente.getFone());
            tfCel.setText(cliente.getCelular());
            tfEmail.setText(cliente.getEmail());
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Cadastro não encontrado");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Campo vazio - Digite um código");
        }

    }
    private void deletaCliente(){
        dao.ClienteDAO.getInstance().delete(Integer.parseInt(tfCod.getText()));
        limparCliente();
    }
    
    private void updateCliente(){
        int codigo = Integer.parseInt(tfCod.getText());
        String nome = tfNome.getText();
        String cpf = tfCPF.getText();
        String fone = tfTelefone.getText();
        String celular = tfCel.getText();
        String email = tfEmail.getText();
        Cliente cliente = new Cliente(codigo, nome, cpf, fone, celular, email);
        dao.ClienteDAO.getInstance().update(cliente);
        limparCliente();
    }

    private void gravarProduto() {
        String descricao = tfProdDescricao.getText();
        int estoque = (int) spProdEstoque.getValue();
        boolean ativo = cbAtivo.isSelected();
        double custo = Converte.textoToValue(tfProCusto.getText());
        double valor = Converte.textoToValue(tfProValor.getText());
        System.out.println(new Produto(descricao, ativo, estoque, custo, valor));
        limparProduto();
    }

    private void limparProduto() {
        tfProdDescricao.setText("");
        tfProCusto.setText("");
        spProdEstoque.setValue(0);
        cbAtivo.setSelected(true);
        tfProCusto.setText("0,00");
        tfProValor.setText("0,00");
        tfProdDescricao.requestFocus();

    }
    
    private void consultaProduto(){
        try{
            String padrao = "##.##";
            DecimalFormat df = new DecimalFormat(padrao);
            Produto produto = new Produto();
            produto = dao.ProdutoDAO.getInstance().findByCodigo(Integer.parseInt(tfCod.getText()));
            tfProdDescricao.setText(produto.getDescricao());
            tfProCusto.setText(df.format(produto.getCusto()));
            tfProValor.setText(df.format(produto.getValor()));
            cbAtivo.setSelected(produto.isAtivo());
            spProdEstoque.setValue(produto.getEstoque());
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "O produto desejado não existe");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Campo vazio - Digite um código");
        }
    }
    
    private void updateProduto(){
        int codigo = Integer.parseInt(tfCod.getText());
        String desc = tfProdDescricao.getText();
        double custo = Converte.textoToValue(tfProCusto.getText());
        double valor = Converte.textoToValue(tfProValor.getText());
        boolean ativo = cbAtivo.isSelected();
        int estoque = (int) spProdEstoque.getValue();
        Produto produto = new Produto(codigo, desc, ativo, estoque, custo, valor);
        dao.ProdutoDAO.getInstance().update(produto);
    }
    
    private void deletaProduto(){
        dao.ProdutoDAO.getInstance().delete(Integer.parseInt(tfCod.getText()));
        limparProduto();
    }
    
    
    private void limparTecnico() {
        tfTecNome.setText("");
        tfTecSalario.setText("0,00");
        tfTecValorHora.setText("0,00");
        tfTecNome.requestFocus();
    }

    private void gravarTecnico() {
        String nome = tfTecNome.getText();
        Double salario = Converte.textoToValue(tfTecSalario.getText());
        Double valorHora = Converte.textoToValue(tfTecValorHora.getText());
        System.out.println(new Tecnico(nome, salario, valorHora));
        limparTecnico();
    }
    
    private void deletaTecnico(){
        dao.TecnicoDAO.getInstance().delete(Integer.parseInt(tfCod.getText()));
        limparTecnico();
    }
    
    private void updateTecnico(){
        int codigo = Integer.parseInt(tfCod.getText());
        String nome = tfTecNome.getText();
        double salario = Converte.textoToValue((tfTecSalario.getText()));
        double valHora = Converte.textoToValue(tfTecValorHora.getText());
        Tecnico tecnico = new Tecnico(codigo, nome, salario, valHora);
        dao.TecnicoDAO.getInstance().update(tecnico);
    }
    
    private void consultaTecnico(){
        try{
            String padrao = "##.##";
            DecimalFormat df = new DecimalFormat(padrao);
            Tecnico tecnico = new Tecnico();
            tecnico = dao.TecnicoDAO.getInstance().findByCodigo(Integer.parseInt(tfCod.getText()));
            tfTecNome.setText(tecnico.getNome());
            tfTecSalario.setText(df.format(tecnico.getSalario()));
            tfTecValorHora.setText(df.format(tecnico.getValorHora()));
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "O Tecnico desejado não existe");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Campo vazio - Digite um código");
        }
        
    }
    
    private void gravarServico(){
        Cliente cliente  = new Cliente();
        cliente = dao.ClienteDAO.getInstance().findByCodigo(Integer.parseInt(tfServCodCliente.getText()));
        Tecnico tecnico = new Tecnico();
        tecnico = dao.TecnicoDAO.getInstance().findByCodigo(Integer.parseInt(tfServCodigoTec.getText()));
        String endereco = tfServEnd.getText();
        System.out.println(new Servico(cliente, tecnico, endereco));
        limparServico();
    }
    
    private void consultaServico(){
        try{
            Servico servico = new Servico();
            servico = dao.ServicoDAO.getInstance().findByCodigo(Integer.parseInt(tfCod.getText()));
            tfServCodCliente.setText(Integer.toString(servico.getCliente().getCodigo()));
            tfServCodigoTec.setText(Integer.toString(servico.getTecnico().getCodigo()));
            tfServEnd.setText(servico.getEndereco());
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "O Serviço desejado não existe");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Campo vazio - Digite um código");
        }
        
    }
    
    private void limparServico(){
        tfServCodCliente.setText("");
        tfServNomeCl.setText("");
        tfServNomeTec.setText("");
        tfServTelefoneCl.setText("");
        tfServCelularCl.setText("");
        tfServCodigoTec.setText("");
        tfServEnd.setText("");
        tfServCodCliente.requestFocus();
   }
   
   private void deletaServico(){
       dao.ServicoDAO.getInstance().delete(Integer.parseInt(tfCod.getText()));
       limparServico();
   }
    
   private void updateServico(){
       //Capturar cliente
       Cliente cliente = new Cliente();
       cliente = dao.ClienteDAO.getInstance().findByCodigo(Integer.parseInt(tfServCodCliente.getText()));
       //Capturar Tecnico
       Tecnico tecnico = new Tecnico();
       tecnico = dao.TecnicoDAO.getInstance().findByCodigo(Integer.parseInt(tfServCodigoTec.getText()));
       //Preenche Obejeto serviço
       String endereco = tfServEnd.getText();
       int codigo = Integer.parseInt(tfCod.getText());
       Servico servico = new Servico(codigo, cliente, tecnico, endereco);
       dao.ServicoDAO.getInstance().update(servico);
       limparServico();
   }
   
    public TelaCadastro() {
        initComponents();
        tfNome.requestFocus();
        setMask();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        pnTitulo = new javax.swing.JPanel();
        lbLogo = new javax.swing.JLabel();
        lbTitulo = new javax.swing.JLabel();
        pnButtons = new javax.swing.JPanel();
        btGravar = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        btconsulta = new javax.swing.JButton();
        tfCod = new javax.swing.JTextField();
        btDeletar = new javax.swing.JButton();
        btUpdate = new javax.swing.JButton();
        btListar = new javax.swing.JButton();
        tbTela = new javax.swing.JTabbedPane();
        pnCliente = new javax.swing.JPanel();
        lbNome = new javax.swing.JLabel();
        tfNome = new javax.swing.JTextField();
        lbTelefone = new javax.swing.JLabel();
        lbCelular = new javax.swing.JLabel();
        lbCpf = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        tfCPF = new javax.swing.JFormattedTextField();
        tfTelefone = new javax.swing.JFormattedTextField();
        tfCel = new javax.swing.JFormattedTextField();
        pnProduto = new javax.swing.JPanel();
        lbProdDescricao = new javax.swing.JLabel();
        lbProdEstoque = new javax.swing.JLabel();
        tfProdDescricao = new javax.swing.JTextField();
        spProdEstoque = new javax.swing.JSpinner();
        cbAtivo = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        lbProdCusto = new javax.swing.JLabel();
        lbProdVenda = new javax.swing.JLabel();
        tfProCusto = new javax.swing.JFormattedTextField();
        tfProValor = new javax.swing.JFormattedTextField();
        pnTecnicos = new javax.swing.JPanel();
        lbTecNome = new javax.swing.JLabel();
        tfTecNome = new javax.swing.JTextField();
        lbTecSalario = new javax.swing.JLabel();
        lbTecValorHora = new javax.swing.JLabel();
        tfTecSalario = new javax.swing.JFormattedTextField();
        tfTecValorHora = new javax.swing.JFormattedTextField();
        pnServico = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfServCodCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfServEnd = new javax.swing.JTextField();
        tfServCodigoTec = new javax.swing.JTextField();
        lbServNomeCl = new javax.swing.JLabel();
        tfServNomeCl = new javax.swing.JTextField();
        lbServTelefoneCl = new javax.swing.JLabel();
        tfServTelefoneCl = new javax.swing.JFormattedTextField();
        tfServCelularCl = new javax.swing.JFormattedTextField();
        lbServCelularCl = new javax.swing.JLabel();
        tfServNomeTec = new javax.swing.JTextField();
        lbServNomeCl1 = new javax.swing.JLabel();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnTitulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/InfoSysLogo-Pequeno.png"))); // NOI18N
        pnTitulo.add(lbLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lbTitulo.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lbTitulo.setText("Cadastro\n");
        pnTitulo.add(lbTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 38, -1, -1));

        pnButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btGravar.setText("Gravar");
        btGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGravarActionPerformed(evt);
            }
        });

        btLimpar.setText("Limpar");
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        btconsulta.setText("Consultar");
        btconsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btconsultaActionPerformed(evt);
            }
        });
        btconsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btconsultaKeyPressed(evt);
            }
        });

        tfCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodActionPerformed(evt);
            }
        });
        tfCod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCodKeyPressed(evt);
            }
        });

        btDeletar.setText("Deletar");
        btDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletarActionPerformed(evt);
            }
        });

        btUpdate.setText("Update");
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        btListar.setText("Listar");
        btListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btListarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnButtonsLayout = new javax.swing.GroupLayout(pnButtons);
        pnButtons.setLayout(pnButtonsLayout);
        pnButtonsLayout.setHorizontalGroup(
            pnButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btLimpar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btDeletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btconsulta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfCod, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        pnButtonsLayout.setVerticalGroup(
            pnButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btListar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btGravar)
                    .addComponent(btLimpar)
                    .addComponent(btconsulta)
                    .addComponent(tfCod, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btDeletar)
                    .addComponent(btUpdate))
                .addContainerGap())
        );

        lbNome.setText("Nome:");

        tfNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeActionPerformed(evt);
            }
        });
        tfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNomeKeyPressed(evt);
            }
        });

        lbTelefone.setText("Telefone:");

        lbCelular.setText("Celular:");

        lbCpf.setText("CPF:");

        lbEmail.setText("E-Mail");

        tfEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfEmailKeyPressed(evt);
            }
        });

        tfCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCPFKeyPressed(evt);
            }
        });

        tfTelefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTelefoneKeyPressed(evt);
            }
        });

        tfCel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCelKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnClienteLayout = new javax.swing.GroupLayout(pnCliente);
        pnCliente.setLayout(pnClienteLayout);
        pnClienteLayout.setHorizontalGroup(
            pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNome)
                    .addComponent(lbCpf)
                    .addComponent(lbCelular)
                    .addComponent(lbTelefone)
                    .addComponent(lbEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(tfCPF)
                    .addComponent(tfTelefone)
                    .addComponent(tfCel))
                .addContainerGap(205, Short.MAX_VALUE))
        );
        pnClienteLayout.setVerticalGroup(
            pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnClienteLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNome)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCpf)
                    .addComponent(tfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCelular)
                    .addComponent(tfCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTelefone)
                    .addComponent(tfTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEmail)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        tbTela.addTab("Cliente", pnCliente);

        lbProdDescricao.setText("Descrição:");

        lbProdEstoque.setText("Estoque:");

        tfProdDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfProdDescricaoKeyPressed(evt);
            }
        });

        spProdEstoque.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spProdEstoqueStateChanged(evt);
            }
        });
        spProdEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                spProdEstoqueKeyPressed(evt);
            }
        });

        cbAtivo.setText("Ativo");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Preço"));

        lbProdCusto.setText("Custo:");

        lbProdVenda.setText("Venda:");

        tfProCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfProCustoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbProdCusto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfProCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbProdVenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfProValor, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProdCusto)
                    .addComponent(lbProdVenda)
                    .addComponent(tfProCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfProValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnProdutoLayout = new javax.swing.GroupLayout(pnProduto);
        pnProduto.setLayout(pnProdutoLayout);
        pnProdutoLayout.setHorizontalGroup(
            pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnProdutoLayout.createSequentialGroup()
                        .addGroup(pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbProdDescricao)
                            .addComponent(lbProdEstoque))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfProdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnProdutoLayout.createSequentialGroup()
                                .addComponent(spProdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbAtivo)))
                        .addGap(0, 107, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnProdutoLayout.setVerticalGroup(
            pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProdutoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProdDescricao)
                    .addComponent(tfProdDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProdEstoque)
                    .addComponent(spProdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAtivo))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tbTela.addTab("Produto", pnProduto);

        lbTecNome.setText("Nome:");

        tfTecNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTecNomeKeyPressed(evt);
            }
        });

        lbTecSalario.setText("Salário:");

        lbTecValorHora.setText("Valor Hora:");

        tfTecSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTecSalarioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnTecnicosLayout = new javax.swing.GroupLayout(pnTecnicos);
        pnTecnicos.setLayout(pnTecnicosLayout);
        pnTecnicosLayout.setHorizontalGroup(
            pnTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTecnicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnTecnicosLayout.createSequentialGroup()
                        .addComponent(lbTecNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfTecNome, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnTecnicosLayout.createSequentialGroup()
                        .addComponent(lbTecSalario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTecSalario))
                    .addGroup(pnTecnicosLayout.createSequentialGroup()
                        .addComponent(lbTecValorHora)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTecValorHora)))
                .addContainerGap(218, Short.MAX_VALUE))
        );
        pnTecnicosLayout.setVerticalGroup(
            pnTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTecnicosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTecNome)
                    .addComponent(tfTecNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTecSalario)
                    .addComponent(tfTecSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTecValorHora)
                    .addComponent(tfTecValorHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        tbTela.addTab("Tecnicos", pnTecnicos);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        jLabel1.setText("Codigo do Cliente:");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        jLabel2.setText("Codigo do Tecnico:");

        tfServCodCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfServCodClienteKeyPressed(evt);
            }
        });

        jLabel3.setText("Endereço:");

        tfServCodigoTec.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfServCodigoTecKeyPressed(evt);
            }
        });

        lbServNomeCl.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        lbServNomeCl.setText("Nome:");

        lbServTelefoneCl.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        lbServTelefoneCl.setText("Telefone fixo");

        tfServTelefoneCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfServTelefoneClActionPerformed(evt);
            }
        });

        tfServCelularCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfServCelularClActionPerformed(evt);
            }
        });

        lbServCelularCl.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        lbServCelularCl.setText("Celular");

        lbServNomeCl1.setFont(new java.awt.Font("Dialog", 0, 9)); // NOI18N
        lbServNomeCl1.setText("Nome:");

        javax.swing.GroupLayout pnServicoLayout = new javax.swing.GroupLayout(pnServico);
        pnServico.setLayout(pnServicoLayout);
        pnServicoLayout.setHorizontalGroup(
            pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnServicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnServicoLayout.createSequentialGroup()
                        .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfServCodigoTec))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbServNomeCl1)
                            .addComponent(tfServNomeTec, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnServicoLayout.createSequentialGroup()
                        .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnServicoLayout.createSequentialGroup()
                                    .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbServTelefoneCl)
                                        .addComponent(tfServTelefoneCl, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbServCelularCl)
                                        .addComponent(tfServCelularCl, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnServicoLayout.createSequentialGroup()
                                    .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfServCodCliente, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(33, 33, 33)
                                    .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbServNomeCl)
                                        .addComponent(tfServNomeCl, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel3))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(tfServEnd))
                .addContainerGap())
        );
        pnServicoLayout.setVerticalGroup(
            pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnServicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbServNomeCl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfServCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfServNomeCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnServicoLayout.createSequentialGroup()
                        .addComponent(lbServTelefoneCl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfServTelefoneCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnServicoLayout.createSequentialGroup()
                        .addComponent(lbServCelularCl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfServCelularCl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(pnServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnServicoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfServCodigoTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnServicoLayout.createSequentialGroup()
                        .addComponent(lbServNomeCl1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfServNomeTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfServEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        tbTela.addTab("Serviço", pnServico);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tbTela, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnButtons, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbTela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        if (tbTela.getSelectedComponent() == pnCliente)
            limparCliente();
        else if (tbTela.getSelectedComponent() == pnTecnicos)
            limparTecnico();
        else if (tbTela.getSelectedComponent() == pnServico)
            limparServico();
        else if(tbTela.getSelectedComponent() == pnProduto)limparProduto();    }//GEN-LAST:event_btLimparActionPerformed

    private void btGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGravarActionPerformed
        if (tbTela.getSelectedComponent() == pnCliente)
            gravarCliente();
        else if (tbTela.getSelectedComponent() == pnProduto)
            gravarProduto();
        else if (tbTela.getSelectedComponent() == pnTecnicos)
            gravarTecnico();
        else if (tbTela.getSelectedComponent() == pnServico)
            gravarServico();
    }//GEN-LAST:event_btGravarActionPerformed

    private void spProdEstoqueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spProdEstoqueStateChanged
        int v = (int) spProdEstoque.getValue();
        if (v < 0) {
            spProdEstoque.setValue(0);
        }
    }//GEN-LAST:event_spProdEstoqueStateChanged

    private void btconsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btconsultaActionPerformed
        if(tbTela.getSelectedComponent() == pnCliente) consultaCliente();
        else if(tbTela.getSelectedComponent() == pnTecnicos) consultaTecnico();
        else if(tbTela.getSelectedComponent() == pnProduto) consultaProduto();
        else if(tbTela.getSelectedComponent() == pnServico) consultaServico();
    }//GEN-LAST:event_btconsultaActionPerformed

    private void btDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletarActionPerformed
        if(tbTela.getSelectedComponent()== pnCliente) deletaCliente();
        else if(tbTela.getSelectedComponent() == pnTecnicos)  deletaTecnico();
        else if(tbTela.getSelectedComponent() == pnProduto)  deletaProduto();
        else if(tbTela.getSelectedComponent() == pnServico) deletaServico();
    }//GEN-LAST:event_btDeletarActionPerformed

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
         if(tbTela.getSelectedComponent()== pnCliente) updateCliente();
         else if(tbTela.getSelectedComponent() == pnTecnicos) updateTecnico();
         else if(tbTela.getSelectedComponent() == pnProduto) updateProduto();
         else if(tbTela.getSelectedComponent() == pnServico) updateServico();
    }//GEN-LAST:event_btUpdateActionPerformed

    private void tfNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeActionPerformed

    private void tfCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodActionPerformed

    private void btListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btListarActionPerformed
        String cadastro = null;
        if(tbTela.getSelectedComponent() == pnCliente) cadastro = "Cliente";
        else if(tbTela.getSelectedComponent() == pnProduto) cadastro = "Produto";
        else if(tbTela.getSelectedComponent() == pnTecnicos) cadastro = "Tecnico";
        else if(tbTela.getSelectedComponent() == pnServico) cadastro = "Servicos";
        TelaConsulta tela = new TelaConsulta(cadastro);
        tela.setVisible(true);
    }//GEN-LAST:event_btListarActionPerformed

    private void tfServCelularClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfServCelularClActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfServCelularClActionPerformed

    private void tfServTelefoneClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfServTelefoneClActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfServTelefoneClActionPerformed

    private void tfServCodClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfServCodClienteKeyPressed
        try{
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                Cliente cliente = new Cliente();
                cliente = dao.ClienteDAO.getInstance().findByCodigo(Integer.parseInt(tfServCodCliente.getText()));
                tfServNomeCl.setText(cliente.getNome());
                tfServTelefoneCl.setText(cliente.getFone());
                tfServCelularCl.setText(cliente.getCelular());
                tfServCodigoTec.requestFocus(); 
            }
        }catch(NumberFormatException ex){
            tfServNomeCl.setText("");
            tfServTelefoneCl.setText("");
            tfServCelularCl.setText("");           
            JOptionPane.showMessageDialog(null, "Nenhum código foi digitado");
            tfServCodCliente.requestFocus();
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Cadastro não encontrado");
        }
    }//GEN-LAST:event_tfServCodClienteKeyPressed

    private void tfServCodigoTecKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfServCodigoTecKeyPressed
        // TODO add your handling code here:
        try{
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                Tecnico tecnico = new Tecnico();
                tecnico = dao.TecnicoDAO.getInstance().findByCodigo(Integer.parseInt(tfServCodigoTec.getText()));
                tfServNomeTec.setText(tecnico.getNome());
                tfServEnd.requestFocus();
            }
        }catch(NumberFormatException ex){
            tfServNomeTec.setText("");
            JOptionPane.showMessageDialog(null, "Nenhum código foi digitado");
            tfServCodigoTec.requestFocus();
            
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Cadastro não encontrado");
        }
        
    }//GEN-LAST:event_tfServCodigoTecKeyPressed

    private void tfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomeKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfCPF.requestFocus();
    }//GEN-LAST:event_tfNomeKeyPressed

    private void tfCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCPFKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfCel.requestFocus();
    }//GEN-LAST:event_tfCPFKeyPressed

    private void tfCelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCelKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfTelefone.requestFocus();
    }//GEN-LAST:event_tfCelKeyPressed

    private void tfTelefoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTelefoneKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfEmail.requestFocus();
    }//GEN-LAST:event_tfTelefoneKeyPressed

    private void tfEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailKeyPressed

    private void tfCodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCodKeyPressed

    }//GEN-LAST:event_tfCodKeyPressed

    private void btconsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btconsultaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btconsultaKeyPressed

    private void tfProdDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfProdDescricaoKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfProCusto.requestFocus();
    }//GEN-LAST:event_tfProdDescricaoKeyPressed

    private void spProdEstoqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spProdEstoqueKeyPressed

    }//GEN-LAST:event_spProdEstoqueKeyPressed

    private void tfProCustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfProCustoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfProValor.requestFocus();
    }//GEN-LAST:event_tfProCustoKeyPressed

    private void tfTecNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTecNomeKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfTecSalario.requestFocus();
    }//GEN-LAST:event_tfTecNomeKeyPressed

    private void tfTecSalarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTecSalarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            tfTecValorHora.requestFocus();
    }//GEN-LAST:event_tfTecSalarioKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadastro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadastro().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDeletar;
    private javax.swing.JButton btGravar;
    private javax.swing.JButton btLimpar;
    private javax.swing.JButton btListar;
    private javax.swing.JButton btUpdate;
    private javax.swing.JButton btconsulta;
    private javax.swing.JCheckBox cbAtivo;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbCelular;
    private javax.swing.JLabel lbCpf;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbLogo;
    private javax.swing.JLabel lbNome;
    private javax.swing.JLabel lbProdCusto;
    private javax.swing.JLabel lbProdDescricao;
    private javax.swing.JLabel lbProdEstoque;
    private javax.swing.JLabel lbProdVenda;
    private javax.swing.JLabel lbServCelularCl;
    private javax.swing.JLabel lbServNomeCl;
    private javax.swing.JLabel lbServNomeCl1;
    private javax.swing.JLabel lbServTelefoneCl;
    private javax.swing.JLabel lbTecNome;
    private javax.swing.JLabel lbTecSalario;
    private javax.swing.JLabel lbTecValorHora;
    private javax.swing.JLabel lbTelefone;
    private javax.swing.JLabel lbTitulo;
    private javax.swing.JPanel pnButtons;
    private javax.swing.JPanel pnCliente;
    private javax.swing.JPanel pnProduto;
    private javax.swing.JPanel pnServico;
    private javax.swing.JPanel pnTecnicos;
    private javax.swing.JPanel pnTitulo;
    private javax.swing.JSpinner spProdEstoque;
    private javax.swing.JTabbedPane tbTela;
    private javax.swing.JFormattedTextField tfCPF;
    private javax.swing.JFormattedTextField tfCel;
    private javax.swing.JTextField tfCod;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfNome;
    private javax.swing.JFormattedTextField tfProCusto;
    private javax.swing.JFormattedTextField tfProValor;
    private javax.swing.JTextField tfProdDescricao;
    private javax.swing.JFormattedTextField tfServCelularCl;
    private javax.swing.JTextField tfServCodCliente;
    private javax.swing.JTextField tfServCodigoTec;
    private javax.swing.JTextField tfServEnd;
    private javax.swing.JTextField tfServNomeCl;
    private javax.swing.JTextField tfServNomeTec;
    private javax.swing.JFormattedTextField tfServTelefoneCl;
    private javax.swing.JTextField tfTecNome;
    private javax.swing.JFormattedTextField tfTecSalario;
    private javax.swing.JFormattedTextField tfTecValorHora;
    private javax.swing.JFormattedTextField tfTelefone;
    // End of variables declaration//GEN-END:variables

    public void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
