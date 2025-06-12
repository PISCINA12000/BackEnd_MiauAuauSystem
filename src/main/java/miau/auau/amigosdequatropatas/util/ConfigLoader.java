package miau.auau.amigosdequatropatas.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("dbconfig.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo 'dbconfig.properties' não encontrado.");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco: " + e.getMessage(), e);
        }
    }

    public static String get(String chave) {
        return props.getProperty(chave);
    }
}
