package so.cra.tes.selenide.extjs;

import so.cra.tes.selenide.extjs.ext.Component;

public class SimpleTasksPage {
  public ListTree listTree() {
    return Component.component(ListTree.XTYPE, ListTree.class);
  }

  public TaskGrid taskGrid() {
    return Component.component(TaskGrid.XTYPE, TaskGrid.class);
  }

}
