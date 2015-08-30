package so.cra.tes.selenide.extjs.ext.tree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;
import so.cra.tes.selenide.extjs.ext.panel.Table;

public class Panel extends Table {
  public Panel(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Panel(@NotNull String query) {
    super(query);
  }
}
