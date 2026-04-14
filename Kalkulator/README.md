# Kalkulator Android (Jetpack Compose)

Aplikasi kalkulator sederhana berbasis **Jetpack Compose** dengan fitur:
- Operasi dasar: `+`, `-`, `*`, `/`
- Validasi input angka
- Proteksi pembagian dengan nol
- Dukungan input desimal koma (`,`)
- UI aman saat keyboard muncul (`imePadding`, `verticalScroll`)

## 1) Prasyarat

Pastikan sudah terpasang:
- Android Studio (versi terbaru yang mendukung AGP 8.x)
- JDK 11
- Android SDK (min SDK 24)

Konfigurasi proyek saat ini:
- `compileSdk = 36`
- `minSdk = 24`
- `targetSdk = 36`
- Kotlin `2.0.21`
- AGP `8.11.2`

## 2) Struktur File Utama

- `app/src/main/java/com/example/kalkulator/MainActivity.kt`
  - Entry point aplikasi
  - UI kalkulator (`CalculatorScreen`)
  - Logika perhitungan (`runCalculation`, `parseNumber`, `formatResult`)
- `app/src/main/res/values/strings.xml`
  - Semua teks UI dan pesan error
- `app/build.gradle.kts`
  - Konfigurasi Android + dependency Compose
- `gradle/libs.versions.toml`
  - Versi dependency dan plugin

## 3) Cara Membuat Aplikasi Kalkulator (Step by Step)

### Step A - Buat proyek baru
1. Buka Android Studio.
2. Pilih **New Project** > **Empty Activity**.
3. Gunakan Kotlin dan aktifkan Compose.
4. Set package, misalnya `com.example.kalkulator`.

### Step B - Tambahkan UI input, hasil, dan tombol
Di `MainActivity.kt`, buat composable `CalculatorScreen` dengan komponen:
- 2 `OutlinedTextField` untuk angka pertama dan kedua
- 1 `OutlinedTextField` read-only untuk hasil
- Tombol operasi: `+`, `-`, `*`, `/`
- Tombol `Clear`
- Teks error jika input tidak valid

Gunakan pengaturan layout berikut agar nyaman saat keyboard muncul:
- `verticalScroll(rememberScrollState())`
- `imePadding()`
- `navigationBarsPadding()`

### Step C - Simpan state
Gunakan `rememberSaveable` untuk:
- `number1`
- `number2`
- `result`
- `errorMessage`

Contoh konsep:
- Saat input berubah, kosongkan `errorMessage`
- Saat tombol operator ditekan, jalankan fungsi hitung

### Step D - Implementasi logika hitung
Gunakan fungsi lokal `runCalculation(operator: String)`:
1. Parse input dengan helper `parseNumber(...)`
2. Jika salah satu input invalid, tampilkan error
3. Jalankan operasi berdasarkan operator
4. Cegah pembagian nol
5. Format hasil dengan `formatResult(...)`

Contoh helper yang dipakai di proyek ini:
- `parseNumber`: trim input dan ubah `,` menjadi `.` agar desimal lokal tetap terbaca
- `formatResult`: jika bilangan bulat, tampilkan tanpa desimal

### Step E - Simpan string ke resources
Masukkan label dan pesan error ke `strings.xml`, contoh:
- `label_first_number`
- `label_second_number`
- `label_result_field`
- `error_invalid_number`
- `error_divide_by_zero`

Ini memudahkan maintenance dan lokalisasi.

## 4) Menjalankan Aplikasi

### Opsi Android Studio
1. Klik **Sync Project with Gradle Files**.
2. Pilih emulator/perangkat Android.
3. Klik **Run app**.

### Opsi Terminal (Windows PowerShell)
```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

Untuk menjalankan unit test:
```powershell
.\gradlew.bat test
```

## 5) Pengembangan Lanjutan (Opsional)

Ide fitur berikutnya:
- Tombol `%`
- Riwayat perhitungan
- Pembulatan hasil dengan jumlah digit tertentu
- Dukungan tema gelap/terang yang lebih kustom

## 6) Catatan

Jika muncul error build terkait versi SDK/AGP, pastikan:
- Android SDK platform yang dibutuhkan sudah terinstal
- JDK yang dipakai Android Studio adalah JDK 11
- Gradle sync selesai tanpa error

