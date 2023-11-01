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
        int contadorUser = 1;
        try{
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            Statement statement = conexao.createStatement();
            String query = "INSERT INTO USUARIOS(COD_USUARIO, NOME, EMAIL, SENHA) VALUES (" + contadorUser + "," + "'" + nomeInput + "'" +  "," + "'" + emailInput + "'" + "," + "'" + senhaInput + "'" + ")";
            resul = statement.executeQuery(query);
            if(resul.rowInserted()){
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso! :)");
            } else {
                JOptionPane.showMessageDialog(null, "Algo de errado aconteceu! :(");
            }
            statement.close();
            conexao.close();
        } catch(Exception e){
            System.err.println(e);
        }
    }
}

