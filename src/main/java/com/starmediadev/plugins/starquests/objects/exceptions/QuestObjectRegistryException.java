package com.starmediadev.plugins.starquests.objects.exceptions;

/**
 * Wrapper exception for when there is a reflection problem with registering an object
 */
public class QuestObjectRegistryException extends RuntimeException {
    /**
     * Construct a new exception
     * @param s The message
     */
    public QuestObjectRegistryException(String s) {
        super(s);
    }
}
