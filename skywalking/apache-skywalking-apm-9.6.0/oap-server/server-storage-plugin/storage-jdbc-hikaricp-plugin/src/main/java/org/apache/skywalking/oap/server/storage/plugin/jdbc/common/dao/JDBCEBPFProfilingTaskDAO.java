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

package org.apache.skywalking.oap.server.storage.plugin.jdbc.common.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.skywalking.oap.server.core.profiling.ebpf.storage.EBPFProfilingTargetType;
import org.apache.skywalking.oap.server.core.profiling.ebpf.storage.EBPFProfilingTaskRecord;
import org.apache.skywalking.oap.server.core.profiling.ebpf.storage.EBPFProfilingTriggerType;
import org.apache.skywalking.oap.server.core.storage.StorageData;
import org.apache.skywalking.oap.server.core.storage.profiling.ebpf.IEBPFProfilingTaskDAO;
import org.apache.skywalking.oap.server.library.client.jdbc.hikaricp.JDBCClient;
import org.apache.skywalking.oap.server.library.util.StringUtil;
import org.apache.skywalking.oap.server.storage.plugin.jdbc.common.JDBCTableInstaller;
import org.apache.skywalking.oap.server.storage.plugin.jdbc.common.SQLAndParameters;
import org.apache.skywalking.oap.server.storage.plugin.jdbc.common.TableHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JDBCEBPFProfilingTaskDAO extends JDBCSQLExecutor implements IEBPFProfilingTaskDAO {
    private final JDBCClient jdbcClient;
    private final TableHelper tableHelper;

    protected SQLAndParameters buildSQLForQueryTasksByServices(
        final List<String> serviceIdList,
        EBPFProfilingTriggerType triggerType,
        final long taskStartTime,
        final long latestUpdateTime,
        final String table) {
        final var sql = new StringBuilder();
        final var parameters = new ArrayList<>();
        sql.append("select * from ").append(table);

        final var conditionSql = new StringBuilder();
        conditionSql.append(JDBCTableInstaller.TABLE_COLUMN).append(" = ?");
        parameters.add(EBPFProfilingTaskRecord.INDEX_NAME);

        appendListCondition(conditionSql, parameters, EBPFProfilingTaskRecord.SERVICE_ID, serviceIdList);
        if (taskStartTime > 0) {
            appendCondition(conditionSql, parameters,
                EBPFProfilingTaskRecord.START_TIME, ">=", taskStartTime);
        }
        if (latestUpdateTime > 0) {
            appendCondition(conditionSql, parameters,
                EBPFProfilingTaskRecord.LAST_UPDATE_TIME, ">", latestUpdateTime);
        }
        if (triggerType != null) {
            appendCondition(conditionSql, parameters,
                EBPFProfilingTaskRecord.TRIGGER_TYPE, "=", triggerType.value());
        }

        if (conditionSql.length() > 0) {
            sql.append(" where ").append(conditionSql);
        }
        return new SQLAndParameters(sql.toString(), parameters);
    }

    @SneakyThrows
    @Override
    public List<EBPFProfilingTaskRecord> queryTasksByServices(List<String> serviceIdList, EBPFProfilingTriggerType triggerType, long taskStartTime, long latestUpdateTime) throws IOException {
        final var tables = tableHelper.getTablesWithinTTL(EBPFProfilingTaskRecord.INDEX_NAME);
        final var results = new ArrayList<EBPFProfilingTaskRecord>();

        for (final var table : tables) {
            final var sqlAndParameters = buildSQLForQueryTasksByServices(serviceIdList, triggerType, taskStartTime, latestUpdateTime, table);
            results.addAll(
                jdbcClient.executeQuery(
                    sqlAndParameters.sql(),
                    this::buildTasks,
                    sqlAndParameters.parameters()
                )
            );
        }

        return results;
    }

    @Override
    @SneakyThrows
    public List<EBPFProfilingTaskRecord> queryTasksByTargets(String serviceId, String serviceInstanceId, List<EBPFProfilingTargetType> targetTypes,
                                                             EBPFProfilingTriggerType triggerType, long taskStartTime, long latestUpdateTime) throws IOException {
        final var results = new ArrayList<EBPFProfilingTaskRecord>();
        final var tables = tableHelper.getTablesWithinTTL(EBPFProfilingTaskRecord.INDEX_NAME);

        for (final var table : tables) {
            final var sqlAndParameters = buildSQLForQueryTasksByTargets(
                serviceId, serviceInstanceId, targetTypes, triggerType, taskStartTime, latestUpdateTime, table
            );
            results.addAll(
                jdbcClient.executeQuery(
                    sqlAndParameters.sql(),
                    this::buildTasks,
                    sqlAndParameters.parameters()
                )
            );
        }
        return results;
    }

    @Override
    @SneakyThrows
    public List<EBPFProfilingTaskRecord> getTaskRecord(String id) throws IOException {
        final List<EBPFProfilingTaskRecord> results = new ArrayList<>();
        final var tables = tableHelper.getTablesWithinTTL(EBPFProfilingTaskRecord.INDEX_NAME);
        for (final var table : tables) {
            String sql = "select * from " + table +
                " where " + JDBCTableInstaller.TABLE_COLUMN + " = ?" +
                EBPFProfilingTaskRecord.LOGICAL_ID + " = ?";

            results.addAll(jdbcClient.executeQuery(
                sql,
                this::buildTasks,
                EBPFProfilingTaskRecord.INDEX_NAME, id
            ));
        }
        return results;
    }

    protected SQLAndParameters buildSQLForQueryTasksByTargets(
        final String serviceId,
        final String serviceInstanceId,
        final List<EBPFProfilingTargetType> targetTypes,
        EBPFProfilingTriggerType triggerType,
        final long taskStartTime,
        final long latestUpdateTime,
        final String table) {
        final var sql = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var conditions = new StringBuilder();

        sql.append("select * from ").append(table);
        conditions.append(JDBCTableInstaller.TABLE_COLUMN).append(" = ?");
        parameters.add(EBPFProfilingTaskRecord.INDEX_NAME);

        if (StringUtil.isNotEmpty(serviceId)) {
            appendCondition(conditions, parameters, EBPFProfilingTaskRecord.SERVICE_ID, serviceId);
        }
        if (StringUtil.isNotEmpty(serviceInstanceId)) {
            appendCondition(conditions, parameters, EBPFProfilingTaskRecord.INSTANCE_ID, serviceInstanceId);
        }
        appendListCondition(conditions, parameters, EBPFProfilingTaskRecord.TARGET_TYPE,
            targetTypes.stream().map(EBPFProfilingTargetType::value).collect(Collectors.toList()));
        if (taskStartTime > 0) {
            appendCondition(conditions, parameters,
                EBPFProfilingTaskRecord.START_TIME, ">=", taskStartTime);
        }
        if (latestUpdateTime > 0) {
            appendCondition(conditions, parameters,
                EBPFProfilingTaskRecord.LAST_UPDATE_TIME, ">", latestUpdateTime);
        }
        if (triggerType != null) {
            appendCondition(conditions, parameters,
                EBPFProfilingTaskRecord.TRIGGER_TYPE, "=", triggerType.value());
        }

        if (conditions.length() > 0) {
            sql.append(" where ").append(conditions);
        }

        return new SQLAndParameters(sql.toString(), parameters);
    }

    private List<EBPFProfilingTaskRecord> buildTasks(ResultSet resultSet) throws SQLException {
        List<EBPFProfilingTaskRecord> tasks = new ArrayList<>();
        StorageData data;
        while ((data = toStorageData(resultSet, EBPFProfilingTaskRecord.INDEX_NAME, new EBPFProfilingTaskRecord.Builder())) != null) {
            tasks.add((EBPFProfilingTaskRecord) data);
        }
        return tasks;
    }

    private void appendCondition(StringBuilder conditionSql, List<Object> condition, String filed, Object data) {
        appendCondition(conditionSql, condition, filed, "=", data);
    }

    private void appendCondition(StringBuilder conditionSql, List<Object> condition, String filed, String compare, Object data) {
        if (conditionSql.length() > 0) {
            conditionSql.append(" and ");
        }
        conditionSql.append(filed).append(compare).append("?");
        condition.add(data);
    }

    private <T> void appendListCondition(StringBuilder conditionSql, List<Object> condition, String filed, List<T> data) {
        if (conditionSql.length() > 0) {
            conditionSql.append(" and ");
        }
        conditionSql.append(filed).append(" in (");
        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                conditionSql.append(",");
            }
            conditionSql.append("?");
        }
        conditionSql.append(")");
        condition.addAll(data);
    }
}
