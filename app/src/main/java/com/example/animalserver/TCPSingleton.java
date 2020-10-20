package com.example.animalserver;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    //variables globales tcp
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public void setObserver (OnMessageListener observer) {
        this.observer = observer;
    }


    public void run() {
        try {
            //conexion
            socket = new Socket (codigo, 5000);
            Log.d("cliente conectado", "yei");

            //emisor
            OutputStream os = socket.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(os));

            //receptor
            InputStream is = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));

            while(true){
                //recibe constantemente
                String line = reader.readLine();
                observer.cuandoLlegueElMensaje(line);
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
