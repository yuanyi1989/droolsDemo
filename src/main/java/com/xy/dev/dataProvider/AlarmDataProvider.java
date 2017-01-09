package com.xy.dev.dataProvider;

import com.xy.dev.alarm.AlarmData;

import java.util.Date;
import java.util.Random;

/**
 * Created by 袁意 on 2017/1/6.
 */
public class AlarmDataProvider {

    private Random random = new Random();
    private String[] names = new String[]{"peter", "jack", "john"};
    private short[] levels = new short[]{1,2,3};

    public boolean hasNext(){
        return random.nextBoolean();
    }

    public AlarmData next(){
        AlarmData data = new AlarmData();
        data.setAlarmCode(random.nextInt(10));
        data.setAlarmDesc("告警描述"+random.nextInt(20));
        data.setAlarmeID(random.nextInt(100));
        data.setAlarmLevel(levels[random.nextInt(3)]);
        data.setAlarmSuggestion("告警建议");
        data.setAlarmTime(new Date());
        data.setAlarmType(levels[random.nextInt(3)]);
        data.setAlarmUrgent(levels[random.nextInt(3)]);


        if(random.nextBoolean()){
            data.setDeviceName(random.nextBoolean()?"hello":"world");
        }else{
            data.setDeviceName("iphone");
        }
        if(random.nextBoolean()){
            data.setFromAddress("127.0.0.1:24");
        }else{
            data.setFromAddress("192.168.1.1:11101");
        }

        return data;
    }
}
