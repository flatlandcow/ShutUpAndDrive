package com.DK.shutupdrive;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.view.Menu;
//import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements LocationListener{
//Author - Kyle Corry(programmer), Dylan Kiley(design, research and idea), Brian Thornber(design, research and idea)
	int audioMode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Toast.makeText(getApplicationContext(), "Make sure to plug your phone in before driving! ... And don't text!", Toast.LENGTH_LONG).show();
		final AudioManager current = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		audioMode = current.getRingerMode();
		
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		this.onLocationChanged(null);
	}
	@Override
	protected void onPause(){
		 android.os.Process.killProcess(android.os.Process.myPid());
	}
	public float speed;	
	//SilentToNomal and NormalToSilent device
	 public void silent(){
		 final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//Silent Mode
		mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	 }
	public void vibrate(){
		final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//vibrate mode
		mode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}
	 public void normal(){
		 final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//Normal Mode
		  mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	 }
	
	/*
	 //this is for an options menu, I might add this in the future
	 * @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	@Override
	public void onLocationChanged(Location location) {
		TextView speedStatus = (TextView) this.findViewById(R.id.speed);
		ImageView textImg = (ImageView) this.findViewById(R.id.ivImage);
		if( location == null){
			speed = 0;
		} else{
			speed = location.getSpeed();
			System.out.println(speed);
			speed *= 2.23694;
			speed = Math.round(speed);
			if(speed > 10 && speed < 100){
				silent();
				speedStatus.setText("Current speed: " + speed + " mph, texting disabled.");
				textImg.setImageResource(R.drawable.text_off);
			} else{
				if(audioMode == 1){
					vibrate();
				} else if(audioMode == 2){
					normal();
				} else{
					silent();
				}
				speedStatus.setText("Current speed: " + speed + " mph, texting enabled.");
				textImg.setImageResource(R.drawable.text_on);
			}
		}
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}