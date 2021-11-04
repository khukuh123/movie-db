package com.miko.moviedb.ext.utils

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun SearchView.initTextChanges(): Flow<String?> {
    return callbackFlow<String?>{
        val onQueryTextListener = object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean{
                trySend(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean{
                trySend(newText)

                return true
            }

        }

        setOnQueryTextListener(onQueryTextListener)
        awaitClose { setOnQueryTextListener(null) }
    }.onStart {
        if(query.isNotEmpty()){
            emit(query.toString())
        }
    }
}