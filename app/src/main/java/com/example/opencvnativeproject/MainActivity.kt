package com.example.opencvnativeproject

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.ViewDebug.IntToString
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.*

//import org.
class MainActivity : AppCompatActivity() {
    ///
   // private val imageBitmap by lazy { (ContextCompat.getDrawable(this, R.drawable.lena) as BitmapDrawable).bitmap }


    override fun onCreate(savedInstanceState: Bundle?) {
       //*******
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //*******

        val textResourceId: TextView =  findViewById <TextView> (R.id.mapsview)
        val imageResourceId: ImageView = findViewById <ImageView> (R.id.articleImg)!!


        OpenCVLoader.initDebug()




        val img = Utils.loadResource(getApplicationContext(), R.drawable.lena);



        if ( img.empty()){
            Log.e("print","-----Empty Matrix________" )
        }

        if ( img != null ) {
            Log.e("print", "number " + img.width().toString() )
            textResourceId.text = img.cols().toString()
        }
         else{
            Log.e("print", "Input==NULL ")
            textResourceId.text = "input ===NULLL"
        }


        Imgproc.cvtColor(img,img,Imgproc.COLOR_RGB2GRAY)


        //creat a template bitmap
        val img_bitmap = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888)

        //convert from mat to bitmap
        Utils.matToBitmap(img, img_bitmap);

        //set picture to image view
        imageResourceId.setImageBitmap(img_bitmap)


    }




    //a function  convert gray scale
    fun makeGray(bitmap: Bitmap) : Bitmap {

        // Create OpenCV mat object and copy content from bitmap
        val mat = Mat()
        //grab data from input Mat and put it onto new local Mat
        Utils.bitmapToMat(bitmap, mat)

        // Convert to grayscale
        //input Mat , do some gray scale on it , and output grayscale to same Mat
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)

        // Make a mutable bitmap to copy grayscale image
        //make a mutable bitmap named grayBitmap and to be used later
        val grayBitmap = bitmap.copy(bitmap.config, true)

        //copy from input -> old Mat to the new mutable bitmap named => grayBitmap
        Utils.matToBitmap(mat, grayBitmap)

        return grayBitmap
    }


    //function get edges of Bitmap
    fun getEdges(source: Mat): Mat {
        //create new instance of bitmap
        val gray = Mat()
        //get input bitmap (source ) apply grayscale and send it as new name => gray
        cvtColor(source, gray, COLOR_RGB2GRAY)
        //create new instance of bitmap name it blur
        val blur = Mat()
        //take the gray bitmap , pick size to do blurring technique and output => blur
        GaussianBlur(gray, blur, Size(5.0, 5.0), 0.0)
        //create new instance of bitmap name it destination
        //Cannny function input-> blur , do canny algorithm with threshold and output => dest
        val dest = Mat()
        Canny(blur, dest, 50.0, 150.0)
        //return bitmap
        return dest
    }

}