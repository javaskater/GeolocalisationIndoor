package fr.cnam.nfa024.jpmena.geolocalisationindoor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MouvementActivity extends AppCompatActivity {


    /*
    * cas où le mouvement nous demande de nous orienter
     */
    private SensorManager mSensorManager;
    private Sensor mMagnetometer;
    private Sensor mAccelerometer;
    private ImageView mImageViewCompass;
    private float[] mGravityValues=new float[3];
    private float[] mAccelerationValues=new float[3];
    private float[] mRotationMatrix=new float[9];
    private float mLastDirectionInDegrees = 0f;

    private Button mOkFait;

    public static final String NORD = "NORD";
    public static final String SUD = "SUD";
    public static final String EST = "EST";
    public static final String OUEST = "OUEST";


    private SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            calculateCompassDirection(event);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //Nothing to do
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouvement_boussole);
        Bundle bundle = getIntent().getExtras();
        String deplacement = bundle.getString(EtapeActivity.MOUVEMENT);
        if(deplacement.equalsIgnoreCase("MONTER") || deplacement.equalsIgnoreCase("DESCENDRE")) {
            setTitle(deplacement+" d'un niveau");
            setContentView(R.layout.activity_mouvement_etage);
        } else {
            setTitle("se diriger vers le "+ deplacement);
            setContentView(R.layout.activity_mouvement_boussole);
            mImageViewCompass = (ImageView) findViewById(R.id.imageViewCompass);
            /*
            * On affiche une image différente suivant la direction que l'on veut prendre comme suggéré
            * sur https://stackoverflow.com/questions/61425687/adding-a-circle-on-an-imagei-n-android/61425779#61425779
            * Cette image reprend celle trouvée sur https://pixabay.com/en/geography-map-compass-rose-plot-42608/
            * on lui a rajouté par Gimp une flèche en jaune vers la direction souhaitée
             */
            if(deplacement.equalsIgnoreCase(NORD)){
                mImageViewCompass.setImageResource(R.drawable.compassn);
            } else if (deplacement.equalsIgnoreCase(SUD)){
                mImageViewCompass.setImageResource(R.drawable.compasss);
            } else if (deplacement.equalsIgnoreCase(EST)) {
                mImageViewCompass.setImageResource(R.drawable.compasse);
            } else if (deplacement.equalsIgnoreCase(OUEST)) {
                mImageViewCompass.setImageResource(R.drawable.compasso);
            }
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mOkFait = (Button) findViewById(R.id.boutonConfirmation);
            mOkFait.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                        MouvementActivity.this.finish();
                }
            });
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(mSensorListener, mMagnetometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    private void calculateCompassDirection(SensorEvent event){
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerationValues = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGravityValues = event.values.clone();
                break;
        }
        //on veut obtenir la matrice de rotation (float[9)) à partir des valeurs de l'acccéléromètrre et du magnétomètre
        boolean success = SensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerationValues, mGravityValues);
        if (success){
            float[] orientationValues = new float[3];
            // j'extraie de la matrice de roation les valeurs qui m'intéressent
            SensorManager.getOrientation(mRotationMatrix, orientationValues);
            // L'image de la boussole devra se déplacer pour que le haut de l'image montre le nord !!!
            float azimuth = (float) Math.toDegrees(-orientationValues[0]);
            // on tourne l'image de la boussole
            RotateAnimation rotateAnimation = new RotateAnimation(mLastDirectionInDegrees, azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(50);
            rotateAnimation.setFillAfter(true);
            mImageViewCompass.startAnimation(rotateAnimation);
            mLastDirectionInDegrees = azimuth;
        }
    }

}
