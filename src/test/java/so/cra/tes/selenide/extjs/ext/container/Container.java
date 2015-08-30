package so.cra.tes.selenide.extjs.ext.container;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;

public class Container extends Component {
  public Container(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public Container(@NotNull String query) {
    super(query);
  }
}
