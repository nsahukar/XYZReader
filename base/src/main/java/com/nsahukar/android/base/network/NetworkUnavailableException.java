package com.nsahukar.android.base.network;

import java.io.IOException;

/**
 * Exception thrown when network is unavailable.
 */
public class NetworkUnavailableException extends IOException {
    public NetworkUnavailableException() {
        super("Network not available. Unable to make network requests");
    }
}