package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/google-chrome"); // Явное указание пути
        options.addArguments(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--remote-allow-origins=*",
                "--disable-gpu",
                "--window-size=1920,1080"
        );
        options.setCapability("acceptInsecureCerts", true);

        try {
            driver = new ChromeDriver(options);
        } catch (SessionNotCreatedException e) {
            System.out.println("Ошибка создания сессии: " + e.getMessage());
            throw e;
        }


        @BeforeAll
        static void setupAll () {
            try {
                WebDriverManager.chromedriver().clearDriverCache().setup();
                System.out.println("ChromeDriver version: "
                        + WebDriverManager.chromedriver().getDownloadedDriverVersion()n());
            } catch (Exception e) {
                System.err.println("Ошибка инициализации WebDriverManager: " + e);
                throw e;
            }
        }

        @AfterEach
        void tearDown () {
            driver.quit();
            driver = null;
        }

        @Test
        void shouldValidateInputs () {
            driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
            driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
            driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
            driver.findElement(By.cssSelector("button[type=button]")).click();
            String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
            assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
        }

    }
