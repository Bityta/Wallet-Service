package org.example.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Класс, который читает файл Application.property
 */
public final class PropertiesUtil {


    private static final Properties PROPERTIES = new Properties();


    static {
        loadProperties();
    }

    /**
     * Приватный конструктор
     */
    private PropertiesUtil() {
    }

    /**
     * Получение значения по ключю из файла
     *
     * @param key ключ
     * @return значение
     */
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Загрузка свойств с файла
     */
    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}