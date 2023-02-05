package rudenia.fit.bstu.stpms9;


import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;



import java.util.List;



public class ContactRepository {

    public ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    public ContactRepository(Application application) {
        ContactDatabase db = ContactDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }


    public void insert (Contact contact) {
        new insertAsyncTask(mContactDao).execute(contact);
    }

    public LiveData<List<Contact>> getAllContacts(){
        return mAllContacts;
    }


    private class insertAsyncTask extends AsyncTask<Contact,Void,Void>{
        public insertAsyncTask(ContactDao mContactDao) {
        }


        @Override
        protected Void doInBackground(Contact... contacts) {
            mContactDao.insert(contacts[0]);
            return null;
        }
    }
}
