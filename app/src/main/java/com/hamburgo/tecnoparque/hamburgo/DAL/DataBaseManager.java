package com.hamburgo.tecnoparque.hamburgo.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;

import java.util.ArrayList;

public class DataBaseManager {
    private AdminSQLiteOpenHelper helper;
    private  SQLiteDatabase db;
    //----------------------------------TABLA 1----------------------------------------------
    public static  final String TABLA_1="Clientes"; // Nombre de la tabla



    public static  final String CAMPO_1="Cedula";
    public static  final String CAMPO_2="Nombres";
    public static  final String CAMPO_3="Apellidos";
    public static  final String CAMPO_4="Direccion";
    public static  final String CAMPO_5="Empleo";
    public static  final String CAMPO_6="Empresa";
    public static  final String CAMPO_7="Celular";

    public static  final String CREATE_TABLE_1 = "create table " + TABLA_1 + " ("
            + CAMPO_1 + " text primary key not null, "
            + CAMPO_2 + " text not null, "
            + CAMPO_3 + " text not null, "
            + CAMPO_4 + " text,"
            + CAMPO_5 + " text,"
            + CAMPO_6 + " text,"
            + CAMPO_7 + " text not null);" ; //integer
//------------------------------------Tabla 2---------------------------------------------

    public DataBaseManager(Context context) {
         helper = new AdminSQLiteOpenHelper(context);
         db = helper.getWritableDatabase();
    }


    private ContentValues GenerarContentValues(ClienteDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(CAMPO_1, m.getCedula());
        valores.put(CAMPO_2, m.getNombres());
        valores.put(CAMPO_3, m.getApellidos());
        valores.put(CAMPO_4, m.getDireccion());
        valores.put(CAMPO_5, m.getEmpleo());
        valores.put(CAMPO_6, m.getEmpresa());
        valores.put(CAMPO_7, m.getCelular());
        return valores;
    }

    public void Insertar(ClienteDTO m) {
            db.insert(TABLA_1, null, GenerarContentValues(m));
    }
    public void Actualizar (ClienteDTO c, String cedula){
        ContentValues valores = new ContentValues();
        valores.put(CAMPO_1,c.getCedula());
        valores.put(CAMPO_2,c.getNombres());
        valores.put(CAMPO_3,c.getApellidos());
        valores.put(CAMPO_4,c.getDireccion());
        valores.put(CAMPO_5,c.getEmpleo());
        valores.put(CAMPO_6,c.getEmpresa());
        valores.put(CAMPO_7,c.getCelular());
        db.update(TABLA_1, valores,CAMPO_1 +  " = " + cedula, null);
    }

    public void Eliminar (String cedula){
        db.delete(TABLA_1,CAMPO_1 +  " = " + cedula, null);
    }

    public ClienteDTO getUsuario(String cedula){

        ClienteDTO m = null;
        Cursor c = db.rawQuery(" SELECT " + CAMPO_1 + " , "  + CAMPO_2 + " , "+ CAMPO_3 + " , "+ CAMPO_4 + " , " + CAMPO_5 + " , "
                + CAMPO_6 + " , "+ CAMPO_7 + " FROM " + TABLA_1 + " where " + CAMPO_1 + " = " + cedula, null);
        if (c.moveToFirst()) {
            m = new ClienteDTO();
            m.setCedula(c.getString(0));
            m.setNombres(c.getString(1));
            m.setApellidos(c.getString(2));
            m.setDireccion(c.getString(3));
            m.setEmpleo(c.getString(4));
            m.setEmpresa(c.getString(5));
            m.setCelular(c.getString(6));
        }
        return m;
    }

    public ArrayList<ClienteDTO> getListaClientes(){
        Cursor c = db.rawQuery(" SELECT " + CAMPO_1 + " , "  + CAMPO_2 + " , "+ CAMPO_3 + " , "+ CAMPO_4 + " , " + CAMPO_5 + " , "
                + CAMPO_6 + " , "+ CAMPO_7 + " FROM " + TABLA_1, null);
        ArrayList<ClienteDTO> Lista = new ArrayList<ClienteDTO>();
        while (c.moveToNext()){
            ClienteDTO m = new ClienteDTO();
            m.setCedula(c.getString(0));
            m.setNombres(c.getString(1));
            m.setApellidos(c.getString(2));
            m.setDireccion(c.getString(3));
            m.setEmpleo(c.getString(4));
            m.setEmpresa(c.getString(5));
            m.setCelular(c.getString(6));
            Lista.add(m);
        }
        return Lista;
    }

    public void deleteTodo() {
        db.execSQL("DELETE FROM " + TABLA_1);
    }

}
