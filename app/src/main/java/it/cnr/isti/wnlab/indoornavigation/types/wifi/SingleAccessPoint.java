package it.cnr.isti.wnlab.indoornavigation.types.wifi;

import it.cnr.isti.wnlab.indoornavigation.types.RawData;

/**
 * A class encapsulating (bssd,rssi) pair.
 */
public class SingleAccessPoint implements RawData {
    public final String bssid;
    public final int rssi;

    public SingleAccessPoint(String bssid , int rssi) {
        this.bssid = bssid;
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return bssid + RawData.LOG_SEPARATOR + rssi;
    }
}
