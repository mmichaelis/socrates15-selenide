package so.cra.tes.selenide.extjs.ext.form;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;

public class Panel extends so.cra.tes.selenide.extjs.ext.panel.Panel {
  public static final String XTYPE = "form";

  public Panel(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Panel(@NotNull String query) {
    super(query);
  }
}
