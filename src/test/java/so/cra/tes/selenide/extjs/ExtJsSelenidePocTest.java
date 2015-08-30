package so.cra.tes.selenide.extjs;

import com.codeborne.selenide.SelenideElement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import so.cra.tes.selenide.extjs.ext.form.field.Text;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.visible;

public class ExtJsSelenidePocTest {

  @Rule
  public TestName testName = new TestName();

  @Test
  public void examplesAreAvailable() throws Exception {
    ExamplePage page = ExamplePage.open();
    page.logo().shouldBe(visible.because("It must be there."));
  }

  @Test
  public void mainSimpleTasksComponentsShouldBeAvailable() throws Exception {
    ExamplePage page = ExamplePage.open();
    SimpleTasksPage tasksPage = page.openSimpleTasks();
    tasksPage.listTree().element().shouldBe(visible.because("Simple Tasks page should have opened with a tree."));
    tasksPage.taskGrid().element().shouldBe(visible);
  }

  @Test
  public void taskFormShouldBeAvailable() throws Exception {
    ExamplePage page = ExamplePage.open();
    SimpleTasksPage tasksPage = page.openSimpleTasks();
    tasksPage.taskGrid().taskForm().element().shouldBe(visible);
  }

  @Test
  public void shouldBeAbleToAddNewTask() throws Exception {
    ExamplePage page = ExamplePage.open();
    SimpleTasksPage tasksPage = page.openSimpleTasks();
    Text textfield = tasksPage.taskGrid().taskForm().addTaskField();
    textfield.element().shouldBe(visible);

    SelenideElement textfieldInput = textfield.input();
    textfieldInput.sendKeys(testName.getMethodName());
    textfieldInput.pressEnter();
    textfieldInput.shouldBe(empty.because("Text should have been accepted and textfield cleared therefore."));
  }
}
