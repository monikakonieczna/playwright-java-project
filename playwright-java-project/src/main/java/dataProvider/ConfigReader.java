package dataProvider;

import java.io.*;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;
    private static ConfigReader configReader;

    private ConfigReader() {
        BufferedReader reader;
        String propertyFilePath = "configs//Configuration.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public static ConfigReader getInstance() {
        if (configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public static String getRunValueFromConfig(ConfigEnums propertyName) {
        Properties prop = new Properties();

        try {
            InputStream inputStream = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            prop.load(inputStream);

            if (prop.getProperty(propertyName.label) != null) {
                return prop.getProperty(propertyName.label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
