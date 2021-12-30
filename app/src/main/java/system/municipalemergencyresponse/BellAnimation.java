package system.municipalemergencyresponse;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class BellAnimation {
    ImageView imageView;

    public void StartAnimateNotif(View view){
        RotateAnimation shake = new RotateAnimation(-10f, 10f, 50f, 50f);
        shake.setInterpolator(new LinearInterpolator());
        shake.setRepeatCount(Animation.INFINITE);
        shake.setRepeatMode(Animation.REVERSE);

        shake.setDuration(300);

        imageView = (ImageView) view.findViewById(R.id.bellImage);
        imageView.startAnimation(shake);


    }

    public void StopAnimateNotif(){
        imageView.setAnimation(null);
    }


}
