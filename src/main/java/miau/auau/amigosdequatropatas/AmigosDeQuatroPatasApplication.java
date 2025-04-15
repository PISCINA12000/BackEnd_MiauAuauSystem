package miau.auau.amigosdequatropatas;

import miau.auau.amigosdequatropatas.util.SingletonDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.swing.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AmigosDeQuatroPatasApplication {
    private static final String mydatabase = "auaumiaudb";

    public static void main(String[] args) {
        // Sua lógica personalizada de conexão de banco de dados
        SingletonDB singletonDB = SingletonDB.getInstance();
        if (singletonDB.Conectar()) {
            System.out.println("Conectado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Problemas ao conectar: " + singletonDB.getConexao().getMensagemErro());
            if (JOptionPane.showConfirmDialog(null, "Confirma a tentativa de criação de uma nova base de dados") == JOptionPane.YES_OPTION) {
                if (singletonDB.criarBD(mydatabase)) {
                    try {
                        singletonDB.restaurar("bkpinicial.backup", mydatabase);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SpringApplication.run(AmigosDeQuatroPatasApplication.class, args);
    }
}
