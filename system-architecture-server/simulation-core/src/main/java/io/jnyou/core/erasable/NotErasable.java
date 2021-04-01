package io.jnyou.core.erasable;

/**
 * @author yuojiannan
 */
public class NotErasable implements Erasable {

    @Override
    public boolean expression(int parentId) {
        return false;
    }
}