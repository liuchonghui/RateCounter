package tool.ratecounter.android;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * Verify money input text.
 *
 * @author liu_chonghui
 */
public class MoneyInputVerifier implements TextWatcher {
    EditText mEditText;

    public MoneyInputVerifier(EditText mEditText) {
        this.mEditText = mEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String currentStr = s.toString();
        if (currentStr == null || currentStr.length() == 0) {
            return;
        }
        if (currentStr.endsWith(".") || currentStr.endsWith(".0")) {
            return;
        }
        BigDecimal bd = new BigDecimal(currentStr);
        bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        Money m = new Money();
        m.setValue(bd.toString());
        if (!currentStr.equalsIgnoreCase(m.getShortValue())) {
            mEditText.setText(m.getShortValue());
            mEditText.setSelection(m.getShortValue().length());
        }
    }
}