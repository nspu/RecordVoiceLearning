package fr.nspu.dev.recordvoicelearning.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.model.Folder;
import fr.nspu.dev.recordvoicelearning.view.fragment.AddFolderFragment;
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderFragment;
import fr.nspu.dev.recordvoicelearning.view.fragment.FolderListFragment;

/**
 * Created by nspu on 18-02-04.
 */

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private final String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            FolderListFragment
                    folderListFragment = new FolderListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, folderListFragment, FolderListFragment.TAG).commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /** Shows the folder detail fragment */
    public void showFolder(Folder folder) {

        FolderFragment productFragment = FolderFragment.forFolder(folder.getId(), folder.getQuestionToAnswer());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("folder")
                .replace(R.id.fragment_container,
                        productFragment, null).commit();
    }



    public void addFolder(){
        AddFolderFragment
                addFolderFragment = new AddFolderFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("addfolder")
                .replace(R.id.fragment_container,
                        addFolderFragment, null).commit();
    }
}
