package com.example.animalserver;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class TCPSingleton extends Thread{

    private String codigo;
    private OnMessageListener observer;


    //constructor privado
    private TCPSingleton(){}


    //unica instancia de TcpSingleton
    private static TCPSingleton unicaInstancia;

    //metodo estático que devuelve la unica instancia
    public static TCPSingleton getInstance(){
        if(unicaInstancia == null){
            unicaInstancia = new TCPSingleton();
            //unicaInstancia.start();
        }
        return unicaInstancia;
    }

    private Socket socket;

    public void setObserver (OnMessageListener observer) {
        this.observer = observer;
    }


    public void run() {
        try {

            //conexion

            while(true) {
                System.out.println("esperando conexion");

                socket = new Socket (codigo, 5000);


            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void getIP (String myIp) {
        codigo = myIp;
        Log.d(">>>>>>>>>", ""+codigo);
    }
}
