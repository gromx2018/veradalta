package veradalta.app.gera.com.veradalta2;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cancelar extends AppCompatActivity {

    Spinner sp_reservacionescan;
    int idUsuarior;
    String [] items;
    String idreservacions;
    int idreservacionint;

    Button btnCancelar;

    Button salircan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar);

        salircan=(Button)findViewById(R.id.btnSalirCan);
        btnCancelar=(Button)findViewById(R.id.btnCancelarCan);
        salircan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarModificar();

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
            Toast.makeText(Cancelar.this, "idpersona error", Toast.LENGTH_SHORT).show();
        }


        sp_reservacionescan=(Spinner)findViewById(R.id.Sp_reservacionescan);

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

            sp_reservacionescan.setAdapter(NoCoreAdapter);




        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        sp_reservacionescan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                idreservacions=sp_reservacionescan.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // items=getResources().getStringArray(R.array.lista_reservaciones);

        //ArrayAdapter<String> adapter1= new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,items);
        //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sp_espacioscan.setAdapter(adapter1);


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
            //ResultSet rs;
            //Toast.makeText(getApplicationContext(),"antes del prrepare",Toast.LENGTH_SHORT).show();
           // PreparedStatement pst1=conexionBD().prepareStatement("select idcancha from canchas where cancha=?");
            //Toast.makeText(getApplicationContext(),"despues del prepare",Toast.LENGTH_SHORT).show();
            //pst1.setString(1,cancha2);
           // rs = pst1.executeQuery();
            // Toast.makeText(getApplicationContext(),"al ejecutar query",Toast.LENGTH_SHORT).show();

           // while (rs.next()) {

            //    idcanchaint = rs.getInt("idcancha");

            //}

            // Toast.makeText(getApplicationContext(),idcanchaint,Toast.LENGTH_SHORT).show();

            //PreparedStatement id=conexionBD().prepareStatement("insert into reservacion values(?,?,?,?,?)");
            PreparedStatement pst=conexionBD().prepareStatement("UPDATE reservacion SET estatus=? where idreservacion=?");
           pst.setString(1,"cancelada");
            pst.setInt(2,idreservacionint);
           // Toast.makeText(getApplicationContext(),"aqui llega antes de fecha",Toast.LENGTH_SHORT).show();
           // pst.setString(3,efechamod.getText().toString() );
           // Toast.makeText(getApplicationContext(),"aqui llega despues de fecha",Toast.LENGTH_SHORT).show();
           // pst.setString(4,ehoramod.getText().toString());
           // Toast.makeText(getApplicationContext(),"aqui llega despues de hora inicio",Toast.LENGTH_SHORT).show();
           // pst.setString(5,efinmod.getText().toString());
           // Toast.makeText(getApplicationContext(),"aqui llega despues de hora fin",Toast.LENGTH_SHORT).show();
           // pst.setInt(6,idreservacionint);
           // Toast.makeText(getApplicationContext(),"aqui llega despues de idreservacion",Toast.LENGTH_SHORT).show();

            pst.executeUpdate();

            Toast.makeText(getApplicationContext(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
        }catch (SQLException e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

}
