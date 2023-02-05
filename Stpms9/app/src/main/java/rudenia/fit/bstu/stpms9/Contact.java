package rudenia.fit.bstu.stpms9;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;







@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "number")
    private String mNumber;



    @Ignore
    @ColumnInfo(name = "altr_number")
    private String mAltrNumber;

    public Contact(@NonNull String name, @NonNull String number) {
        mName = name;
        mNumber = number;
        mAltrNumber="";
    }
    public Contact(@NonNull String name, @NonNull String number, @NonNull String alterNumber) {
        mName = name;
        mNumber = number;
        mAltrNumber=alterNumber;
    }


    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getAltrNumber() {
        return mAltrNumber;
    }

    public void setAltrNumber(String altrNumber) {
        this.mAltrNumber = altrNumber;
    }
}

