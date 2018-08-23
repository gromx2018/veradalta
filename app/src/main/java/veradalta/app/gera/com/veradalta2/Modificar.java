package veradalta.app.gera.com.veradalta2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import  java.sql.*;

public class Modificar extends AppCompatActivity implements View.OnClickListener {
    String idreservacions;
    int idreservacionint;
    int idcanchaint;
    String cancha;
    int idUsuarior;

    Spinner sp_reservaciones;
    String [] reservacion;
    String cancha2;
    Spinner sp_espacios;
    String [] items;

    Button mod;
    Button bfechamod;
    Button bhoramod;
    Button bhorafinmod;
    Button salirmod;

    EditText efechamod, ehoramod,efinmod;

    private int dia,mes,anio,hora,minutos,horafin,minutosfin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);



        salirmod=(Button)findViewById(R.id.btnSalirmod);
        mod=(Button)findViewById(R.id.btnActualizarmod);
        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               registrarModificar();
            }
        });

        salirmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);

            }
        });


        /*COMIENZA FECHA Y HORA*/

        bfechamod=(Button)findViewById(R.id.btnFechamod);
        bhoramod=(Button)findViewById(R.id.btnHoramod);
        bhorafinmod=(Button)findViewById(R.id.btnfinmod);

        efechamod=(EditText)findViewById(R.id.txtFechamod);
        ehoramod=(EditText)findViewById(R.id.txtHoramod);
        efinmod=(EditText)findViewById(R.id.txtHoraFinmod);

        bfechamod.setOnClickListener(this);

        bhoramod.setOnClickListener(this);

        bhorafinmod.setOnClickListener(this);
        sp_reservaciones=(Spinner)findViewById(R.id.sp_reservacionesmod);

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
            Toast.makeText(Modificar.this, "idpersona error", Toast.LENGTH_SHORT).show();
        }


        /*TERMINA FECHA Y HORA*/

        /*
        CONSULTA BD DE RESERVACIONES
       */
        ResultSet rs;

        //Reservacion reserva=new Reservacion();



        Toast.makeText(getApplicationContext(),"aqui falla",Toast.LENGTH_SHORT).show();

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

            sp_reservaciones.setAdapter(NoCoreAdapter);




        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        /*AQUI TERMINA LA CONSULTA*/

        /*COMIENZA SPINNER ESPACIOS*/
        sp_espacios=(Spinner)findViewById(R.id.Sp_espaciosmod);
        ResultSet rs2;


        try {
            PreparedStatement pst=conexionBD().prepareStatement("select cancha from canchas");
            rs=pst.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                String id = rs.getString("cancha");

                data.add(id);


            }

            String[] array = data.toArray(new String[0]);

            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this,

                    android.R.layout.simple_list_item_1, data);

            sp_espacios.setAdapter(NoCoreAdapter);




        } catch (SQLException e) {
            e.printStackTrace();
        }

       // items=getResources().getStringArray(R.array.lista_espacios);

       // ArrayAdapter<String> adapter1= new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,items);
       // adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sp_espacios.setAdapter(adapter1);

        sp_espacios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                cancha2=sp_espacios.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //TERMINA SPINNER ESPACIOS

        //COMIENZA SPINNER RESERVACIONES


        //reservacion=getResources().getStringArray(R.array.lista_reservaciones);

       // ArrayAdapter<String> adapter=new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_spinner_item,reservacion);

       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // sp_reservaciones.setAdapter(adapter);

        /*TERMINA SPINNER*/
        sp_reservaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 idreservacions=sp_reservaciones.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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




    public void registrarModificar(){

        try{


            // Calendar calendar = Calendar.getInstance();
            //java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());



            //java.sql.Date startDate = new java.sql.Date();

            //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            // String date2=efecha.getText().toString();
            // Date date = formatter.parse(efecha.getText().toString().);

            /*aqui aremos pruebas*/

              /*  Date date = null;
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                try {
                    date=df.parse(efecha.getText().toString());
                    long millies=date.getTime();// Here is milli seconds
                } catch (ParseException e) {
                    e.printStackTrace();
                }

*/
            idreservacionint=Integer.parseInt(idreservacions);
            //idcanchaint=Integer.parseInt()

            /*fin pruebas*/
            /*fin pruebas*/
            ResultSet rs;
            //Toast.makeText(getApplicationContext(),"antes del prrepare",Toast.LENGTH_SHORT).show();
            PreparedStatement pst1=conexionBD().prepareStatement("select idcancha from canchas where cancha=?");
            //Toast.makeText(getApplicationContext(),"despues del prepare",Toast.LENGTH_SHORT).show();
            pst1.setString(1,cancha2);
            rs = pst1.executeQuery();
            // Toast.makeText(getApplicationContext(),"al ejecutar query",Toast.LENGTH_SHORT).show();

            while (rs.next()) {

                idcanchaint = rs.getInt("idcancha");

            }

            // Toast.makeText(getApplicationContext(),idcanchaint,Toast.LENGTH_SHORT).show();

            //PreparedStatement id=conexionBD().prepareStatement("insert into reservacion values(?,?,?,?,?)");
            PreparedStatement pst=conexionBD().prepareStatement("UPDATE reservacion SET idpersona = ?, idcancha=?, fecha=?, horainicio=?,horafin=? where idreservacion=?");
            pst.setInt(1,idUsuarior);
            pst.setInt(2,idcanchaint);
            Toast.makeText(getApplicationContext(),"aqui llega antes de fecha",Toast.LENGTH_SHORT).show();
            pst.setString(3,efechamod.getText().toString() );
            Toast.makeText(getApplicationContext(),"aqui llega despues de fecha",Toast.LENGTH_SHORT).show();
            pst.setString(4,ehoramod.getText().toString());
            Toast.makeText(getApplicationContext(),"aqui llega despues de hora inicio",Toast.LENGTH_SHORT).show();
            pst.setString(5,efinmod.getText().toString());
            Toast.makeText(getApplicationContext(),"aqui llega despues de hora fin",Toast.LENGTH_SHORT).show();
            pst.setInt(6,idreservacionint);
            Toast.makeText(getApplicationContext(),"aqui llega despues de idreservacion",Toast.LENGTH_SHORT).show();

            pst.executeUpdate();

            Toast.makeText(getApplicationContext(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onClick(View v) {

        if (v==bfechamod){

            final Calendar c = Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            anio=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    efechamod.setText(i+"-"+(i1+1)+"-"+i2);


                }
            }, anio,mes,dia);

            datePickerDialog.show();


        }

        if (v==bhoramod){


            final Calendar c = Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    ehoramod.setText(hourOfDay+":"+minute);


                }
            },hora,minutos,false);

            timePickerDialog.show();

        }

        if (v==bhorafinmod){


            final Calendar c = Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    efinmod.setText(hourOfDay+":"+minute);


                }
            },hora,minutos,false);

            timePickerDialog.show();

        }


    }
}
