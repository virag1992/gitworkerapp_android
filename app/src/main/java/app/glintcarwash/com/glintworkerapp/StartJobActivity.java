package app.glintcarwash.com.glintworkerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

public class StartJobActivity extends ActivityManagePermission {
    RelativeLayout rlOne, rlTwo;
    ImageView img1, img2;

    Bitmap photo;
    String Longitude, Latitude;
    ByteArrayOutputStream bytes;
    int order_id;
    String filepath, description;
    private Uri mImageCaptureUri;
    File sourceFile;
    List<String> response;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    protected static final int GALLERY_KITKAT_INTENT_CALLED = 0;
    private static final int GET_VIDEO_FROM_GALLARY = 4;
    private static final long MIN_TIME_BW_UPDATES = 0;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

    boolean one_click=false;
    Toolbar toolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_job_activity);
        setActionBar();
        rlOne = (RelativeLayout) findViewById(R.id.rlOne);
        rlTwo = (RelativeLayout) findViewById(R.id.rlTwo);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);

        askPermissions(new String[]{PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE ,PermissionUtils.Manifest_READ_EXTERNAL_STORAGE})
                .setPermissionResult(new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        rlOne.setClickable(false);
                        rlTwo.setClickable(false);
                        Toast.makeText(getApplicationContext(),"Please accept permission.",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void permissionNotGranted() {
                        //permission denied
                        //replace with your action
                        rlOne.setClickable(true);
                        rlTwo.setClickable(true);
                    }
                })
                .requestPermission(PermissionUtils.KEY_CAMERA);

        rlOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one_click = true;
                showDialogForImage();
            }
        });

        rlTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one_click = false;
                showDialogForImage();
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void setActionBar() {
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_action_list));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //tvtitle.setText(getResources().getString(R.string.home_title));
        Drawable upArrow;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            upArrow = getResources().getDrawable(R.drawable.back, StartJobActivity.this.getTheme());
        } else {
            upArrow = getResources().getDrawable(R.drawable.back);
        }

//        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        //upArrow.setColorFilter(Color.parseColor("#33cc90"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }







    public void showDialogForImage() {
        final String[] items = new String[]{"Take from camera",
                "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @SuppressLint("InlinedApi")
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    try {
                        if (Build.VERSION.SDK_INT >= 19) {
                            Intent intent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } else {
                            Intent intent = new Intent(
                                    "android.media.action.IMAGE_CAPTURE");
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        }
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { // pick from file

                    if (Build.VERSION.SDK_INT >= 19) {
                        Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent1.addCategory(Intent.CATEGORY_OPENABLE);
                        intent1.setType("image/jpeg");
                        startActivityForResult(intent1,
                                GALLERY_KITKAT_INTENT_CALLED);
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_PICK);
                        startActivityForResult(Intent.createChooser(intent,
                                "Complete action using"), PICK_FROM_FILE);
                    }

                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:

                Bitmap imageData = (Bitmap) data.getExtras().get("data");
                mImageCaptureUri = getImageUri(StartJobActivity.this, imageData);
                photo = imageData;
                filepath = Utility.getPath(getApplicationContext(), mImageCaptureUri);
                File f_obj = new File(filepath);
                long file_size = f_obj.length() / 1024;
                double file_sizeMb = file_size / 1024;
                if (file_sizeMb >= 1) {
                    String f_path = filepath;
                    filepath = Utility.compressImage(f_path);
                }
                setImageView();
                // cropCapturedImage();
                break;
            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    try {
                        Bitmap bm = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(mImageCaptureUri));

                        filepath = Utility.getPath(getApplicationContext(),
                                mImageCaptureUri);
                        File f_obj2 = new File(filepath);
                        long file_size2 = f_obj2.length() / 1024;
                        double file_sizeMb2 = file_size2 / 1024;
                        if (file_sizeMb2 >= 1) {
                            String f_path = filepath;
                            filepath = Utility.compressImage(f_path);
                        }
                        photo = bm;
                        setImageView();
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                // cropCapturedImage();
                break;
            case GALLERY_KITKAT_INTENT_CALLED:
                ParcelFileDescriptor parcelFileDescriptor;
                mImageCaptureUri = data.getData();
                try {
                    parcelFileDescriptor = getContentResolver().openFileDescriptor(
                            mImageCaptureUri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor
                            .getFileDescriptor();
                    Bitmap image = BitmapFactory
                            .decodeFileDescriptor(fileDescriptor);
                    Bitmap bm = BitmapFactory.decodeStream(getContentResolver()
                            .openInputStream(mImageCaptureUri));

                    parcelFileDescriptor.close();
                    filepath = Utility.getPath(getApplicationContext(),
                            mImageCaptureUri);
                    File f_obj3 = new File(filepath);
                    long file_size3 = f_obj3.length() / 1024;
                    double file_sizeMb3 = file_size3 / 1024;
                    if (file_sizeMb3 > 1) {
                        String f_path = filepath;
                        filepath = Utility.compressImage(f_path);
                    }
                    photo = image;
                    setImageView();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                    setImageView();
                }
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "temp", null);
        return Uri.parse(path);
    }

    @SuppressWarnings("unused")
    private String getFilePathFromContentUri(Uri selectedVideoUri,
                                             ContentResolver contentResolver) {

        String filePath;
        String[] filePathColumn = { MediaStore.MediaColumns.DATA };

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;

    }

    public void setImageView() {
        if(one_click){
            if (photo != null) {
                Drawable dr = new BitmapDrawable(photo);
                rlOne.setBackgroundDrawable(dr);
                img1.setVisibility(View.GONE);
            }
        }else{
            if (photo != null) {
                Drawable dr = new BitmapDrawable(photo);
                rlTwo.setBackgroundDrawable(dr);
                img2.setVisibility(View.GONE);
            }
        }

    }

}
