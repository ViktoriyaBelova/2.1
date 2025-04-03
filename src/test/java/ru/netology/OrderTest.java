package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup(); // Автоматическая настройка драйвера
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Режим без GUI
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testOrder() {
        driver.get("http://localhost:9999"); // URL вашего SUT
        // Ваши тесты...
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}