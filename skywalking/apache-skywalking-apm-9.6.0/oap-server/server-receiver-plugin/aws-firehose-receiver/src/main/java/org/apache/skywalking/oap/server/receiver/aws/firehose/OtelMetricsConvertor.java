/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.skywalking.oap.server.receiver.aws.firehose;

import io.opentelemetry.proto.collector.metrics.firehose.v0_7.ExportMetricsServiceRequest;
import io.opentelemetry.proto.common.firehose.v0_7.ArrayValue;
import io.opentelemetry.proto.common.firehose.v0_7.KeyValue;
import io.opentelemetry.proto.common.firehose.v0_7.KeyValueList;
import io.opentelemetry.proto.common.firehose.v0_7.StringKeyValue;
import io.opentelemetry.proto.common.v1.AnyValue;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleDataPoint;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleGauge;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleHistogram;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleHistogramDataPoint;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleSum;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleSummary;
import io.opentelemetry.proto.metrics.firehose.v0_7.DoubleSummaryDataPoint;
import io.opentelemetry.proto.metrics.firehose.v0_7.InstrumentationLibraryMetrics;
import io.opentelemetry.proto.metrics.firehose.v0_7.IntDataPoint;
import io.opentelemetry.proto.metrics.firehose.v0_7.IntGauge;
import io.opentelemetry.proto.metrics.firehose.v0_7.IntHistogram;
import io.opentelemetry.proto.metrics.firehose.v0_7.IntHistogramDataPoint;
import io.opentelemetry.proto.metrics.firehose.v0_7.IntSum;
import io.opentelemetry.proto.metrics.firehose.v0_7.Metric;
import io.opentelemetry.proto.metrics.firehose.v0_7.ResourceMetrics;
import io.opentelemetry.proto.metrics.v1.AggregationTemporality;
import io.opentelemetry.proto.metrics.v1.DataPointFlags;
import io.opentelemetry.proto.metrics.v1.Gauge;
import io.opentelemetry.proto.metrics.v1.Histogram;
import io.opentelemetry.proto.metrics.v1.HistogramDataPoint;
import io.opentelemetry.proto.metrics.v1.NumberDataPoint;
import io.opentelemetry.proto.metrics.v1.ScopeMetrics;
import io.opentelemetry.proto.metrics.v1.Sum;
import io.opentelemetry.proto.metrics.v1.Summary;
import io.opentelemetry.proto.metrics.v1.SummaryDataPoint;
import io.opentelemetry.proto.resource.v1.Resource;
import java.util.Optional;

public class OtelMetricsConvertor {

    public static io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest convertExportMetricsRequest(
        ExportMetricsServiceRequest sourceRequest) {
        io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest.Builder targetRequestBuilder = io.opentelemetry.proto.collector.metrics.v1.ExportMetricsServiceRequest.newBuilder();
        for (ResourceMetrics resourceMetrics : sourceRequest.getResourceMetricsList()) {
            targetRequestBuilder.addResourceMetrics(convertResourceMetrics(resourceMetrics));
        }
        return targetRequestBuilder.build();
    }

    private static io.opentelemetry.proto.metrics.v1.ResourceMetrics convertResourceMetrics(final ResourceMetrics resourceMetrics) {
        io.opentelemetry.proto.metrics.v1.ResourceMetrics.Builder targetResourceMetricsBuilder = io.opentelemetry.proto.metrics.v1.ResourceMetrics.newBuilder();
        targetResourceMetricsBuilder.setResource(convertResource(resourceMetrics.getResource()));
        for (InstrumentationLibraryMetrics instrumentationLibraryMetrics : resourceMetrics.getInstrumentationLibraryMetricsList()) {
            targetResourceMetricsBuilder.addScopeMetrics(convertScopeMetrics(instrumentationLibraryMetrics));
        }
        return targetResourceMetricsBuilder.build();
    }

    private static ScopeMetrics convertScopeMetrics(final InstrumentationLibraryMetrics instrumentationLibraryMetrics) {
        final ScopeMetrics.Builder builder = ScopeMetrics.newBuilder();
        for (Metric metric : instrumentationLibraryMetrics.getMetricsList()) {
            builder.addMetrics(convertMetrics(metric));
        }
        return builder.build();
    }

    private static io.opentelemetry.proto.metrics.v1.Metric convertMetrics(final Metric metric) {
        final io.opentelemetry.proto.metrics.v1.Metric.Builder builder = io.opentelemetry.proto.metrics.v1.Metric.newBuilder();
        builder.setDescription(metric.getDescription());
        builder.setDescriptionBytes(metric.getDescriptionBytes());
        builder.setName(metric.getName());
        builder.setNameBytes(metric.getNameBytes());
        builder.setUnit(metric.getUnit());
        builder.setUnitBytes(metric.getUnitBytes());
        Optional.of(metric.getDoubleGauge())
                .map(OtelMetricsConvertor::convertDoubleGauge)
                .ifPresent(builder::setGauge);
        Optional.of(metric.getIntGauge()).map(OtelMetricsConvertor::convertIntGauge).ifPresent(builder::setGauge);
        Optional.of(metric.getDoubleHistogram())
                .map(OtelMetricsConvertor::convertDoubleHistogram)
                .ifPresent(builder::setHistogram);
        Optional.of(metric.getIntHistogram())
                .map(OtelMetricsConvertor::convertIntHistogram)
                .ifPresent(builder::setHistogram);
        Optional.of(metric.getDoubleSum()).map(OtelMetricsConvertor::convertDoubleSum).ifPresent(builder::setSum);
        Optional.of(metric.getIntSum()).map(OtelMetricsConvertor::convertIntSum).ifPresent(builder::setSum);
        Optional.of(metric.getDoubleSummary())
                .map(OtelMetricsConvertor::convertDoubleSummary)
                .ifPresent(builder::setSummary);
        return builder.build();
    }

    private static Summary convertDoubleSummary(final DoubleSummary doubleSummary) {
        final Summary.Builder builder = Summary.newBuilder();
        doubleSummary.getDataPointsList()
                     .stream()
                     .map(OtelMetricsConvertor::convertDoubleSummaryDatapoint)
                     .forEach(builder::addDataPoints);
        return builder.build();
    }

    private static SummaryDataPoint convertDoubleSummaryDatapoint(final DoubleSummaryDataPoint doubleSummaryDataPoint) {
        final SummaryDataPoint.Builder builder = SummaryDataPoint.newBuilder();
        doubleSummaryDataPoint.getLabelsList()
                              .stream()
                              .map(OtelMetricsConvertor::convertStringKV)
                              .forEach(builder::addAttributes);
        builder.setCount(doubleSummaryDataPoint.getCount());
        builder.setSum(doubleSummaryDataPoint.getSum());
        builder.setFlags(DataPointFlags.FLAG_NONE_VALUE);
        doubleSummaryDataPoint.getQuantileValuesList()
                              .stream()
                              .map(OtelMetricsConvertor::convertValueAtQuantile)
                              .forEach(builder::addQuantileValues);
        builder.setStartTimeUnixNano(doubleSummaryDataPoint.getStartTimeUnixNano());
        builder.setTimeUnixNano(doubleSummaryDataPoint.getTimeUnixNano());
        return builder.build();
    }

    private static SummaryDataPoint.ValueAtQuantile convertValueAtQuantile(final DoubleSummaryDataPoint.ValueAtQuantile valueAtQuantile) {
        final SummaryDataPoint.ValueAtQuantile.Builder builder = SummaryDataPoint.ValueAtQuantile.newBuilder();
        builder.setValue(valueAtQuantile.getValue());
        builder.setQuantile(valueAtQuantile.getQuantile());
        return builder.build();
    }

    private static Sum convertIntSum(final IntSum intSum) {
        final Sum.Builder builder = Sum.newBuilder();
        intSum.getDataPointsList()
              .stream()
              .map(OtelMetricsConvertor::convertIntDataPoint)
              .forEach(builder::addDataPoints);
        return builder.build();
    }

    private static Sum convertDoubleSum(final DoubleSum doubleSum) {
        final Sum.Builder builder = Sum.newBuilder();
        doubleSum.getDataPointsList()
                 .stream()
                 .map(OtelMetricsConvertor::convertDoubleDataPoint)
                 .forEach(builder::addDataPoints);
        return builder.build();
    }

    /**
     * Convert DoubleDataPoint in OTEL 0.7 to NumberDataPoint
     * Notice this method ignore Exemplar field in HistogramDataPoint
     */
    private static NumberDataPoint convertDoubleDataPoint(final DoubleDataPoint doubleDataPoint) {
        final NumberDataPoint.Builder builder = NumberDataPoint.newBuilder();
        doubleDataPoint.getLabelsList()
                       .stream()
                       .map(OtelMetricsConvertor::convertStringKV)
                       .forEach(builder::addAttributes);
        builder.setTimeUnixNano(doubleDataPoint.getTimeUnixNano());
        builder.setStartTimeUnixNano(doubleDataPoint.getStartTimeUnixNano());
        builder.setAsDouble(doubleDataPoint.getValue());
        return builder.build();
    }

    private static NumberDataPoint convertIntDataPoint(final IntDataPoint intDataPoint) {
        final NumberDataPoint.Builder builder = NumberDataPoint.newBuilder();
        intDataPoint.getLabelsList()
                    .stream()
                    .map(OtelMetricsConvertor::convertStringKV)
                    .forEach(builder::addAttributes);
        builder.setTimeUnixNano(intDataPoint.getTimeUnixNano());
        builder.setStartTimeUnixNano(intDataPoint.getStartTimeUnixNano());
        builder.setAsInt(intDataPoint.getValue());
        return builder.build();
    }

    private static io.opentelemetry.proto.common.v1.KeyValue convertStringKV(final StringKeyValue stringKeyValue) {
        return io.opentelemetry.proto.common.v1.KeyValue.newBuilder()
                                                        .setKey(stringKeyValue.getKey())
                                                        .setValue(
                                                            AnyValue.newBuilder()
                                                                    .setStringValue(stringKeyValue.getValue()))
                                                        .build();
    }

    private static Histogram convertIntHistogram(final IntHistogram intHistogram) {
        final Histogram.Builder builder = Histogram.newBuilder();
        builder.setAggregationTemporality(convertAggregationTemporality(intHistogram.getAggregationTemporality()));
        builder.setAggregationTemporalityValue(intHistogram.getAggregationTemporalityValue());
        intHistogram.getDataPointsList()
                    .stream()
                    .map(OtelMetricsConvertor::convertIntHistogramDataPoint)
                    .forEach(builder::addDataPoints);
        return builder.build();
    }

    /**
     * Convert IntHistogramDataPoint in OTEL 0.7 to HistogramDataPoint
     * Notice this method ignore min, max, Exemplar fields in HistogramDataPoint
     */
    private static HistogramDataPoint convertIntHistogramDataPoint(final IntHistogramDataPoint intHistogramDataPoint) {
        final HistogramDataPoint.Builder builder = HistogramDataPoint.newBuilder();
        intHistogramDataPoint.getLabelsList()
                             .stream()
                             .map(OtelMetricsConvertor::convertStringKV)
                             .forEach(builder::addAttributes);

        builder.setCount(intHistogramDataPoint.getCount());
        builder.setSum(intHistogramDataPoint.getSum());
        builder.setStartTimeUnixNano(intHistogramDataPoint.getStartTimeUnixNano());
        builder.setTimeUnixNano(intHistogramDataPoint.getTimeUnixNano());
        builder.addBucketCounts(intHistogramDataPoint.getBucketCountsCount());
        builder.setFlags(DataPointFlags.FLAG_NONE_VALUE);
        builder.addAllExplicitBounds(intHistogramDataPoint.getExplicitBoundsList());
        return builder.build();
    }

    /**
     * Convert IntHistogramDataPoint in OTEL 0.7 to HistogramDataPoint
     * Notice this method ignore min, max, Exemplar fields in HistogramDataPoint
     */
    private static HistogramDataPoint convertDoubleHistogramDataPoint(final DoubleHistogramDataPoint intHistogramDataPoint) {
        final HistogramDataPoint.Builder builder = HistogramDataPoint.newBuilder();
        intHistogramDataPoint.getLabelsList()
                             .stream()
                             .map(OtelMetricsConvertor::convertStringKV)
                             .forEach(builder::addAttributes);
        builder.setCount(intHistogramDataPoint.getCount());
        builder.setSum(intHistogramDataPoint.getSum());
        builder.setStartTimeUnixNano(intHistogramDataPoint.getStartTimeUnixNano());
        builder.setTimeUnixNano(intHistogramDataPoint.getTimeUnixNano());
        builder.addBucketCounts(intHistogramDataPoint.getBucketCountsCount());
        builder.setFlags(DataPointFlags.FLAG_NONE_VALUE);
        builder.addAllExplicitBounds(intHistogramDataPoint.getExplicitBoundsList());
        return builder.build();
    }

    private static AggregationTemporality convertAggregationTemporality(final io.opentelemetry.proto.metrics.firehose.v0_7.AggregationTemporality aggregationTemporality) {

        if (aggregationTemporality == io.opentelemetry.proto.metrics.firehose.v0_7.AggregationTemporality.AGGREGATION_TEMPORALITY_UNSPECIFIED) {
            return AggregationTemporality.AGGREGATION_TEMPORALITY_UNSPECIFIED;
        }
        if (aggregationTemporality == io.opentelemetry.proto.metrics.firehose.v0_7.AggregationTemporality.AGGREGATION_TEMPORALITY_CUMULATIVE) {
            return AggregationTemporality.AGGREGATION_TEMPORALITY_CUMULATIVE;
        }

        if (aggregationTemporality == io.opentelemetry.proto.metrics.firehose.v0_7.AggregationTemporality.AGGREGATION_TEMPORALITY_DELTA) {
            return AggregationTemporality.AGGREGATION_TEMPORALITY_DELTA;
        }
        throw new UnsupportedOperationException("Can't convert " + aggregationTemporality);
    }

    private static Histogram convertDoubleHistogram(final DoubleHistogram doubleHistogram) {
        final Histogram.Builder builder = Histogram.newBuilder();
        builder.setAggregationTemporality(convertAggregationTemporality(doubleHistogram.getAggregationTemporality()));
        builder.setAggregationTemporalityValue(doubleHistogram.getAggregationTemporalityValue());
        doubleHistogram.getDataPointsList()
                       .stream()
                       .map(OtelMetricsConvertor::convertDoubleHistogramDataPoint)
                       .forEach(builder::addDataPoints);
        return builder.build();

    }

    private static Gauge convertIntGauge(final IntGauge intGauge) {
        final Gauge.Builder builder = Gauge.newBuilder();
        intGauge.getDataPointsList()
                .stream()
                .map(OtelMetricsConvertor::convertIntDataPoint)
                .forEach(builder::addDataPoints);
        return builder.build();
    }

    private static Gauge convertDoubleGauge(final DoubleGauge doubleGauge) {
        final Gauge.Builder builder = Gauge.newBuilder();
        doubleGauge.getDataPointsList()
                   .stream()
                   .map(OtelMetricsConvertor::convertDoubleDataPoint)
                   .forEach(builder::addDataPoints);
        return builder.build();
    }

    private static Resource convertResource(final io.opentelemetry.proto.resource.firehose.v0_7.Resource resource) {
        final Resource.Builder builder = Resource.newBuilder();
        for (KeyValue keyValue : resource.getAttributesList()) {
            builder.addAttributes(convertKeyValue(keyValue));
        }
        return builder.build();
    }

    private static AnyValue convertAnyValue(final io.opentelemetry.proto.common.firehose.v0_7.AnyValue value) {
        final AnyValue.Builder builder = AnyValue.newBuilder();
        if (value.hasBoolValue()) {
            builder.setBoolValue(value.getBoolValue());
        }
        if (value.hasDoubleValue()) {
            builder.setDoubleValue(value.getDoubleValue());
        }
        if (value.hasIntValue()) {
            builder.setIntValue(value.getIntValue());
        }
        if (value.hasStringValue()) {
            builder.setStringValue(value.getStringValue());
        }
        if (value.hasArrayValue()) {
            builder.setArrayValue(convertValuList(value.getArrayValue()));
        }
        if (value.hasKvlistValue()) {
            builder.setKvlistValue(convertKvlistValue(value.getKvlistValue()));
        }
        return builder.build();
    }

    private static io.opentelemetry.proto.common.v1.KeyValueList convertKvlistValue(final KeyValueList keyValueList) {
        final io.opentelemetry.proto.common.v1.KeyValueList.Builder builder = io.opentelemetry.proto.common.v1.KeyValueList.newBuilder();
        keyValueList.getValuesList().stream().map(OtelMetricsConvertor::convertKeyValue).forEach(builder::addValues);
        return builder.build();
    }

    private static io.opentelemetry.proto.common.v1.KeyValue convertKeyValue(final KeyValue keyValue) {
        return io.opentelemetry.proto.common.v1.KeyValue.newBuilder()
                                                        .setKey(keyValue.getKey())
                                                        .setValue(convertAnyValue(keyValue.getValue()))
                                                        .build();
    }

    private static io.opentelemetry.proto.common.v1.ArrayValue convertValuList(final ArrayValue arrayValue) {
        final io.opentelemetry.proto.common.v1.ArrayValue.Builder builder = io.opentelemetry.proto.common.v1.ArrayValue.newBuilder();
        arrayValue.getValuesList().stream().map(OtelMetricsConvertor::convertAnyValue).forEach(builder::addValues);
        return builder.build();
    }

}
