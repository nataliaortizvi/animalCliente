package com.example.animalserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.animalserver.modelo.CoorAnimal;
import com.google.gson.Gson;

public class Seleccion extends AppCompatActivity implements OnMessageListener, View.OnClickListener {

    Button btnChicken, btnElephant, btnPig;
    private TCPSingleton tcp;

    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);

        btnPig = findViewById(R.id.btnPig);
        btnElephant = findViewById(R.id.btnElephant);
        btnChicken = findViewById(R.id.btnChicken);

        btnPig.setOnClickListener(this);
        btnElephant.setOnClickListener(this);
        btnChicken.setOnClickListener(this);


        tcp = TCPSingleton.getInstance();
        tcp.setObserver(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPig:
                tipo = "pig";
                break;
            case R.id.btnElephant:
                tipo = "elephant";
                break;
            case R.id.btnChicken:
                tipo = "chicken";
                break;
        }
        CoorAnimal coo = new CoorAnimal(50,320, tipo);
        Gson gson = new Gson();
        String json = gson.toJson(coo);
        tcp.sendMessage(json);

    }

    @Override
    public void cuandoLlegueElMensaje(String msg) {

    }
}