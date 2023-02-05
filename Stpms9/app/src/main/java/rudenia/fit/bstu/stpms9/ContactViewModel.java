package rudenia.fit.bstu.stpms9;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;



import java.util.List;



public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;
    private LiveData<List<Contact>> mContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mContacts = mRepository.getAllContacts();
    }

    LiveData<List<Contact>> getAllContacts() {
        return mContacts;
    }

    void insert(Contact contact) {
        mRepository.insert(contact);
    }

}

