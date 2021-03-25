package com.ithawk.demo.pattern.observer.myevent;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Demo {
    static class Event {
        private String eventType;
        private Method callback;
        private Object target;
        private String trigger;

        public String getTrigger() {
            return trigger;
        }

        public Event setTrigger(String trigger) {
            this.trigger = trigger;
            return this;
        }

        Object[] args;

        public Event(Object target, Method callback) {
            this.target = target;
            this.callback = callback;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public Method getCallback() {
            return callback;
        }

        public void setCallback(Method callback) {
            this.callback = callback;
        }

        public Object[] getArgs() {
            return args;
        }

        public Event setArgs(Object[] args) {
            this.args = args;
            return this;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }
    }

    static class EventListener {

        //可以重复消费
        protected Map<String, List<Event>> events1 = new ConcurrentHashMap<>();
        //一次消费
        protected Map<String, Event> events2 = new ConcurrentHashMap<>();

        public void addListener(String type, String eventType, Object target) {
            try {

                Class<?> clazz = target.getClass();
                Method[] methods = clazz.getDeclaredMethods();

                Method method = null;
                for (Method s : methods) {
                    if (s.getName().equals(eventType)) {
                        method = s;
                    }
                }

                this.addListener(type,
                        eventType,
                        target,
                        method);
            } catch (Exception e) {
                System.out.println("error :" + e.toString());
            }
        }

        public void addListener(String type, String eventType, Object target, Method callback) {
            //注册事件
            if ("on".equals(type)) {
                if (events1.containsKey(eventType)) {
                    events1.get(eventType).add(new Event(target, callback));
                } else {
                    List<Event> events = new ArrayList<>();
                    events.add(new Event(target, callback));
                    events1.put(eventType, events);
                }
            }
            if ("once".equals(type)) {
                events2.put(eventType, new Event(target, callback));
            }

        }

        private void trigger(String order, Event event) {
            try {
                if (event.getCallback() != null) {
                    event.getCallback().invoke(event.getTarget(), new Object[]{order, event.getArgs()});
                }
            } catch (Exception e) {
                System.out.println("error :" + e.toString());
            }
        }

        //事件名称触发
        protected void trigger(String order, Object... args) {
            //注册事件
            if (this.events1.containsKey(order)) {
                int i = 1;

                for (Event event : this.events1.get(order)) {
                    event.setTrigger(order);
                    String orderNow = event.getTrigger() + i;
                    trigger(orderNow, event.setArgs(args));
                    i++;
                }
            }

            if (this.events2.containsKey(order)) {
                trigger(order, this.events2.get(order).setTrigger(order).setArgs(args));
                this.events2.remove(order);
            }

        }
    }

    static class CallBack {
        public void add(String order, Object... args) {
            StringBuilder stringBuilder = new StringBuilder();
            if (null != args) {
                for (Object o : args) {
                    stringBuilder.append(o).append(",");
                }
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            }
            System.out.println("receive event " + order + " args are:" + stringBuilder);
        }


        public void minus(String order, Object... args) {
            System.out.println("receive event minus ");
        }

        public void addonce(String order, Object... args) {
            StringBuilder stringBuilder = new StringBuilder();
            if (null != args) {
                for (Object o : args) {
                    stringBuilder.append(o).append(",");
                }
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            }

            System.out.println("receive event  " + order + " args are:" + stringBuilder);
        }
    }


    static class EventEmitter extends EventListener {

        public void on(String order, Object... args) {
            CallBack callback = new CallBack();
            super.addListener("on", order, callback);
        }

        public void once(String order, Object... args) {
            CallBack callback = new CallBack();
            super.addListener("once", order, callback);
        }

        public void send(String order, Object... args) {

            this.trigger(order, args);
        }

    }


    public static void main(String[] args) {

        EventEmitter eventListener = new EventEmitter();

        //事件可被重复触发
        eventListener.on("add");
        eventListener.on("add");
        eventListener.on("minus");

        //事件只会被重复触发一次
        eventListener.once("addonce");
        eventListener.send("add", 1, 2, 3);
        eventListener.send("add", 4, 5, 6);
        eventListener.send("minus");
        eventListener.send("minus");
        eventListener.send("addonce", 1, 2, 3);
        eventListener.send("addonce", 1, 2, 3); //由于是once订阅，这行不会触发上面注册的监听器

        //再订阅一次addonce，事件又能被触发了（但还是只会被触发一次）
        eventListener.once("addonce");

        eventListener.send("addonce", 4, 5, 6);
        eventListener.send("addonce", 4, 5, 6); //由于是once订阅，这行不会触发上面注册的监听器


//
//        上述代码应该能输出
//        receive event add1, args are: 1,2,3
//        receive event add2, args are: 1,2,3
//        receive event add1, args are: 4,5,6
//        receive event add2, args are: 4,5,6
//        receive event minus
//        receive event minus
//        receive event addonce, args are: 1,2,3
//        receive event addonce2, args are: 4,5,6

    }
}
