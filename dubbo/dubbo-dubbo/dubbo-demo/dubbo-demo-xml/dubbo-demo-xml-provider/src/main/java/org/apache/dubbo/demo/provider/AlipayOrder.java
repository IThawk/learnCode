package org.apache.dubbo.demo.provider;

public class AlipayOrder implements Order {

   private TestSpiAutowired testSpiAutowired;

    public void setTestSpiAutowired(TestSpiAutowired testSpiAutowired) {
        this.testSpiAutowired = testSpiAutowired;
    }

    @Override
    public String way() {
        testSpiAutowired.sayStr();
        System.out.println("---  使用支付宝支付  ---");
        return "支付宝支付";
    }
}
