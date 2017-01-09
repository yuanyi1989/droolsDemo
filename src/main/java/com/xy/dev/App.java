package com.xy.dev;

import com.xy.dev.alarm.AlarmData;
import com.xy.dev.dataProvider.AlarmDataProvider;
import com.xy.dev.dataProvider.OrderProvider;
import org.drools.core.common.InternalAgenda;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hello world!
 *
 */
public class App
{
    private static final ExecutorService service = Executors.newFixedThreadPool(10);
    private static final List<String> list = new ArrayList<String>(2000000);
    public static void main( String[] args ) throws InterruptedException, IOException {

        final AlarmDataProvider provider = new AlarmDataProvider();
        long start = System.currentTimeMillis();
        for(int i=0; i<8; i++){
            service.execute(new Task(187500));
        }
        service.shutdown();
        service.awaitTermination(100, TimeUnit.SECONDS);
        System.out.print(System.currentTimeMillis()-start);

        //更换规则
        //changeVersion();
    }

    private static void action(String obj){
        list.add(obj);
    }


    private static void autoGenerateFact(final OrderProvider provider) {
        new Thread(new Runnable() {

            public void run() {
                while(true){
                    KieSession session = sessionHolder.get();
                    if(!((InternalAgenda)session.getAgenda()).isAlive()){
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            Thread.interrupted();
                        }
                        continue;
                    }
                    sessionHolder.get().insert(provider.next());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }

            }
        }).start();
    }

    private static void changeVersion() {

        KieServices kieServices = KieServices.Factory.get();
        final ReleaseId releaseId = kieServices.newReleaseId("com.xy.dev", "drools", "1.0.0");
        KieRepository repository = kieServices.getRepository();




        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        kieContainer.updateToVersion(releaseId);

        KieSession session2 = kieContainer.newKieSession();
        sessionHolder.set(session2);
        sessionHolder.get().fireUntilHalt();
    }


    private static class Task implements  Runnable{

        private int count;
        private Task(int count){
            this.count = count;
        }
        @Override
        public void run() {
            AlarmDataProvider provider = new AlarmDataProvider();
            for(int i=0; i<count; i++){
                AlarmData data = provider.next();
                if(data.getDeviceName().equals("hello") && data.getFromAddress().split(":")[0].equals("127.0.0.1")){
                    action("hello meet a alarm");
                }
                if(data.getDeviceName().equals("hello") || data.getDeviceName().equals("world")){
                    action(data.getDeviceName() + " meet a alarm");
                }
                if(data.getAlarmType()!=2){
                    action("ignore a alarm");
                }
                boolean match = false;
                if(data.getDeviceName().equals("hello") || data.getDeviceName().equals("world")){
                    if(data.getAlarmType()==2 && data.getAlarmUrgent()!=1 && data.getAlarmLevel() != 1){
                        match = true;
                    }
                }
                if(match){
                    action ("hello world ignore a alarm");
                }else{
                    action(data.toString());
                }
            }

        }
    }
    private static final AtomicReference<KieSession> sessionHolder = new AtomicReference();
    private static final String RULESFILE_NAME = "version2.drl";
    private static final String rules =
            "package com.xy.dev.version; " +
            "import com.xy.dev.entity.Order;" +
            "import com.xy.dev.entity.OrderStatus;" +
            " rule \"version2 \" dialect \"mvel\" " +
                    "when  $order : Order(status == OrderStatus.CREATED) " +
                    "then System.out.println(\"hello, \"+$order.user.name); " +
                    "end";

}
