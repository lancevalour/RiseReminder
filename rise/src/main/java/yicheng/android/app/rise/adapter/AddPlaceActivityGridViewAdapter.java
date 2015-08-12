package yicheng.android.app.rise.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RisePlace;

public class AddPlaceActivityGridViewAdapter extends BaseAdapter {

	Context context;
	Activity activity;
	List<RisePlace> placesList;
	ArrayList<String> selectedNameList;

	public AddPlaceActivityGridViewAdapter(Context context, Activity activity,
			List<RisePlace> placesList, ArrayList<String> selectedNameList) {
		this.context = context;
		this.activity = activity;
		this.placesList = placesList;
		this.selectedNameList = selectedNameList;

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(
					R.layout.activity_add_place_grid_item, null);
			viewHolder = new ViewHolder();
			viewHolder.activity_add_place_place_name_textView = (TextView) convertView
					.findViewById(R.id.activity_add_place_place_name_textView);
			viewHolder.activity_add_place_place_address_textView = (TextView) convertView
					.findViewById(R.id.activity_add_place_place_address_textView);
			convertView.setTag(viewHolder);

		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.activity_add_place_place_name_textView.setText(placesList
				.get(position).getPlaceName());

		viewHolder.activity_add_place_place_address_textView.setText(placesList
				.get(position).getPlaceAddress());

		if (selectedNameList.contains(placesList.get(position).getPlaceName())) {
			convertView
					.setBackgroundResource(R.drawable.card_background_green_ripple);
		}
		else {
			convertView
					.setBackgroundResource(R.drawable.card_background_ripple);
		}

		/*	final RelativeLayout fragment_places_layout = (RelativeLayout) gridView
					.findViewById(R.id.fragment_places_layout);

			gridView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (fragment_places_layout.getBackground() == activity
							.getResources().getDrawable(
									R.drawable.green_card_background)) {
						fragment_places_layout
								.setBackgroundResource(R.drawable.card_background);

					}
					else {
						fragment_places_layout
								.setBackgroundResource(R.drawable.green_card_background);

					}
				}

			});*/
		return convertView;
	}

	private class ViewHolder {
		TextView activity_add_place_place_name_textView;
		TextView activity_add_place_place_address_textView;

	}

}
