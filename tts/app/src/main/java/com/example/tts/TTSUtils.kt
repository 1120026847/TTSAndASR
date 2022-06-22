package com.example.tts

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import java.util.*

@Suppress("DEPRECATION")

class TTSUtils private constructor(context: Context) {

     //context对象

     private val mContext: Context = context.applicationContext

     //核心播放对象

     private val textToSpeech: TextToSpeech?

    //是否支持

     private var isSupport = true

     init {

         textToSpeech = TextToSpeech(mContext, TextToSpeech.OnInitListener { i ->

             //textToSpeech的配置

            init(i)

            })

         }

     //textToSpeech的配置

   private fun init(i: Int) {

         if (i == TextToSpeech.SUCCESS) {

             val result = textToSpeech!!.setLanguage(Locale.SIMPLIFIED_CHINESE)

             // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规

            textToSpeech.setPitch(1.0f) //(这里推荐默认,不然不同手机可能发声不同，并且异常)

           textToSpeech.setSpeechRate(1.5f)

          if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

             //系统不支持中文播报

             isSupport = false

            Log.e("语音",""+isSupport)

             }

          }

        }

   fun isTTsSupport():Boolean{

      return isSupport

         }

    fun play(text: String) {

        if (!isSupport) {

             Toast.makeText(mContext, "暂不支持", Toast.LENGTH_SHORT).show()

             return

           }

         //设置播报语音音量（跟随手机音量调节而改变）

         val myHashAlarm = hashMapOf<String,String>()

      myHashAlarm[TextToSpeech.Engine.KEY_PARAM_STREAM] = AudioManager.STREAM_MUSIC.toString()

         //语音播报

        //QUEUE_ADD：播放完之前的语音任务后才播报本次内容

         //QUEUE_FLUSH：丢弃之前的播报任务，立即播报本次内容

       textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm)

        }

     fun destroy() {

         textToSpeech?.stop()

     textToSpeech?.shutdown()

        }

    companion object {

         //单例对象

         @SuppressLint("StaticFieldLeak")

        private var singleton: TTSUtils? = null

        fun getInstance(context: Context): TTSUtils {

           if (singleton == null) {

                synchronized(TTSUtils::class.java) {

                if (singleton == null) {

                singleton = TTSUtils(context)

               }

                 }

               }

            return this.singleton!!

            }

      }

}