package it.cnr.isti.wnlab.indoornavigation.javaonly.types.inertial;

import java.io.Serializable;

import it.cnr.isti.wnlab.indoornavigation.javaonly.types.DecomposedSensorData;
import it.cnr.isti.wnlab.indoornavigation.javaonly.types.RawData;

/**
 * m/s^2
 */

public class Acceleration extends DecomposedSensorData implements Serializable {
    public Acceleration(float x, float y, float z, float accuracy, long timestamp) {
        super(x, y, z, accuracy, timestamp);
    }

    @Override
    public String toString() {
        return "A" + RawData.LOG_SEPARATOR + timestamp + RawData.LOG_SEPARATOR + x + RawData.LOG_SEPARATOR + y + RawData.LOG_SEPARATOR + z;
    }
}
