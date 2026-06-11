package net.jjcobb03.secretsofthearcane.api.essentia;

/**
 * Determines whether a transfer should be executed or simulated
 */
public enum TransferAction {
    // Perform the transfer normally
    EXECUTE,
    // Simulate the transfer, don't modify data
    SIMULATE
}
