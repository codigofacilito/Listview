package com.example.marines.mywidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;

/**
 * Created by codigofacilito on 21/12/16.
 */
public class ListProvider implements RemoteViewsFactory {
    private ArrayList<Notas>listaNotas=new ArrayList<>();
    private Context context=null;

    public ListProvider(Context context, Intent intent){

        this.context=context;
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        lista();
    }

    public void lista(){

        listaNotas=new Notas().notas();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listaNotas.size();
    }

    @Override
    public RemoteViews getViewAt(int posicion) {

        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.adaptador_notas);
        Notas nota=listaNotas.get(posicion);

        if(nota.getRealizado()){
            SpannableString spannableString=new SpannableString(nota.getNota());
            spannableString.setSpan(new StrikethroughSpan(),0,nota.getNota().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            remoteViews.setTextViewText(R.id.textAdapter,spannableString);
        }else{
            remoteViews.setTextViewText(R.id.textAdapter,nota.getNota());
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
