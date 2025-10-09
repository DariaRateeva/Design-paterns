public class PremiumFeeCalculator extends LateFeeCalculator {
    @Override
    public double calculateFee(int daysLate) {
        return daysLate * 0.5;
    }
}
