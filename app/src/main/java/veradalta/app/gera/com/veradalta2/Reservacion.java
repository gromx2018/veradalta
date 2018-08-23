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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reservacion extends AppCompatActivity implements View.OnClickListener {

    String cancha;
    int idcancha;
    int idUsuarior;

    Spinner sp_espacios;
    String [] items;


    private boolean isFirstTime=true;
    Button salirres;

    Date test=null;
    Button bfecha;
    Button bhora;
    Button bhorafin;
    Button bregistrar;

    EditText efecha, ehora,efin;

    private int dia,mes,anio,hora,minutos,horafin,minutosfin;

    //final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion);


        salirres=(Button)findViewById(R.id.btnSalirRes);
        salirres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);

            }
        });


        bfecha=(Button)findViewById(R.id.btnFecha);
        bhora=(Button)findViewById(R.id.btnHora);
        bhorafin=(Button)findViewById(R.id.btnfin);
        bregistrar=(Button)findViewById(R.id.btnRegistrarRes);

        efecha=(EditText)findViewById(R.id.txtFechamod);
        ehora=(EditText)findViewById(R.id.txtHoramod);
        efin=(EditText)findViewById(R.id.txtHoraFin);

        bfecha.setOnClickListener(this);

        bhora.setOnClickListener(this);

        bhorafin.setOnClickListener(this);

        sp_espacios=(Spinner)findViewById(R.id.Sp_espacios);

        bregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarReservacion();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            idUsuarior = bundle.getInt("idUsuario");

            String idUsuarios=String.valueOf(idUsuarior);

            Toast.makeText(Reservacion.this,idUsuarios,Toast.LENGTH_LONG).show();
            //et2Nombre.setText(etNombre);

            // Double Calificacion = bundle.getDouble("Calificacion");
            // Toast.makeText(Intent2.this,Calificacion+"",Toast.LENGTH_LONG).show();
            // etCalificacion.setText(Calificacion+"");



        }else{
            Toast.makeText(Reservacion.this, "Esta vacio", Toast.LENGTH_SHORT).show();
        }

        //LLENADO DE ESPACIOS

        ResultSet rs;


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


       /* items=getResources().getStringArray(R.array.lista_espacios);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_espacios.setAdapter(adapter);*/
        sp_espacios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            cancha=sp_espacios.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

//AQUI TERMINADO LLENADO DE ESPACIOS






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


    public void registrarReservacion(){

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


                /*fin pruebas*/
               ResultSet rs;
            //Toast.makeText(getApplicationContext(),"antes del prrepare",Toast.LENGTH_SHORT).show();
                 PreparedStatement pst1=conexionBD().prepareStatement("select idcancha from canchas where cancha=?");
            //Toast.makeText(getApplicationContext(),"despues del prepare",Toast.LENGTH_SHORT).show();
                 pst1.setString(1,cancha);
                 rs = pst1.executeQuery();
           // Toast.makeText(getApplicationContext(),"al ejecutar query",Toast.LENGTH_SHORT).show();

            while (rs.next()) {

               idcancha = rs.getInt("idcancha");

            }

           // Toast.makeText(getApplicationContext(),idcancha,Toast.LENGTH_SHORT).show();

                PreparedStatement id=conexionBD().prepareStatement("insert into reservacion values(?,?,?,?,?)");
                PreparedStatement pst=conexionBD().prepareStatement("insert into reservacion values(?,?,?,?,?,?)");
                pst.setInt(1,idUsuarior);
                pst.setInt(2,idcancha);
                pst.setString(3,efecha.getText().toString() );
                pst.setString(4,ehora.getText().toString());
                pst.setString(5,efin.getText().toString());
                pst.setString(6,"vigente");

                pst.executeUpdate();

                Toast.makeText(getApplicationContext(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }





    @Override
    public void onClick(View v) {

        if (v==bfecha){

           final Calendar c = Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            anio=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    efecha.setText(i+"-"+(i1+1)+"-"+i2);
                    //test=java.util.Date (i2+"/"+(i1+1)+"/"+i);


                }
            }, anio,mes,dia);

            datePickerDialog.show();


           // java.sql.Date startDate = new java.sql.Date(c.getTime().getTime());
        }

        if (v==bhora){


            final Calendar c = Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    ehora.setText(hourOfDay+":"+minute);


                }
            },hora,minutos,false);

            timePickerDialog.show();

        }

        if (v==bhorafin){


            final Calendar c = Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    efin.setText(hourOfDay+":"+minute);


                }
            },hora,minutos,false);

            timePickerDialog.show();

        }



    }
}
