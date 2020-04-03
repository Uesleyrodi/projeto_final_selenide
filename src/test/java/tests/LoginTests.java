package tests;

import common.BaseTest;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.text;

public class LoginTests extends BaseTest {

    @DataProvider(name = "login-alerts")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"uesley_rsantos2@hotmail.com", "abc123", "Usuário e/ou senha inválidos"},
                {"404@hotmail.com", "abc123", "Usuário e/ou senha inválidos"},
                {"", "abc123", "Opps. Cadê o email?"},
                {"uesley_rsantos2@hotmail.com", "", "Opps. Cadê a senha?"},
        };
    }

    //Não colocar o teste de login com sucesso pois há comportamento diferentes
    @Test
    public void shouldSeeLoggedUser() {

        login
                .open()
                .with("uesley_rsantos2@hotmail.com", "pwd123");

        side.loggedUser().shouldHave(text("Uesley"));
    }

    // DDT (Data Driven Testing) Teste orientado a Dados
    @Test(dataProvider = "login-alerts")
    public void shouldSeeLoginAlerts(String email, String pass, String expectAlert) {

        login
                .open()
                .with(email, pass)
                .alert().shouldHave(text(expectAlert));
    }
    @AfterMethod
    public void cleanup() {
        login.clearSession();
    }
}
