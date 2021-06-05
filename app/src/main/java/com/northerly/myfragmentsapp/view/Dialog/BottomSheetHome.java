package com.northerly.myfragmentsapp.view.Dialog;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.Model.SQliteDB.DBHelper;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.view.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class BottomSheetHome extends BottomSheetDialogFragment {
    List<String> brands;
    List<User> alluserlist;
    public String brand;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    int k;
    String line;
    DBHelper DB;
    String filename;
    String filepath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_home,container, false);

        RelativeLayout relativeLayoutAddUser = getActivity().findViewById(R.id.relativeLayout);
        firstName = v.findViewById(R.id.firstnameBottomSheethome);
        lastName = v.findViewById(R.id.lastnameBottomSheethome);
        email = v.findViewById(R.id.emailBottomSheethome);
        phone = v.findViewById(R.id.phonenumBottomSheethome);
        filename = "myFile.txt";
        filepath = "MyFileDir";
        Spinner spinner = v.findViewById(R.id.spinnerhome);
        Button addButton = v.findViewById(R.id.addBottomSheethome);
        DB = new DBHelper(getActivity());
        brands = new ArrayList<>();
        brands.add("Select a brand");
        brands.add("Suzuki");
        brands.add("Honda");
        brands.add("Tata");
        brands.add("Hyundai");
        brands.add("Chevrolet");
        brands.add("Mahendra");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,brands);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        brand = "brand";
                        break;
                    case 1:
                        brand = "Suzuki";
                        break;
                    case 2:
                        brand = "Honda";
                        break;
                    case 3:
                        brand = "Tata";
                        break;
                    case 4:
                        brand = "Hyundai";
                        break;
                    case 5:
                        brand = "Chevrolet";
                        break;
                    case 6:
                        brand = "Mahendra";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FileReader fr = null;
        File myExternalReadFile = new File(getActivity().getExternalFilesDir(filepath),filename);
        try {
            fr = new FileReader(myExternalReadFile);
            BufferedReader br = new BufferedReader(fr);
            line = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            firstName.setText(line);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm:ss");

            String date = simpleDate.format(calendar.getTime());
            String time = simpleTime.format(calendar.getTime());
            @Override
            public void onClick(View v) {

                ValidateEmail();
                ValidateName(firstName);
                ValidateName(lastName);
                ValidatePhonenum();
                if(!brand.equals("brand") &&
                        ValidatePhonenum() &&
                        ValidateEmail() &&
                        ValidateName(firstName) &&
                        ValidateName(lastName)
                )
                {
                    if (isConnected()) {
                        String name = String.valueOf(firstName.getText());
                        String job = String.valueOf(lastName.getText());
                        String emails = String.valueOf(email.getText());
                        String phones = String.valueOf(phone.getText());

                        Boolean updatecheck = DB.updateuserdata(name, job, emails, phones, brand);

                        if(updatecheck == true){
                            Toast.makeText(getActivity(),"user added..", Toast.LENGTH_LONG);
                        }
                        else {
                            DB.insertuserdata(name, job, emails, phones, brand);
                            Toast.makeText(getActivity(),"user not added..", Toast.LENGTH_LONG);
                        }

                        new MainActivity().snackBarOnine(relativeLayoutAddUser);
                        dismiss();

                        File myExternalFile = new File(getActivity().getExternalFilesDir(filepath), filename);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myExternalFile);
                            fos.write(name.getBytes());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        new MainActivity().snackBarOffline(relativeLayoutAddUser);
                    }
                }
                if(brand.equals("brand")) {
                    Toast.makeText(getActivity(),"Select a Brand..",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;}
    private Boolean ValidateEmail() {
        String inputemail = email.getText().toString().trim();
        if(inputemail.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()){
            email.setError("Enter a valid email");
            return false;
        }
        else{
            return true;
        }
    }

    private Boolean ValidateName(EditText inputName){
        String input = inputName.getText().toString().trim();
        String regex = "^[a-zA-Z]+$";
        if(input.isEmpty()){
            inputName.setError("Field can't be empty");
            return false;
        }
        else if(!input.matches(regex)){
            inputName.setError("Enter a valid Name");
            return false;
        }
        else{
            return true;
        }
    }

    private Boolean ValidatePhonenum(){
        String number = phone.getText().toString();
        if(number.isEmpty()){
            phone.setError("Field can't be empty");
            return false;
        }
        else if(!Pattern.compile("[0-9]{10}").matcher(number).matches()){
            phone.setError("Enter a valid Number");
            return false;
        }
        else{
            return true;
        }
    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
