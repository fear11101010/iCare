package com.example.arafathossain.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arafathossain.adapter.FoodAdapter;
import com.example.arafathossain.icare.AlarmReceiver;
import com.example.arafathossain.icare.ApplicationMain;
import com.example.arafathossain.icare.DatabaseHelper;
import com.example.arafathossain.icare.DietInformation;
import com.example.arafathossain.icare.R;
import com.example.arafathossain.icare.Reminder;
import com.example.arafathossain.interfacee.OnDietCreateListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class CreateDietFragment extends DialogFragment implements View.OnClickListener {
    View time;
    View title;
    View menu;
    View repeat;
    EditText foodName;
    TextView timeView, repeatView, menuView, titleView;
    ListView foodList;
    private FoodAdapter foodAdapter;
    CheckedTextView reminder;
    OnDietCreateListener onDietCreateListener;
    DietInformation dietInformation;
    boolean[] checkItem = {false, false, false, false, false, false, false};

    public static CreateDietFragment getInstance(String profileTitle) {
        Bundle bundle = new Bundle();
        bundle.putString("profileTitle", profileTitle);
        CreateDietFragment dietFragment = new CreateDietFragment();
        dietFragment.setArguments(bundle);
        return dietFragment;
    }

    public static CreateDietFragment getInstance(DietInformation dietInformation) {
        CreateDietFragment dietFragment = new CreateDietFragment();
        dietFragment.dietInformation = dietInformation;
        return dietFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.diet_create_layout, null);
        time = view.findViewById(R.id.timeLayout);
        title = view.findViewById(R.id.titleButtonLayout);
        menu = view.findViewById(R.id.menuButtonLayout);
        repeat = view.findViewById(R.id.repeatLayout);
        reminder = (CheckedTextView) view.findViewById(R.id.reminder);
        timeView = (TextView) view.findViewById(R.id.time);
        titleView = (TextView) view.findViewById(R.id.title);
        menuView = (TextView) view.findViewById(R.id.foodManu);
        repeatView = (TextView) view.findViewById(R.id.repeat);
        reminder = (CheckedTextView) view.findViewById(R.id.reminder);
        time.setOnClickListener(this);
        repeat.setOnClickListener(this);
        title.setOnClickListener(this);
        menu.setOnClickListener(this);
        reminder.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        if (dietInformation != null) {
            timeView.setText(dietInformation.getTime());
            titleView.setText(dietInformation.getTitle());
            menuView.setText(dietInformation.getMenu());
            repeatView.setText(dietInformation.getDay());
            repeat.setFocusable(false);
            repeat.setClickable(false);
            repeat.setBackgroundResource(R.drawable.button_layout_disable_background);
            if (dietInformation.getReminder().equalsIgnoreCase("yes")) {
                reminder.setChecked(true);
                reminder.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
            } else {
                reminder.setChecked(false);
                reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
            }
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    updateDietInformation();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveDietInformation();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        return builder.create();
    }

    public void showTimePicker() {
        final TimePicker timePicker = new TimePicker(getActivity());
        timePicker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(timePicker);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String time = format.format(calendar.getTime());
                timeView.setText(time);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showRepeatPicker() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMultiChoiceItems(R.array.weekDay, checkItem, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    checkItem[which] = true;
                }
            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String repeatDay = "";
                String[] days = getResources().getStringArray(R.array.weekDay);
                for (int i = 0; i < 7; i++) {
                    if (checkItem[i]) repeatDay += days[i] + ",";
                }
                if (repeatDay.length() == 0) repeatView.setText("Not set");
                else repeatView.setText(repeatDay.substring(0, repeatDay.length() - 1));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showTitlePicker() {
        final EditText titleBox = new EditText(getActivity());
        titleBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        titleBox.setHint("Add a title");
        if (!titleView.getText().toString().equalsIgnoreCase("Not set")) {
            titleBox.setText(titleView.getText());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(titleBox);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = titleBox.getText().toString();
                if (text == null || text.isEmpty()) titleView.setText("Not set");
                else titleView.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showMenuPicker() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.menu_layout, null);
        view.findViewById(R.id.add).setOnClickListener(this);
        foodName = (EditText) view.findViewById(R.id.foodName);
        foodList = (ListView) view.findViewById(R.id.foodList);
        String foods = menuView.getText().toString();
        foodAdapter = new FoodAdapter(getActivity());
        if (foods != null && !foods.isEmpty() && !foods.equalsIgnoreCase("Not set")) {
            foodAdapter.addAll(Arrays.asList(foods.split(",")));
            foodAdapter.notifyDataSetChanged();
        }
        foodList.setAdapter(foodAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (foodAdapter.getCount() == 0) menuView.setText("Not set");
                else {
                    String s = "";
                    for (int i = 0; i < foodAdapter.getCount(); i++) {
                        s += foodAdapter.getItem(i) + ",";
                    }
                    menuView.setText(s.substring(0, s.length() - 1));
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void saveDietInformation() {
        String timeText = timeView.getText().toString();
        String titleText = titleView.getText().toString();
        String menuText = menuView.getText().toString();
        String repeatText = repeatView.getText().toString();
        String reminderText = reminder.isChecked() ? "yes" : "no";
        if (timeText.equalsIgnoreCase("Not set")) {
            Toast.makeText(getActivity(), "Set time first", Toast.LENGTH_LONG).show();
            return;
        }
        if (titleText.equalsIgnoreCase("Not set")) {
            Toast.makeText(getActivity(), "Set title first", Toast.LENGTH_LONG).show();
            return;
        }
        if (menuText.equalsIgnoreCase("Not set")) {
            Toast.makeText(getActivity(), "Set menu first", Toast.LENGTH_LONG).show();
            return;
        }
        if (repeatText.equalsIgnoreCase("Not set")) {
            Toast.makeText(getActivity(), "Set day first", Toast.LENGTH_LONG).show();
            return;
        }
        DietInformation dietInformation = new DietInformation();
        dietInformation.setMenu(menuText);
        dietInformation.setProfileId(getActivity().getIntent().getStringExtra("profileId"));
        dietInformation.setTime(timeText);
        dietInformation.setReminder(reminderText);
        dietInformation.setTitle(titleText);
        for (String s : repeatText.split(",")) {
            dietInformation.setDay(s);
            int i = ApplicationMain.getDatabase().addDietInformation(dietInformation);
            if (i <= 0) {
                Toast.makeText(getActivity(), "Unable to add diet information", Toast.LENGTH_LONG).show();
                return;
            }
            if (reminder.isChecked()) setReminder(timeView.getText().toString(), s, i);
        }
        onDietCreateListener.onCreateDiet();
        Toast.makeText(getActivity(), "Insert Complete", Toast.LENGTH_LONG).show();
    }

    public void updateDietInformation() {
        String timeText = timeView.getText().toString();
        String titleText = titleView.getText().toString();
        String menuText = menuView.getText().toString();
        String reminderText = reminder.isChecked() ? "yes" : "no";
        ContentValues values = new ContentValues();
        if (!dietInformation.getTime().equalsIgnoreCase(timeText)) {
            if (timeText.equalsIgnoreCase("Not set")) {
                Toast.makeText(getActivity(), "Set time first", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.DietTable.COLUMN_TIME, timeText);
        }
        if (!dietInformation.getTitle().equalsIgnoreCase(titleText)) {
            if (titleText.equalsIgnoreCase("Not set")) {
                Toast.makeText(getActivity(), "Set title first", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.DietTable.COLUMN_TITLE, titleText);
        }
        if (!dietInformation.getMenu().equalsIgnoreCase(menuText)) {
            if (menuText.equalsIgnoreCase("Not set")) {
                Toast.makeText(getActivity(), "Set menu first", Toast.LENGTH_LONG).show();
                return;
            }
            values.put(DatabaseHelper.DietTable.COLUMN_MENU, menuText);
        }
        if (!dietInformation.getReminder().equalsIgnoreCase(reminderText)) {
            values.put(DatabaseHelper.DietTable.COLUMN_REMINDER, reminderText);
        }
        if (values.size() <= 0) {
            Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_LONG).show();
        } else if (ApplicationMain.getDatabase().updateDiet(values, dietInformation.getId()) > 0) {
            Toast.makeText(getActivity(), "Update complete", Toast.LENGTH_LONG).show();
            onDietCreateListener.onUpdateDiet();
        } else Toast.makeText(getActivity(), "Unable to update", Toast.LENGTH_LONG).show();
    }

    public void setReminder(String time, String day, int id) {
        String[] values = time.split(" |:");
        Calendar calendar = Calendar.getInstance();
        long currentTimeInMills = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR, Integer.parseInt(values[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(values[1]));
        if (values[2].equalsIgnoreCase("AM")) {
            calendar.set(Calendar.AM_PM, Calendar.AM);
        } else calendar.set(Calendar.AM_PM, Calendar.PM);
        if (day.equalsIgnoreCase("sunday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        } else if (day.equalsIgnoreCase("monday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else if (day.equalsIgnoreCase("tuesday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        } else if (day.equalsIgnoreCase("wednesday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        } else if (day.equalsIgnoreCase("thursday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        } else if (day.equalsIgnoreCase("friday")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }
        long reminderTimeInMills = calendar.getTimeInMillis();
        if (reminderTimeInMills < currentTimeInMills) {
            reminderTimeInMills += 7 * 24 * 60 * 60 * 1000;
            Log.d("reminder", "less");
        }
        int alarmId = ApplicationMain.getDatabase().addAlarmInformation(new Reminder(DietInformation.ALARM_KEY_DIET,String.valueOf(id),getActivity().getIntent().getStringExtra("profileId")));
        if (alarmId>-1) {
            Log.d("ppppp",alarmId+"");
            Intent alarmReceiver = new Intent(getActivity(), AlarmReceiver.class);
            alarmReceiver.setAction(DietInformation.ACTION_DIET);
            alarmReceiver.putExtra("title", titleView.getText());
            alarmReceiver.putExtra("day", repeatView.getText());
            alarmReceiver.putExtra("menu", menuView.getText());
            PendingIntent dietIntent = PendingIntent.getBroadcast(getActivity(), alarmId, alarmReceiver, 0);
            ApplicationMain.getAlarmManager().setRepeating(AlarmManager.RTC_WAKEUP,reminderTimeInMills, 24*60*60*1000* 7, dietIntent);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDietCreateListener = (OnDietCreateListener) activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timeLayout:
                showTimePicker();
                break;
            case R.id.repeatLayout:
                showRepeatPicker();
                break;
            case R.id.titleButtonLayout:
                showTitlePicker();
                break;
            case R.id.menuButtonLayout:
                showMenuPicker();
                break;
            case R.id.add:
                foodAdapter.add(foodName.getText().toString());
                foodAdapter.notifyDataSetChanged();
                foodName.setText("");
                break;
            case R.id.reminder:
                if (reminder.isChecked()) {
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                    reminder.setChecked(false);
                } else {
                    reminder.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                    reminder.setChecked(true);
                }
                break;
        }
    }
}
