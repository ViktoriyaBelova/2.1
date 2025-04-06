package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
        System.out.println("ChromeDriver version: " +
                WebDriverManager.chromedriver().getDownloadedDriverVersion());
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-dev-shm-usage",
                "--no-sandbox",
              //  "--headless",
                "--remote-allow-origins=*"
        );
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @Test
    void shouldValidateInputs() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button[type=button]")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
    }

    @Test
    void shouldShowErrorIfNameInvalid() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button[type=button]")).click();
        String errorText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", errorText.trim());
    }
}