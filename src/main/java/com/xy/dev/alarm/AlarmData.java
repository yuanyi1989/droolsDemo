package com.xy.dev.alarm;

import lombok.Data;

import java.util.Date;

/**
 * Created by 袁意 on 2017/1/6.
 */
@Data
public class AlarmData {
    private long alarmeID;          // 告警ID
    private String deviceName;      // 告警来源设备名
    private String fromAddress;     // 告警来源地址:Port
    private short alarmType;          // 告警类型 1:事件告警，2:故障告警；3:告警恢复
    private short alarmUrgent;      // 告警紧急程度：1:一般 2:紧急 3:非常紧急
    private short alarmLevel;       // 告警严重程度 1:一般 2:重要 3:非常重要
    private int alarmCode;          // 告警业务标识，表示是什么问题的告警，业务分配
    private String alarmDesc;       // 告警描述
    private String alarmSuggestion; // 告警修复建议
    private Date alarmTime;         // 告警时间


}
