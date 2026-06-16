# Android Studio Projects

Kumpulan project aplikasi Android untuk belajar dan praktik pengembangan aplikasi Android dengan Kotlin dan Jetpack Compose.

## Daftar Project

### 1. **CatatanKeuanganPribadi (Artharum)**
Aplikasi Android untuk mencatat dan mengelola keuangan pribadi dengan fitur transaksi, budget, dan statistik.
- **Fitur**: Pencatatan income, expense, transfer; Dashboard ringkasan saldo, income, expense; Daftar transaksi filter by period, account, category, search; Budget per kategori & monitor usage; Statistik visualisasi pengeluaran; Multiple accounts (cash, bank, e-wallet).
- **Tech Stack**: Kotlin, Clean Architecture + MVVM, Jetpack Compose, Room Database, Material 3, Gradle 8.13, AGP 8.11.2, Java 21.
- **Folder**: `CatatanKeuanganPribadi/`
- **Detail**: Lihat [CatatanKeuanganPribadi/README.md](CatatanKeuanganPribadi/README.md)

### 2. **CoffeeBliss**
Aplikasi Digital Membership toko kopi Coffee Bliss untuk mengelola loyalitas pelanggan, melacak poin otomatis, dan penukaran rewards.
- **Fitur**: Registrasi member baru dengan validasi email real-time, Daftar member & pencarian, Digital Membership Card (ID unik `CB-XXXXX`, Tiering, sisa poin, QR Code generator), Kalkulasi poin otomatis (kelipatan Rp10.000 = 1 poin), Riwayat transaksi & log hadiah, Penukaran poin (Redeem Rewards).
- **Tech Stack**: Kotlin, Jetpack Compose (Material 3), Room Database, MVVM, Navigation Compose, Custom QR Code Bitmap Generator (offline).
- **Folder**: `CoffeeBliss/`
- **Detail**: Lihat [CoffeeBliss/README.md](CoffeeBliss/README.md)

### 3. **DiceRoller**
Aplikasi pelempar dadu sederhana menggunakan Jetpack Compose.
- **Fitur**: Simulasi lemparan dadu, tampilan hasil dadu
- **Tech Stack**: Kotlin, Jetpack Compose, Material 3
- **Folder**: `DiceRoller/`

### 4. **HappyBirthDay**
Aplikasi untuk menampilkan ucapan ulang tahun dengan desain minimalis.
- **Fitur**: Ucapan ulang tahun interaktif, preview Compose
- **Tech Stack**: Kotlin, Jetpack Compose, Material 3, AGP 8.11.2
- **Folder**: `HappyBirthDay/`
- **Detail**: Lihat [HappyBirthDay/README.md](HappyBirthDay/README.md)

### 5. **Kalkulator**
Aplikasi kalkulator dengan operasi dasar dan validasi input.
- **Fitur**: Operasi dasar (`+`, `-`, `*`, `/`), validasi input angka, proteksi pembagian dengan nol, dukungan desimal koma (`,`), UI aman saat keyboard muncul.
- **Tech Stack**: Kotlin, Jetpack Compose, Material 3, AGP 8.11.2
- **Folder**: `Kalkulator/`
- **Detail**: Lihat [Kalkulator/README.md](Kalkulator/README.md)

### 6. **Login**
Aplikasi login dengan validasi form sederhana.
- **Fitur**: Validasi email dan password
- **Tech Stack**: Kotlin, Android UI
- **Folder**: `Login/`

### 7. **LoginMVVM**
Aplikasi login dan register menggunakan arsitektur MVVM dengan UI modern (glassmorphism/semi-transparan).
- **Fitur**: Register (validasi minimal 4 karakter password, username unik), Login (validasi input, feedback sukses/kegagalan via Toast/banner), UI Modern (glassmorphism/semi-transparan, gradasi latar belakang), StateFlow.
- **Tech Stack**: Kotlin, Jetpack Compose (Material 3), Room Database, MVVM + Repository Pattern, Coroutines & Flow.
- **Folder**: `LoginMVVM/`
- **Detail**: Lihat [LoginMVVM/README.md](LoginMVVM/README.md)

### 8. **Marketplace (Skoola)**
Platform marketplace digital mobile khusus untuk lingkungan sekolah dan kampus untuk memfasilitasi transaksi jual-beli barang bekas, penyewaan perlengkapan, dan jasa akademik.
- **Fitur**: Autentikasi (OTP 6 digit), Home Screen (promotional banner, kategori dinamis, rekomendasi lokasi), Pencarian & filter produk, Detail produk (profil penjual, rating, ulasan), Keranjang belanja (cart), Manajemen alamat & kampus, Checkout & pembayaran terintegrasi (Bank, E-Wallet, COD, QRIS), Dashboard penjual & Unggah produk.
- **Tech Stack**: Kotlin 2.0+, Clean Architecture + MVVM, Jetpack Compose (Material 3), Dagger Hilt, Room Database, Retrofit & OkHttp, Coroutines & Flow, Coil, Kotlinx Serialization.
- **Folder**: `Marketplace/`
- **Detail**: Lihat [Marketplace/README.md](Marketplace/README.md)

### 9. **MyApplication**
Aplikasi dasar template untuk memulai project baru.
- **Fitur**: Template project Android dasar
- **Tech Stack**: Kotlin
- **Folder**: `MyApplication/`

### 10. **NewsApp**
Aplikasi berita modern untuk menampilkan berita terkini menggunakan REST API dari NewsAPI secara real-time.
- **Fitur**: Integrasi API Berita Riil (NewsAPI), Antarmuka Premium (Slate Theme), Bottom Navigation (Home, Search, Saved), In-App WebView (membuka berita asli di dalam aplikasi).
- **Tech Stack**: Kotlin, Jetpack Compose (Material 3), Retrofit & Gson, Coroutines & StateFlow, Coil, Navigation Compose.
- **Folder**: `NewsApp/`
- **Detail**: Lihat [NewsApp/README.md](NewsApp/README.md)

### 11. **RegistrasiSiswa (Student Registration App)**
Aplikasi pendaftaran siswa untuk mendemonstrasikan implementasi operasi CRUD secara lokal dengan Room Database.
- **Fitur**: CRUD lengkap siswa (Create, Read, Update, Delete), Validasi input (nama & format email), UI Dinamis (badge inisial nama dengan warna pastel dinamis), Dukungan tema (Material 3 terang/gelap).
- **Tech Stack**: Kotlin, Jetpack Compose (Material 3), Room Database, MVVM, Coroutines & Flow.
- **Folder**: `RegistrasiSiswa/`
- **Detail**: Lihat [RegistrasiSiswa/README.md](RegistrasiSiswa/README.md)

### 12. **tugas1**
Project tugas/assignment pertama.
- **Folder**: `tugas1/`

## Persyaratan Sistem

Untuk menjalankan semua project, pastikan sudah terpasang:
- **Android Studio** (versi terbaru, minimal kompatibel dengan AGP 8.x)
- **JDK 11** atau lebih tinggi
- **Android SDK** dengan konfigurasi:
  - `compileSdk 36` atau lebih
  - `minSdk 24`
  - `targetSdk 36` atau lebih

## Cara Menjalankan Project

### Opsi 1: Melalui Android Studio
1. Buka Android Studio
2. Pilih **File > Open**
3. Navigasi ke folder project yang ingin dijalankan
4. Tunggu Gradle Sync selesai
5. Klik **Run** atau tekan `Shift + F10`

### Opsi 2: Melalui Terminal (PowerShell)
```powershell
# Navigasi ke folder project
cd DiceRoller

# Build debug
.\gradlew.bat :app:assembleDebug

# Install ke emulator/device
.\gradlew.bat :app:installDebug
```

> **Catatan**: Pastikan emulator atau device fisik sudah aktif sebelum menjalankan `installDebug`.

## Struktur Project Umum

Setiap project memiliki struktur standar Android:
```
ProjectName/
├── app/
│   ├── src/
│   │   ├── main/          # Kode sumber utama
│   │   ├── test/          # Unit test
│   │   └── androidTest/   # Instrumented test
│   ├── build.gradle.kts   # Konfigurasi module app
│   └── proguard-rules.pro # ProGuard rules
├── gradle/                # Gradle wrapper config
├── build.gradle.kts       # Build script root
├── settings.gradle.kts    # Project settings
└── README.md              # Dokumentasi project
```

## Gradle Tasks yang Tersedia

```powershell
# Clean build
.\gradlew.bat clean

# Build debug
.\gradlew.bat :app:assembleDebug

# Build release
.\gradlew.bat :app:assembleRelease

# Run unit tests
.\gradlew.bat :app:testDebugUnitTest

# Run instrumented tests
.\gradlew.bat :app:connectedAndroidTest

# Verify (build + test)
.\gradlew.bat verify
```

## Dokumentasi Tambahan

Setiap project memiliki dokumentasi lengkap di folder masing-masing:
- [CatatanKeuanganPribadi/README.md](CatatanKeuanganPribadi/README.md) - Pengelolaan keuangan pribadi (Artharum)
- [CoffeeBliss/README.md](CoffeeBliss/README.md) - Digital Membership & Loyalitas pelanggan
- [HappyBirthDay/README.md](HappyBirthDay/README.md) - Tutorial lengkap Jetpack Compose
- [Kalkulator/README.md](Kalkulator/README.md) - Penjelasan logika kalkulator
- [LoginMVVM/README.md](LoginMVVM/README.md) - Autentikasi dengan arsitektur MVVM & Room Database
- [Marketplace/README.md](Marketplace/README.md) - Platform marketplace digital khusus (Skoola)
- [NewsApp/README.md](NewsApp/README.md) - Integrasi NewsAPI & In-App WebView
- [RegistrasiSiswa/README.md](RegistrasiSiswa/README.md) - CRUD Siswa dengan Room Database
- Lihat folder `docs/` atau README masing-masing project untuk informasi lebih lanjut

## Tips Pembelajaran

1. **Mulai dari DiceRoller** - Project ini paling sederhana untuk memahami dasar Compose
2. **Lanjut ke HappyBirthDay** - Belajar layout dan styling
3. **Praktik Logika dengan Kalkulator** - Implementasi state management dan validasi

## Catatan Teknis

- Semua project menggunakan **Kotlin** sebagai bahasa utama
- Dependency management menggunakan **Gradle Version Catalog** (`gradle/libs.versions.toml`)
- Beberapa project menggunakan **Jetpack Compose** untuk UI modern
- Gradle wrapper (`gradlew.bat`) sudah included untuk konsistensi versi Gradle

## Troubleshooting

### Gradle Sync Gagal
- Update Android Studio ke versi terbaru
- Hapus folder `.gradle` di home directory
- Jalankan `.\gradlew.bat clean` sebelum sync ulang

### Build Error
- Pastikan JDK version yang digunakan adalah JDK 11 atau lebih tinggi
- Verify Android SDK versi di `local.properties`
- Cek file `build.gradle.kts` dan `libs.versions.toml` untuk kompatibilitas