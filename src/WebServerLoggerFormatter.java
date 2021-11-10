import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * WebServerLoggerFormatter class represents the format of information to be logged.
 * @author 200011181
 * @version 1.0
 */
public class WebServerLoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuffer stringBuffer = new StringBuffer();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(
                record.getInstant(), ZoneId.systemDefault());

        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = "\n" + sw;
        }
        return stringBuffer.append(zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(" - ").
                append(record.getLoggerName()).append(" : ").append(source).append("\n").
                append(record.getLevel()).append(": ").append(message).
                append(throwable).append("\n\n").toString();
    }
}
