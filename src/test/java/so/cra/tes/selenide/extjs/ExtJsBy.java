package so.cra.tes.selenide.extjs;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import so.cra.tes.selenide.extjs.ext.ComponentQuery;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public abstract class ExtJsBy extends By {

  private static String getQuery(@NotNull String originalQuery, @NotNull SearchContext searchContext) {
    if (!(searchContext instanceof WebElement)) {
      return originalQuery;
    }
    WebElement element = (WebElement) searchContext;
    String id = requireNonNull(element.getAttribute("id"), format("Element %s misses required id attribute.", element));
    return format("#%s %s", id, originalQuery);
  }

  public static ExtJsBy query(@NotNull String query) {
    return new ExtJsBy() {
      @Override
      public List<WebElement> findElements(SearchContext context) {
        return ComponentQuery.query(getQuery(query, context)).stream().map(id -> context.findElement(By.id(id))).collect(Collectors.toList());
      }

      @Override
      public String toString() {
        return String.format("Ext Query %s", query);
      }
    };
  }

}
