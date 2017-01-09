package com.xy.dev;

import com.xy.dev.dataProvider.OrderProvider;
import org.drools.core.common.InternalAgenda;
import org.kie.api.KieServices;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException, IOException {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        sessionHolder.set(kc.newKieSession("merchantKS"));

        final OrderProvider provider = new OrderProvider();
        //自动生成fact
        autoGenerateFact(provider);

        //10秒后停止当前session
        new Thread(new Runnable() {
            public void run() {
                //10秒后停止
            KieSession old = sessionHolder.get();
                try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                old.dispose();
        }
        }).start();

        sessionHolder.get().fireUntilHalt();
        System.out.println("stop-----------------------");



        //更换规则
        //changeVersion();
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
