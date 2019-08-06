# SoCraTes 2015: Selenide PoC

First steps with [Selenide][selenide] started at [SoCraTes 2015][socrates].

It controls a [Ext JS 5 example application][extjs-examples]. The required [Ext JS Component Hierarchy][extjs-api] has
been rebuild as Java proxies. This way a textfield for example can provide the concrete input field which should
receive the key events.

Just a short note why not using the DOM to locate elements: Actually the component manager of Ext JS knows much
better how to locate component elements. Because of this we only fall back to the Web-/SelenideElements if we need
to.

The test to run this proof-of-concept is `ExtJsSelenidePocTest`.

[selenide]: <http://selenide.org/> "Selenide: concise UI tests in Java"
[socrates]: <https://www.socrates-conference.de/> "SoCraTes Germany - International Software Craftsmanship and Testing Conference"
[extjs-examples]: <http://dev.sencha.com/extjs/5.1.0/examples/> "Ext JS 5.0 Examples"
[extjs-api]: <https://docs.sencha.com/extjs/5.1.0/index.html> "API Documentation - Ext JS - Sencha Docs"
[ComponentQuery]: <https://docs.sencha.com/extjs/5.1.0/api/Ext.ComponentQuery.html>

## Going through the Example

* We start at `ExtJsSelenidePocTest.shouldBeAbleToAddNewTask()`.

    ```java
    ExamplePage page = ExamplePage.open();
    SimpleTasksPage tasksPage = page.openSimpleTasks();
    Text textfield = tasksPage.taskGrid().taskForm().addTaskField();
    textfield.element().shouldBe(visible);

    SelenideElement textfieldInput = textfield.input();
    textfieldInput.sendKeys(testName.getMethodName());
    textfieldInput.pressEnter();
    textfieldInput.shouldBe(empty.because("Text should have been accepted and textfield cleared therefore."));    
    ```
    
* `ExamplePage.open()` (setup)

    This is the first wrapper which hides UI access from the test. Here, the `ExamplePage` knows
    how to open the Ext JS examples page. We use Selenide as wrapper around Selenium WebDriver, as
    it provides some convenience, like for example out of the box start of requested browser
    by system property and automatic shutdown of the browser after test run.

* `page.openSimpleTasks()` (setup)

    Again we ask our wrappers to do the next step, which is to open a given example, the _Tasks_
    example. In this case, we click through the page, as we are on static pages still. The preferred
    way would be to reduce clicks in test setup as much as possible, as every click comes with the
    risk that it may fail for unknown reasons.

* `tasksPage.taskGrid().taskForm().addTaskField()`

    Having the `tasksPage` we are actually at our AUT, the _application under test_. The wrapper
    just describes how to access its sub-elements, the list tree and the task grid.
    
    As you can see `TaskGrid` has an inheritance tree of `TaskGrid` → `grid:Panel` → `Table` →
    `panel:Panel` → `Container` → `Component` → `Base`. This is the very same hierarchy as in Ext JS.
    Having this, the `TaskGrid` shares the very same methods such as `Component` for example. Thus, we
    can for example access the DOM element of `TaskGrid`, because `Component` knows how to access this
    element.
    
    It also provides the opportunity to override such methods. So, if for any reason the DOM element
    for a `TaskGrid` is calculated in a different way, we can just override the method for this
    special behavior.
    
    `TaskGrid` has a child `TaskForm`. See, that `TaskGrid` does not know anything about how it is
    accessed - this is only known by its parent `TasksPage`. But the grid knows, how to access its
    very own sub elements. In order to build the complete query, to access `TaskForm` as part of
    `TaskGrid` as part of `TasksPage`, we just hand over a self-reference. The underlying code will
    then take care to build the complete query to execute to locate that element.
    
    Similar the `TaskForm` only knows how to access its `addTaskField`. Here, the component query
    is not only done by XType, but also by attribute value (`name=title`).
    
* `textfield.element().shouldBe(visible)`

    Up to now we only built the _description_ of how to access the UI - despite the setup phase.
    We did not do any click, but just collect the hierarchy information how to eventually perform
    the user interaction.
    
    With this line, we now start to really access the UI - in this case to query its state. What
    we need is to transform the Ext component to a representation of its DOM element, which then
    can be handled by Selenide/Selenium.
    
    To do so, we have a helper class `ExtJsBy` which again delegated to a wrapper to Ext's
    [ComponentQuery][]. It will eventually execute JavaScript which looks like follows:
    
    ```javascript
    Ext && Ext.ComponentQuery && Ext.ComponentQuery
      .query("taskGrid taskForm textfield[name=title]")
      .map(function(c){return c.id})) || []
    ```
    
    Note: For an application we develop, we would have used IDs and ItemIDs in favor of selection by
    xtype as this is much more robust. But as we cannot control this example application, we
    have to rely on xtypes and attributes only.
    
    The expression above just uses Ext JS to locate the component to access, and return its unique
    ID (in this case generic), which we will then use to hand over to Selenide/Selenium to directly
    access the DOM element by its ID.
    
    Note, that all of this access is eager. It may very well happen, that the component we want to
    access does not exist yet. In a real scenario test, we would need to ensure, that the DOM element
    access is retried until any element is found prior to continuing for example testing for its
    visibility.
    
The rest of the test steps is quite similar.

## From UI-Element to its Test-Representation

When you start writing your test, you typically start with the very component you want to interact with. In this case, it was the textfield to anter a new task. here is one approach, without knowing the sources, how to get to know how to access the element
from within your test.

1. Open the AUT.
2. Hit F12 to get to your developer console.
3. Open the target element in the inspector.
4. Get the ID of the element like `textfield-1037-inputEl`
5. Reduce it to the component ID. In this case `textfield-1037`.
6. Access the element via Ext in JavaScript console: `Ext.getCmp("textfield-1037")`
7. Get some way how to identify this element later on, for example via XType: `Ext.getCmp("textfield-1037").xtype` (`textfield`)
8. Climb up its owners: `Ext.getCmp("textfield-1037").ownerCt.xtype` (`taskForm`)
9. And again: `Ext.getCmp("textfield-1037").ownerCt.ownerCt.xtype` (`taskGrid`)

Now you have the hierarchy how to access the element, i. e. `TaskGrid` to `TaskForm` to its textfield.

Note, that if it is not important to you, that the textfield is part of the `TaskForm` then you would simply address it directly via `TaskGrid`. This will ease refactoring the UI structure later on, without breaking the test. If you address it via its complete path, the complete path will become part of the test specification, thus, changing it, will break the test - and you then have to decide, if the change was wrong, or your test needs to be adapted.

## Summary

* We wrap Ext JS components in the very same hierarchy as Ext JS does, so that we can re-use general methods via inheritance.

* Although wrappers are very similar to the actual Ext JS components, they are different. While Ext JS components are responsible for layout as well as to receive events from the user, the wrappers are responsible for locating components and to send signals to them.

* Using evaluated expressions for `ComponentQueries` allows to use the same wrapper for a component in many contexts. So, the representation for a textfield may end up with very different representations as `ComponentQuery`, depending on its parent elements.

* Instead of eager access to UI elements, you should wrap them in a representation, which retries access for a given time (and time out eventually if not found). Frameworks such as [Awaitility](https://github.com/awaitility/awaitility) may help you with that.

* It is always good to check pre-conditions prior to accessing elements. So, it makes no sense to access the textfield's input prior to the textfield to be visible. That is why we check for visiblity first. We should even extend the check not only for visibility, but also check that the component is actually enabled prior to interacting with it.
