package rudenia.fit.bstu.stpms9;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;





@Database(entities = {Contact.class}, version = 3)
public abstract class ContactDatabase extends RoomDatabase {

    private static ContactDatabase INSTANCE;

    public static ContactDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ContactDatabase.class, "contacts_db")
                            .addCallback(sRoomDatabaseCallback)
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);

//                    INSTANCE.contactDao().deleteAll();
                }
            };


    public abstract ContactDao contactDao();


    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE contacts "
                    + " ADD COLUMN altr_number TEXT");
        }
    };
}