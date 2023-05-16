package com.backstreetbrogrammer.dip;

public class ComputerDIP {
    private final Monitor monitor;
    private final Keyboard keyboard;
    private final Mouse mouse;

    public ComputerDIP(final Monitor monitor, final Keyboard keyboard, final Mouse mouse) {
        this.monitor = monitor;
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    // other methods

}
