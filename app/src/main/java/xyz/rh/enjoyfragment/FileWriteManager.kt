package xyz.rh.enjoyfragment

/**
 * Created by rayxiong on 2023/11/28.
 */
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import xyz.rh.common.xlog
import java.io.File
import java.io.RandomAccessFile
import java.util.Random

//java.lang.IllegalArgumentException: Primary directory null not allowed for content://media/external_primary/file; allowed directories are [Download, Documents]
//这个错误表明在尝试使用content://media/external_primary/file时，出现了 java.lang.IllegalArgumentException 异常。错误消息中还指出只允许使用 [Download, Documents] 这些目录。
//这可能是因为在Android 10及更高版本上，应用程序不再具有对外部存储（例如SD卡）的直接写入权限，而是通过使用特定目录进行访问。
//
//您可以尝试以下方法来解决这个问题：
//使用正确的目录： 确保您正在尝试将文件写入允许的目录，即 [Download, Documents]。这可能需要更改您的代码以指向正确的目录。
//动态请求权限： 如果您的应用程序在运行时需要外部存储的写入权限，确保您在运行时请求这些权限。在Android 6.0及更高版本中，权限必须在运行时动态请求。
//// 示例代码请求写入权限
//if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//}
//请确保在清单文件中添加了相应的权限声明。
//
//适配 Android 10 和更高版本的变化： Android 10 引入了存储访问框架（Storage Access Framework，SAF）。您可能需要使用 Intent.ACTION_CREATE_DOCUMENT 和 Intent.ACTION_OPEN_DOCUMENT 等操作来允许用户选择保存或打开文件的位置。

class FileWriteManager {
    private var filePath : String? = null
    private val handler = Handler(Looper.getMainLooper())

    init {
        val sdcardPath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            // 获取 SD 卡根目录
            Environment.getExternalStorageDirectory().absolutePath
        } else {
            // SD 卡未挂载，或者不可用
            null
        }
        xlog("FileWriteManager=== sdcardPath = $sdcardPath")
        sdcardPath?.let {
            filePath = "$it/Download/enjoyfile1.txt" // 想demo简单点默认只能往Download, Documents这俩目录写入文件。。。
        }

    }

    fun writeRandomDataFor5Seconds() {
        val random = Random()
        val startTime = System.currentTimeMillis()
        val endTime = startTime + 20_000 // 20 seconds

        // 使用 Handler.postDelayed 在主线程中循环执行写入操作
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (System.currentTimeMillis() < endTime) {
                    writeRandomDataToFile(random)
                    // 继续延迟执行
                    handler.postDelayed(this, 100) // 每100毫秒执行一次
                }
            }
        }, 0)
    }

    private fun writeRandomDataToFile(random: Random) {


        try {
            val file = File(filePath)
            if (!file.exists()) {
                file.createNewFile()
            }

            val randomAccessFile = RandomAccessFile(file, "rw")
            val currentPosition = randomAccessFile.length()

            // 移动到文件末尾
            randomAccessFile.seek(currentPosition)

            // 生成随机数据
            val data = random.nextInt().toString() + "\n"

            // 写入数据
            randomAccessFile.write(data.toByteArray())
            xlog("writeRandomDataToFile== $data")

            randomAccessFile.close()
        } catch (e: Exception) {
            xlog("writeRandomDataToFile== Exception")
            e.printStackTrace()
        }
    }
}



