package com.example.yourbooks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : BaseFragment() {

    private val REG_DEBUG = "REG_DEBUG"
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSingUpClick(view)
    }

    private fun setupSingUpClick(v:View) {
        registerButton.setOnClickListener {
            val email = registerEmail.text?.trim().toString()
            val pass = registerPassword.text?.trim().toString()

            v.hideKeyboard()

            if(email=="")
                Snackbar.make(requireView(), "Nie wprowadziłeś maila.", Snackbar.LENGTH_SHORT).show()
            else if (pass == "")
                Snackbar.make(requireView(), "Nie wprowadziłeś maila.", Snackbar.LENGTH_SHORT).show()
            else if(pass.length<8)
                    Snackbar.make(requireView(), "Hasło musi mieć wiecej niż 8 znaków.", Snackbar.LENGTH_SHORT).show()
            else {
                auth.createUserWithEmailAndPassword(email, pass)
                         .addOnSuccessListener { authRes ->
                                if (authRes != null)
                                    startApp()

                            }
                            .addOnFailureListener { exc ->
                                if(exc.message.toString()=="The email address is badly formatted.")
                                    Snackbar.make(requireView(), "Niepoprawny format maila.", Snackbar.LENGTH_SHORT).show()
                                else
                                    Snackbar.make(requireView(), "Coś poszło nie tak jak powinno.", Snackbar.LENGTH_SHORT).show()
                                Log.d(REG_DEBUG, exc.message.toString())
                            }
                }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}