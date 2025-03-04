package maslova;

import java.text.DecimalFormat;
/**
 * Функция расчёта стоимости доставки.
 * Стоимость рассчитывается в зависимости от:
 * - расстояния до пункта назначения:
 * более 30 км: +300 рублей к доставке;
 * до 30 км: +200 рублей к доставке;
 * до 10 км: +100 рублей к доставке;
 * до 2 км: +50 рублей к доставке;
 * - габаритов груза:
 * большие габариты: +200 рублей к доставке;
 * маленькие габариты: +100 рублей к доставке;
 * - хрупкости груза. Если груз хрупкий — +300 рублей к доставке.
 * Хрупкие грузы нельзя возить на расстояние более 30 км;
 * - загруженности службы доставки. Стоимость умножается на коэффициент доставки:
 * очень высокая загруженность — 1.6;
 * высокая загруженность — 1.4;
 * повышенная загруженность — 1.2;
 * во всех остальных случаях коэффициент равен 1.
 * Минимальная сумма доставки — 400 рублей.
 * Если сумма доставки меньше минимальной, выводится минимальная сумма.
 * На входе функция получает расстояние до пункта назначения,
 * габариты, информацию о хрупкости, загруженность службы на текущий
 * момент. На выходе пользователь получает стоимость доставки.
 */

public class DeliveryCost {
    public static final double MIN_DELIVERY_COST = 400;

    private final double distance;
    private final ItemDimension itemDimension;
    private final boolean isFragile;
    private final Workload workload;

    public DeliveryCost(double distance, ItemDimension itemDimension, boolean isFragile, Workload workload) {
        this.distance = distance;
        this.itemDimension = itemDimension;
        this.isFragile = isFragile;
        this.workload = workload;
    }

    /**
     * Returns delivery cost or an error, if there are wrong input
     * @return calculated cost or default if calculated is less than 400
     */
    public double calculateDeliveryCost() {
        if (this.isFragile && this.distance > 30)
            throw new UnsupportedOperationException("Fragile item cannot be delivered for the distance > 30 km");

        double calculatedDeliveryCost = (getDistanceCostIncrease(this.distance) +
                this.itemDimension.getCostIncrease() +
                getFragileCostIncrease(this.isFragile)) * this.workload.getDeliveryRate();
        DecimalFormat df = new DecimalFormat("###");
        return Math.max(Double.parseDouble(df.format(calculatedDeliveryCost)), MIN_DELIVERY_COST);
    }

    /**
     * Returns additional cost based on the destination distance
     * @param distance - distance to the target
     * @return calculated cost
     */
    private int getDistanceCostIncrease(double distance) {
        if (distance > 50) throw new IllegalArgumentException("Too long distance, please discuss maximal value with Product Owner");
        if (distance > 30) return 300;
        if (distance > 10) return 200;
        if (distance > 2) return 100;
        if (distance >= 0) return 50;
        throw new IllegalArgumentException("Distance should not be negative");
    }

    /**
     * Returns additional cost based on the fragility of the item
     * @param isFragile - is item fragile or not
     * @return calculated cost, zero for a not fragile item
     */
    private int getFragileCostIncrease(boolean isFragile) {
        return isFragile ? 300 : 0;
    }
}
