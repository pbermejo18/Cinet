package com.example.cinet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class EntradasHoraViewModel extends AndroidViewModel {
    MutableLiveData<String> hora = new MutableLiveData<>();
    MutableLiveData<String> nentradas = new MutableLiveData<>();

    public EntradasHoraViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(String hora, String nentradas){
        this.hora.setValue(hora);
        this.nentradas.setValue(nentradas);
    }

    MutableLiveData<String> hora_seleccionada(){
        return hora;
    }
    MutableLiveData<String> entradas_seleccionadas(){ return nentradas; }
}