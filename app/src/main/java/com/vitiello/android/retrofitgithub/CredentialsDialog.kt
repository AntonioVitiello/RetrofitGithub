package com.vitiello.android.retrofitgithub

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class CredentialsDialog : DialogFragment() {

    private lateinit var listener: ICredentialsDialogListener

    interface ICredentialsDialogListener {
        fun onDialogPositiveClick(username: String, password: String)
    }

    companion object {
        private const val USERNAME_ARGS_KEY = "username_args_key"
        private const val PASSWORD_ARGS_KEY = "password_args_key"

        fun newInstance(username: String?, password: String?): CredentialsDialog {
            return CredentialsDialog().apply {
                arguments = Bundle().apply {
                    putString(USERNAME_ARGS_KEY, username)
                    putString(PASSWORD_ARGS_KEY, password)
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is ICredentialsDialogListener) {
            listener = activity as ICredentialsDialogListener
        } else throw IllegalArgumentException("Owner should implements ICredentialsDialogListener interface")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_credentials, null)
        val usernameEditText = view.findViewById<EditText>(R.id.usernameEditText).apply {
            setText(arguments!!.getString(USERNAME_ARGS_KEY))
        }
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText).apply {
            setText(arguments!!.getString(PASSWORD_ARGS_KEY))
        }
        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setTitle("Credentials")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Continue") { dialog, which ->
                isNotEmpty(usernameEditText.text.toString(), passwordEditText.text.toString()) { username, password ->
                    listener.onDialogPositiveClick(username, password)
                }
            }
        return builder.create()
    }

}