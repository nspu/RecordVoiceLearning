package fr.nspu.dev.recordvoicelearning.view.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar

import fr.nspu.dev.recordvoicelearning.R
import fr.nspu.dev.recordvoicelearning.databinding.DialogRecordBinding

/**
 * Created by nspu on 18-02-12.
 */

class RecordDialog(context: Context) : Dialog(context) {

    private val mBinding: DialogRecordBinding

    val soundProgress: ProgressBar
        get() = mBinding.soundPb

    val stopButton: Button
        get() = mBinding.stopBtn

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_record,
                null,
                true)
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

    }
}
