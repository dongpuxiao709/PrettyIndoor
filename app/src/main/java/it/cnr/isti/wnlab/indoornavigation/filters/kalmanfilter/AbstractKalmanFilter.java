package it.cnr.isti.wnlab.indoornavigation.filters.kalmanfilter;

/**
 * This class represents the method of Kalman Filter. Override this for writing your own
 * implementation.
 */
public abstract class AbstractKalmanFilter implements KalmanFilter {

    // State vectors
    private float[] x;
    private float[][] mP;

    // Prediction step
    private float[][] mA; // State transition matrix
    private float[][] mAt; // Transposed state transition matrix (used in Covariance Prediction)
    private float[][] mB; // Control matrix
    private float[][] mQ; // Estimated process error covariance

    // Update step
    private float[][] mH; // Observation matrix
    private float[][] mHt; // Transposed observation matrix (used in Innovation Covariance and Kalman Gain steps)
    private float[][] mR; // Estimated measurement error covariance

    public AbstractKalmanFilter(float[] x0, float[][] mP0) {
        this.x = x0;
        this.mP = mP0;
        this.mA = initMatrixA();
        this.mAt = newTransposedA(mA);
        this.mB = initMatrixB();
        this.mQ = initMatrixQ();
        this.mH = initMatrixH();
        this.mHt = newTransposedH(mH);
        this.mR = initMatrixR();
    }

    @Override
    public void predict(float[] u) {
        // Predict state
        x = statePrediction(mA,x,mB,u);

        // Predict covariance
        mP = covariancePrediction(mA,mP,mAt,mQ);
    }

    @Override
    public void update(float[] z) {
        // Calculate innovation
        float[] y = innovation(z,mH,x);

        // Calculate innovation covariance
        float[][] mSInv = innovationCovariance(mH, mP, mHt, mR);
        invertInnovationCovariance(mSInv);

        // Calculate Kalman gain
        float mK[][] = kalmanGain(mP, mHt, mSInv);

        // Update state and covariance
        x = updateState(x, mK, y);
        mP = updateCovariance(mP, mK, mH);
    }

    protected abstract float[][] initMatrixA();

    protected abstract float[][] initMatrixB();

    protected abstract float[][] initMatrixQ();

    protected abstract float[][] initMatrixH();

    protected abstract float[][] initMatrixR();

    protected abstract float[][] newTransposedA(float[][] mA);

    protected abstract float[][] newTransposedH(float[][] mH);

    protected abstract float[] statePrediction(float[][] mA, float[] x, float[][] mB, float[] u);

    protected abstract float[][] covariancePrediction(float[][] mA, float[][] mP, float[][] mAt, float[][] mQ);

    protected abstract float[] innovation(float[] z, float[][] mH, float[] x);

    protected abstract float[][] kalmanGain(float[][] mP, float[][] mHt, float[][] mSinv);

    protected abstract float[][] innovationCovariance(float[][] mH, float[][] mP, float[][] mHt, float[][] mR);

    protected abstract void invertInnovationCovariance(float[][] mS);

    protected abstract float[] updateState(float[] x, float[][] mK, float[] y);

    protected abstract float[][] updateCovariance(float[][] mP, float[][] mK, float[][] mH);

    public float[] getStateVector() {
        return x;
    }

    public float[][] getCovarianceMatrix() {
        return mP;
    }
}
