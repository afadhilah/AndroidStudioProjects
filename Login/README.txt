Panduan Pembuatan Aplikasi Login Android (Jetpack Compose)

Project: Login
Package: com.example.login

============================================================
1. DESKRIPSI SINGKAT
============================================================
Aplikasi ini adalah halaman login sederhana berbasis Jetpack Compose.
Fitur utamanya:
- Tampilan form email dan password
- Tombol Login
- Teks "Forgot Password?"
- Ikon social login (Facebook, Google, Twitter)
- Logo diambil dari internet menggunakan Coil (AsyncImage)

============================================================
2. PRASYARAT
============================================================
Sebelum mulai, pastikan Anda punya:
- Android Studio (versi terbaru direkomendasikan)
- JDK 11
- Koneksi internet (untuk download dependency dan load gambar URL)
- SDK Android sesuai project (compileSdk 36, minSdk 24)

============================================================
3. MEMBUAT PROJECT BARU
============================================================
1) Buka Android Studio
2) Pilih New Project
3) Pilih template Empty Activity (Compose)
4) Isi:
   - Name: Login
   - Package name: com.example.login
   - Minimum SDK: 24
5) Klik Finish

============================================================
4. KONFIGURASI DEPENDENCY
============================================================
A. Tambah Coil pada Version Catalog
File: gradle/libs.versions.toml
Tambahkan:

[versions]
coilCompose = "2.7.0"

[libraries]
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coilCompose" }

B. Pakai dependency Coil di modul app
File: app/build.gradle.kts
Tambahkan pada dependencies:

implementation(libs.coil.compose)

============================================================
5. IZIN INTERNET
============================================================
Karena logo diambil dari URL, wajib menambahkan permission internet.

File: app/src/main/AndroidManifest.xml
Tambahkan di dalam tag <manifest>:

<uses-permission android:name="android.permission.INTERNET" />

============================================================
6. IMPLEMENTASI UI LOGIN (COMPOSE)
============================================================
File utama:
app/src/main/java/com/example/login/MainActivity.kt

Langkah penting:
1) Panggil LoginScreen() di setContent:
   - enableEdgeToEdge()
   - setContent { MaterialTheme { LoginScreen() } }

2) Buat composable LoginScreen() dengan:
   - Column (kontainer utama)
   - AsyncImage untuk logo utama
   - Text untuk judul dan deskripsi
   - OutlinedTextField untuk email
   - OutlinedTextField untuk password
   - Button Login
   - Text clickable: "Forgot Password?"
   - Row berisi 3 AsyncImage untuk ikon sosial

3) Tambahkan Preview:
   - @Preview(showBackground = true)
   - fun LoginScreenPreview() { MaterialTheme { LoginScreen() } }

Contoh URL logo yang dipakai:
- Main logo
- Facebook
- Google
- Twitter

============================================================
7. MENJALANKAN APLIKASI
============================================================
1) Sync Gradle terlebih dahulu
2) Jalankan ke emulator atau device fisik
3) Pastikan internet aktif agar logo tampil

Opsional build via terminal (dari root project):
- Windows PowerShell:
  .\gradlew.bat :app:assembleDebug

============================================================
8. TROUBLESHOOTING UMUM
============================================================
1) Error "Unresolved reference: AsyncImage"
   Solusi:
   - Pastikan dependency Coil sudah ditambahkan
   - Lakukan Sync Project with Gradle Files

2) Gambar tidak tampil
   Solusi:
   - Cek permission INTERNET di AndroidManifest.xml
   - Cek koneksi internet emulator/device
   - Pastikan URL gambar valid (bisa dibuka di browser)

3) Build gagal setelah ubah dependency
   Solusi:
   - Clean Project lalu Rebuild
   - Pastikan versi dependency tidak bentrok


