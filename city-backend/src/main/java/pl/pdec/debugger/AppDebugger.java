package pl.pdec.debugger;

import java.util.AbstractMap;
import java.util.List;

public class AppDebugger {
    enum Color {
        ANSI_RESET, ANSI_BLACK, ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE;

        private String colorUnicode() {
            return switch (this) {
                case ANSI_RESET -> "\u001B[0m";
                case ANSI_BLACK -> "\u001B[30m";
                case ANSI_RED -> "\u001B[31m";
                case ANSI_GREEN -> "\u001B[32m";
                case ANSI_YELLOW -> "\u001B[33m";
                case ANSI_BLUE -> "\u001B[34m";
                case ANSI_PURPLE -> "\u001B[35m";
                case ANSI_CYAN -> "\u001B[36m";
                case ANSI_WHITE -> "\u001B[37m";
            };
        }
    }

    protected boolean debug = false;

    public void setDebug(boolean _debug) {
        debug = _debug;
        System.out.println(ConsoleUtils.ANSI_CYAN + "Current debugging: " + ConsoleUtils.ANSI_RESET
                + (debug ? ConsoleUtils.ANSI_RED + "On" : ConsoleUtils.ANSI_GREEN + "Off") + ConsoleUtils.ANSI_RESET);
    }

    public void setDebug(String _debug) {
        setDebug(Boolean.parseBoolean(_debug));
    }

    public void debugInfo(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    public void debugInfo(List<AbstractMap.SimpleEntry<Color, String>> messagesWithColor) {
        if (debug) {
            messagesWithColor.stream()
                    .map(msgWithColorEntry ->
                            msgWithColorEntry.getKey().colorUnicode() + msgWithColorEntry.getValue() + Color.ANSI_RESET.colorUnicode())
                    .reduce(String::concat).ifPresent(System.out::println);
        }
    }

    public void debugInfo(String format, Object... args) {
        if (debug) {
            System.out.printf(format, args);
        }
    }

    public void debugError(String message) {
        if (debug) {
            System.err.println(message);
        }
    }

    public void debugError(Color color, String message) {
        if (debug) {
            System.out.println(color.colorUnicode() + message + Color.ANSI_RED);
        }
    }

    public void debugError(String format, Object... args) {
        if (debug) {
            System.err.printf(format, args);
        }
    }

    public void debugError(Throwable throwable) {
        if (debug) {
            throwable.printStackTrace();
        }
    }
}
