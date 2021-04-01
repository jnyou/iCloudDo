package io.jnyou.core.erasable;

import java.util.Set;

/**
 * @author yuojiannan
 */
public class SetErasable implements Erasable {

    private final Set<Integer> standard;

    public SetErasable(Set<Integer> standard) {
        this.standard = standard;
    }

    @Override
    public boolean expression(int parentId) {
        return !standard.contains(parentId);
    }
}