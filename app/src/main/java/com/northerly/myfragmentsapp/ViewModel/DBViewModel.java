package com.northerly.myfragmentsapp.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.northerly.myfragmentsapp.Model.RoomDB.User;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDao;
import com.northerly.myfragmentsapp.Model.RoomDB.UserDataBase;

import java.util.List;

public class DBViewModel extends AndroidViewModel {
    MutableLiveData<List<User>> user = new MutableLiveData<>();
    UserDataBase userDataBase = UserDataBase.getDataBase(getApplication());
    UserDao userDao = userDataBase.userDao();

    public DBViewModel(@NonNull Application application) {
        super(application);
    }

    public class GettAsync extends AsyncTask<Void, Void, List<User>>
    {

        @Override
        protected List<User> doInBackground(Void... voids) {
          return userDao.getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            user.postValue(users);
        }
    }
    public void getuser(){
        GettAsync gettAsync = new GettAsync();
        gettAsync.execute();
    }
    public MutableLiveData<List<User>> getUser()
    {
        return user;
    }
}
