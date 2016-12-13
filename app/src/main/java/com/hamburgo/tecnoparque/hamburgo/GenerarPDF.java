package com.hamburgo.tecnoparque.hamburgo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.hamburgo.tecnoparque.hamburgo.DAL.DataBaseManager;
import com.hamburgo.tecnoparque.hamburgo.DTO.ClienteDTO;
import com.hamburgo.tecnoparque.hamburgo.DTO.CuotasDTO;
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
import com.itextpdf.text.pdf.PdfPCell;
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
    ArrayList<CuotasDTO> cuotasVenta;
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
       cuotasVenta = manager.getCuotasVenta(venta.getNumeroVenta());

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
            documento.add(new Paragraph("Fecha: " + venta.getFecha()));
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
            Paragraph preface = new Paragraph("Venta N° " + venta.getNumeroVenta(), fontTitulo);
            preface.setAlignment(Element.ALIGN_CENTER);
            documento.add(preface);

            // Insertamos una imagen que se encuentra en los recursos de la
            // aplicación.
//            Bitmap bitmap = BitmapFactory.decodeResource(cnt.getResources(),
//                    R.drawable.logo);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            Image imagen = Image.getInstance(stream.toByteArray());
//            documento.add(imagen);
            documento.add(new Paragraph());
            documento.add(new Paragraph());
            documento.add(new Paragraph());
            documento.add(new Paragraph());
            documento.add(new Paragraph());
            documento.add(new Paragraph());

            // Insertamos una tabla.
            PdfPTable tabla = new PdfPTable(4);
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.BLUE);
            cell.setPhrase(new Phrase("Nombre"));
            tabla.addCell(cell);
            cell.setPhrase(new Phrase("Precio"));
            tabla.addCell(cell);
            cell.setPhrase(new Phrase("Cantidad"));
            tabla.addCell(cell);
            cell.setPhrase(new Phrase("Valor Total"));
            tabla.addCell(cell);

            for (int i = 0; i < detalleVenta.size() ; i++) {
                tabla.addCell(detalleVenta.get(i).getNombre());
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorderColor(BaseColor.BLUE);
                cell.setPhrase(new Phrase(detalleVenta.get(i).getPrecio().toString()));
                tabla.addCell(cell);
                cell.setPhrase(new Phrase(detalleVenta.get(i).getTipo()));
                tabla.addCell(cell);
                Integer Valor = detalleVenta.get(i).getPrecio() * Integer.valueOf(detalleVenta.get(i).getTipo());
                cell.setPhrase(new Phrase(String.valueOf(Valor)));
                tabla.addCell(cell);

            }
            documento.add(tabla);
            documento.add(new Paragraph());
            Paragraph Vtotal = new Paragraph("Total " + venta.getValorVenta(), fontTitulo);
            Vtotal.setAlignment(Element.ALIGN_RIGHT);
            documento.add(Vtotal);


            documento.add(new Paragraph());
            documento.add(new Paragraph());


            Paragraph prefaceCuota = new Paragraph("Cuotas", fontTitulo);
            prefaceCuota.setAlignment(Element.ALIGN_CENTER);
            documento.add(prefaceCuota);


            documento.add(new Paragraph());
            documento.add(new Paragraph());


            PdfPTable tablaCuota = new PdfPTable(4);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPhrase(new Phrase("Fecha"));
            tablaCuota.addCell(cell);
            cell.setPhrase(new Phrase("Valor"));
            tablaCuota.addCell(cell);
            cell.setPhrase(new Phrase("Valor Pagado"));
            tablaCuota.addCell(cell);
            cell.setPhrase(new Phrase("Cancelado"));
            tablaCuota.addCell(cell);

            for (int i = 0; i < cuotasVenta.size() ; i++) {
                tablaCuota.addCell(cuotasVenta.get(i).getFechaPago());
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorderColor(BaseColor.BLUE);
                cell.setPhrase(new Phrase(cuotasVenta.get(i).getValorCuota().toString()));
                tablaCuota.addCell(cell);
                cell.setPhrase(new Phrase(cuotasVenta.get(i).getValorDeuda().toString()));
                tablaCuota.addCell(cell);
                cell.setPhrase(new Phrase(cuotasVenta.get(i).getPagada().toString()));
                tablaCuota.addCell(cell);

            }
            documento.add(tablaCuota);
//            documento.add(new Paragraph());
//            Paragraph CInicial = new Paragraph("Cuota Inicial ");
//            CInicial.setAlignment(Element.ALIGN_LEFT);
//            documento.add(Vtotal);



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
            Log.e("Sadainer","Funciooooooona");

            // Cerramos el documento.
            documento.close();

            sendEmail();
        }
    }

    protected void sendEmail() {
        Log.e("Sadainer", "Send email");
        String[] TO = {cliente.getEmail().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        String filename="VentaHamburgo.pdf";

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        boolean isPresent = true;
        if (!docsFolder.exists()) {
            isPresent = docsFolder.mkdir();
        }

        if (isPresent) {
            File filelocation = new File(Environment.getExternalStorageDirectory(), NOMBRE_DIRECTORIO + "/" + filename);
            Uri path = Uri.fromFile(filelocation);

            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Compra Hamburgo Sale");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Gracias por su compra, Adjunto encontrará el documento con el detalle de la compra y fechas de pago");
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);

            try {
                cnt.startActivity(Intent.createChooser(emailIntent, "Enviando Correo..."));

                Log.e("v", "Finished sending email...");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(cnt, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                Log.e("Sadainer", ex.getMessage().toString());
            }
        }
    }
}
