package com.example.cinet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class PeliculasViewModel extends AndroidViewModel {
    MutableLiveData<List<Pelicula>> listElementosMutableLiveData = new MutableLiveData<>();

    MutableLiveData<Post> elementoSeleccionado = new MutableLiveData<>();

    public PeliculasViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(Post elemento){
        elementoSeleccionado.setValue(elemento);
    }

    MutableLiveData<Post> seleccionado(){
        return elementoSeleccionado;
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
