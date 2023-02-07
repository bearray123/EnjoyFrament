package xyz.rh.common.eventpublisher;

import androidx.annotation.Keep;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * UI层通用的事件分发逻辑,基于泛型的消息总线
 * 可以通过类别和消息的对象类型进行订阅和分发
 * <p>
 * Created by yuhenghui on 16/8/23.
 */
@Keep
public final class BaseEventPublisher {

    private static BaseEventPublisher sPublisher;

    private final Map<String, Set<OnEventListener>> mSpreadList = new HashMap<>();
    private final Map<String, Set<OnEventListener>> mNormalList = new HashMap<>();

    private final Map<String, Set<OnEventListener>> mPendingUnsubscribeList = new HashMap<>();
    private final Map<String, Object> mStrickList = new ConcurrentHashMap<>();
    private SerialExecutor mExecutor = new SerialExecutor();
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static final Subscription NULL_SUBSCRIPTION = new Subscription();

    private BaseEventPublisher() {
    }

    /**
     * 获取到事件分发的单例对象
     *
     * @return
     */
    public static BaseEventPublisher getPublisher() {
        if (sPublisher == null) {
            synchronized (BaseEventPublisher.class) {
                if (sPublisher == null) {
                    sPublisher = new BaseEventPublisher();
                }
            }
        }
        return sPublisher;
    }

    public Subscription subscribe(Lifecycle lifecycle, final String category, final OnEventListener l) {
        if (subscribe(category, l)) {
            return new Subscription(lifecycle, category, l, null);
        }
        return NULL_SUBSCRIPTION;
    }

    public Subscription subscribe(Lifecycle lifecycle, final String category, final OnEventListener l, final Class<?> clazz) {
        if (subscribe(category, l, clazz)) {
            return new Subscription(lifecycle, category, l, clazz);
        }
        return NULL_SUBSCRIPTION;
    }

    /**
     * 订阅某个类别的事件, 当事件发生时分发到注册的接口上
     *
     * @param category
     * @param l
     * @return
     */
    public boolean subscribe(final String category, final OnEventListener l) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }
        subscribe(category, l, Callback.NULL);
        return true;
    }

    private void subscribe(final String category, final OnEventListener l, final Callback callback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                /** 根据泛型信息订阅内容*/
                if (l == null) {
                    // 处理异常情况
                    if (callback != null) {
                        callback.onCallback(false);
                    }
                    return;
                }
                Class clazz;
                try {
                    clazz = GenericHelper.getGenericTypeArgument(l.getClass(), OnEventListener.class, 0);
                } catch (Exception e) {
                    Log.e("BaseEventPublisher", "caught exception: category=" + category + ", listener=" + l);
                    throw e;
                }
                String key = createKey(category, clazz);
                if (TextUtils.isEmpty(key)) {
                    if (callback != null) {
                        callback.onCallback(false);
                    }
                    return;
                }

                /** 根据订阅的内容是否接受子类分别放到不同的队列中*/
                Set<OnEventListener> list;
                Map<String, Set<OnEventListener>> map = spreadable(l.getClass()) ? mSpreadList :
                    mNormalList;
                synchronized (map) {
                    list = map.get(key);
                    if (list == null) {
                        list = new LinkedHashSet<>();
                        map.put(key, list);
                    }
                }

                synchronized (list) {
                    if (list.contains(l)) {
                        if (callback != null) {
                            callback.onCallback(false);
                        }
                    } else {
                        list.add(l);
                        if (callback != null) {
                            callback.onCallback(true);
                        }
                    }
                }
            }
        });
    }

    public boolean subscribe(final String category, final OnEventListener l, final Class<?> clazz) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }
        subscribe(category, l, clazz, Callback.NULL);
        return true;
    }

    private void subscribe(final String category, final OnEventListener l, final Class<?> clazz, final Callback callback) {
        if (clazz == null) {
            subscribe(category, l, callback);
            return;
        }
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String key = createKey(category, clazz);
                if (TextUtils.isEmpty(key)) {
                    if (callback != null) {
                        callback.onCallback(false);
                    }
                    return;
                }

                /** 根据订阅的内容是否接受子类分别放到不同的队列中*/
                Set<OnEventListener> list;
                Map<String, Set<OnEventListener>> map = spreadable(l.getClass()) ? mSpreadList :
                    mNormalList;
                synchronized (map) {
                    list = map.get(key);
                    if (list == null) {
                        list = new LinkedHashSet<>();
                        map.put(key, list);
                    }
                }

                synchronized (list) {
                    if (list.contains(l)) {
                        if (callback != null) {
                            callback.onCallback(false);
                        }
                    } else {
                        list.add(l);
                        if (callback != null) {
                            callback.onCallback(true);
                        }
                    }
                }
            }
        });
    }


    /**
     * 粘性订阅，如果已经有发送过消息，则把最好一次的消息内容发送给当前新的订阅者
     *
     * @param category
     * @param l
     * @return
     */
    public boolean subscribeSticky(final String category, final OnEventListener l) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }
        subscribe(category, l, new Callback() {
            @Override
            public void onCallback(boolean result) {
                if (!result) return;
                synchronized (mStrickList) {
                    // 判断是否有发送过消息
                    if (mStrickList.containsKey(category)) {
                        // 发送消息给新注册的订阅者
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (checkPendingUnsubscribe(category, l)) {
                                    return;
                                }

                                if (!checkPendingUnsubscribe(category, l)) {
                                    l.onEvent(category, mStrickList.get(category));
                                }
                            }
                        });
                    }
                }
            }
        });
        return true;
    }


    private void recordPendingUnsubscribe(final String category, final OnEventListener l) {
        Set<OnEventListener> list;
        synchronized (mPendingUnsubscribeList) {
            list = mPendingUnsubscribeList.get(category);
            if (list == null) {
                list = new LinkedHashSet<>();
                mPendingUnsubscribeList.put(category, list);
            }
        }
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (list) {
            list.add(l);
        }
    }

    private void rmPendingUnsubscribe(final String category, final OnEventListener l) {
        Set<OnEventListener> list;
        synchronized (mPendingUnsubscribeList) {
            list = mPendingUnsubscribeList.get(category);
        }
        if (list != null) {
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (list) {
                list.remove(l);
            }
        }
    }

    private boolean checkPendingUnsubscribe(final String category, final OnEventListener l) {
        Set<OnEventListener> list;
        synchronized (mPendingUnsubscribeList) {
            list = mPendingUnsubscribeList.get(category);
        }
        if (list == null) {
            return false;
        }
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (list) {
            return list.contains(l);
        }
    }

    /**
     * 取消某个事件的订阅,取消成功返回true
     * 参数检查没通过,或者不存在反注册的监听返回false
     *
     * @param category
     * @param l
     * @return
     */
    public boolean unsubscribe(final String category, final OnEventListener l) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }
        recordPendingUnsubscribe(category, l);
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    /** 根据泛型信息取消订阅 */
                    Class clazz;
                    try {
                        clazz = GenericHelper.getGenericTypeArgument(l.getClass(), OnEventListener.class, 0);
                    } catch (Exception e) {
                        Log.e("BaseEventPublisher", "unsubscribe caught exception: category=" + category + ", listener=" + l);
                        throw e;
                    }
                    String key = createKey(category, clazz);
                    if (TextUtils.isEmpty(key)) {
                        return;
                    }

                    /** 根据是否订阅子类型,分别从不同的队列取消订阅*/
                    Map<String, Set<OnEventListener>> map = spreadable(l.getClass()) ? mSpreadList :
                        mNormalList;
                    Set<OnEventListener> list;
                    synchronized (map) {
                        list = map.get(key);
                    }
                    if (list == null) {
                        return;
                    }

                    synchronized (list) {
                        list.remove(l);
                    }
                } finally {
                    rmPendingUnsubscribe(category, l);
                }
            }
        });
        return true;
    }

    public boolean unsubscribe(final String category, final OnEventListener l, final Class<?> clazz) {
        if (TextUtils.isEmpty(category) || l == null) {
            return false;
        }
        recordPendingUnsubscribe(category, l);
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    /** 根据泛型信息取消订阅 */
                    String key = createKey(category, clazz);
                    if (TextUtils.isEmpty(key)) {
                        return;
                    }

                    /** 根据是否订阅子类型,分别从不同的队列取消订阅*/
                    Map<String, Set<OnEventListener>> map = spreadable(l.getClass()) ? mSpreadList :
                        mNormalList;
                    Set<OnEventListener> list;
                    synchronized (map) {
                        list = map.get(key);
                    }
                    if (list == null) {
                        return;
                    }
                    synchronized (list) {
                        list.remove(l);
                    }
                } finally {
                    rmPendingUnsubscribe(category, l);
                }
            }
        });
        return true;
    }


    /**
     * 发布一个只包括类别信息的事件到事件分发器
     *
     * @param category
     */
    public void publish(String category) {
        publish(category, null);
    }

    /**
     * 发布一个事件提交到事件分发器
     *
     * @param event
     */
    public void publish(final String category, final Object event) {
        if (TextUtils.isEmpty(category)) {
            return;
        }
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                /** 通过实际分发的类型进行分发*/
                Class clazz = event != null ? event.getClass() : NullEvent.class;
                final String key = createKey(category, clazz);

                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        publishStep(key, category, event, mNormalList);
                    }
                });

                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        publishStep(key, category, event, mSpreadList);
                    }
                });


                /** 将内容分发给订阅了父类内容的监听*/
                clazz = clazz.getSuperclass();
                while (clazz != null) {
                    final String newKey = createKey(category, clazz);
                    mExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            publishStep(newKey, category, event, mSpreadList);
                        }
                    });
                    clazz = clazz.getSuperclass();
                }
            }
        });
    }

    /**
     * 会缓存该事件，等新的注册时，下发事件给注册
     * event是发送的给监听者的参数，可以是null
     */
    public void publishSticky(String category) {
        publishSticky(category, null);
    }

    public void publishSticky(String category, Object event) {
        if (TextUtils.isEmpty(category)) {
            return;
        }
        synchronized (mStrickList) {
            // 直接使用category作为key，而不是createKey，因为不同的注册界面，sticky共用一个即可
            Object mapVal = event != null ? event : new NullEvent();
            mStrickList.put(category, mapVal);
        }
        publish(category, event);
    }

    /**
     * 移除sticky数据
     *
     * @param category
     */
    public void removeStickyEvent(String category) {
        synchronized (mStrickList) {
            mStrickList.remove(category);
        }
    }

    public void removeAllStickyEvent() {
        synchronized (mStrickList) {
            mStrickList.clear();
        }
    }

    /**
     * 根据key来分发内容给订阅者
     *
     * @param key
     * @param category
     * @param event
     * @param map
     */
    private void publishStep(String key, final String category, final Object event,
        Map<String, Set<OnEventListener>> map) {
        OnEventListener[] listeners;
        final Set<OnEventListener> list;
        synchronized (map) {
            list = map.get(key);
            listeners = list != null ? list.toArray(new OnEventListener[list.size()]) : null;
        }

        int size = listeners != null ? listeners.length : 0;
        for (int i = 0; i < size; i++) {
            final OnEventListener listener = listeners[i];
            if (listener == null) {
                continue;
            }
            if (checkPendingUnsubscribe(category, listener)) {
                continue;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (checkPendingUnsubscribe(category, listener)) {
                        return;
                    }
                    synchronized (list) {
                        if (!list.contains(listener)) {
                            return;
                        }
                    }
                    listener.onEvent(category, event);
                }
            });
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{spread: ").append(mSpreadList);
        builder.append(", normal: ").append(mNormalList);
        return builder.append("}").toString();
    }

    /**
     * 订阅者是否接受子类型的事件信息
     *
     * @param clazz
     * @return true接受
     */
    private static boolean spreadable(Class clazz) {
        Method onEventMethod;
        try {
            onEventMethod = clazz.getMethod("onEvent", String.class, Object.class);
        } catch (NoSuchMethodException e) {
            /** 查找onEvent方法出错,走默认流程*/
            return true;
        }
        /** 参数数量不是两个,直接走默认流程*/
        Annotation[][] annotations = onEventMethod.getParameterAnnotations();
        if (annotations == null || annotations.length != 2) {
            return true;
        }
        int len = annotations[1] != null ? annotations[1].length : 0;
        for (int i = 0; i < len; i++) {
            if (StrictType.class.isAssignableFrom(annotations[1][i].getClass())) {
                return false;
            }
        }
        return true;
    }

    private static String createKey(String category, Class<? extends Object> clazz) {
        if (TextUtils.isEmpty(category) || clazz == null) {
            return null;
        }
        return category + "@" + clazz.getCanonicalName();
    }

    /**
     * 标识接口,用于接收只有category的事件
     */
    public static final class NullEvent {
        private NullEvent() {
        }
    }


    /**
     * 事件订阅者,分发系统会根据订阅的类别和内容类型进行分发
     *
     * @param <T> 事件内容.事件内容参数可以带上StrictType,
     *            带上StrictType表示当前事件订阅者只接受声明的类型,不接受其子类型
     */
    @Keep
    public interface OnEventListener<T> {
        void onEvent(String category, T event);
    }

    /**
     * 参数标识注解,带这个注解的就表示对于方法只接收完全一致的类型,不接收其子类型
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.TYPE})
    public @interface StrictType {
    }

    private interface Callback {
        Callback NULL = null;

        void onCallback(boolean result);
    }

    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<>();
        Runnable mActive;

        public synchronized void execute(@NonNull final Runnable r) {
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        private synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    public static class Subscription implements DefaultLifecycleObserver {

        private Lifecycle mLifecycle;
        private String mCategory;
        private OnEventListener mListener;
        private Class<?> mClazz;

        private Subscription() {
        }

        private Subscription(Lifecycle lifecycle, String category, OnEventListener l, Class<?> clazz) {
            if (lifecycle == null) {
                throw new IllegalArgumentException("lifecycle is null");
            }
            mLifecycle = lifecycle;
            mCategory = category;
            mListener = l;
            mClazz = clazz;
        }

        public void autoRelease() {
            if (mLifecycle != null) {
                mLifecycle.addObserver(this);
            }
        }

        @Override
        public void onDestroy(@NonNull LifecycleOwner owner) {
            mLifecycle.removeObserver(this);

            if (mClazz != null) {
                BaseEventPublisher.getPublisher().unsubscribe(mCategory, mListener, mClazz);
            } else {
                BaseEventPublisher.getPublisher().unsubscribe(mCategory, mListener);
            }
        }
    }

}

