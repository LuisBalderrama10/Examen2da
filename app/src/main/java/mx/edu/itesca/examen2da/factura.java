package mx.edu.itesca.examen2da;
import android.os.Parcel;
import android.os.Parcelable;

public class factura implements Parcelable {
    private float iva;
    private float subtotal;
    private float total;

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public factura() {}

    @Override
    public String toString() {
        return "factura{" +
                "iva=" + iva +
                ", subtotal=" + subtotal +
                ", total=" + total +
                '}';
    }

    public factura(float iva, float subtotal, float total) {
        this.iva = iva;
        this.subtotal = subtotal;
        this.total = total;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.iva);
        dest.writeFloat(this.subtotal);
        dest.writeFloat(this.total);
    }
    public void readFromParcel(Parcel source) {
        this.iva = source.readFloat();
        this.subtotal = source.readFloat();
        this.total = source.readFloat();
    }
    protected factura(Parcel in) {
        this.iva = in.readFloat();
        this.total = in.readFloat();
        this.subtotal = in.readFloat();
    }
    public static final Creator<factura> CREATOR = new Creator<factura>() {
        @Override
        public factura createFromParcel(Parcel source) {return new factura(source);}
        @Override
        public factura[] newArray(int size) {return new factura[size];}
    };
}