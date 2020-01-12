package com.example.seoyoungsapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun connect() : Connection?
    {
        val server: String = "27.35.9.249:60000"
        val username: String = "cit"
        val password : String = "citcitcit"
        val database : String = "citdb"
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var conn : Connection? = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            val connURL : String = "jdbc:jtds:sqlserver://" + server + ";databaseName=" +database + ";";
            conn = DriverManager.getConnection(connURL, username, password)
        }catch(sqle:SQLException){
            Log.e("ERRO", sqle.message)
        }catch(cnfe : ClassNotFoundException){
            Log.e("ERRO", cnfe.message)
        }catch(e:Exception){
            Log.e("ERRO", e.message)
        }
        return conn
    }
    fun connectionEvent(v: View){
        val conn = connect()
        if (conn == null)
            text.setText("연결 안 됨 ")
        else
            text.setText("연결됨 ")
    }
    fun loginEvent(v:View) {
        val conn:Connection? = connect()

        val id = editid.getText()
        val pw = editpw.getText()

        try {
            if (conn != null) {
                val query : String = "SELECT * FROM Accounts WHERE id='"+id + "' AND pw='" + pw + "';"
                val stmt = conn.createStatement();
                val rs = stmt.executeQuery(query);

                if(rs.next())
                    text.setText("로그인 성공");
                else
                    text.setText("로그인 실패");
            }
        }
        catch(se:SQLException){
            text.setText("쿼리 실행 실페")
        }catch(e:Exception){
            text.setText("오류 발생")
        }
    }
}