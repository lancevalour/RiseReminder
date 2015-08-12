package yicheng.android.app.rise.adapter;

import java.util.Arrays;
import java.util.List;

import yicheng.android.ui.materialdesignlibrary.widgets.SnackBar;
import yicheng.android.app.rise.R;
import yicheng.android.app.rise.activity.NavigationDrawerActivity;
import yicheng.android.app.rise.activity.NewEventActivity;
import yicheng.android.app.rise.activity.NewPlaceActivity;
import yicheng.android.app.rise.database.RisePlace;
import yicheng.android.app.rise.fragment.PlacesFragment;
import yicheng.android.app.rise.ui.utility.SwipeDimissTouchListener;
import yicheng.android.ui.floatingactionbutton.FloatingActionsMenu;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesFragmentGridViewAdapter extends BaseAdapter {
	private Context context;
	List<RisePlace> placesList;

	private Activity activity;

	RisePlace deletedPlace;
	boolean isBigScreen;

	public PlacesFragmentGridViewAdapter(Context context, Activity activity,
			List<RisePlace> placesList, boolean isBigScreen) {
		this.context = context;
		this.activity = activity;
		this.placesList = placesList;
		this.isBigScreen = isBigScreen;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (placesList != null) {
			return placesList.size();
		}
		else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (this.placesList != null) {
			return this.placesList.get(position);
		}
		else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	boolean isDeleted = true;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final int curPosition = position;

		ViewHolder viewHolder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.fragment_places_grid_item,
					parent, false);

			viewHolder = new ViewHolder();

			viewHolder.fragment_places_place_name_textView = (TextView) convertView
					.findViewById(R.id.fragment_places_place_name_textView);
			viewHolder.fragment_places_place_address_textView = (TextView) convertView
					.findViewById(R.id.fragment_places_place_address_textView);
			viewHolder.fragment_places_place_label_textView = (TextView) convertView
					.findViewById(R.id.fragment_places_place_label_textView);

			convertView.setTag(viewHolder);

		}
		else {

			viewHolder = (ViewHolder) convertView.getTag();

		}

		viewHolder.fragment_places_place_name_textView.setText(placesList.get(
				position).getPlaceName());

		viewHolder.fragment_places_place_address_textView.setText(placesList
				.get(position).getPlaceAddress());

		String[] types = placesList.get(position).getPlaceTypes().split(",");

		System.out.println(Arrays.toString(types));

		StringBuilder sBuilder = new StringBuilder();
		for (String s : types) {
			switch (s) {
			case "work": {
				sBuilder.append("W ");
			}
				break;
			case "home": {
				sBuilder.append("H ");
			}
				break;
			case "play": {
				sBuilder.append("P ");
			}
				break;

			}
		}

		viewHolder.fragment_places_place_label_textView.setText(sBuilder
				.toString().trim());

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
						.isExpanded()) {
					NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
							.collapse();
				}
				goToNewPlaceActivity(placesList.get(curPosition));
			}
		});

		convertView.setOnTouchListener(new SwipeDimissTouchListener(
				convertView, null,
				new SwipeDimissTouchListener.DismissCallbacks() {

					@Override
					public boolean canDismiss(Object token) {
						// TODO Auto-generated method stub
						return true;
					}

					@Override
					public void onDismiss(View view, Object token) {
						// TODO Auto-generated method stub
			/*			Toast.makeText(activity, "" + curPosition,
								Toast.LENGTH_SHORT).show();*/

						deletedPlace = placesList.remove(curPosition);

						notifyDataSetChanged();

						NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
								.animate()
								.translationY(-120)
								.setDuration(100)
								.setInterpolator(
										new AccelerateDecelerateInterpolator());

						SnackBar snackbar = new SnackBar(activity,
								"Place Deleted", "UNDO",
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										/*		Toast.makeText(context, "Clicked",
														Toast.LENGTH_SHORT).show();

												Toast.makeText(context,
														"curPosition: " + curPosition,
														Toast.LENGTH_SHORT).show();
										*/
										placesList.add(curPosition,
												deletedPlace);

										notifyDataSetChanged();

										isDeleted = false;
									}

								}, !isBigScreen);

						snackbar.setOnDismissListener(new OnDismissListener() {

							@Override
							public void onDismiss(DialogInterface dialog) {
								// TODO Auto-generated method stub

								NavigationDrawerActivity.activity_navigation_drawer_floatingActionMenu
										.animate()
										.translationY(0)
										.setDuration(100)
										.setInterpolator(
												new AccelerateDecelerateInterpolator());

								if (isDeleted) {
									deletePlaceFromDatabase();
								}

							}

						});

						snackbar.setIndeterminate(true);
						snackbar.show();
						snackbar.setColorButton(activity.getResources()
								.getColor(R.color.theme_primary));
						snackbar.setCanceledOnTouchOutside(true);

					}

				}));

		return convertView;

	}

	private void goToNewPlaceActivity(RisePlace place) {
		Intent intent = new Intent(activity, NewPlaceActivity.class);
		intent.putExtra("place_name", place.getPlaceName());

		intent.putExtra("place_address", place.getPlaceAddress());
		intent.putExtra("place_types", place.getPlaceTypes());

		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
	}

	private void deletePlaceFromDatabase() {
		PlacesFragment.placeSQLiteHelper.deletePlaceByName(deletedPlace
				.getPlaceName());
	}

	/*	private void updateGridView() {
			loadPlacesFromDatabase();
			PlacesFragmentGridViewAdapter gridViewAdapter = new PlacesFragmentGridViewAdapter(
					rootView.getContext(), getActivity(), placesList);

			fragment_places_gridView.setAdapter(gridViewAdapter);
		}*/
	private class ViewHolder {
		TextView fragment_places_place_name_textView;
		TextView fragment_places_place_address_textView;
		TextView fragment_places_place_label_textView;

	}
}
