import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Project {
@Test
    public void first() throws InterruptedException {
        WebDriver wb = new ChromeDriver();
        wb.manage().window().maximize();
        wb.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wb.get("https://www.cargurus.com/");
        wb.findElement(By.xpath("//label[@data-tab-name='UsedCar']")).click();
    Assert.assertEquals(new Select(wb.findElement(By.id("carPickerUsed_makerSelect"))).getFirstSelectedOption().getText(),"All Makes");
        new Select(wb.findElement(By.id("carPickerUsed_makerSelect"))).selectByVisibleText("Lamborghini");
    Assert.assertEquals(new Select(wb.findElement(By.id("carPickerUsed_modelSelect"))).getFirstSelectedOption().getText(),"All Models");
    List<String> allModels = new ArrayList<>();
    for (WebElement one :new Select(wb.findElement(By.id("carPickerUsed_modelSelect"))).getOptions()){
        allModels.add(one.getText());
    }
    Assert.assertEquals(allModels, List.of("All Models", "Aventador",  "Gallardo","Huracan", "Urus",
            "400GT", "Centenario", "Countach", "Diablo", "Espada", "Murcielago"));
    new Select(wb.findElement(By.id("carPickerUsed_modelSelect"))).selectByVisibleText("Gallardo");
        wb.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).clear();
        wb.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031", Keys.ENTER);

    List<WebElement> elements = wb.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
    for (WebElement one : elements){
        Assert.assertTrue(one.getText().contains("Lamborghini Gallardo"));
    }
    new Select(wb.findElement(By.id("sort-listing"))).selectByVisibleText("Lowest price first");
    Thread.sleep(1000);
    List<WebElement> elements1 = wb.findElements(By.xpath("//span[@data-cg-ft='srp-listing-blade-price-and-payment'][not(contains(@href, 'FEATURED'))]"));
    elements1.remove(0);
    List<Double> newPrice2 = new ArrayList<>();
    for (WebElement one: elements1){
        newPrice2.add(Double.valueOf(one.getText().substring(1,7).replace(',','.')));
    }
    Double[] o = newPrice2.toArray(new Double[0]);
    Arrays.sort(o);
    Assert.assertEquals(newPrice2.toString(),Arrays.toString(o));
    System.out.println(newPrice2);
    System.out.println(Arrays.toString(o));

    new Select(wb.findElement(By.id("sort-listing"))).selectByVisibleText("Highest mileage first");
    Thread.sleep(1000);
    List<WebElement> miles = wb.findElements(By.xpath("//p[@class=\"JKzfU4 umcYBP\"]//span[not(@class)]"));
    List<Double> second  = new ArrayList<>();
    for(WebElement mile:miles){
        String one = mile.getText();
        second.add(Double.parseDouble(one.substring(0,one.length()-3).replace(",",".")));
    }
    List<Double> coppy = new ArrayList<>(second);
    Collections.sort(coppy);
    Collections.reverse(coppy);
    System.out.println(second);
    System.out.println(coppy);
    Assert.assertEquals(second,coppy);
    Thread.sleep(1000);
//    new WebDriverWait(wb,Duration.ofSeconds(5)).until(ExpectedConditions.())
    wb.findElement(By.xpath("//*[@id=\"cargurus-listing-search\"]/div/div/div[2]/div[1]/div[2]/div[2]/fieldset[5]/ul/li[1]")).click();
    Thread.sleep(1000);
    List<WebElement> option = wb.findElements(By.cssSelector("a[data-cg-ft=\"car-blade-link\"]"));
    for (WebElement one :option){
        Assert.assertTrue(one.getText().contains("Coupe AWD"));
    }
    option.get(option.size()-1).click();
    wb.navigate().back();
    Assert.assertTrue(wb.getPageSource().contains("Viewed"));
    Thread.sleep(5000);
    wb.close();
    }
}
