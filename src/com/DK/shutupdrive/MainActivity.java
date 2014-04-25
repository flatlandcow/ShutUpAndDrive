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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements LocationListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		TextView speedStatus = (TextView) this.findViewById(R.id.speed);
		ImageView textImg = (ImageView) this.findViewById(R.id.ivImage);
		this.onLocationChanged(null);
		if(speedMPH > 10 || speedMPH < 100){
			silent();
			speedStatus.setText("Your current speed is: " + speedMPH + " mph, texting disabled.");
			textImg.setImageResource(R.drawable.text_off);
		} else{
			normal();
			speedStatus.setText("Your current speed is: " + speedMPH + " mph, texting enabled.");
			textImg.setImageResource(R.drawable.text_on);
		}
	}
	public float speed;
	public double speedMPH;
	//SilentToNomal and NormalToSilent device Programatically
	 public void silent(){
		 final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//Silent Mode Programatically
		mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	 }
	
	 public void normal(){
		 final AudioManager mode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
			//Silent Mode Programatically
		//Normal Mode Programatically
		  mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	 }
	
	@Override
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
	}

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
	public void MPH(float speed){
		speedMPH = speed * 2.23694;
	}
	@Override
	public void onLocationChanged(Location location) {
		if( location == null){
			speed = 0;
		} else{
			speed = location.getSpeed();
			MPH(speed);
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
