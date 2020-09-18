/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.rpc.cluster.loadbalance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * random load balance.
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "random";

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // Number of invokers
        int length = invokers.size(); //集合大小
        // Every invoker has the same weight?
        boolean sameWeight = true; //是不是所有的服务都是相同的权重
        // the weight of every invokers
        int[] weights = new int[length];
        // the first invoker's weight
        int firstWeight = getWeight(invokers.get(0), invocation);
        weights[0] = firstWeight;
        // The sum of weights
        int totalWeight = firstWeight;
        //所有逻辑是去判断权重是否相同
        for (int i = 1; i < length; i++) {
            //getWeight 并不仅仅是根据我们的url中配置来实现，还有个服务的启动时间
            int weight = getWeight(invokers.get(i), invocation);
            // save for later use
            weights[i] = weight;
            // Sum
            totalWeight += weight;
            if (sameWeight && weight != firstWeight) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) { //权重不一样的情况
            // If (not every invoker has the same weight & at least one invoker's weight>0), select randomly based on totalWeight.
            //随机值
            //servers [a,b,c], weights=[2,3,5]
            //offset（随机值） 6 -> 10以内
            // 6-2>0
            // 4 -> 3 >0
            // 1-5 <0
            int offset = ThreadLocalRandom.current().nextInt(totalWeight);
            // Return a invoker based on the random value.
            for (int i = 0; i < length; i++) {
                offset -= weights[i];
                if (offset < 0) {
                    return invokers.get(i); //第三个节点
                }
            }
        }
        // 如果权重都相同或者为0， 这及生成一个随机数，返回
        return invokers.get(ThreadLocalRandom.current().nextInt(length));
    }

}
