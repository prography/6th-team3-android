package com.prography.pethotel.ui.authentication.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.prography.pethotel.utils.TAG_PHOTO
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

open class BaseFragment : Fragment(){

    companion object {

        const val REQUEST_TAKE_PHOTO = 1;
        const val REQUEST_TAKE_ALBUM = 2;
        const val MY_PERMISSION_CAMERA = 10;
    }

    var myView: View? = null
    var myViewTag : String = ""
    var currentPhotoPath = ""

    private fun createImageFile() : File{
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }

    open fun getAlbum(view : View? = null){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        if(view != null){
            myView = view
            myViewTag = view.tag.toString()
            Log.d(TAG_PHOTO, view.tag.toString() +  " from base fragment")
        }
        startActivityForResult(intent, REQUEST_TAKE_ALBUM)
    }


    open fun takePhoto(view : View? = null) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName,
                    photoFile
                )
                Log.d(TAG_PHOTO, "photo uri : $photoURI")
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                if(view != null){
                    myView = view
                    myViewTag = view.tag.toString()
                    Log.d(TAG_PHOTO, view.tag.toString() +  " from base fragment")
                }
                startActivityForResult(takePictureIntent,
                    REQUEST_TAKE_PHOTO
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CAMERA -> {
                for (i in grantResults.indices) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(context, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                }
            }//end of my_permission_camera
        }
    }


    open fun checkPermission(){
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED &&
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton(
                            "설정"
                        ) { dialogInterface, i ->
                            val intent =
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:" + requireContext().packageName)
                            startActivity(intent)
                        }
                        .setPositiveButton(
                            "확인"
                        ) { dialogInterface, i -> activity?.finish() }
                        .setCancelable(false)
                        .create()
                        .show()
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    MY_PERMISSION_CAMERA
                )
            }
        }
    }
}