package com.xy.dev.alarm;

import com.xy.dev.DroolsUtils;
import com.xy.dev.ResourceWrapper;
import com.xy.dev.dataProvider.AlarmDataProvider;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by 袁意 on 2017/1/6.
 */
public class AlarmApp {

    public static void main(String[] args) throws IOException {

        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        sessionHolder.set(kc.newKieSession("alarmKS"));

        final AlarmDataProvider provider = new AlarmDataProvider();
        //自动生成fact
        //autoGenerateFact(provider);
        for(int i=0; i<1000000; i++){
            sessionHolder.get().insert(provider.next());
        }
        /*//10秒后停止当前session
        new Thread(new Runnable() {
            public void run() {
                //10秒后停止
                KieSession old = sessionHolder.get();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    return;
                }
                old.dispose();
            }
        }).start();*/

        long start = System.currentTimeMillis();

        sessionHolder.get().fireAllRules();
        long end = System.currentTimeMillis();
        System.err.println(end-start);


        //更换规则
       /* System.out.println("enter any key to continue");
        new Scanner(System.in).next();

        changeVersion();
        autoGenerateFact(provider);
        sessionHolder.get().fireUntilHalt();*/

    }


    private static void autoGenerateFact(final AlarmDataProvider provider) {
        new Thread(new Runnable() {

            public void run() {
                for(int i=0; i<10000; i++){
                    sessionHolder.get().insert(provider.next());
                }
            }
        }).start();
    }


    private static void changeVersion() {

        KieServices kieServices = KieServices.Factory.get();
        final ReleaseId releaseId = kieServices.newReleaseId("com.xy.dev", "alarm", "1.0.0");
        KieRepository repository = kieServices.getRepository();

        List<ResourceWrapper> resources = getResourceWrappers();
        InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId, resources);
        repository.addKieModule(kJar);

        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        kieContainer.updateToVersion(releaseId);

        KieSession session2 = kieContainer.newKieSession();
        sessionHolder.set(session2);
    }

    private static List<ResourceWrapper> getResourceWrappers() {
        ResourceWrapper resource1 = new ResourceWrapper(getdsl(), "alarm.dsl");
        ResourceWrapper resource2 = new ResourceWrapper(getdslr(), "alarm.dslr");
        List<ResourceWrapper> resources = new ArrayList<ResourceWrapper>();
        resources.add(resource1);
        resources.add(resource2);
        return resources;
    }

    private static Resource getdslr() {
        return ResourceFactory.newClassPathResource("com/xy/dev/alarm/alarm.dslr");

    }

    private static Resource getdsl() {
        return ResourceFactory.newClassPathResource("com/xy/dev/alarm/alarm.dsl");
    }

    private static final AtomicReference<KieSession> sessionHolder = new AtomicReference();

}