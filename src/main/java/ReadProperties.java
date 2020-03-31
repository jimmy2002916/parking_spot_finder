import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    private String fileSource;

    public ReadProperties (String fileSource) {
        this.fileSource = fileSource;
    }

    public String getProperty(String key) {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(this.fileSource);

            // Load a properties file
            prop.load(input);

            // Get the property value
            return prop.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}