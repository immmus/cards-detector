package org.detect.commands;

@FunctionalInterface
public interface ActionHandler {
    void handle(String actionInitiator);

//    default void option(OptionHandler<A> oOptionHandler) {
//        final A a = oOptionHandler.get();
//        handle(a);
//        oOptionHandler.get();
//    }
}
