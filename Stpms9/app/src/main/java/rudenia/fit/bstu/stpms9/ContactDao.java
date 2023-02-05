package rudenia.fit.bstu.stpms9;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

/**
 * Created by CH-E01449 on 06-12-2017.
 */

@Dao
public interface  ContactDao {

    @Insert
    void insert(Contact contact);

//    @Delete
//    void delete(int id);

//    @Update
//    boolean updateContact(Contact contact);

    @Query("DELETE FROM contacts")
    void deleteAll();

    @Query("SELECT * from contacts")
    LiveData<List<Contact>> getAllContacts();
}

