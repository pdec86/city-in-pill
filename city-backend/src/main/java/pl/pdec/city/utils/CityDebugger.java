package pl.pdec.city.utils;

import pl.pdec.debugger.AppDebugger;

public class CityDebugger {
    private static class AppDebuggerHolder {
        private static final AppDebugger INSTANCE = new AppDebugger();
    }

    public static AppDebugger getInstance() {
        return AppDebuggerHolder.INSTANCE;
    }
}
