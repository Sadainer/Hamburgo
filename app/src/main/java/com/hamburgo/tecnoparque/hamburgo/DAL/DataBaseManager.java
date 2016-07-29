package com.hamburgo.tecnoparque.hamburgo.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;

import java.util.ArrayList;

public class DataBaseManager {
    private AdminSQLiteOpenHelper helper;
    private  SQLiteDatabase db;
    //----------------------------------TABLA 1----------------------------------------------
    public static  final String TABLA_1="Clientes"; // Nombre de la tabla

    public static  final String TABLA_1_CAMPO_1="Cedula";
    public static  final String TABLA_1_CAMPO_2="Nombres";
    public static  final String TABLA_1_CAMPO_3="Apellidos";
    public static  final String TABLA_1_CAMPO_4="Direccion";
    public static  final String TABLA_1_CAMPO_5="Empleo";
    public static  final String TABLA_1_CAMPO_6="Empresa";
    public static  final String TABLA_1_CAMPO_7="Celular";

    public static  final String CREATE_TABLE_1 = "create table " + TABLA_1 + " ("
            + TABLA_1_CAMPO_1 + " text primary key not null, "
            + TABLA_1_CAMPO_2 + " text not null, "
            + TABLA_1_CAMPO_3 + " text not null, "
            + TABLA_1_CAMPO_4 + " text,"
            + TABLA_1_CAMPO_5 + " text,"
            + TABLA_1_CAMPO_6 + " text,"
            + TABLA_1_CAMPO_7 + " text not null);" ; //integer
//------------------------------------Tabla 2---------------------------------------------
public static  final String TABLA_2="Productos"; // Nombre de la tabla

    public static  final String TABLA_2_CAMPO_1="Nombre";
    public static  final String TABLA_2_CAMPO_2="Tipo";
    public static  final String TABLA_2_CAMPO_3="Precio";
    public static  final String TABLA_2_CAMPO_4="Descripcion";

    public static  final String CREATE_TABLE_2 = "create table " + TABLA_2 + " ("
            + TABLA_2_CAMPO_1 + " text not null, "
            + TABLA_2_CAMPO_2 + " text not null, "
            + TABLA_2_CAMPO_3 + " text, "
            + TABLA_2_CAMPO_4 + " text);" ; //integer





    public DataBaseManager(Context context) {
         helper = new AdminSQLiteOpenHelper(context);
         db = helper.getWritableDatabase();
    }


    private ContentValues GenerarContentValues(ClienteDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(TABLA_1_CAMPO_1, m.getCedula());
        valores.put(TABLA_1_CAMPO_2, m.getNombres());
        valores.put(TABLA_1_CAMPO_3, m.getApellidos());
        valores.put(TABLA_1_CAMPO_4, m.getDireccion());
        valores.put(TABLA_1_CAMPO_5, m.getEmpleo());
        valores.put(TABLA_1_CAMPO_6, m.getEmpresa());
        valores.put(TABLA_1_CAMPO_7, m.getCelular());
        return valores;
    }

    public void Insertar(ClienteDTO m) {
            db.insert(TABLA_1, null, GenerarContentValues(m));
    }
    public void Actualizar (ClienteDTO c, String cedula){
        ContentValues valores = new ContentValues();
        valores.put(TABLA_1_CAMPO_1,c.getCedula());
        valores.put(TABLA_1_CAMPO_2,c.getNombres());
        valores.put(TABLA_1_CAMPO_3,c.getApellidos());
        valores.put(TABLA_1_CAMPO_4,c.getDireccion());
        valores.put(TABLA_1_CAMPO_5,c.getEmpleo());
        valores.put(TABLA_1_CAMPO_6,c.getEmpresa());
        valores.put(TABLA_1_CAMPO_7,c.getCelular());
        db.update(TABLA_1, valores,TABLA_1_CAMPO_1 +  " = " + cedula, null);
    }

    public void Eliminar (String cedula){
        db.delete(TABLA_1,TABLA_1_CAMPO_1 +  " = " + cedula, null);
    }

    public ClienteDTO getUsuario(String cedula){

        ClienteDTO m = null;
        Cursor c = db.rawQuery(" SELECT " + TABLA_1_CAMPO_1 + " , "  + TABLA_1_CAMPO_2 + " , "+ TABLA_1_CAMPO_3 + " , "
                + TABLA_1_CAMPO_4 + " , " + TABLA_1_CAMPO_5 + " , " + TABLA_1_CAMPO_6 + " , "+ TABLA_1_CAMPO_7
                + " FROM " + TABLA_1 + " where " + TABLA_1_CAMPO_1 + " = " + cedula, null);
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
        Cursor c = db.rawQuery(" SELECT " + TABLA_1_CAMPO_1 + " , "  + TABLA_1_CAMPO_2 + " , "+ TABLA_1_CAMPO_3 + " , "
                + TABLA_1_CAMPO_4 + " , " + TABLA_1_CAMPO_5 + " , " + TABLA_1_CAMPO_6 + " , "+ TABLA_1_CAMPO_7
                + " FROM " + TABLA_1, null);
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

    ///////////////////////////////////////////////////////////////////////////////////


    private ContentValues GenerarContentValuesProductos(ProductoDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(TABLA_2_CAMPO_1, m.getNombre());
        valores.put(TABLA_2_CAMPO_2, m.getTipo());
        valores.put(TABLA_2_CAMPO_3, m.getPrecio());
        valores.put(TABLA_2_CAMPO_4, m.getDescripcion());
        return valores;
    }

    public void InsertarProductos(ProductoDTO m) {
        db.insert(TABLA_2, null, GenerarContentValuesProductos(m));
    }
    public void ActualizarProductos (ProductoDTO m, String nombre){
        ContentValues valores = new ContentValues();
        valores.put(TABLA_2_CAMPO_1, m.getNombre());
        valores.put(TABLA_2_CAMPO_2, m.getTipo());
        valores.put(TABLA_2_CAMPO_3, m.getPrecio());
        valores.put(TABLA_2_CAMPO_4, m.getDescripcion());
        db.update(TABLA_2, valores,TABLA_2_CAMPO_1 +  " = " + nombre, null);
    }

    public void EliminarProductos (String nombre){
        db.delete(TABLA_2,TABLA_2_CAMPO_1 +  " = " + nombre, null);
    }

    public ProductoDTO getProducto(String nombre){

        ProductoDTO m = null;
        Cursor c = db.rawQuery(" SELECT " + TABLA_2_CAMPO_1 + " , "  + TABLA_2_CAMPO_2 + " , "+ TABLA_2_CAMPO_3 + " , "
                + TABLA_2_CAMPO_4
                + " FROM " + TABLA_2 + " where " + TABLA_2_CAMPO_1 + " = '" + nombre + "'" , null);
        if (c.moveToFirst()) {
            m = new ProductoDTO();
            m.setNombre(c.getString(0));
            m.setTipo(c.getString(1));
            m.setPrecio(c.getInt(2));
            m.setDescripcion(c.getString(3));
        }
        return m;
    }

    public ArrayList<ProductoDTO> getListaProductos(){
        Cursor c = db.rawQuery(" SELECT " + TABLA_2_CAMPO_1 + " , "  + TABLA_2_CAMPO_2 + " , "+ TABLA_2_CAMPO_3 + " , "
                + TABLA_2_CAMPO_4
                + " FROM " + TABLA_2, null);
        ArrayList<ProductoDTO> Lista = new ArrayList<ProductoDTO>();
        while (c.moveToNext()){
            ProductoDTO m = new ProductoDTO();
            m = new ProductoDTO();
            m.setNombre(c.getString(0));
            m.setTipo(c.getString(1));
            m.setPrecio(c.getInt(2));
            m.setDescripcion(c.getString(3));
            Lista.add(m);
        }
        return Lista;
    }

    public void deleteTodoProductos() {
        db.execSQL("DELETE FROM " + TABLA_2);
    }
}
