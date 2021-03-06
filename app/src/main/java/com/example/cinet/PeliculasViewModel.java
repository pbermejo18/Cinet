package com.example.cinet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class PeliculasViewModel extends AndroidViewModel {
    MutableLiveData<Post> elementoSeleccionado = new MutableLiveData<>();
    MutableLiveData<String> id_seleccionado = new MutableLiveData<>();
    MutableLiveData<String> colecion_seleccionado = new MutableLiveData<>();

    public PeliculasViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(Post elemento, String id_doc, String colecion){
        elementoSeleccionado.setValue(elemento);
        id_seleccionado.setValue(id_doc);
        colecion_seleccionado.setValue(colecion);
    }

    MutableLiveData<Post> seleccionado(){
        return elementoSeleccionado;
    }
    MutableLiveData<String> id_doc_seleccionado(){
        System.out.println(id_seleccionado.getValue());return id_seleccionado;
    }
    MutableLiveData<String> colecion_seleccionada(){ return colecion_seleccionado; }

}
