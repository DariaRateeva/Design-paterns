public class StandardFeeCalculator extends LateFeeCalculator {
    @Override
    public double calculateFee(int daysLate) {
        return daysLate * 1.0;
    }
}
