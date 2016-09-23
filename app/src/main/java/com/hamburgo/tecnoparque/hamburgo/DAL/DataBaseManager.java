package com.hamburgo.tecnoparque.hamburgo.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.hamburgo.tecnoparque.hamburgo.DTO.CarteraDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.DetalleVentaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;
import com.hamburgo.tecnoparque.hamburgo.Formatos;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataBaseManager {
    private AdminSQLiteOpenHelper helper;
    private  SQLiteDatabase db;
    Formatos format = new Formatos();
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
            + TABLA_1_CAMPO_4 + " text, "
            + TABLA_1_CAMPO_5 + " text, "
            + TABLA_1_CAMPO_6 + " text, "
            + TABLA_1_CAMPO_7 + " text not null); " ;

//------------------------------------Tabla 2---------------------------------------------
public static  final String TABLA_2="Productos"; // Nombre de la tabla

    public static  final String TABLA_2_CAMPO_1="Nombre";
    public static  final String TABLA_2_CAMPO_2="Tipo";
    public static  final String TABLA_2_CAMPO_3="Precio";
    public static  final String TABLA_2_CAMPO_4="Descripcion";

    public static  final String CREATE_TABLE_2 = "create table " + TABLA_2 + " ("
            + TABLA_2_CAMPO_1 + " text primary key not null, "
            + TABLA_2_CAMPO_2 + " text not null, "
            + TABLA_2_CAMPO_3 + " text, "
            + TABLA_2_CAMPO_4 + " text); " ; //integer

    //----------------------------------TABLA 3----------------------------------------------
    public static  final String TABLA_3="Ventas"; // Nombre de la tabla

    public static  final String TABLA_3_CAMPO_1="NumeroVenta";
    public static  final String TABLA_3_CAMPO_2="Fecha";
    public static  final String TABLA_3_CAMPO_3="IdCliente";
    public static  final String TABLA_3_CAMPO_4="IdVenderor";
    public static  final String TABLA_3_CAMPO_5="ValorVenta";
    public static  final String TABLA_3_CAMPO_6="NumeroCuotas";
    public static  final String TABLA_3_CAMPO_7="Observacion";

    public static  final String CREATE_TABLE_3 = "create table " + TABLA_3 + " ("
            + TABLA_3_CAMPO_1 + " integer primary key autoincrement, "
            + TABLA_3_CAMPO_2 + " text not null, "
            + TABLA_3_CAMPO_3 + " text not null, "
            + TABLA_3_CAMPO_4 + " text not null, "
            + TABLA_3_CAMPO_5 + " text not null, "
            + TABLA_3_CAMPO_6 + " integer not null, "
            + TABLA_3_CAMPO_7 + " text, "
            + "FOREIGN KEY(" + TABLA_3_CAMPO_3 + ") REFERENCES " + TABLA_1 + "(" + TABLA_1_CAMPO_1 + "))" ;
    //----------------------------------TABLA 1----------------------------------------------
    public static  final String TABLA_4="DetalleVenta"; // Nombre de la tabla

    public static  final String TABLA_4_CAMPO_1="NumeroDetalleVenta";
    public static  final String TABLA_4_CAMPO_2="NumeroVenta";
    public static  final String TABLA_4_CAMPO_3="Nombre";
    public static  final String TABLA_4_CAMPO_4="Valor";
    public static  final String TABLA_4_CAMPO_5="Cantidad";
    public static  final String TABLA_4_CAMPO_6="ValorTotal";

    public static  final String CREATE_TABLE_4 = "create table " + TABLA_4 + " ("
            + TABLA_4_CAMPO_1 + " integer primary key autoincrement, "
            + TABLA_4_CAMPO_2 + " integer not null, "
            + TABLA_4_CAMPO_3 + " text not null, "
            + TABLA_4_CAMPO_4 + " text not null, "
            + TABLA_4_CAMPO_5 + " text not null, "
            + TABLA_4_CAMPO_6 + " text not null); "
            + "FOREIGN KEY(" + TABLA_4_CAMPO_2 + ") REFERENCES " + TABLA_3 + "(" + TABLA_3_CAMPO_1 + "))" ;
    //----------------------------------TABLA 1----------------------------------------------
    public static  final String TABLA_5="Cartera"; // Nombre de la tabla

    public static  final String TABLA_5_CAMPO_1="NumeroCartera";
    public static  final String TABLA_5_CAMPO_2="Fecha";
    public static  final String TABLA_5_CAMPO_3="IdCliente";
    public static  final String TABLA_5_CAMPO_4="IdVendedor";
    public static  final String TABLA_5_CAMPO_5="Valor";
    public static  final String TABLA_5_CAMPO_6="Observacion";

    public static  final String CREATE_TABLE_5 = "create table " + TABLA_5 + " ("
            + TABLA_5_CAMPO_1 + " integer primary key autoincrement, "
            + TABLA_5_CAMPO_2 + " integer not null, "
            + TABLA_5_CAMPO_3 + " text not null, "
            + TABLA_5_CAMPO_4 + " text not null, "
            + TABLA_5_CAMPO_5 + " text not null, "
            + TABLA_5_CAMPO_6 + " text); " ;
    //----------------------------------TABLA 1----------------------------------------------
    public static  final String TABLA_6="Cuotas"; // Nombre de la tabla

    public static  final String TABLA_6_CAMPO_1="NumeroCuota";
    public static  final String TABLA_6_CAMPO_2="NumeroVenta";
    public static  final String TABLA_6_CAMPO_3="FechaPago";
    public static  final String TABLA_6_CAMPO_4="ValorCuota";
    public static  final String TABLA_6_CAMPO_5="ValorDeuda";
    public static  final String TABLA_6_CAMPO_6="Cancelado";

    public static  final String CREATE_TABLE_6 = "create table " + TABLA_6 + " ("
            + TABLA_6_CAMPO_1 + " integer primary key autoincrement, "
            + TABLA_6_CAMPO_2 + " integer not null, "
            + TABLA_6_CAMPO_3 + " text not null, "
            + TABLA_6_CAMPO_4 + " integer not null, "
            + TABLA_6_CAMPO_5 + " integer not null, "
            + TABLA_6_CAMPO_6 + " integer);" ;


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
    ////////////////////////////////////////////////////////////////

    private ContentValues GenerarContentValuesVenta(VentaDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(TABLA_3_CAMPO_1, m.getNumeroVenta());
        valores.put(TABLA_3_CAMPO_2, m.getFecha());
        valores.put(TABLA_3_CAMPO_3, m.getIdCliente());
        valores.put(TABLA_3_CAMPO_4, m.getIdVenderor());
        valores.put(TABLA_3_CAMPO_5, m.getValorVenta());
        valores.put(TABLA_3_CAMPO_6, m.getNumeroCuotas());
        valores.put(TABLA_3_CAMPO_7, m.getObservacion());
        return valores;
    }
    private ContentValues GenerarContentValuesDetalleVenta(DetalleVentaDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(TABLA_4_CAMPO_1, m.getNumeroDetalleVenta());
        valores.put(TABLA_4_CAMPO_2, m.getNumeroVenta());
        valores.put(TABLA_4_CAMPO_3, m.getNombre());
        valores.put(TABLA_4_CAMPO_4, m.getValor());
        valores.put(TABLA_4_CAMPO_5, m.getCantidad());
        valores.put(TABLA_4_CAMPO_6, m.getValorTotal());
        return valores;
    }

    private void InsertarDetalleVenta(DetalleVentaDTO m) {
            db.insert(TABLA_4, null, GenerarContentValuesDetalleVenta(m));
    }

    private Integer MaxVenta(){
        Cursor c = db.rawQuery(" SELECT Max(" + TABLA_3_CAMPO_1 + ") FROM " + TABLA_3, null);
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return null;
    }
    private boolean InsertarVenta(VentaDTO m) {

        try {
            db.insert(TABLA_3, null, GenerarContentValuesVenta(m));
        }catch (SQLiteException e){
           return false;
        }finally {
            return true;
        }

    }


    public VentaDTO UltimaVenta(){

        VentaDTO m = null;
        Cursor c = db.rawQuery(" SELECT " + TABLA_3_CAMPO_1 + " , "  + TABLA_3_CAMPO_2 + " , "+ TABLA_3_CAMPO_3 + " , "
                + TABLA_3_CAMPO_4 + " , "+ TABLA_3_CAMPO_5 + " , "+ TABLA_3_CAMPO_6 + " , "+ TABLA_3_CAMPO_7
                + " FROM " + TABLA_3 + " order by " + TABLA_3_CAMPO_1 + " desc limit 1" , null);
        Log.e("Sadainer","14");
        if (c.moveToFirst()) {

            Log.e("Sadainer","15");
            m = new VentaDTO();
            m.setNumeroVenta(c.getInt(0));
            m.setFecha(c.getString(1));
            m.setIdCliente(c.getString(2));
            m.setIdVenderor(c.getString(3));
            m.setValorVenta(c.getInt(4));
            m.setNumeroCuotas(c.getInt(5));
            m.setObservacion(c.getString(6));
        }

        return m;
    }

    public VentaDTO RegistrarVenta (VentaDTO venta, ArrayList<DetalleVentaDTO> detalle, String CInicial, Boolean Mensual){


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fechaCompleta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mesFormat = new SimpleDateFormat("MM");
        SimpleDateFormat diaFormat = new SimpleDateFormat("dd");

        String Fecha = fechaCompleta.format(calendar.getTime());
        Integer year = Integer.valueOf(yearFormat.format(calendar.getTime()));
        Integer mes = Integer.valueOf(mesFormat.format(calendar.getTime()));
        Integer dia = Integer.valueOf(diaFormat.format(calendar.getTime()));


        Integer CuotaInicial = Integer.valueOf(CInicial);
        Integer ValorCuotas = (venta.getValorVenta() - CuotaInicial)/venta.getNumeroCuotas();

        venta.setFecha(Fecha);

        if (InsertarVenta(venta)){
            try {

                Integer max = MaxVenta();
                for (DetalleVentaDTO d : detalle) {
                    d.setNumeroVenta(max);
                    InsertarDetalleVenta(d);
                }

                CuotasDTO cuotaInicialPago = new CuotasDTO();
                cuotaInicialPago.setFechaPago(Fecha);
                cuotaInicialPago.setNumeroVenta(max);
                cuotaInicialPago.setPagada(1);
                cuotaInicialPago.setValorDeuda(0);
                cuotaInicialPago.setValorCuota(CuotaInicial);
                InsertarCuotas(cuotaInicialPago);

                for (int i=0 ; i< venta.getNumeroCuotas(); i++){

                    CuotasDTO cuota = new CuotasDTO();
                    cuota.setFechaPago(year.toString()+ "-" + format.MesString(mes) + "-30");

                    cuota.setNumeroVenta(max);
                    cuota.setPagada(0);
                    cuota.setValorDeuda(ValorCuotas);
                    cuota.setValorCuota(ValorCuotas);
                    InsertarCuotas(cuota);
                    mes++;
                    if (mes == 13) {
                        mes = 1;
                        year++;
                    }
                }

                CarteraDTO cartera = new CarteraDTO();
                cartera.setIdCliente(venta.getIdCliente());
                cartera.setFecha(venta.getFecha());
                cartera.setIdVendedor("1065582510");
                cartera.setValor(CuotaInicial);
                cartera.setObservacion("Cuota Inicial");
                InsertarCartera(cartera);
            }catch (Exception e){
                return null;
            }finally {

                return UltimaVenta();
            }

        }else{
            return null;
        }
    }
    public ArrayList<VentaDTO> getListadoVentas (){
        Cursor c = db.rawQuery(" SELECT " + TABLA_3_CAMPO_1 + " , "  + TABLA_3_CAMPO_2 + " , "+ TABLA_3_CAMPO_3 + " , "
                + TABLA_3_CAMPO_4 + " , "+ TABLA_3_CAMPO_5 + " , "+ TABLA_3_CAMPO_6 + " , "+ TABLA_3_CAMPO_7
                + " FROM " + TABLA_3 + " order by " + TABLA_3_CAMPO_1 + " desc" , null);
        ArrayList<VentaDTO> Lista = new ArrayList<VentaDTO>();
        while (c.moveToNext()){
            VentaDTO m = new VentaDTO();
            m.setNumeroVenta(c.getInt(0));
            m.setFecha(c.getString(1));
            m.setIdCliente(c.getString(2));
            m.setIdVenderor(c.getString(3));
            m.setValorVenta(c.getInt(4));
            m.setNumeroCuotas(c.getInt(5));
            m.setObservacion(c.getString(6));

            Lista.add(m);
        }
        return Lista;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<ProductoDTO> getDetalleVenta(Integer NumeroVenta){
        Cursor c = db.rawQuery("SELECT " + TABLA_4_CAMPO_1 + " , "  + TABLA_4_CAMPO_2 + " , "+ TABLA_4_CAMPO_3 + " , "
                + TABLA_4_CAMPO_4 + " , " + TABLA_4_CAMPO_5 + " , " + TABLA_4_CAMPO_6
                + " FROM " + TABLA_4 + " WHERE " + TABLA_4_CAMPO_2 + " = " + NumeroVenta, null);

        ArrayList<ProductoDTO> Lista = new ArrayList<ProductoDTO>();
        while (c.moveToNext()){
            Log.e("Sadainer",c.getString(2));
            ProductoDTO m = new ProductoDTO();
            m.setNombre(c.getString(2));
            m.setPrecio(c.getInt(3));
            m.setTipo(c.getString(4));
            Lista.add(m);
        }
        return Lista;
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<ClienteDTO> getCartera() {
//        Cursor c = db.rawQuery(" SELECT "  + TABLA_3 + "." + TABLA_3_CAMPO_3 + "," + TABLA_6 + "." + TABLA_6_CAMPO_2
//                + " , sum(" + TABLA_6 + "." + TABLA_6_CAMPO_5 + ") FROM " + TABLA_6 + " INNER JOIN "
//                + TABLA_3 + " ON " + TABLA_3 + "." + TABLA_3_CAMPO_1 + " = " + TABLA_6 + "." + TABLA_6_CAMPO_2 + " group by "
//                + TABLA_3 + "." + TABLA_3_CAMPO_3 , null);

        Cursor c = db.rawQuery("SELECT " + TABLA_3 + "." + TABLA_3_CAMPO_3
                + ", SUM(" + TABLA_6 + "." + TABLA_6_CAMPO_5 + ")"
                + " FROM " + TABLA_6 + " INNER JOIN " + TABLA_3 + " ON " + TABLA_6 + "." + TABLA_6_CAMPO_2  + " = " + TABLA_3 + "." + TABLA_3_CAMPO_1
                + " GROUP BY " + TABLA_3 + "." + TABLA_3_CAMPO_3 + " ORDER BY " + TABLA_3 + "." + TABLA_3_CAMPO_3 , null);


        ArrayList<ClienteDTO> Lista = new ArrayList<ClienteDTO>();

        while (c.moveToNext()) {
            ClienteDTO m = getUsuario(c.getString(0));
            m.setCelular(c.getString(1));
            Lista.add(m);
        }
        return Lista;
    }

    public ArrayList<ClienteDTO> getPagosCartera() {
        Cursor c = db.rawQuery(" SELECT " + TABLA_5_CAMPO_3 + " , sum(" + TABLA_5_CAMPO_5
                + ") FROM " + TABLA_5 + " group by " + TABLA_5_CAMPO_3 , null);
        ArrayList<ClienteDTO> Lista = new ArrayList<ClienteDTO>();
        while (c.moveToNext()) {
            ClienteDTO m = getUsuario(c.getString(0));
            m.setCelular(c.getString(1));
            Lista.add(m);
        }
        return Lista;
    }

    public void InsertarCartera(CarteraDTO m) {
        db.insert(TABLA_5, null, GenerarContentValuesCartera(m));
    }

    private ContentValues GenerarContentValuesCartera(CarteraDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(TABLA_5_CAMPO_1, m.getNumeroCartera());
        valores.put(TABLA_5_CAMPO_2, m.getFecha());
        valores.put(TABLA_5_CAMPO_3, m.getIdCliente());
        valores.put(TABLA_5_CAMPO_4, m.getIdVendedor());
        valores.put(TABLA_5_CAMPO_5, m.getValor());
        valores.put(TABLA_5_CAMPO_6, m.getObservacion());
        return valores;
    }

    //////////////////////////////////////////////////////////
    public void InsertarCuotas(CuotasDTO m) {
        db.insert(TABLA_6, null, GenerarContentValuesCuotas(m));
    }

    private ContentValues GenerarContentValuesCuotas(CuotasDTO m) {
        ContentValues valores = new ContentValues();
        valores.put(TABLA_6_CAMPO_1, m.getNumeroCuota());
        valores.put(TABLA_6_CAMPO_2, m.getNumeroVenta());
        valores.put(TABLA_6_CAMPO_3, m.getFechaPago());
        valores.put(TABLA_6_CAMPO_4, m.getValorCuota());
        valores.put(TABLA_6_CAMPO_5, m.getValorDeuda());
        valores.put(TABLA_6_CAMPO_6, m.getPagada());
        return valores;
    }

    public ArrayList<CuotasDTO> getCuotasVenta(Integer NumeroVenta){
        Cursor c = db.rawQuery("SELECT " + TABLA_6_CAMPO_1 + " , "  + TABLA_6_CAMPO_2 + " , "+ TABLA_6_CAMPO_3 + " , "
                + TABLA_6_CAMPO_4 + " , " + TABLA_6_CAMPO_5 + " , " + TABLA_6_CAMPO_6
                + " FROM " + TABLA_6 + " WHERE " + TABLA_6_CAMPO_2 + " = " + NumeroVenta, null);

        ArrayList<CuotasDTO> Lista = new ArrayList<CuotasDTO>();
        while (c.moveToNext()){
            Log.e("Sadainer","2");

            CuotasDTO m = new CuotasDTO();
            m.setNumeroCuota(c.getInt(0));
            m.setNumeroVenta(c.getInt(1));
            m.setFechaPago(c.getString(2));
            m.setValorCuota(c.getInt(3));
            m.setValorDeuda(c.getInt(4));
            m.setPagada(c.getInt(5));
            Lista.add(m);
        }
        return Lista;
    }
    public CuotasDTO PagarCuotasVenta(ClienteDTO cliente, Integer ValorPagado){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fechaCompleta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Fecha = fechaCompleta.format(calendar.getTime());

        CarteraDTO cartera = new CarteraDTO();
        cartera.setIdCliente(cliente.getCedula());
        cartera.setIdVendedor("1065582510");
        cartera.setValor(ValorPagado);
        cartera.setFecha(Fecha);
        cartera.setObservacion("Nunguna");




        Cursor c = db.rawQuery("SELECT " + TABLA_6_CAMPO_1 + " , "  + TABLA_6_CAMPO_2 + " , "+ TABLA_6_CAMPO_3 + " , "
                + TABLA_6_CAMPO_4 + " , " + TABLA_6_CAMPO_5 + " , " + TABLA_6_CAMPO_6
                + " FROM " + TABLA_6 + " WHERE " + TABLA_6_CAMPO_2 + " = " + NumeroVenta, null);

        ArrayList<CuotasDTO> Lista = new ArrayList<CuotasDTO>();
        while (c.moveToNext()){
            Log.e("Sadainer","2");

            CuotasDTO m = new CuotasDTO();
            m.setNumeroCuota(c.getInt(0));
            m.setNumeroVenta(c.getInt(1));
            m.setFechaPago(c.getString(2));
            m.setValorCuota(c.getInt(3));
            m.setValorDeuda(c.getInt(4));
            m.setPagada(c.getInt(5));
            Lista.add(m);
        }
        return Lista;
    }

    public ArrayList<CuotasDTO> getCuotasCartera(String Cedula){

        Cursor c = db.rawQuery("SELECT " + TABLA_6 + "." + TABLA_6_CAMPO_1 + " , "  + TABLA_6 + "." + TABLA_6_CAMPO_2 + " , "+ TABLA_6 + "." + TABLA_6_CAMPO_3
                + ",SUM(" + TABLA_6 + "." + TABLA_6_CAMPO_4 + "), SUM(" + TABLA_6 + "." + TABLA_6_CAMPO_5 + "), " + TABLA_6 + "." + TABLA_6_CAMPO_6
                + " FROM " + TABLA_6 + " INNER JOIN " + TABLA_3 + " ON " + TABLA_6 + "." + TABLA_6_CAMPO_2  + " = " + TABLA_3 + "." + TABLA_3_CAMPO_1
                + " WHERE " + TABLA_3_CAMPO_3 + " = " + Cedula + " AND " + TABLA_6 + "." + TABLA_6_CAMPO_5 + " > 0 "
                + " GROUP BY " + TABLA_6 + "." + TABLA_6_CAMPO_3 + " ORDER BY " + TABLA_6 + "." + TABLA_6_CAMPO_3 , null);

        ArrayList<CuotasDTO> Lista = new ArrayList<CuotasDTO>();
        while (c.moveToNext()){
            Log.e("Sadainer","2");

            CuotasDTO m = new CuotasDTO();
            m.setNumeroCuota(c.getInt(0));
            m.setNumeroVenta(c.getInt(1));
            m.setFechaPago(c.getString(2));
            m.setValorCuota(c.getInt(3));
            m.setValorDeuda(c.getInt(4));
            m.setPagada(c.getInt(5));
            Lista.add(m);
        }
        return Lista;
    }


}
