package com.example.vinod.githubrepoapp.utility

import android.text.Html
import android.text.Spanned
import android.view.View

fun String.capitalizeFirstLetterOfWords(): String = split(" ").joinToString(
  " "
) { word -> word.toLowerCase().capitalize() }

fun String.fromHtml(): Spanned =
  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
  } else {
    Html.fromHtml(this)
  }

fun Int?.orDefaultInt() = this ?: 0

fun View.hideView() {
  visibility = View.GONE
}

fun View.showView() {
  visibility = View.VISIBLE
}