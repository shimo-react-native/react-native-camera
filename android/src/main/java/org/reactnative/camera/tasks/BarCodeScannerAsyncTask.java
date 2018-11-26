package org.reactnative.camera.tasks;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

import org.reactnative.camera.CropRect;
import org.reactnative.camera.RNCameraViewHelper;

public class BarCodeScannerAsyncTask extends android.os.AsyncTask<Void, Void, Result> {
    private final CropRect mCropRect;
    private byte[] mImageData;
    private int mWidth;
    private int mHeight;
    private BarCodeScannerAsyncTaskDelegate mDelegate;
    private final MultiFormatReader mMultiFormatReader;

    //  note(sjchmiela): From my short research it's ok to ignore rotation of the image.
    public BarCodeScannerAsyncTask(
        BarCodeScannerAsyncTaskDelegate delegate,
        MultiFormatReader multiFormatReader,
        byte[] imageData,
        int width,
        int height
    ) {
        this(delegate, multiFormatReader, imageData, width, height, null);
    }

    public BarCodeScannerAsyncTask(
        BarCodeScannerAsyncTaskDelegate delegate,
        MultiFormatReader multiFormatReader,
        byte[] imageData,
        int width,
        int height,
        CropRect cropRect
    ) {
        mImageData = imageData;
        mWidth = width;
        mHeight = height;
        mDelegate = delegate;
        mMultiFormatReader = multiFormatReader;
        mCropRect = cropRect;
    }

    @Override
    protected Result doInBackground(Void... ignored) {
        if (isCancelled() || mDelegate == null) {
            return null;
        }

        Result result = null;

        try {
            BinaryBitmap bitmap = generateBitmapFromImageData(mImageData, mWidth, mHeight);
            result = mMultiFormatReader.decodeWithState(bitmap);
        } catch (NotFoundException e) {
            BinaryBitmap bitmap = generateBitmapFromImageData(rotateImage(mImageData, mWidth, mHeight), mWidth,
                mHeight);
            try {
                result = mMultiFormatReader.decodeWithState(bitmap);
            } catch (NotFoundException e1) {
                //no barcode Found
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private byte[] rotateImage(byte[] imageData, int width, int height) {
        byte[] rotated = new byte[imageData.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotated[x * height + height - y - 1] = imageData[x + y * width];
            }
        }
        return rotated;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (result != null) {
            mDelegate.onBarCodeRead(result, mWidth, mHeight);
        }
        mDelegate.onBarCodeScanningTaskCompleted();
    }

    private BinaryBitmap generateBitmapFromImageData(byte[] imageData, int width, int height) {
        int left, top, dstWidth, dstHeight;
        if (mCropRect == null) {
            left = top = 0;
            dstWidth = width;
            dstHeight = height;
        } else {
            if (RNCameraViewHelper.isPortrait(mCropRect.rotation)) {
                left = mCropRect.left;
                top = mCropRect.top;
                dstWidth = mCropRect.width;
                dstHeight = mCropRect.height;
            } else {
                left = mCropRect.top;
                top = mCropRect.left;
                dstWidth = mCropRect.height;
                dstHeight = mCropRect.width;
            }
        }

        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
            imageData, // byte[] yuvData
            width, // int dataWidth
            height, // int dataHeight
            left, // int left
            top, // int top
            dstWidth, // int width
            dstHeight, // int height
            false // boolean reverseHorizontal
        );
        return new BinaryBitmap(new HybridBinarizer(source));
    }
}
