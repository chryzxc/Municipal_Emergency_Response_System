package system.municipalemergencyresponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.reflect.TypeToken;
import com.google.firebase.FirebaseException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;


import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;

import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ccy.focuslayoutmanager.FocusLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static ccy.focuslayoutmanager.FocusLayoutManager.dp2px;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;
import static java.nio.file.Paths.get;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        MapboxMap.OnMapClickListener,PermissionsListener {
    static ChipNavigationBar bottomNavigationView;
    KProgressHUD hud;

    Boolean hasConnection;


    private BuildingPlugin buildingPlugin;
    private PermissionsManager permissionsManager;
    static Marker emergencyMarker,responderMarker,locationMarker;
    Style loadedStyle;
    SymbolLayer emergencySymbolLayer,responderSymbolLayer;
    private LocationEngine locationEngine;
    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);

    static ConstraintLayout respondCard;
    static LinearLayout sideButtons;
    static CardView emergencyCard;
    static TextView rSender,rNumber,rAddress,rDate,rDetails,rLocation;
    ConstraintLayout responderCard;
    MaterialCardView notificationCard;
    CardView myNotification;
    TextView myNotifCount;

    static Button emergencyRespond;


    private static final int SEND_SMS_CODE = 23;
    Boolean isSendSmsAllowed;

    List<EmergencyList> emergency_myLists;
    EmergencyAdapter emergency_adapter;
    RecyclerView emergency_rv;

    List<ResponderList> responder_myLists;
    ResponderAdapter responder_adapter;
    RecyclerView responder_rv;
    int responder_rv_pos;

    static List<RespondList> respond_myLists;
    static RespondAdapter respond_adapter;
    static RecyclerView respond_rv;

    List<MyNotificationList> my_notification_myLists;
    MyNotificationAdapter my_notification_adapter;
    RecyclerView my_notification_rv;



    List<VerifyList> verify_myLists;
    VerifyAdapter verify_adapter;
    RecyclerView verify_rv;

    List<UsersList> users_myLists;
    UsersAdapter users_adapter;
    RecyclerView users_rv;




    private static final String EMERGENCYGEOJSON_SOURCE_ID = "EMERGENCYGEOJSON_SOURCE_ID";
    private static final String RESPONDERGEOJSON_SOURCE_ID = "RESPONDERGEOJSON_SOURCE_ID";
    private static final String EMERGENCYMARKER_LAYER_ID = "EMERGENCYMARKER_LAYER_ID";
    private static final String RESPONDERMARKER_LAYER_ID = "RESPONDERMARKER_LAYER_ID";
    GeoJsonSource emergencyGeoJsonSource,responderGeoJsonSource;

    private MapView mapView;
    static MapboxMap mapboxMap;

   // FeatureCollection featureCollection;
    JSONObject properties;


    View reportLayout,gpsLayout,setupLayout,notificationLayout,smsLayout,reportUserLayout;
    androidx.appcompat.app.AlertDialog reportDialog,gpsDialog,nameDialog,notificationDialog,smsDialog,reportUserDialog;
    private final HashMap<String, View> viewMap = new HashMap<>();
    LocationManager locationManager ;
    boolean isGpsEnabled;
    boolean gpsLayoutIsShowing;
    static Double currentLat, currentLong = null;
    static CountDownTimer countDownTimer;

    ConstraintLayout expandList;
    static CardView respondCardList;
    static String emergencyID,reporterID;
    static ListenerRegistration listenerRegistrationEmergency,listenerRegistrationRespond,listenerRegistrationResponder;
    static ImageView emptyImage;
    static ImageView listMaximize,listMinimize;
    private static final int PERMISSION_REQUEST_CODE = 1;

    TextView userLogin,hideUserprofile,loginBack,verificationMessage,verificationResend;
    EditText loginNumber,verificationCode;
    Button loginProceed,verificationProceed;
    ImageView userProfile;
    CardView userLayout,numberLayout,adminLayout;
    ConstraintLayout loginLayout,authenticateLayout;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onVerificationStateChangedCallbacks;
    private String verificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private PhoneAuthCredential credential;

    static String loadName,loadNumber,loadAddress,loadId,loadAge,loadType,loadClass = null;
    Boolean profileIsLoaded = false;

    String userType = null;
    String typeChecked = null;

    static FirebaseFirestore db;

    List<NotificationList> notification_myLists;
    NotificationAdapter notification_adapter;
    RecyclerView notification_rv;

    boolean activityStart = true;

    private static final String DIRECTIONS_LAYER_ID = "DIRECTIONS_LAYER_ID";
    private static final String LAYER_BELOW_ID = "road-label-small";
    private static final String SOURCE_ID = "SOURCE_ID";
    private FeatureCollection dashedLineDirectionsFeatureCollection;
    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    SharedPreferences sharedPreferences;

    // Verification
    LinearLayout uploadDocu,uploadSelfie;
    ImageView docuImage,docuDelete,selfieImage,selfieDelete;
    String docuPhotoPath,selfiePhotoPath;
    ConstraintLayout imageDocu,imageSelfie;
    View myLayoutReview,myLayoutSelection,myLayoutWarning;
    androidx.appcompat.app.AlertDialog dialogReview,dialogSelection,dialogWarning;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    SharedPreferences preferences;
    Switch registerType;
    static View myLayout;
    static LayoutInflater inflater;
    static androidx.appcompat.app.AlertDialog verifyDialog,adminDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        inflater = getLayoutInflater();



        mapView.getMapAsync(this);

        emergencyCard = (CardView) findViewById(R.id.emergencyCard);
        respondCard = (ConstraintLayout) findViewById(R.id.respondCard);
        responderCard = (ConstraintLayout) findViewById(R.id.responderCard);
        notificationCard = (MaterialCardView) findViewById(R.id.notificationCard);
        myNotification = (CardView) findViewById(R.id.myNotification);

        sideButtons = (LinearLayout) findViewById(R.id.sideButtons);
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);

        rSender = (TextView) findViewById(R.id.rSender);
        rNumber = (TextView) findViewById(R.id.rNumber);
        rAddress = (TextView) findViewById(R.id.rAddress);
        rDate = (TextView) findViewById(R.id.rDate);
        rDetails =(TextView) findViewById(R.id.rDetails);
        rLocation =(TextView) findViewById(R.id.rLocation);

        expandList = findViewById(R.id.expandList);
        respondCardList = (CardView) findViewById(R.id.respondCardList);
        emptyImage = (ImageView) findViewById(R.id.emptyImage);
        listMaximize = (ImageView) findViewById(R.id.listMaximize);
        listMinimize = (ImageView) findViewById(R.id.listMinimize);

        emergency_rv = (RecyclerView) findViewById(R.id.emergency_rec);
        emergency_rv.setHasFixedSize(true);
        emergency_rv.setLayoutManager(new GridLayoutManager(this, 1));
        emergency_myLists = new ArrayList<>();


        responder_rv = (RecyclerView) findViewById(R.id.responder_rec);
        FocusLayoutManager focusLayoutManager =
                new FocusLayoutManager.Builder()
                        .layerPadding(dp2px(this, 100))
                        .normalViewGap(dp2px(this, 5))
                        .focusOrientation(FocusLayoutManager.FOCUS_LEFT)
                        .isAutoSelect(true)
                        .maxLayerCount(1)
                        .setOnFocusChangeListener(new FocusLayoutManager.OnFocusChangeListener() {
                            @Override
                            public void onFocusChanged(int focusdPosition, int lastFocusdPosition) {

                            }
                        })
                        .build();
        responder_rv.setLayoutManager(focusLayoutManager);



       // responder_rv = (RecyclerView) findViewById(R.id.responder_rec);
      //  responder_rv.setHasFixedSize(true);
       // responder_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        responder_myLists = new ArrayList<>();



        respond_rv = (RecyclerView) findViewById(R.id.respond_rec);
        respond_rv.setHasFixedSize(true);
        respond_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        respond_myLists = new ArrayList<>();

        my_notification_rv = (RecyclerView) findViewById(R.id.my_notifications_rec);
        my_notification_rv.setHasFixedSize(true);
        my_notification_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
     //   my_notification_rv.setLayoutManager(new GridLayoutManager(this, 1));
        my_notification_myLists = new ArrayList<>();
        
        emergencyRespond = (Button) findViewById(R.id.emergencyRespond);

        userLogin = findViewById(R.id.userLogin);
        userLayout = findViewById(R.id.userLayout);
        userProfile = findViewById(R.id.userProfile);
        hideUserprofile = findViewById(R.id.hideUserprofile);
        loginBack = findViewById(R.id.loginBack);
        numberLayout = findViewById(R.id.numberLayout);
        adminLayout = findViewById(R.id.adminLayout);
        loginLayout = findViewById(R.id.loginLayout);
        authenticateLayout = findViewById(R.id.authenticateLayout);
        verificationMessage = findViewById(R.id.verificationMessage);
        verificationResend = findViewById(R.id.verificationResend);
        loginNumber = findViewById(R.id.loginNumber);
        verificationCode = findViewById(R.id.verificationCode);

        loginProceed = findViewById(R.id.loginProceed);
        verificationProceed = findViewById(R.id.verificationProceed);

        userLogin.setPaintFlags(userLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginBack.setPaintFlags(loginBack.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        inflater = getLayoutInflater();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        myNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sideButtons.setVisibility(GONE);

                db.collection("Notifications").document(loadId).collection("notifications").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                db.collection("Notifications").document(loadId).collection("notifications").document(document.getId()).update("state","seen");

                               // Toast.makeText(MainActivity.this, document.getString("state"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                notificationCard.setVisibility(VISIBLE);
                myNotification.setVisibility(GONE);

                myNotifCount.setText(" ");



                ImageView myNotifClose = (ImageView) findViewById(R.id.myNotifClose);
                myNotifClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myNotification.setVisibility(VISIBLE);
                        notificationCard.setVisibility(GONE);
                        sideButtons.setVisibility(VISIBLE);

                        for (int i = 0 ; i < my_notification_myLists.size();i++){

                        db.collection("Notifications").document(firebaseAuth.getCurrentUser().getUid()).collection("notifications").document(my_notification_myLists.get(i).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                        }




                    }
                });



            }
        });


        loginProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = loginNumber.getText().toString().trim();
                if (TextUtils.isEmpty(number)){
                    loginNumber.setError("Phone number is required");
                    loginNumber.requestFocus();
                    hideKeyboard();
                    return;

                }else if (number.length() != 13){
                    loginNumber.setError("Phone number is incomplete");
                    loginNumber.requestFocus();
                    hideKeyboard();
                    return;
                }else{
                    hideKeyboard();
                    verification(number);

                }
            }
        });

        ImageView adminLogin = (ImageView) findViewById(R.id.adminLogin);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                myLayout = inflater.inflate(R.layout.admin_popup, null);


                Button adminSignin = (Button)myLayout.findViewById(R.id.adminSignin);
                TextInputEditText adminUser = (TextInputEditText)myLayout.findViewById(R.id.adminUser);
                TextInputEditText adminPassword = (TextInputEditText)myLayout.findViewById(R.id.adminPassword);

                adminSignin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adminUser.getText().toString().isEmpty()){
                            adminUser.setError("Username is required");
                            adminUser.requestFocus();
                            hideKeyboard();
                            return;
                        }else if (!adminUser.getText().toString().matches("admin")){
                            adminUser.setError("Incorrect username");
                            adminUser.requestFocus();
                            hideKeyboard();
                            return;
                        }

                        db.collection("Admin").document("admin").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        if (adminPassword.getText().toString().matches(document.getString("password"))){

                                            adminDialog.dismiss();
                                            numberLayout.setVisibility(GONE);
                                            adminLayout.setVisibility(VISIBLE);


                                        }else{
                                            adminPassword.setError("Incorrect password");
                                            adminPassword.requestFocus();
                                            hideKeyboard();
                                            return;
                                        }


                                    }
                                } else {
                                    Log.d(TAG, task.getException().toString(), task.getException());
                                }
                            }
                        });


                    }
                });






                builder.setView(myLayout);
                adminDialog = builder.create();


                adminDialog.setCancelable(true);
                adminDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                adminDialog.show();



            }
        });

        verificationProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verificationCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    verificationCode.setError("Verification code is required");
                    verificationCode.requestFocus();
                    hideKeyboard();
                    return;

                }else{
                    hideKeyboard();
                    verifyPhoneNumber(verificationId,code);

                }


            }
        });

        verificationResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = loginNumber.getText().toString().trim();
                resendVerification(number,forceResendingToken);
            }
        });



        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberLayout.setVisibility(GONE);

            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userLogin.getText().equals("Sign in")){

                    numberLayout.setVisibility(VISIBLE);
                    authenticateLayout.setVisibility(GONE);
                    loginLayout.setVisibility(VISIBLE);
                }else if (userLogin.getText().equals("Sign out")){
                    firebaseAuth.signOut();
                    loadProfile();
                }

            }
        });

        hideUserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLayout.setVisibility(GONE);
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberLayout.setVisibility(GONE);
                userLayout.setVisibility(VISIBLE);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
     //   firebaseAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);


        progressDialog = new ProgressDialog(this);



        onVerificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
               // progressDialog.dismiss();
                hud.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                forceResendingToken = token;

               // progressDialog.dismiss();
                hud.dismiss();
                loginLayout.setVisibility(GONE);
                authenticateLayout.setVisibility(VISIBLE);

            }
        };

        ImageView reportUserButton = (ImageView) findViewById(R.id.reportUserButton);
        reportUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                reportUserLayout = inflater.inflate(R.layout.report_user, null);


                Button proceedButton = (Button) reportUserLayout.findViewById(R.id.reportUserProceed);
                proceedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.collection("Users").document(reporterID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        if (document.getDouble("attempts") != null){
                                            if (Integer.valueOf(document.getDouble("attempts").intValue()) > 0){
                                                if (Integer.valueOf(document.getDouble("attempts").intValue()) == 1){
                                                    db.collection("Users").document(reporterID).update("attempts", FieldValue.increment(-1));
                                                    db.collection("Users").document(reporterID).update("disabled", true);
                                                    Toast.makeText(MainActivity.this, "Successfully reported", Toast.LENGTH_LONG).show();
                                                }else{
                                                    db.collection("Users").document(reporterID).update("attempts", FieldValue.increment(-1));
                                                    Toast.makeText(MainActivity.this, "Successfully reported", Toast.LENGTH_LONG).show();
                                                }

                                            }else{
                                                Toast.makeText(MainActivity.this, "The user has already been banned. Thanks for reporting", Toast.LENGTH_LONG).show();
                                            }

                                        }else{
                                            db.collection("Users").document(reporterID).update("attempts", 3);

                                        }

                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });




                        reportUserDialog.dismiss();

                    }
                });

                Button cancelButton = (Button) reportUserLayout.findViewById(R.id.reportUserCancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reportUserDialog.dismiss();
                    }
                });


                builder.setView(reportUserLayout);
                reportUserDialog = builder.create();
                reportUserDialog.setCancelable(false);
                reportUserDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                reportUserDialog.show();

            }
        });





        expandList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (respondCardList.getVisibility() == GONE){

                        listMaximize.setVisibility(GONE);
                        listMinimize.setVisibility(VISIBLE);
                        respondCardList.setVisibility(VISIBLE);


                    }else{

                        listMaximize.setVisibility(VISIBLE);
                        listMinimize.setVisibility(GONE);
                        respondCardList.setVisibility(GONE);
                    }


            }
        });

        emergencyRespond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Date currentTime = Calendar.getInstance().getTime();

                Map<String, Object> data = new HashMap<>();
                data.put("responder_name",loadName);
                data.put("responder_classification", loadClass);
                data.put("responder_phone", loadNumber);
                data.put("time_responded", currentTime);
                data.put("time_canceled", currentTime);

                if (emergencyRespond.getText().equals("Respond")){
                    data.put("status","responded");
                }else{
                    data.put("status","cancelled");

                }
                data.put("latitude",currentLat);
                data.put("longitude",currentLong);



                DocumentReference documentReference = db.collection("Emergencies").document(emergencyID).collection("responders").document(loadId);
                documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
/*
                   //     emergencyRespond.setVisibility(GONE);

                        if (emergencyRespond.getText().equals("Respond")){
                            emergencyRespond.setText("Cancel");
                            emergencyRespond.setBackgroundColor(Color.RED);

                        }else{
                            emergencyRespond.setText("Respond");
                            emergencyRespond.setBackgroundColor(Color.MAGENTA);
                        }


 */



                        Date currentTime = Calendar.getInstance().getTime();

                        Map<String, Object> data = new HashMap<>();
                        data.put("responder_id",loadId);
                        data.put("responder_name",loadName);
                        data.put("responder_classification", loadClass);
                        data.put("responder_phone", loadNumber);
                        data.put("time_responded", currentTime);
                        data.put("time_canceled", currentTime);
                        data.put("state", "unread");

                        if (emergencyRespond.getText().equals("Respond")){
                            data.put("status","responded");
                        }else{
                            data.put("status","cancelled");

                        }




                        DocumentReference documentReference = db.collection("Notifications").document(reporterID).collection("notifications").document();
                        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });



        CardView fabMyLocation = (CardView) findViewById(R.id.fabMyLocation);
        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableLocationComponent(loadedStyle);
            }
        });



        CardView fabMarkers = (CardView) findViewById(R.id.fabMarkers);
        View fabMarkersHide = (View) findViewById(R.id.fabMarkersHide);
        fabMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fabMarkersHide.getVisibility() == GONE){

                    mapboxMap.clear();
                    loadedStyle.removeLayer(EMERGENCYMARKER_LAYER_ID);
                    loadedStyle.removeSource(EMERGENCYGEOJSON_SOURCE_ID);
                    loadedStyle.removeLayer(RESPONDERMARKER_LAYER_ID);
                    loadedStyle.removeSource(RESPONDERGEOJSON_SOURCE_ID);
                    fabMarkersHide.setVisibility(VISIBLE);


                }else{
                    fabMarkersHide.setVisibility(GONE);
                    activityStart = true;

                    displayEmergencies(loadedStyle);


                   displayResponder(loadedStyle);

                }

            }
        });


        FloatingActionButton fabReport = (FloatingActionButton) findViewById(R.id.fabReport);
        fabReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser() != null){

                    if (currentLat != null && currentLong != null){
                        bottomNavigationView.setItemSelected(R.id.map, true);


                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                        reportLayout = inflater.inflate(R.layout.report_popup, null);


                        Button popupReportReport = (Button) reportLayout.findViewById(R.id.reportButton);
                        Button popupReportPhoto = (Button) reportLayout.findViewById(R.id.popupReportPhoto);
                        EditText popupReportDetails = (EditText) reportLayout.findViewById(R.id.popupReportDetails);
                        CardView popupReportFailed = (CardView) findViewById(R.id.popupReportFailed);

                        popupReportReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (hasConnection == true){





                                    Date currentTime = Calendar.getInstance().getTime();


                                    Map<String, Object> data = new HashMap<>();
                                    data.put("reporter_id", loadId);
                                    data.put("reporter_address", loadAddress);
                                    data.put("reporter_name", loadName);
                                    data.put("reporter_phone", loadNumber);
                                    data.put("date", currentTime);
                                    data.put("details",popupReportDetails.getText().toString());
                                    data.put("latitude",currentLat);
                                    data.put("longitude",currentLong);


                                    DocumentReference documentReference = db.collection("Emergencies").document();
                                    documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            reportDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Report submitted", Toast.LENGTH_SHORT).show();

                                        }

                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            popupReportFailed.setVisibility(VISIBLE);
                                            Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();


                                        }
                                    });







                                }else{


                                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                                    smsLayout = inflater.inflate(R.layout.offline_report, null);


                                    Button proceedButton = (Button) smsLayout.findViewById(R.id.proceedButton);
                                    proceedButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            permissionCheck(loadName,loadAddress,popupReportDetails.getText().toString(),currentLat,currentLong);
                                            smsDialog.dismiss();
                                            reportDialog.dismiss();
                                        }
                                    });

                                    Button cancelButton = (Button) smsLayout.findViewById(R.id.cancelButton);
                                    cancelButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            smsDialog.dismiss();
                                        }
                                    });


                                    builder.setView(smsLayout);
                                    smsDialog = builder.create();
                                    smsDialog.setCancelable(false);
                                    smsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    smsDialog.show();




                                }


                            }
                        });


                        builder.setView(reportLayout);
                        reportDialog = builder.create();

                        reportDialog.setCancelable(true);
                        reportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        reportDialog.show();


                        //   mapboxMap.clear();
                        //loadedStyle.removeLayer(symbolLayer);
                        //mapView.invalidate();


                    }else{
                        Toast.makeText(MainActivity.this, "Current location is empty", Toast.LENGTH_SHORT).show();
                    }



                }else{

                    userLayout.setVisibility(VISIBLE);
                    Toast.makeText(MainActivity.this, "You need to sign in first", Toast.LENGTH_SHORT).show();

                }






            }

        });



        bottomNavigationView = (ChipNavigationBar) findViewById(R.id.menu);

        bottomNavigationView.setItemSelected(R.id.map, true);

        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i) {

                    case R.id.map:

                        emergencyCard.setVisibility(View.GONE);
                        responderCard.setVisibility(View.GONE);
                        sideButtons.setVisibility(VISIBLE);
                        break;
                    case R.id.emergency:
                        emergencyCard.setVisibility(VISIBLE);
                        responderCard.setVisibility(View.GONE);
                        sideButtons.setVisibility(View.GONE);
                        respondCard.setVisibility(GONE);
                        if (emergency_myLists.isEmpty()){
                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "List is empty", Snackbar.LENGTH_LONG)
                                    .setAnchorView(R.id.bottom_app_bar)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();

                        }

                        break;
                    case R.id.responder:
                        emergencyCard.setVisibility(View.GONE);
                        respondCard.setVisibility(GONE);
                        if (responder_myLists.isEmpty()){

                            View parentLayout = findViewById(android.R.id.content);
                            Snackbar.make(parentLayout, "List is empty", Snackbar.LENGTH_LONG)
                                    .setAnchorView(R.id.bottom_app_bar)
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                        }else{
                            responderCard.setVisibility(VISIBLE);
                            sideButtons.setVisibility(VISIBLE);

                        }

                        break;
                    default:
                        break;
                }
            }
        });


        runBackground();
        checkConnection();



        //start

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, 100);
            }
        }

        // this is a special permission required only by devices using
        // Android Q and above. The Access Background Permission is responsible
        // for populating the dialog with "ALLOW ALL THE TIME" option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
        }

        // check for BatteryOptimization,
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
                askIgnoreOptimization();
            }
        }

        // start the service
        SensorService sensorService = new SensorService();
        Intent intent = new Intent(this, sensorService.getClass());
        if (!isMyServiceRunning(sensorService.getClass())) {
            startService(intent);
        }




        //end



    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    // battery optimisation constraints from the App
    private void askIgnoreOptimization() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST);
        }

    }



    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        if(bottomNavigationView.getSelectedItemId() == R.id.map){
            sideButtons.setVisibility(VISIBLE);
            respondCard.setVisibility(GONE);
        }

        if (locationMarker != null){
            mapboxMap.removeMarker(locationMarker);
        }

        return false;
    }



    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                MainActivity.this.mapboxMap = mapboxMap;
                MainActivity.this.loadedStyle = style;

                buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);
                buildingPlugin.setMinZoomLevel(15f);
                buildingPlugin.setVisibility(true);
                enableLocationComponent(style);

                mapboxMap.addOnMapClickListener(MainActivity.this);




                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(10.953476, 125.030895))
                        .zoom(16.5)
                        .tilt(60)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);


                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(MainActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }


                });


                mapboxMap.addOnMoveListener(new MapboxMap.OnMoveListener() {
                    @Override
                    public void onMoveBegin(@NonNull MoveGestureDetector detector) {
                        sideButtons.setVisibility(VISIBLE);
                        respondCard.setVisibility(GONE);

                    }

                    @Override
                    public void onMove(MoveGestureDetector detector) {


                    }
                    @Override
                    public void onMoveEnd(MoveGestureDetector detector) {


                    }
                });


                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {

                        PointF screenPoint = mapboxMap.getProjection().toScreenLocation(point);
                        List<Feature> features = mapboxMap.queryRenderedFeatures(screenPoint, "layer");
                        if (!features.isEmpty()) {
                            Feature selectedFeature = features.get(0);
                            String title = selectedFeature.getStringProperty("details");
                            selectedFeature.addBooleanProperty("selected", true);
                            Toast.makeText(MainActivity.this, "You selected " + title, Toast.LENGTH_SHORT).show();

                        }

                        //    Toast.makeText(MainActivity.this, String.format("User clicked at: %s", point.toString()), Toast.LENGTH_LONG).show();

                        return true;
                    }

                });
                View view = findViewById(android.R.id.content);
                BellAnimation bellAnimation = new BellAnimation();
                bellAnimation.StartAnimateNotif(view);

                loadProfile();

            }
        });


    }

    public void checkNumber(String number){

        db.collection("Users")
                .whereEqualTo("number", number)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().isEmpty()){


                          //  progressDialog.setVisibility(View.GONE);
                           // idBox.setError("ID Number does not exist");
                           // Toast.makeText(Login.this, "ID Number does not exist", Toast.LENGTH_SHORT).show();
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                            setupLayout = inflater.inflate(R.layout.setup_popup, null);



                            Button registerConfirm = (Button) setupLayout.findViewById(R.id.registerConfirm);
                            registerType= (Switch) setupLayout.findViewById(R.id.registerType);

                            TextInputEditText registerName = (TextInputEditText) setupLayout.findViewById(R.id.registerName);
                            TextInputEditText registerAge = (TextInputEditText) setupLayout.findViewById(R.id.registerAge);
                            TextInputEditText registerAddress = (TextInputEditText) setupLayout.findViewById(R.id.registerAddress);
                            LinearLayout registerCategory = (LinearLayout) setupLayout.findViewById(R.id.registerCategory);

                            ConstraintLayout typeFire = (ConstraintLayout)setupLayout.findViewById(R.id.typeFire);
                            ConstraintLayout typeMedical = (ConstraintLayout)setupLayout.findViewById(R.id.typeMedical);
                            ConstraintLayout typePolice = (ConstraintLayout)setupLayout.findViewById(R.id.typePolice);
                            ConstraintLayout typeTraffic = (ConstraintLayout)setupLayout.findViewById(R.id.typeTraffic);
                            ConstraintLayout typeOthers = (ConstraintLayout)setupLayout.findViewById(R.id.typeOthers);


                            ConstraintLayout registerAttachments = (ConstraintLayout)setupLayout.findViewById(R.id.registerAttachments);

                            imageDocu = setupLayout.findViewById(R.id.imageDocu);
                            imageSelfie = setupLayout.findViewById(R.id.imageSelfie);


                            docuImage = setupLayout.findViewById(R.id.docuImage);
                            docuDelete = setupLayout.findViewById(R.id.docuDelete);
                            selfieImage = setupLayout.findViewById(R.id.selfieImage);
                            selfieDelete = setupLayout.findViewById(R.id.selfieDelete);

                            uploadDocu = setupLayout.findViewById(R.id.uploadDocu);
                            uploadSelfie = setupLayout.findViewById(R.id.uploadSelfie);







                            registerType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked == true){


                                        registerCategory.setVisibility(VISIBLE);
                                        registerAttachments.setVisibility(VISIBLE);




                                    }else{

                                        registerCategory.setVisibility(GONE);
                                        registerAttachments.setVisibility(GONE);
                                    }
                                }
                            });

                            typeFire.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeFire.setBackgroundColor(Color.WHITE);
                                    typeMedical.setBackgroundColor(Color.WHITE);
                                    typePolice.setBackgroundColor(Color.WHITE);
                                    typeTraffic.setBackgroundColor(Color.WHITE);
                                    typeOthers.setBackgroundColor(Color.WHITE);

                                    typeChecked = "Fire";
                                    typeFire.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.silver));

                                }
                            });

                            typeMedical.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeFire.setBackgroundColor(Color.WHITE);
                                    typeMedical.setBackgroundColor(Color.WHITE);
                                    typePolice.setBackgroundColor(Color.WHITE);
                                    typeTraffic.setBackgroundColor(Color.WHITE);
                                    typeOthers.setBackgroundColor(Color.WHITE);

                                    typeChecked = "Medical";
                                    typeMedical.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.silver));

                                }
                            });

                            typePolice.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeFire.setBackgroundColor(Color.WHITE);
                                    typeMedical.setBackgroundColor(Color.WHITE);
                                    typePolice.setBackgroundColor(Color.WHITE);
                                    typeTraffic.setBackgroundColor(Color.WHITE);
                                    typeOthers.setBackgroundColor(Color.WHITE);

                                    typeChecked = "Police";
                                    typePolice.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.silver));
                                }
                            });

                            typeTraffic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeFire.setBackgroundColor(Color.WHITE);
                                    typeMedical.setBackgroundColor(Color.WHITE);
                                    typePolice.setBackgroundColor(Color.WHITE);
                                    typeTraffic.setBackgroundColor(Color.WHITE);
                                    typeOthers.setBackgroundColor(Color.WHITE);

                                    typeChecked = "Traffic";
                                    typeTraffic.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.silver));
                                }
                            });


                            typeOthers.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    typeFire.setBackgroundColor(Color.WHITE);
                                    typeMedical.setBackgroundColor(Color.WHITE);
                                    typePolice.setBackgroundColor(Color.WHITE);
                                    typeTraffic.setBackgroundColor(Color.WHITE);
                                    typeOthers.setBackgroundColor(Color.WHITE);

                                    typeChecked = "Others";
                                    typeOthers.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.silver));
                                }
                            });

                            docuDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imageDocu.setVisibility(View.GONE);
                                    uploadDocu.setVisibility(View.VISIBLE);
                                }
                            });

                            selfieDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imageSelfie.setVisibility(View.GONE);
                                    uploadSelfie.setVisibility(View.VISIBLE);
                                }
                            });


                            uploadDocu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA)
                                            == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_DENIED){

                                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
                                        Toast.makeText(MainActivity.this, "Not granted", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Granted", Toast.LENGTH_SHORT).show();
                                        showSelection("document");
                                        //  takePhoto("document");
                                    }

                                }
                            });


                            uploadSelfie.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA)
                                            == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            == PackageManager.PERMISSION_DENIED){

                                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);

                                    }else{

                                        showWarning("selfie");

                                        // Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                        // startActivity(intent);
                                    }
                                }
                            });


                            registerConfirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String name = registerName.getText().toString().trim();
                                    String age = registerAge.getText().toString().trim();
                                    String address = registerAddress.getText().toString().trim();




                                    if (name.isEmpty()){
                                        registerName.setError("Name is required");
                                        registerName.requestFocus();
                                        return;
                                    }

                                    if (registerAge.getVisibility() == VISIBLE){
                                        if (age.isEmpty()){
                                            registerAge.setError("Age is required");
                                            registerAge.requestFocus();
                                            return;
                                        }
                                    }


                                    if (address.isEmpty()){
                                        registerAddress.setError("Address is required");
                                        registerAddress.requestFocus();
                                        return;
                                    }

                                    if (registerType.isChecked()){
                                        if  (typeChecked == null){
                                            Toast.makeText(MainActivity.this, "Please select the type of responder", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (imageDocu.getVisibility() == View.VISIBLE && imageSelfie.getVisibility() == View.VISIBLE){

                                        }else{
                                            Toast.makeText(MainActivity.this, "Upload your selfie and documents first", Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                    }


                                    Date currentTime = Calendar.getInstance().getTime();

                                    Double coordinates = null;


                                    Map<String, Object> data = new HashMap<>();

                                    data.put("name", registerName.getText().toString());
                                    data.put("phone", number);

                                    if (registerAge.getVisibility() == VISIBLE){
                                        data.put("age", registerAge.getText().toString());
                                    }

                                    data.put("address", registerAddress.getText().toString());
                                    data.put("last_active",currentTime);


                                    if (hud !=null){
                                        hud.dismiss();
                                    }
                                    hud = KProgressHUD.create(MainActivity.this)
                                            .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                                            .setMaxProgress(100)
                                            .show();


                                    if (registerType.isChecked() == true){
                                        data.put("type", "responder");
                                        data.put("verified",false);
                                        data.put("classification",typeChecked);
                                        data.put("latitude",coordinates);
                                        data.put("longitude",coordinates);
                                        data.put("date_applied", currentTime);




                                        FirebaseStorage storageDocu = FirebaseStorage.getInstance();
                                        StorageReference storageRefDocu = storageDocu.getReference();

                                        StorageReference docuImageRef = storageRefDocu.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/document.jpg");

                                        docuImage.setDrawingCacheEnabled(true);
                                        docuImage.buildDrawingCache();
                                        Bitmap docuBitmap = ((BitmapDrawable) docuImage.getDrawable()).getBitmap();
                                        ByteArrayOutputStream docuBaos = new ByteArrayOutputStream();
                                        docuBitmap.compress(Bitmap.CompressFormat.JPEG, 100, docuBaos);
                                        byte[] docuData = docuBaos.toByteArray();


                                        UploadTask uploadTaskDocu = docuImageRef.putBytes(docuData);
                                        uploadTaskDocu.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {

                                                Toast.makeText(MainActivity.this, exception.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                hud.setLabel("Uploading document: " + String.format("%.2f", progress) + "%");
                                                hud.setProgress(Integer.valueOf((int) progress));
                                                hud.setCancellable(false);
                                                // Log.d(TAG, "Upload is " + progress + "% done");
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            }
                                        });


                                        FirebaseStorage storageSelfie = FirebaseStorage.getInstance();
                                        StorageReference storageRefSelfie = storageSelfie.getReference();


                                        StorageReference selfieImageRef = storageRefSelfie.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/selfie.jpg");
                                        selfieImage.setDrawingCacheEnabled(true);
                                        selfieImage.buildDrawingCache();
                                        Bitmap selfieBitmap = ((BitmapDrawable) selfieImage.getDrawable()).getBitmap();
                                        ByteArrayOutputStream selfieBaos = new ByteArrayOutputStream();
                                        selfieBitmap.compress(Bitmap.CompressFormat.JPEG, 100, selfieBaos);
                                        byte[] selfieData = selfieBaos.toByteArray();

                                        UploadTask uploadTaskSelfie = selfieImageRef.putBytes(selfieData);
                                        uploadTaskSelfie.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                Toast.makeText(MainActivity.this, exception.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                hud.setLabel("Uploading selfie: " + String.format("%.2f", progress) + "%");
                                                hud.setProgress(Integer.valueOf((int) progress));
                                                hud.setCancellable(false);
                                                // Log.d(TAG, "Upload is " + progress + "% done");
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                DocumentReference documentReference = db.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                                                documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        hud.dismiss();
                                                        nameDialog.dismiss();
                                                        loadProfile();
                                                        //  Toast.makeText(MainActivity.this, "Report submitted", Toast.LENGTH_SHORT).show();

                                                    }

                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        hud.dismiss();

                                                        Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                            }
                                        });






                                    }else{
                                        data.put("date_registered", currentTime);
                                        data.put("type", "nonresponder");
                                        data.put("classification",null);
                                        data.put("latitude",coordinates);
                                        data.put("longitude",coordinates);

                                        hud.setLabel("Please wait");


                                        DocumentReference documentReference = db.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                                        documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                hud.dismiss();
                                                nameDialog.dismiss();
                                                loadProfile();
                                                //  Toast.makeText(MainActivity.this, "Report submitted", Toast.LENGTH_SHORT).show();

                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                hud.dismiss();


                                                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                            }
                                        });


                                    }








                                }
                            });


                            builder.setView(setupLayout);
                            nameDialog = builder.create();

                            nameDialog.setCancelable(false);
                            nameDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            nameDialog.show();


                        }else{

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.exists()){
                                       loadProfile();
                                    }

                                }


                            } else {
                                Toast.makeText(MainActivity.this, "Failed to login: "+  task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }


                    }
                });


    }

    public void loadProfile(){

        numberLayout.setVisibility(GONE);
        userLayout.setVisibility(VISIBLE);

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userNumber = (TextView) findViewById(R.id.userNumber);
        ImageView userImage = (ImageView) findViewById(R.id.userImage);


        if (firebaseAuth.getCurrentUser() != null){

            db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    if (value.exists()) {
                        if (value.getBoolean("disabled") != null && value.getBoolean("disabled") == true){

                            Toast.makeText(MainActivity.this, "Your account has been disabled", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            firebaseAuth.signOut();
                            userName.setText("Guest");
                            userLogin.setText("Sign in");
                            userNumber.setVisibility(GONE);
                            userImage.setImageResource(R.drawable.account);
                            userImage.setPadding(20,20,20,20);
                            return;

                        }


                        loadId = value.getId();
                        loadName = value.getString("name");
                        loadAddress = value.getString("address");
                        loadNumber = value.getString("phone");
                        loadType = value.getString("type");
                        loadClass = value.getString("classification");


                        userName.setText(loadName);
                        userNumber.setText(loadNumber);
                        userNumber.setVisibility(VISIBLE);
                        userLogin.setText("Sign out");
                        userImage.setImageResource(R.drawable.account);
                        userImage.setPadding(20,20,20,20);

                        if (loadClass != null && loadType.matches("responder")){
                            if  (value.getBoolean("verified") == true){

                                if (loadClass.matches("Fire")){
                                    userImage.setImageResource(R.drawable.flame);
                                    userImage.setPadding(20,20,20,20);
                                }
                                else if (loadClass.matches("Medical")){
                                    userImage.setImageResource(R.drawable.hospital);
                                    userImage.setPadding(20,20,20,20);
                                }
                                else if (loadClass.matches("Police")){
                                    userImage.setImageResource(R.drawable.police);
                                    userImage.setPadding(20,20,20,20);
                                }
                                else if (loadClass.matches("Traffic")){
                                    userImage.setImageResource(R.drawable.traffic);
                                    userImage.setPadding(20,20,20,20);

                                }
                                else if (loadClass.matches("Others")){
                                    userImage.setImageResource(R.drawable.others);
                                    userImage.setPadding(20,20,20,20);
                                }

                            }else{
                                Toast.makeText(MainActivity.this, "Your account is not yet verified", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();

                                userName.setText("Guest");
                                userLogin.setText("Sign in");
                                userNumber.setVisibility(GONE);
                                userImage.setImageResource(R.drawable.account);
                                userImage.setPadding(20,20,20,20);
                             //   displayEmergencies(loadedStyle);
                             //   displayResponder(loadedStyle);
                              //  return;
                            }


                        }
                        if (firebaseAuth.getCurrentUser() != null){
                            notificationListener();
                        }



                    }else{

                        checkNumber(firebaseAuth.getCurrentUser().getPhoneNumber());

                    }


                }
            });


        }else{
            if (hud !=null){
                hud.dismiss();
            }
            hud = KProgressHUD.create(MainActivity.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Signing out")
                    .setCancellable(false)
                    .show();
         //   progressDialog.setMessage("Signing out");
          //  progressDialog.show();
            firebaseAuth.signOut();
            userName.setText("Guest");
            userLogin.setText("Sign in");
            userNumber.setVisibility(GONE);
            userImage.setImageResource(R.drawable.account);
            userImage.setPadding(20,20,20,20);

         //   progressDialog.dismiss();
            hud.dismiss();
        }




        displayEmergencies(loadedStyle);
        displayResponder(loadedStyle);

        loadAdmin();


    }

    public void verification(String number){
        if (hud !=null){
            hud.dismiss();
        }

        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Verifying phone number")
                .setCancellable(false)
                .show();

       // progressDialog.setMessage("Verifying phone number");
       // progressDialog.show();

       // firebaseAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);

        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(onVerificationStateChangedCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

    }

    public void resendVerification(String number, PhoneAuthProvider.ForceResendingToken token){

        if (hud !=null){
            hud.dismiss();
        }
        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Resending verification code")
                .setCancellable(false)
                .show();

      //  progressDialog.setMessage("Resending verification code");
      //  progressDialog.show();

        PhoneAuthOptions phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setForceResendingToken(token)
                .setCallbacks(onVerificationStateChangedCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

    }

    public void verifyPhoneNumber(String verificationId, String code){

        if (hud !=null){
            hud.dismiss();
        }
        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Verifying code")
                .setCancellable(false)
                .show();
     //   progressDialog.setMessage("Verifying code");
     //   progressDialog.show();

        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,code);
        signInCredential(phoneAuthCredential);

    }

    public void signInCredential(PhoneAuthCredential phoneAuthCredential){
        if (hud != null){
            hud.setLabel("Logging in");
        }

   //     progressDialog.setMessage("Logging in");

        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        hud.dismiss();
                       // progressDialog.dismiss();
                        loadProfile();
                      //  checkNumber(firebaseAuth.getCurrentUser().getPhoneNumber());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hud.dismiss();
               // progressDialog.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void showWarning(String type){
        myLayoutWarning = inflater.inflate(R.layout.warning, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);

        Button buttonOk = (Button)myLayoutWarning.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.matches("document")){
                    takePhoto("document");
                    dialogWarning.dismiss();
                }
                if (type.matches("selfie")){

                    takePhoto("selfie");
                    dialogWarning.dismiss();
                }

            }
        });





        builder.setView(myLayoutWarning);
        dialogWarning = builder.create();

        dialogWarning.setCancelable(false);
        dialogWarning.show();

    }

    public void showSelection(String selectiontype){
        myLayoutSelection = inflater.inflate(R.layout.selection, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);


        LinearLayout pickSelection = (LinearLayout)myLayoutSelection.findViewById(R.id.pickSelection);
        LinearLayout takeSelection = (LinearLayout)myLayoutSelection.findViewById(R.id.takeSelection);

        pickSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectiontype.matches("document")){
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, 300);
                    dialogSelection.dismiss();

                }

            }
        });


        takeSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectiontype.matches("document")){
                    showWarning("document");
                    //takePhoto("document");
                    dialogSelection.dismiss();

                }
                if (selectiontype.matches("selfie")){
                    showWarning("selfie");
                    // takePhoto("selfie");
                    dialogSelection.dismiss();
                }

            }
        });

        builder.setView(myLayoutSelection);
        dialogSelection = builder.create();

        dialogSelection.setCancelable(true);
        dialogSelection.show();
    }

    public void takePhoto(String type){
        if (type.matches("document")){
            dispatchTakePictureIntentDocument();
            //   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //   startActivityForResult(intent,100);

        }
        if (type.matches("selfie")){

            dispatchTakePictureIntentSelfie();
            //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //  startActivityForResult(intent,200);

        }

    }


    private void dispatchTakePictureIntentDocument() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            //    if (type.matches("document")){
            try {
                docuPhotoPath = null;
                File photoFile = null;
                // photoFile = createImageFileDocument();


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "document_Emergency_" + timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                );
                photoFile = image;

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("docuPhotoPath", image.getAbsolutePath());
                editor.apply();

                docuPhotoPath = image.getAbsolutePath();



                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, 100);
                }

            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();


            }


        }
    }




    private void dispatchTakePictureIntentSelfie() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                selfiePhotoPath = null;
                File photoFile = null;
                //   photoFile = createImageFileSelfie();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "selfie_Emergency_" + timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                );

                photoFile = image;

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selfiePhotoPath", image.getAbsolutePath());
                editor.apply();

                selfiePhotoPath = image.getAbsolutePath();


                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);


                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                    takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                    takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                    takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    startActivityForResult(takePictureIntent, 200);

                }

            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }


        }
    }

    public void reviewPhotoDocument(){

        myLayoutReview = inflater.inflate(R.layout.review, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);


        ImageView imageReview = (ImageView)myLayoutReview.findViewById(R.id.imageReview);
        imageReview.setImageURI(Uri.fromFile(new File(docuPhotoPath)));



        Button buttonConfirm = (Button)myLayoutReview.findViewById(R.id.buttonConfirm);
        Button buttonRetry = (Button)myLayoutReview.findViewById(R.id.buttonRetry);

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto("document");
                dialogReview.dismiss();

            }
        });



        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(docuPhotoPath);

                //ImageView imageView = (ImageView) findViewById(R.id.docuImage);
                try {
                    docuImage.setImageURI(Uri.fromFile(file));
                    uploadDocu.setVisibility(View.GONE);
                    imageDocu.setVisibility(View.VISIBLE);
                    dialogReview.dismiss();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }

            }



        });



        builder.setView(myLayoutReview);
        dialogReview = builder.create();

        dialogReview.setCancelable(false);
        dialogReview.show();

    }

    public void reviewPhotoSelfie(){

        myLayoutReview = inflater.inflate(R.layout.review, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);


        ImageView imageReview = (ImageView)myLayoutReview.findViewById(R.id.imageReview);
        imageReview.setImageURI(Uri.fromFile(new File(selfiePhotoPath)));


        Button buttonConfirm = (Button)myLayoutReview.findViewById(R.id.buttonConfirm);
        Button buttonRetry = (Button)myLayoutReview.findViewById(R.id.buttonRetry);

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto("selfie");
                dialogReview.dismiss();

            }
        });



        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(selfiePhotoPath);

              //  ImageView imageView = (ImageView) findViewById(R.id.selfieImage);
                try {
                    selfieImage.setImageURI(Uri.fromFile(file));
                    uploadSelfie.setVisibility(View.GONE);
                    imageSelfie.setVisibility(View.VISIBLE);
                    dialogReview.dismiss();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }


            }
        });



        builder.setView(myLayoutReview);
        dialogReview = builder.create();

        dialogReview.setCancelable(false);
        dialogReview.show();

    }



    public void reviewPhotoDocumentPick(Bitmap bitmap){

        myLayoutReview = inflater.inflate(R.layout.review, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);


        ImageView imageReview = (ImageView)myLayoutReview.findViewById(R.id.imageReview);
        imageReview.setImageBitmap(bitmap);


        Button buttonConfirm = (Button)myLayoutReview.findViewById(R.id.buttonConfirm);
        Button buttonRetry = (Button)myLayoutReview.findViewById(R.id.buttonRetry);

        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 300);
                dialogReview.dismiss();

            }
        });



        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



              //  ImageView imageView = (ImageView) findViewById(R.id.docuImage);
                try {

                    docuImage.setImageBitmap(bitmap);
                    uploadDocu.setVisibility(View.GONE);
                    imageDocu.setVisibility(View.VISIBLE);
                    dialogReview.dismiss();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }


            }
        });



        builder.setView(myLayoutReview);
        dialogReview = builder.create();

        dialogReview.setCancelable(false);
        dialogReview.show();

    }


    public void uploadPhoto(String id){
        if (hud !=null){
            hud.dismiss();
        }
        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                .setMaxProgress(100)
                .show();

        FirebaseStorage storageDocu = FirebaseStorage.getInstance();
        StorageReference storageRefDocu = storageDocu.getReference();

        StorageReference docuImageRef = storageRefDocu.child("users/"+id+"/document.jpg");


        docuImage.setDrawingCacheEnabled(true);
        docuImage.buildDrawingCache();
        Bitmap docuBitmap = ((BitmapDrawable) docuImage.getDrawable()).getBitmap();
        ByteArrayOutputStream docuBaos = new ByteArrayOutputStream();
        docuBitmap.compress(Bitmap.CompressFormat.JPEG, 100, docuBaos);
        byte[] docuData = docuBaos.toByteArray();


        UploadTask uploadTaskDocu = docuImageRef.putBytes(docuData);
        uploadTaskDocu.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(MainActivity.this, exception.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                hud.setLabel("Uploading document: " + String.format("%.2f", progress) + "%");
                hud.setProgress(Integer.valueOf((int) progress));
                hud.setCancellable(false);
                // Log.d(TAG, "Upload is " + progress + "% done");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        FirebaseStorage storageSelfie = FirebaseStorage.getInstance();
        StorageReference storageRefSelfie = storageSelfie.getReference();


        StorageReference selfieImageRef = storageRefSelfie.child("users/"+id+"/selfie.jpg");
        selfieImage.setDrawingCacheEnabled(true);
        selfieImage.buildDrawingCache();
        Bitmap selfieBitmap = ((BitmapDrawable) selfieImage.getDrawable()).getBitmap();
        ByteArrayOutputStream selfieBaos = new ByteArrayOutputStream();
        selfieBitmap.compress(Bitmap.CompressFormat.JPEG, 100, selfieBaos);
        byte[] selfieData = selfieBaos.toByteArray();

        UploadTask uploadTaskSelfie = selfieImageRef.putBytes(selfieData);
        uploadTaskSelfie.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity.this, exception.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                hud.setLabel("Uploading selfie: " + String.format("%.2f", progress) + "%");
                hud.setProgress(Integer.valueOf((int) progress));
                hud.setCancellable(false);
                // Log.d(TAG, "Upload is " + progress + "% done");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                hud.dismiss();
            }
        });

    }






    public void checkConnection(){
        CardView appStatus = (CardView) findViewById(R.id.appStatus);

        db.collection("Emergencies")
                .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {

                            if (querySnapshot.getMetadata().isFromCache() == true){
                                appStatus.setCardBackgroundColor(Color.RED);
                                hasConnection = false;

                            }else{
                                appStatus.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.lightGreen));
                                hasConnection = true;
                            }

                         }
                });


    }

    public void updateLocation(){

        if (currentLat != null && currentLong != null){
            DocumentReference documentReferenceLat = db.collection("Emergencies").document("SrmU80ax6m3P8ftOjl6n");
            documentReferenceLat.update("latitude",currentLat).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                   // isOnline(true);

                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                   // isOnline(false);

                }
            });
            DocumentReference documentReferenceLong = db.collection("Emergencies").document("SrmU80ax6m3P8ftOjl6n");
            documentReferenceLong.update("longitude",currentLong).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Toast.makeText(MainActivity.this, "ggasdad111", Toast.LENGTH_SHORT).show();
                   // isOnline(true);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                   // isOnline(false);
                }
            });
        }


    }




    public void displayResponder(@NonNull Style loadedStyle) {

        responder_rv_pos = 0;
        mapboxMap.clear();


        List<Feature> featurelist = new ArrayList<>();

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.responderlocation);
        loadedStyle.addImage("responderImage", icon);

        IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
        Icon markerIcon = iconFactory.fromResource(R.drawable.responderlocation);


        db.collection("Users").whereEqualTo("type", "responder").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                responder_myLists.clear();
                ArrayList<String> numbers = new ArrayList<>();

                Parcelable recylerViewState = responder_rv.getLayoutManager().onSaveInstanceState();



                for (DocumentSnapshot document : value.getDocuments()) {
                    if (document.exists()) {

                        loadedStyle.removeLayer(RESPONDERMARKER_LAYER_ID);
                        loadedStyle.removeSource(RESPONDERGEOJSON_SOURCE_ID);


                        Timestamp timestamp = (Timestamp) document.getTimestamp("last_active");
                        Date last_active = timestamp.toDate();

                        if (document.getDouble("longitude") != null && document.getDouble("latitude") != null){

                                         if (firebaseAuth.getCurrentUser() != null) {

                            if (!document.getId().matches(firebaseAuth.getCurrentUser().getUid())) {
                                responder_myLists.add(new ResponderList(null, document.getString("name"), last_active, document.getString("classification"), document.getString("phone"), document.getDouble("latitude"), document.getDouble("longitude")));
                                JsonObject properties = new JsonObject();
                                properties.addProperty("details", document.getString("details"));


                                featurelist.add(Feature.fromGeometry(Point.fromLngLat(document.getDouble("longitude"), document.getDouble("latitude"))));
                                responderMarker = mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(document.getDouble("latitude"), document.getDouble("longitude")))
                                        .title(document.getString("name"))
                                        .setSnippet(document.getString("phone") + System.getProperty("line.separator") + document.getString("classification"))

                                        .icon(markerIcon));
                                numbers.add(document.getString("phone"));

                            } else {
                                responder_myLists.add(new ResponderList(null, "You", last_active, document.getString("classification"), document.getString("phone"), document.getDouble("latitude"), document.getDouble("longitude")));

                            }
                        } else {

                            responder_myLists.add(new ResponderList(null, document.getString("name"), last_active, document.getString("classification"), document.getString("phone"), document.getDouble("latitude"), document.getDouble("longitude")));
                            JsonObject properties = new JsonObject();
                            properties.addProperty("details", document.getString("details"));


                            featurelist.add(Feature.fromGeometry(Point.fromLngLat(document.getDouble("longitude"), document.getDouble("latitude"))));
                            responderMarker = mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(document.getDouble("latitude"), document.getDouble("longitude")))
                                    .title(document.getString("name"))
                                    .setSnippet(document.getString("phone") + System.getProperty("line.separator") + document.getString("classification"))

                                    .icon(markerIcon));

                        }

                        }



                    }
                }


                if (!responder_myLists.isEmpty()) {


                    FeatureCollection featureCollection = FeatureCollection.fromFeatures(featurelist);
                    responderGeoJsonSource = new GeoJsonSource(RESPONDERGEOJSON_SOURCE_ID, featureCollection);
                    loadedStyle.addSource(responderGeoJsonSource);


                    responderSymbolLayer = new SymbolLayer(RESPONDERMARKER_LAYER_ID, RESPONDERGEOJSON_SOURCE_ID)
                            .withProperties(PropertyFactory.iconImage("responderImage"),
                                    PropertyFactory.textAnchor("top".toString()),
                                    //PropertyFactory.textField(Expression.id()),
                                    //  PropertyFactory.textSize(12f),
                                    // PropertyFactory.textColor(Color.RED),
                                    //  PropertyFactory.textKeepUpright(false),
                                    //  PropertyFactory.textAllowOverlap(true),

                                    PropertyFactory.textIgnorePlacement(false),
                                    // PropertyFactory.iconAnchor("top".toString()),
                                    PropertyFactory.iconIgnorePlacement(false),
                                    PropertyFactory.circleColor(Color.BLACK),
                                    //           PropertyFactory.iconOffset(new Float[]{0f,-9f}),
                                    PropertyFactory.iconAllowOverlap(false),
                                    ///     PropertyFactory.textOpacity(0.5f),
                                    PropertyFactory.textSize(12f));


                    Gson gson = new Gson();
                    String json = gson.toJson(numbers);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("numbers",json);
                    editor.commit();




                    responder_adapter = new ResponderAdapter(responder_myLists, MainActivity.this);
                    responder_rv.setAdapter(responder_adapter);

                    responder_rv.getLayoutManager().onRestoreInstanceState(recylerViewState);
                    bottomNavigationView.showBadge(R.id.responder, responder_myLists.size());

                    loadedStyle.addLayer(responderSymbolLayer);
                }


            }
        });

        Uri data = getIntent().getData();
        if (data != null){
            Double sLat = null;
            Double sLong = null;
            String string = data.toString()
                    .replaceAll("http://emergencyresponse.com/municipal/", "");

            String[] splitted = string.split("/");
            for (String item : splitted)
            {
                String[] separated = item.split(":");
                if (separated[0].matches("latitude")){
                    sLat = Double.parseDouble(separated[1]);
                }

                if (separated[0].matches("longitude")){
                    sLong = Double.parseDouble(separated[1]);
                }

            }

            locationMarker = mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(sLat, sLong)).title("SMS").setSnippet("Location acquired from sms link"));


            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(sLat, sLong))
                    .zoom(16)
                    .tilt(0)
                    .build();

            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);



        }else{

        }

    }


    public void loadAdmin(){

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        MaterialCardView signOutUser = (MaterialCardView) findViewById(R.id.signoutUser);
        CardView adminLayout = (CardView) findViewById(R.id.adminLayout);
        LinearLayout settingsLayout = (LinearLayout) findViewById(R.id.settingsLayout);
        TextInputEditText currentPassword = (TextInputEditText) findViewById(R.id.currentPassword);
        TextInputEditText retypePassword = (TextInputEditText) findViewById(R.id.retypePassword);
        TextInputEditText newPassword = (TextInputEditText) findViewById(R.id.newPassword);
        Button saveButton = (Button)findViewById(R.id.saveButton) ;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("Admin").document("admin").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if (currentPassword.getText().toString().matches(document.getString("password"))){
                                    if(newPassword.getText().toString().matches(retypePassword.getText().toString())){

                                        db.collection("Admin").document("admin").update("password", newPassword.getText().toString())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(MainActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                        currentPassword.setText("");
                                                        newPassword.setText("");
                                                        retypePassword.setText("");
                                                        hideKeyboard();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }else{
                                        retypePassword.setError("Password does not match");
                                        retypePassword.requestFocus();
                                        hideKeyboard();
                                        return;
                                    }

                                }else{
                                    currentPassword.setError("Incorrect password");
                                    currentPassword.requestFocus();
                                    hideKeyboard();
                                    return;
                                }


                            }
                        } else {
                            Log.d(TAG, task.getException().toString(), task.getException());
                        }
                    }
                });




            }
        });



        signOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLayout.setVisibility(GONE);

            }
        });
        int responder_rv_pos;

        verify_rv = (RecyclerView) findViewById(R.id.verify_rec);
        verify_rv.setHasFixedSize(true);
        verify_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        verify_myLists = new ArrayList<>();

        users_rv = (RecyclerView) findViewById(R.id.users_rec);
        users_rv.setHasFixedSize(true);
        users_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        users_myLists = new ArrayList<>();


        db.collection("Users").whereEqualTo("verified",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                users_myLists.clear();



                Parcelable recylerViewState = users_rv.getLayoutManager().onSaveInstanceState();


                for (DocumentSnapshot document : value.getDocuments()) {
                    if (document.exists()) {

                        //    Toast.makeText(MainActivity.this, document.toString(), Toast.LENGTH_SHORT).show();


                        Date date_applied = null;
                            Timestamp timestamp = (Timestamp) document.getTimestamp("date_registered");
                            if (timestamp != null){
                                date_applied= timestamp.toDate();
                            }

                            if (document.getString("classification") != null){
                                if (document.getDouble("attempts") != null){
                                    users_myLists.add(new UsersList(document.getId(), document.getString("name"), document.getString("address"), document.getString("age"), document.getString("classification"), document.getString("phone"),date_applied,document.getString("type"),document.getDouble("attempts")));

                                }else{
                                    users_myLists.add(new UsersList(document.getId(), document.getString("name"), document.getString("address"), document.getString("age"), document.getString("classification"), document.getString("phone"),date_applied,document.getString("type"),null));

                                }

                            }else{
                                if (document.getDouble("attempts") != null){
                                    users_myLists.add(new UsersList(document.getId(), document.getString("name"), document.getString("address"), document.getString("age"), "nonresponder", document.getString("phone"),date_applied,document.getString("type"),document.getDouble("attempts")));
                                  //  Toast.makeText(MainActivity.this, document.getString("name") + " :" +String.valueOf(document.getDouble("attempts")), Toast.LENGTH_SHORT).show();

                                }else{
                                    users_myLists.add(new UsersList(document.getId(), document.getString("name"), document.getString("address"), document.getString("age"), "nonresponder", document.getString("phone"),date_applied,document.getString("type"),null));

                                }

                            }

                         //   Toast.makeText(MainActivity.this, document.getString("name"), Toast.LENGTH_SHORT).show();


                    }
                }

              //  Toast.makeText(MainActivity.this, String.valueOf(users_myLists.size()), Toast.LENGTH_SHORT).show();


                users_adapter = new UsersAdapter(users_myLists, MainActivity.this);
                users_rv.setAdapter(users_adapter);
                users_adapter.notifyDataSetChanged();

                db.collection("Users").whereEqualTo("type", "responder").whereEqualTo("verified",false).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        verify_myLists.clear();



                        Parcelable recylerViewState = responder_rv.getLayoutManager().onSaveInstanceState();


                        for (DocumentSnapshot document : value.getDocuments()) {
                            if (document.exists()) {

                                //    Toast.makeText(MainActivity.this, document.toString(), Toast.LENGTH_SHORT).show();


                                if (document.getDate("date_applied") != null){
                                    Timestamp timestamp = (Timestamp) document.getTimestamp("date_applied");
                                    Date date_applied = timestamp.toDate();
                                    verify_myLists.add(new VerifyList(document.getId(), document.getString("name"), document.getString("address"), document.getString("age"), document.getString("classification"), document.getString("phone"),date_applied));


                                }

                            }
                        }


                        verify_adapter = new VerifyAdapter(verify_myLists, MainActivity.this);
                        verify_rv.setAdapter(verify_adapter);
                        verify_adapter.notifyDataSetChanged();
                    }
                });



            }
        });










        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()) {

                    case "Users":
                        users_rv.setVisibility(VISIBLE);
                        verify_rv.setVisibility(INVISIBLE);
                        settingsLayout.setVisibility(GONE);


                        //  manageSettings.setVisibility(INVISIBLE);

                        break;
                    case "Verify":
                        users_rv.setVisibility(INVISIBLE);
                        verify_rv.setVisibility(VISIBLE);
                        settingsLayout.setVisibility(GONE);
                        verify_adapter = new VerifyAdapter(verify_myLists, MainActivity.this);
                        verify_rv.setAdapter(verify_adapter);
                        verify_adapter.notifyDataSetChanged();

                        // manageSettings.setVisibility(INVISIBLE);

                        break;
                    case "Settings":
                        verify_rv.setVisibility(INVISIBLE);
                        users_rv.setVisibility(INVISIBLE);
                        // manageSettings.setVisibility(VISIBLE);
                        settingsLayout.setVisibility(VISIBLE);
                        break;

                    default:
                        break;
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    public void notificationListener(){

        db.collection("Notifications").document(loadId).collection("notifications").orderBy("time_responded")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        my_notification_myLists.clear();


                        if (!snapshots.isEmpty()){

                            int count = 1;

                            int newlyAdded = 0;

                            for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {

                               if (documentSnapshot.getString("state").toString().matches("unread")){
                                   newlyAdded++;
                               }
                                my_notification_myLists.add(new MyNotificationList(documentSnapshot.getId(),documentSnapshot.getString("responder_name"),documentSnapshot.getString("responder_classification"),documentSnapshot.getString("responder_phone"),documentSnapshot.getString("status"),documentSnapshot.getDate("time_responded")));


                            }


                            myNotifCount = (TextView) findViewById(R.id.myNotifCount);
                            if (newlyAdded != 0){
                                if (notificationCard.getVisibility() != VISIBLE){
                                    myNotification.setVisibility(VISIBLE);
                                    myNotifCount.setText(String.valueOf(newlyAdded));
                                }
                            }else{
                                myNotification.setVisibility(GONE);
                            }



                        }else{
                            myNotification.setVisibility(GONE);
                        }

                        my_notification_adapter = new MyNotificationAdapter(my_notification_myLists, MainActivity.this);
                        my_notification_rv.setAdapter(my_notification_adapter);
                        my_notification_adapter.notifyDataSetChanged();



                    }
                });


    }


    public void displayEmergencies(@NonNull Style loadedStyle) {


        if (listenerRegistrationEmergency != null){
            listenerRegistrationEmergency.remove();
        }

        mapboxMap.clear();
        if (locationMarker != null){
            mapboxMap.removeMarker(locationMarker);
        }


        List<Feature> featurelist = new ArrayList<>();

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.reportlocation);
        loadedStyle.addImage("emergencyImage", icon);

        IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
        Icon markerIcon = iconFactory.fromResource(R.drawable.reportlocation);

        listenerRegistrationEmergency = db.collection("Emergencies").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        emergency_myLists.clear();
                        loadedStyle.removeLayer(EMERGENCYMARKER_LAYER_ID);
                        loadedStyle.removeSource(EMERGENCYGEOJSON_SOURCE_ID);


                       if (activityStart == false){

                           if (!snapshots.isEmpty() && loadType != null && loadType.matches("responder")){

                               int count = 1;

                               androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                               notificationLayout = inflater.inflate(R.layout.notification_layout, null);

                               ImageView notificationClose = (ImageView) notificationLayout.findViewById(R.id.notificationClose);

                               notification_rv = (RecyclerView) notificationLayout.findViewById(R.id.notification_rec);
                               notification_rv.setHasFixedSize(false);
                               notification_rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                               notification_myLists = new ArrayList<>();

                               notificationClose.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {

                                       notificationDialog.dismiss();
                                   }
                               });

                               int newlyAdded = 0;

                               for (DocumentChange dc : snapshots.getDocumentChanges()) {

                                   if (dc.getType() == DocumentChange.Type.ADDED) {

                                       newlyAdded++;

                                       Timestamp timestamp = (Timestamp) dc.getDocument().getTimestamp("date");
                                       Date date = timestamp.toDate();


                                       notification_myLists.add(new NotificationList(dc.getDocument().getId(), dc.getDocument().getString("reporter_name"),date,dc.getDocument().getString("details"),dc.getDocument().getDouble("latitude"), dc.getDocument().getDouble("longitude")));

                                       NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                       if(Build.VERSION.SDK_INT >= 26)
                                       {

                                           //When sdk version is larger than26
                                           String id = "channel_1";
                                           String description = "143";
                                           int importance = NotificationManager.IMPORTANCE_HIGH;
                                           NotificationChannel channel = new NotificationChannel(id, description, importance);
                                           channel.enableLights(true);
                                           channel.enableVibration(true);//
                                           manager.createNotificationChannel(channel);
                                           Notification notification = new Notification.Builder(MainActivity.this, id)
                                                   .setCategory(Notification.CATEGORY_MESSAGE)
                                                   .setSmallIcon(R.drawable.respond)
                                                   .setContentTitle(dc.getDocument().getString("reporter_name"))
                                                   .setContentText(dc.getDocument().getString("details"))
                                                   //.setContentIntent(pendingIntent)
                                                   .setAutoCancel(true)
                                                   .build();
                                           manager.notify(count, notification);
                                       }
                                       else
                                       {
                                           //When sdk version is less than26
                                           Notification notification = new NotificationCompat.Builder(MainActivity.this)
                                                   .setContentTitle(dc.getDocument().getString("reporter_name"))
                                                   .setContentText(dc.getDocument().getString("details"))
                                                   //  .setContentIntent(pendingIntent)
                                                   .setSmallIcon(R.drawable.respond)

                                                   .build();
                                           manager.notify(count,notification);
                                       }

                                       count++;

                                   }


                               }

                               notification_adapter = new NotificationAdapter(notification_myLists, MainActivity.this);
                               notification_rv.setAdapter(notification_adapter);

                               builder.setView(notificationLayout);
                               notificationDialog = builder.create();

                               notificationDialog.setCancelable(false);
                               notificationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                               notificationDialog.show();

                               WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                               layoutParams.copyFrom(notificationDialog.getWindow().getAttributes());
                               layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                               layoutParams.height =1000;
                               notificationDialog.getWindow().setAttributes(layoutParams);

                               if (newlyAdded == 0) {

                                   notificationDialog.dismiss();

                               }
                           }



                        }else{

                            activityStart = false;

                        }





                        for (DocumentSnapshot document : snapshots.getDocuments()) {


                            if (document.exists()){


                             //   loadedStyle.removeLayer(EMERGENCYMARKER_LAYER_ID);
                             //   loadedStyle.removeSource(EMERGENCYGEOJSON_SOURCE_ID);



                                if (document.getString("reporter_name") != null && document.getString("reporter_phone") !=null && document.getString("details") !=null && document.getDouble("latitude") != null && document.getDouble("longitude") != null){


                                    Timestamp timestamp = (Timestamp) document.getTimestamp("date");
                                    Date reported_date = timestamp.toDate();

                                    JsonObject properties = new JsonObject();
                                    properties.addProperty("details", document.getString("details"));



                                    featurelist.add(Feature.fromGeometry(Point.fromLngLat(document.getDouble("longitude"),document.getDouble("latitude"))));
                                    emergency_myLists.add(new EmergencyList(document.getId(),document.getString("reporter_id"), document.getString("reporter_name"),document.getString("reporter_address"),reported_date,document.getString("reporter_phone"), document.getString("details"), document.getDouble("latitude"), (document.getDouble("longitude"))));
                                    emergencyMarker = mapboxMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(document.getDouble("latitude"), document.getDouble("longitude")))
                                            .title(document.getString("reporter_name"))
                                            .setSnippet(document.getString("details"))
                                            .icon(markerIcon));


                                }



                                //    featurelist.add(Feature.fromGeometry(
                                //           Point.fromLngLat(125.032781, 10.956433), properties, "test"));



                            }else{

                                Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();

                            }



                        }


                        FeatureCollection featureCollection = FeatureCollection.fromFeatures(featurelist);
                        emergencyGeoJsonSource = new GeoJsonSource(EMERGENCYGEOJSON_SOURCE_ID, featureCollection);
                        loadedStyle.addSource(emergencyGeoJsonSource);


                        emergencySymbolLayer = new SymbolLayer(EMERGENCYMARKER_LAYER_ID, EMERGENCYGEOJSON_SOURCE_ID)
                                .withProperties(PropertyFactory.iconImage("emergencyImage"),
                                        PropertyFactory.textAnchor("top".toString()),
                                        //PropertyFactory.textField(Expression.id()),
                                        //  PropertyFactory.textSize(12f),
                                        // PropertyFactory.textColor(Color.RED),
                                        //  PropertyFactory.textKeepUpright(false),
                                        //  PropertyFactory.textAllowOverlap(true),

                                        PropertyFactory.textIgnorePlacement(false),
                                        // PropertyFactory.iconAnchor("top".toString()),
                                        PropertyFactory.iconIgnorePlacement(false),
                                        PropertyFactory.circleColor(Color.BLACK),
                                        //           PropertyFactory.iconOffset(new Float[]{0f,-9f}),
                                        PropertyFactory.iconAllowOverlap(false),
                                        ///     PropertyFactory.textOpacity(0.5f),
                                        PropertyFactory.textSize(12f));



                        bottomNavigationView.showBadge(R.id.emergency, emergency_myLists.size());
                        emergency_adapter = new EmergencyAdapter(emergency_myLists, MainActivity.this);
                        emergency_rv.setAdapter(emergency_adapter);

                        //mapView.invalidate();
                        loadedStyle.addLayer(emergencySymbolLayer);


                    }
                });


    }





    static void currentEmergency(Context context, String reporterID){

        emptyImage.setVisibility(VISIBLE);
        respond_rv.setVisibility(GONE);

        if (reporterID.matches(loadId)){
          //  emergencyRespond.setVisibility(GONE);
        }else{
            emergencyRespond.setVisibility(VISIBLE);

        }

        respond_adapter = new RespondAdapter(respond_myLists, context);
        respond_rv.setAdapter(respond_adapter);

        if (listenerRegistrationResponder != null){
            listenerRegistrationResponder.remove();
        }

        Query query = db.collection("Emergencies").document(emergencyID).collection("responders").orderBy("time_responded", Query.Direction.DESCENDING);
        listenerRegistrationResponder = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        respond_myLists.clear();
                        if (e != null) {
                            return;
                        }

                        for (DocumentSnapshot document : snapshots.getDocuments()) {
                            if (document.exists()){

                                Timestamp timestamp = (Timestamp) document.getTimestamp("time_responded");
                                Date time_responded = timestamp.toDate();

                                respond_myLists.add(new RespondList(document.getId(), document.getString("responder_name"),time_responded,document.getString("responder_classification"),document.getString("responder_phone"), document.getString("status"), 10.953708, 125.028020));
                                respond_adapter = new RespondAdapter(respond_myLists, context);
                                respond_rv.setAdapter(respond_adapter);

                                emptyImage.setVisibility(GONE);
                                respond_rv.setVisibility(VISIBLE);


                            }else{

                                respond_myLists.clear();
                            }

                        }

                    }
                });



    }

    private void getRoute(Point myorigin,Point mydestination) {
        MapboxDirections client = MapboxDirections.builder()
                .origin(myorigin)
                .destination(mydestination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();
        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    Toast.makeText(MainActivity.this, "No routes found, make sure you set the right user and access token.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (response.body().routes().size() < 1) {
                    Toast.makeText(MainActivity.this, "No routes found", Toast.LENGTH_SHORT).show();

                    return;
                }
                DirectionsRoute currentRoute = response.body().routes().get(0);
                initDottedLineSourceAndLayer(loadedStyle);
                drawNavigationPolylineRoute(response.body().routes().get(0),mydestination);





                //Toast.makeText(context, currentRoute.duration().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Timber.d("Error: %s", throwable.getMessage());
                if (!throwable.getMessage().equals("Coordinate is invalid: 0,0")) {
                    Toast.makeText(MainActivity.this,
                            "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void drawNavigationPolylineRoute(final DirectionsRoute route,Point target) {
        if (mapboxMap != null) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    CameraPosition position = new CameraPosition.Builder()
                            .target(new LatLng(target.latitude(),target.longitude()))
                            .zoom(15)
                            .tilt(0)
                            .build();

                    mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);


                    List<Feature> directionsRouteFeatureList = new ArrayList<>();
                    LineString lineString = LineString.fromPolyline(route.geometry(), PRECISION_6);
                    List<Point> coordinates = lineString.coordinates();
                    for (int i = 0; i < coordinates.size(); i++) {
                        directionsRouteFeatureList.add(Feature.fromGeometry(LineString.fromLngLats(coordinates)));
                    }
                    dashedLineDirectionsFeatureCollection = FeatureCollection.fromFeatures(directionsRouteFeatureList);
                    GeoJsonSource source = style.getSourceAs(SOURCE_ID);
                    if (source != null) {
                        source.setGeoJson(dashedLineDirectionsFeatureCollection);
                    }
                }
            });
        }
    }


    private void initDottedLineSourceAndLayer(@NonNull Style loadedMapStyle) {

        GeoJsonSource source = loadedMapStyle.getSourceAs(SOURCE_ID);
        if (source == null) {


            loadedMapStyle.addSource(new GeoJsonSource(SOURCE_ID));
            loadedMapStyle.addLayerBelow(
                    new LineLayer(
                            DIRECTIONS_LAYER_ID, SOURCE_ID).withProperties(
                            // lineWidth(4.5f),
                            // lineColor(Color.BLACK),
                            // lineTranslate(new Float[] {0f, 4f}),
                            // lineDasharray(new Float[] {1.2f, 1.2f})
                            lineCap(Property.LINE_CAP_ROUND),
                            lineJoin(Property.LINE_JOIN_ROUND),
                            lineWidth(5f),
                            lineColor(Color.parseColor("#67595E"))
                    ), LAYER_BELOW_ID);

        }



    }


    private void permissionCheck(String reporter,String address,String message, Double rlat, Double rlong) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            sendSms(reporter,address,message,rlat,rlong);

        }else{
            requestSmsSendPermission();
        }


    }

    private void sendSms(String reporter,String address,String message, Double rlat, Double rlong) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("numbers", "");
        if (json.isEmpty()) {
            Toast.makeText(this, "Responder numbers are empty. Please connect to the internet to load data", Toast.LENGTH_LONG).show();

        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> numbers = gson.fromJson(json, type);

            try {

                String messageLat = "latitude:" + rlat;
                String messageLong = "longitude:"+ rlong;


                SmsManager sms = SmsManager.getDefault();
                String timeStamp = String.valueOf(DateFormat.format("MMM dd yyyy hh:mm aa",new Date()));
                String mymessage = "Sender: "+reporter + System.getProperty("line.separator") +"Address: "+ address + System.getProperty("line.separator") +"Timestamp: " + timeStamp + System.getProperty("line.separator") + "Message: "+message + System.getProperty("line.separator") +System.getProperty("line.separator") + "Click this link to see my location " + "http://emergencyresponse.com/municipal/" + messageLat+"/" + messageLong;
                ArrayList<String> msgArray = sms.divideMessage(mymessage);
                for (String number : numbers){
                    Toast.makeText(this, String.valueOf(number), Toast.LENGTH_SHORT).show();
                    try {
                        sms.sendMultipartTextMessage(number, null,msgArray, null, null);
                    }catch (Exception ex){
                        Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                   // sms.sendTextMessage(number,null,mymessage,null,null);

                }

            }catch (Exception e){
                Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        }



    }

    private void requestSmsSendPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                SEND_SMS_CODE);
    }


    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(10000)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY).setFastestInterval(10000)
                .setMaxWaitTime(10000).build();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            enableLocationComponent(loadedStyle);
            return;
        }
        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MainActivity> activityWeakReference;


        MainActivityLocationCallback(MainActivity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    /**
     * The LocationEngineCallback interface's method which fires when the device's location has changed.
     *
     * @param result the LocationEngineResult object which has the last known location within it.
     */
    @Override
    public void onSuccess(LocationEngineResult result) {
        MainActivity activity = activityWeakReference.get();

        if (activity != null) {
            Location location = result.getLastLocation();

            if (location == null) {
                return;
            }

            Date currentTime = Calendar.getInstance().getTime();

            activity.currentLat = result.getLastLocation().getLatitude();
            activity.currentLong = result.getLastLocation().getLongitude();

            Map<String, Object> data = new HashMap<>();
            data.put("latitude", result.getLastLocation().getLatitude());
            data.put("longitude",  result.getLastLocation().getLongitude());
            data.put("last_active",currentTime);

            if (activity.firebaseAuth.getCurrentUser() != null){
                db.collection("Users").document(activity.firebaseAuth.getCurrentUser().getUid())
                        .update(data);
            }


// Create a Toast which displays the new location's coordinates
         //   Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
           //         String.valueOf(result.getLastLocation().getLatitude()), String.valueOf(result.getLastLocation().getLongitude())),
           //         Toast.LENGTH_SHORT).show();



// Pass the new location to the Maps SDK's LocationComponent
            if (activity.mapboxMap != null && result.getLastLocation() != null) {
                activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
            }
        }
    }

    /**
     * The LocationEngineCallback interface's method which fires when the device's location can not be captured
     *
     * @param exception the exception message
     */
    @Override
    public void onFailure(@NonNull Exception exception) {
        //Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
        MainActivity activity = activityWeakReference.get();
        if (activity != null) {
            Toast.makeText(activity, exception.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}



    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            LocationComponentOptions locationComponentOptions =
                    LocationComponentOptions.builder(this)
                            .pulseEnabled(true)

                            .pulseMaxRadius(35)
                            .pulseFadeEnabled(true)
                            .pulseColor(ContextCompat.getColor(this, R.color.lightBlue))
                           // .pulseAlpha(.5f)
                           // .pulseInterpolator(new BounceInterpolator())
                            .build();

            LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions
                    .builder(this, loadedMapStyle)
                    .locationComponentOptions(locationComponentOptions)
                    .build();

            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING,2000,16.0,null,null,null);



            locationComponent.setRenderMode(RenderMode.COMPASS);
            initLocationEngine();

        } else {

            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);

        }


    }


    public void runBackground() {


        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {





                            locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                            assert locationManager != null;
                            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                            if(isGpsEnabled == false && gpsLayoutIsShowing == false) {


                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                                gpsLayout = inflater.inflate(R.layout.enable_gps, null);


                                Button gpsSettings = (Button) gpsLayout.findViewById(R.id.gpsSettings);
                                gpsSettings.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(isGpsEnabled == false){
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivity(intent);
                                        }
                                        gpsDialog.dismiss();
                                        gpsLayoutIsShowing = false;


                                    }
                                });


                                builder.setView(gpsLayout);
                                gpsDialog = builder.create();

                                gpsDialog.setCancelable(false);
                                gpsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                gpsLayoutIsShowing = true;
                                gpsDialog.show();

                            }else if (isGpsEnabled == true){
                                gpsDialog.dismiss();
                                gpsLayoutIsShowing = false;

                            }





                          //  updateLocation();


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 10000 ms
    }


    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }






    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

        //  if (selfiePhotoPath != null){
        outState.putString("photoSelfie",selfiePhotoPath);
        // outState.putParcelable("pho",selfiePhotoPath);
        // }
        //  if (docuPhotoPath != null){
        outState.putString("photoDocument",docuPhotoPath);
        //  }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, ReactivateService.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
        mapView.onDestroy();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }



    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "Location is needed", Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK){


            docuPhotoPath = preferences.getString("docuPhotoPath",null);
            //    File file = new File(docuPhotoPath);

            try {
                if (docuPhotoPath != null){
                    reviewPhotoDocument();
                }else{
                    Toast.makeText(this, "Error capturing image. Please try again", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Toast.makeText(this, "Please try again :" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }


        }
        if (resultCode == Activity.RESULT_CANCELED) {
           // Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }



        if (requestCode == 200 && resultCode == Activity.RESULT_OK){

            selfiePhotoPath = preferences.getString("selfiePhotoPath",null);
            try {
                if (selfiePhotoPath != null){
                    reviewPhotoSelfie();
                }else{
                    Toast.makeText(this, "Error capturing image. Please try again", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                Toast.makeText(this, "Please try again :" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

/*
                File file = new File(selfiePhotoPath);
                Toast.makeText(this, String.valueOf(file), Toast.LENGTH_SHORT).show();
                ImageView imageView = (ImageView) findViewById(R.id.selfieImage);
                try {
                    imageView.setImageURI(Uri.fromFile(file));
                    uploadSelfie.setVisibility(View.GONE);
                    imageSelfie.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }

*/

            // Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            //  reviewPhoto(file,requestCode);
            //   ImageView imageView = (ImageView) findViewById(R.id.selfieImage);
            //  imageView.setImageBitmap(capturedImage);
            //  imageSelfie.setVisibility(View.VISIBLE);
            //   uploadSelfie.setVisibility(View.GONE);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }


        if (requestCode == 300 && resultCode == Activity.RESULT_OK){

            try {

                uploadDocu.setVisibility(View.GONE);
                imageDocu.setVisibility(View.VISIBLE);
                ImageView imageView = (ImageView) findViewById(R.id.docuImage);

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                reviewPhotoDocumentPick(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }



    }




    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)         {
        super.onRestoreInstanceState(savedInstanceState);

        // selfiePhotoPath = savedInstanceState.getParcelable("photoSelfie");
        //  docuPhotoPath = savedInstanceState.getParcelable("photoDocument");

        registerType.setChecked(true);


        docuPhotoPath = preferences.getString("docuPhotoPath",null);

        if (docuPhotoPath != null){

            File file = new File(docuPhotoPath);

            ImageView imageViewDocu = (ImageView) findViewById(R.id.docuImage);
            try {
                imageViewDocu.setImageURI(Uri.fromFile(file));
                uploadDocu.setVisibility(View.GONE);
                imageDocu.setVisibility(View.VISIBLE);
                dialogReview.dismiss();
            }catch (Exception e){


            }
        }





        selfiePhotoPath = preferences.getString("selfiePhotoPath",null);

        if (selfiePhotoPath != null){

            File file = new File(selfiePhotoPath);

            ImageView imageView = (ImageView) findViewById(R.id.selfieImage);
            try {
                imageView.setImageURI(Uri.fromFile(file));
                uploadSelfie.setVisibility(View.GONE);
                imageSelfie.setVisibility(View.VISIBLE);
                dialogReview.dismiss();
            }catch (Exception e){


            }
        }




    }

    @Override
    public void onBackPressed()
    {


        if (loginLayout.getVisibility() == VISIBLE){
            numberLayout.setVisibility(GONE);
            return;
        }
        else if (authenticateLayout.getVisibility() == VISIBLE){
            numberLayout.setVisibility(GONE);
            return;
        }
        else if (numberLayout.getVisibility() == VISIBLE){
            numberLayout.setVisibility(GONE);
            return;
        }



    }
}