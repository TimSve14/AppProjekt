package db.hfad.com.inloggning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGetGameClick(View view) {
        Intent getGameScreenIntent = new Intent(this,
                SecondScreen.class);
        final int result = 1;

        getGameScreenIntent.putExtra("callingActivity","MainActivity");
        startActivityForResult(getGameScreenIntent,result);
    }
}
