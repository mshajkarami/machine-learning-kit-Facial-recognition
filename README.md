# ML Kit Facial Recognition App 🤖📷

This Android application uses **Google ML Kit** to detect human faces in real-time using the device camera. It provides detailed information such as:

- Number of faces detected
- Head rotation angles (Euler angles)
- Smile probability
- Eye openness probability

---

## 📸 Features

- Capture an image from the camera
- Detect faces using **ML Kit Face Detection API**
- Display face information with tracking ID
- Copy detection results with a long press
- Supports multiple faces in a single image

---

## 🚀 Technologies Used

- **Kotlin / Java** (Base language)
- **Android SDK**
- **ML Kit Face Detection**
- **Camera Intent**
- **ViewBinding**
- **Firebase ML Kit (on-device)**

---

## 📱 Screenshots

| Camera | Detection Result |
|--------|------------------|
| 📷 Image Capture | ✅ Face Info Displayed |

*(You can add your own screenshots here in the future)*

---

## 🧠 How It Works

1. User taps the camera button.
2. App captures image via `MediaStore.ACTION_IMAGE_CAPTURE`.
3. Image is sent to `ML Kit FaceDetector`.
4. Results (face count, smile probability, eye openness, etc.) are displayed in a scrollable `TextView`.
5. Long-press on the result allows copying to clipboard.

---

## 🛠 Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/machine-learning-kit-Facial-recognition.git
2.Open the project in Android Studio.

3.Make sure Firebase and ML Kit dependencies are correctly added in build.gradle.

4.Run the app on a physical device (ML Kit might not work properly on emulators for face detection).

🔐 Permissions Required
Make sure the app has these permissions in AndroidManifest.xml:
implementation 'com.google.mlkit:face-detection:16.1.5'
implementation 'com.google.firebase:firebase-ml-vision:24.0.3'
Also, request camera permissions at runtime if needed.

📦 Dependencies
implementation 'com.google.mlkit:face-detection:16.1.5'
implementation 'com.google.firebase:firebase-ml-vision:24.0.3'
📄 License
This project is licensed under the MIT License.
🙋‍♂️ Author
Mohamad Saleh HajKarami
☕ Contributions
Pull requests and stars are welcome!
If you find bugs or want to suggest features, feel free to open an issue.
