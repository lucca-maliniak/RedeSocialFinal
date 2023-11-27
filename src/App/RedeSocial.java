package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe Rede Social
 * @author Lucca Maliniak
 */
public class RedeSocial {
    private static final JFrame frame = new JFrame("Rede Social - Login");
    private static final JFrame frame2 = new JFrame("Rede Social - Cadastro");
    private static final JPanel painel = new JPanel();
    private static final JPanel painel2 = new JPanel();
    private static final JLabel textoLogin = new JLabel("Digite sua conta!");
    private static final JLabel textoCadastrar = new JLabel("Cadastre-se");
    //k
    private static final JLabel labelEmail = new JLabel("Email:");
    private static final JLabel labelLogin = new JLabel("Login:");
    private static final JTextField inputLogin = new JTextField(20);
    private static final JTextField inputLogin2 = new JTextField(20);
    private static final JTextField inputNome = new JTextField(20);
    private static final JTextField inputNome2 = new JTextField(20);
    private static final JLabel labelSenha = new JLabel("Senha:");
    private static final JPasswordField inputSenha = new JPasswordField(20);
    private static final JPasswordField inputSenha2 = new JPasswordField(20);
    private static final JButton btnCadastrar = new JButton("Cadastrar");
    private static final JButton btnEnviarMensagem = new JButton("Enviar Mensagem");
    private static final JButton btnConsultarMensagem = new JButton("Consultar Mensagens");
    private static final JButton btnEfetuarCadastro = new JButton("Efetuar Cadastro");
    private static final JButton btnLogin = new JButton("Login");
    private static final JLabel lblTelaInicial = new JLabel("Bem vindo a Rede Social!");
    private static final JButton btnIncluirAmigo = new JButton("Incluir Amigo");
    private static final JButton btnConsultarAmigo = new JButton("Consultar Amigos");
    private static final JButton btnRemoverAmigo = new JButton("Remover Amigo");
    private static final JButton btnDeslogar = new JButton("Sair");
    String resultadoMensagem = "";
    int codeUserAtual = 0;
    String nameUserAtual;

    public RedeSocial() {
        inputLogin.setText("");
        inputSenha.setText("");
        frame.setSize(400, 400);
        frame.add(painel);
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));
        painel.setBackground(new Color(255, 255, 255));
        painel.add(textoLogin);
        painel.add(labelLogin);
        painel.add(inputLogin);
        painel.add(labelSenha);
        painel.add(inputSenha);
        painel.add(btnLogin);
        painel.add(btnCadastrar);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaLogin();
    }

    private void telaLogin() {
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputLogin2.setText("");
                inputSenha2.setText("");
                inputNome2.setText("");
                frame2.setSize(400, 400);
                painel2.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));
                painel2.setBackground(new Color(255, 255, 255));
                painel2.add(textoCadastrar);
                painel2.add(labelLogin);
                painel2.add(inputNome2);
                painel2.add(labelEmail);
                painel2.add(inputLogin2);
                painel2.add(labelSenha);
                painel2.add(inputSenha2);
                painel2.add(btnEfetuarCadastro);
                frame2.add(painel2);
                frame2.setVisible(true);
                frame2.setLocationRelativeTo(null);
            }
        });
        btnEfetuarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexaoBD cnxBD = null;
                try {
                    cnxBD = new ConexaoBD();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String emailLogin = inputLogin2.getText();
                String senhaLogin = inputSenha2.getText();
                String nomeLogin = inputNome2.getText();
                try {
                    if (emailLogin.isEmpty() || senhaLogin.isEmpty() || nomeLogin.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    } else {
                        codeUserAtual = cnxBD.addUsuario(nomeLogin, emailLogin, senhaLogin);
                        inputLogin.setText("");
                        inputSenha.setText("");
                        inputNome.setText("");
                        frame2.setVisible(false);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexaoBD cnxBD = null;
                try {
                    cnxBD = new ConexaoBD();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String emailLogin = inputLogin.getText();
                String senhaLogin = inputSenha.getText();
                try {
                    if (emailLogin.isEmpty() || senhaLogin.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha estão vazios! :(");
                    } else if (cnxBD.ObterResultado("nome").contains(emailLogin) && cnxBD.ObterResultado("senha").contains(senhaLogin)) {
                        JOptionPane.showMessageDialog(null, "Login Encontrado! :)");
                        codeUserAtual = cnxBD.consultarId(emailLogin);
                        nameUserAtual = emailLogin;
                        System.out.println(codeUserAtual);
                        TelaInicial();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha incorreto! :(");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void TelaInicial() throws SQLException {
        ConexaoBD cnx = new ConexaoBD();
        ResultSet resultado = cnx.selectUser();
        ArrayList<String> nomesList = new ArrayList<>();
        while (resultado.next()) {
            String nome = resultado.getString("nome");
            nomesList.add(nome);
        }
        String[] nomes = nomesList.toArray(new String[0]);
        int index = codeUserAtual;

        lblTelaInicial.setText("Bem vindo " + nomes[--index] + " :)");
        ArrayList<String> amigos = new ArrayList<>();
        painel.removeAll();
        painel.add(lblTelaInicial);
        painel.add(btnIncluirAmigo);
        painel.add(btnConsultarAmigo);
        painel.add(btnRemoverAmigo);
        painel.add(btnEnviarMensagem);
        painel.add(btnConsultarMensagem);
        painel.add(btnDeslogar);
        painel.updateUI(); // update na tela para carregar os novos componentes

        btnIncluirAmigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String amigo = JOptionPane.showInputDialog("Digite o nome do amigo para adicionar: ");
                    var USER_EXIST = cnx.consultaExistenciaUsuario(amigo);
                    var CODE_USER = cnx.consultarId(amigo);
                    if (USER_EXIST && codeUserAtual != CODE_USER) {
                        amigos.add(amigo);
                        JOptionPane.showMessageDialog(null, "Usuário foi adicionado como amigo com sucesso! :)");
                    } else if (codeUserAtual == CODE_USER){
                        JOptionPane.showMessageDialog(null, "Não é possível adicionar a si mesmo! :(");
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário não foi encontrado no sistema! :(");
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro! :( \n" + err);
                }
            }
        });

        btnConsultarAmigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (amigos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum amigo! :(");
                    } else {
                        String resultado = "";
                        int i = 0;
                        for (String amigo : amigos) {
                            i = amigos.indexOf(amigo);
                            resultado += ++i + "- " + amigo + "\n";
                        }
                        JOptionPane.showMessageDialog(null, resultado);
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro! :( \n" + err);
                }
            }
        });

        btnEnviarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexaoBD conexao = null;
                try {
                    conexao = new ConexaoBD();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if (amigos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum amigo! :(");
                    } else {
                        String resultado = "";
                        int i = 0;
                        for (String amigo : amigos) {
                            i = amigos.indexOf(amigo);
                            resultado += ++i + "- " + amigo + "\n";
                        }
                        String amigoInput = JOptionPane.showInputDialog(resultado + "\nDigite o nome do amigo que deseja mandar a mensagem");
                        if (amigos.contains(amigoInput)) {
                            String mensagem = JOptionPane.showInputDialog("Digite a mensagem");
                            conexao.addMensagem(mensagem, amigoInput);
                        } else {
                            JOptionPane.showMessageDialog(null, "Amigo não encontrado!");
                        }
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante o processo do envio da mensagem! :( \n" + err);
                }
            }
        });

        btnConsultarMensagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultadoMensagem = "";
                    ConexaoBD conexao = new ConexaoBD();
                    ArrayList<String> retorno = conexao.consultarMensagens(nameUserAtual);
                    if(retorno.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrada nenhuma mensagem!");
                    } else {
                        retorno.forEach(elem -> resultadoMensagem += (retorno.indexOf(elem)) + 1 + "- " + elem + "\n");
                        JOptionPane.showMessageDialog(null, "Mensagens:\n" + resultadoMensagem);
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante o processo do envio da mensagem! :( \n" + err);
                }
            }
        });

        btnDeslogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Deslogando...");
                frame.dispose();
            }
        });

        btnRemoverAmigo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (amigos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum amigo! :(");
                    } else {
                        String resultado = "";
                        int i = 0;
                        for (String amigo : amigos) {
                            i = amigos.indexOf(amigo);
                            resultado += ++i + "- " + amigo + "\n";
                        }
                        String amigo = JOptionPane.showInputDialog(resultado + "\nDigite o nome do amigo para remover: ");
                        if (amigos.contains(amigo)) {
                            amigos.remove(amigo);
                            JOptionPane.showMessageDialog(null, "Usuário foi removido como amigo com sucesso! :)");
                        } else {
                            JOptionPane.showMessageDialog(null, "Amigo não foi encontrado no sistema! :(");
                        }
                    }
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro! :( \n" + err);
                }
            }
        });
    }


    public static void main(String[] args) {
        new RedeSocial();
    }
}
;