package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.DetalleVentaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.EmpresaDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.ProductoDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.VentaDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Header;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin_Sena on 03/11/2016.
 */

public class GenerarPDF {

    Context cnt;
    VentaDTO venta;
    ClienteDTO cliente;
    ArrayList<ProductoDTO> detalleVenta;
    DataBaseManager manager;

    public GenerarPDF(Context context, VentaDTO venta) {
        this.venta=venta;
        cnt=context;
        manager = new DataBaseManager(cnt);
    }

    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    private final static String ETIQUETA_ERROR = "ERROR";


    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        Log.e("Ruta",ruta.toString());
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
    }

    public void CrearPDF(){

        Document documento = new Document();
        EmpresaDTO empresa = new EmpresaDTO();
        SharedPreferences preferencias = cnt.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        empresa.setCedula(preferencias.getString("cedula", "0"));
        empresa.setEmpresa(preferencias.getString("empresa", ""));
        empresa.setNombres(preferencias.getString("nombre", ""));
        empresa.setApellidos(preferencias.getString("apellido", ""));
        empresa.setEmail(preferencias.getString("email", ""));
        empresa.setCelular(preferencias.getString("celular", ""));

        cliente = manager.getUsuario(venta.getIdCliente());
        detalleVenta = manager.getDetalleVenta(venta.getNumeroVenta());

        try {
            // Creamos el fichero con el nombre que deseemos.
            File f = crearFichero("VentaHamburgo.pdf");
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA,18,Font.BOLD, BaseColor.BLACK);
            Font fontTituloGrande = FontFactory.getFont(FontFactory.HELVETICA,24,Font.BOLD, BaseColor.BLACK);
            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Incluimos el píe de página y una cabecera


            // Abrimos el documento.
            documento.open();

            // Añadimos un título con la fuente por defecto.
            Paragraph titulo = new Paragraph("Detalle Venta",fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph( empresa.getEmpresa(),fontTitulo));
            documento.add(new Paragraph("Propietario: " + empresa.getNombres() + " " + empresa.getApellidos()));
            documento.add(new Paragraph("Dirección: " + empresa.getDireccion()));
            documento.add(new Paragraph("Celular: " + empresa.getCelular()));
            documento.add(new Paragraph("E-Mail: " + empresa.getEmail()));

            documento.add(new Paragraph());
            documento.add(new Paragraph("Datos del cliente",fontTitulo));
            documento.add(new Paragraph("Cédula: " + cliente.getCedula()));
            documento.add(new Paragraph("Nombres: " + cliente.getNombres() + " " + cliente.getApellidos()));
            documento.add(new Paragraph("E-Mail: " + cliente.getEmail()));
            documento.add(new Paragraph("Celular: " + cliente.getCelular()));

            // Añadimos un título con una fuente personalizada.
            documento.add(new Paragraph());
            documento.add(new Paragraph("Venta N° " + venta.getNumeroVenta(), fontTitulo));

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicación.
//            Bitmap bitmap = BitmapFactory.decodeResource(cnt.getResources(),
//                    R.drawable.logo);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            Image imagen = Image.getInstance(stream.toByteArray());
//            documento.add(imagen);
            documento.add(new Paragraph());

            // Insertamos una tabla.
            PdfPTable tabla = new PdfPTable(detalleVenta.size() + 1);

            for (int i = 0; i < detalleVenta.size() + 1 ; i++) {
                tabla.addCell(detalleVenta.get(i).getNombre());
                tabla.addCell(detalleVenta.get(i).getPrecio().toString());
                tabla.addCell(detalleVenta.get(i).getTipo());
            }
            documento.add(tabla);

            // Agregar marca de agua
//            font = FontFactory.getFont(FontFactory.HELVETICA,28,Font.BOLD, BaseColor.BLACK);
//            ColumnText.showTextAligned(writer.getDirectContentUnder(),
//                    Element.ALIGN_CENTER, new Paragraph(
//                            "amatellanes.wordpress.com", font), 297.5f, 421,
//                    writer.getPageNumber() % 2 == 1 ? 45 : -45);

        } catch (DocumentException e) {
            Log.e("Sadainer","Enttra");
            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {
            Log.e("Sadainer","Enttra");
            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {
            Log.e("Sadainer","Funciona");

            // Cerramos el documento.
            documento.close();
        }
    }
}
