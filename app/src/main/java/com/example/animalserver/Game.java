package com.example.animalserver;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MicrophoneInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.animalserver.modelo.CoorAnimal;
import com.google.gson.Gson;

public class Game extends AppCompatActivity implements View.OnTouchListener, OnMessageListener {

    private Button jump;
    private Button left;
    private Button right;
    private Button shot;
    private Button mySuper;
    private TCPSingleton tcp;

    private float posX = 50;
    private float posY = 350;

    private Boolean elJump = false;
    private Boolean elRight = false;
    private Boolean elLeft = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        jump = findViewById(R.id.jump);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        shot = findViewById(R.id.shot);
        mySuper = findViewById(R.id.mySuper);

        tcp = TCPSingleton.getInstance();
        tcp.setObserver(this);


        jump.setOnTouchListener(this);
        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        shot.setOnTouchListener(this);
        mySuper.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:


                switch (view.getId()) {

                    ///////SI UNDE BOTON DE SALTAR////////////
                    case R.id.jump:


                        break;

                        ///////////SI UNDE IZQUIERDA//////////////
                    case R.id.left:
                        Log.e("LEFT", "PRESIONADO");
                        elLeft = true;
                        Gson gsonL = new Gson();
                        new Thread(
                                () -> {
                                    while (elLeft==true) {
                                        if (posX >= 0) {
                                            posX -= 0.2;

                                            CoorAnimal jumps = new CoorAnimal(posX, posY);
                                            String jsonL = gsonL.toJson(jumps);
                                            tcp.sendMessage(jsonL);
                                        }
                                    }
                                }
                        ).start();
                        break;

                        //////////SI UNDE DERECHA///////////
                    case R.id.right:
                        Log.e("RIGHT", "PRESIONADO");
                        elRight = true;
                        Gson gsonR = new Gson();

                        new Thread(
                                () -> {

                                    while (elRight == true) {
                                        if (posX <= 1040) {
                                            posX += 0.2;

                                            CoorAnimal jumps = new CoorAnimal(posX, posY);
                                            String jsonR = gsonR.toJson(jumps);
                                            tcp.sendMessage(jsonR);
                                        }
                                    }
                                }
                        ).start();
                        break;

            /*case R.id.shot:

                gson = new Gson ();
                CoorAnimal shots = new CoorAnimal("shot");
                json = gson.toJson(shots);
                tcp.sendMessage(json);
                break;*/

                }
                break;

                case MotionEvent.ACTION_UP:
                    switch (view.getId()){
                        ////////si el boton soltado es el boton de saltar////////
                        case R.id.jump:
                            elJump = false;
                            Log.e("UP", "SOLTADO");
                            break;

                        ////////si el boton soltado es el boton de izquierda////////
                        case R.id.left:
                            elLeft = false;
                            Log.e("UP", "SOLTADO");
                            break;

                        ////////si el boton soltado es el boton de la derecha////////
                        case R.id.right:
                            elRight = false;
                            Log.e("UP", "SOLTADO");
                            break;
                    }
                    break;
        }
        return true;
    }



    @Override
    public void cuandoLlegueElMensaje(String msg) {

    }
}