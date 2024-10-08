package com.example.gympal2.util

const val API_PORT = "3000"
const val API_BASE_URL = "http://192.168.50.232:$API_PORT"
const val API_WS_URL = "ws://192.168.50.232:$API_PORT"

const val USER_INITIAL = "user"
const val GYM_INITIAL = "gym"
const val WORKOUT_INITIAL = "workout"


const val REGISTER_USER_URL = "$USER_INITIAL/register"
const val LOGIN_USER_URL = "$USER_INITIAL/login"
const val GET_USER_URL = "$USER_INITIAL/getUser"
const val UPDATE_USER_URL = "$USER_INITIAL/update" //need id as param after update.

const val GET_ALL_GYMS_URL = "$GYM_INITIAL/all"
const val CREATE_GYM_URL = "$GYM_INITIAL/create"
const val UPDATE_GYM_URL = "$GYM_INITIAL/update" //need id as param after update.

const val CREATE_WORKOUT_URL = "$WORKOUT_INITIAL/create"
const val GET_GYM_WORKOUT_URL = "$WORKOUT_INITIAL/gym"