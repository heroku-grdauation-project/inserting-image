package com.example.mac.gradproj2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Image extends AppCompatActivity implements LocationListener {
    byte[] byteArray;
    Button submit;
    Button view;
    ImageView imgcapture;
    Button camera;
    int REQUEST_IMAGE_CAPTURE = 1;
    Image instance;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    double lat;
    double lon;
    String provider;
    Intent intent;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    String imglon;
    String imglat;
    Address address;
    String timestamp;
    //double longitude;
    //double latitude;
    LocationManager lm;
    public Criteria criteria;
    public String bestProvider;
    static public final int REQUEST_LOCATION = 1;
    TextView txtLat;
    ImageView img;
    String img_str;
    String JSON;
    @SuppressLint("MissingPermission")
    protected void onCreate(final Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitimage);
        camera = (Button) findViewById(R.id.camera);
        imgcapture = (ImageView) findViewById(R.id.image);
        submit = (Button) findViewById(R.id.submit);
        txtLat = (TextView) findViewById(R.id.textview1);
        img = (ImageView) findViewById(R.id.dataview);
        view = (Button) findViewById(R.id.view);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                configure_button();
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, instance);
                startActivityForResult(cInt, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    public void submitImage(View View)
    {
        String image = img_str;
        Bundle bd= getIntent().getExtras();
        String uid= bd.getString("1");
        String address = txtLat.getText().toString() ;
        String resulti ="0";
        String name = "img";
        String type = "submit";
        //String type = "http://192.168.1.102/webapp/Submit.php";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,image,address,uid,resulti,name);
        Toast.makeText(getApplicationContext(),"submitting successfully, Thanks for your Cooperation", Toast.LENGTH_SHORT).show();
        //Intent ret = new Intent(Image.this, MainActivity.class);
        //startActivity(ret);
    }

    public void showJSON (View View)
    {
        String id = "14";
        String type = "select";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,id);
        Toast.makeText(getApplicationContext(),"successfully retrieved the JSON", Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgcapture.setImageBitmap(photo);
            img.setImageBitmap(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();
            img_str = Base64.encodeToString(byteArray, Base64.DEFAULT);
            System.err.println("THis is the byte array" + byteArray);
//
//           imagefile = Base64.encodeToString(byteArray, Base64.DEFAULT);
//           System.err.println("This is the image file " + storeFilename);
//
//           ExifInterface exifInterface = null;
//            try {
//               exifInterface = new ExifInterface(storeFilename);
//               timestamp = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
//               imglat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
//                imglon = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
//
//              System.err.println("This is the timestamp " + timestamp );
//              System.err.println("This is the " + imglat );
//              System.err.println("This is the " + imglon);
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
        }
    }


        @SuppressLint("SetTextI18n")
        public void onLocationChanged(Location location) {
            /*
            imglon = String.valueOf(location.getLatitude());
            imglat = String.valueOf(location.getLongitude());
            System.err.println("This is the Longitude" + imglon);
            System.err.println("This is the Longitude" + imglat);
            txtLat = (TextView) findViewById(R.id.textview1);
            txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude()+"2"+"cairo,egypt,yacoub artin"+"img");
            *////*
            imglon = String.valueOf(location.getLatitude());
            imglat = String.valueOf(location.getLongitude());
            lat = location.getLatitude();
            lon = location.getLongitude();
            System.err.println("This is the Longitude" + imglon);
            System.err.println("This is the Longitude" + imglat);
            txtLat = (TextView) findViewById(R.id.textview1);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;
            try {

                addresses = geocoder.getFromLocation(lat, lon, 1);
                if (addresses.size() > 0) {
                    address = addresses.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            System.err.println("This is the address" + address);
            txtLat.setText(" adress: " + address.getAddressLine(0));


        }

        @Override
        public void onProviderDisabled(String provider) {
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Latitude", "enable");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude", "status");
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case 10:
                    configure_button();
                    break;
                default:
                    break;
            }
        }

        void configure_button() {
            // first check for permissions
            if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                            , 10);
                }
                return;
            }
        }
    }





