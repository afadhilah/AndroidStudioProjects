# Student Registration App

Student Registration App adalah aplikasi Android modern yang dikembangkan menggunakan bahasa pemrograman Kotlin dan Jetpack Compose. Aplikasi ini dirancang untuk mendemonstrasikan implementasi operasi CRUD (Create, Read, Update, Delete) secara lokal menggunakan Room Database dengan pola arsitektur Model-View-ViewModel (MVVM).

## Teknologi Utama

- Kotlin: Bahasa pemrograman utama untuk pengembangan aplikasi Android modern.
- Jetpack Compose: Toolkit deklaratif untuk membangun antarmuka pengguna (UI) Android yang responsif.
- Room Database: Lapisan abstraksi di atas SQLite untuk penyimpanan data lokal secara terstruktur dan aman.
- MVVM Architecture: Pemisahan logika bisnis (ViewModel), data (Model), dan tampilan (View) untuk kemudahan pemeliharaan kode.
- Coroutines & Flow: Pengolahan data asinkron dan reaktivitas aliran data secara real-time.
- Material Design 3: Panduan desain visual modern dari Google yang diterapkan pada komponen kartu, tombol, dan input form.

## Fitur Utama

1. Tambah Siswa (Create): Memasukkan nama dan email siswa ke dalam database lokal.
2. Tampilkan Siswa (Read): Menampilkan daftar seluruh siswa yang terdaftar dalam database lokal secara reaktif.
3. Edit Siswa (Update): Mengubah informasi nama atau email siswa yang sudah terdaftar secara langsung melalui form input.
4. Hapus Siswa (Delete): Menghapus data siswa tertentu dari penyimpanan lokal secara instan.
5. Validasi Input: Memastikan kolom nama dan email terisi serta format email menggunakan tanda "@" sebelum data disimpan.
6. Desain UI Dinamis: Badge inisial nama siswa menggunakan warna latar belakang pastel yang berubah secara dinamis tergantung pada karakter pertama nama siswa.
7. Dukungan Tema: Integrasi warna Material 3 yang kompatibel dengan mode terang dan gelap perangkat.

## Struktur Direktori Proyek

```text
com.aflabs.registrasisiswa
│
├── data
│   ├── Siswa.kt (Entity Room)
│   ├── SiswaDao.kt (Interface DAO)
│   └── AppDatabase.kt (Kelas Database Room)
│
├── ui
│   ├── Theme.kt (Tema Material 3)
│   ├── FormInput.kt (Form Input Composable)
│   ├── StudentItem.kt (Card Item Daftar Siswa)
│   └── MainScreen.kt (Tampilan Utama Composable)
│
├── viewmodel
│   └── StudentViewModel.kt (Logika Bisnis & State Management)
│
└── MainActivity.kt (Entry Point Utama Aplikasi)
```

## Persyaratan Sistem

- Android SDK: Minimum SDK 24, Target SDK 34 (atau SDK 36 sesuai build.gradle proyek).
- Gradle: Menggunakan Gradle Version Catalogs (libs.versions.toml).
- JDK: Versi 11 atau yang lebih baru.
- Android Studio: Versi kompatibel dengan Kotlin 2.0.21 dan Jetpack Compose terbaru.
