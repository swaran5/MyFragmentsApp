package com.northerly.myfragmentsapp.View.Dialog;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfRenderer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.northerly.myfragmentsapp.Model.PojoClass.MyDataSet;
import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDao;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDataBase;
import com.northerly.myfragmentsapp.R;
import com.northerly.myfragmentsapp.View.MainActivity;
import com.northerly.myfragmentsapp.View.PDFViewerActivity;
import com.northerly.myfragmentsapp.ViewModel.AddUserViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class BottomSheet extends BottomSheetDialogFragment {
    List<String> brands;
    List<User> alluserlist;
    public String brand;
    private UserDao userDao;
    private UserDataBase userDB;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    Spinner spinner;
    int k;


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
         userDB = UserDataBase.getDataBase(getActivity().getApplicationContext());
         userDao = userDB.userDao();

         GetAsyncTask getuser = new GetAsyncTask();
         getuser.execute();

        spinner = v.findViewById(R.id.spinner);
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
                        String emails = String.valueOf(email.getText());
                        String phones = String.valueOf(phone.getText());

                        MyDataSet myDataSet = new MyDataSet(name, job);
                        addUserViewModel.postUser(myDataSet);
                        User user = new User(name , job, emails, phones, brand);

                        Boolean checkupdate = true;
                        for(int j= 0 ; j < alluserlist.size(); j++) {
                            k = j;
                            if (alluserlist.get(k).getPhone().equals(phones)) {
                                updateUser(user);
                                checkupdate = false;
                                try {
                                    createPdf(user);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(checkupdate){
                            insert(user);
                            try {
                                createPdf(user);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

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

        return v;
    }
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
    public void insert(User user){

    new InsertAsyncTask().execute(user);
    }

    private class InsertAsyncTask extends AsyncTask<User, Void ,Void>{

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    public class GetAsyncTask extends AsyncTask<Void, Void, List<User>>{

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }

    @Override
    protected void onPostExecute(List<User> users) {
            alluserlist = users;
            if(users.size() != 0) {
                int i = users.size() - 1;
                firstName.setText(users.get(i).getFirstName());
                lastName.setText(users.get(i).getLastName());
                email.setText(users.get(i).getEmail());
                phone.setText(users.get(i).getPhone());
                switch (users.get(i).getBrand()) {
                    case "Suzuki":
                        spinner.setSelection(1);
                        break;
                    case "Honda":
                        spinner.setSelection(2);
                        break;
                    case "Tata":
                        spinner.setSelection(3);
                        break;
                    case "Hyundai":
                        spinner.setSelection(4);
                        break;
                    case "Chevrolet":
                        spinner.setSelection(5);
                        break;
                    case "Mahendra":
                        spinner.setSelection(6);
                        break;
                }
            }
    }
}
    public void updateUser(User user){

        new UpdateAsyncTask().execute(user);
    }
    private class UpdateAsyncTask extends AsyncTask<User, Void, Void>
    {

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

    public void createPdf(User user) throws FileNotFoundException{
        File file = new File(getActivity().getExternalFilesDir("MyPDF"),"/TaxInvoice.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = new PdfWriter(file);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        float[] columnWidth = {100f, 100f};

        Paragraph paragraph = new Paragraph("hellooo....world"+ user.getBrand());

        document.add(paragraph);
        document.close();

        Intent intent = new Intent(getActivity(), PDFViewerActivity.class);
        startActivity(intent);

    }
}
