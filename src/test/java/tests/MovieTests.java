package tests;

import common.BaseTest;
import libs.Database;
import models.MovieModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MovieTests extends BaseTest {

    private Database db;

    @BeforeMethod
    public void setup(){
        login
                .open()
                .with("uesley_rsantos2@hotmail.com", "pwd123");

        side.loggedUser().shouldHave(text("Uesley"));
    }

    @BeforeSuite //Executa apenas uma unica vez
    public void delorean(){
        db = new Database();
        db.resetMovies();
    }

    @Test
    public void shouldRegisterANewMovie(){

        MovieModel movieData = new MovieModel(
                "Jumanji - Próxima fase",
                "Pré-venda",
                2020,
                "16/01/2020",
                Arrays.asList("The Rock", "Jack Black", "Kevin Hart", "Karen Gillan", "Denny DeVito"),
                "Tentado em visitar  o mundo de Jumanji, Spencer decide conserta-o"
                        + "o bug no jogo do game que permite que sejam transportados ao local",
                "jumanji.jpg"
        );


        movie
                .add()
                .create(movieData)
                //Perguntando se existe o cara na lista
                .items().findBy(text(movieData.title)).shouldBe(visible);
    }

    @Test
    public void shouldSearchTwoMovie() {

        movie.search("Batman").items().shouldHaveSize(2);
    }

    @AfterMethod
    public void cleanup() {
        login.clearSession();
    }
}
