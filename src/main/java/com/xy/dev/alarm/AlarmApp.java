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
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by 袁意 on 2017/1/6.
 */
public class AlarmApp {

    private static final ExecutorService service = Executors.newFixedThreadPool(10);
    private static KieContainer kc;
    public static void main(String[] args) throws IOException, InterruptedException {
        redeploy("1.0.0");
        run();
        new Scanner(System.in).next();
        redeploy("1.0.1");
        run();
        service.shutdown();
    }

    public static void run() throws InterruptedException {
        final AlarmDataProvider provider = new AlarmDataProvider();
        final int count = 20;
        for(int i=0; i<10; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    StatelessKieSession session = kc.newStatelessKieSession();
                    session.setGlobal("globalList", new ArrayList<String>());
                    List<AlarmData> list = new ArrayList<AlarmData>();
                    for(int i=0; i<count; i++){
                        list.add(provider.next());
                    }
                    session.execute(list);
                }
            });
        }
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


    private static void redeploy(String version) {

        KieServices kieServices = KieServices.Factory.get();
        final ReleaseId releaseId = kieServices.newReleaseId("com.xy.dev", "alarm", version);
        KieRepository repository = kieServices.getRepository();

        List<ResourceWrapper> resources = getResourceWrappers();
        InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId, resources);
        repository.addKieModule(kJar);

        KieContainer kieContainer = kieServices.newKieContainer(releaseId);
        kieContainer.updateToVersion(releaseId);
        kc = kieContainer;
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
