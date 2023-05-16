package com.backstreetbrogrammer.dip;

public class Computer {
    private final UltraHDMonitor monitor;
    private final CordlessKeyboard keyboard;
    private final LaserMouse mouse;

    public Computer() {
        monitor = new UltraHDMonitor();
        keyboard = new CordlessKeyboard();
        mouse = new LaserMouse();
    }

    // other methods

}
