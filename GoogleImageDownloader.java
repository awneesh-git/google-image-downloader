package testcases;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GoogleImageDownloader {

    public static void main(String[] args) throws IOException, InterruptedException {
     //   WebDriver driver = new ChromeDriver();
    	
    	//For Chrome Driver
    	
  //      System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        
    	//For edge driver
    	
    	System.setProperty("webdriver.edge.driver", "C://Users//YOYO//Downloads//edgedriver_win64//msedgedriver.exe");

    	EdgeDriver driver = new EdgeDriver();

    	
        driver.manage().window().maximize();
        String searchQuery = "sunsets";

        // 1. Go to Google Images
        driver.get("https://images.google.com/");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(searchQuery + Keys.ENTER);

        // 2. Scroll to load more images
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 3; i++) {
            js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
            Thread.sleep(2000);
        }

        // 3. Click on thumbnails and collect image URLs
        List<WebElement> images = driver.findElements(By.cssSelector("img.Q4LuWd"));
        int count = 0;
        for (WebElement img : images) {
            try {
                img.click();
                Thread.sleep(1000);
                WebElement actualImage = driver.findElement(By.cssSelector("img.n3VNCb"));
                String imageURL = actualImage.getAttribute("src");
                if (imageURL.startsWith("http")) {
                    FileUtils.copyURLToFile(new URL(imageURL), new File("downloads/image_" + count + ".jpg"));
                    count++;
                }
            } catch (Exception e) {
                System.out.println("Skipping one image: " + e.getMessage());
            }
            if (count >= 5) break; // Limit to 5 images for testing
        }

        driver.quit();
    }
}

