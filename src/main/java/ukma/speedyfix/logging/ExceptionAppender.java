package ukma.speedyfix.logging;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.ThresholdFilter;

@Plugin(name = "ExceptionAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class ExceptionAppender extends AbstractAppender {
    private final Layout<String> layout;

    public ExceptionAppender(String name, Filter filter, Layout<String> layout) {
        super(name, filter, null, true, Property.EMPTY_ARRAY);
        this.layout = layout;
    }

    @PluginFactory
    public static ExceptionAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginElement("ThresholdFilter") ThresholdFilter filter,
                                                   @PluginElement("CustomJsonLayout") Layout<String> layout) {
        return new ExceptionAppender(name, filter, layout);
    }

    public void append(LogEvent event) {
        if (event.getLevel().equals(org.apache.logging.log4j.Level.ERROR)) {
            String logMessage = layout.toSerializable(event);
            System.out.println(logMessage);
        }
    }
}
