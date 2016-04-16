package tool.ratecounter.android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author liu_chonghui
 */
public class Money implements Parcelable {

    public Money() {
        super();
    }

    private String value = "0.00";
    private String yuan = "0";
    private String jiao = "0";
    private String fen = "0";

    public void objectUpdate(Money o) {
        if (null == o) {
            return;
        }
        setValue(o.getValue());
        setYuan(o.getYuan());
        setJiao(o.getJiao());
        setFen(o.getFen());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        int dot = value.indexOf('.');
        String upHalf = value.substring(0, dot);
        String downHalf = value.substring(dot + 1, value.length());
        if (downHalf.length() == 2) {
            setFen(downHalf.substring(1, 2));
            setJiao(downHalf.substring(0, 1));
        } else {
            setJiao(downHalf.substring(0, 1));
        }
        setYuan(upHalf);
    }

    /**
     * when value is "10.30" then Yuan is "10"
     *
     * @return
     */
    public String getYuan() {
        return yuan;
    }

    public void setYuan(String yuan) {
        this.yuan = yuan;
    }

    /**
     * when value is "10.30" then Jiao is "3"
     *
     * @return
     */
    public String getJiao() {
        return jiao;
    }

    public void setJiao(String jiao) {
        this.jiao = jiao;
    }

    /**
     * when value is "10.30" then Fen is "0"
     *
     * @return
     */
    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    /**
     * when value is "10.30" then Integer is "10"
     *
     * @return
     */
    public String getInteger() {
        return yuan;
    }

    /**
     * when value is "10.30" then Decimal is "30"
     *
     * @return
     */
    public String getDecimal() {
        return jiao + fen;
    }

    /**
     * when value is "10.30" then ShortValue is "10.3"
     *
     * @return
     */
    public String getShortValue() {
        return "0".equalsIgnoreCase(fen) ?
                ("0".equalsIgnoreCase(jiao) ?
                        yuan : (yuan + "." + jiao)) : value;
    }

    /**
     * when value is "10.30" then Double is 10.3
     *
     * @return
     */
    public double getDouble() {
        double value = Double.valueOf(getShortValue());
        return value;
    }

    /**
     * when value is "10.30" then ValueByFen is "1030"
     *
     * @return
     */
    public String getValueByFen() {
        return "0".equalsIgnoreCase(yuan) ?
                ("0".equalsIgnoreCase(jiao) ?
                        fen : (jiao + fen)) : (yuan + jiao + fen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeString(this.yuan);
        dest.writeString(this.jiao);
        dest.writeString(this.fen);
    }

    public static final Creator<Money> CREATOR = new Creator<Money>() {

        @Override
        public Money createFromParcel(Parcel arg0) {
            return new Money(arg0);
        }

        @Override
        public Money[] newArray(int arg0) {
            return new Money[arg0];
        }
    };

    public Money(Parcel in) {
        this.value = in.readString();
        this.yuan = in.readString();
        this.jiao = in.readString();
        this.fen = in.readString();
    }
}
