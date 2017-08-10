package kotiln.com.baselibrary.tools.note

import android.view.View

/**
 * Created by fulixin on 2017/8/10.
 */
class Attribute(view: View, type: Class<*>, message: String) {
    var view: View? = null
    var type: Class<*>? = null
    var message: String? = null

    init {
        this.view = view
        this.type = type
        this.message = message
    }
}