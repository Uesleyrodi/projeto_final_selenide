package common;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.SideBar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.screenshot;

public class BaseTest {

    protected static LoginPage login;
    protected static SideBar side;
    protected static MoviePage movie;

    @BeforeMethod
    public void start() {

        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            prop.load(inputStream);
        } catch (Exception ex){
            System.out.println("Deu ruim ao carregar o config.properties. Trace =>" + ex.getMessage());
        }

        Configuration.browser = prop.getProperty(("browser"));
        Configuration.baseUrl = prop.getProperty("url");
        Configuration.timeout = Long.parseLong(prop.getProperty("timeout")); // Configurando o timeout

        login = new LoginPage();
        side = new SideBar();
        movie = new MoviePage();
    }

    @AfterMethod
    public void finish() {
        // Tiramos um screenshot pelo Selenide
        String tempShot = screenshot("temp_shot");

        //Queremos trasnformar em binário para anezar no report do allure
        try {
            BufferedImage bimage = ImageIO.read(new File(tempShot));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(bimage, "png", baos);

            byte[] finalShot = baos.toByteArray();

            io.qameta.allure.Allure.addAttachment("Evidência", new ByteArrayInputStream(finalShot));

        } catch (Exception ex) {
            System.out.println("Deu erro ao anexar o screenshot :( Trace =>" + ex.getMessage());
        }
    }
}
