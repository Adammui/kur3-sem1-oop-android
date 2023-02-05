package rudenia.fit.bstu.oop5;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;

public class fragment_list extends Fragment {
    private ArrayAdapter<User> adapter;
    private List<User> users;
    ListView listView;
    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView =  view.findViewById(R.id.countriesList);
        users = JSONHelper.importFromJSON(getActivity());
        if(users!=null){
            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
            Toast.makeText(getActivity(), "Data restored", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Not opened files", Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                String selectedItem = (String)parent.getItemAtPosition(position).toString();
                // Посылаем данные Activity
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
        return view;
    }

}