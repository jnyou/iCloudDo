package io.jnyou.core.erasable;

/**
 * @author yuojiannan
 */
public class IntegerErasable implements Erasable {

    private final Integer standard;

    public IntegerErasable(Integer standard) {
        this.standard = standard;
    }


    @Override
    public boolean expression(int parentId) {
        return !this.standard.equals(parentId);
    }
}