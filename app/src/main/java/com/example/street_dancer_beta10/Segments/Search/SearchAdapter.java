package com.example.street_dancer_beta10.Segments.Search;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.street_dancer_beta10.R;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> username_list= new ArrayList<String>();

    private OnItemClickListener mListener;

    public SearchAdapter(Context context, ArrayList<String> username_list, List<Search_items_list> data) {
        this.context = context;
        this.username_list = username_list;

    }

    public SearchAdapter(SearchFragment searchFragment, ArrayList<String> username_list) {
    }


    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_search_item, parent, false);
        return new SearchAdapter.SearchViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.username.setText(username_list.get(position));
    }

    @Override
    public int getItemCount() {
        return username_list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView username;

        public SearchViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
//                            Intent intent = new Intent(view.getContext(), test_activity.class);
//                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}



