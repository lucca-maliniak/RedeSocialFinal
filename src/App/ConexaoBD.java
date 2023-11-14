package App;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ConexaoBD {
        String url = "jdbc:sqlite:/Users/lucca.balona/IdeaProjects/RedeSocialFinal/src/bd/BD_RedeSocial";
        String usuario = "root";
        String senha = "";
        ResultSet resul;
        ArrayList<String> retornoBD = new ArrayList<>();
    ArrayList ObterResultado(String type) throws SQLException {
        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            Statement statement = conexao.createStatement();
            String query = "SELECT * FROM USUARIOS";
            resul = statement.executeQuery(query);
            while(resul.next()){
                retornoBD.add(resul.getString(type));
            }
            statement.close();
            conexao.close();
            return retornoBD;
        } catch (Exception e) {
            System.err.println("Erro ocorrido: " + e);
            return retornoBD;
        }
    }

    public void addUsuario(String nomeInput, String emailInput, String senhaInput) throws Exception {
        try{
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            Statement statement = conexao.createStatement();

            String queryMax = "SELECT MAX(COD_USUARIO) FROM USUARIOS";
            ResultSet resultSet = statement.executeQuery(queryMax);
            int contadorUser = resultSet.getInt(1) + 1;

            String query = "INSERT INTO USUARIOS(COD_USUARIO, NOME, EMAIL, SENHA) VALUES (" + contadorUser + "," + "'" + nomeInput + "'" +  "," + "'" + emailInput + "'" + "," + "'" + senhaInput + "'" + ")";
            int resulUpdate = statement.executeUpdate(query); // executeQuery para select e executeUpdate para todos os outros metodos do CRUD
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso! :)");
            statement.close();
            conexao.close();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Algo de errado aconteceu! :(");
            System.err.println(e);
        }
    }
}

