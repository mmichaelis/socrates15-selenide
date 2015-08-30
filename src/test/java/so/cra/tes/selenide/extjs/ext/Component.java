package so.cra.tes.selenide.extjs.ext;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ExtJsBy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.lang.String.format;

public class Component extends Base {
  @Nullable
  private final Component ownerCt;
  @NotNull
  private final String query;
  @NotNull
  private final Supplier<List<String>> initSupplier = new Supplier<List<String>>() {
    @Override
    public List<String> get() {
      return ownerCt().map(new Function<Component, List<String>>() {
        @Override
        public List<String> apply(Component component) {
          List<String> path = new ArrayList<>(component.getQueryPath());
          path.add(query);
          return path;
        }
      }).orElse(Collections.singletonList(query));
    }
  };
  @NotNull
  private final Supplier<List<String>> queryPathSupplier = new Supplier<List<String>>() {
    private List<String> memoizedPath;

    @Override
    public List<String> get() {
      if (memoizedPath == null) {
        memoizedPath = initSupplier.get();
      }
      return Collections.unmodifiableList(memoizedPath);
    }
  };

  public Component(@NotNull String query) {
    this(null, query);
  }

  public Component(@Nullable Component ownerCt, @NotNull String query) {
    this.ownerCt = ownerCt;
    this.query = query;
  }

  @NotNull
  public List<String> getQueryPath() {
    return queryPathSupplier.get();
  }

  @NotNull
  public String getQuery() {
    return getQueryPath().stream().collect(Collectors.joining(" "));
  }

  @NotNull
  public Optional<Component> ownerCt() {
    return Optional.ofNullable(ownerCt);
  }

  @NotNull
  public SelenideElement element() {
    return $(ExtJsBy.query(getQuery()));
  }

  @NotNull
  public ElementsCollection elements() {
    return $$(ExtJsBy.query(getQuery()));
  }

  @NotNull
  public Component child(@NotNull String subQuery) {
    return child(subQuery, Component.class);
  }

  @NotNull
  public <T extends Component> T child(@NotNull String subQuery, @NotNull Class<T> componentClass) {
    return component(this, subQuery, componentClass);
  }

  @NotNull
  public <T extends Component> T as(@NotNull Class<T> componentClass) {
    return ownerCt()
            .map(component -> component(component, query, componentClass))
            .orElseGet(() -> Component.component(query, componentClass));
  }

  @NotNull
  public static Component component(@NotNull String query) {
    return component(query, Component.class);
  }

  @NotNull
  public static <T extends Component> T component(@NotNull String query, @NotNull Class<T> componentClass) {
    T component;
    try {
      component = componentClass.getConstructor(String.class).newInstance(query);
    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      throw new ExtException(format("Cannot build component of type %s for query: %s", componentClass.getName(), query), e);
    }
    return component;
  }

  @NotNull
  public static <T extends Component> T component(@NotNull Component ownerCt,
                                                  @NotNull String query,
                                                  @NotNull Class<T> componentClass) {
    T component;
    try {
      component = componentClass.getConstructor(Component.class, String.class).newInstance(ownerCt, query);
    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      throw new ExtException(format("Cannot build component of type %s owned by %s for query: %s", componentClass.getName(), ownerCt, query), e);
    }
    return component;
  }
}
