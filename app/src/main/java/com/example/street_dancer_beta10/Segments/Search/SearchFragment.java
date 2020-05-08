package com.example.street_dancer_beta10.Segments.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.street_dancer_beta10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    EditText search_edit_text;
    RecyclerView recyclerView,recyclerView1,recyclerView2;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> username_list;
    SearchAdapter searchAdapter;
    int counter = 0;
    String searchdata;

    public List<Search_items_list> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(SearchFragment.this,username_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(searchAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference  = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       /* recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        username_list = new ArrayList<>();
        search_edit_text = (EditText) view.findViewById(R.id.search_bar);
        Log.d(TAG, "onViewCreated: before");
        //searchdata = search_edit_text.getText().toString();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: aftertextchange");
                if(!editable.toString().isEmpty()){

                    //recyclerView.removeAllViews();
                    Log.d(TAG, "afterTextChanged: text");
                    setAdapter(editable.toString());

                }else
                {
                    Log.d(TAG, "afterTextChanged: else part");
                    username_list.clear();
                    recyclerView.removeAllViews();
                }
                Log.d(TAG, "afterTextChanged: change");
            }
        });


    }

    public void setAdapter (final String searchedString)  {

        Log.d(TAG, "setAdapter: before data change");
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: data changed");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: data changed");
                    String uid = snapshot.getKey();

                    String muser_name = snapshot.child("user_name").getValue(String.class);
                    if (muser_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        username_list.add(muser_name);
                        counter++;
                    }
                    if (counter == 15)
                        break;
                }
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                searchAdapter = new SearchAdapter(SearchFragment.this, username_list);
                recyclerView.setAdapter(searchAdapter);
                searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        username_list.get(position);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: cancel");
            }
        });
    }


}




