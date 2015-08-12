package yicheng.android.app.rise.activity;

import java.util.ArrayList;
import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.adapter.AddPlaceActivityGridViewAdapter;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.ui.staggeredgridview.StaggeredGridView;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;

public class AddPlaceActivity extends ActionBarActivity {

	Toolbar activity_new_event_add_place_toolbar;

	StaggeredGridView activity_new_event_add_place_gridView;

	AddPlaceActivityGridViewAdapter gridViewAdapter;

	List<RisePlace> placesList;

	
	ArrayList<RisePlace> selectedList;
	ArrayList<String> selectedNameList;
	ArrayList<String> selectedAddressList;
	ArrayList<String> selectedIDList;
	ArrayList<String> selectedLatList;
	ArrayList<String> selectedLongList;
	ArrayList<String> selectedTypeList;

	SQLiteHelper placeSQLiteHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event_add_place);

		// setGoogleApiClient();
		loadPlacesFromDatabase();
		initiateComponents();
		setComponentStyle();

		setComponentControl();
	}

	private void loadPlacesFromDatabase() {
		placeSQLiteHelper = new SQLiteHelper(getBaseContext(),
				SQLiteHelper.TABLE_PLACE);
		placesList = placeSQLiteHelper.getAllPlaces();

	}

	private void initiateComponents() {
		activity_new_event_add_place_toolbar = (Toolbar) findViewById(R.id.activity_new_event_add_place_toolbar);

		activity_new_event_add_place_toolbar
				.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);

		setSupportActionBar(activity_new_event_add_place_toolbar);
		getSupportActionBar().setTitle(R.string.toolbar_add_place_title);

		selectedList = new ArrayList<RisePlace>();

		Intent data = getIntent();

		selectedNameList = (ArrayList<String>) data
				.getSerializableExtra("selected_name_list");
		selectedAddressList = (ArrayList<String>) data
				.getSerializableExtra("selected_address_list");
		selectedIDList = (ArrayList<String>) data
				.getSerializableExtra("selected_id_list");
		selectedLatList = (ArrayList<String>) data
				.getSerializableExtra("selected_lat_list");
		selectedLongList = (ArrayList<String>) data
				.getSerializableExtra("selected_long_list");
		selectedTypeList = (ArrayList<String>) data
				.getSerializableExtra("selected_type_list");

		/*	for (int i = 0; i < selectedNameList.size(); i++) {
				selectedList.add(new RisePlace(selectedNameList.get(i),
						selectedAddressList.get(i), selectedIDList.get(i),
						selectedLatList.get(i), selectedLongList.get(i),
						selectedTypeList.get(i)));
			}*/

		activity_new_event_add_place_gridView = (StaggeredGridView) findViewById(R.id.activity_new_event_add_place_gridView);

		gridViewAdapter = new AddPlaceActivityGridViewAdapter(getBaseContext(),
				this, placesList, selectedNameList);

		activity_new_event_add_place_gridView.setAdapter(gridViewAdapter);

	}

	private void setComponentStyle() {
		/*	if (Build.VERSION.SDK_INT >= 21) {
				Window window = getWindow();

				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.setStatusBarColor(getResources().getColor(
						R.color.theme_primary_dark));
			}*/
	}

	private void setComponentControl() {
		setToolbarControl();
		setGridViewControl();

	}

	private void setGridViewControl() {
		activity_new_event_add_place_gridView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						if (!selectedNameList.contains(placesList.get(position)
								.getPlaceName())) {
							view.setBackgroundResource(R.drawable.card_background_green_ripple);
							/*		selectedList.add(placesList.get(position));*/
							selectedNameList.add(placesList.get(position)
									.getPlaceName());
							selectedAddressList.add(placesList.get(position)
									.getPlaceAddress());
							selectedIDList.add(placesList.get(position)
									.getPlaceID());
							selectedLatList.add(placesList.get(position)
									.getPlaceLatitude());
							selectedLongList.add(placesList.get(position)
									.getPlaceLongitude());
							selectedTypeList.add(placesList.get(position)
									.getPlaceTypes());
						}
						else {
							view.setBackgroundResource(R.drawable.card_background_ripple);
							/*selectedList.remove(placesList.get(position));*/
							selectedNameList.remove(placesList.get(position)
									.getPlaceName());
							selectedAddressList.remove(placesList.get(position)
									.getPlaceAddress());
							selectedIDList.remove(placesList.get(position)
									.getPlaceID());
							selectedLatList.remove(placesList.get(position)
									.getPlaceLatitude());
							selectedLongList.remove(placesList.get(position)
									.getPlaceLongitude());
							selectedTypeList.remove(placesList.get(position)
									.getPlaceTypes());
						}

					}

				});
	}

	private void setToolbarControl() {
		activity_new_event_add_place_toolbar
				.setNavigationOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_new_place, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_new_place_add: {
			// addNewPlace();

			/*	selectedNameList.clear();
				selectedAddressList.clear();
				selectedIDList.clear();
				selectedLatList.clear();
				selectedLongList.clear();
				selectedTypeList.clear();*/

			/*		for (RisePlace place : selectedList) {
						selectedNameList.add(place.getPlaceName());
						selectedAddressList.add(place.getPlaceAddress());
						selectedIDList.add(place.getPlaceID());
						selectedLatList.add(place.getPlaceLatitude());
						selectedLongList.add(place.getPlaceLongitude());
						selectedTypeList.add(place.getPlaceTypes());
					}*/

			Intent addPlaceIntent = new Intent();
			/*			if (selectedNameList.size() != 0) {*/
			addPlaceIntent.putExtra("selected_name_list", selectedNameList);
			addPlaceIntent.putExtra("selected_address_list",
					selectedAddressList);
			addPlaceIntent.putExtra("selected_id_list", selectedIDList);
			addPlaceIntent.putExtra("selected_lat_list", selectedLatList);
			addPlaceIntent.putExtra("selected_long_list", selectedLongList);
			addPlaceIntent.putExtra("selected_type_list", selectedTypeList);

			/*}*/
			setResult(RESULT_OK, addPlaceIntent);
			finish();

		}
			break;
		/*		case R.id.menu_new_place_settings: {

				}
					break;*/
		}

		return super.onOptionsItemSelected(item);
	}
}
