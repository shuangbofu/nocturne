package cn.shuangbofu.nocturne.core.observer;

/**
 * Created by shuangbofu on 2020/7/22 10:49
 */
public interface Observable {
    /**
     * register a observer to observable
     */
    public void register(Observer o);

    /**
     * unregister a observer from observable
     */
    public void unregister(Observer o);

    /**
     * notify event to all listeners
     */
    public void notify(Event event) throws Exception;
}
