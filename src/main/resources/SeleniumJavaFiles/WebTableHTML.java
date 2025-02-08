
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTableHTML1 {

    public static void main(String[] args) {
        // 1. Launch the URL https://html.com/tags/table/
        //2. Get the count of number of rows
        //3. Get the count of number of columns

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();

        // 1. Launch the URL https://html.com/tags/table/
        driver.get("https://html.com/tags/table/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30));

        //Get the no.of rows and columns of tables
        List<WebElement> rows = driver.findElements(By.xpath("//table//tr"));
        System.out.println("No.of Rows: "+rows.size());
        List<WebElement> columns = driver.findElements(By.xpath("//table//tr//th"));
        System.out.println("No.of Columns: "+columns.size());


    }

}