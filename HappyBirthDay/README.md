# Happy BirthDay App (Jetpack Compose)

Aplikasi Android sederhana untuk menampilkan ucapan ulang tahun menggunakan Jetpack Compose.

## Fitur
- Tampilan ucapan ulang tahun dengan gaya minimalis.
- Teks utama besar di tengah layar.
- Teks pengirim di bawah teks utama.
- Preview Compose langsung dari Android Studio.

## Hasil Tampilan (Screenshot)
> Simpan file screenshot di `docs/images/hasil-app.png` agar gambar di bawah tampil otomatis di README.

![Hasil Tampilan Aplikasi](docs/images/hasil-app.png)

## Tech Stack
- Kotlin
- Android Gradle Plugin `8.11.2`
- Kotlin `2.0.21`
- Jetpack Compose (BOM `2024.09.00`)
- Material 3

## Struktur Proyek Penting
- `app/src/main/java/com/example/happybirthday/MainActivity.kt` - UI utama dan preview.
- `app/src/main/java/com/example/happybirthday/ui/theme/Theme.kt` - konfigurasi tema Compose.
- `app/src/main/res/` - resource Android (warna, string, icon, dll).
- `app/build.gradle.kts` - konfigurasi module app.
- `gradle/libs.versions.toml` - versi dependency dan plugin.

## Prasyarat
- Android Studio versi terbaru (disarankan yang kompatibel dengan AGP 8.x).
- JDK 11 (proyek ini mengatur target JVM ke 11).
- Android SDK yang sesuai dengan konfigurasi proyek (`compileSdk 36`, `minSdk 24`).

## Cara Menjalankan Proyek
### Opsi 1 - lewat Android Studio
1. Buka folder proyek `HappyBirthDay` di Android Studio.
2. Tunggu Gradle Sync selesai.
3. Jalankan di emulator/device dengan klik **Run**.

### Opsi 2 - lewat terminal (PowerShell)
```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:installDebug
```

> Catatan: pastikan emulator/device sudah aktif sebelum `installDebug`.

## Langkah-Langkah Membuat App Happy Birthday
Berikut alur pembuatan aplikasi seperti implementasi di proyek ini.

### 1) Buat proyek Android Compose
1. Buka Android Studio.
2. Pilih **New Project**.
3. Pilih template **Empty Activity** (Compose).
4. Atur nama project, package name, dan minimum SDK.

### 2) Aktifkan dan siapkan Compose
Pastikan pada `app/build.gradle.kts`:
- `buildFeatures { compose = true }`
- Dependency Compose, Material 3, dan activity-compose sudah terpasang.

### 3) Buat tema aplikasi
Di `ui/theme/Theme.kt`, buat fungsi tema (contoh di proyek ini: `HappyBirthDayTheme`) yang membungkus `MaterialTheme`.

### 4) Buat composable teks ucapan
Di `MainActivity.kt`, buat fungsi:
- `GreetingText(message: String, from: String)`

Isi utamanya:
- `Column` dengan `Arrangement.Center` dan `Alignment.CenterHorizontally` agar konten berada di tengah.
- `Text` pertama untuk pesan utama (font besar).
- `Text` kedua untuk nama pengirim (lebih kecil).

### 5) Tampilkan UI dari `onCreate`
Di `setContent { ... }`:
1. Bungkus dengan `HappyBirthDayTheme`.
2. Tambahkan `Surface` full screen.
3. Panggil `GreetingText(...)` untuk menampilkan ucapan.

### 6) Tambahkan Preview Compose
Di file yang sama, tambahkan:
- `@Preview(showBackground = true, showSystemUi = true)`
- Fungsi preview yang memanggil `GreetingText(...)`

Ini memudahkan melihat hasil UI tanpa menjalankan emulator setiap saat.

### 7) Jalankan dan uji tampilan
- Cek tampilan di Preview.
- Jalankan ke emulator/device.
- Sesuaikan ukuran font, warna background, dan teks jika diperlukan.

## Kustomisasi Cepat
- Ubah teks ucapan di `MainActivity.kt` pada parameter `message` dan `from`.
- Ubah warna background di `Surface(color = ...)`.
- Ubah ukuran teks di `fontSize` dan `lineHeight`.
- (Opsional) Pindahkan teks ke `app/src/main/res/values/strings.xml` agar lebih rapi.

## Troubleshooting Singkat
- Jika preview tidak muncul: lakukan **Build > Rebuild Project**.
- Jika dependency error: klik **Sync Project with Gradle Files**.
- Jika masih error aneh di IDE: **File > Invalidate Caches / Restart**.

## Lisensi
Bebas digunakan untuk pembelajaran dan pengembangan pribadi.
