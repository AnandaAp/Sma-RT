package com.maluku.sma_rt.extentions

import android.util.Patterns

object UserValidator {
    fun verifyData(email: String,password: String,name: String): Boolean{
        var status = false
        when(isEmailValid(email) &&
                isPasswordsValid(password) &&
                isNameValid(name)){
            true -> status = true
        }
        return status
    }
    fun verifyData(email: String,password: String): Boolean{
        var status = false
        when(isEmailValid(email) &&
                isPasswordsValid(password)){
            true -> status = true
        }
        return status
    }

    fun isEmailValid(email: String): Boolean =  Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordsValid(password: String): Boolean{
        var status = false
        when(passwordLengthValidator(password)){
            true -> {
                when(isPasswordIncludeLetters(password) &&
                        isPasswordIncludeUppercase(password) &&
                        isPasswordWithNumber(password)){
                    true -> status = true
                }
            }
            false -> status = false
        }
        return status
    }

    fun isNameValid(name: String): Boolean{
        var status = false
        when(isNameIncludeLetters(name)){
            true -> status = true
        }
        return status
    }

    fun isNameIncludeLetters(name: String): Boolean{
        val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var status = false
        if( name.matches( Regex(".*[$letters].*") ) ) {
            status = true
        }
        return status
    }

    fun passwordLengthValidator(password: String): Boolean{
        val minPasswordLength = 8
        var status = false
        if( password.length >= minPasswordLength){
            status = true
        }
        return status
    }

    fun isPasswordWithNumber(password: String): Boolean{
        var status = false
        val numbers = "0123456789"

        if( password.matches( Regex(".*[$numbers].*") ) ){
            status = true
        }
        return status
    }

    fun isPasswordIncludeUppercase(password: String): Boolean{
        val uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var status = false
        if( password.matches( Regex(".*[$uppercaseLetters].*") ) ){
            status = true
        }
        return status
    }

    fun isPasswordIncludeLetters(password: String): Boolean{
        val letters = "abcdefghijklmnopqrstuvwxyz"
        var status = false
        if( password.matches( Regex(".*[$letters].*") ) ) {
            status = true
        }
        return status
    }
}