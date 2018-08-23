package veradalta.app.gera.com.veradalta2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends AppCompatActivity {


    int idUsuarior;
    String nombre;
    Button reservacion;
    Button modificar;
    Button consultar;
    Button cancelar;
    Button salir;
    TextView txtnombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        reservacion=(Button)findViewById(R.id.btnReservar);
        modificar=(Button)findViewById(R.id.btnModificar);
        consultar=(Button)findViewById(R.id.btnConsultar);
        cancelar=(Button)findViewById(R.id.btnCancelar);
        txtnombre=(TextView) findViewById(R.id.txtNombre);


        salir=(Button)findViewById(R.id.btnSalirmenu);
        salir.setOnClickListener(new View.OnClickListener() {
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

           // Toast.makeText(Menu.this,idUsuarios,Toast.LENGTH_LONG).show();
            //et2Nombre.setText(etNombre);

            // Double Calificacion = bundle.getDouble("Calificacion");
            // Toast.makeText(Intent2.this,Calificacion+"",Toast.LENGTH_LONG).show();
            // etCalificacion.setText(Calificacion+"");

            Consultarres();

        }else{
            Toast.makeText(Menu.this, "Esta vacio", Toast.LENGTH_SHORT).show();
        }






        reservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent reservacion=new Intent(Menu.this,Reservacion.class);
                reservacion.putExtra("idUsuario", idUsuarior);

                startActivity(reservacion);

            }
        });



        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent modificar= new Intent(Menu.this,Modificar.class);
                modificar.putExtra("idUsuario", idUsuarior);
                startActivity(modificar);
            }
        });


        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent consultar= new Intent(Menu.this,Consultar.class);
                consultar.putExtra("idUsuario", idUsuarior);
                startActivity(consultar);
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cancelar= new Intent(Menu.this,Cancelar.class);
                cancelar.putExtra("idUsuario", idUsuarior);
                startActivity(cancelar);
            }
        });




    }


    public void Consultarres() {

        ResultSet rs;
        //ResultSet rs2;

        try {
            PreparedStatement pst=conexionBD().prepareStatement("select nombre from persona where idpersona=? ");
            pst.setInt(1,idUsuarior);
            rs=pst.executeQuery();
            // rs2=pst.executeQuery();
            ArrayList<String> data = new ArrayList<String>();

            while (rs.next()) {

                nombre = rs.getString("nombre");
               // hora = rs.getString("horainicio");
               // horafin=rs.getString("horafin");
                //espacio=rs.getString("cancha");

                // Toast.makeText(getApplicationContext(),fecha,Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplicationContext(),hora,Toast.LENGTH_SHORT).show();

                // data.add(id);


            }

            //String espaciostr=String.valueOf(espacio);
           // txtfecha.setText(fecha);
           // txthora.setText(hora);
           // txthorafin.setText(horafin);
            txtnombre.setText(nombre);




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
