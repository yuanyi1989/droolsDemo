[condition] 有一个告警=$alarm : AlarmData(

[condition] 并且=&&
[condition] 或者=||
[condition] 等于===
[condition] 大于=>
[condition] 小于=<
[condition] 非=!=
[condition] end=)
[condition] 设备名称{operate}{equalProp}=deviceName {operate} {equalProp}
[condition] 来源地址IP{operate}{equalProp}=fromAddress.split(":")[0] {operate} {equalProp}
[condition] 告警类型{operate}{equalProp}=alarmType {operate} {equalProp}
[condition] 严重程度{operate}{equalProp}=alarmLevel {operate} {equalProp}
[condition] 紧急程度{operate}{equalProp}=alarmUrgent {operate} {equalProp}
[consequence] 打印{content}=globalList.add({content})
