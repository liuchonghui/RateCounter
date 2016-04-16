package tool.ratecounter.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.overlay.utils.LogUtils;

import tool.ratecounter.activity.CustomWaitDialog;
import tool.ratecounter.android.MoneyInputVerifier;
import tool.ratecounter.android.R;
import tool.ratecounter.android.RateInputVerifier;
import tool.ratecounter.android.YearsInputVerifier;

/**
 * @author liu_chonghui
 */
public class MainFragment extends BaseFragment {

    protected int getPageLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTransaction();

        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void startTransaction() {
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_still);
    }

    protected void initData() {
        if (getActivity().getIntent() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (!isViewNull()) {
            return mView;
        }
        mView = inflater.inflate(getPageLayout(), container, false);
        intView(mView);
        return mView;
    }

    CustomWaitDialog mUpdateDialog;
    EditText yuan;
    EditText rate;
    EditText years;
    Button confirm;

    @SuppressLint("InflateParams")
    protected void intView(View view) {
        mUpdateDialog = new CustomWaitDialog(getActivity());
        mUpdateDialog.setCanceledOnTouchOutside(false);

        yuan = (EditText) view.findViewById(R.id.yuan);
        rate = (EditText) view.findViewById(R.id.rate);
        years = (EditText)view.findViewById(R.id.years);

        yuan.addTextChangedListener(new MoneyInputVerifier(yuan));
        rate.addTextChangedListener(new RateInputVerifier(rate));
        years.addTextChangedListener(new YearsInputVerifier(years));

        confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yuanStr = yuan.getText().toString().trim();
                String rateStr = rate.getText().toString().trim();
                String yearStr = years.getText().toString().trim();
                LogUtils.d("RC", "yuan=" + yuanStr + ", rate=" + rateStr + ", year=" + yearStr);
            }
        });
    }

    protected void flushPage() {
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    boolean firstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (firstResume) {
            firstResume = false;
        }
        if (!firstResume) {
        }
    }

    public boolean holdGoBack() {
        // if (myOneKeyShare != null && myOneKeyShare.isShow()) {
        // return true;
        // }
        // if (popupAttacher != null && popupAttacher.isShowing()) {
        // return true;
        // }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = false;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (holdGoBack()) {
                // if (myOneKeyShare != null && myOneKeyShare.isShow()) {
                // myOneKeyShare.close();
                // }
                // if (popupAttacher != null && popupAttacher.isShowing()) {
                // popupAttacher.closePop();
                // }
                flag = true;
            }
        }
        return flag;
    }

    public void leaveCurrentPage() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.push_still,
                R.anim.push_right_out);
    }

}
