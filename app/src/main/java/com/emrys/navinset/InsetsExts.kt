package com.emrys.navinset

import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

fun WindowInsets.compat() = WindowInsetsCompat.toWindowInsetsCompat(this)