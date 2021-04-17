package com.northerly.myfragmentsapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.R;

import java.util.List;

public class MyDBAdapter extends RecyclerView.Adapter<MyDBAdapter.ViewHolder> {
    List<User> users;
    User user;
    Context context;
    public MyDBAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listitem = layoutInflater.inflate(R.layout.item_view2,parent,false);
        MyDBAdapter.ViewHolder viewHolder = new MyDBAdapter.ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        user = users.get(position);
        holder.setData(context ,user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstname;
        public TextView lastname;
        public TextView email;
        public TextView phone;
        public TextView brand;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.firstname = itemView.findViewById(R.id.db_firstname);
            this.lastname = itemView.findViewById(R.id.db_lastname);
            this.email = itemView.findViewById(R.id.db_email);
            this.phone = itemView.findViewById(R.id.db_phonenum);
            this.brand = itemView.findViewById(R.id.db_brand);

        }

        void setData(Context context, User user){
            firstname.setText(user.getFirstName());
            lastname.setText(user.getLastName());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            brand.setText(user.getBrand());

        }
    }


    }