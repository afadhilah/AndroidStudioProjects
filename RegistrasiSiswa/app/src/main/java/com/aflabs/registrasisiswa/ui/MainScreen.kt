package com.aflabs.registrasisiswa.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aflabs.registrasisiswa.data.Siswa
import com.aflabs.registrasisiswa.viewmodel.StudentViewModel

@Composable
fun MainScreen(
    viewModel: StudentViewModel
) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var editingSiswa by remember { mutableStateOf<Siswa?>(null) }

    val siswaList by viewModel.siswaList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Registrasi Siswa",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Kelola data siswa",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        FormInput(
            nama = nama,
            email = email,
            onNamaChange = { nama = it },
            onEmailChange = { email = it },
            isEditing = editingSiswa != null,
            onTambahClick = {
                if (nama.isBlank() || email.isBlank()) {
                    return@FormInput
                }

                if (!email.contains("@")) {
                    return@FormInput
                }

                val currentEditing = editingSiswa
                if (currentEditing != null) {
                    viewModel.editSiswa(
                        currentEditing.copy(
                            nama = nama,
                            email = email
                        )
                    )
                    editingSiswa = null
                } else {
                    viewModel.tambahSiswa(nama, email)
                }

                nama = ""
                email = ""
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Daftar Siswa",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (siswaList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada data siswa",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(siswaList, key = { it.id }) { siswa ->
                    StudentItem(
                        siswa = siswa,
                        onDelete = {
                            if (editingSiswa?.id == siswa.id) {
                                editingSiswa = null
                                nama = ""
                                email = ""
                            }
                            viewModel.hapusSiswa(siswa)
                        },
                        onEdit = {
                            editingSiswa = siswa
                            nama = siswa.nama
                            email = siswa.email
                        }
                    )
                }
            }
        }
    }
}
