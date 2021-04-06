/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teamproject.sample.kosbank;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.sample.kosbank.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private static final String CLOUD_VISION_API_KEY = "AIzaSyBZXVf15nfuSSlv0MdAuIDUf4qEpG3diVo";
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    private TextView mImageDetails;
    private ImageView mMainImage;
    private Button btn_Submit;

    //a
    Map<String,String> personalInfo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageDetails = findViewById(R.id.image_details);
        mMainImage = findViewById(R.id.main_image);
        btn_Submit = findViewById(R.id.btn_Submit);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
            builder.create().show();
        });
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);

                intent.putExtra("Name", personalInfo.get("Name"));
                intent.putExtra("RRN", personalInfo.get("RRN"));
                intent.putExtra("Address", personalInfo.get("Address"));

                startActivity(intent);
            }
        });
    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);

                callCloudVision(bitmap);
                mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("DOCUMENT_TEXT_DETECTION");
                /*
                  TYPE_UNSPECIFIED	Unspecified feature type.
                  FACE_DETECTION	Run face detection.
                  LANDMARK_DETECTION	Run landmark detection.
                  LOGO_DETECTION	Run logo detection.
                  LABEL_DETECTION	Run label detection.
                  TEXT_DETECTION	Run text detection / optical character recognition (OCR). Text detection is optimized for areas of text within a larger image; if the image is a document, use DOCUMENT_TEXT_DETECTION instead.
                  DOCUMENT_TEXT_DETECTION	Run dense text document OCR. Takes precedence when both DOCUMENT_TEXT_DETECTION and TEXT_DETECTION are present.
                  SAFE_SEARCH_DETECTION	Run Safe Search to detect potentially unsafe or undesirable content.
                  IMAGE_PROPERTIES	Compute a set of image properties, such as the image's dominant colors.
                  CROP_HINTS	Run crop hints.
                  WEB_DETECTION	Run web detection.
                  PRODUCT_SEARCH	Run Product Search.
                  OBJECT_LOCALIZATION	Run localizer for object detection.

                  TYPE_UNSPECIFIED 지정되지 않은 기능 유형입니다.
                  FACE_DETECTION 얼굴 감지를 실행합니다.
                  LANDMARK_DETECTION 랜드 마크 감지를 실행합니다.
                  LOGO_DETECTION 로고 감지를 실행합니다.
                  LABEL_DETECTION 라벨 감지를 실행합니다.
                  TEXT_DETECTION 텍스트 감지 / 광학 문자 인식 (OCR)을 실행합니다. 텍스트 감지는 더 큰 이미지 내의 텍스트 영역에 최적화되어 있습니다. 이미지가 문서 인 경우 대신 DOCUMENT_TEXT_DETECTION을 사용하십시오.
                  DOCUMENT_TEXT_DETECTION 고밀도 텍스트 문서 OCR을 실행합니다. DOCUMENT_TEXT_DETECTION 및 TEXT_DETECTION이 모두있는 경우 우선 적용됩니다.
                  SAFE_SEARCH_DETECTION 안전 검색을 실행하여 잠재적으로 안전하지 않거나 바람직하지 않은 콘텐츠를 감지합니다.
                  IMAGE_PROPERTIES 이미지의 주요 색상과 같은 이미지 속성 집합을 계산합니다.
                  CROP_HINTS 자르기 힌트를 실행합니다.
                  WEB_DETECTION 웹 감지를 실행합니다.
                  PRODUCT_SEARCH 상품 검색을 실행합니다.
                  OBJECT_LOCALIZATION 개체 감지를 위해 로컬 라이저를 실행합니다.
                 */
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }
    private static class LableDetectionTask extends AsyncTask<Object, Void, Map<String,String>> {
        private final WeakReference<MainActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(MainActivity activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected Map<String,String> doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return null;
        }


        //파라미터 값이 Map<> result
        protected void onPostExecute(Map<String,String> result) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                TextView imageDetail = activity.findViewById(R.id.image_details);
                String a = result.get("Name")+'\n';
                a += result.get("RRN")+'\n';
                a += result.get("Address")+'\n';


                imageDetail.setText(a);
                //a
            }
            
            
        }
    }

    //cloudVision (OCR)기능을 부를때 발생하는
    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, Map<String,String>> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            personalInfo =  labelDetectionTask.execute().get();

            //
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder

                    .setMessage("이름 : " + personalInfo.get("Name") +
                            "\n주민번호 : " + personalInfo.get("RRN") +
                            "\n주소 : " + personalInfo.get("Address") + "가 맞습니까?" +
                            "\n(아니오를 누르면 카메라 화면으로 이동합니다.")



                    /*
                        .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                        .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
                    * */
                    .setPositiveButton("예", null)
                    .setNegativeButton("아니오", (dialog, which) -> startCamera());
            builder.create().show();

        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    //OCR 돌려서 나온 값들을 가공해서 출력
    private static Map<String,String> convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("출력값 입니다!:\n\n");
        Map<String, String> map = new HashMap<>();
        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();

        if (labels != null) {
            String[] infos = labels.get(0).getDescription().split("\n");

            String Name= null;
            String RRN = null;
            String Address= null;
            /*
            for(int i =0; i < infos.length; i++){
                message.append(i+"번째 : " + args[i] + "\n");
            }
            */
            int nameidx = infos[1].indexOf("(");
            Name = infos[1].substring(0, nameidx);
            RRN = infos[2];
            Address =  infos[3] + infos[4] + infos[5];

            message.append("이름 : "+ Name + "\n");
            message.append("주민 : " + RRN + "\n");
            message.append("주소 : " + Address + "\n");

            map.put("Name", Name);
            map.put("RRN", RRN);
            map.put("Address", Address);


        } else {
            message.append("nothing");
        }

        return map;
    }
}
