package demo.utils;

import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StringResourceHandler {
  public static final String BASENAME = "Messages";

  public static String getString(String name, List<Object> args) throws IllegalFormatException {
    var format = ResourceBundle.getBundle(BASENAME).getString(name);
    return String.format(format, args.toArray());
  }

  public static String getString(String name, List<Object> args, Locale locale) throws IllegalFormatException {
    var format = ResourceBundle.getBundle(BASENAME, locale).getString(name);
    return String.format(locale, format, args.toArray());
  }
}