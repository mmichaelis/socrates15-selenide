package so.cra.tes.selenide.extjs;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ExamplePage {

  private static final String EXTJS_EXAMPLE_URL = "http://dev.sencha.com/extjs/5.1.0/examples/";

  public static ExamplePage open() {
    return Selenide.open(EXTJS_EXAMPLE_URL, ExamplePage.class);
  }

  public SelenideElement logo() {
    return $(By.id("ext-logo"));
  }

  public SimpleTasksPage openSimpleTasks() {
    Subpage.SIMPLE_TASKS.open();
    return page(SimpleTasksPage.class);
  }

  private enum Subpage {
    SIMPLE_TASKS("simple-tasks", "SimpleTasks");

    private final String urlPattern;
    private final String windowTitle;

    Subpage(String urlPattern, String windowTitle) {
      this.urlPattern = urlPattern;
      this.windowTitle = windowTitle;
    }

    public void open() {
      $("a[href*='" + urlPattern + "']").click();
      Selenide.switchTo().window(windowTitle);
    }
  }
}
