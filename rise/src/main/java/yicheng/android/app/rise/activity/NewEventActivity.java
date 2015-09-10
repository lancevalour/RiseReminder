package yicheng.android.app.rise.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import yicheng.android.app.rise.R;
import yicheng.android.app.rise.database.RiseEvent;
import yicheng.android.app.rise.database.SQLiteHelper;
import yicheng.android.app.rise.fragment.TimePickerDialogFragment;
import yicheng.android.app.rise.receiver.EventAlarmReceiver;
import yicheng.android.app.rise.receiver.EventIntervalAlarmReceiver;
import yicheng.android.ui.materialdesignlibrary.views.Slider;
import yicheng.android.ui.materialdesignlibrary.views.Slider.OnValueChangedListener;

public class NewEventActivity extends ActionBarActivity {
    ScrollView activity_new_event_scrollView;
    RelativeLayout activity_new_event_layout;

    Toolbar activity_new_event_toolbar;


    Button activity_new_event_start_time_button,
            activity_new_event_end_time_button;

    Slider activity_new_event_time_interval_slider;

    RatingBar activity_new_event_event_priority_ratingBar;

    EditText activity_new_event_event_name_editText,
            activity_new_event_event_content_editText;

    TextView activity_new_event_time_interval_textView;

    ImageButton activity_new_event_event_location_add_button,
            activity_new_event_event_name_clear_button,
            activity_new_event_event_content_clear_button;

    RelativeLayout activity_new_event_event_location_add_location_layout,
            activity_new_event_time_interval_layout;

    LinearLayout activity_new_event_event_time_button_layout;

    CheckBox activity_new_event_event_location_checkbox;

    CheckBox activity_new_event_event_time_checkBox;


    ListView activity_new_event_event_location_listView;

    Switch new_event_actionbar_switch;

    ArrayAdapter<String> placeListAdapter;

    String eventStartTime;
    String eventEndTime;
    String eventCycleInterval;

    String eventName;
    String eventContent;
    String eventCreateDate;
    String eventPriority;

    String isEventFinished;
    String isNotificationOn = String.valueOf(false);

    String eventLocationList;

    SQLiteHelper eventSQLiteHelper;

    boolean isUpdatingEvent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_event);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eventStartTime = extras.getString("event_start_time");
            eventEndTime = extras.getString("event_end_time");
            eventCycleInterval = extras.getString("event_cycle_interval");
            eventName = extras.getString("event_name");
            eventContent = extras.getString("event_content");
            eventPriority = extras.getString("event_priority");
            isNotificationOn = extras.getString("event_is_notification_on");
            eventLocationList = extras.getString("event_location_list");
            isUpdatingEvent = true;

        }

        initiateComponents();
        setComponentStyle();
        setComponentControl();

    }

    private void setComponentStyle() {
        /*if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(
					R.color.theme_primary_dark));

		}*/

        LayerDrawable layerDrawable = (LayerDrawable) activity_new_event_event_priority_ratingBar
                .getProgressDrawable();
        DrawableCompat.setTint(
                layerDrawable.getDrawable(0),
                getResources().getColor(R.color.theme_accent)); // Empty
        // star
        DrawableCompat.setTint(
                layerDrawable.getDrawable(1),
                getResources().getColor(R.color.theme_accent)); // Partial
        // star
        DrawableCompat.setTint(
                layerDrawable.getDrawable(2),
                getResources().getColor(R.color.theme_accent)); // Full
        // star
    }

    private void updatePlaceList() {

        placeListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                selectedNameList.toArray(new String[selectedNameList.size()]));

        activity_new_event_event_location_listView.setAdapter(placeListAdapter);
        adjustListViewHeight();
    }

    private void initiateComponents() {
        activity_new_event_scrollView = (ScrollView) findViewById(R.id.activity_new_event_scrollView);

        activity_new_event_layout = (RelativeLayout) findViewById(R.id.activity_new_event_layout);
        activity_new_event_event_location_add_location_layout = (RelativeLayout) findViewById(R.id.activity_new_event_event_location_add_location_layout);
        activity_new_event_time_interval_layout = (RelativeLayout) findViewById(R.id.activity_new_event_time_interval_layout);

        activity_new_event_event_time_button_layout = (LinearLayout) findViewById(R.id.activity_new_event_event_time_button_layout);

        activity_new_event_event_location_checkbox = (CheckBox) findViewById(R.id.activity_new_event_event_location_checkbox);
        activity_new_event_event_location_checkbox.setChecked(false);

        activity_new_event_event_time_checkBox = (CheckBox) findViewById(R.id.activity_new_event_event_time_checkBox);
        activity_new_event_event_time_checkBox.setChecked(false);

        activity_new_event_time_interval_layout.setVisibility(View.INVISIBLE);
        activity_new_event_event_time_button_layout.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.activity_new_event_event_time_checkBox);

        activity_new_event_time_interval_layout.setLayoutParams(layoutParams);
        activity_new_event_event_time_button_layout.setLayoutParams(layoutParams);


        activity_new_event_event_location_add_button = (ImageButton) findViewById(R.id.activity_new_event_event_location_add_button);
        activity_new_event_event_location_add_button.setVisibility(View.GONE);

        activity_new_event_event_name_clear_button = (ImageButton) findViewById(R.id.activity_new_event_event_name_clear_button);
        activity_new_event_event_content_clear_button = (ImageButton) findViewById(R.id.activity_new_event_event_content_clear_button);
        activity_new_event_event_name_clear_button.setAlpha(0.0f);
        activity_new_event_event_name_clear_button.setEnabled(false);
        activity_new_event_event_content_clear_button.setAlpha(0.0f);
        activity_new_event_event_content_clear_button.setEnabled(false);

        activity_new_event_event_name_editText = (EditText) findViewById(R.id.activity_new_event_event_name_editText);
        activity_new_event_event_content_editText = (EditText) findViewById(R.id.activity_new_event_event_content_editText);

        activity_new_event_toolbar = (Toolbar) findViewById(R.id.activity_new_event_toolbar);

        activity_new_event_toolbar
                .setNavigationIcon(R.drawable.ic_action_navigation_arrow_back);

        setSupportActionBar(activity_new_event_toolbar);
        if (isUpdatingEvent) {
            getSupportActionBar().setTitle(R.string.toolbar_edit_event_title);
            // activity_new_event_event_content_editText.requestFocus();
        } else {
            getSupportActionBar().setTitle(R.string.toolbar_new_event_title);
        }
        activity_new_event_start_time_button = (Button) findViewById(R.id.activity_new_event_start_time_button);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        // get current date time with Date()
        activity_new_event_start_time_button.setText(dateFormat
                .format(new Date()));

        activity_new_event_end_time_button = (Button) findViewById(R.id.activity_new_event_end_time_button);

        activity_new_event_end_time_button.setText(dateFormat
                .format(new Date()));

        activity_new_event_event_priority_ratingBar = (RatingBar) findViewById(R.id.activity_new_event_event_priority_ratingBar);

        activity_new_event_time_interval_slider = (Slider) findViewById(R.id.activity_new_event_time_interval_slider);

        activity_new_event_time_interval_textView = (TextView) findViewById(R.id.activity_new_event_time_interval_textView);
        activity_new_event_time_interval_textView.setText(""
                + activity_new_event_time_interval_slider.getValue());

        selectedNameList = new ArrayList<String>();
        selectedAddressList = new ArrayList<String>();
        selectedIDList = new ArrayList<String>();
        selectedLatList = new ArrayList<String>();
        selectedLongList = new ArrayList<String>();
        selectedTypeList = new ArrayList<String>();

        activity_new_event_event_location_listView = (ListView) findViewById(R.id.activity_new_event_event_location_listView);

        if (!isUpdatingEvent) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

        if (isUpdatingEvent) {

            activity_new_event_event_content_editText
                    .setText(this.eventContent);
            activity_new_event_event_content_editText.requestFocus();
            activity_new_event_event_content_editText
                    .setSelection(activity_new_event_event_content_editText
                            .getText().length());

            activity_new_event_event_name_editText.setText(this.eventName);
            activity_new_event_event_name_editText.setEnabled(false);

            activity_new_event_start_time_button.setText(this.eventStartTime);
            activity_new_event_end_time_button.setText(this.eventEndTime);

            activity_new_event_event_priority_ratingBar.setRating(Float
                    .valueOf(this.eventPriority));

            activity_new_event_time_interval_slider.setValue(Integer
                    .valueOf(this.eventCycleInterval));
            activity_new_event_time_interval_textView.setText(""
                    + this.eventCycleInterval);

            String[] array = eventLocationList.split(",");
            for (String s : array) {
                selectedNameList.add(s);
            }

            if (selectedNameList.size() != 0) {
                activity_new_event_event_location_checkbox.setChecked(true);
            }

            if (eventStartTime.trim().equals("0")) {
                activity_new_event_event_time_checkBox.setChecked(false);
                activity_new_event_time_interval_layout.setVisibility(View.INVISIBLE);
                activity_new_event_event_time_button_layout.setVisibility(View.INVISIBLE);
                layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 0);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.activity_new_event_event_time_checkBox);

                activity_new_event_time_interval_layout.setLayoutParams(layoutParams);
                activity_new_event_event_time_button_layout.setLayoutParams(layoutParams);
            } else {
                activity_new_event_event_time_checkBox.setChecked(true);
                activity_new_event_time_interval_layout.setVisibility(View.VISIBLE);
                activity_new_event_event_time_button_layout.setVisibility(View.VISIBLE);
                layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.activity_new_event_event_time_checkBox);

                activity_new_event_time_interval_layout.setLayoutParams(layoutParams);
                activity_new_event_event_time_button_layout.setLayoutParams(layoutParams);
            }


        }

        updatePlaceList();

    }

    private void setComponentControl() {
        setToolbarControl();
        setStartTimeButtonControl();
        setEndTimeButtonControl();
        setSliderControl();
        setRatingBarControl();
        setCheckBoxControl();
        setAddPlaceButtonControl();
        setEditTextControl();
        setClearButtonControl();
        setTouchHideKeyboardControl();
        setListViewControl();
    }

    private void adjustListViewHeight() {
        ListAdapter listAdapter = activity_new_event_event_location_listView
                .getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null,
                    activity_new_event_event_location_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = activity_new_event_event_location_listView
                .getLayoutParams();
        params.height = totalHeight
                + (activity_new_event_event_location_listView
                .getDividerHeight() * (listAdapter.getCount() - 1));
        activity_new_event_event_location_listView.setLayoutParams(params);

    }

    private void setListViewControl() {
        adjustListViewHeight();
        /*	activity_new_event_event_location_listView
                    .setOnTouchListener(new View.OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							activity_new_event_scrollView
									.requestDisallowInterceptTouchEvent(true);
							int action = event.getActionMasked();
							switch (action) {
							case MotionEvent.ACTION_UP:
								activity_new_event_scrollView
										.requestDisallowInterceptTouchEvent(false);
								break;
							}
							return false;
						}
					});*/
    }

    private void setTouchHideKeyboardControl() {
        activity_new_event_scrollView
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });
        activity_new_event_toolbar
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });
        activity_new_event_start_time_button
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });
        activity_new_event_end_time_button
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });
        activity_new_event_time_interval_slider
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });

        activity_new_event_event_priority_ratingBar
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });
        activity_new_event_event_location_checkbox
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });

        activity_new_event_event_location_add_button
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        return false;
                    }
                });

    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        if (activity_new_event_event_name_clear_button.getAlpha() == 1.0f) {
            activity_new_event_event_name_clear_button.animate().alpha(0.0f);

        }
        if (activity_new_event_event_name_clear_button.isEnabled()) {
            activity_new_event_event_name_clear_button.setEnabled(false);
        }
        if (activity_new_event_event_content_clear_button.getAlpha() == 1.0f) {
            activity_new_event_event_content_clear_button.animate().alpha(0.0f);

        }
        if (activity_new_event_event_content_clear_button.isEnabled()) {
            activity_new_event_event_content_clear_button.setEnabled(false);
        }
        activity_new_event_event_name_editText.clearFocus();
        activity_new_event_event_content_editText.clearFocus();
    }

    private void setClearButtonControl() {
        activity_new_event_event_content_clear_button
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        activity_new_event_event_content_editText.setText("");
                    }
                });
        activity_new_event_event_name_clear_button
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        activity_new_event_event_name_editText.setText("");
                    }
                });
    }

    private void setEditTextControl() {
        activity_new_event_event_name_editText
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (activity_new_event_event_name_editText.getText()
                                .toString().length() != 0) {
                            activity_new_event_event_name_clear_button
                                    .animate().alpha(1.0f);
                            activity_new_event_event_name_clear_button
                                    .setEnabled(true);

                        }
                        return false;
                    }
                });

        activity_new_event_event_name_editText
                .addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                        // TODO Auto-generated method stub
                        if (s.length() > 0) {
                            activity_new_event_event_name_clear_button
                                    .animate().alpha(1.0f);
                            activity_new_event_event_name_clear_button
                                    .setEnabled(true);
                        } else {
                            activity_new_event_event_name_clear_button
                                    .animate().alpha(0.0f);
                            activity_new_event_event_name_clear_button
                                    .setEnabled(false);
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if (s.length() > 0) {
                            activity_new_event_event_name_clear_button
                                    .animate().alpha(1.0f);
                            activity_new_event_event_name_clear_button
                                    .setEnabled(true);
                        } else {
                            activity_new_event_event_name_clear_button
                                    .animate().alpha(0.0f);
                            activity_new_event_event_name_clear_button
                                    .setEnabled(false);
                        }
                    }

                });

        activity_new_event_event_content_editText
                .setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        if (activity_new_event_event_content_editText.getText()
                                .toString().length() != 0) {
                            activity_new_event_event_content_clear_button
                                    .animate().alpha(1.0f);
                            activity_new_event_event_content_clear_button
                                    .setEnabled(true);

                        }
                        return false;
                    }
                });

        activity_new_event_event_content_editText
                .addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                        // TODO Auto-generated method stub
                        if (s.length() > 0) {
                            activity_new_event_event_content_clear_button
                                    .animate().alpha(1.0f);
                            activity_new_event_event_content_clear_button
                                    .setEnabled(true);
                        } else {
                            activity_new_event_event_content_clear_button
                                    .animate().alpha(0.0f);
                            activity_new_event_event_content_clear_button
                                    .setEnabled(false);
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        if (s.length() > 0) {
                            activity_new_event_event_content_clear_button
                                    .animate().alpha(1.0f);
                            activity_new_event_event_content_clear_button
                                    .setEnabled(true);
                        } else {
                            activity_new_event_event_content_clear_button
                                    .animate().alpha(0.0f);
                            activity_new_event_event_content_clear_button
                                    .setEnabled(false);
                        }
                    }

                });

    }

    private void setAddPlaceButtonControl() {
        activity_new_event_event_location_add_button
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        goToAddPlaceActivity();
                    }
                });
    }

    private void goToAddPlaceActivity() {

        Intent intent = new Intent(NewEventActivity.this,
                AddPlaceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("selected_name_list", selectedNameList);
        intent.putExtra("selected_address_list", selectedAddressList);
        intent.putExtra("selected_id_list", selectedIDList);
        intent.putExtra("selected_lat_list", selectedLatList);
        intent.putExtra("selected_long_list", selectedLongList);
        intent.putExtra("selected_type_list", selectedTypeList);

        startActivityForResult(intent, REQUEST_CODE);

        // finish();
    }

    private void setCheckBoxControl() {
        activity_new_event_event_location_checkbox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // TODO Auto-generated method stub
                        if (isChecked) {
                            activity_new_event_event_location_add_button
                                    .setVisibility(View.VISIBLE);
                            activity_new_event_event_location_listView
                                    .setVisibility(View.VISIBLE);
                        } else {
                            activity_new_event_event_location_add_button
                                    .setVisibility(View.GONE);
                            activity_new_event_event_location_listView
                                    .setVisibility(View.GONE);
                        }
                    }

                });

        activity_new_event_event_time_checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    activity_new_event_time_interval_layout.setVisibility(View.VISIBLE);
                    activity_new_event_event_time_button_layout.setVisibility(View.VISIBLE);
                    new_event_actionbar_switch.setEnabled(true);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.activity_new_event_event_time_checkBox);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT );
                    lp.addRule(RelativeLayout.BELOW, R.id.activity_new_event_event_time_button_layout);

                    activity_new_event_time_interval_layout.setLayoutParams(lp);
                    activity_new_event_event_time_button_layout.setLayoutParams(layoutParams);

                } else {
                    activity_new_event_time_interval_layout.setVisibility(View.INVISIBLE);
                    activity_new_event_event_time_button_layout.setVisibility(View.INVISIBLE);
                    new_event_actionbar_switch.setEnabled(false);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 0);
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.activity_new_event_event_time_checkBox);

                    activity_new_event_time_interval_layout.setLayoutParams(layoutParams);
                    activity_new_event_event_time_button_layout.setLayoutParams(layoutParams);
                }
            }
        });

    }

    private void setSliderControl() {
        activity_new_event_time_interval_slider
                .setOnValueChangedListener(new OnValueChangedListener() {

                    @Override
                    public void onValueChanged(int value) {
                        // TODO Auto-generated method stub

                        activity_new_event_time_interval_textView.setText(""
                                + value);
                    }

                });
    }

    private void setRatingBarControl() {
        activity_new_event_event_priority_ratingBar
                .setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar,
                                                float rating, boolean fromUser) {
                        // TODO Auto-generated method stub

                    }

                });
    }

    private void setStartTimeButtonControl() {
        activity_new_event_start_time_button
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        TimePickerDialogFragment fragment = new TimePickerDialogFragment(
                                activity_new_event_start_time_button,
                                activity_new_event_start_time_button);

                        // fragment.getDialog().getWindow().setLayout(100, 100);
                        fragment.show(getFragmentManager(),
                                "start_time_picker_dialog");

                    }
                });
    }

    private void setEndTimeButtonControl() {
        activity_new_event_end_time_button
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        TimePickerDialogFragment fragment = new TimePickerDialogFragment(
                                activity_new_event_start_time_button,
                                activity_new_event_end_time_button);
                        fragment.show(getFragmentManager(),
                                "end_time_picker_dialog");
                    }
                });
    }

    private void setToolbarControl() {
        activity_new_event_toolbar
                .setNavigationOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        finish();

                    }
                });
    }

    private void setSwitchControl() {
        new_event_actionbar_switch
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // TODO Auto-generated method stub
                        hideKeyboard();
                        if (isChecked) {
                            isNotificationOn = String.valueOf(true);
                        } else {
                            isNotificationOn = String.valueOf(false);
                        }
                    }

                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);

        if (isUpdatingEvent) {
            menu.findItem(R.id.menu_new_event_add).setIcon(
                    R.drawable.ic_action_done);
        }
        new_event_actionbar_switch = (Switch) menu.findItem(
                R.id.menu_new_event_switch).getActionView();

        if (isUpdatingEvent) {
            if (eventStartTime.trim().equals("0")) {
                new_event_actionbar_switch.setEnabled(false);
            } else {
                new_event_actionbar_switch.setEnabled(true);
            }

        } else {
            new_event_actionbar_switch.setEnabled(false);
        }


        new_event_actionbar_switch
                .setChecked(Boolean.valueOf(isNotificationOn));




		/*	int colorOn = 0xFF323E46;
            int colorOff = 0xFF666666;
			int colorDisabled = 0xFF333333;
			StateListDrawable thumbStates = new StateListDrawable();
			thumbStates.addState(new int[] { android.R.attr.state_checked },
					new ColorDrawable(colorOn));
			thumbStates.addState(new int[] { -android.R.attr.state_enabled },
					new ColorDrawable(colorDisabled));
			thumbStates.addState(new int[] {}, new ColorDrawable(colorOff)); // this
																				// one
																				// has
																				// to
																				// come
																				// last
			new_event_actionbar_switch.setThumbDrawable(thumbStates);
			
		*/

		/*new_event_actionbar_switch.getThumbDrawable().setColorFilter(
                getResources().getColor(R.color.theme_accent), Mode.MULTIPLY);
		new_event_actionbar_switch.getTrackDrawable().setColorFilter(
				getResources().getColor(R.color.theme_accent), Mode.MULTIPLY);*/

		/*	int colorOn = 0xFF323E46;
            int colorOff = 0xFF666666;
			int colorDisabled = 0xFF333333;
			StateListDrawable thumbStates = new StateListDrawable();
			thumbStates.addState(new int[] { android.R.attr.state_checked },
					new ColorDrawable(colorOn));
			thumbStates.addState(new int[] { -android.R.attr.state_enabled },
					new ColorDrawable(colorDisabled));
				thumbStates
						.addState(new int[] {}, new ColorDrawable(this.app.colorOff)); // this
																						// one
																						// has
																						// to
																						// come
																						// last
					new_event_actionbar_switch.setThumbDrawable(thumbStates);*/
        /*	ColorStateList buttonStates = new ColorStateList(new int[][] {
                    new int[] { android.R.attr.state_checked },
					new int[] { -android.R.attr.state_enabled }, new int[] {} },
					new int[] { getResources().getColor(R.color.theme_accent),
							getResources().getColor(R.color.theme_accent),
							getResources().getColor(R.color.theme_accent) });
			new_event_actionbar_switch.setButtonTintList(buttonStates);*/
        setSwitchControl();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.menu_new_event_add: {
                hideKeyboard();
                addNewEvent();
            }
            break;

        }

        return super.onOptionsItemSelected(item);
    }

    long gapInMins;

    private boolean isEndTimeAfterStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,
                Integer.valueOf(eventStartTime.split(":")[0]));
        cal.set(Calendar.MINUTE, Integer.valueOf(eventStartTime.split(":")[1]));

        Date startDate = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY,
                Integer.valueOf(eventEndTime.split(":")[0]));
        cal.set(Calendar.MINUTE, Integer.valueOf(eventEndTime.split(":")[1]));
        Date endDate = cal.getTime();

        long diffInMs = endDate.getTime() - startDate.getTime();

        gapInMins = TimeUnit.MILLISECONDS.toMinutes(diffInMs);
        return endDate.after(startDate);
    }

    private boolean isTimeIntervalValid() {
        return Long.parseLong(eventCycleInterval) < gapInMins;
    }

    private void addNewEvent() {
        this.eventName = activity_new_event_event_name_editText.getText()
                .toString().trim();
        this.eventContent = activity_new_event_event_content_editText.getText()
                .toString().trim();

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.eventCreateDate = df.format(Calendar.getInstance().getTime());

        this.eventPriority = String
                .valueOf(activity_new_event_event_priority_ratingBar
                        .getRating());

        this.eventStartTime = activity_new_event_start_time_button.getText()
                .toString();
        this.eventEndTime = activity_new_event_end_time_button.getText()
                .toString();

        this.eventCycleInterval = String
                .valueOf(activity_new_event_time_interval_slider.getValue());

        this.isEventFinished = String.valueOf(false);

		/*	this.isNotificationOn = String.valueOf(true);*/

        StringBuilder sBuilder = new StringBuilder();
        for (String s : selectedNameList) {
            sBuilder.append(s).append(",");
        }

        this.eventLocationList = sBuilder.toString();

        if (!activity_new_event_event_time_checkBox.isChecked()) {

            isNotificationOn = "false";
            eventStartTime = "0";
            eventEndTime = "0";
            eventCycleInterval = "0";

            if (this.eventName == null || this.eventName.length() == 0
                    || this.eventContent == null || this.eventContent.length() == 0
                    || this.eventCreateDate == null || this.eventPriority == null
                    || this.eventStartTime == null || this.eventEndTime == null
                    || this.eventCycleInterval == null
                    || this.isEventFinished == null
                    || this.isNotificationOn == null
                    || this.eventLocationList == null) {

                Toast.makeText(getBaseContext(), "One of the event info is empty.",
                        Toast.LENGTH_SHORT).show();

            } else {
                addNewEventInDatabase();
                finish();
            }
        } else {

            if (this.eventName == null || this.eventName.length() == 0
                    || this.eventContent == null || this.eventContent.length() == 0
                    || this.eventCreateDate == null || this.eventPriority == null
                    || this.eventStartTime == null || this.eventEndTime == null
                    || this.eventCycleInterval == null
                    || this.isEventFinished == null
                    || this.isNotificationOn == null
                    || this.eventLocationList == null) {

                Toast.makeText(getBaseContext(), "One of the event info is empty.",
                        Toast.LENGTH_SHORT).show();
            } else {

                if (isEndTimeAfterStartTime()) {
                    if (isTimeIntervalValid()) {
                        addNewEventInDatabase();
                        startAlarm(isUpdatingEvent);

                        finish();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Please set time interval within range.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(),
                            "End time should be after start time.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void addNewEventInDatabase() {
        eventSQLiteHelper = new SQLiteHelper(getBaseContext(),
                SQLiteHelper.TABLE_EVENT);

        if (eventSQLiteHelper.getEventByName(this.eventName) != null) {
            eventSQLiteHelper.updateEventByName(this.eventName, new RiseEvent(
                    this.eventName, this.eventContent, this.eventCreateDate,
                    this.eventPriority, this.eventStartTime, this.eventEndTime,
                    this.eventCycleInterval, this.isEventFinished,
                    this.isNotificationOn, this.eventLocationList));
        } else {
            eventSQLiteHelper.addEvent(new RiseEvent(this.eventName,
                    this.eventContent, this.eventCreateDate,
                    this.eventPriority, this.eventStartTime, this.eventEndTime,
                    this.eventCycleInterval, this.isEventFinished,
                    this.isNotificationOn, this.eventLocationList));

        }

    }

    private AlarmManager alarmManager;
    PendingIntent pendingIntent;
    PendingIntent intervalPendingIntent;

    public void startAlarm(boolean isUpdatingEvent) {

        if (Boolean.valueOf(isNotificationOn)) {
            int eventID = Integer.valueOf(eventSQLiteHelper
                    .getEventPrimaryIDByName(this.eventName));

            System.out.println(eventID);

            Intent alarmIntent = new Intent(this, EventAlarmReceiver.class);

            alarmIntent.putExtra("event_name", this.eventName);
            alarmIntent.putExtra("event_content", this.eventContent);
            alarmIntent.putExtra("event_id", eventID);
            alarmIntent.putExtra("event_location_list", this.eventLocationList);
            alarmIntent.putExtra("event_interval", this.eventCycleInterval);
            alarmIntent.putExtra("event_end_time", this.eventEndTime);
            alarmIntent.putExtra("event_start_time", this.eventStartTime);

            pendingIntent = PendingIntent.getBroadcast(this, eventID,
                    alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int interval = 60000;

			/*	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), interval, pendingIntent);*/

            String[] startTime = eventStartTime.split(":");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime[0]));
            calendar.set(Calendar.MINUTE, Integer.valueOf(startTime[1]));

            // setRepeating() lets you specify a precise custom interval--in
            // this
            // case,
            // 20 minutes.

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                    pendingIntent);

            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        } else {
            int eventID = Integer.valueOf(eventSQLiteHelper
                    .getEventPrimaryIDByName(this.eventName));

            System.out.println(eventID);

            Intent alarmIntent = new Intent(this, EventAlarmReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(this, eventID,
                    alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.cancel(pendingIntent);

            Intent intervalAlarmIntent = new Intent(this,
                    EventIntervalAlarmReceiver.class);

            intervalPendingIntent = PendingIntent.getBroadcast(this,
                    (eventID + 1) * 100000, intervalAlarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.cancel(intervalPendingIntent);

            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }

    }

    int REQUEST_CODE = 1;

    ArrayList<String> selectedNameList;
    ArrayList<String> selectedAddressList;
    ArrayList<String> selectedIDList;
    ArrayList<String> selectedLatList;
    ArrayList<String> selectedLongList;
    ArrayList<String> selectedTypeList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("selected_name_list")) {
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
                updatePlaceList();

            }
        }
    }
}
