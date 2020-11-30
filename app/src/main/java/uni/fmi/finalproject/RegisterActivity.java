package uni.fmi.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    EditText repeatPasswordET;
    EditText emailET;

    Button registerB;
    Button cancelB;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        repeatPasswordET = findViewById(R.id.repeatPasswordEditText);
        emailET = findViewById(R.id.emailEditText);
        cancelB = findViewById(R.id.cancelButton);
        registerB = findViewById(R.id.registerButton);

        registerB.setOnClickListener(onClickListener);
        cancelB.setOnClickListener(onClickListener);

        dbHelper = new DbHelper(this);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            if (v.getId() == R.id.cancelButton) {
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                return;
            }

            if (v.getId() == R.id.registerButton) {
                if (usernameET.getText().length() == 0 || passwordET.getText().length() == 0 ||
                        !passwordET.getText().toString().equals(repeatPasswordET.getText().toString()) ||
                        !Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", emailET.getText().toString())) {
                    return;
                }

                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String email = emailET.getText().toString();

                User user = new User(username, password, email);

                if (!dbHelper.userRegister(user)) {
                    Toast.makeText(RegisterActivity.this, "Failed to Register. Something went wrong!", Toast.LENGTH_LONG).show();
                    return;
                }
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        }
    };
}