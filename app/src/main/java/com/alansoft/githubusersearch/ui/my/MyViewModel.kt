package com.alansoft.githubusersearch.ui.my

import androidx.lifecycle.ViewModel
import com.alansoft.githubusersearch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@HiltViewModel
class MyViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {


}