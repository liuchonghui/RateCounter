package tool.ratecounter.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.sina.sinagame.windowattacher.FooterWindowAttacher;

/**
 * 弹窗
 *
 * @author liu_chonghui
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("deprecation")
public class AddPopupAttacher extends FooterWindowAttacher {

    public AddPopupAttacher(Activity attachedActivity) {
        this(attachedActivity, R.layout.add_popupattacher);
    }

    private AddPopupAttacher(Activity attachedActivity, int layoutResId) {
        super(attachedActivity, R.layout.add_popupattacher,
                R.id.popup_animation_layout);
    }

    public void setData(int year) {
        this.year = year;
    }

    int year;
    EditText years;

    @Override
    public void findViewByContentView(View contentView) {
        years = (EditText) contentView.findViewById(R.id.value);
    }

    @Override
    public void adjustContentView(View contentView) {
        super.adjustContentView(contentView);
        years.setText(String.valueOf(year));
    }

    @Override
    protected void onStateChangeToDismiss() {
        super.onStateChangeToDismiss();
        String yearStr = years.getText().toString().trim();
        if (yearStr == null || yearStr.length() == 0) {
            yearStr = years.getHint().toString().trim();
        }
        refreshYears(Integer.valueOf(yearStr));
    }

    protected void refreshYears(int year) {
    }
}
