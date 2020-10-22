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
    private float salto = 0, bajo = 0;


    private Boolean elJump = false;
    private Boolean elRight = false;
    private Boolean elLeft = false;

    private Boolean tope = false;




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

        left.setOnTouchListener(this);
        right.setOnTouchListener(this);
        shot.setOnTouchListener(this);
        mySuper.setOnTouchListener(this);

        jump.setOnClickListener(
                (view) -> {
                    Log.e("UPPPP", "PRESIONADO");
                    elJump = true;
                    Gson gsonU = new Gson();
                    Gson gsonD = new Gson();
                    new Thread(
                            ()->{
                                while(elJump == true){
                                    if(salto >= 0 && tope == false){
                                        salto += 0.5;
                                        posY -= salto;
                                        bajo = 0;
                                        if(salto == 11){
                                            tope = true;
                                        }
                                        CoorAnimal jumps = new CoorAnimal(posX, posY);
                                        String jsonU = gsonU.toJson(jumps);
                                        tcp.sendMessage(jsonU);
                                    }
                                    if(tope == true){
                                        bajo += 0.5;
                                        salto -= 0.5;
                                        posY += bajo;

                                        if(salto == 0){
                                            tope = false;
                                            elJump = false;
                                        }
                                        CoorAnimal jumps = new CoorAnimal(posX, posY);
                                        String jsonD = gsonD.toJson(jumps);
                                        tcp.sendMessage(jsonD);
                                    }

                                    try {
                                        Thread.sleep(30);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                    ).start();
                }
        );
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:


                switch (view.getId()) {

                        ///////////SI UNDE IZQUIERDA//////////////
                    case R.id.left:
                        Log.e("LEFT", "PRESIONADO");
                        elLeft = true;
                        Gson gsonL = new Gson();
                        new Thread(
                                () -> {
                                    while (elLeft==true) {
                                        if (posX >= 0) {
                                            posX -= 0.1;

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
                                        if (posX <= 1030) {
                                            posX += 0.1;

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