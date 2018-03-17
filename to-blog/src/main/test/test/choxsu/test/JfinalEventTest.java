package test.choxsu.test;


import com.choxsu.test.Test1Event;
import net.dreamlu.event.EventKit;
import net.dreamlu.event.EventPlugin;
import net.dreamlu.event.core.ApplicationEvent;
import net.dreamlu.event.core.EventListener;
import net.dreamlu.event.support.DuangBeanFactory;
import net.dreamlu.event.support.ObjenesisBeanFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author chox su
 * @date 2018/03/16 16:23
 */
public class JfinalEventTest {

    public JfinalEventTest() {
    }

    private EventPlugin plugin;

    @Before
    public void init() {
        // 初始化插件
        plugin = new EventPlugin();
        // 设置为异步，默认同步，或者使用`threadPool(ExecutorService executorService)`自定义线程池。
        plugin.async();
        // 设置扫描jar包，默认不扫描
        plugin.scanJar();
        // 设置监听器默认包，多个包名使用;分割，默认全扫描
        plugin.scanPackage("test.choxsu");
        // bean工厂，默认为DefaultBeanFactory，可实现IBeanFactory自定义扩展
        // 对于将@EventListener写在不含无参构造器的类需要使用`ObjenesisBeanFactory`
        plugin.beanFactory(new DuangBeanFactory());
        plugin.start();
    }

    @After
    public void dis() {
        plugin.stop();
    }

    public class TestListener {

        @EventListener(order = 1, events = TestCsEvent.class, async = true)
        public void test1(ApplicationEvent event) {
            Object xx = event.getSource();
            System.out.println(Thread.currentThread().getName() + " " + this.getClass() + " " + "\tsource:" + xx);
            for (int i = 0; i < 100000; i++) {
                System.out.println("i:" + i);
            }
        }

        @EventListener(order = 2, events = TestCsEvent.class)
        public void test2(ApplicationEvent event) {
            Object xx = event.getSource();
            System.out.println(Thread.currentThread().getName() + " " + this.getClass() + " " + "\tsource:" + xx);
            System.out.println("test2:发送邮箱确认监听");
        }


    }

    @Test
    public void testEvent() {
        long begin = System.currentTimeMillis();
        //发送事件
        EventKit.post(new TestCsEvent("hello world"));

        System.out.println("用时：" + (System.currentTimeMillis() - begin) + "ms");
        System.out.println("事件发送成功");

    }
}
