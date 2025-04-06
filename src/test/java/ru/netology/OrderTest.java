package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void BeforeEach() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);

    }


    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldValidateInputs() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name]input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone]input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]input")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        var actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText.trim());

    }

}