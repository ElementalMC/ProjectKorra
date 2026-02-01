package com.projectkorra.projectkorra.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Logger formatter class based on bukkit's formatter.
 *
 * @author Jacklin213
 * @version 2.1.0
 */
public class LogFormatter extends Formatter {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM-dd|HH:mm:ss").withZone(ZoneId.systemDefault());

	@Override
	public String format(final LogRecord record) {
		final StringBuilder builder = new StringBuilder();
		final Throwable ex = record.getThrown();

		builder.append("(");
		builder.append(DATE_FORMAT.format(Instant.ofEpochMilli(record.getMillis())));
		builder.append(")");
		builder.append(" [");
		builder.append(record.getLevel().getLocalizedName().toUpperCase());
		builder.append("] ");
		builder.append(this.formatMessage(record));
		builder.append('\n');

		if (ex != null) {
			final StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			builder.append(writer);
		}

		return builder.toString();
	}

}
