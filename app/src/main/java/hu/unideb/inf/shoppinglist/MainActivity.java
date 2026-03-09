package hu.unideb.inf.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TEXTVIEW_CONTENTS = "TEXTVIEW_CONTENTS";
    TextView shoppingLIstTextView;



    ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            activityResult -> {
                String item = activityResult.getData().getStringExtra(ItemsActivity.ITEM);
                Log.d("ITEMS_TEST", "I have returned");
                Log.d("ITEMS_TEST", item);

                if (shoppingLIstTextView.getText().toString().equals(getString(R.string.emptyList)))
                    shoppingLIstTextView.setText("");

                shoppingLIstTextView.append(item + "\n");
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shoppingLIstTextView = findViewById(R.id.shoppingListTextView);
        if (savedInstanceState!=null)
            shoppingLIstTextView.setText(savedInstanceState.getString(TEXTVIEW_CONTENTS));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXTVIEW_CONTENTS, shoppingLIstTextView.getText().toString());
    }

    public void handleAddButton(View view) {
        Intent intent = new Intent(this, ItemsActivity.class);
        activityResultLauncher.launch(intent);
    }
}