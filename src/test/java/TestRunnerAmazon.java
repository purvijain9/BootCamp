import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public class TestRunnerAmazon {
    @Test
    public void OpenShoppingSiteAmazon() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","/Users/purvijain/Downloads/chromedriver");
        WebDriver driver =new ChromeDriver();

        driver.get("http://www.amazon.in");
        driver.manage().window().maximize();

        driver.findElement(By.id("twotabsearchtextbox")).click();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Apple iPhone 13 (128GB) - Midnight");
        driver.findElement(By.id("nav-search-submit-button")).click();
        List<WebElement> elements = driver.findElements
                (By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
        for(WebElement element:elements){
            if(element.getText().equalsIgnoreCase("Apple iPhone 13 (128GB) - Midnight")){
                element.click();
                break;
            }
        }
        Thread.sleep(20000);

        Set<String> windows = driver.getWindowHandles();
        Iterator<String> it =  windows.iterator();
        String parentID=it.next();
        String childID =it.next();
        driver.switchTo().window(childID);

        String productPrice=driver.findElement(By.cssSelector("span.a-price.a-text-price.a-size-medium.apexPriceToPay")).getText();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,150)", "");

        String productDetails=driver.findElement(By.cssSelector("ul.a-unordered-list.a-vertical.a-spacing-mini")).getText();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        JSONObject amazonProductDetails =new JSONObject();
        amazonProductDetails.put("Date",dtf.format(now));
        amazonProductDetails.put("Product Price",productPrice);
        amazonProductDetails.put("Product Details",productDetails);

        JSONObject amazonObject =new JSONObject();
        amazonObject.put("Amazon",amazonProductDetails);
        System.out.println(amazonObject);
    }
}
