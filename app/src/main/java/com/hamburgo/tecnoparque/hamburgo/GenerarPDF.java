package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.hamburgo.tecnoparque.hamburgo.DTO.EmpresaDTO;
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

/**
 * Created by Admin_Sena on 03/11/2016.
 */

public class GenerarPDF {

    Context cnt;
    VentaDTO venta;

    public GenerarPDF(Context context, VentaDTO venta) {
        this.venta=venta;
        cnt=context;
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

        try {
            // Creamos el fichero con el nombre que deseemos.
            File f = crearFichero(venta.getNumeroVenta().toString() + ".pdf");

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
            documento.add(new Paragraph("Título 1"));

            // Añadimos un título con una fuente personalizada.
            Font font = FontFactory.getFont(FontFactory.HELVETICA,28,Font.BOLD, BaseColor.BLACK);

            documento.add(new Paragraph("Título personalizado", font));

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicación.
            Bitmap bitmap = BitmapFactory.decodeResource(cnt.getResources(),
                    R.drawable.logo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            documento.add(imagen);
            Log.e("Sadainer","Enttra");
            // Insertamos una tabla.
            PdfPTable tabla = new PdfPTable(5);
            for (int i = 0; i < 15; i++) {
                tabla.addCell("Celda " + i);
            }
            documento.add(tabla);

            // Agregar marca de agua
            font = FontFactory.getFont(FontFactory.HELVETICA,28,Font.BOLD, BaseColor.BLACK);
            ColumnText.showTextAligned(writer.getDirectContentUnder(),
                    Element.ALIGN_CENTER, new Paragraph(
                            "amatellanes.wordpress.com", font), 297.5f, 421,
                    writer.getPageNumber() % 2 == 1 ? 45 : -45);

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
