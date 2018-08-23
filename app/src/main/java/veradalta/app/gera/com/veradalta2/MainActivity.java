package veradalta.app.gera.com.veradalta2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button Menu;

    EditText txtUsuario, txtPassword;

    int idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario=(EditText)findViewById(R.id.txtUsuario);
        txtPassword=(EditText)findViewById(R.id.txtPassword);

        Menu=(Button)findViewById(R.id.btnEntrar);
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Consulta();
                //Toast.makeText(getApplicationContext(),idusuario,Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(MainActivity.this, Menu.class);
               // startActivity(intent);
            }
        });
    }

    //Conexion a BD
    public Connection conexionBD(){
        Connection conexion=null;
        try{
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion= DriverManager.getConnection("jdbc:jtds:sqlserver://198.50.178.185/SQLEXPRESS;databaseName=bdveredalta;user=sa;password=morelia123;");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return  conexion;
    }

    //Logueo
    public void Consulta(){




        try{


            ResultSet rs1;
            //Toast.makeText(getApplicationContext(),"antes del prrepare",Toast.LENGTH_SHORT).show();
            PreparedStatement pst1=conexionBD().prepareStatement("select idpersona from persona where correo=?");
            //Toast.makeText(getApplicationContext(),"despues del prepare",Toast.LENGTH_SHORT).show();
            pst1.setString(1,txtUsuario.getText().toString());
            rs1 = pst1.executeQuery();
            // Toast.makeText(getApplicationContext(),"al ejecutar query",Toast.LENGTH_SHORT).show();

            while (rs1.next()) {

                idusuario = rs1.getInt("idpersona");
               //Toast.makeText(getApplicationContext(),idusuario,Toast.LENGTH_SHORT).show();

            }

           // String idUsuarios=String.valueOf(idusuario);

            //Toast.makeText(getApplicationContext(),idUsuarios,Toast.LENGTH_LONG).show();






            ResultSet rs;
            PreparedStatement pst=conexionBD().prepareStatement("select * from dbo.persona where correo=? AND idrol!=1");
           // Statement stn1=conexionBD().createStatement();
           // ResultSet rs=stn1.executeQuery("select * from dbo.persona where correo='"+txtUsuario.getText().toString()+"'" + "AND idrol!=1");

            pst.setString(1,txtUsuario.getText().toString());
            rs=pst.executeQuery();
            if(rs.next()){
                Statement stn2=conexionBD().createStatement();
                ResultSet rs2=stn2.executeQuery("select * from dbo.persona where password='"+txtPassword.getText().toString()+"'");
                if (rs2.next()){
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    intent.putExtra("idUsuario", idusuario);
                    //intent.putExtra("Calificacion", Calificacion);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Contraseña invalida",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Acceso Denegado",Toast.LENGTH_SHORT).show();
            }
        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
