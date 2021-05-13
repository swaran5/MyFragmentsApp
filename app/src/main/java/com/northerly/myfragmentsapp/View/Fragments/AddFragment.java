package com.northerly.myfragmentsapp.View.Fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.northerly.myfragmentsapp.Model.Endpoints;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.Model.PojoClass.Root;
import com.northerly.myfragmentsapp.Model.ServiceBuilder;
import com.northerly.myfragmentsapp.Model.Support.AESUtils;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.Dialog.BottomSheet;
import com.northerly.myfragmentsapp.View.Helper.AlertReciever;
import com.northerly.myfragmentsapp.View.Helper.NotiicationHelper;
import com.northerly.myfragmentsapp.View.MainActivity;
import com.northerly.myfragmentsapp.ViewModel.AddUserViewModel;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class AddFragment extends Fragment {
    MyDataSet myDataSet;
    RelativeLayout relativeLayoutAddUser;
    String name;
    String job;
    SharedPreferences sp;
    NotiicationHelper mNotiicationHelper;
    Context context;

    public AddFragment(Context context){
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_user,container,false);

        EditText editTextName = v.findViewById(R.id.editTextName);
        EditText editTextJob = v.findViewById(R.id.editTextJob);
        Button addButton = v.findViewById(R.id.add_button);
        FloatingActionButton floatingActionButton = v.findViewById(R.id.floatingActionButton);
        TextView textName = v.findViewById(R.id.addTextViewName);
        TextView textJob = v.findViewById(R.id.addTextViewJob);
        TextView textId = v.findViewById(R.id.addTextViewId);
        TextView textCreatedOn = v.findViewById(R.id.addTextViewCreatedOn);
        relativeLayoutAddUser = getActivity().findViewById(R.id.relativeLayout);
        sp = getActivity().getSharedPreferences("User_Details", Context.MODE_PRIVATE);
        AESUtils aesUtils = new AESUtils();
        mNotiicationHelper = new NotiicationHelper(getActivity());

        if(sp != null){
            try {
                String dec1 = aesUtils.decrypt(sp.getString("name",""));
                String dec2 = aesUtils.decrypt(sp.getString("job",""));
                editTextName.setText(dec1);
                editTextJob.setText(dec2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AddUserViewModel addUserViewModel = ViewModelProviders.of(getActivity()).get(AddUserViewModel.class);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getActivity().getSupportFragmentManager(), "Bottom Sheet");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
         @RequiresApi(api = Build.VERSION_CODES.M)
         @Override
         public void onClick(View v) {
             if(isConnected()) {
                 name = String.valueOf(editTextName.getText());
                 job = String.valueOf(editTextJob.getText());
                 String enc1 = null;
                 String enc2 = null;

                 try {
                      enc1 = aesUtils.encrypt(name);
                      enc2 = aesUtils.encrypt(job);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }

                 SharedPreferences.Editor editor = sp.edit();
                 editor.putString("name", enc1);
                 editor.putString("job", enc2);
                 editor.commit();

                 createTimePicker().show();

//                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                     sendOnChannel1(enc1, enc2);
//                 }

                 myDataSet = new MyDataSet(name, job);
                 addUserViewModel.postUser(myDataSet);
                 new MainActivity().snackBarOnine(relativeLayoutAddUser);

             }
             else {
                 new MainActivity().snackBarOffline(relativeLayoutAddUser);
             }
         }
     });

        addUserViewModel.postedUser.observe(getActivity(), new Observer<MyDataSet>() {
            @Override
            public void onChanged(MyDataSet myDataSet) {
                textName.setText("Name : "+myDataSet.getName());
                textJob.setText("Job : "+myDataSet.getJob());
                textId.setText("Id : "+myDataSet.getId());
                textCreatedOn.setText("Created On : "+myDataSet.getCreatedAt());

            }
        });

   return v; }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb = mNotiicationHelper.getChannel1Notification(title, message);
        mNotiicationHelper.getManager().notify(1, nb.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(long c){

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReciever.class);
        intent.putExtra("name", name);
        intent.putExtra("job", job);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c , pendingIntent);
    }
    public TimePickerDialog createTimePicker(){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);

                        SharedPreferences shrdPref = getActivity().getSharedPreferences("sharePref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefEditor = shrdPref.edit();

                        long millis = c.getTimeInMillis();
                        prefEditor.putLong("calender", millis);
                        prefEditor.putString("name", name);
                        prefEditor.putString("job", job);
                        prefEditor.apply();

                        setAlarm(millis);
                    }
                },
                hour,
                minutes,
                DateFormat.is24HourFormat(getActivity()));
    }
}
