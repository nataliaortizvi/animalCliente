package com.example.animalserver;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MicrophoneInfo;
import android.os.Bundle;
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

    private boolean pressJump = false;
    private boolean pressLeft = false;
    private boolean pressRight = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        jump = findViewById(R.id.jump);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        shot = findViewById(R.id.shot);
        mySuper = findViewById(R.id.mySuper);

        jump.setOnTouchListener(this);
        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        shot.setOnTouchListener(this);
        mySuper.setOnTouchListener(this);

        tcp.setObserver(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:


                switch (view.getId()) {

                    ///////SI UNDE BOTON DE SALTAR////////////
                    case R.id.jump:

                        pressJump = true;
                        Gson gson = new Gson();
                        new Thread(
                                () -> {

                                    CoorAnimal jumps = new CoorAnimal(posX, posY);
                                    String json = gson.toJson(jumps);
                                    tcp.sendMessage(json);
                                }
                        ).start();
                        break;

                        ///////////SI UNDE IZQUIERDA//////////////
                    case R.id.left:

                        pressLeft = true;
                        Gson gsonL = new Gson();
                        new Thread(
                                () -> {

                                    while (pressLeft) {
                                        if (posX >= 0) {
                                            posX -= 0.1;

                                            CoorAnimal jumps = new CoorAnimal(posX, posY);
                                            String json = gsonL.toJson(jumps);
                                            tcp.sendMessage(json);
                                        }
                                    }
                                }
                        ).start();
                        break;

                        //////////SI UNDE DERECHA///////////
                    case R.id.right:

                        pressRight = true;
                        Gson gsonR = new Gson();

                        new Thread(
                                () -> {

                                    while (pressLeft) {
                                        if (posX <= 1040) {
                                            posX += 0.1;

                                            CoorAnimal jumps = new CoorAnimal(posX, posY);
                                            String json = gsonR.toJson(jumps);
                                            tcp.sendMessage(json);
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
                            pressJump = false;
                            break;

                        ////////si el boton soltado es el boton de izquierda////////
                        case R.id.left:
                            pressLeft = false;
                            break;

                        ////////si el boton soltado es el boton de la derecha////////
                        case R.id.right:
                            pressRight = false;
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