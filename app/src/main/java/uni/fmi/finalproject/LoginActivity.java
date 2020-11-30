package uni.fmi.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginB;
    TextView registerTV;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET=findViewById(R.id.usernameEditText);
        passwordET=findViewById(R.id.passwordEditText);
        loginB=findViewById(R.id.loginButton);
        registerTV = findViewById(R.id.registerTextView);

        loginB.setOnClickListener(onClickListener);
        registerTV.setOnClickListener(onClickListener);
        dbHelper = new DbHelper(this);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            if(v.getId() == R.id.registerTextView){
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                return;
            }

            if(usernameET.getText().length() > 0 && passwordET.getText().length() > 0){
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if(dbHelper.isLoginSuccessful(username, password)){
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Wrong credentials! Try again.", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}