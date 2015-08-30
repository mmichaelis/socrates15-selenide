package so.cra.tes.selenide.extjs.ext.panel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;
import so.cra.tes.selenide.extjs.ext.container.Container;

public class Panel extends Container {
  public Panel(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Panel(@NotNull String query) {
    super(query);
  }
}
