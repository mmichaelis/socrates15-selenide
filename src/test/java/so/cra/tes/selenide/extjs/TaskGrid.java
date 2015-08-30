package so.cra.tes.selenide.extjs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import so.cra.tes.selenide.extjs.ext.Component;
import so.cra.tes.selenide.extjs.ext.grid.Panel;

public class TaskGrid extends Panel {
  public static final String XTYPE = "taskGrid";
  private final TaskForm taskForm = Component.component(this, TaskForm.XTYPE, TaskForm.class);

  public TaskGrid(@Nullable Component ownerCt, @NotNull String query) {
    super(ownerCt, query);
  }

  public TaskGrid(@NotNull String query) {
    super(query);
  }

  public TaskForm taskForm() {
    return taskForm;
  }
}
