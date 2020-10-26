package com.ithawk.demo.pattern.strategy.promotion;

/**
 * 优惠活动 ：模拟策略模式
 */
public class PromotionActivity {
    private PromotionStrategy promotionStrategy;

    //传入具体的策略实现类
    public PromotionActivity(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void execute() {
        promotionStrategy.doPromotion();
    }

}
