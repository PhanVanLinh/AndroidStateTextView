package toong.vn.androidstatetextview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        textView.setSelected(true);
//        textView.setEnabled(false);

        // test change text color at runtime
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setTextColor(Color.RED);
            }
        }, 5000);
    }
}
