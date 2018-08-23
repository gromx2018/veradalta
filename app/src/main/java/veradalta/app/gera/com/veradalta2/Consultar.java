package veradalta.app.gera.com.veradalta2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Consultar extends AppCompatActivity {

    Spinner sp_reservacionescon;
    int idreservacionint;
    String [] items;
    String idreservacionscon;
    int idUsuarior;
    String fecha;
    String hora;
    String horafin;
    String espacio;

    Button salircon;
    TextView txtfecha;
    TextView txthora;
    TextView txthorafin;
    TextView txtespacio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        salircon=(Button)findViewById(R.id.btnSalircon);
        txtfecha=(TextView)findViewById(R.id.txtFechaCon);
        txthora=(TextView)findViewById(R.id.txtHoraInicioCon);
        txthorafin=(TextView)findViewById(R.id.txtHoraFinCon);
        txtespacio=(TextView)findViewById(R.id.txtEspacioCon);
        salircon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);

            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            idUsuarior = bundle.getInt("idUsuario");

            String idUsuarios=String.valueOf(idUsuarior);

            //Toast.makeText(Cancelar.this,idUsuarios,Toast.LENGTH_LONG).show();
            //et2Nombre.setText(etNombre);

            // Double Calificacion = bundle.getDouble("Calificacion");
            // Toast.makeText(Intent2.this,Calificacion+"",Toast.LENGTH_LONG).show();
            // etCalificacion.setText(Calificacion+"");



        }else{
            Toast.makeText(Consultar.this, "idpersona error", Toast.LENGTH_SHORT).show();
        }
        ResultSet rs;
       sp_reservacionescon=(Spinner)findViewById(R.id.Sp_reservaciones_Con);



        try {
            PreparedStatement pst=conexionBD().prepareStatement("select idreservacion from reservacion where estatus=? and idpersona=?");


            pst.setString(1,"vigente");

            pst.setInt(2,idUsuarior);
            rs=pst.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("idreservacion");

                data.add( id);


            }

            String[] array = data.toArray(new String[0]);

            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,

                    android.R.layout.simple_list_item_1, data);

            sp_reservacionescon.setAdapter(NoCoreAdapter);




        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        sp_reservacionescon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                idreservacionscon=sp_reservacionescon.getSelectedItem().toString();
                idreservacionint=Integer.parseInt(idreservacionscon);
                Consultarres(idreservacionint);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

      //  idreservacionint=Integer.parseInt(idreservacionscon);


        //  items=getResources().getStringArray(R.array.lista_espacios);

       // ArrayAdapter<String> adapter1= new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,items);
       // adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // sp_espaciosmod.setAdapter(adapter1);

    }



    public void Consultarres(int var) {

        ResultSet rs;
        //ResultSet rs2;

        try {
            PreparedStatement pst=conexionBD().prepareStatement("select fecha, horainicio, horafin, cancha from reservacion inner join canchas on reservacion.idcancha=canchas.idcancha where idreservacion=? ");
            pst.setInt(1,var);
            rs=pst.executeQuery();
           // rs2=pst.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                fecha = rs.getString("fecha");
                hora = rs.getString("horainicio");
                horafin=rs.getString("horafin");
                espacio=rs.getString("cancha");

               // Toast.makeText(getApplicationContext(),fecha,Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(),hora,Toast.LENGTH_SHORT).show();

               // data.add(id);


            }

                String espaciostr=String.valueOf(espacio);
               txtfecha.setText(fecha);
                txthora.setText(hora);
                txthorafin.setText(horafin);
               txtespacio.setText(espacio);




           // String[] array = data.toArray(new String[0]);

            //ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,

            //        android.R.layout.simple_list_item_1, data);

           // sp_espacios.setAdapter(NoCoreAdapter);




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Connection conexionBD(){

        Connection conexion=null;

        try{

            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://198.50.178.185/SQLEXPRESS;databaseName=bdveredalta;user=sa;password=morelia123;");

            Toast.makeText(getApplicationContext(),"conectado",Toast.LENGTH_SHORT).show();
        }catch (Exception e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return conexion;

    }



}
