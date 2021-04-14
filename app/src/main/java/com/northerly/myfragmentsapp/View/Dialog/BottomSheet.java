package com.northerly.myfragmentsapp.View.Dialog;

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
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.MainActivity;
import com.northerly.myfragmentsapp.ViewModel.AddUserViewModel;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class BottomSheet extends BottomSheetDialogFragment {
    List<String> brands;
    public String brand;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet,container, false);

        AddUserViewModel addUserViewModel = ViewModelProviders.of(getActivity()).get(AddUserViewModel.class);
        RelativeLayout relativeLayoutAddUser = getActivity().findViewById(R.id.relativeLayout);

        firstName = v.findViewById(R.id.firstnameBottomSheet);
         lastName = v.findViewById(R.id.lastnameBottomSheet);
         email = v.findViewById(R.id.emailBottomSheet);
         phone = v.findViewById(R.id.phonenumBottomSheet);

        Spinner spinner = v.findViewById(R.id.spinner);
        Button addButton = v.findViewById(R.id.addBottomSheet);
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

                        MyDataSet myDataSet = new MyDataSet(name, job);
                        addUserViewModel.postUser(myDataSet);
                        new MainActivity().snackBarOnine(relativeLayoutAddUser);
                        dismiss();
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