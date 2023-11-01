package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RedeSocial {
    private static final JFrame frame = new JFrame("Rede Social");
    private static final JPanel painel = new JPanel();
    private static final JPanel painel2 = new JPanel();
    private static final JLabel textoLogin = new JLabel("Digite sua conta!");
    private static final JLabel textoCadastrar = new JLabel("Cadastre-se");
    private static final JLabel labelLogin = new JLabel("Login:");
    private static final JLabel labelNome = new JLabel("Nome:");
    private static final JTextField inputLogin = new JTextField(20);
    private static final JTextField inputNome = new JTextField(20);
    private static final JLabel labelSenha = new JLabel("Senha:");
    private static final JPasswordField inputSenha = new JPasswordField(20);
    private static final JButton btnCadastrar = new JButton("Cadastrar");
    private static final JButton btnEfetuarCadastro = new JButton("Efetuar Cadastro");
    private static final JButton btnLogin = new JButton("Login");

    public RedeSocial() {
        frame.setSize(400, 400);
        frame.add(painel);
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 20));
        painel.setBackground(new Color(255, 255, 255));
        painel.add(textoLogin);
        painel.add(labelLogin);
        painel.add(inputLogin);
        painel.add(labelSenha);
        painel.add(inputSenha);
        painel.add(btnCadastrar);
        painel.add(btnLogin);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(painel2);
                painel2.add(textoCadastrar);
                painel2.add(labelNome);
                painel2.add(inputNome);
                painel2.add(labelLogin);
                painel2.add(inputLogin);
                painel2.add(labelSenha);
                painel2.add(inputSenha);
                painel2.add(btnCadastrar);
                painel2.setVisible(true);
            }
        });
        btnEfetuarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexaoBD cnxBD = new ConexaoBD();
                String emailLogin = inputLogin.getText();
                String senhaLogin = inputSenha.getText();
                String nomeLogin = inputNome.getText();
                try {
                    cnxBD.addUsuario(nomeLogin, emailLogin, senhaLogin);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConexaoBD cnxBD = new ConexaoBD();
                String emailLogin = inputLogin.getText();
                String senhaLogin = inputSenha.getText();
                try {
                    if(cnxBD.ObterResultado("nome").contains(emailLogin) && cnxBD.ObterResultado("senha").contains(senhaLogin)){
                        JOptionPane.showMessageDialog(null,"Login Encontrado! :)");
                    } else {
                        JOptionPane.showMessageDialog(null,"Usuário ou senha incorreto! :(");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void main(String[] args) {
        new RedeSocial();
//        ConexaoBD ResultadoConexaoBD = new ConexaoBD();
    }
}
;