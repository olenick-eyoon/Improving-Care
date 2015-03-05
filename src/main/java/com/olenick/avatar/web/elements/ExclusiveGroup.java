package com.olenick.avatar.web.elements;

import java.util.EnumMap;

import javax.validation.constraints.NotNull;

import org.openqa.selenium.NoSuchElementException;

/**
 * Radio button group.
 */
public class ExclusiveGroup<E extends Enum<E>> {
    private EnumMap<E, ExtendedWebElement> clickables;

    public ExclusiveGroup(@NotNull final Class<E> enumClass) {
        this.clickables = new EnumMap<>(enumClass);
    }

    public ExclusiveGroup<E> add(@NotNull final E key,
            @NotNull final ExtendedWebElement clickable) {
        this.clickables.put(key, clickable);
        return this;
    }

    /**
     * Clicks on the radio button that corresponds to the enum key.
     * 
     * @param key Key.
     * @throws org.openqa.selenium.NoSuchElementException if the radio button is
     *             not added to the group.
     * @throws com.olenick.avatar.exceptions.ElementNotLoadedException if the
     *             radio button cannot load.
     */
    public void select(@NotNull final E key) {
        try {
            this.clickables.get(key).click();
        } catch (NullPointerException exception) {
            throw new NoSuchElementException("Group element for key " + key
                    + " not found.");
        }
    }

    /**
     * Clicks on the radio button that corresponds to the enum key.
     * 
     * @param key Key (if null, it will not do anything).
     * @throws org.openqa.selenium.NoSuchElementException if the radio button is
     *             not added to the group.
     * @throws com.olenick.avatar.exceptions.ElementNotLoadedException if the
     *             radio button cannot load.
     */
    public void safeSelect(final E key) {
        if (key != null) {
            this.select(key);
        }
    }
}
