package net.jjcobb03.secretsofthearcane.api.aspect;

public final class AspectTransfer {

    private AspectTransfer() {}

    /**
     * Moves up to 'max' of one aspect. Returns how much actually moved
     *
     * @param from - the container being extracted from
     * @param to - the container being inserted into
     * @param aspect - The Aspect being transferred
     * @param max - the highest amount to transfer
     * @return - The amount moved as an int
     */
    public static int transfer(IAspectContainer from, IAspectContainer to, Aspect aspect, int max) {
        int available = from.extract(aspect, max, true);
        if (available <= 0) return 0;

        int accepted = to.insert(new AspectStack(aspect, available), true);
        if (accepted <= 0) return 0;

        int removed = from.extract(aspect, accepted, false);
        to.insert(new AspectStack(aspect, removed), false);
        return removed;
    }

    /**
     * Moves as much as possible of everything in 'from'. Returns the total moved
     *
     * @param from - The container being extracted from.
     * @param to - The container being inserted into.
     * @return - The total amount transfered as an int.
     */
    public static int transferAll(IAspectContainer from, IAspectContainer to) {
        int moved = 0;
        // getContents() is a snapshot, so changing 'from' while looping is safe
        for (AspectStack stack : from.getContents()) {
            moved += transfer(from, to, stack.aspect(), stack.amount());
        }
        return moved;
    }
}