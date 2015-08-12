package yicheng.android.app.rise.adapter;

import java.net.URI;

import com.squareup.picasso.Picasso;

import yicheng.android.app.rise.R;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NavigationDrawerRecyclerViewAdapter extends
		RecyclerView.Adapter<NavigationDrawerRecyclerViewAdapter.ViewHolder> {

	Context context;

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;

	private String drawerTitles[];

	private int drawerIcons[];

	private String profileName;
	private String profileAvatarImageID;
	private String profileBackgroundImageID;

	private String profileEmail;

	public class ViewHolder extends RecyclerView.ViewHolder {
		int holderTypeID;

		ImageView profile_background_imageView, profile_avatar_imageView;
		TextView profile_name_textView, profile_email_textView;

		TextView row_item_textView;
		ImageView row_item_imageView;

		public ViewHolder(View itemView, int ViewType) {
			super(itemView);
			itemView.setClickable(true);

			if (ViewType == TYPE_ITEM) {
				row_item_textView = (TextView) itemView
						.findViewById(R.id.navigation_drawer_recyclerview_row_textView);
				row_item_imageView = (ImageView) itemView
						.findViewById(R.id.navigation_drawer_recyclerview_row_imageView);

				holderTypeID = 1;
			}
			else {

				profile_name_textView = (TextView) itemView
						.findViewById(R.id.navigation_drawer_recyclerview_header_name_textView);
				profile_email_textView = (TextView) itemView
						.findViewById(R.id.navigation_drawer_recyclerview_header_email_textView);
				profile_avatar_imageView = (ImageView) itemView
						.findViewById(R.id.navigation_drawer_recyclerview_header_circleImageView);
				profile_background_imageView = (ImageView) itemView
						.findViewById(R.id.navigation_drawer_header_imageView);

				holderTypeID = 0;
			}

		}

	}

	public NavigationDrawerRecyclerViewAdapter(Context context,
			String navigationTitles[], int navigationIcons[],
			String profileName, String profileEmail,
			String profileAvatarImageID, String profileBackgroundImageID) {

		this.context = context;
		this.drawerTitles = navigationTitles;
		this.drawerIcons = navigationIcons;
		this.profileName = profileName;
		this.profileEmail = profileEmail;
		this.profileAvatarImageID = profileAvatarImageID;
		this.profileBackgroundImageID = profileBackgroundImageID;

	}

	@Override
	public NavigationDrawerRecyclerViewAdapter.ViewHolder onCreateViewHolder(
			ViewGroup parent, int viewType) {

		if (viewType == TYPE_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.navigation_drawer_recyclerview_row_item, parent,
					false);

			ViewHolder vhItem = new ViewHolder(v, viewType);
			return vhItem;

		}
		else if (viewType == TYPE_HEADER) {

			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.navigation_drawer_recyclerview_header, parent,
					false);

			ViewHolder vhHeader = new ViewHolder(v, viewType);

			return vhHeader;

		}
		return null;

	}

	@Override
	public void onBindViewHolder(
			NavigationDrawerRecyclerViewAdapter.ViewHolder holder, int position) {
		if (holder.holderTypeID == 1) {

			holder.row_item_textView.setText(drawerTitles[position - 1]);

			holder.row_item_imageView
					.setImageResource(drawerIcons[position - 1]);

			// setDrawerButtonControl(holder.itemView, position);
		}
		else {
			/*	holder.profile_background_imageView
						.setImageURI(profileBackgroundImageID);*/

			Picasso.with(context).load(profileBackgroundImageID).fit()
					.into(holder.profile_background_imageView);

			Picasso.with(context).load(profileAvatarImageID).fit()
					.into(holder.profile_avatar_imageView);

			holder.profile_name_textView.setText(profileName);
			holder.profile_email_textView.setText(profileEmail);

			// setDrawerHeaderControl();

		}

	}

	private void setDrawerHeaderControl() {

	}

	/*	private void setDrawerButtonControl(View v, final int position) {
			v.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "" + position, Toast.LENGTH_SHORT)
							.show();

					FragmentManager fragmentManager = 
							
					fragmentManager
							.beginTransaction()
							.replace(
									R.id.activity_navigation_drawer_content_framelayout,
									fragment).commit();

				}
			});
		}
	*/
	@Override
	public int getItemCount() {
		return drawerTitles.length + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return TYPE_HEADER;
		}

		return TYPE_ITEM;
	}

}
