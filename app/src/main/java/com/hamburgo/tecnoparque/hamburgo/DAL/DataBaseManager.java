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
            + CAMPO_1 + " integer primary key not null, "
            + CAMPO_2 + " text not null, "
            + CAMPO_3 + " text not null, "
            + CAMPO_4 + " text,"
            + CAMPO_5 + " text,"
            + CAMPO_6 + " text,"
            + CAMPO_7 + " integer not null);" ; //integer
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

    public ClienteDTO getUsuario(){

        ClienteDTO m = new ClienteDTO();
        Cursor c = db.rawQuery(" SELECT " + CAMPO_1 + " , "  + CAMPO_2 + " , "+ CAMPO_3 + " , "+ CAMPO_4 + " , " + CAMPO_5 + " , "
                + CAMPO_6 + " , "+ CAMPO_7 + " FROM " + TABLA_1, null);
        if (c.moveToFirst()) {
            m.setCedula(c.getInt(0));
            m.setNombres(c.getString(1));
            m.setApellidos(c.getString(2));
            m.setDireccion(c.getString(3));
            m.setEmpleo(c.getString(4));
            m.setEmpresa(c.getString(5));
            m.setCelular(c.getInt(6));
        }
        return m;
    }

    public ArrayList<ClienteDTO> getListaClientes(){
        Cursor c = db.rawQuery(" SELECT " + CAMPO_1 + " , "  + CAMPO_2 + " , "+ CAMPO_3 + " , "+ CAMPO_4 + " , " + CAMPO_5 + " , "
                + CAMPO_6 + " , "+ CAMPO_7 + " FROM " + TABLA_1, null);
        ArrayList<ClienteDTO> Lista = new ArrayList<ClienteDTO>();
        while (c.moveToNext()){
            ClienteDTO m = new ClienteDTO();
            m.setCedula(c.getInt(0));
            m.setNombres(c.getString(1));
            m.setApellidos(c.getString(2));
            m.setDireccion(c.getString(3));
            m.setEmpleo(c.getString(4));
            m.setEmpresa(c.getString(5));
            m.setCelular(c.getInt(6));
            Lista.add(m);
        }
        return Lista;
    }

    public void deleteTodo() {
        db.execSQL("DELETE FROM " + TABLA_1);
    }

}
