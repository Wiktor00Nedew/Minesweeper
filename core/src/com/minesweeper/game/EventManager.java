package com.minesweeper.game;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

public class EventManager {
    private static EventManager instance;
    private Set<Listener> listeners;
    private Queue<Event> eventQueue;

    private EventManager() {
        listeners = new HashSet<>();
        eventQueue = new LinkedList<>();
    }

    public static EventManager get() {
        if (instance == null) {
            instance = new EventManager();
        }

        return instance;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void notify(Event event) {
        eventQueue.add(event);
    }

    public void handleEvents() {
        Event event;
        while (!eventQueue.isEmpty()) {
            event = eventQueue.element();
            for (Listener listener : listeners) {
                listener.onNotify(event);
            }
            eventQueue.remove();
        }

    }
}
