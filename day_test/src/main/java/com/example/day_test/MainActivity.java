package com.example.day_test;

import android.Manifest;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.day_test.bean.AnimalBean;
import com.example.day_test.bean.AnimalKindBean;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.android.tpush.XGPushConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "王鹏";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnXg = findViewById(R.id.main_btn);
        Button btnRx = findViewById(R.id.main_btn_rx);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushMsg();
            }
        };
        btnXg.setOnClickListener(this);
        btnRx.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn:
                pushMsg();//发送通知
                break;
            case R.id.main_btn_rx:
                moduleRx();
                break;
        }
    }

    private void moduleRx() {
        //testObservableCreate();
        //testMap();
        //testFlatMap();
        //testInterval();
        //testDistinct();
        //testFilter();
        //testBuffer();
        //testTimer();
        //testInterVal2();

        testPermission();

    }

    private void testPermission() {
        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });
    }

    private void testInterVal2() {
        Flowable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: " + aLong);
                    }
                });
    }

    private void testTimer() {
        Flowable.timer(1, TimeUnit.SECONDS)//延时一秒发送
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: " + aLong);
                    }
                });
    }

    private void testBuffer() {
        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .buffer(2, 3)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d(TAG, "accept: " + integers);
                    }
                });
    }

    private void testFilter() {
        Flowable.just(12, 3, 5, 13, 15, 20, 30, 1, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 10;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: " + integer);
            }
        });
    }

    private void testDistinct() {
        Flowable.just(1, 2, 1, 1, 1, 3, 5, 6, 7, 8)//12,3,5,13,15,20,30,1,6
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept: " + integer);
                    }
                });
    }

    private void testInterval() {
        Disposable subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: " + aLong);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept222 " + aLong);
                    }
                });
        /*Observable.interval(1,TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept: "+aLong);
                    }
                });*/


    }

    private void testFlatMap() {
        AnimalKindBean kBean = new AnimalKindBean("老虎", "东北");
        Observable.just(kBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<AnimalKindBean>() {
                    @Override
                    public void accept(AnimalKindBean animalKindBean) throws Exception {
                        Log.d(TAG, "accept: " + Thread.currentThread().getName());
                        Log.d(TAG, "accept: " + animalKindBean.getAddr());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<AnimalKindBean, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final AnimalKindBean animalKindBean) throws Exception {
                        Log.d(TAG, "accept: " + Thread.currentThread().getName());
                        return Observable.just(animalKindBean.getName());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: " + Thread.currentThread().getName());
                        Log.d(TAG, "accept: " + s);
                    }
                });

    }

    private void testMap() {
        Observable.create(new ObservableOnSubscribe<List<AnimalKindBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AnimalKindBean>> emitter) throws Exception {
                List<AnimalKindBean> kinds = new ArrayList<>();
                for (int a = 0; a < 2; a++) {
                    kinds.add(new AnimalKindBean("黄色" + a, "英国"));
                }
                emitter.onNext(kinds);
            }
        }).map(new Function<List<AnimalKindBean>, String>() {
            @Override
            public String apply(List<AnimalKindBean> animalKindBeans) throws Exception {
                String s = "";
                for (AnimalKindBean kBean : animalKindBeans) {
                    s += kBean.getName();
                }
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);
            }
        });
    }

    private void testObservableCreate() {
        Observable.create(new ObservableOnSubscribe<List<AnimalBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AnimalBean>> emitter) throws Exception {
                List<AnimalBean> animals = new ArrayList<>();
                List<AnimalKindBean> kinds = new ArrayList<>();
                for (int a = 0; a < 2; a++) {
                    kinds.add(new AnimalKindBean("黄色" + a, "英国"));
                }
                for (int i = 5; i > 0; i--) {
                    animals.add(new AnimalBean("猫咪", "抓鱼", kinds));
                }
                emitter.onNext(animals);
                emitter.onComplete();
            }
        }).subscribe(new Observer<List<AnimalBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe()");
            }

            @Override
            public void onNext(List<AnimalBean> animalBeans) {
                Log.d(TAG, "animalBeans:" + animalBeans);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete()");
            }
        });
    }

    private void pushMsg() {
        String XGtoken = XGPushConfig.getToken(this);
    }


}
