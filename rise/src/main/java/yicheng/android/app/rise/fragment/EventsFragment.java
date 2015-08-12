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
import yicheng.android.app.rise.adapter.EventsFragmentGridViewAdapter;
import yicheng.android.app.rise.database.RiseEvent;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.ui.staggeredgridview.StaggeredGridView;

public class EventsFragment extends Fragment {

	ViewGroup rootView;

	StaggeredGridView fragment_events_gridView;

	List<RiseEvent> eventList;

	EventsFragmentGridViewAdapter gridViewAdapter;

	public static SQLiteHelper eventSQLiteHelper;

	boolean isBigScreen;

	public EventsFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_events,
				container, false);
		isBigScreen = isBigScreen();
		loadEventsFromDatabase();
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

	private void loadEventsFromDatabase() {
		eventSQLiteHelper = new SQLiteHelper(rootView.getContext(),
				SQLiteHelper.TABLE_EVENT);
		eventList = eventSQLiteHelper.getAllEvents();
		System.out.println(eventList.size());

	}

	private void initiateComponents() {
		fragment_events_gridView = (StaggeredGridView) rootView
				.findViewById(R.id.fragment_events_gridView);

		gridViewAdapter = new EventsFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), this.eventList,
				isBigScreen);

		fragment_events_gridView.setAdapter(gridViewAdapter);

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
		loadEventsFromDatabase();
		gridViewAdapter = new EventsFragmentGridViewAdapter(
				rootView.getContext(), getActivity(), this.eventList,
				isBigScreen);

		fragment_events_gridView.setAdapter(gridViewAdapter);

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