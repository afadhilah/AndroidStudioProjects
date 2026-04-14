# Android Studio Projects

Kumpulan project aplikasi Android untuk belajar dan praktik pengembangan aplikasi Android dengan Kotlin dan Jetpack Compose.

## Daftar Project

### 1. **DiceRoller**
Aplikasi pelempar dadu sederhana menggunakan Jetpack Compose.
- **Fitur**: Simulasi lemparan dadu, tampilan hasil dadu
- **Tech Stack**: Kotlin, Jetpack Compose, Material 3
- **Folder**: `DiceRoller/`

### 2. **HappyBirthDay**
Aplikasi untuk menampilkan ucapan ulang tahun dengan desain minimalis.
- **Fitur**: Ucapan ulang tahun interaktif, preview Compose
- **Tech Stack**: Kotlin, Jetpack Compose, Material 3, AGP 8.11.2
- **Folder**: `HappyBirthDay/`
- **Detail**: Lihat [HappyBirthDay/README.md](HappyBirthDay/README.md)

### 3. **Kalkulator**
Aplikasi kalkulator dengan operasi dasar dan validasi input.
- **Fitur**: 
  - Operasi dasar: `+`, `-`, `*`, `/`
  - Validasi input angka
  - Proteksi pembagian dengan nol
  - Dukungan desimal koma (`,`)
  - UI aman saat keyboard muncul
- **Tech Stack**: Kotlin, Jetpack Compose, Material 3, AGP 8.11.2
- **Folder**: `Kalkulator/`
- **Detail**: Lihat [Kalkulator/README.md](Kalkulator/README.md)

### 4. **Login**
Aplikasi login dengan validasi form sederhana.
- **Fitur**: Validasi email dan password
- **Tech Stack**: Kotlin, Android UI
- **Folder**: `Login/`

### 5. **MyApplication**
Aplikasi dasar template untuk memulai project baru.
- **Fitur**: Template project Android dasar
- **Tech Stack**: Kotlin
- **Folder**: `MyApplication/`

### 6. **tugas1**
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
- [HappyBirthDay/README.md](HappyBirthDay/README.md) - Tutorial lengkap Jetpack Compose
- [Kalkulator/README.md](Kalkulator/README.md) - Penjelasan logika kalkulator
- Lihat folder `docs/` di setiap project untuk informasi lebih lanjut

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