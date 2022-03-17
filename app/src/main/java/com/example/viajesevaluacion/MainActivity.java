package com.example.viajesevaluacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText jetvalor,jetcodigo,jetcantida,jetdestino;
    Button jbtnguardar,jbtnconsultar,jbtncancelar,jbtnanular;
    long resp;
    int V;
    String Codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        jetcantida=findViewById(R.id.etCantida);
        jetcodigo=findViewById(R.id.etcodigo);
        jetdestino=findViewById(R.id.etdestino);
        jetvalor=findViewById(R.id.etvalor);
        jbtnanular=findViewById(R.id.btnanularv);
        jbtncancelar=findViewById(R.id.btncancelarv);
        jbtnconsultar=findViewById(R.id.btncansultar);
        jbtnguardar=findViewById(R.id.btnguardar);
    }
    public void limpiar_campos(){
        V=0;
        jetcodigo.setText("");
        jetdestino.setText("");
        jetcantida.setText("");
        jetvalor.setText("");
        jetcodigo.requestFocus();
    }
    public void Guardar(View view){
        String  destino, Cantida,Valor;
        Codigo=jetcodigo.getText().toString();
        destino=jetdestino.getText().toString();
        Cantida=jetcantida.getText().toString();
        Valor=jetvalor.getText().toString();
        if (Codigo.isEmpty() || destino.isEmpty() || Cantida.isEmpty()
                || Valor.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
                Coneciones_Viajes admin=new Coneciones_Viajes (this,"Viajes.bd",null,1);
                SQLiteDatabase db=admin.getWritableDatabase();
                ContentValues dato=new ContentValues();
                dato.put("Codigo",Codigo );
                dato.put("destino",destino);
                dato.put("Cantida",Cantida);
                dato.put("Valor",Valor);
                if (V == 0)
                    resp=db.insert("TblViajes",null,dato);
                else{
                    V=0;
                    resp=db.update("TblViajes",dato,"Codigo='" + Codigo+ "'",null);
                }
                if (resp > 0){
                    Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                    limpiar_campos();
                }
                else{
                    Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }

    }
    public void Consultar(View view){
        Consultar_Viaje();
    }

    public void Consultar_Viaje(){

        Codigo=jetcodigo.getText().toString();
        if (Codigo.isEmpty()){
            Toast.makeText(this, "Identificacion requerida", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else {
            Coneciones_Viajes admin = new Coneciones_Viajes(this, "Viajes.bd", null, 1);
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblViajes where Codigo='" + Codigo + "'",null);
            if (fila.moveToNext()){
                V=1;
                jetdestino.setText(fila.getString(1));
                jetcantida.setText(fila.getString(2));
                jetvalor.setText(fila.getString(3));
                Toast.makeText(this, "Registrado encontrado", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Registro no existe", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }
   /* public void Anular(View view){
        Consultar_Viaje();
        if (V == 1){
            Coneciones_Viajes admin=new Coneciones_Viajes(this,"Viajes.bd",null,1);
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues dato=new ContentValues();
            dato.put("Codigo",Codigo);
            dato.put("activo",0);
            resp=db.update("TblViajes",dato,"Codigo='"+Codigo+"'",null);
            if (resp>0){
                Toast.makeText(this,"Registro eliminado",Toast.LENGTH_LONG).show();
                limpiar_campos();
            }else
            {
                Toast.makeText(this,"Error eliminando registro",Toast.LENGTH_LONG).show();
            }

        }
    }*/

    public void Cancelar(View view){

        limpiar_campos();
    }

}