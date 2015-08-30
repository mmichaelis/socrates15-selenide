package so.cra.tes.selenide.extjs.ext.form.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;

public class Base extends Component {
  public Base(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Base(@NotNull String query) {
    super(query);
  }
}
