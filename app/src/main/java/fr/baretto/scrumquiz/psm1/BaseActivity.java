package fr.baretto.scrumquiz.psm1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * BaseActivity contains default behaviour of scrumquiz.psm1 activity.
 * Created by mehdi on 10/03/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent aboutUs = new Intent(this, AboutActivity.class);
                startActivity(aboutUs);
                break;
            case R.id.contact:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:scrumquiz.psm1.contact@baretto.fr"));
                startActivity(Intent.createChooser(emailIntent,
                        "Send Email Using: "));
                break;
            default:
                break;
        }

        return true;
    }
}
