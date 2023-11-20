package App;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe de conexão com o banco de dados
 * @author Lucca Maliniak
 */
public class ConexaoBD {
    String url = "jdbc:sqlite:/Users/lucca.balona/IdeaProjects/RedeSocialFinal/src/bd/BD_RedeSocial";
    String usuario = "root";
    String senha = "";
    ResultSet resul;
    ArrayList<String> retornoBD = new ArrayList<>();
    ArrayList<String> retornoBD2 = new ArrayList<>();
    Connection conexao;
    Statement statement;

    public ConexaoBD() throws SQLException {
    }

    public ArrayList ObterResultado(String type) throws SQLException {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.createStatement();
            String query = "SELECT * FROM USUARIOS";
            resul = statement.executeQuery(query);
            while (resul.next()) {
                retornoBD.add(resul.getString(type));
            }
            return retornoBD;
        } catch (Exception e) {
            System.err.println("Erro ocorrido: " + e);
            return retornoBD;
        } finally {
            conexao.close();
            statement.close();
            if (conexao != null) {
                conexao.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public boolean consultaExistenciaUsuario(String nome) throws SQLException {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.createStatement();
            String query = "SELECT * FROM USUARIOS WHERE NOME = '" + nome + "'";
            ResultSet resultado = statement.executeQuery(query);
            return resultado.next();
        } catch (Exception e) {
            System.err.println("Erro ao consultar a existência do usuário: " + e);
            return false;
        } finally {
            if (conexao != null) {
                conexao.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public int addUsuario(String nomeInput, String emailInput, String senhaInput) throws Exception {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.createStatement();
            String queryMax = "SELECT MAX(COD_USUARIO) FROM USUARIOS";
            ResultSet resultSet = statement.executeQuery(queryMax);
            int contadorUser = resultSet.getInt(1) + 1;
            String query = "INSERT INTO USUARIOS(COD_USUARIO, NOME, EMAIL, SENHA) VALUES (" + contadorUser + "," + "'" + nomeInput + "'" + "," + "'" + emailInput + "'" + "," + "'" + senhaInput + "'" + ")";
            int resulUpdate = statement.executeUpdate(query);
            if (resulUpdate == 1) JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso! :)");
            return contadorUser;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo de errado aconteceu! :( \n" + e);
            return 0;
        } finally {
            if (conexao != null) {
                conexao.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }


    public ArrayList<String> consultarMensagens(int indexUser) throws SQLException {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.createStatement();
            String query = "SELECT DESC_MSG FROM MENSAGEM WHERE FK_USER = " + indexUser;
            ResultSet resultado = statement.executeQuery(query);
            retornoBD2.clear();
            while (resultado.next()) {
                retornoBD2.add(resultado.getString("DESC_MSG"));
            }
            return retornoBD2;
        } catch (Exception e) {
            System.err.println("Erro ao consultar mensagens: " + e);
            return retornoBD2;
        } finally {
            if (conexao != null) {
                conexao.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }



    public void addMensagem(String mensagem, Integer indexUserDestino) {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.createStatement();
            String queryMax = "SELECT MAX(COD_MSG) FROM MENSAGEM";
            ResultSet resultSet = statement.executeQuery(queryMax);
            int contadorMsg = resultSet.getInt(1) + 1;
            String query = "INSERT INTO MENSAGEM(COD_MSG, DESC_MSG, FK_USER) VALUES (" + contadorMsg + "," + "'" + mensagem + "'" + "," + indexUserDestino + ")";
            int resulUpdate = statement.executeUpdate(query);
            if (resulUpdate == 1) JOptionPane.showMessageDialog(null, "Mensagem enviada com sucesso! :)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo de errado aconteceu! :( \n" + e);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public int consultarId(String nome) {
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            statement = conexao.createStatement();
            String query = "SELECT COD_USUARIO FROM USUARIOS WHERE NOME = '" + nome + "'";
            ResultSet resultado = statement.executeQuery(query);
            int id = 0;
            if (resultado.next()) {
                id = resultado.getInt("COD_USUARIO");
            }
            return id;
        } catch (Exception e) {
            System.out.println("Erro ao consultar ID do usuário: " + e);
            return 0;
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}

