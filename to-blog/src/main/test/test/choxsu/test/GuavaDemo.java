package test.choxsu.test;

import com.google.common.base.Optional;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Test;

import javax.swing.event.ChangeEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @author chox su
 * @date 2018/03/14 13:24
 */
public class GuavaDemo {

    @Test
    public void test0() {
        //optional
        Optional<Integer> possible = Optional.of(5);
        boolean present = possible.isPresent();// returns true
        Integer integer = possible.get();// returns 5

        System.out.println("present:" + present);
        System.out.println("integer:" + integer);
    }

    class OrderEvent {
        private String message;

        public OrderEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    class HelloEventListener {

        @Subscribe
        public void listen(OrderEvent event) {
            System.out.println("receive1 msg:" + event.getMessage());
        }


        @Subscribe
        public void listen(String event) {
            System.out.println("receive2 msg:" + event);
        }


        @Subscribe
        public void listen(Integer event) {
            System.out.println("receive3 msg:" + event);
        }

    }

    class Hello2EventListener {
        @Subscribe
        public void listen(OrderEvent event) {
            System.out.println("hello2 receive2 msg:" + event.getMessage());
        }

        @Subscribe
        public void listen(String event) {
            System.out.println("hello2 receive2 msg:" + event);
        }
    }

    @Test
    public void test1() {
        //Creates a new EventBus with the given identifier.
        EventBus eventBus = new EventBus("choxsu");
        //register all subscriber
        eventBus.register(new HelloEventListener());
        eventBus.register(new Hello2EventListener());

        //publish event
        eventBus.post(new OrderEvent("hello"));
        eventBus.post(new OrderEvent("world"));

        eventBus.post("hello world");
        eventBus.post("hello world2");

        eventBus.post(1);
        eventBus.post(12);
    }

    private final Object lock = new Object();

    class AsyncRegisterEvent {

        @Subscribe
//        @AllowConcurrentEvents
        public void listen(String event) {

            System.out.println("event:" + event);
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("i:" + i);
                }
            }
            System.out.println("说好的十万次呢");
            System.out.println("end");
        }

    }

    class AsyncRegisterEvent2 {

        @Subscribe
        @AllowConcurrentEvents
        public void listen(Integer event) {
            System.out.println("Integer:" + event);
        }
    }

    @Test
    public void test2() {
//        EventBus asyncEventBus = new EventBus("choxsu");
        AsyncEventBus asyncEventBus = new AsyncEventBus("choxsu1", Executors.newSingleThreadExecutor());
        asyncEventBus.register(new AsyncRegisterEvent());
//        asyncEventBus.register(new AsyncRegisterEvent2());+·
        asyncEventBus.post("掉包了");
//        asyncEventBus.post(20152);
        System.out.println("测试异步，发送成功");

    }


}
