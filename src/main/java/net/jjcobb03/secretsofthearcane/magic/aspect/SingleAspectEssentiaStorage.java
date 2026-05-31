package net.jjcobb03.secretsofthearcane.magic.aspect;


public class SingleAspectEssentiaStorage extends EssentiaStorage {

    public SingleAspectEssentiaStorage(int capacity) {
        super(capacity);
    }

    @Override
    public int insert(AspectStack stack, TransferAction action) {

        Aspect storedAspect = getStoredAspect();

        // Container is empty, and can accept any Aspect
        if (storedAspect == null) {
            return super.insert(stack, action);
        }

        // Container is holding the matching Aspect
        if (storedAspect.equals(stack.getAspect())) {
            return super.insert(stack, action);
        }

        // Container is holding a different Aspect
        return 0;
    }
}
