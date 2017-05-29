package com.example.marines.mywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by codigofacilito on 21/12/16.
 */
public class WidgetProvider extends AppWidgetProvider{

    Context context;
    int id;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length;i++){
          this.context=context;
           id=appWidgetIds[i];
        RemoteViews lista=actualizarLista(appWidgetIds[i]);
           appWidgetManager.updateAppWidget(appWidgetIds[i],lista);
       }


        RemoteViews numero=actualizar();
        appWidgetManager.updateAppWidget(id,numero);


        RemoteViews boton=boton();
        appWidgetManager.updateAppWidget(id,boton);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public RemoteViews actualizar(){
        RemoteViews remoteViews=obtenerWidget();
        String numero=String.format((new Random().nextInt(900)+100)+"");
        remoteViews.setTextViewText(R.id.textView,numero);

        return remoteViews;

    }

    public RemoteViews boton(){
        Intent inten =new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,inten,0);
        RemoteViews remoteViews=obtenerWidget();
        remoteViews.setOnClickPendingIntent(R.id.nuevo,pendingIntent);

        return  remoteViews;
    }


    public RemoteViews obtenerWidget(){
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.vista_widget);
        return  remoteViews;

    }

    public RemoteViews actualizarLista(int appWidgetId){

        RemoteViews remoteViews =obtenerWidget();
        Intent intent=new Intent(context,WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(appWidgetId,R.id.listView,intent);
        remoteViews.setEmptyView(R.id.listView,R.id.textAdapter);

        return remoteViews;
    }
}
