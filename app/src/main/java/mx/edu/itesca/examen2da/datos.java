package mx.edu.itesca.examen2da;
import android.os.Parcel;
import android.os.Parcelable;

public class datos implements Parcelable {
    private float precio;
    private int cantidad;
    private String descripcion;

    public datos(float precio, int cantidad, String descripcion) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public float getPrecio() {return precio;}
    public void setPrecio(float precio) {this.precio = precio;}
    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
    public datos() {}
    @Override
    public int describeContents() {return 0;}

    @Override
    public String toString() {
        return precio + "  " + cantidad + "   " + descripcion;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.precio);
        dest.writeInt(this.cantidad);
        dest.writeString(this.descripcion);
    }
    public void readFromParcel(Parcel source) {
        this.precio = source.readFloat();
        this.cantidad = source.readInt();
        this.descripcion = source.readString();
    }
    protected datos(Parcel in) {
        this.precio = in.readFloat();
        this.cantidad = in.readInt();
        this.descripcion = in.readString();
    }
    public static final Creator<datos> CREATOR = new Creator<datos>() {
        @Override
        public datos createFromParcel(Parcel source) {return new datos(source);}
        @Override
        public datos[] newArray(int size) {return new datos[size];}
    };
}