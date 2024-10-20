package ukma.speedyfix.logging;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Plugin(name = "CustomJsonLayout", category = "Core", elementType = Layout.ELEMENT_TYPE, printObject = true)
public class CustomJsonLayout extends AbstractStringLayout {

    protected CustomJsonLayout(Charset charset) {
        super(charset);
    }

    @Override
    public String toSerializable(LogEvent event) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("timestamp", event.getTimeMillis());
        logMap.put("level", event.getLevel().toString());
        logMap.put("thread", event.getThreadName());
        logMap.put("logger", event.getLoggerName());
        logMap.put("message", event.getMessage().getFormattedMessage());

        return toJsonString(logMap) + Strings.LINE_SEPARATOR;
    }

    private String toJsonString(Map<String, Object> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append('\n');
        map.forEach((key, value) -> {
            json.append(" \"").append(key).append("\":\"").append(value).append("\",").append('\n');
        });
        json.append("}");
        return json.toString();
    }

    @PluginFactory
    public static CustomJsonLayout createLayout() {
        return new CustomJsonLayout(Charset.defaultCharset());
    }
}
