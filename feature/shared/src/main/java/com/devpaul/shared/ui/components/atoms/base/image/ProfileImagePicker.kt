package com.devpaul.shared.ui.components.atoms.base.image

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.shared.domain.base64ToImageBitmap
import com.devpaul.shared.domain.bitmapToBase64
import com.devpaul.shared.domain.uriToBitmap
import java.io.File

@Composable
fun ProfileImagePicker(
    defaultImageUrl: String,
    base64Image: String?,
    modifier: Modifier = Modifier,
    showDialogOnClick: Boolean = true,
    onImageSelected: (base64: String?, uri: Uri?) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val photoFile = remember {
        File(context.getExternalFilesDir("Pictures"), "profile_${System.currentTimeMillis()}.jpg")
    }
    val photoUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        photoFile
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = photoUri
            val bitmap = uriToBitmap(context, photoUri)
            val base64 = bitmapToBase64(bitmap)
            onImageSelected(base64, photoUri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            val bitmap = uriToBitmap(context, it)
            val base64 = bitmapToBase64(bitmap, quality = 40)
            onImageSelected(base64, uri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showDialog = false
            cameraLauncher.launch(photoUri)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    val imageBitmap = base64Image?.let { base64ToImageBitmap(it) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Selecciona imagen",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            showDialog = false
                            if (ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                cameraLauncher.launch(photoUri)
                            } else {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_photo_camera_24),
                            contentDescription = "Abrir cámara",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        )
                        Text(text = "Cámara")
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            showDialog = false
                            galleryLauncher.launch("image/*")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_gallery_thumbnail_24),
                            contentDescription = "Abrir galería",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        )
                        Text(text = "Galería")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    Box(
        modifier = modifier
            .size(180.dp)
            .background(Color.White)
            .then(
                if (showDialogOnClick)
                    Modifier.clickable { showDialog = true }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUri ?: defaultImageUrl),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
            )
        }

        if (showDialogOnClick) {
            Icon(
                painter = painterResource(id = R.drawable.outline_photo_camera_24),
                contentDescription = "Cambiar foto",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (2).dp, y = (2).dp)
                    .size(38.dp)
                    .background(BrickRed, CircleShape)
                    .padding(6.dp)
            )
        }
    }

}