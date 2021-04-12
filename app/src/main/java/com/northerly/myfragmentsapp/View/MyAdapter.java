package com.northerly.myfragmentsapp.View;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northerly.myfragmentsapp.Model.PojoClass.Data;
import com.northerly.myfragmentsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.northerly.myfragmentsapp.R.layout.item_view;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(int id);
    }
    private OnItemClickListener mListener;

    public void setOnItemClickListener( OnItemClickListener listener){
            mListener = listener;
    }

    public  Context context;
    public List<Data> users;
    public static Data user;

    public MyAdapter(Context context , List<Data> listdata) {
        this.users = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listitem = layoutInflater.inflate(R.layout.item_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        user = users.get(position);
        holder.setData(context ,user,mListener);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstname;
        public TextView lastname;
        public ImageView imgView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            this.firstname = itemView.findViewById(R.id.firstname);
            this.lastname = itemView.findViewById(R.id.lastname);
            this.imgView = itemView.findViewById(R.id.avatar);

        }

        public void setData(Context holdercontext , Data user, OnItemClickListener listener) {

            String firstName = user.getFirst_name();
            String lastName = user.getLast_name();
            String imgUrl = user.getAvatar();
            int id = user.getId();

            Picasso.get().load(imgUrl).into(imgView);
            firstname.setText("First Name : " +firstName);
            lastname.setText("Last Name : " +lastName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(id);

                        }
                    }
                }
            });

        }
    }
}
