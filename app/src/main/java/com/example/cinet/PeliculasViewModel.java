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

    public PeliculasViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(Post elemento, String id_doc){
        elementoSeleccionado.setValue(elemento);
        id_seleccionado.setValue(id_doc);
    }

    MutableLiveData<Post> seleccionado(){
        return elementoSeleccionado;
    }
    MutableLiveData<String> id_doc_seleccionado(){
        System.out.println(id_seleccionado.getValue());return id_seleccionado;
    }
/*
    public PeliculasViewModel(@NonNull Application application) {
        super(application);

        elementosRepositorio = new ElementosRepositorio();

        listElementosMutableLiveData.setValue(elementosRepositorio.obtener());
    }

    MutableLiveData<List<Pelicula>> obtener(){
        return listElementosMutableLiveData;
    }

    void seleccionar(Pelicula elemento){
        elementoSeleccionado.setValue(elemento);
    }

    MutableLiveData<Pelicula> seleccionado(){
        return elementoSeleccionado;
    }

 */
}
