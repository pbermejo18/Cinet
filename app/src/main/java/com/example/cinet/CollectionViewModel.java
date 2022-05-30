package com.example.cinet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CollectionViewModel extends AndroidViewModel {
    MutableLiveData<String> colecion_seleccionado = new MutableLiveData<>();

    public CollectionViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(String collection){ colecion_seleccionado.setValue(collection); }

    MutableLiveData<String> colecion_seleccionada(){ return colecion_seleccionado; }
}
