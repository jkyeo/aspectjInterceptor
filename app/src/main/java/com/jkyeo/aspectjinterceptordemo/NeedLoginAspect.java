package com.jkyeo.aspectjinterceptordemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import rx_activity_result2.Result;
import rx_activity_result2.RxActivityResult;

import static android.app.Activity.RESULT_OK;

/**
 * @author 杨建宽
 * @date 2017/11/16
 * @mail yangjiankuan@lanjingren.com
 * @desc
 */

@Aspect
public class NeedLoginAspect {
    @Pointcut("execution(@com.jkyeo.aspectjinterceptordemo.NeedLogin * *(..)) && @annotation(needLogin)")
    public void pointcutOnNeedLoginMethod(NeedLogin needLogin) {
    }

    @Around("pointcutOnNeedLoginMethod(needLogin)")
    public void adviceOnNeedLoginMethod(final ProceedingJoinPoint joinPoint, final NeedLogin needLogin) {
        if (hasLoggedIn()) {
            try {
                joinPoint.proceed();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return;
        }
        Object target = joinPoint.getTarget();
        /** AppCompatActivity extends FragmentActivity and FragmentActivity extends Activity, so handle it only with Activity **/
        Activity activity = null;
        if (target instanceof Activity) {
            activity = (Activity) target;
        } else if (target instanceof Fragment) {
            activity = ((Fragment) target).getActivity();
        } else if (target instanceof android.support.v4.app.Fragment) {
            activity = ((android.support.v4.app.Fragment) target).getActivity();
        } else {
            /** if target is not activity nor fragment, try to get first arg as activity from the method annotated **/
            Object[] args = joinPoint.getArgs();
            if (null != args) {
                for (Object obj: args) {
                    if (null != obj && obj instanceof Activity) {
                        activity = (Activity) obj;
                        break;
                    } else if (null != obj && obj instanceof Fragment) {
                        activity = ((Fragment) obj).getActivity();
                        break;
                    } else if (null != obj && obj instanceof android.support.v4.app.Fragment) {
                        activity = ((android.support.v4.app.Fragment) obj).getActivity();
                        break;
                    }
                }
            }
        }
        if (null == activity) {
            throw new IllegalArgumentException("If using 'NeedLogin' annotation not in activity nor fragment class," +
                    " an activity or fragment object needed in method args.");
        }
        gotoLogin(activity, joinPoint, needLogin);
    }

    private void gotoLogin(Activity activity, final ProceedingJoinPoint joinPoint, final NeedLogin needLogin) {
        RxActivityResult.on(activity).startIntent(new Intent(activity, LoginActivity.class))
                .subscribe(new Observer<Result<Activity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<Activity> activityResult) {
                        if (activityResult.resultCode() == RESULT_OK) {
                            try {
                                if (needLogin.retry()) {
                                    joinPoint.proceed();
                                }
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private boolean hasLoggedIn() {
        return false;
    }
}
