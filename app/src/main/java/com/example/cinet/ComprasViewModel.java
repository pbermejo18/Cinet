package com.example.cinet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ComprasViewModel extends AndroidViewModel {
    MutableLiveData<Compra> compraSeleccionada = new MutableLiveData<>();

    public ComprasViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(Compra elemento){
        compraSeleccionada.setValue(elemento);
    }

    MutableLiveData<Compra> seleccionado(){
        return compraSeleccionada;
    }
}
