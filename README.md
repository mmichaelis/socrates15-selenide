# SoCraTes 2015: Selenide PoC

First steps with [Selenide][selenide] started at [SoCraTes 2015][socrates].

It controls a [Ext JS 5 example application][extjs-examples]. The required [Ext JS Component Hierarchy][extjs-api] has
been rebuild as Java proxies. This way a textfield for example can provide the concrete input field which should
receive the key events.

Just a short note why not using the DOM to locate elements: Actually the component manager of Ext JS knows much
better how to locate component elements. Because of this we only fall back to the Web-/SelenideElements if we need
to.

The test to run this proof-of-concept is `ExtJsSelenidePocTest`.

Find more information in the related blog post [#socrates15: Ext JS 5 Tests with Selenide][mindsblog].

[selenide]: <http://selenide.org/> "Selenide: concise UI tests in Java"
[socrates]: <https://www.socrates-conference.de/> "SoCraTes Germany - International Software Craftsmanship and Testing Conference"
[extjs-examples]: <http://dev.sencha.com/extjs/5.1.0/examples/> "Ext JS 5.0 Examples"
[extjs-api]: <https://docs.sencha.com/extjs/5.1/5.1.0-apidocs/> "API Documentation - Ext JS - Sencha Docs"
[mindsblog]: <http://minds.coremedia.com/2015/08/31/socrates15-ext-js-5-tests-with-selenide/> "#socrates15: Ext JS 5 Tests with Selenide | Minds"
