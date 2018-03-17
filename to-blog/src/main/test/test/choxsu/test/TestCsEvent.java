package test.choxsu.test;

import net.dreamlu.event.core.ApplicationEvent;

/**
 * @author chox su
 * @date 2018/03/16 16:30
 */
public class TestCsEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public TestCsEvent(Object source) {
        super(source);
    }
}
