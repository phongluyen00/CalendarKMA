package com.example.retrofitrxjava.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.pre.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Calendar c = Calendar.getInstance();
//        System.out.println("Current dateTime => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String toDay = df.format(c.getTime());
        CharSequence date  = toDay;


        CharSequence widgetText = "Không có lịch học";
        if (PrefUtils.loadData(context) != null && PrefUtils.loadData(context).getToken() != null) {
            String mssv = PrefUtils.loadData(context).getToken();
            ScheduleModelResponse scheduleModelResponse = PrefUtils.loadData(context).getModelResponse();
            List<ScheduleModelResponse.Data> lst =  scheduleModelResponse.getData();
            for(int i = 0; i < lst.size();i++)
            {
                if (toDay.equals(lst.get(i).getDatetime())){
                    widgetText = covert(lst.get(i).getCaHoc() )+"\t"+  lst.get(i).getMonHoc() + "\n" ;
                    Log.d("AAAAAAAAAAAA",toDay + "-----------" + lst.get(i).getDatetime());
                }
            }
        }
        else
        {
            widgetText = "Kết nối internet không ổn định";
        }
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.tv_event_name, widgetText);
        views.setTextViewText(R.id.date, date);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    public static String covert(String input)
    {
        if(input.contains("1,2,3"))
        {
            input = "7:00 - 9:15";
        }
        else if (input.contains("4,5,6")){
            input = "9:30 - 11:15";
        }
        else if (input.contains("7,8,9"))
        {
            input = "12:30 - 14:45";
        }
        else
        {
            input = "15:00 - 17:30";
        }
        return input;
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

