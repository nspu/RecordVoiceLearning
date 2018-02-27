package fr.nspu.dev.recordvoicelearning.utils.callback

/**
 * Created by nspu on 18-02-04.
 */

interface ClickCallback {
    fun onClick(o: Any)
    fun onLongClick(o: Any): Boolean
}
