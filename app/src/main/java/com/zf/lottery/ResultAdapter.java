package com.zf.lottery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

public class ResultAdapter extends TableDataAdapter<MaxStat> {
    public ResultAdapter(Context context, List<MaxStat> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        MaxStat maxStat = getRowData(rowIndex);
        String text = null;
        switch (columnIndex) {
            case 0:
                text = maxStat.getType().getName();
                break;
            case 1:
                text = String.valueOf(maxStat.getNumber());
                break;
            case 2:
                text = String.valueOf(maxStat.getAbsence());
                break;
            case 3:
                text = String.valueOf(maxStat.getMaxAbsence());
                break;
            case 4:
                text = String.format(Locale.getDefault(), "%.2f", maxStat.getProbability() * 10000);
                break;
        }
        textView.setText(text);
        return textView;
    }

    @Override
    public int getCount() {
        return getData() == null ? 0 : super.getCount();
    }
}
