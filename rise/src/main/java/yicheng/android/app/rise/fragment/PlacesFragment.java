package yicheng.android.app.rise.fragment;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.activity.NavigationDrawerActivity;
import yicheng.android.app.rise.adapter.PlacesFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.ui.staggeredgridview.StaggeredGridView;

public class PlacesFragment extends Fragment {

	ViewGroup rootView;

	public static StaggeredGridView fragment_places_gridView;

	List<RisePlace> placesList;

	public static PlacesFragmentGridViewAdapter gridViewAdapter;

	public static SQLiteHelper placeSQLiteHelper;

	boolean isBigScreen;

	public PlacesFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_places,
				container, false);
		isBigScreen = isBigScreen();
		loadPlacesFromDatabase();
		initiateComponents();
		setComponentControl();

		return rootView;

	}

	private boolean isBigScreen() {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenWidth = size.x;
		return !(screenWidth < 1000);
	}

	private void loadPlacesFromDatabase() {
		placeSQLiteHelper = new SQLiteHelper(rootView.getContext(),
				SQLiteHelper.TABLE_PLACE);
		placesList = placeSQLiteHelper.getAllPlaces();
		System.out.println(placesList.size());
	}

	private void initiateComponents() {
		fragment_places_gridView = (StaggeredGridView) rootView
				.findViewById(R.id.fragment_places_gridView);

		gridViewAdapter = new PlacesFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), placesList, isBigScreen);

		fragment_places_gridView.setAdapter(gridViewAdapter);

	}

	private void setComponentControl() {
		setGridViewControl();
	}

	private void setGridViewControl() {
		rootView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
						.isExpanded()) {
					NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
							.collapse();
				}
				return false;
			}
		});
	}

	private void updateGridView() {
		loadPlacesFromDatabase();

		// gridViewAdapter.notifyDataSetChanged();
		gridViewAdapter = new PlacesFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), placesList, isBigScreen);

		fragment_places_gridView.setAdapter(gridViewAdapter);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		updateGridView();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}