package fr.nspu.dev.recordvoicelearning.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import fr.nspu.dev.recordvoicelearning.R;
import fr.nspu.dev.recordvoicelearning.databinding.DialogRecordBinding;

/**
 * Created by nspu on 18-02-12.
 */

public class RecordDialog extends Dialog {

    private final DialogRecordBinding mBinding;

    public RecordDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.dialog_record,
                null,
                true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mBinding.getRoot());

    }

    public ProgressBar getSoundProgress(){
        return mBinding.soundPb;
    }

    public Button getStopButton(){
        return mBinding.stopBtn;
    }
}
