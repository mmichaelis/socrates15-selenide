package so.cra.tes.selenide.extjs.ext;

import com.codeborne.selenide.Selenide;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ComponentQuery {
  private static final Logger LOG = LoggerFactory.getLogger(ComponentQuery.class);

  public static List<String> query(@NotNull String query) {
    List<String> ids = Selenide.executeJavaScript(
            "return (Ext && Ext.ComponentQuery && Ext.ComponentQuery.query(arguments[0]).map(function(c){return c.id})) || []",
            query);
    LOG.debug("Executing\n\t(Ext && Ext.ComponentQuery && Ext.ComponentQuery.query('{}').map(function(c){return c.id})) || []\n\tResult: {}", query, ids);
    return ids;
  }
}
