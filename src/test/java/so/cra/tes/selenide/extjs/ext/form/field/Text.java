package so.cra.tes.selenide.extjs.ext.form.field;

import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;

public class Text extends Base {
  public static final String XTYPE = "textfield";

  public Text(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Text(@NotNull String query) {
    super(query);
  }

  public SelenideElement input() {
    return element().$("input");
  }
}
