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
 *
 */

package org.apache.skywalking.oap.server.network.trace.proto;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.DnsNameResolverProvider;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.skywalking.apm.network.common.v3.Commands;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;
import org.apache.skywalking.apm.network.language.agent.v3.TraceSegmentReportServiceGrpc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GRPCNoServerTest {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannelBuilder<?> channelBuilder = NettyChannelBuilder.forAddress("127.0.0.1", 8080)
                                                                     .nameResolverFactory(new DnsNameResolverProvider())
                                                                     .maxInboundMessageSize(1024 * 1024 * 50)
                                                                     .usePlaintext();
        ManagedChannel channel = channelBuilder.build();
        TraceSegmentReportServiceGrpc.TraceSegmentReportServiceStub serviceStub = TraceSegmentReportServiceGrpc.newStub(channel);
        final Status[] status = {null};
        StreamObserver<SegmentObject> streamObserver = serviceStub.collect(new StreamObserver<Commands>() {
            @Override
            public void onNext(Commands value) {

            }

            @Override
            public void onError(Throwable t) {
                status[0] = ((StatusRuntimeException) t).getStatus();
            }

            @Override
            public void onCompleted() {

            }
        });

        streamObserver.onNext(null);
        streamObserver.onCompleted();

        Thread.sleep(2 * 1000);

        assertThat(status[0].getCode()).isEqualTo(Status.UNAVAILABLE.getCode());
    }
}
