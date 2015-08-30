package so.cra.tes.selenide.extjs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;
import so.cra.tes.selenide.extjs.ext.form.Panel;
import so.cra.tes.selenide.extjs.ext.form.field.Text;

import static java.lang.String.format;

public class TaskForm extends Panel {
  public static final String XTYPE = "taskForm";
  private final Text addTaskField = Component.component(this, format("%s[name=title]", Text.XTYPE), Text.class);

  public TaskForm(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public TaskForm(@NotNull String query) {
    super(query);
  }

  public Text addTaskField() {
    return addTaskField;
  }
}
