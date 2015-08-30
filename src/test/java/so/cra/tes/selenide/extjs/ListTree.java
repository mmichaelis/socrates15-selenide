package so.cra.tes.selenide.extjs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;
import so.cra.tes.selenide.extjs.ext.tree.Panel;

public class ListTree extends Panel {
  public static final String XTYPE = "listTree";

  public ListTree(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public ListTree(@NotNull String query) {
    super(query);
  }
}
