package sih.firebasesendnotif;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CityPicker extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String city_pick;
    Button button;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);

        button = findViewById(R.id.complete_login);
        spinner = findViewById(R.id.city_picker);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city_pick.equals("")){
                    Toast.makeText(CityPicker.this, "Invalid information", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CityPicker.this, NavbarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("City", city_pick);
                    startActivity(intent);
                }
            }
        });
    }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            city_pick = parent.getItemAtPosition(pos).toString();
            final SharedPreferences.Editor editor = this.getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
            editor.putString("city_name", city_pick);
            editor.apply();

        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
            Toast.makeText(this, "Please select something", Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }
}
