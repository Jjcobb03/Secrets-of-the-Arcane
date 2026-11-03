package net.jjcobb03.secretsofthearcane.api.aspect;

import java.util.List;

/**
 * Interface used by anything that stores Aspects in any form (Essentia, or Vis)
 */
public interface IAspectContainer {

    // Attempts to add the stack. If simulated, returns how much would be added.
    int insert(AspectStack stack, boolean simulate);

    // Attempts to subtract the stack. If simulated, returns how much would be subtracted.
    int extract(Aspect stack, int max, boolean simulate);

    // Amount of one Aspect currently stored
    int getAmount(Aspect aspect);

    // Amount stored across all Aspects
    int getTotalAmount();

    // Everything stored as a list. One stack per Aspect, no empty stacks.
    List<AspectStack> getContents();


}
