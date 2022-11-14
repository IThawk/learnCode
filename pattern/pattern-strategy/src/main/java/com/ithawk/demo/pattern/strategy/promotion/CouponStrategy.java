package com.ithawk.demo.pattern.strategy.promotion;

/**
 * 优惠券
 */
public class CouponStrategy implements PromotionStrategy {

    public void doPromotion() {
        System.out.println("领取优惠券,课程的价格直接减优惠券面值抵扣");
    }
}
