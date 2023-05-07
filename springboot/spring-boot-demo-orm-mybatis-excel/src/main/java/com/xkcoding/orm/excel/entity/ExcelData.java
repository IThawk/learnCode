package com.xkcoding.orm.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelData implements Serializable {
    private static final long serialVersionUID = -1840831686851699943L;

    private Map<String,List<Object>> data;

    private  Map<String, Map<String, Object>>  dateConfigs;
}
