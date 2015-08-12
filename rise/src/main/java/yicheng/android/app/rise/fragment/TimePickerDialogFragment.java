package yicheng.android.app.rise.fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import yicheng.android.app.rise.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerDialogFragment extends DialogFragment {
	Dialog dialog;

	View rootView;

	TimePicker framgent_time_picker_time_picker;
	Button preTimeButton, curTimeButton;

	public TimePickerDialogFragment(Button preTimeButton, Button curTimeButton) {
		this.preTimeButton = preTimeButton;
		this.curTimeButton = curTimeButton;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		rootView = inflater.inflate(R.layout.fragment_time_picker, null);
		builder.setView(rootView);
		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Calendar curCal = Calendar.getInstance();
				curCal.set(Calendar.HOUR_OF_DAY,
						framgent_time_picker_time_picker.getCurrentHour());
				curCal.set(Calendar.MINUTE,
						framgent_time_picker_time_picker.getCurrentMinute());

				Date curDate = curCal.getTime();

				String[] time = preTimeButton.getText().toString().split(":");

				Calendar preCal = Calendar.getInstance();
				preCal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0]));
				preCal.set(Calendar.MINUTE, Integer.valueOf(time[1]));

				Date preDate = preCal.getTime();

				if (curDate.after(preDate)) {

					curTimeButton.setText(new SimpleDateFormat("HH:mm")
							.format(curDate));

				}
				else {
					Toast.makeText(getActivity(),
							"Please set end time after start time.",
							Toast.LENGTH_SHORT).show();
				}

				dialog.cancel();
			}

		});
		builder.setNegativeButton("CANCEL", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}

		});

		dialog = builder.create();

		initiateComponents();
		setComponentStyle();
		setComponentControl();
		// setHandlerControl();

		return dialog;
	}

	private void initiateComponents() {
		framgent_time_picker_time_picker = (TimePicker) rootView
				.findViewById(R.id.framgent_time_picker_time_picker);

	}

	private void setComponentStyle() {
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(final DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog)
						.getButton(DialogInterface.BUTTON_POSITIVE);
				positiveButton.setTextColor(getResources().getColor(
						R.color.theme_accent));

				positiveButton.invalidate();
			}
		});
	}

	private void setComponentControl() {

	}

	@Override
	public void onStart() {
		super.onStart();

		// safety check
		if (getDialog() == null) {
			return;
		}

		int dialogWidth = 650; // specify a value here
		int dialogHeight = 950; // specify a value here

		getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

		// ... other stuff you want to do in your onStart() method
	}
}
