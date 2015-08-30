package so.cra.tes.selenide.extjs.ext.panel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;

public class Table extends Panel {
  public Table(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Table(@NotNull String query) {
    super(query);
  }
}
